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

import static org.lwjgl.opengl.GL11.*;

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
    public CFont(FileLocation filepath, int fontSize) {
        addUnicodeRange(UnicodeRange.BASIC_LATIN_START, UnicodeRange.BASIC_LATIN_END);

        this.filepath = filepath;
        this.fontSize = fontSize;
        this.characterMap = new HashMap<>();
        generateBitmap();
    }

    public CFont(ResourcesLocation filepath, int fontSize) {
        this((FileLocation) filepath, fontSize);
    }

    public static void addUnicodeRange(int start, int end) {
        unicodeRanges.add(start);
        unicodeRanges.add(end);
    }

    public void cleanup() {
        characterMap.clear();
    }

    public int getFontHeight() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        graphics.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics();

        int fontHeight = metrics.getHeight();

        graphics.dispose();

        return fontHeight;
    }

    public int getFontWidth(String text) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        graphics.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics();

        int fontWidth = metrics.stringWidth(text);

        graphics.dispose();

        return fontWidth;
    }

    public CharInfo getCharacter(int codepoint) {
        return characterMap.getOrDefault(codepoint, new CharInfo(0, 0, 0, 0));
    }

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

        int estimatedWidth = (int) Math.sqrt(font.getNumGlyphs()) * font.getSize() + 1;
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
                width = Math.max(x + fontMetrics.charWidth(i), width);

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

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public FileLocation getFilepath() {
        return filepath;
    }

    public void setFilepath(FileLocation filepath) {
        this.filepath = filepath;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public Map<Integer, CharInfo> getCharacterMap() {
        return characterMap;
    }

    public void setCharacterMap(Map<Integer, CharInfo> characterMap) {
        this.characterMap = characterMap;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}
