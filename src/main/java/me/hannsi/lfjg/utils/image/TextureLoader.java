package me.hannsi.lfjg.utils.image;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.debug.exceptions.texture.CreatingTextureException;
import me.hannsi.lfjg.utils.buffer.BufferUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.TextureLoaderType;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;

public class TextureLoader {
    private final ResourcesLocation texturePath;
    private final TextureLoaderType textureLoaderType;
    private int textureId;

    public TextureLoader(ResourcesLocation texturePath, TextureLoaderType textureLoaderType) {
        this.textureLoaderType = textureLoaderType;
        this.texturePath = texturePath;

        loadTexture();
    }

    public static ByteBuffer loadImageInSTBImage(ResourcesLocation resourcesLocation, IntBuffer widthBuffer, IntBuffer heightBuffer, IntBuffer channelsBuffer) {
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

    private void loadTexture() {
        switch (textureLoaderType) {
            case STBImage -> {
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    IntBuffer w = stack.mallocInt(1);
                    IntBuffer h = stack.mallocInt(1);
                    IntBuffer channels = stack.mallocInt(1);

                    ByteBuffer buf = loadImageInSTBImage(texturePath, w, h, channels);

                    int width = w.get();
                    int height = h.get();

                    generateTexture(width, height, buf);

                    stbi_image_free(Objects.requireNonNull(buf));
                }
            }
            case JavaCV -> {
                Mat bgrMat = opencv_imgcodecs.imdecode(new Mat(this.texturePath.getBytes()), opencv_imgcodecs.IMREAD_COLOR);

                if (bgrMat.empty()) {
                    DebugLog.error(getClass(), "Image file [" + texturePath + "] not loaded.");
                    return;
                }

                Mat mat = new Mat();
                cvtColor(bgrMat, mat, opencv_imgproc.COLOR_BGR2RGBA);

                generateTexture(mat.cols(), mat.rows(), BufferUtil.matToByteBufferRGBA(mat));
            }
            default -> throw new IllegalStateException("Unexpected value: " + textureLoaderType);
        }
    }

    private void generateTexture(int width, int height, ByteBuffer buf) {
        textureId = glGenTextures();
        if (textureId == 0) {
            throw new CreatingTextureException("Could not create texture");
        }

        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glGenerateMipmap(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void cleanup() {
        glDeleteTextures(textureId);
    }

    public ResourcesLocation getTexturePath() {
        return texturePath;
    }

    public TextureLoaderType getTextureLoaderType() {
        return textureLoaderType;
    }

    public int getTextureId() {
        return textureId;
    }
}
