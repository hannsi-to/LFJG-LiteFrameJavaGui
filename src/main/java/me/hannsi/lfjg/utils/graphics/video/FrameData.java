package me.hannsi.lfjg.utils.graphics.video;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class FrameData {
    private BufferedImage frameImage;
    private ByteBuffer bufferedImage;
    private int textureId;

    public FrameData(BufferedImage bufferedImage) {
        this.frameImage = bufferedImage;
        this.bufferedImage = convert(bufferedImage);
    }

    public ByteBuffer convert(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = convertedImage.createGraphics();
        g.drawImage(image, 0, 0, frameImage.getWidth(), frameImage.getHeight(), null);
        g.dispose();

        byte[] pixelData = ((DataBufferByte) convertedImage.getRaster().getDataBuffer()).getData();
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);

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

    public BufferedImage getFrameImage() {
        return frameImage;
    }

    public void setFrameImage(BufferedImage frameImage) {
        this.frameImage = frameImage;
    }

    public ByteBuffer getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(ByteBuffer bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}
