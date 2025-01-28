package me.hannsi.lfjg.utils.graphics.video;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class VideoFrameData {
    private BufferedImage frameImage;
    private int textureId;

    public VideoFrameData(BufferedImage frameImage) {
        this.frameImage = frameImage;
        this.textureId = -1;
    }

    public static ByteBuffer convert(BufferedImage image) {
        BufferedImage rgbaImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        rgbaImage.getGraphics().drawImage(image, 0, 0, null);

        DataBufferByte dataBuffer = (DataBufferByte) rgbaImage.getRaster().getDataBuffer();
        byte[] pixels = dataBuffer.getData();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(pixels.length);
        byteBuffer.put(pixels);
        byteBuffer.flip();

        return byteBuffer;
    }

    public void createTexture() {
        if (frameImage == null) {
            this.textureId = 0;
            return;
        }

        ByteBuffer buffer = convert(frameImage);

        textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA4, frameImage.getWidth(), frameImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glBindTexture(GL_TEXTURE_2D, 0);

        buffer.clear();
    }

    public BufferedImage getFrameImage() {
        return frameImage;
    }

    public void setFrameImage(BufferedImage frameImage) {
        this.frameImage = frameImage;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}
