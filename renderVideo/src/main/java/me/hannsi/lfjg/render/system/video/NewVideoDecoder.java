package me.hannsi.lfjg.render.system.video;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.math.io.ByteBufferPool;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.concurrent.*;

import static me.hannsi.lfjg.core.utils.math.MathHelper.min;
import static me.hannsi.lfjg.render.RenderVideoSystemSetting.*;

public class NewVideoDecoder {
    private final BlockingQueue<TimedVideoFrame> videoQueue = new LinkedBlockingQueue<>(VIDEO_DECODER_VIDEO_QUEUE_CAPACITY);
    private final BlockingQueue<TimedAudioFrame> audioQueue = new LinkedBlockingQueue<>(VIDEO_DECODER_AUDIO_QUEUE_CAPACITY);
    private final FFmpegFrameGrabber grabber;
    private FFmpegFrameFilter filter;
    private ByteBufferPool yBufferPool;
    private ByteBufferPool uvBufferPool;
    private ByteBufferPool audioBufferPool;
    private volatile boolean running;
    private ExecutorService executor;
    private boolean doVideo;
    private boolean doAudio;

    public NewVideoDecoder(Location videoLocation) throws Exception {
        switch (videoLocation.locationType()) {
            case RESOURCE ->
                    grabber = new FFmpegFrameGrabber(videoLocation.openStream());
            case FILE ->
                    grabber = new FFmpegFrameGrabber(videoLocation.getFile());
            default ->
                    throw new IllegalStateException("Unexpected value: " + videoLocation.locationType());
        }
    }

    public void grabberStart(boolean doVideo, boolean doAudio) throws Exception {
        this.doVideo = doVideo;
        this.doAudio = doAudio;

        grabber.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        grabber.setImageMode(FFmpegFrameGrabber.ImageMode.RAW);
        grabber.start();

        yBufferPool = new ByteBufferPool(VIDEO_DECODER_VIDEO_BUFFER_POOL_SIZE, grabber.getImageWidth() * grabber.getImageHeight());
        uvBufferPool = new ByteBufferPool(VIDEO_DECODER_VIDEO_BUFFER_POOL_SIZE, grabber.getImageWidth() * grabber.getImageHeight() / 2);
        audioBufferPool = new ByteBufferPool(VIDEO_DECODER_AUDIO_BUFFER_POOL_SIZE, grabber.getSampleRate() * grabber.getAudioChannels() * 4 / 10);

        running = true;
        executor = Executors.newSingleThreadExecutor();
        executor.execute(this::decode);
    }

    public void grabberStop() throws Exception {
        running = false;

        if (grabber != null) {
            grabber.stop();
        }

        if (executor != null) {
            executor.shutdown();
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }
    }

