package me.hannsi.lfjg.render.system.video;

import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.reflection.location.URLLocation;
import me.hannsi.lfjg.utils.toolkit.IOUtil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class VideoFrameSystem {
    private static final Logger log = LoggerFactory.getLogger(VideoFrameSystem.class);
    private Location location;
    private Frame frame;
    private FFmpegFrameGrabber grabber;
    private Java2DFrameConverter java2DFrameConverter;
    private int textureId = -1;
    private int width;
    private int height;

    private boolean paused = false;
    private boolean doVideo = true;
    private boolean doAudio = true;
    private long startSystemNanos = -1;
    private long startVideoTimestamp = -1;

    VideoFrameSystem() {
    }

    public static VideoFrameSystem initVideoFrameSystem() {
        return new VideoFrameSystem();
    }

    public void cleanup() {
        try {
            if (grabber != null) {
                grabber.stop();
                grabber.release();
                grabber.close();
            }
        } catch (Exception e) {
            log.warn("Failed to stop or release FFmpegFrameGrabber", e);
        }

        if (java2DFrameConverter != null) {
            java2DFrameConverter.close();
        }

        if (textureId != -1) {
            glDeleteTextures(textureId);
            textureId = -1;
        }

        if (location != null) {
            location.cleanup();
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                textureId,
                ""
        );
    }

    public VideoFrameSystem createFFmpegFrameGrabber(Location location) {
        this.location = location;
        if (location.isPath()) {
            this.grabber = new FFmpegFrameGrabber(((FileLocation) location).getInputStream());
        } else if (location.isUrl()) {
            this.grabber = new FFmpegFrameGrabber(((URLLocation) location).getURL());
        }

        return this;
    }

    public VideoFrameSystem createJava2DFrameConverter() {
        this.java2DFrameConverter = new Java2DFrameConverter();
        return this;
    }

    public VideoFrameSystem setGrabberOption(String key, String value) {
        grabber.setOption(key, value);

        return this;
    }

    public VideoFrameSystem startVideoLoad() {
        try {
            grabber.start();
        } catch (FFmpegFrameGrabber.Exception e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    public void stopVideoLoad() {
        try {
            grabber.stop();
        } catch (FFmpegFrameGrabber.Exception e) {
            throw new RuntimeException(e);
        }
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

    public void drawFrame() {
        long currentSystemTime = System.nanoTime();

        if (startSystemNanos == -1) {
            startSystemNanos = currentSystemTime;
            startVideoTimestamp = grabber.getTimestamp();
        }

        long elapsedSystemMicros = (currentSystemTime - startSystemNanos) / 1000;
        long expectedVideoTimestamp = startVideoTimestamp + elapsedSystemMicros;
        long actualVideoTimestamp = grabber.getTimestamp();

        if (!paused && actualVideoTimestamp < expectedVideoTimestamp) {
            try {
                frame = grabber.grabFrame(true, true, true, false, true);
                if (frame != null && doVideo && frame.image != null) {
                    BufferedImage image = java2DFrameConverter.convert(frame);

                    ByteBuffer byteImage = IOUtil.convertBufferedImageToByteBuffer(image, BufferedImage.TYPE_4BYTE_ABGR, true);
                    width = image.getWidth();
                    height = image.getHeight();

                    if (textureId == -1) {
                        textureId = glGenTextures();

                        glBindTexture(GL_TEXTURE_2D, textureId);
                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteImage);
                        glBindTexture(GL_TEXTURE_2D, 0);
                    }

                    glBindTexture(GL_TEXTURE_2D, textureId);
                    glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, byteImage);
                    glBindTexture(GL_TEXTURE_2D, 0);
                }
            } catch (FFmpegFrameGrabber.Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public FFmpegFrameGrabber getGrabber() {
        return grabber;
    }

    public void setGrabber(FFmpegFrameGrabber grabber) {
        this.grabber = grabber;
    }

    public Java2DFrameConverter getJava2DFrameConverter() {
        return java2DFrameConverter;
    }

    public void setJava2DFrameConverter(Java2DFrameConverter java2DFrameConverter) {
        this.java2DFrameConverter = java2DFrameConverter;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isDoVideo() {
        return doVideo;
    }

    public VideoFrameSystem setDoVideo(boolean doVideo) {
        this.doVideo = doVideo;
        return this;
    }

    public boolean isDoAudio() {
        return doAudio;
    }

    public VideoFrameSystem setDoAudio(boolean doAudio) {
        this.doAudio = doAudio;
        return this;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public long getStartSystemNanos() {
        return startSystemNanos;
    }

    public void setStartSystemNanos(long startSystemNanos) {
        this.startSystemNanos = startSystemNanos;
    }

    public long getStartVideoTimestamp() {
        return startVideoTimestamp;
    }

    public void setStartVideoTimestamp(long startVideoTimestamp) {
        this.startVideoTimestamp = startVideoTimestamp;
    }
}
