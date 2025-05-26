package me.hannsi.test.test1;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.frame.frame.LFJGFrame;
import me.hannsi.lfjg.render.openGL.effect.effects.DrawObject;
import me.hannsi.lfjg.render.openGL.effect.effects.Rotate;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.rendering.GLObjectCache;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.math.MathHelper;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Vector2i;

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
        LFJGContext.projection = new Projection(ProjectionType.ORTHOGRAPHIC_PROJECTION, frame.getFrameBufferWidth(), frame.getFrameBufferHeight());
        LFJGContext.frameBufferSize = new Vector2i(frame.getFrameBufferWidth(), frame.getFrameBufferHeight());

        glRect1 = new GLRect("Rect1");
        glRect1.rect(500, 500, 1000, 1000, Color.RED);

        glRect1EffectCache = new EffectCache();
        glRect1EffectCache.createCache("DrawObject1", new DrawObject());
        glRect1EffectCache.createCache("Rotate1", new Rotate(0, 0, MathHelper.toRadians(45), 500, 500));

        glRect1.setEffectCache(glRect1EffectCache);

        glObjectCache = new GLObjectCache();
        glObjectCache.createCache(glRect1);
    }

    @Override
    public void drawFrame() {
        Rotate rotate1 = (Rotate) glRect1EffectCache.getEffectBase("Rotate1");
        rotate1.setZ(rotate1.getZ() + MathHelper.toRadians(10));

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