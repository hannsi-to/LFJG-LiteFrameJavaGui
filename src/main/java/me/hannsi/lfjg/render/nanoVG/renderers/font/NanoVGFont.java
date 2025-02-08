package me.hannsi.lfjg.render.nanoVG.renderers.font;

import me.hannsi.lfjg.render.nanoVG.system.NanoVGUtil;
import me.hannsi.lfjg.render.nanoVG.renderers.polygon.NanoVGLine;
import me.hannsi.lfjg.render.nanoVG.system.font.AlignExtractor;
import me.hannsi.lfjg.render.nanoVG.system.font.ChatFormatting;
import me.hannsi.lfjg.render.nanoVG.system.font.Font;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.math.StringUtil;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import java.util.List;

/**
 * Represents a font renderer using NanoVG.
 */
public class NanoVGFont {
    public long nvg;
    public Font font;
    public float x;
    public float y;
    public float fontSize;
    public int align;
    public String text;
    public boolean base;
    public Color baseColor;
    public boolean blur;
    public float blurSize;
    public Color blurColor;

    /**
     * Constructs a new NanoVGFont with the specified NanoVG context.
     *
     * @param nvg the NanoVG context
     */
    public NanoVGFont(long nvg) {
        this.nvg = nvg;
        this.base = false;
        this.blur = false;
    }

    /**
     * Sets the font and text for rendering.
     *
     * @param font the font to use
     * @param text the text to render
     */
    public void setFontSetting(Font font, String text) {
        this.font = font;
        this.text = text;
    }

    /**
     * Sets the size and alignment for rendering.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param fontSize the font size
     * @param align the alignment
     */
    public void setSize(float x, float y, float fontSize, int align) {
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.align = align;
    }

    /**
     * Sets the base rendering options.
     *
     * @param base whether to render the base text
     * @param baseColor the color of the base text
     */
    public void setBase(boolean base, Color baseColor) {
        this.base = base;
        this.baseColor = baseColor;
    }

    /**
     * Sets the blur effect for the font rendering.
     *
     * @param blur whether to apply the blur effect
     * @param blurSize the size of the blur effect
     * @param blurColor the color of the blur effect
     */
    public void setBlur(boolean blur, float blurSize, Color blurColor) {
        this.blur = blur;
        this.blurSize = blurSize;
        this.blurColor = blurColor;
    }

    /**
     * Draws the text with the specified settings.
     * Applies blur and base rendering options if enabled.
     */
    public void draw() {
        if (blur) {
            NanoVG.nvgFontBlur(nvg, blurSize);
            drawChatFormattingText(text, x, y, blurColor, align);
            NanoVG.nvgFontBlur(nvg, 0f);
        }
        if (base) {
            drawChatFormattingText(text, x, y, baseColor, align);
        }
    }

