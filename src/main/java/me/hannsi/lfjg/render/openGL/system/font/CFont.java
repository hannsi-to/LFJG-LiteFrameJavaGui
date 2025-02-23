package me.hannsi.lfjg.render.openGL.system.font;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.hannsi.lfjg.utils.math.MathHelper.max;
import static me.hannsi.lfjg.utils.math.MathHelper.sqrt;
import static org.lwjgl.opengl.GL11.*;

/**
 * Represents a font used in OpenGL rendering.
 */
public class CFont {
    public static final List<Integer> unicodeRanges = new ArrayList<>();
    public int textureId;
    private FileLocation filepath;
    private Font font;
    private int fontSize;
    private int width;
    private int height;
    private int lineHeight;
    private Map<Integer, CharInfo> characterMap;

    /**
     * Constructs a new CFont with the specified file path and font size.
     *
     * @param filepath the file path of the font
     * @param fontSize the size of the font
     */
    public CFont(FileLocation filepath, int fontSize) {
        addUnicodeRange(UnicodeRange.BASIC_LATIN_START, UnicodeRange.BASIC_LATIN_END);

        this.filepath = filepath;
        this.fontSize = fontSize;
        this.characterMap = new HashMap<>();
        generateBitmap();
    }

    /**
     * Constructs a new CFont with the specified resource location and font size.
     *
     * @param filepath the resource location of the font
     * @param fontSize the size of the font
     */
    public CFont(ResourcesLocation filepath, int fontSize) {
        this((FileLocation) filepath, fontSize);
    }

    /**
     * Adds a range of Unicode characters to be included in the font.
     *
     * @param start the start of the Unicode range
     * @param end   the end of the Unicode range
     */
    public static void addUnicodeRange(int start, int end) {
        unicodeRanges.add(start);
        unicodeRanges.add(end);
    }

    /**
     * Cleans up the resources used by the font.
     */
    public void cleanup() {
        filepath.cleanup();
        font = null;
        characterMap.clear();
    }

    /**
     * Gets the height of the font.
     *
     * @return the height of the font
     */
    public int getFontHeight() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        graphics.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics();

        int fontHeight = metrics.getHeight();

        graphics.dispose();

        return fontHeight;
    }

    /**
     * Gets the width of the specified text.
     *
     * @param text the text to measure
     * @return the width of the text
     */
    public int getFontWidth(String text) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        graphics.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics();

        int fontWidth = metrics.stringWidth(text);

        graphics.dispose();

        return fontWidth;
    }

    /**
     * Gets the character information for the specified codepoint.
     *
     * @param codepoint the Unicode codepoint of the character
     * @return the character information
     */
    public CharInfo getCharacter(int codepoint) {
        return characterMap.getOrDefault(codepoint, new CharInfo(0, 0, 0, 0));
    }

    /**
     * Registers the font from the specified file location.
     *
     * @param fontFile the file location of the font
     * @return the registered font
     */
    private Font registerFont(FileLocation fontFile) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile.getInputStream());
            ge.registerFont(font);
            return font;
        } catch (Exception e) {
            DebugLog.error(getClass(), e);
        }
        return null;
    }

    /**
     * Generates a bitmap for the font and uploads it as a texture.
     */
    public void generateBitmap() {
        font = registerFont(filepath);
        assert font != null;
        font = new Font(font.getName(), Font.PLAIN, fontSize);

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        FontMetrics fontMetrics = g2d.getFontMetrics();

        int estimatedWidth = (int) sqrt(font.getNumGlyphs()) * font.getSize() + 1;
        width = 0;
        height = fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent();
        lineHeight = fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent();
        int x = 0;
        int y = (int) (fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent() * 1.4f);

        for (int r = 0; r < unicodeRanges.size(); r += 2) {
            for (int i = unicodeRanges.get(r); i <= unicodeRanges.get(r + 1); i++) {
                if (!font.canDisplay(i)) {
                    continue;
                }

                CharInfo charInfo = new CharInfo(x, y, fontMetrics.charWidth(i), fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent());
                characterMap.put(i, charInfo);
                width = max(x + fontMetrics.charWidth(i), width);

                x += charInfo.getWidth();
                if (x > estimatedWidth) {
                    x = 0;
                    y += (int) (fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent() * 1.4f);
                    height += (int) (fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent() * 1.4f);
                }
            }
        }

        height += (int) (fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent() * 1.4f);
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        for (int r = 0; r < unicodeRanges.size(); r += 2) {
            for (int i = unicodeRanges.get(r); i <= unicodeRanges.get(r + 1); i++) {
                if (!font.canDisplay(i)) {
                    continue;
                }

                CharInfo info = characterMap.get(i);
                info.calculateTextureCoordinates(width, height);
                g2d.drawString("" + (char) i, info.getSourceX(), info.getSourceY());
            }
        }

        g2d.dispose();

        uploadTexture(img);
    }

    /**
     * Uploads the specified image as a texture to OpenGL.
     *
     * @param image the image to upload
     */
    private void uploadTexture(BufferedImage image) {
        int[] pixels = new int[image.getHeight() * image.getWidth()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                byte alphaComponent = (byte) ((pixel >> 24) & 0xFF);
                buffer.put(alphaComponent);
                buffer.put(alphaComponent);
                buffer.put(alphaComponent);
                buffer.put(alphaComponent);
            }
        }
        buffer.flip();

        textureId = glGenTextures();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        buffer.clear();
    }

    /**
     * Gets the font.
     *
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the font.
     *
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Gets the file path of the font.
     *
     * @return the file path of the font
     */
    public FileLocation getFilepath() {
        return filepath;
    }

    /**
     * Sets the file path of the font.
     *
     * @param filepath the file path to set
     */
    public void setFilepath(FileLocation filepath) {
        this.filepath = filepath;
    }

    /**
     * Gets the size of the font.
     *
     * @return the size of the font
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Sets the size of the font.
     *
     * @param fontSize the size to set
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Gets the width of the font texture.
     *
     * @return the width of the font texture
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the font texture.
     *
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of the font texture.
     *
     * @return the height of the font texture
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the font texture.
     *
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the line height of the font.
     *
     * @return the line height of the font
     */
    public int getLineHeight() {
        return lineHeight;
    }

    /**
     * Sets the line height of the font.
     *
     * @param lineHeight the line height to set
     */
    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    /**
     * Gets the character map of the font.
     *
     * @return the character map of the font
     */
    public Map<Integer, CharInfo> getCharacterMap() {
        return characterMap;
    }

    /**
     * Sets the character map of the font.
     *
     * @param characterMap the character map to set
     */
    public void setCharacterMap(Map<Integer, CharInfo> characterMap) {
        this.characterMap = characterMap;
    }

    /**
     * Gets the texture ID of the font.
     *
     * @return the texture ID of the font
     */
    public int getTextureId() {
        return textureId;
    }

    /**
     * Sets the texture ID of the font.
     *
     * @param textureId the texture ID to set
     */
    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}