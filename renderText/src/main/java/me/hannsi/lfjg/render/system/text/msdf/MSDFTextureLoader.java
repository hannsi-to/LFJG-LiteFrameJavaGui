package me.hannsi.lfjg.render.system.text.msdf;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
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
        this.textureId = Core.GL11.glGenTextures();
    }

    public static MSDFTextureLoader createMSDFTextureLoader() {
        return new MSDFTextureLoader();
    }

    public void cleanup() {
        Core.GL11.glDeleteTextures(textureId);
        textureId = -1;
    }

    public MSDFTextureLoader textureLocation(Location textureLocation) {
        this.textureLocation = textureLocation;
        return this;
    }

    public MSDFTextureLoader loadTexture() {
        Core.GL11.glBindTexture(Core.GL11.GL_TEXTURE_2D, textureId);

        Core.GL11.glTexParameteri(Core.GL11.GL_TEXTURE_2D, Core.GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        Core.GL11.glTexParameteri(Core.GL11.GL_TEXTURE_2D, Core.GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        Core.GL11.glTexParameteri(Core.GL11.GL_TEXTURE_2D, Core.GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        Core.GL11.glTexParameteri(Core.GL11.GL_TEXTURE_2D, Core.GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer image = STBImage.stbi_load_from_memory(textureLocation.getByteBuffer(), width, height, channels, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + textureLocation.path());
            }

            Core.GL11.glTexImage2D(Core.GL11.GL_TEXTURE_2D, 0, Core.GL11.GL_RGBA, width.get(0), height.get(0), 0, Core.GL11.GL_RGBA, Core.GL11.GL_UNSIGNED_BYTE, image);
            STBImage.stbi_image_free(image);
        }

        Core.GL11.glBindTexture(Core.GL11.GL_TEXTURE_2D, 0);

        return this;
    }
}
