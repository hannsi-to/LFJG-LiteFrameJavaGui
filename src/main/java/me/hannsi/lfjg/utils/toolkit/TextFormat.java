package me.hannsi.lfjg.utils.toolkit;

import me.hannsi.lfjg.utils.graphics.color.Color;

/**
 * Utility class for text formatting using custom codes.
 */
public class TextFormat {
    /**
     * Prefix code for text formatting.
     */
    public static final char PREFIX_CODE = '§';

    public static final String BLACK = PREFIX_CODE + "0";
    public static final String DARK_BLUE = PREFIX_CODE + "1";
    public static final String DARK_GREEN = PREFIX_CODE + "2";
    public static final String DARK_AQUA = PREFIX_CODE + "3";
    public static final String DARK_RED = PREFIX_CODE + "4";
    public static final String DARK_PURPLE = PREFIX_CODE + "5";
    public static final String GOLD = PREFIX_CODE + "6";
    public static final String GRAY = PREFIX_CODE + "7";
    public static final String DARK_GRAY = PREFIX_CODE + "8";
    public static final String BLUE = PREFIX_CODE + "9";
    public static final String GREEN = PREFIX_CODE + "a";
    public static final String AQUA = PREFIX_CODE + "b";
    public static final String RED = PREFIX_CODE + "c";
    public static final String LIGHT_PURPLE = PREFIX_CODE + "d";
    public static final String YELLOW = PREFIX_CODE + "e";
    public static final String WHITE = PREFIX_CODE + "f";
    public static final String OBFUSCATED = PREFIX_CODE + "g";

    /**
     * Bold text format (deprecated).
     */
    @Deprecated
    public static final String BOLD = PREFIX_CODE + "h";

    /**
     * Strikethrough text format (deprecated).
     */
    @Deprecated
    public static final String STRIKETHROUGH = PREFIX_CODE + "i";

    /**
     * Underline text format (deprecated).
     */
    @Deprecated
    public static final String UNDERLINE = PREFIX_CODE + "j";

    public static final String ITALIC = PREFIX_CODE + "k";
    public static final String RESET = PREFIX_CODE + "r";
    public static final String NEWLINE = PREFIX_CODE + "l";
    public static final String SPASE_X = PREFIX_CODE + "m";
    public static final String SPASE_Y = PREFIX_CODE + "n";
    public static final String RESET_POINT_X = PREFIX_CODE + "o";
    public static final String RESET_POINT_Y = PREFIX_CODE + "p";

    /**
     * Gets the color associated with a text format code.
     *
     * @param format       the text format code
     * @param defaultColor the default color to return if the format code is not recognized
     * @return the color associated with the format code, or the default color if the format code is not recognized
     */
    public static Color getColor(String format, Color defaultColor) {
        return switch (format) {
            case BLACK -> new Color(0, 0, 0);
            case DARK_BLUE -> new Color(0, 0, 170);
            case DARK_GREEN -> new Color(0, 170, 0);
            case DARK_AQUA -> new Color(0, 170, 170);
            case DARK_RED -> new Color(170, 0, 0);
            case DARK_PURPLE -> new Color(170, 0, 170);
            case GOLD -> new Color(255, 170, 0);
            case GRAY -> new Color(170, 170, 170);
            case DARK_GRAY -> new Color(85, 85, 85);
            case BLUE -> new Color(85, 85, 255);
            case GREEN -> new Color(85, 255, 85);
            case AQUA -> new Color(85, 255, 255);
            case RED -> new Color(255, 85, 85);
            case LIGHT_PURPLE -> new Color(255, 85, 255);
            case YELLOW -> new Color(255, 255, 85);
            case WHITE -> new Color(255, 255, 255);
            default -> defaultColor;
        };
    }

    /**
     * Checks if a character is a prefix code.
     *
     * @param c the character to check
     * @return true if the character is a prefix code, false otherwise
     */
    public static boolean isPrefix(char c) {
        return PREFIX_CODE == c;
    }

    /**
     * Checks if a string starts with a prefix code.
     *
     * @param s the string to check
     * @return true if the string starts with a prefix code, false otherwise
     */
    public static boolean isPrefix(String s) {
        return isPrefix(s.charAt(0));
    }
}