package me.hannsi.lfjg.render.system.text.msdf;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class MSDFTextureLoader {
    public int textureId;

    private Location textureLocation;

    MSDFTextureLoader() {
        this.textureId = GL11.glGenTextures();
    }

    public static MSDFTextureLoader createMSDFTextureLoader() {
        return new MSDFTextureLoader();
    }

    public void cleanup() {
        GL11.glDeleteTextures(textureId);
        textureId = -1;
    }

    public MSDFTextureLoader textureLocation(Location textureLocation) {
        this.textureLocation = textureLocation;
        return this;
    }

    public MSDFTextureLoader loadTexture() {
        GLStateCache.bindTexture(GL11.GL_TEXTURE_2D, textureId);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer image = STBImage.stbi_load_from_memory(textureLocation.getByteBuffer(), width, height, channels, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + textureLocation.path());
            }

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(0), height.get(0), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
            STBImage.stbi_image_free(image);
        }

        GLStateCache.bindTexture(GL11.GL_TEXTURE_2D, 0);

        return this;
    }
}
