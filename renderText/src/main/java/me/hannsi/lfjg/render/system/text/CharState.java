package me.hannsi.lfjg.render.system.text;

import me.hannsi.lfjg.core.utils.graphics.color.Color;

public class CharState {
    public Color defaultColor;
    public Color color;
    public boolean obfuscated;
    public boolean strikethrough;
    public boolean underLine;
    public boolean doubleUnderLine;
    public boolean doubleStrikethrough;
    public boolean italic;
    public boolean bold;
    public boolean ghost;
    public boolean box;
    public boolean shadow;
    public boolean outLine;
    public boolean overLine;
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
        this.doubleUnderLine = false;
        this.doubleStrikethrough = false;
        this.italic = false;
        this.bold = false;
        this.ghost = false;
        this.box = false;
        this.shadow = false;
        this.outLine = false;
        this.overLine = false;
        this.spaseX = false;
        this.spaseY = false;
        this.spaseCheck = false;
        this.value = "";
        this.skip = false;
    }

    public void setState(TextFormatType textFormatType) {
        switch (textFormatType) {
            case BLACK:
            case DARK_BLUE:
            case DARK_GREEN:
            case DARK_AQUA:
            case DARK_RED:
            case DARK_PURPLE:
            case GOLD:
            case GRAY:
            case DARK_GRAY:
            case BLUE:
            case GREEN:
            case AQUA:
            case RED:
            case LIGHT_PURPLE:
            case YELLOW:
            case WHITE:
            case DEFAULT_COLOR:
                color = TextFormatType.getColor(textFormatType, defaultColor);
                break;
            case OBFUSCATED:
                obfuscated = true;
                break;
            case BOLD:
                bold = true;
                break;
            case STRIKETHROUGH:
                strikethrough = true;
                break;
            case UNDERLINE:
                underLine = true;
                break;
            case DOUBLE_UNDERLINE:
                doubleUnderLine = true;
                break;
            case DOUBLE_STRIKETHROUGH:
                doubleStrikethrough = true;
                break;
            case ITALIC:
                italic = true;
                break;
            case GHOST:
                ghost = true;
                break;
            case HIDE_BOX:
                box = true;
                break;
            case SHADOW:
                shadow = true;
                color = Color.of(0f, 0f, 0f, 0.5f);
                break;
            case OUTLINE:
                outLine = true;
                break;
            case OVERLINE:
                overLine = true;
                break;
            case RESET:
                reset();
                break;
            case NEWLINE:
                break;
            case SPASE_X:
                spaseX = true;
                break;
            case SPASE_Y:
                spaseY = true;
                break;
            case SKIP_PUSH:
                skip = true;
                break;
            case SKIP_POP:
                skip = false;
                break;
        }
    }
}
