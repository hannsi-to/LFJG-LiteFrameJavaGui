package me.hannsi.lfjg.core.utils.graphics.image;

import lombok.Getter;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.render.debug.exceptions.texture.CreatingTextureException;
import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ImageLoaderType;
import me.hannsi.lfjg.core.utils.type.types.LocationType;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * Class for loading textures from various sources.
 */
public class TextureLoader {
    /**
     * -- GETTER --
     * Gets the texture path.
     *
     * @return the texture path
     */
    @Getter
    private final Location texturePath;
    private final ImageLoaderType imageLoaderType;
    /**
     * -- GETTER --
     * Gets the texture ID.
     *
     * @return the texture ID
     */
    @Getter
    private int textureId;

    /**
     * Constructs a TextureLoader instance with the specified texture path and loader type.
     *
     * @param texturePath     the path to the texture resource
     * @param imageLoaderType the type of texture loader to use
     */
    public TextureLoader(Location texturePath, ImageLoaderType imageLoaderType) {
        this.imageLoaderType = imageLoaderType;
        this.texturePath = texturePath;

        loadTexture();
    }

    /**
     * Loads an image using STBImage and returns it as a ByteBuffer.
     *
     * @param location       the location of the resource
     * @param widthBuffer    the buffer to store the image width
     * @param heightBuffer   the buffer to store the image height
     * @param channelsBuffer the buffer to store the number of channels
     * @return the ByteBuffer containing the image data
     */
    public static ByteBuffer loadImageInSTBImage(Location location, IntBuffer widthBuffer, IntBuffer heightBuffer, IntBuffer channelsBuffer) {
        ByteBuffer image;
        ByteBuffer buffer = null;

        try (InputStream inputStream = location.openStream()) {
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + location.path());
            }

            byte[] data = inputStream.readAllBytes();
            buffer = MemoryUtil.memAlloc(data.length);
            buffer.put(data);
            buffer.flip();

            STBImage.stbi_set_flip_vertically_on_load(true);
            image = STBImage.stbi_load_from_memory(buffer, widthBuffer, heightBuffer, channelsBuffer, STBImage.STBI_rgb_alpha);

            if (image == null) {
                throw new RuntimeException("Failed to load image: " + STBImage.stbi_failure_reason());
            }

            if (widthBuffer.get(0) == 0 || heightBuffer.get(0) == 0) {
                STBImage.stbi_image_free(image);
                throw new RuntimeException("Invalid texture dimensions.");
            }

            return image;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource: " + location.path(), e);
        } finally {
            if (buffer != null) {
                MemoryUtil.memFree(buffer);
            }
        }
    }

    /**
     * Cleans up the texture resources.
     */
    public void cleanup() {
        glDeleteTextures(textureId);
    }

    /**
     * Loads the texture based on the specified loader type.
     */
    private void loadTexture() {
        if (texturePath.locationType() == LocationType.FILE || texturePath.locationType() == LocationType.RESOURCE) {
            switch (imageLoaderType) {
                case STB_IMAGE -> {
                    try (MemoryStack stack = MemoryStack.stackPush()) {
                        IntBuffer width = stack.mallocInt(1);
                        IntBuffer height = stack.mallocInt(1);
                        IntBuffer channels = stack.mallocInt(1);

                        ByteBuffer image = STBImage.stbi_load_from_memory(texturePath.getByteBuffer(), width, height, channels, 4);
                        if (image == null) {
                            throw new RuntimeException("Failed to load image: " + texturePath.path());
                        }

                        generateTexture(width.get(0), height.get(0), image);

                        STBImage.stbi_image_free(image);
                    }
                }
                case JAVA_CV -> {
                    Mat bgrMat = opencv_imgcodecs.imdecode(new Mat(texturePath.getBytes()), opencv_imgcodecs.IMREAD_COLOR);

                    if (bgrMat.empty()) {
                        DebugLog.error(getClass(), "Image file [" + texturePath + "] not loaded.");
                        return;
                    }

                    Mat mat = new Mat();
                    cvtColor(bgrMat, mat, opencv_imgproc.COLOR_BGR2RGBA);

                    generateTexture(mat.cols(), mat.rows(), IOUtil.matToByteBufferRGBA(mat));
                }
                default -> throw new IllegalStateException("Unexpected value: " + imageLoaderType);
            }
        } else {
            try {
                URL url = new URL(texturePath.path());

                BufferedImage image = ImageIO.read(url);
                if (image == null) {
                    throw new IOException("Failed to load image: " + texturePath.path());
                }

                int width = image.getWidth();
                int height = image.getHeight();

                BufferedImage rgbaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = rgbaImage.createGraphics();
                g.drawImage(image, 0, 0, null);
                g.dispose();

                int[] pixels = rgbaImage.getRGB(0, 0, width, height, null, 0, width);
                ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);

                for (int pixel : pixels) {
                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) (pixel & 0xFF));
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }

                buffer.flip();

                generateTexture(width, height, buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Generates an OpenGL texture from the given image data.
     *
     * @param width  the width of the image
     * @param height the height of the image
     * @param buf    the ByteBuffer containing the image data
     */
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

    /**
     * Binds the texture for rendering.
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
     * Gets the texture loader type.
     *
     * @return the texture loader type
     */
    public ImageLoaderType getTextureLoaderType() {
        return imageLoaderType;
    }

}