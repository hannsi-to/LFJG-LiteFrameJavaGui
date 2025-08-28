package me.hannsi.lfjg.render.renderers.text;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.text.Align;
import me.hannsi.lfjg.render.system.text.AlignType;
import me.hannsi.lfjg.render.system.text.font.Font;
import org.joml.Vector2f;

import static me.hannsi.lfjg.render.LFJGRenderTextContext.fontCache;

public class GLText extends GLRect {
    private TextRenderer textRenderer;
    private String text;
    private int fontSize;
    private boolean textFormat;
    private AlignType alignType;

    public GLText(String name) {
        super(name);
    }

    public void text(String name,String text, float x, float y, int fontSize, Color fontColor, boolean textFormat, AlignType alignType){
        Font font = fontCache.getFont(name);

        this.text = text;
        this.fontSize = fontSize;
        this.textFormat = textFormat;
        this.alignType = alignType;

        textRenderer = TextRenderer.createTextRender()
                .textMeshBuilder(font.getTextMeshBuilder())
                .msdfTextureLoader(font.getMsdfTextureLoader())
                .defaultFontColor(fontColor)
                .textFormat(textFormat)
                .pos(new Vector2f(x,y))
                .size(fontSize)
                .align(alignType);

        rectWH(x,y, getTextWidth(text),getTextHeight(text),Color.of(0,0,0,0));

        rendering();

        textRenderer.setBaseMatrix(getTransform().getModelMatrix());
    }

    public float getTextWidth(String text) {
        return textRenderer.getTextWidth(text);
    }

    public float getTextHeight(String text) {
        return textRenderer.getTextHeight(text);
    }

    @Override
    public void drawVAORendering() {
        textRenderer.draw(text);
    }
}
