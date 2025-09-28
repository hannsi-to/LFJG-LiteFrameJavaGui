package me.hannsi.example.wikiPage5;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.renderers.polygon.GLTriangle;
import me.hannsi.lfjg.render.system.rendering.GLObjectCache;

public class ObjectCacheExample implements LFJGFrame {
    GLObjectCache glObjectCache;
    GLRect glRect1;
    GLRect glRect2;
    GLTriangle glTriangle1;
    private Frame frame;

    public static void main(String[] args) {
        new ObjectCacheExample().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        glRect1 = GLRect.createGLRect("Rect1")
                .x1_y1_color1_2p(0, 0, Color.RED)
                .x3_y3_color3_2p(100, 100, Color.RED)
                .fill()
                .update();

        glRect2 = GLRect.createGLRect("Rect2")
                .x1_y1_color1_2p(100, 100, Color.BLUE)
                .x3_y3_color3_2p(200, 200, Color.BLUE)
                .fill()
                .update();

        glTriangle1 = GLTriangle.createGLTriangle("Triangle1")
                .x1_y1_color1(100, 100, Color.GREEN)
                .x2_y2_color2(200, 100, Color.GREEN)
                .x3_y3_color3(150, 200, Color.GREEN)
                .fill();

        glObjectCache = GLObjectCache.createGLObjectCache()
                .createCache(glRect1)
                .createCache(glRect2)
                .createCache(glTriangle1);
    }

    @Override
    public void drawFrame() {
        glObjectCache.draw();
    }

    @Override
    public void stopFrame() {
        glObjectCache.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "ObjectCacheExample");
    }
}