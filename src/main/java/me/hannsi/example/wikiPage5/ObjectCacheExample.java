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

        glRect1 = new GLRect("Rect1");
        glRect1.rect(0, 0, 100, 100, Color.RED);

        glRect2 = new GLRect("Rect2");
        glRect2.rect(100, 100, 200, 200, Color.BLUE);

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