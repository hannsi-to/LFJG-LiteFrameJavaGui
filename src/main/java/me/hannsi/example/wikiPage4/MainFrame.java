package me.hannsi.example.wikiPage4;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.renderers.font.AlignType;
import me.hannsi.lfjg.render.renderers.font.GLText;
import me.hannsi.lfjg.render.system.font.Font;
import me.hannsi.lfjg.render.system.font.FontCache;

public class MainFrame implements LFJGFrame {
    GLText glText;
    private Frame frame;

    public static void main(String[] args) {
        new MainFrame().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        LFJGRenderContext.fontCache = FontCache.initFontCache()
                .createCache(new Font("Font1", Location.fromResource("font/default.ttf")))
                .loadFonts();
        
        glText = new GLText("Text1");
        glText.text("Font1", "Hello World!!", 450, 450, 64f, Color.of(255, 0, 255, 255), AlignType.LEFT_BOTTOM);
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