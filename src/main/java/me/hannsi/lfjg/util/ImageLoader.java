package me.hannsi.lfjg.util;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ImageLoader {
    public static ByteBuffer loadImage(ResourcesLocation resourcesLocation, IntBuffer widthBuffer, IntBuffer heightBuffer, IntBuffer channelsBuffer) {
        ByteBuffer image;
        try (InputStream inputStream = resourcesLocation.getInputStream()) {
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + resourcesLocation.getPath());
            }

            byte[] data = inputStream.readAllBytes();
            ByteBuffer buffer = MemoryUtil.memAlloc(data.length);
            buffer.put(data);
            buffer.flip();

            STBImage.stbi_set_flip_vertically_on_load(true);
            image = STBImage.stbi_load_from_memory(buffer, widthBuffer, heightBuffer, channelsBuffer, STBImage.STBI_rgb_alpha);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + STBImage.stbi_failure_reason());
            }
            if (widthBuffer.get(0) == 0 || heightBuffer.get(0) == 0) {
                throw new RuntimeException("Invalid texture dimensions.");
            }
            return image;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource: " + resourcesLocation.getPath(), e);
        }
    }
}
