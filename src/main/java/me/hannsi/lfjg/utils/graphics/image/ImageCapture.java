package me.hannsi.lfjg.utils.graphics.image;

import me.hannsi.lfjg.debug.debug.system.DebugLevel;
import me.hannsi.lfjg.debug.debug.log.LogGenerator;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.type.types.ColorFormatType;
import me.hannsi.lfjg.utils.type.types.ImageLoaderType;
import me.hannsi.lfjg.utils.type.types.JavaCVImageFormat;
import me.hannsi.lfjg.utils.type.types.STBImageFormat;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImageWrite;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glReadPixels;

public class ImageCapture {
    private final FileLocation filePath;
    private int width;
    private int height;
    private ImageLoaderType imageLoaderType;
    private ColorFormatType colorFormatType;
    private STBImageFormat stbImageFormat;
    private JavaCVImageFormat javaCVImageFormat;
    private int jpgQuality;
    private String saveType;

    public ImageCapture(FileLocation filePath) {
        this.filePath = filePath;

        this.width = (int) LFJGContext.resolution.x();
        this.height = (int) LFJGContext.resolution.y();
        this.imageLoaderType = ImageLoaderType.STBImage;
        this.javaCVImageFormat = JavaCVImageFormat.png;
        this.colorFormatType = ColorFormatType.RGB;
        this.stbImageFormat = STBImageFormat.png;
        this.jpgQuality = 90;
    }

    public void saveImage(FrameBuffer frameBuffer, String name) {
        String path = filePath.getPath() + "/" + name + "." + stbImageFormat.getName();
        saveType = "FrameBuffer";

        int x = (int) frameBuffer.getX();
        int y = (int) frameBuffer.getY();
        this.width = (int) frameBuffer.getWidth();
        this.height = (int) frameBuffer.getHeight();

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * colorFormatType.getChannels());
        frameBuffer.bindFrameBufferNoClear();
        glReadPixels(x, y, width, height, colorFormatType.getId(), GL_UNSIGNED_BYTE, buffer);
        frameBuffer.unbindFrameBuffer();

        ByteBuffer flippedBuffer = BufferUtils.createByteBuffer(width * height * colorFormatType.getChannels());
        for (int i = 0; i < height; i++) {
            buffer.position((height - i - 1) * width * colorFormatType.getChannels());
            flippedBuffer.put(buffer.slice().limit(width * colorFormatType.getChannels()));
        }

        switch (imageLoaderType) {
            case STBImage -> {
                writeSTBImage(path, flippedBuffer);
            }
            case JavaCV -> {
                writeJavaCV(path, flippedBuffer, colorFormatType.getChannels());
            }
        }
    }

    public void saveImage(String name) {
        String path = filePath.getPath() + "/" + name + "." + stbImageFormat.getName();
        saveType = "ScreenShot";

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * colorFormatType.getChannels());
        glReadPixels(0, 0, width, height, colorFormatType.getId(), GL_UNSIGNED_BYTE, buffer);

        ByteBuffer flippedBuffer = BufferUtils.createByteBuffer(width * height * colorFormatType.getChannels());
        for (int y = 0; y < height; y++) {
            buffer.position((height - y - 1) * width * colorFormatType.getChannels());
            flippedBuffer.put(buffer.slice().limit(width * colorFormatType.getChannels()));
        }

        LogGenerator logGenerator = null;
        switch (imageLoaderType) {
            case STBImage -> {
                logGenerator = writeSTBImage(path, flippedBuffer);
            }
            case JavaCV -> {
                logGenerator = writeJavaCV(path, flippedBuffer, colorFormatType.getChannels());
            }
        }

        logGenerator.logging(DebugLevel.INFO);
    }

    private LogGenerator writeJavaCV(String path, ByteBuffer flippedBuffer, int channels) {
        Mat mat = new Mat(height, width, opencv_core.CV_8UC(channels));
        flippedBuffer.flip();
        mat.data().put(flippedBuffer.slice().get());

        boolean success = false;

        switch (javaCVImageFormat) {
            case png, bmp, tga, pbm, pgm, ppm, gif -> {
                success = opencv_imgcodecs.imwrite(path, mat);
            }
            case jpg, jpeg -> {
                int[] params = new int[]{opencv_imgcodecs.IMWRITE_JPEG_QUALITY, jpgQuality};
                success = opencv_imgcodecs.imwrite(path, mat, params);
            }
        }

        if (!success) {
            throw new RuntimeException("Failed to save screenshot to image: " + path);
        }

        return new LogGenerator("ImageCapture Info Message", "Source: ImageCapture", "Type: Save image", "Severity: Info", "Message: Saved " + saveType + " to image.", "Path: " + path);
    }

    private LogGenerator writeSTBImage(String path, ByteBuffer flippedBuffer) {
        flippedBuffer.flip();

        boolean success = switch (stbImageFormat) {
            case png ->
                    STBImageWrite.stbi_write_png(path, width, height, colorFormatType.getChannels(), flippedBuffer, width * colorFormatType.getChannels());
            case jpg ->
                    STBImageWrite.stbi_write_jpg(path, width, height, colorFormatType.getChannels(), flippedBuffer, jpgQuality);
            case bmp -> STBImageWrite.stbi_write_bmp(path, width, height, colorFormatType.getChannels(), flippedBuffer);
            case tga -> STBImageWrite.stbi_write_tga(path, width, height, colorFormatType.getChannels(), flippedBuffer);
        };

        if (!success) {
            throw new RuntimeException("Failed to save screenshot to image: " + path);
        }

        return new LogGenerator("ImageCapture Info Message", "Source: ImageCapture", "Type: Save image", "Severity: Info", "Message: Saved " + saveType + " to image.", "Path: " + path);
    }

    public void cleanup() {
        filePath.cleanup();
    }

    public FileLocation getFilePath() {
        return filePath;
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

    public ColorFormatType getColorFormatType() {
        return colorFormatType;
    }

    public void setColorFormatType(ColorFormatType colorFormatType) {
        this.colorFormatType = colorFormatType;
    }

    public STBImageFormat getStbImageFormat() {
        return stbImageFormat;
    }

    public void setStbImageFormat(STBImageFormat stbImageFormat) {
        this.stbImageFormat = stbImageFormat;
    }

    public int getJpgQuality() {
        return jpgQuality;
    }

    public void setJpgQuality(int jpgQuality) {
        this.jpgQuality = jpgQuality;
    }

    public ImageLoaderType getImageLoaderType() {
        return imageLoaderType;
    }

    public void setImageLoaderType(ImageLoaderType imageLoaderType) {
        this.imageLoaderType = imageLoaderType;
    }

    @Deprecated
    public JavaCVImageFormat getJavaCVImageFormat() {
        return javaCVImageFormat;
    }

    @Deprecated
    public void setJavaCVImageFormat(JavaCVImageFormat javaCVImageFormat) {
        this.javaCVImageFormat = javaCVImageFormat;
    }
}
