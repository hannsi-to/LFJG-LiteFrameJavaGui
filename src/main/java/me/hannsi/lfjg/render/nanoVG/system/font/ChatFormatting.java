package me.hannsi.lfjg.render.nanoVG.system.font;

import java.awt.*;
import java.util.Objects;

/**
 * Enum representing various chat formatting options.
 */
public enum ChatFormatting {
    BLACK("Black", "black", "0", new Color(0, 0, 0), new Color(0, 0, 0)),
    DARK_BLUE("DarkBlue", "dark_blue", "1", new Color(0, 0, 170), new Color(0, 0, 42)),
    DARK_GREEN("DarKGreen", "dark_green", "2", new Color(0, 170, 0), new Color(0, 42, 0)),
    DARK_AQUA("DarkAqua", "dark_aqua", "3", new Color(0, 170, 170), new Color(0, 42, 42)),
    DARK_RED("DarkRed", "dark_red", "4", new Color(170, 0, 0), new Color(42, 0, 0)),
    DARK_PURPLE("DarkPurple", "dark_purple", "5", new Color(170, 0, 170), new Color(42, 0, 42)),
    GOLD("Gold", "gold", "6", new Color(255, 170, 0), new Color(42, 42, 0)),
    GRAY("Gray", "gray", "7", new Color(170, 170, 170), new Color(42, 42, 42)),
    DARK_GRAY("DarkGray", "dark_gray", "8", new Color(85, 85, 85), new Color(21, 21, 21)),
    BLUE("Blue", "blue", "9", new Color(85, 85, 255), new Color(21, 21, 63)),
    GREEN("Green", "green", "a", new Color(85, 255, 85), new Color(21, 63, 21)),
    AQUA("Aqua", "aqua", "b", new Color(85, 255, 255), new Color(21, 63, 63)),
    RED("Red", "red", "c", new Color(255, 85, 85), new Color(63, 21, 21)),
    LIGHT_PURPLE("LightPurple", "light_purple", "d", new Color(255, 85, 255), new Color(63, 21, 63)),
    YELLOW("Yellow", "yellow", "e", new Color(255, 255, 85), new Color(63, 63, 21)),
    WHITE("White", "white", "f", new Color(255, 255, 255), new Color(63, 63, 63)),
    OBFUSCATED("Obfuscated", "obfuscated", "k"),
    BOLD("Bold", "bold", "l"),
    STRIKETHROUGH("Strikethrough", "strikethrough", "m"),
    UNDERLINE("Underline", "underline", "n"),
    ITALIC("Italic", "italic", "o"),
    RESET("Reset", "reset", "r"),
    NEWLINE("NewLine", "new_line", "\n");

    /**
     * The prefix code used for chat formatting.
     */
    public static final char PREFIX_CODE = '§';
    String display;
    String technologyName;
    String code;
    Color foregroundColor;
    Color backgroundColor;

    /**
     * Constructs a ChatFormatting enum with the specified display name, technology name, and code.
     *
     * @param display the display name
     * @param technologyName the technology name
     * @param code the code
     */
    ChatFormatting(String display, String technologyName, String code) {
        this.display = display;
        this.technologyName = technologyName;
        this.code = code;
    }

    /**
     * Constructs a ChatFormatting enum with the specified display name, technology name, code, foreground color, and background color.
     *
     * @param display the display name
     * @param technologyName the technology name
     * @param code the code
     * @param foregroundColor the foreground color
     * @param backgroundColor the background color
     */
    ChatFormatting(String display, String technologyName, String code, Color foregroundColor, Color backgroundColor) {
        this.display = display;
        this.technologyName = technologyName;
        this.code = code;
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
    }

    /**
     * Retrieves the ChatFormatting enum corresponding to the specified code.
     *
     * @param code the code
     * @return the ChatFormatting enum
     */
    public static ChatFormatting getChatFormatting(String code) {
        ChatFormatting returnChatFormatting = null;
        for (ChatFormatting chatFormatting : values()) {
            if (Objects.equals(chatFormatting.getCode(), code)) {
                returnChatFormatting = chatFormatting;
            }
        }

        return returnChatFormatting;
    }

    /**
     * Gets the technology name.
     *
     * @return the technology name
     */
    public String getTechnologyName() {
        return technologyName;
    }

    /**
     * Sets the technology name.
     *
     * @param technologyName the technology name
     */
    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the foreground color.
     *
     * @return the foreground color
     */
    public Color getForegroundColor() {
        return foregroundColor;
    }

    /**
     * Sets the foreground color.
     *
     * @param foregroundColor the foreground color
     */
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    /**
     * Gets the background color.
     *
     * @return the background color
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the background color.
     *
     * @param backgroundColor the background color
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplay() {
        return display;
    }

    /**
     * Sets the display name.
     *
     * @param display the display name
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     * Returns the string representation of the ChatFormatting enum.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return PREFIX_CODE + code;
    }
}