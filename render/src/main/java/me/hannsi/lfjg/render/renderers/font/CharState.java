package me.hannsi.lfjg.render.renderers.font;

import me.hannsi.lfjg.core.utils.graphics.color.Color;

public class CharState {
    public Color defaultColor;
    public Color color;
    public boolean obfuscated;
    public boolean strikethrough;
    public boolean underLine;
    public boolean italic;
    public boolean bold;
    public boolean ghost;
    public boolean box;
    public boolean shadow;
    public boolean outLine;
    public boolean spaseX;
    public boolean spaseY;
    public boolean spaseCheck;
    public String value;
    public boolean skip;

    public CharState(Color defaultColor) {
        this.defaultColor = defaultColor;
        reset();
    }

    public void reset() {
        this.color = defaultColor;
        this.obfuscated = false;
        this.strikethrough = false;
        this.underLine = false;
        this.italic = false;
        this.bold = false;
        this.ghost = false;
        this.box = false;
        this.shadow = false;
        this.outLine = false;
        this.spaseX = false;
        this.spaseY = false;
        this.spaseCheck = false;
        this.value = "";
        this.skip = false;
    }

    public void setState(TextFormatType textFormatType) {
        switch (textFormatType) {
            case BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN,
                 AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, DEFAULT_COLOR ->
                    color = TextFormatType.getColor(textFormatType, defaultColor);
            case OBFUSCATED -> obfuscated = true;
            case BOLD -> bold = true;
            case STRIKETHROUGH -> strikethrough = true;
            case UNDERLINE -> underLine = true;
            case ITALIC -> italic = true;
            case GHOST -> ghost = true;
            case HIDE_BOX -> box = true;
            case SHADOW -> {
                shadow = true;
                color = Color.of(0f, 0f, 0f, 0.5f);
            }
            case OUTLINE -> outLine = true;
            case RESET -> reset();
            case NEWLINE -> {
            }
            case SPASE_X -> spaseX = true;
            case SPASE_Y -> spaseY = true;

            case SKIP_PUSH -> skip = true;
            case SKIP_POP -> skip = false;
        }
    }
}
