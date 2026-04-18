package me.hannsi.lfjg.render.system.video;

import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static me.hannsi.lfjg.render.RenderVideoSystemSetting.VIDEO_DECODER_AUDIO_QUEUE_CAPACITY;
import static me.hannsi.lfjg.render.RenderVideoSystemSetting.VIDEO_DECODER_VIDEO_QUEUE_CAPACITY;

public class VideoDecoder {
    public FFmpegFrameGrabber grabber;
    private final BlockingQueue<TimedVideoFrame> videoQueue = new LinkedBlockingQueue<>(VIDEO_DECODER_VIDEO_QUEUE_CAPACITY);
    private final BlockingQueue<TimedAudioFrame> audioQueue = new LinkedBlockingQueue<>(VIDEO_DECODER_AUDIO_QUEUE_CAPACITY);
    private long playbackStartNanos = -1;
    private long playbackStartMicros = -1;
    private volatile long pausedAtNanos = -1;
    private volatile boolean paused = false;
    private volatile boolean decodeThreadWaiting = false;
    private volatile boolean decodeLoopStarted = false;
    private volatile long seekRequestMicros = -1;
    private volatile boolean seekPending = false;
    private volatile Throwable decodeError = null;
    private boolean finished = false;
    private boolean doVideo = false;
    private boolean doAudio = false;
    private Java2DFrameConverter converter;
    private double fps;
    private int width;
    private int height;

    public VideoDecoder open(Location videoLocation) throws Exception {
        switch (videoLocation.locationType()) {
            case FILE ->
                    grabber = new FFmpegFrameGrabber(videoLocation.getFile());
            case RESOURCE ->
                    grabber = new FFmpegFrameGrabber(videoLocation.openStream());
            default ->
                    throw new IllegalStateException("Unexpected value: " + videoLocation.locationType());
        }
        converter = new Java2DFrameConverter();
        return this;
    }

    public void grabberStart() throws Exception {
        grabber.start();
        fps = grabber.getFrameRate();
        width = grabber.getImageWidth();
        height = grabber.getImageHeight();

        playbackStartNanos = System.nanoTime();
        playbackStartMicros = grabber.getTimestamp();
    }

    public void grabberStop() throws Exception {
        if (grabber != null) {
            grabber.stop();
        }
    }

