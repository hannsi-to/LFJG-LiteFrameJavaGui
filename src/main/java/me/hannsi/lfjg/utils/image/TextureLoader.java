package me.hannsi.lfjg.utils.image;

import org.lwjgl.opengl.GL12;

import java.nio.ByteBuffer;

public class TextureLoader {
    public static int createTexture(ByteBuffer image, int width, int height) {
        int textureId = GL12.glGenTextures();
        GL12.glBindTexture(GL12.GL_TEXTURE_2D, textureId);

        GL12.glPixelStorei(GL12.GL_UNPACK_ALIGNMENT, 1);

        GL12.glTexImage2D(GL12.GL_TEXTURE_2D, 0, GL12.GL_RGBA, width, height, 0, GL12.GL_RGBA, GL12.GL_UNSIGNED_BYTE, image);

        int error = GL12.glGetError();
        if (error != GL12.GL_NO_ERROR) {
            throw new RuntimeException("OpenGL Error: " + error);
        }

        GL12.glTexParameteri(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL12.glTexParameteri(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        GL12.glTexParameteri(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_FILTER, GL12.GL_LINEAR);
        GL12.glTexParameteri(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAG_FILTER, GL12.GL_LINEAR);

        GL12.glBindTexture(GL12.GL_TEXTURE_2D, 0);

        return textureId;
    }
}
