package me.hannsi.lfjg.utils.image;

import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

public class TextureLoader {
    public static int createTexture(ByteBuffer image, int width, int height) {
        int textureId = GL12.glGenTextures();
        GL12.glBindTexture(GL12.GL_TEXTURE_2D, textureId);

        GL12.glPixelStorei(GL12.GL_UNPACK_ALIGNMENT, 1);

        GL12.glTexParameteri(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_FILTER, GL12.GL_NEAREST);
        GL12.glTexParameteri(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAG_FILTER, GL12.GL_NEAREST);

        GL12.glTexImage2D(GL12.GL_TEXTURE_2D, 0, GL12.GL_RGBA, width, height, 0, GL12.GL_RGBA, GL12.GL_UNSIGNED_BYTE, image);

        GL30.glGenerateMipmap(GL12.GL_TEXTURE_2D);

        GL12.glBindTexture(GL12.GL_TEXTURE_2D, 0);

        return textureId;
    }
}
