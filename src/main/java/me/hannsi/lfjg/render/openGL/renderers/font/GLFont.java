package me.hannsi.lfjg.render.openGL.renderers.font;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.font.CFont;
import me.hannsi.lfjg.render.openGL.system.font.CharInfo;
import me.hannsi.lfjg.render.openGL.system.font.FontCache;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;

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

    public GLFont(String name) {
        super(name);
    }

    public void setFont(FontCache fontCache, ResourcesLocation fontPath, int size) {
        this.fontCache = fontCache;
        this.fontPath = fontPath;
        this.cFont = fontCache.getFont(fontPath, size);
        batch = new Batch(getProjectionMatrix());
        batch.font = cFont;
        batch.initBatch();
    }

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
            LogGenerator logGenerator = new LogGenerator("Unknown characters", unknownCharacters.toString());
            DebugLog.warning(getClass(), logGenerator.createLog());
        }

        frameBuffer = new FrameBuffer(getResolution());
        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();

        uv(0, 0, 1, 1);
        rectWH(0, 0, getResolution().x(), getResolution().y(), new Color(0, 0, 0, 0));
    }

    public void font(String text, double x, double y, double scale, Color color) {
        font(text, (float) x, (float) y, (float) scale, color);
    }

    @Override
    public void draw(Vector2f resolution, Projection projection) {
        frameBuffer.bindFrameBuffer();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        batch.addText(text, x, y, scale, color);

        batch.flushBatch();

        glDisable(GL_BLEND);
        frameBuffer.unbindFrameBuffer();

        getGlUtil().addGLTarget(GL30.GL_TEXTURE_2D);
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, frameBuffer.getTextureId());
        super.draw(resolution, projection);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public FontCache getFontCache() {
        return fontCache;
    }

    public void setFontCache(FontCache fontCache) {
        this.fontCache = fontCache;
    }

    public ResourcesLocation getFontPath() {
        return fontPath;
    }

    public void setFontPath(ResourcesLocation fontPath) {
        this.fontPath = fontPath;
    }

    public CFont getcFont() {
        return cFont;
    }

    public void setcFont(CFont cFont) {
        this.cFont = cFont;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
