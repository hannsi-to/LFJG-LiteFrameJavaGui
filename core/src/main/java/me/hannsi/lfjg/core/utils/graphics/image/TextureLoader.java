package me.hannsi.lfjg.core.utils.graphics.image;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ImageLoaderType;
import me.hannsi.lfjg.core.utils.type.types.LocationType;
import org.lwjgl.system.MemoryStack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static me.hannsi.lfjg.core.Core.CreatingTextureException;
import static me.hannsi.lfjg.core.Core.GL11.*;
import static me.hannsi.lfjg.core.Core.GL30.glGenerateMipmap;
import static me.hannsi.lfjg.core.Core.LFJGRenderContext.bindTexture;
import static me.hannsi.lfjg.core.Core.OPEN_GL_PARAMETER_NAME_MAP;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

public class TextureLoader {
    private final Location texturePath;
    private final ImageLoaderType imageLoaderType;
    private int textureId;

    public TextureLoader(Location texturePath, ImageLoaderType imageLoaderType) {
        this.imageLoaderType = imageLoaderType;
        this.texturePath = texturePath;

        loadTexture();
    }

    public void cleanup() {
        glDeleteTextures(textureId);
    }

    private void loadTexture() {
        if (texturePath.locationType() == LocationType.FILE || texturePath.locationType() == LocationType.RESOURCE) {
            switch (imageLoaderType) {
                case STB_IMAGE:
                    try (MemoryStack stack = MemoryStack.stackPush()) {
                        IntBuffer width = stack.mallocInt(1);
                        IntBuffer height = stack.mallocInt(1);
                        IntBuffer channels = stack.mallocInt(1);

                        ByteBuffer image = stbi_load_from_memory(texturePath.getByteBuffer(), width, height, channels, 4);
                        if (image == null) {
                            throw new RuntimeException("Failed to load image: " + texturePath.path());
                        }

                        generateTexture(width.get(0), height.get(0), image);

                        stbi_image_free(image);
                    }
                    break;
                case JAVA_CV://                    Mat bgrMat = opencv_imgcodecs.imdecode(new Mat(texturePath.getBytes()), opencv_imgcodecs.IMREAD_COLOR);
//
//                    if (bgrMat.empty()) {
//                        DebugLog.error(getClass(), "Image file [" + texturePath + "] not loaded.");
//                        return;
//                    }
//
//                    Mat mat = new Mat();
//                    cvtColor(bgrMat, mat, opencv_imgproc.COLOR_BGR2RGBA);
//
//                    generateTexture(mat.cols(), mat.rows(), IOUtil.matToByteBufferRGBA(mat));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + imageLoaderType);
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

    private void generateTexture(int width, int height, ByteBuffer buf) {
        textureId = glGenTextures();
        if (textureId == 0) {
            throw CreatingTextureException.createCreatingTextureException("Could not create texture");
        }

        bindTexture(OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_2D"), textureId);
        glPixelStorei(OPEN_GL_PARAMETER_NAME_MAP.get("GL_UNPACK_ALIGNMENT"), 1);

        glTexImage2D(OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_2D"), 0, OPEN_GL_PARAMETER_NAME_MAP.get("GL_RGBA"), width, height, 0, OPEN_GL_PARAMETER_NAME_MAP.get("GL_RGBA"), OPEN_GL_PARAMETER_NAME_MAP.get("GL_UNSIGNED_BYTE"), buf);

        glTexParameteri(OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_2D"), OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_WRAP_S"), OPEN_GL_PARAMETER_NAME_MAP.get("GL_CLAMP_TO_EDGE"));
        glTexParameteri(OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_2D"), OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_WRAP_T"), OPEN_GL_PARAMETER_NAME_MAP.get("GL_CLAMP_TO_EDGE"));
        glTexParameteri(OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_2D"), OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_MIN_FILTER"), OPEN_GL_PARAMETER_NAME_MAP.get("GL_NEAREST"));
        glTexParameteri(OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_2D"), OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_MAG_FILTER"), OPEN_GL_PARAMETER_NAME_MAP.get("GL_NEAREST"));
        glGenerateMipmap(OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_2D"));

        bindTexture(OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_2D"), 0);
    }

    public void bind() {
        bindTexture(OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_2D"), textureId);
    }

    public void unbind() {
        bindTexture(OPEN_GL_PARAMETER_NAME_MAP.get("GL_TEXTURE_2D"), 0);
    }

    public ImageLoaderType getTextureLoaderType() {
        return imageLoaderType;
    }

    public Location getTexturePath() {
        return texturePath;
    }

    public ImageLoaderType getImageLoaderType() {
        return imageLoaderType;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}