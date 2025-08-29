package me.hannsi.example.demo;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.UnicodeBlocks;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.renderers.text.GLText;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import me.hannsi.lfjg.render.system.text.AlignType;
import me.hannsi.lfjg.render.system.text.TextFormatType;
import me.hannsi.lfjg.render.system.text.font.Font;

import static me.hannsi.lfjg.render.LFJGRenderTextContext.fontCache;

public class Demo2 implements IScene {
    Frame frame;
    Scene scene;

    GLRect glBackGround;
    GLText glText1;
    GLText glText2;
    GLText glText3;
    GLText glText4;
    GLText glText5;
    GLText glText6;
    GLText glText7;
    GLText glText8;
    GLText glText9;

    public Demo2(Frame frame) {
        this.frame = frame;
        this.scene = new Scene("Demo2", this);
    }

    @Override
    public void init() {
        fontCache.createCache(
                "Font1",
                Font.createCustomFont()
                        .name("Font1")
                        .trueTypeFontPath(Location.fromFile("C:\\Windows\\Fonts\\Arial.ttf"))
                        .unicodeBlocks(UnicodeBlocks.getBlocks("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.:,;'\"(!?)+-*/="))
                        .createCache()
        );

        glBackGround = new GLRect("GLBackGround");
        glBackGround.rect(0, 0, frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), Color.WHITE);

        float offsetY = frame.getWindowHeight() - 80;
        glText1 = new GLText("GLText1");
        glText1.text(
                "Font1", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" + TextFormatType.NEWLINE + "1234567890.:,;'\"(!?)+-*/=",
                20, offsetY, 30, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText1.getTextHeight("A") * 3;
        glText2 = new GLText("GLText2");
        glText2.text(
                "Font1", "12    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 12, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText2.getTextHeight("A");
        glText3 = new GLText("GLText3");
        glText3.text(
                "Font1", "18    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 18, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText3.getTextHeight("A");
        glText4 = new GLText("GLText4");
        glText4.text(
                "Font1", "24    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 24, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText4.getTextHeight("A");
        glText5 = new GLText("GLText5");
        glText5.text(
                "Font1", "36    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 36, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText5.getTextHeight("A");
        glText6 = new GLText("GLText6");
        glText6.text(
                "Font1", "48    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 48, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText6.getTextHeight("A");
        glText7 = new GLText("GLText7");
        glText7.text(
                "Font1", "60    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 60, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText7.getTextHeight("A");
        glText8 = new GLText("GLText8");
        glText8.text(
                "Font1", "72    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 72, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText1.getTextHeight("A") * 3;
        glText9 = new GLText("GLText9");
        glText9.text(
                "Font1",
                TextFormatType.BLACK + "BLACK  " +
                        TextFormatType.DARK_BLUE + "DARK_BLUE  " +
                        TextFormatType.DARK_GREEN + "DARK_GREEN  " +
                        TextFormatType.DARK_AQUA + "DARK_AQUA  " +
                        TextFormatType.DARK_RED + "DARK_RED  " +
                        TextFormatType.DARK_PURPLE + "DARK_PURPLE  " +
                        TextFormatType.GOLD + "GOLD  " +
                        TextFormatType.GRAY + "GRAY  " + TextFormatType.NEWLINE +
                        TextFormatType.DARK_GRAY + "DARK_GRAY  " +
                        TextFormatType.BLUE + "BLUE  " +
                        TextFormatType.GREEN + "GREEN  " +
                        TextFormatType.AQUA + "AQUA  " +
                        TextFormatType.RED + "RED  " +
                        TextFormatType.LIGHT_PURPLE + "LIGHT_PURPLE  " +
                        TextFormatType.YELLOW + "YELLOW  " +
                        TextFormatType.WHITE + "WHITE  " +
                        TextFormatType.DEFAULT_COLOR + "DEFAULT_COLOR" + TextFormatType.NEWLINE +
                        "OBFUSCATED: " + TextFormatType.OBFUSCATED + "OBFUSCATED" + TextFormatType.RESET + TextFormatType.NEWLINE +
                        "BOLD: " + TextFormatType.BOLD + "BOLD" + TextFormatType.RESET +
                        "  STRIKETHROUGH: " + TextFormatType.STRIKETHROUGH + "STRIKETHROUGH" + TextFormatType.RESET +
                        "  UNDERLINE: " + TextFormatType.UNDERLINE + "UnderLine" + TextFormatType.RESET +
                        "  ITALIC: " + TextFormatType.ITALIC + "Italic" + TextFormatType.RESET +
                        "  GHOST: " + TextFormatType.GHOST + "Ghost" + TextFormatType.RESET + TextFormatType.NEWLINE +
                        "HIDE_BOX: " + TextFormatType.HIDE_BOX + "HideBox" + TextFormatType.RESET +
                        "  SHADOW: " + TextFormatType.SHADOW + "Shadow" + TextFormatType.RESET +
                        "  OUTLINE: " + TextFormatType.OUTLINE + "OutLine" + TextFormatType.RESET +
                        "  DOUBLE_UNDERLINE: " + TextFormatType.DOUBLE_UNDERLINE + "DoubleUnderLine" + TextFormatType.RESET + TextFormatType.NEWLINE +
                        "DOUBLE_STRIKETHROUGH: " + TextFormatType.DOUBLE_STRIKETHROUGH + "DoubleSTRIKETHROUGH" + TextFormatType.RESET +
                        "  OVERLINE: " + TextFormatType.OVERLINE + "OverLine" + TextFormatType.RESET,
                20, offsetY, 30, Color.BLACK, true, AlignType.LEFT_TOP
        );
    }

    @Override
    public void drawFrame() {
        glBackGround.draw();
        glText1.draw();
        glText2.draw();
        glText3.draw();
        glText4.draw();
        glText5.draw();
        glText6.draw();
        glText7.draw();
        glText8.draw();
        glText9.draw();
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