    public void decodeLoop() {
        decodeLoopStarted = true;

        while (!finished) {
            if (seekPending) {
                videoQueue.clear();
                audioQueue.clear();

                try {
                    grabber.setTimestamp(seekRequestMicros, true);

                    Frame firstFrame = grabber.grabFrame(false, true, true, false, true);
                    long actualTimestamp = (firstFrame != null) ? grabber.getTimestamp() : seekRequestMicros;

                    playbackStartNanos = System.nanoTime();
                    playbackStartMicros = actualTimestamp;

                    if (firstFrame != null && firstFrame.image != null) {
                        ByteBuffer buffer = convertVideoFrame(firstFrame);
                        videoQueue.offer(new TimedVideoFrame(buffer, actualTimestamp));
                    }
                } catch (Exception e) {
                    decodeError = e;
                    finished = true;
                    seekPending = false;
                    break;
                }

                if (paused) {
                    pausedAtNanos = System.nanoTime();
                }

                finished = false;
                seekPending = false;
                continue;
            }

            if (paused) {
                decodeThreadWaiting = true;
                while (paused && !seekPending) {
                    Thread.onSpinWait();
                }
                decodeThreadWaiting = false;
                continue;
            }

            try {
                Frame frame = grabber.grabFrame(doAudio, doVideo, true, false, true);
                if (frame == null) {
                    finished = true;
                    break;
                }

                long ts = grabber.getTimestamp();
                if (frame.image != null) {
                    ByteBuffer buffer = convertVideoFrame(frame);
                    while (!seekPending) {
                        if (videoQueue.offer(new TimedVideoFrame(buffer, ts), 5, TimeUnit.MILLISECONDS)) {
                            break;
                        }
                    }
                } else if (frame.samples != null && doAudio) {
                    ByteBuffer buffer = convertAudioFrame(frame);
                    while (!seekPending) {
                        if (audioQueue.offer(new TimedAudioFrame(buffer, ts), 5, TimeUnit.MILLISECONDS)) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                decodeError = e;
                finished = true;
                break;
            }
        }
    }

    private ByteBuffer convertVideoFrame(Frame frame) {
        BufferedImage image = converter.convert(frame);
        width = image.getWidth();
        height = image.getHeight();
        return IOUtil.convertBufferedImageToByteBuffer(image, false);
    }

    private ByteBuffer convertAudioFrame(Frame frame) {
        int channelCount = frame.samples.length;

        if (frame.samples[0] instanceof ShortBuffer) {
            ShortBuffer[] channels = new ShortBuffer[channelCount];
            int sampleCount = 0;
            for (int i = 0; i < channelCount; i++) {
                channels[i] = (ShortBuffer) frame.samples[i];
                channels[i].rewind();
                sampleCount = channels[i].remaining();
            }

            ByteBuffer byteBuffer = ByteBuffer
                    .allocateDirect(sampleCount * channelCount * 2)
                    .order(ByteOrder.nativeOrder());
            for (int i = 0; i < sampleCount; i++) {
                for (int ch = 0; ch < channelCount; ch++) {
                    byteBuffer.putShort(channels[ch].get());
                }
            }
            byteBuffer.flip();
            return byteBuffer;

        } else if (frame.samples[0] instanceof FloatBuffer) {
            FloatBuffer[] channels = new FloatBuffer[channelCount];
            int sampleCount = 0;
            for (int i = 0; i < channelCount; i++) {
                channels[i] = (FloatBuffer) frame.samples[i];
                channels[i].rewind();
                sampleCount = channels[i].remaining();
            }

            ByteBuffer byteBuffer = ByteBuffer
                    .allocateDirect(sampleCount * channelCount * 4)
                    .order(ByteOrder.nativeOrder());
            for (int i = 0; i < sampleCount; i++) {
                for (int ch = 0; ch < channelCount; ch++) {
                    byteBuffer.putFloat(channels[ch].get());
                }
            }
            byteBuffer.flip();
            return byteBuffer;

        } else {
            throw new UnsupportedOperationException("Unsupported audio buffer type: " + frame.samples[0].getClass().getSimpleName());
        }
    }

    public ByteBuffer nextVideoBuffer() {
        if (decodeError != null) {
            throw new RuntimeException("Decode error", decodeError);
        }

        TimedVideoFrame peeked = videoQueue.peek();
        if (peeked == null) {
            return null;
        }

        long nowMicros = getElapsedPlaybackMicros();
        if (nowMicros < peeked.timestampMicros()) {
            return null;
        }

        TimedVideoFrame frame = null;
        while (true) {
            TimedVideoFrame next = videoQueue.peek();
            if (next == null || next.timestampMicros() > nowMicros) {
                break;
            }
            frame = videoQueue.poll();
        }

        return frame != null ? frame.buffer() : null;
    }

    public ByteBuffer nextAudioBuffer() {
        TimedAudioFrame peeked = audioQueue.peek();
        if (peeked == null) {
            return null;
        }

        long nowMicros = getElapsedPlaybackMicros();
        if (nowMicros < peeked.timestampMicros()) {
            return null;
        }

        TimedAudioFrame frame = audioQueue.poll();
        return frame != null ? frame.buffer() : null;
    }

    private long getElapsedPlaybackMicros() {
        if (playbackStartNanos == -1) {
            return 0;
        }
        return (System.nanoTime() - playbackStartNanos) / 1000 + playbackStartMicros;
    }

    public void pause() {
        if (!paused) {
            pausedAtNanos = System.nanoTime();
            this.paused = true;
        }
    }

    public void resume() {
        if (paused) {
            if (playbackStartNanos == -1) {
                playbackStartNanos = System.nanoTime();
                playbackStartMicros = grabber.getTimestamp();
            } else if (pausedAtNanos != -1) {
                long pausedDuration = System.nanoTime() - pausedAtNanos;
                playbackStartNanos += pausedDuration;
            }
            pausedAtNanos = -1;
            this.paused = false;
        }
    }

    public void seek(long timestampMicroseconds) {
        seekRequestMicros = timestampMicroseconds;
        seekPending = true;

        while (seekPending) {
            Thread.onSpinWait();
        }
    }

    public void seek(double seconds) {
        long timestampMicros = (long) (seconds * 1_000_000);
        seek(timestampMicros);
    }

    public double getCurrentTimestampSeconds() {
        return getElapsedPlaybackMicros() / 1_000_000.0;
    }

    public long getCurrentTimestampMicros() {
        return getElapsedPlaybackMicros();
    }

    public double getDurationSeconds() {
        return grabber.getLengthInTime() / 1_000_000.0;
    }

    public long getDurationMicros() {
        return grabber.getLengthInTime();
    }

    public double getFps() {
        return fps;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        if (paused) {
            pause();
        } else {
            resume();
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isDoVideo() {
        return doVideo;
    }

    public void setDoVideo(boolean doVideo) {
        this.doVideo = doVideo;
    }

    public boolean isDoAudio() {
        return doAudio;
    }

    public void setDoAudio(boolean doAudio) {
        this.doAudio = doAudio;
    }

    public FFmpegFrameGrabber getGrabber() {
        return grabber;
    }

    private record TimedVideoFrame(ByteBuffer buffer, long timestampMicros) {}

    private record TimedAudioFrame(ByteBuffer buffer, long timestampMicros) {}
}