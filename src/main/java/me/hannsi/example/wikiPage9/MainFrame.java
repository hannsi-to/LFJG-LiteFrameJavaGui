package me.hannsi.example.wikiPage9;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.UnicodeBlocks;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.text.GLText;
import me.hannsi.lfjg.render.system.text.AlignType;
import me.hannsi.lfjg.render.system.text.font.Font;

import static me.hannsi.lfjg.render.LFJGRenderTextContext.FONT_CACHE;

public class MainFrame implements LFJGFrame {
    GLText glText;
    private Frame frame;

    public static void main(String[] args) {
        new MainFrame().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        FONT_CACHE.createCache(
                "Font1",
                Font.createCustomFont()
                        .name("Font1")
                        .trueTypeFontPath(Location.fromFile("C:\\Windows\\Fonts\\Arial.ttf"))
                        .unicodeBlocks(UnicodeBlocks.getBlocks("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.:,;'\"(!?)+-*/="))
                        .createCache()
        );

        glText = new GLText("GLText1");
        glText.text(
                "Font1",
                "Hello World!!",
                500, 500,
                64,
                Color.WHITE,
                true,
                AlignType.LEFT_BASELINE
        );
    }

    @Override
    public void drawFrame() {
        glText.draw();
    }

    @Override
    public void stopFrame() {
        glText.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "MainFrame");
    }
}