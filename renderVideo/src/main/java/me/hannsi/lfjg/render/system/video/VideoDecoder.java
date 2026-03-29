package me.hannsi.lfjg.render.system.video;

import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class VideoDecoder {
    private boolean paused = false;
    private boolean finished = false;
    private boolean doVideo = true;
    private boolean doAudio = false;
    private Frame frame;
    private FFmpegFrameGrabber grabber;
    private Java2DFrameConverter converter;
    private double fps;
    private int width;
    private int height;
    private long startSystemNanos = -1;
    private long startVideoTimestamp = -1;

    public VideoDecoder open(Location videoLocation) throws Exception {
        switch (videoLocation.locationType()) {
            case RESOURCE, FILE ->
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
    }

    public void grabberStop() throws Exception {
        grabber.stop();
    }


    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }

    public void seek(long timestampMicroseconds) {
        try {
            pause();
            grabber.setTimestamp(timestampMicroseconds);
            resume();
        } catch (FFmpegFrameGrabber.Exception e) {
            throw new RuntimeException("Failed to seek to timestamp: " + timestampMicroseconds, e);
        }
    }

    public void seek(double seconds) {
        long timestampMicros = (long) (seconds * 1_000_000);
        seek(timestampMicros);
    }

    public double getCurrentTimestampSeconds() {
        return grabber.getTimestamp() / 1_000_000.0;
    }

    public long getCurrentTimestampMicros() {
        return grabber.getTimestamp();
    }

    public double getDurationSeconds() {
        return grabber.getLengthInTime() / 1_000_000.0;
    }

    public double getDurationMicros() {
        return grabber.getLengthInTime();
    }

    public ByteBuffer nextFrame() {
        long currentSystemTime = System.nanoTime();

        if (startSystemNanos == -1) {
            startSystemNanos = currentSystemTime;
            startVideoTimestamp = grabber.getTimestamp();
        }

        long elapsedSystemMicros = (currentSystemTime - startSystemNanos) / 1000;
        long expectedVideoTimestamp = startVideoTimestamp + elapsedSystemMicros;
        long actualVideoTimestamp = grabber.getTimestamp();

        if (!paused && actualVideoTimestamp < expectedVideoTimestamp) {
            do
            {
                try {
                    frame = grabber.grabFrame(doAudio, doVideo, true, false, true);
                } catch (FFmpegFrameGrabber.Exception e) {
                    throw new RuntimeException(e);
                }
                if (frame == null) {
                    finished = true;
                    return null;
                }
            } while (grabber.getTimestamp() < expectedVideoTimestamp);

            if (grabber.getTimestamp() >= grabber.getLengthInTime()) {
                finished = true;
            }

            if (frame != null && doVideo && frame.image != null) {
                BufferedImage image = converter.convert(frame);

                ByteBuffer byteImage = IOUtil.convertBufferedImageToByteBuffer(image, false);
                width = image.getWidth();
                height = image.getHeight();

                return byteImage;
            }
        }

        return null;
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
        this.paused = paused;
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
}
