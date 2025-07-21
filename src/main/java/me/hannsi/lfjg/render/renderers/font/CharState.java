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
    public boolean spaseX;
    public boolean spaseY;
    public boolean spaseCheck;
    public String spase;

    public CharState(Color defaultColor) {
        this.defaultColor = defaultColor;
        reset();
    }

    public void reset() {
        this.color = null;
        this.obfuscated = false;
        this.strikethrough = false;
        this.underLine = false;
        this.italic = false;
        this.bold = false;
        this.spaseX = false;
        this.spaseY = false;
        this.spaseCheck = false;
        this.spase = "";
    }

    public void setState(TextFormatType textFormatType) {
        switch (textFormatType) {
            case BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN,
                 AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE -> color = TextFormatType.getColor(textFormatType);
            case OBFUSCATED -> obfuscated = true;
            case BOLD -> bold = true;
            case STRIKETHROUGH -> strikethrough = true;
            case UNDERLINE -> underLine = true;
            case ITALIC -> italic = true;
            case REST -> reset();
            case NEWLINE -> {
            }
            case SPASE_X -> this.spaseX = true;
            case SPASE_Y -> this.spaseY = true;
        }
    }
}
