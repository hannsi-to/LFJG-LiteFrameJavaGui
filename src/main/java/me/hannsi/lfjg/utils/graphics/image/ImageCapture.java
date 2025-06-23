package me.hannsi.lfjg.utils.graphics.image;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.utils.math.io.IOUtil;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
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
    @Getter
    private final FileLocation filePath;
    @Getter
    @Setter
    private int width;
    @Getter
    @Setter
    private int height;
    @Getter
    @Setter
    private ImageLoaderType imageLoaderType;
    @Getter
    @Setter
    private ColorFormatType colorFormatType;
    @Getter
    @Setter
    private STBImageFormat stbImageFormat;
    private JavaCVImageFormat javaCVImageFormat;
    @Getter
    @Setter
    private boolean flip;
    @Getter
    @Setter
    private int jpgQuality;
    @Getter
    @Setter
    private String saveType;

    public ImageCapture(FileLocation filePath) {
        this.filePath = filePath;

        this.width = LFJGContext.frameBufferSize.x();
        this.height = LFJGContext.frameBufferSize.y();
        this.imageLoaderType = ImageLoaderType.STB_IMAGE;
        this.javaCVImageFormat = JavaCVImageFormat.PNG;
        this.colorFormatType = ColorFormatType.RGB;
        this.stbImageFormat = STBImageFormat.PNG;
        this.flip = true;
        this.jpgQuality = 90;
    }

    public void saveImage(ByteBuffer buffer, String name) {
        String path = filePath.getPath() + "/" + name + "." + stbImageFormat.getName();
        saveType = "ByteBuffer";

        ByteBuffer flippedBuffer = BufferUtils.createByteBuffer(width * height * colorFormatType.getChannels());
        if (flip) {
            for (int i = 0; i < height; i++) {
                buffer.position((height - i - 1) * width * colorFormatType.getChannels());
                flippedBuffer.put(buffer.slice().limit(width * colorFormatType.getChannels()));
            }
        } else {
            flippedBuffer.put(buffer);
        }

        ByteBuffer convertedBuffer;
        if (colorFormatType == ColorFormatType.BGRA) {
            convertedBuffer = IOUtil.convertBGRAtoRGBA(flippedBuffer, width, height);
        } else {
            convertedBuffer = flippedBuffer;
        }

        LogGenerator logGenerator = null;
        switch (imageLoaderType) {
            case STB_IMAGE -> logGenerator = writeSTBImage(path, convertedBuffer);
            case JAVA_CV -> logGenerator = writeJavaCV(path, convertedBuffer);
        }

        logGenerator.logging(DebugLevel.INFO);
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
        if (flip) {
            for (int i = 0; i < height; i++) {
                buffer.position((height - i - 1) * width * colorFormatType.getChannels());
                flippedBuffer.put(buffer.slice().limit(width * colorFormatType.getChannels()));
            }
        } else {
            flippedBuffer.put(buffer);
        }

        ByteBuffer convertedBuffer;
        if (colorFormatType == ColorFormatType.BGRA) {
            convertedBuffer = IOUtil.convertBGRAtoRGBA(flippedBuffer, width, height);
        } else {
            convertedBuffer = flippedBuffer;
        }

        LogGenerator logGenerator = null;
        switch (imageLoaderType) {
            case STB_IMAGE -> logGenerator = writeSTBImage(path, convertedBuffer);
            case JAVA_CV -> logGenerator = writeJavaCV(path, convertedBuffer);
        }

        logGenerator.logging(DebugLevel.INFO);
    }

    public void saveImage(String name) {
        String path = filePath.getPath() + "/" + name + "." + stbImageFormat.getName();
        saveType = "ScreenShot";

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * colorFormatType.getChannels());
        glReadPixels(0, 0, width, height, colorFormatType.getId(), GL_UNSIGNED_BYTE, buffer);

        ByteBuffer flippedBuffer = BufferUtils.createByteBuffer(width * height * colorFormatType.getChannels());
        if (flip) {
            for (int i = 0; i < height; i++) {
                buffer.position((height - i - 1) * width * colorFormatType.getChannels());
                flippedBuffer.put(buffer.slice().limit(width * colorFormatType.getChannels()));
            }
        } else {
            flippedBuffer.put(buffer);
        }

        ByteBuffer convertedBuffer;
        if (colorFormatType == ColorFormatType.BGRA) {
            convertedBuffer = IOUtil.convertBGRAtoRGBA(flippedBuffer, width, height);
        } else {
            convertedBuffer = flippedBuffer;
        }

        LogGenerator logGenerator = null;
        switch (imageLoaderType) {
            case STB_IMAGE -> logGenerator = writeSTBImage(path, convertedBuffer);
            case JAVA_CV -> logGenerator = writeJavaCV(path, convertedBuffer);
        }

        logGenerator.logging(DebugLevel.INFO);
    }

    private LogGenerator writeJavaCV(String path, ByteBuffer imageBuffer) {
        Mat mat = new Mat(height, width, opencv_core.CV_8UC(colorFormatType.getChannels()));
        imageBuffer.flip();
        mat.data().put(imageBuffer.slice().get());

        boolean success = false;

        switch (javaCVImageFormat) {
            case PNG, BMP, TGA, PBM, PGM, PPM, GIF -> {
                success = opencv_imgcodecs.imwrite(path, mat);
            }
            case JPG, JPEG -> {
                int[] params = new int[]{opencv_imgcodecs.IMWRITE_JPEG_QUALITY, jpgQuality};
                success = opencv_imgcodecs.imwrite(path, mat, params);
            }
        }

        if (!success) {
            throw new RuntimeException("Failed to save screenshot to image: " + path);
        }

        return new LogGenerator("ImageCapture Info Message", "Source: ImageCapture", "Type: Save image", "Severity: Info", "Message: Saved " + saveType + " to image.", "Path: " + path);
    }

    private LogGenerator writeSTBImage(String path, ByteBuffer imageBuffer) {
        imageBuffer.rewind();

        boolean success = switch (stbImageFormat) {
            case PNG ->
                    STBImageWrite.stbi_write_png(path, width, height, colorFormatType.getChannels(), imageBuffer, width * colorFormatType.getChannels());

            case JPG -> {
                if (colorFormatType.getChannels() != 3) {
                    throw new IllegalArgumentException("JPEG format only supports 3 (RGB) channels.");
                }
                yield STBImageWrite.stbi_write_jpg(path, width, height, 3, imageBuffer, jpgQuality);
            }

            case BMP -> STBImageWrite.stbi_write_bmp(path, width, height,
                    colorFormatType.getChannels(), imageBuffer);

            case TGA -> STBImageWrite.stbi_write_tga(path, width, height,
                    colorFormatType.getChannels(), imageBuffer);
        };

        if (!success) {
            throw new RuntimeException("Failed to save screenshot to image: " + path);
        }

        return new LogGenerator(
                "ImageCapture Info Message",
                "Source: ImageCapture",
                "Type: Save image",
                "Severity: Info",
                "Message: Saved " + stbImageFormat + " to image.",
                "Path: " + path
        );
    }

    public void cleanup() {
        filePath.cleanup();
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
