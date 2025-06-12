package me.hannsi.lfjg.render.system.video;

import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.toolkit.IOUtil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class VideoFrameSystem {
    private Location location;
    private Frame frame;
    private FFmpegFrameGrabber grabber;
    private Java2DFrameConverter java2DFrameConverter;
    private VideoCache videoCache;
    private int textureId = -1;
    private int width;
    private int height;

    VideoFrameSystem() {
    }

    public static VideoFrameSystem initVideoFrameSystem() {
        return new VideoFrameSystem();
    }

    public VideoFrameSystem createFFmpegFrameGrabber(Location location) {
        this.location = location;
        if (location.isPath()) {
            this.grabber = new FFmpegFrameGrabber(((FileLocation) location).getInputStream());
        } else if (location.isUrl()) {

        }

        return this;
    }

    public VideoFrameSystem createJava2DFrameConverter() {
        this.java2DFrameConverter = new Java2DFrameConverter();
        return this;
    }

    public VideoFrameSystem createVideoCache() {
        this.videoCache = VideoCache.initVideoCache();
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

    public void drawFrame() {
        try {
            if (videoCache.checkUpdate()) {
                frame = grabber.grabFrame(true, true, true, false, true);
                if (frame != null) {
                    BufferedImage image = java2DFrameConverter.convert(frame);
                    if (image != null) {
                        videoCache.addFrame(image);
                    }
                }
            }
        } catch (FFmpegFrameGrabber.Exception e) {
            throw new RuntimeException(e);
        }

        BufferedImage image = videoCache.getFrame();
        if (image != null) {
            ByteBuffer byteImage = IOUtil.convertBufferedImageToByteBuffer(image, BufferedImage.TYPE_4BYTE_ABGR);
            width = image.getWidth();
            height = image.getHeight();

            if (textureId == -1) {
                textureId = glGenTextures();

                glBindTexture(GL_TEXTURE_2D, textureId);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteImage);
                glBindTexture(GL_TEXTURE_2D, 0);
            }

            glBindTexture(GL_TEXTURE_2D, textureId);
            glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, byteImage);
            glBindTexture(GL_TEXTURE_2D, 0);
        }

        if (textureId == -1) {
            return;
        }
        glBindTexture(GL_TEXTURE_2D, textureId);
        glEnable(GL_TEXTURE_2D);

        glBegin(GL_QUADS);
        glVertex2f(0, 0);
        glTexCoord2i(1, 1);
        glVertex2f(width, 0);
        glTexCoord2i(1, 0);
        glVertex2f(width, height);
        glTexCoord2i(0, 0);
        glVertex2f(0, height);
        glTexCoord2i(0, 1);
        glEnd();

        glBindTexture(GL_TEXTURE_2D, 0);
        glDisable(GL_TEXTURE_2D);
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

    public VideoCache getVideoCache() {
        return videoCache;
    }

    public void setVideoCache(VideoCache videoCache) {
        this.videoCache = videoCache;
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
}
