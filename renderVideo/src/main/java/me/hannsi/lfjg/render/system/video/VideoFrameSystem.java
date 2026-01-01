package me.hannsi.lfjg.render.system.video;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.LocationType;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL11.*;

public class VideoFrameSystem {
    private Location location;
    private Frame frame;
    private FFmpegFrameGrabber grabber;
    private Java2DFrameConverter java2DFrameConverter;
    private int textureId = -1;
    private int width;
    private int height;

    private boolean paused = false;
    private boolean doVideo = true;
    private boolean doAudio = false;

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
            DebugLog.warning(getClass(), e);
        }

        if (java2DFrameConverter != null) {
            java2DFrameConverter.close();
        }

        if (textureId != -1) {
            glStateCache.deleteTexture(GL_TEXTURE_2D, textureId);
            textureId = -1;
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
        if (location.locationType() == LocationType.FILE || location.locationType() == LocationType.RESOURCE) {
            try {
                this.grabber = new FFmpegFrameGrabber(location.openStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (location.locationType() == LocationType.URL) {
            try {
                this.grabber = new FFmpegFrameGrabber(location.getURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
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
            do
            {
                try {
                    frame = grabber.grabFrame(doAudio, doVideo, true, false, true);
                } catch (FFmpegFrameGrabber.Exception e) {
                    throw new RuntimeException(e);
                }
            } while (frame != null && grabber.getTimestamp() < expectedVideoTimestamp);

            if (frame != null && doVideo && frame.image != null) {
                BufferedImage image = java2DFrameConverter.convert(frame);

                ByteBuffer byteImage = IOUtil.convertBufferedImageToByteBuffer(image, true);
                width = image.getWidth();
                height = image.getHeight();

                if (textureId == -1) {
                    textureId = glGenTextures();

                    glStateCache.bindTexture(GL_TEXTURE_2D, textureId);
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteImage);
                }

                glStateCache.bindTexture(GL_TEXTURE_2D, textureId);
                glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, byteImage);
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

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
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
