package me.hannsi.lfjg.render.nanoVG.system.font;

import java.awt.*;
import java.util.Objects;

public enum ChatFormatting {
    BLACK("Black", "black", "0", new Color(0, 0, 0), new Color(0, 0, 0)), DARK_BLUE("DarkBlue", "dark_blue", "1", new Color(0, 0, 170), new Color(0, 0, 42)), DARK_GREEN("DarKGreen", "dark_green", "2", new Color(0, 170, 0), new Color(0, 42, 0)), DARK_AQUA("DarkAqua", "dark_aqua", "3", new Color(0, 170, 170), new Color(0, 42, 42)), DARK_RED("DarkRed", "dark_red", "4", new Color(170, 0, 0), new Color(42, 0, 0)), DARK_PURPLE("DarkPurple", "dark_purple", "5", new Color(170, 0, 170), new Color(42, 0, 42)), GOLD("Gold", "gold", "6", new Color(255, 170, 0), new Color(42, 42, 0)), GRAY("Gray", "gray", "7", new Color(170, 170, 170), new Color(42, 42, 42)), DARK_GRAY("DarkGray", "dark_gray", "8", new Color(85, 85, 85), new Color(21, 21, 21)), BLUE("Blue", "blue", "9", new Color(85, 85, 255), new Color(21, 21, 63)), GREEN("Green", "green", "a", new Color(85, 255, 85), new Color(21, 63, 21)), AQUA("Aqua", "aqua", "b", new Color(85, 255, 255), new Color(21, 63, 63)), RED("Red", "red", "c", new Color(255, 85, 85), new Color(63, 21, 21)), LIGHT_PURPLE("LightPurple", "light_purple", "d", new Color(255, 85, 255), new Color(63, 21, 63)), YELLOW("Yellow", "yellow", "e", new Color(255, 255, 85), new Color(63, 63, 21)), WHITE("White", "white", "f", new Color(255, 255, 255), new Color(63, 63, 63)), OBFUSCATED("Obfuscated", "obfuscated", "k"), BOLD("Bold", "bold", "l"), STRIKETHROUGH("Strikethrough", "strikethrough", "m"), UNDERLINE("Underline", "underline", "n"), ITALIC("Italic", "italic", "o"), RESET("Reset", "reset", "r"), NEWLINE("NewLine", "new_line", "\n");

    public static final char PREFIX_CODE = '§';
    String display;
    String technologyName;
    String code;
    Color foregroundColor;
    Color backgroundColor;

    ChatFormatting(String display, String technologyName, String code) {
        this.display = display;
        this.technologyName = technologyName;
        this.code = code;
    }

    ChatFormatting(String display, String technologyName, String code, Color foregroundColor, Color backgroundColor) {
        this.display = display;
        this.technologyName = technologyName;
        this.code = code;
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
    }

    public static ChatFormatting getChatFormatting(String code) {
        ChatFormatting returnChatFormatting = null;
        for (ChatFormatting chatFormatting : values()) {
            if (Objects.equals(chatFormatting.getCode(), code)) {
                returnChatFormatting = chatFormatting;
            }
        }

        return returnChatFormatting;
    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return PREFIX_CODE + code;
    }
}