    private void decode() {
        try {
            TimedVideoFrame vf;
            while ((vf = videoQueue.poll()) != null) {
                yBufferPool.release(vf.y());
                uvBufferPool.release(vf.uv());
            }

            TimedAudioFrame af;
            while ((af = audioQueue.poll()) != null) {
                audioBufferPool.release(af.buffer());
            }

            while (running) {
                Frame frame;
                try {
                    frame = grabber.grab();
                } catch (Exception e) {
                    break;
                }
                if (frame == null) {
                    break;
                }

                long timestamp = frame.timestamp;
                if (doVideo && frame.image != null && frame.opaque instanceof AVFrame avFrame) {
                    BytePointer yPtr = avFrame.data(0);
                    BytePointer uPtr = avFrame.data(1);
                    BytePointer vPtr = avFrame.data(2);

                    int width = avFrame.width();
                    int height = avFrame.height();

                    int strideY = avFrame.linesize(0);
                    int strideU = avFrame.linesize(1);
                    int strideV = avFrame.linesize(2);

                    ByteBuffer yBuffer = yBufferPool.borrow();
                    ByteBuffer uvBuffer = uvBufferPool.borrow();

                    yBuffer.clear();
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            yBuffer.put(yPtr.get((long) y * strideY + x));
                        }
                    }

                    uvBuffer.clear();
                    int uvWidth = width / 2;
                    int uvHeight = height / 2;
                    for (int y = 0; y < uvHeight; y++) {
                        for (int x = 0; x < uvWidth; x++) {
                            uvBuffer.put(uPtr.get((long) y * strideU + x));
                            uvBuffer.put(vPtr.get((long) y * strideV + x));
                        }
                    }

                    yBuffer.flip();
                    uvBuffer.flip();

                    int counter = 0;
                    TimedVideoFrame old;
                    while (!videoQueue.offer(new TimedVideoFrame(yBuffer, uvBuffer, width, height, strideY, strideU, strideV, timestamp)) && counter < 4) {
                        old = videoQueue.poll();
                        if (old != null) {
                            yBufferPool.release(old.y());
                            uvBufferPool.release(old.uv());
                        }
                        counter++;
                    }
                }

                if (doAudio && frame.samples != null) {
                    int channelCount = frame.samples.length;

                    ByteBuffer buffer;
                    if (frame.samples[0] instanceof ShortBuffer) {
                        ShortBuffer[] channels = new ShortBuffer[channelCount];
                        int sampleCount = Integer.MAX_VALUE;
                        for (int i = 0; i < channelCount; i++) {
                            channels[i] = (ShortBuffer) frame.samples[i];
                            channels[i].rewind();
                            sampleCount = min(sampleCount, channels[i].remaining());
                        }

                        int required = sampleCount * channelCount * 2;

                        ByteBuffer byteBuffer = audioBufferPool.borrow();
                        if (byteBuffer.capacity() < required) {
                            audioBufferPool.release(byteBuffer);
                            byteBuffer = ByteBuffer.allocateDirect(required);
                        }
                        byteBuffer.clear();
                        for (int i = 0; i < sampleCount; i++) {
                            for (int ch = 0; ch < channelCount; ch++) {
                                byteBuffer.putShort(channels[ch].get());
                            }
                        }
                        byteBuffer.flip();
                        buffer = byteBuffer;
                    } else if (frame.samples[0] instanceof FloatBuffer) {
                        FloatBuffer[] channels = new FloatBuffer[channelCount];
                        int sampleCount = Integer.MAX_VALUE;
                        for (int i = 0; i < channelCount; i++) {
                            channels[i] = (FloatBuffer) frame.samples[i];
                            channels[i].rewind();
                            sampleCount = min(sampleCount, channels[i].remaining());
                        }

                        int required = sampleCount * channelCount * 4;

                        ByteBuffer byteBuffer = audioBufferPool.borrow();
                        if (byteBuffer.capacity() < required) {
                            audioBufferPool.release(byteBuffer);
                            byteBuffer = ByteBuffer.allocateDirect(required);
                        }
                        byteBuffer.clear();
                        for (int i = 0; i < sampleCount; i++) {
                            for (int ch = 0; ch < channelCount; ch++) {
                                byteBuffer.putFloat(channels[ch].get());
                            }
                        }
                        byteBuffer.flip();
                        buffer = byteBuffer;
                    } else {
                        throw new UnsupportedOperationException("Unsupported audio buffer type: " + frame.samples[0].getClass().getSimpleName());
                    }

                    int counter = 0;
                    TimedAudioFrame old;
                    while (!audioQueue.offer(new TimedAudioFrame(buffer, timestamp)) && counter < 4) {
                        old = audioQueue.poll();
                        if (old != null) {
                            audioBufferPool.release(old.buffer());
                        }
                        counter++;
                    }
                }
            }
        } catch (Exception e) {
            DebugLog.error(getClass(), e);
        }
    }

    public BlockingQueue<TimedVideoFrame> getVideoQueue() {
        return videoQueue;
    }

    public BlockingQueue<TimedAudioFrame> getAudioQueue() {
        return audioQueue;
    }

    public record TimedVideoFrame(ByteBuffer y, ByteBuffer uv, int width, int height, int strideY, int strideU, int strideV, long timestampMicros) {}

    public record TimedAudioFrame(ByteBuffer buffer, long timestampMicros) {}
}
