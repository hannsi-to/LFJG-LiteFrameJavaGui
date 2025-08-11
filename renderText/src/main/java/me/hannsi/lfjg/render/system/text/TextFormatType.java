package me.hannsi.lfjg.render.system.text;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.toolkit.StringUtil;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum TextFormatType implements IEnumTypeBase {
    BLACK("Black", 0, "0"),
    DARK_BLUE("DarkBlue", 1, "1"),
    DARK_GREEN("DarkGreen", 2, "2"),
    DARK_AQUA("DarkAqua", 3, "3"),
    DARK_RED("DarkRed", 4, "4"),
    DARK_PURPLE("DarkPurple", 5, "5"),
    GOLD("Gold", 6, "6"),
    GRAY("Gray", 7, "7"),
    DARK_GRAY("DarkGray", 8, "8"),
    BLUE("Blue", 9, "9"),
    GREEN("Green", 10, "a"),
    AQUA("Aqua", 11, "b"),
    RED("Red", 12, "c"),
    LIGHT_PURPLE("LightPurple", 13, "d"),
    YELLOW("Yellow", 14, "e"),
    WHITE("White", 15, "f"),
    DEFAULT_COLOR("DefaultColor", 16, "g"),
    OBFUSCATED("Obfuscated", 17, "h"),
    BOLD("Bold", 18, "i"),
    STRIKETHROUGH("Strikethrough", 19, "j"),
    UNDERLINE("UnderLine", 20, "k"),
    ITALIC("Italic", 21, "l"),
    GHOST("Ghost", 22, "m"),
    HIDE_BOX("HideBox", 23, "n"),
    SHADOW("Shadow", 24, "o"),
    RESET("Reset", 25, "p"),
    NEWLINE("NewLine", 26, "q"),
    SPASE_X("SpaceX", 27, "r"),
    SPASE_Y("SpaceY", 28, "s"),
    SKIP_PUSH("SkipPush", 29, "t"),
    SKIP_POP("SkipPop", 30, "u"),
    OUTLINE("OutLine", 31, "v"),
    DOUBLE_UNDERLINE("DoubleUnderLine", 32, "w"),
    DOUBLE_STRIKETHROUGH("DoubleSTRIKETHROUGH", 33, "x"),
    OVERLINE("OverLine", 34, "y");

    public static final char PREFIX_CODE = '§';

    final String name;
    final int id;
    final String code;

    TextFormatType(String name, int id, String code) {
        this.name = name;
        this.id = id;
        this.code = code;
    }

    public static Color getColor(TextFormatType textFormatType, Color defaultColor) {
        switch (textFormatType) {
            case BLACK:
                return new Color(0, 0, 0);
            case DARK_BLUE:
                return new Color(0, 0, 170);
            case DARK_GREEN:
                return new Color(0, 170, 0);
            case DARK_AQUA:
                return new Color(0, 170, 170);
            case DARK_RED:
                return new Color(170, 0, 0);
            case DARK_PURPLE:
                return new Color(170, 0, 170);
            case GOLD:
                return new Color(255, 170, 0);
            case GRAY:
                return new Color(170, 170, 170);
            case DARK_GRAY:
                return new Color(85, 85, 85);
            case BLUE:
                return new Color(85, 85, 255);
            case GREEN:
                return new Color(85, 255, 85);
            case AQUA:
                return new Color(85, 255, 255);
            case RED:
                return new Color(255, 85, 85);
            case LIGHT_PURPLE:
                return new Color(255, 85, 255);
            case YELLOW:
                return new Color(255, 255, 85);
            case WHITE:
                return new Color(255, 255, 255);
            case DEFAULT_COLOR:
                return defaultColor;
            default:
                return null;
        }
    }

    public static TextFormatType getTextFormatType(char code) {
        for (TextFormatType testFormatType : values()) {
            if (testFormatType.getCode().equals(StringUtil.getStringFromChar(code))) {
                return testFormatType;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return PREFIX_CODE + code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}
