package me.hannsi.lfjg.render.system.text.msdf;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static me.hannsi.lfjg.core.Core.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class MSDFTextureLoader {
    public int textureId;

    private Location textureLocation;

    MSDFTextureLoader() {
        this.textureId = glGenTextures();
    }

    public static MSDFTextureLoader createMSDFTextureLoader() {
        return new MSDFTextureLoader();
    }

    public MSDFTextureLoader textureLocation(Location textureLocation) {
        this.textureLocation = textureLocation;
        return this;
    }

    public MSDFTextureLoader loadTexture() {
        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer image = STBImage.stbi_load_from_memory(textureLocation.getByteBuffer(), width, height, channels, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + textureLocation.path());
            }

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            STBImage.stbi_image_free(image);
        }

        glBindTexture(GL_TEXTURE_2D, 0);

        return this;
    }
}
