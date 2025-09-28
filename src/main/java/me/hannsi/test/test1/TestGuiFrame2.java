package me.hannsi.test.test1;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.math.Projection;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.rendering.GLObjectCache;

public class TestGuiFrame2 implements LFJGFrame {
    GLObjectCache glObjectCache;
    EffectCache glRect1EffectCache;

    GLRect glRect1;

    private Frame frame;

    public static void main(String[] args) {
        new TestGuiFrame2().setFrame();
    }

    public void setFrame() {
        frame = new Frame(this, "TestGuiFrame");
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();
        Core.projection2D = new Projection(ProjectionType.ORTHOGRAPHIC_PROJECTION, frame.getFrameBufferWidth(), frame.getFrameBufferHeight());

//        glRect1 = new GLRect("Rect1");
//        glRect1.rect(500, 500, 1000, 1000, Color.RED);

        glRect1EffectCache = EffectCache.createEffectCache();

        glRect1.setEffectCache(glRect1EffectCache);

        glObjectCache = new GLObjectCache();
        glObjectCache.createCache(glRect1);
    }

    @Override
    public void drawFrame() {
        glObjectCache.draw();
    }

    @Override
    public void stopFrame() {
        glRect1EffectCache.cleanup();
        glObjectCache.cleanup();
    }

    @Override
    public void setFrameSetting() {
    }
}