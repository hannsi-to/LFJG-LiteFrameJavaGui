package me.hannsi.lfjg.utils.graphics.video;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.lwjgl.opengl.GL11.*;

/**
 * Class representing frame data, including the frame image, buffered image, and texture ID.
 */
public class FrameData {
    private BufferedImage frameImage;
    private ByteBuffer bufferedImage;
    private int textureId;

    /**
     * Constructs a FrameData instance with the specified buffered image.
     *
     * @param bufferedImage the buffered image
     */
    public FrameData(BufferedImage bufferedImage) {
        this.frameImage = bufferedImage;
        this.bufferedImage = convert(bufferedImage);
    }

    /**
     * Converts a BufferedImage to a ByteBuffer.
     *
     * @param image the BufferedImage to convert
     * @return the ByteBuffer containing the image data
     */
    public ByteBuffer convert(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = convertedImage.createGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();

        byte[] pixelData = ((DataBufferByte) convertedImage.getRaster().getDataBuffer()).getData();
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4).order(ByteOrder.nativeOrder());

        for (int i = 0; i < pixelData.length; i += 4) {
            byte a = pixelData[i];
            byte b = pixelData[i + 1];
            byte g2 = pixelData[i + 2];
            byte r = pixelData[i + 3];

            buffer.put(r);
            buffer.put(g2);
            buffer.put(b);
            buffer.put(a);
        }

        buffer.flip();
        return buffer;
    }

    /**
     * Creates an OpenGL texture from the buffered image.
     */
    public void createTexture() {
        textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, frameImage.getWidth(), frameImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, bufferedImage);

        glBindTexture(GL_TEXTURE_2D, 0);

        bufferedImage.clear();
    }

    /**
     * Gets the frame image.
     *
     * @return the frame image
     */
    public BufferedImage getFrameImage() {
        return frameImage;
    }

    /**
     * Sets the frame image.
     *
     * @param frameImage the new frame image
     */
    public void setFrameImage(BufferedImage frameImage) {
        this.frameImage = frameImage;
    }

    /**
     * Gets the buffered image.
     *
     * @return the buffered image
     */
    public ByteBuffer getBufferedImage() {
        return bufferedImage;
    }

    /**
     * Sets the buffered image.
     *
     * @param bufferedImage the new buffered image
     */
    public void setBufferedImage(ByteBuffer bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    /**
     * Gets the texture ID.
     *
     * @return the texture ID
     */
    public int getTextureId() {
        return textureId;
    }

    /**
     * Sets the texture ID.
     *
     * @param textureId the new texture ID
     */
    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}