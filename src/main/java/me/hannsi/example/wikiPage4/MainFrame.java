package me.hannsi.example.wikiPage4;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.system.font.Font;
import me.hannsi.lfjg.render.system.font.FontCache;

public class MainFrame implements LFJGFrame {
    private Frame frame;

    public static void main(String[] args) {
        new MainFrame().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

//        LFJGRenderContext.fontCache = FontCache.initFontCache()
//                .createCache(new Font("Font1", Location.fromResource("font/default.ttf")))
//                .loadFonts();
    }

    @Override
    public void drawFrame() {
    }

    @Override
    public void stopFrame() {
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "MainFrame");
    }
}