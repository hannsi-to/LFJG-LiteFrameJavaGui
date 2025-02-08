package me.hannsi.lfjg.render.openGL.system.model.texture;

import me.hannsi.lfjg.utils.reflection.FileLocation;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

/**
 * Represents a texture model in the OpenGL rendering system.
 */
public class TextureModel {
    private final FileLocation texturePath;
    private int textureId;

    /**
     * Constructs a new TextureModel with the specified width, height, and buffer.
     *
     * @param width the width of the texture
     * @param height the height of the texture
     * @param buf the buffer containing the texture data
     */
    public TextureModel(int width, int height, ByteBuffer buf) {
        this.texturePath = null;
        generateTexture(width, height, buf);
    }

    /**
     * Constructs a new TextureModel with the specified texture path.
     *
     * @param texturePath the file location of the texture
     */
    public TextureModel(FileLocation texturePath) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            this.texturePath = texturePath;
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer buf = stbi_load(texturePath.getPath(), w, h, channels, 4);
            if (buf == null) {
                throw new RuntimeException("Image file [" + texturePath + "] not loaded: " + stbi_failure_reason());
            }

            int width = w.get();
            int height = h.get();

            generateTexture(width, height, buf);

            stbi_image_free(buf);
        }
    }

    /**
     * Binds the texture.
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    /**
     * Unbinds the texture.
     */
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Cleans up the texture by deleting it.
     */
    public void cleanup() {
        glDeleteTextures(textureId);
    }

    /**
     * Generates the texture with the specified width, height, and buffer.
     *
     * @param width the width of the texture
     * @param height the height of the texture
     * @param buf the buffer containing the texture data
     */
    private void generateTexture(int width, int height, ByteBuffer buf) {
        textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    /**
     * Gets the file location of the texture.
     *
     * @return the file location of the texture
     */
    public FileLocation getTexturePath() {
        return texturePath;
    }
}