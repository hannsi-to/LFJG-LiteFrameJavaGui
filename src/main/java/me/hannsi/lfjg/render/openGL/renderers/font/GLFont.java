package me.hannsi.lfjg.render.openGL.renderers.font;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.font.CFont;
import me.hannsi.lfjg.render.openGL.system.font.CharInfo;
import me.hannsi.lfjg.render.openGL.system.font.FontCache;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.lwjgl.opengl.GL30;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a font renderer in OpenGL.
 */
public class GLFont extends GLRect {
    private FrameBuffer frameBuffer;
    private Batch batch;
    private FontCache fontCache;
    private ResourcesLocation fontPath;
    private CFont cFont;
    private String text;
    private float x;
    private float y;
    private float scale;
    private Color color;

    /**
     * Constructs a new GLFont with the specified name.
     *
     * @param name the name of the font renderer
     */
    public GLFont(String name) {
        super(name);
    }

    /**
     * Sets the font for the renderer.
     *
     * @param fontCache the font cache
     * @param fontPath  the path to the font
     * @param size      the size of the font
     */
    public void setFont(FontCache fontCache, ResourcesLocation fontPath, int size) {
        this.fontCache = fontCache;
        this.fontPath = fontPath;
        this.cFont = fontCache.getFont(fontPath, size);
        batch = new Batch();
        batch.font = cFont;
        batch.initBatch();
    }

    /**
     * Sets the text and its properties for rendering.
     *
     * @param text  the text to render
     * @param x     the x position of the text
     * @param y     the y position of the text
     * @param scale the scale of the text
     * @param color the color of the text
     */
    public void font(String text, float x, float y, float scale, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.color = color;

        StringBuilder unknownCharacters = new StringBuilder();
        StringBuilder unknownCharacters2 = new StringBuilder();
        Set<Character> loggedCharacters = new HashSet<>();

        int index = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            CharInfo charInfo = cFont.getCharacter(c);
            if (charInfo.getWidth() == 0 && !loggedCharacters.contains(c)) {
                unknownCharacters2.append(String.format("U+%04X (\"%c\"), ", (int) c, c));
                loggedCharacters.add(c);

                index++;
                if (index == 10) {
                    unknownCharacters.append(unknownCharacters.isEmpty() ? "" : "\n\t").append(unknownCharacters2);
                    unknownCharacters2.delete(0, unknownCharacters2.length());
                    index = 0;
                }
            }
        }

        if (!unknownCharacters2.isEmpty()) {
            unknownCharacters.append(unknownCharacters2);
        }

        if (!unknownCharacters.isEmpty()) {
            unknownCharacters.setLength(unknownCharacters.length() - 2);
            LogGenerator logGenerator = new LogGenerator("Unknown characters", "Source: " + "GLFont", "Severity: Waring", unknownCharacters.toString());
            DebugLog.warning(getClass(), logGenerator.createLog());
        }

        frameBuffer = new FrameBuffer();
        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();

        uv(0, 0, 1, 1);
        rectWH(0, 0, LFJGContext.resolution.x(), LFJGContext.resolution.y(), new Color(0, 0, 0, 0));
    }

    /**
     * Sets the text and its properties for rendering.
     *
     * @param text  the text to render
     * @param x     the x position of the text
     * @param y     the y position of the text
     * @param scale the scale of the text
     * @param color the color of the text
     */
    public void font(String text, double x, double y, double scale, Color color) {
        font(text, (float) x, (float) y, (float) scale, color);
    }

    /**
     * Draws the text with the specified resolution and projection.
     */
    @Override
    public void draw() {
        frameBuffer.bindFrameBuffer();

        batch.addText(text, x, y, scale, color);
        batch.flushBatch();

        frameBuffer.unbindFrameBuffer();

        getGlUtil().addGLTarget(GL30.GL_TEXTURE_2D);
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, frameBuffer.getTextureId());
        super.draw();
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }

    /**
     * Gets the batch renderer.
     *
     * @return the batch renderer
     */
    public Batch getBatch() {
        return batch;
    }

    /**
     * Sets the batch renderer.
     *
     * @param batch the batch renderer
     */
    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    /**
     * Gets the font cache.
     *
     * @return the font cache
     */
    public FontCache getFontCache() {
        return fontCache;
    }

    /**
     * Sets the font cache.
     *
     * @param fontCache the font cache
     */
    public void setFontCache(FontCache fontCache) {
        this.fontCache = fontCache;
    }

    /**
     * Gets the font path.
     *
     * @return the font path
     */
    public ResourcesLocation getFontPath() {
        return fontPath;
    }

    /**
     * Sets the font path.
     *
     * @param fontPath the font path
     */
    public void setFontPath(ResourcesLocation fontPath) {
        this.fontPath = fontPath;
    }

    /**
     * Gets the font.
     *
     * @return the font
     */
    public CFont getcFont() {
        return cFont;
    }

    /**
     * Sets the font.
     *
     * @param cFont the font
     */
    public void setcFont(CFont cFont) {
        this.cFont = cFont;
    }

    /**
     * Gets the text to render.
     *
     * @return the text to render
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text to render.
     *
     * @param text the text to render
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the x position of the text.
     *
     * @return the x position of the text
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x position of the text.
     *
     * @param x the x position of the text
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the y position of the text.
     *
     * @return the y position of the text
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y position of the text.
     *
     * @param y the y position of the text
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets the scale of the text.
     *
     * @return the scale of the text
     */
    public float getScale() {
        return scale;
    }

    /**
     * Sets the scale of the text.
     *
     * @param scale the scale of the text
     */
    public void setScale(float scale) {
        this.scale = scale;
    }


    @Override
    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    @Override
    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    /**
     * Sets the color of the text.
     *
     * @param color the color of the text
     */
    public void setColor(Color color) {
        this.color = color;
    }
}