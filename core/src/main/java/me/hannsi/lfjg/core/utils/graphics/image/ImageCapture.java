package me.hannsi.lfjg.core.utils.graphics.image;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ColorFormatType;
import me.hannsi.lfjg.core.utils.type.types.ImageLoaderType;
import me.hannsi.lfjg.core.utils.type.types.JavaCVImageFormat;
import me.hannsi.lfjg.core.utils.type.types.STBImageFormat;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImageWrite;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.core.Core.frameBufferSize;

public class ImageCapture {
    private final Location filePath;

    private int width;
    private int height;
    private ImageLoaderType imageLoaderType;
    private ColorFormatType colorFormatType;
    private STBImageFormat stbImageFormat;
    private JavaCVImageFormat javaCVImageFormat;
    private boolean flip;
    private int jpgQuality;
    private String saveType;

    public ImageCapture(Location filePath) {
        this.filePath = filePath;

        this.width = frameBufferSize.x();
        this.height = frameBufferSize.y();
        this.imageLoaderType = ImageLoaderType.STB_IMAGE;
        this.javaCVImageFormat = JavaCVImageFormat.PNG;
        this.colorFormatType = ColorFormatType.RGB;
        this.stbImageFormat = STBImageFormat.PNG;
        this.flip = true;
        this.jpgQuality = 90;
    }

    public void saveImage(ByteBuffer buffer, String name) {
        String path = filePath.path() + "/" + name + "." + stbImageFormat.getName();
        saveType = "ByteBuffer";

        ByteBuffer flippedBuffer = BufferUtils.createByteBuffer(width * height * colorFormatType.getChannels());
        if (flip) {
            for (int i = 0; i < height; i++) {
                buffer.position((height - i - 1) * width * colorFormatType.getChannels());
                flippedBuffer.put((ByteBuffer) buffer.slice().limit(width * colorFormatType.getChannels()));
            }
        } else {
            flippedBuffer.put(buffer);
        }

        LogGenerator logGenerator = null;
        switch (imageLoaderType) {
            case STB_IMAGE:
                logGenerator = writeSTBImage(path, flippedBuffer);
                break;
            case JAVA_CV:
                logGenerator = writeJavaCV(path, flippedBuffer);
                break;
        }

        logGenerator.logging(DebugLevel.INFO);
    }

    private LogGenerator writeJavaCV(String path, ByteBuffer imageBuffer) {
//        Mat mat = new Mat(height, width, opencv_core.CV_8UC(colorFormatType.getChannels()));
//        imageBuffer.flip();
//        mat.data().put(imageBuffer.slice().get());
//
//        boolean success = false;
//
//        switch (javaCVImageFormat) {
//            case PNG, BMP, TGA, PBM, PGM, PPM, GIF -> {
//                success = opencv_imgcodecs.imwrite(path, mat);
//            }
//            case JPG, JPEG -> {
//                int[] params = new int[]{opencv_imgcodecs.IMWRITE_JPEG_QUALITY, jpgQuality};
//                success = opencv_imgcodecs.imwrite(path, mat, params);
//            }
//        }
//
//        if (!success) {
//            throw new RuntimeException("Failed to save screenshot to image: " + path);
//        }

        return new LogGenerator("ImageCapture Info Message", "Source: ImageCapture", "Type: Save image", "Severity: Info", "Message: Saved " + saveType + " to image.", "Path: " + path);
    }

    private LogGenerator writeSTBImage(String path, ByteBuffer imageBuffer) {
        imageBuffer.rewind();

        boolean success;
        switch (stbImageFormat) {
            case PNG:
                success = STBImageWrite.stbi_write_png(
                        path,
                        width,
                        height,
                        colorFormatType.getChannels(),
                        imageBuffer,
                        width * colorFormatType.getChannels()
                );
                break;

            case JPG:
                if (colorFormatType.getChannels() != 3) {
                    throw new IllegalArgumentException("JPEG format only supports 3 (RGB) channels.");
                }
                success = STBImageWrite.stbi_write_jpg(
                        path,
                        width,
                        height,
                        3,
                        imageBuffer,
                        jpgQuality
                );
                break;

            case BMP:
                success = STBImageWrite.stbi_write_bmp(
                        path,
                        width,
                        height,
                        colorFormatType.getChannels(),
                        imageBuffer
                );
                break;

            case TGA:
                success = STBImageWrite.stbi_write_tga(
                        path,
                        width,
                        height,
                        colorFormatType.getChannels(),
                        imageBuffer
                );
                break;

            default:
                throw new IllegalArgumentException();
        }

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
    }

    @Deprecated
    public JavaCVImageFormat getJavaCVImageFormat() {
        return javaCVImageFormat;
    }

    @Deprecated
    public void setJavaCVImageFormat(JavaCVImageFormat javaCVImageFormat) {
        this.javaCVImageFormat = javaCVImageFormat;
    }

    public Location getFilePath() {
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

    public ImageLoaderType getImageLoaderType() {
        return imageLoaderType;
    }

    public void setImageLoaderType(ImageLoaderType imageLoaderType) {
        this.imageLoaderType = imageLoaderType;
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

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public int getJpgQuality() {
        return jpgQuality;
    }

    public void setJpgQuality(int jpgQuality) {
        this.jpgQuality = jpgQuality;
    }

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }
}
