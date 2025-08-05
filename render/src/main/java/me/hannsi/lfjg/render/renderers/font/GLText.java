package me.hannsi.lfjg.render.renderers.font;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.toolkit.StringUtil;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.font.AlignExtractor;
import me.hannsi.lfjg.render.system.font.Font;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.nanovg.NVGColor;

import java.util.List;

import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgBeginPath;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgClosePath;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgFill;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgFillColor;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgFontBlur;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgFontFace;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgFontSize;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgLineTo;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgMoveTo;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgRestore;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgSave;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgStroke;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgStrokeColor;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgStrokeWidth;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgText;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgTextAlign;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgTextBounds;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgTextMetrics;
import static me.hannsi.lfjg.render.system.NanoVGUtil.nvgTransform;
import static me.hannsi.lfjg.render.system.NanoVGUtil.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class GLText extends GLRect {
    private FrameBuffer frameBuffer;

    private Font font;
    private String text;
    private float textX;
    private float textY;
    private float fontSize;
    private boolean blur;
    private float blurSize;
    private Color textColor;
    private AlignType align;

    /**
     * Constructs a new GLRect with the specified name.
     *
     * @param name the name of the rectangle renderer
     */
    public GLText(String name) {
        super(name);
    }

    private static void drawLineWH(float x, float y, float width, float height, float lineWidth, Color color) {
        drawLine(x, y, x + width, y + height, lineWidth, color);
    }

    private static void drawLine(float x, float y, float x1, float y1, float lineWidth, Color color) {
        nvgBeginPath();

        nvgMoveTo(x, y);
        nvgLineTo(x1, y1);

        NVGColor nvgColor = colorToNVG(color);
        nvgStrokeColor(nvgColor);
        nvgStrokeWidth(lineWidth);
        nvgStroke();

        nvgClosePath();
    }

    public void text(String fontName, String text, float x, float y, float fontSize, Color color, AlignType align) {
        this.text(fontName, text, x, y, fontSize, false, 0.0f, color, align);
    }

    public void text(String fontName, String text, float x, float y, float fontSize, boolean blur, float blurSize, Color color, AlignType align) {
        this.font = LFJGRenderContext.fontCache.getFont(fontName);
        this.text = text;
        this.fontSize = fontSize;
        this.blur = blur;
        this.blurSize = blurSize;
        this.textColor = color;
        this.align = align;
        this.textX = x;
        this.textY = y;

        float width = getTextWidth(text);
        float height = getTextHeight();

        System.out.println("Text size: " + width + " x " + height);

        this.frameBuffer = new FrameBuffer(0, 0, width, height);
        this.frameBuffer.createShaderProgram();
        this.frameBuffer.createFrameBuffer();

        uv(0, 0, 1, 1);
        rectWH(x, y, width, height, new Color(0, 0, 0, 0));
    }

    public float getWidth() {
        return getTextWidth(text);
    }

    public float getTextWidth(char ch) {
        return getTextWidth(StringUtil.getStringFromChar(ch));
    }

    public float getTextWidth(String str) {
        if (str == null || str.isEmpty()) {
            return 0f;
        }

        nvgFontFace(font.getName());
        nvgFontSize(fontSize);

        float[] bounds = new float[4];
        return nvgTextBounds(0f, 0f, str, bounds);
    }

    public float getHeight() {
        return getTextHeight();
    }

    public float getTextHeight() {
        nvgFontFace(font.getName());
        nvgFontSize(fontSize);

        float[] lineHeight = new float[1];
        nvgTextMetrics(new float[1], new float[1], lineHeight);

        return lineHeight[0];
    }

    @Override
    public void draw() {
        frameBuffer.bindFrameBuffer();

        drawTextFormat(font.getName(), textColor, align.getId());

        frameBuffer.unbindFrameBuffer();

        GLStateCache.enable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, frameBuffer.getTextureId());
        super.draw();
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private void drawTextFormat(String fontName, Color color, int align) {
        nvgFramePush();

        float offsetX = 0.0f;
        float offsetY = 0.0f;
        float spaseX = 0.0f;
        float spaseY;
        boolean code = false;
        TextFormatType textFormatType;
        CharState charState = new CharState(color);

        float size = 0.0f;

        for (char ch : StringUtil.getChars(text)) {
            float strikethroughLineX = 0.0f;
            float strikethroughLineY = 0.0f;
            float strikethroughLineWidth = 0.0f;
            float strikethroughLineHeight = 0.0f;
            float underLineX = 0.0f;
            float underLineY = 0.0f;
            float underLineWidth = 0.0f;
            float underLineHeight = 0.0f;

            if (ch == TextFormatType.PREFIX_CODE) {
                code = true;
                continue;
            }

            if (code) {
                textFormatType = TextFormatType.getTextFormatType(ch);
                code = false;
                if (textFormatType != null) {
                    charState.setState(textFormatType);

                    if (textFormatType == TextFormatType.NEWLINE) {
                        offsetX = 0;
                        offsetY -= (getTextHeight());
                    }
                } else {
                    new LogGenerator("TextFormat Message", "Source: GLFont", "Type: No Code", "ID: " + hashCode(), "Severity: Waring", "Message: Not font text format code: " + TextFormatType.PREFIX_CODE + ch).logging(DebugLevel.WARNING);
                }

                continue;
            }

            if (charState.obfuscated) {
                String characters = "1234567890abcdefghijklmnopqrstuvwxyz~!@#$%^&*()-=_+{}[]";
                ch = StringUtil.getRandomCharacter(characters);
            }
            if (charState.strikethrough) {
                List<Integer> alignments = AlignExtractor.getAlignmentsAsInteger(align);

                for (Integer integer : alignments) {
                    switch (integer) {
                        case NVG_ALIGN_LEFT:
                            strikethroughLineX = 0.0f;
                            strikethroughLineWidth = getTextWidth(ch);
                            break;
                        case NVG_ALIGN_CENTER:
                            float widthCenter = getTextWidth(ch) / 2f;
                            strikethroughLineX = -widthCenter;
                            strikethroughLineWidth = getTextWidth(ch);
                            break;
                        case NVG_ALIGN_RIGHT:
                            strikethroughLineX = -getTextWidth(ch);
                            strikethroughLineWidth = getTextWidth(ch);
                            break;
                    }
                }

                strikethroughLineY = fontSize * 0.3f;
            }
            if (charState.underLine) {
                List<Integer> alignments = AlignExtractor.getAlignmentsAsInteger(align);

                for (Integer integer : alignments) {
                    switch (integer) {
                        case NVG_ALIGN_LEFT:
                            underLineX = 0.0f;
                            underLineWidth = getTextWidth(ch);
                            break;
                        case NVG_ALIGN_CENTER:
                            float widthCenter = getTextWidth(ch) / 2f;
                            underLineX = -widthCenter;
                            underLineWidth = getTextWidth(ch);
                            break;
                        case NVG_ALIGN_RIGHT:
                            underLineX = -getTextWidth(ch);
                            underLineWidth = getTextWidth(ch);
                            break;
                    }
                }

                float[] ascender = new float[1];
                float[] descender = new float[1];
                float[] lineHeight = new float[1];

                nvgTextMetrics(ascender, descender, lineHeight);

                float lineOffset = fontSize * 0.3f;
                float middleLine = -(ascender[0] - descender[0]) / 2f;
                underLineY = middleLine + lineOffset;
            }
            if (charState.spaseX || charState.spaseY) {
                if (ch == '{') {
                    charState.spaseCheck = true;
                    continue;
                } else if (ch == '}') {
                    charState.spaseCheck = false;
                    continue;
                } else if (charState.spaseCheck) {
                    charState.value = charState.value + StringUtil.getStringFromChar(ch);
                    continue;
                }
                if (charState.spaseX) {
                    spaseX = Float.parseFloat(charState.value);
                    charState.value = "";
                    charState.spaseX = false;
                }
                if (charState.spaseY) {
                    spaseY = Float.parseFloat(charState.value);
                    offsetY -= spaseY;
                    charState.spaseY = false;
                }
            }

            float lineWidth = charState.bold ? fontSize + size / 10f : fontSize / 10f;
            drawNanoVGText(fontName, ch, 0 + offsetX, 0 + offsetY, color, align, charState.italic, charState.bold ? 10f : 0);
            if (charState.strikethrough) {
                drawLineWH(0 + offsetX + strikethroughLineX, 0 + offsetY + strikethroughLineY, strikethroughLineWidth + spaseX, strikethroughLineHeight, lineWidth, color);
            }
            if (charState.underLine) {
                drawLineWH(0 + offsetX + underLineX, 0 + offsetY + underLineY, underLineWidth + spaseX, underLineHeight, lineWidth, color);
            }

            offsetX += getTextWidth(ch) + spaseX;
        }

        nvgFramePop();
    }

    private void drawNanoVGText(String fontName, char ch, float x, float y, Color color, int align, boolean italic, float plusSize) {
        if (blur) {
            nvgFontBlur(blurSize);
        }

        NVGColor nvgColor = colorToNVG(color);
        nvgFillColor(nvgColor);

        nvgFontSize(fontSize + plusSize);
        nvgTextAlign(align);
        nvgFontFace(fontName);

        nvgSave();
        if (italic) {
            float skewX = (float) Math.tan(0.3);
            nvgTransform(1, 0, -skewX, 1, skewX * (1060 - y), 0.0f);
        }

        nvgBeginPath();
        nvgText(x, y, StringUtil.getStringFromChar(ch));
        nvgFill();
        nvgRestore();
        nvgClosePath();

        if (blur) {
            nvgFontBlur(0f);
        }
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getTextX() {
        return textX;
    }

    public void setTextX(float textX) {
        this.textX = textX;
    }

    public float getTextY() {
        return textY;
    }

    public void setTextY(float textY) {
        this.textY = textY;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isBlur() {
        return blur;
    }

    public void setBlur(boolean blur) {
        this.blur = blur;
    }

    public float getBlurSize() {
        return blurSize;
    }

    public void setBlurSize(float blurSize) {
        this.blurSize = blurSize;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public AlignType getAlign() {
        return align;
    }

    public void setAlign(AlignType align) {
        this.align = align;
    }
}