    /**
     * Draws the text with chat formatting options such as bold, italic, underline, and strikethrough.
     *
     * @param text the text to render
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param color the color of the text
     * @param align the alignment of the text
     */
    private void drawChatFormattingText(String text, float x, float y, Color color, int align) {
        float offsetX = 0.0f;
        float offsetY = 0.0f;
        boolean code = false;
        float heightMiddle = getHeight() / 2f;
        boolean obfuscated = false;
        char obfuscatedChar = '\u0000';
        boolean drawStrikethrough = false;
        float strikethroughLineX = 0.0f;
        float strikethroughLineY = 0.0f;
        float strikethroughLineWidth = 0.0f;
        float strikethroughLineHeight = 0.0f;
        boolean drawUnderline = false;
        float underLineX = 0.0f;
        float underLineY = 0.0f;
        float underLineWidth = 0.0f;
        float underLineHeight = 0.0f;
        float italic = 0.0f;
        float size = 0.0f;
        ChatFormatting chatFormatting = null;
        boolean bold = false;

        for (char ch : StringUtil.getChars(text)) {
            String str = StringUtil.getStringFromChar(ch);

            if (code) {
                chatFormatting = ChatFormatting.getChatFormatting(StringUtil.getStringFromChar(ch));
                code = false;
                continue;
            }

            if (chatFormatting != null) {
                if (chatFormatting.getForegroundColor() != null) {
                    color = new Color(chatFormatting.getForegroundColor());
                }
                if (chatFormatting == ChatFormatting.RESET) {
                    drawStrikethrough = false;
                    strikethroughLineX = 0.0f;
                    strikethroughLineY = 0.0f;
                    strikethroughLineWidth = 0.0f;
                    strikethroughLineHeight = 0.0f;
                    drawUnderline = false;
                    underLineX = 0.0f;
                    underLineY = 0.0f;
                    underLineWidth = 0.0f;
                    underLineHeight = 0.0f;
                    italic = 0.0f;
                    size = 0.0f;
                    chatFormatting = null;
                    obfuscated = false;
                    obfuscatedChar = '\u0000';
                    bold = false;
                }
                if (chatFormatting == ChatFormatting.OBFUSCATED) {
                    String characters = "1234567890abcdefghijklmnopqrstuvwxyz~!@#$%^&*()-=_+{}[]";
                    obfuscatedChar = StringUtil.getRandomCharacter(characters);
                    obfuscated = true;
                }
                if (chatFormatting == ChatFormatting.BOLD) {
                    bold = true;
                    size = 2f;
                }
                if (chatFormatting == ChatFormatting.STRIKETHROUGH) {
                    List<Integer> alignments = AlignExtractor.getAlignmentsAsInteger(align);

                    for (Integer integer : alignments) {
                        switch (integer) {
                            case NanoVG.NVG_ALIGN_LEFT:
                                strikethroughLineX = 0.0f;
                                strikethroughLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_CENTER:
                                float widthCenter = getWidth(str) / 2f;
                                strikethroughLineX = -widthCenter;
                                strikethroughLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_RIGHT:
                                strikethroughLineX = -getWidth(str);
                                strikethroughLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_TOP:
                                strikethroughLineY = heightMiddle;
                                strikethroughLineHeight = 0.0f;
                                break;
                            case NanoVG.NVG_ALIGN_MIDDLE:
                                strikethroughLineY = 0;
                                strikethroughLineHeight = 0;
                                break;
                            case NanoVG.NVG_ALIGN_BOTTOM:
                            case NanoVG.NVG_ALIGN_BASELINE:
                                strikethroughLineY = -heightMiddle;
                                strikethroughLineHeight = 0.0f;
                                break;
                        }
                    }

                    drawStrikethrough = true;
                }
                if (chatFormatting == ChatFormatting.UNDERLINE) {
                    List<Integer> alignments = AlignExtractor.getAlignmentsAsInteger(align);

                    for (Integer integer : alignments) {
                        switch (integer) {
                            case NanoVG.NVG_ALIGN_LEFT:
                                underLineX = 0.0f;
                                underLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_CENTER:
                                float widthCenter = getWidth(str) / 2f;
                                underLineX = -widthCenter;
                                underLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_RIGHT:
                                underLineX = -getWidth(str);
                                underLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_TOP:
                                underLineY = getHeight();
                                underLineHeight = 0.0f;
                                break;
                            case NanoVG.NVG_ALIGN_MIDDLE:
                                underLineY = heightMiddle;
                                underLineHeight = 0.0f;
                                break;
                            case NanoVG.NVG_ALIGN_BOTTOM:
                            case NanoVG.NVG_ALIGN_BASELINE:
                                underLineY = 0.0f;
                                underLineHeight = 0.0f;
                                break;
                        }
                    }

                    drawUnderline = true;
                }
                if (chatFormatting == ChatFormatting.ITALIC) {
                    italic = 0.1f;
                }
            }

            if (str.equals("§")) {
                code = true;
                continue;
            }

            drawNanoVGText(obfuscated ? StringUtil.getStringFromChar(obfuscatedChar) : str, x + offsetX, y + offsetY, color, align, italic, bold ? size : 0);
            if (drawStrikethrough) {
                NanoVGLine nanoVGLine = new NanoVGLine(nvg);
                nanoVGLine.setSize(x + offsetX + strikethroughLineX, y + offsetY + strikethroughLineY, strikethroughLineWidth, strikethroughLineHeight);
                nanoVGLine.setBase(true, color);
                nanoVGLine.setLineWidth(1.0f);
                nanoVGLine.draw();
            }
            if (drawUnderline) {
                NanoVGLine nanoVGLine = new NanoVGLine(nvg);
                nanoVGLine.setSize(x + offsetX + underLineX, y + offsetY + underLineY, underLineWidth, underLineHeight);
                nanoVGLine.setBase(true, color);
                nanoVGLine.setLineWidth(1.0f);
                nanoVGLine.draw();
            }

            offsetX += getWidth(str);
        }
    }

    /**
     * Draws the text using NanoVG with the specified settings.
     *
     * @param text the text to render
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param color the color of the text
     * @param align the alignment of the text
     * @param italic the italic angle
     * @param plusSize the additional size for bold text
     */
    private void drawNanoVGText(String text, float x, float y, Color color, int align, float italic, float plusSize) {
        NanoVG.nvgBeginPath(nvg);

        NanoVG.nvgRotate(nvg, italic);
        NVGColor nvgColor = NVGColor.calloc();
        NanoVGUtil.color(color, nvgColor);

        NanoVG.nvgFillColor(nvg, nvgColor);

        NanoVG.nvgFontSize(nvg, fontSize + plusSize);
        NanoVG.nvgTextAlign(nvg, align);
        NanoVG.nvgFontFace(nvg, font.getName());
        NanoVG.nvgText(nvg, x, y, text);

        NanoVG.nvgRGBAf(0, 0, 0, 0, nvgColor);
        NanoVG.nvgFillColor(nvg, nvgColor);
        NanoVG.nvgFill(nvg);
        NanoVG.nvgRotate(nvg, 0.0f);

        NanoVG.nvgRotate(nvg, -italic);

        NanoVG.nvgClosePath(nvg);
    }

    /**
     * Gets the width of the specified text.
     *
     * @param text the text to measure
     * @return the width of the text
     */
    public float getWidth(String text) {
        NanoVG.nvgFontFace(nvg, font.getName());
        NanoVG.nvgFontSize(nvg, fontSize);
        return NanoVG.nvgTextBounds(nvg, 0, 0, text, new float[4]);
    }

    /**
     * Gets the height of the current font.
     *
     * @return the height of the font
     */
    public float getHeight() {
        NanoVG.nvgFontFace(nvg, font.getName());
        NanoVG.nvgFontSize(nvg, fontSize);

        float[] lineH = new float[1];
        NanoVG.nvgTextMetrics(nvg, new float[1], new float[1], lineH);

        return lineH[0];
    }
}
