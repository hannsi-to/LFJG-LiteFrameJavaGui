package me.hannsi.example.wikiPage6;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.effect.effects.Rotate;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;

public class MainFrame implements LFJGFrame {
    Frame frame;
    GLRect glRect1;
    EffectCache effectCache1;

    public static void main(String[] args) {
        new MainFrame().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        glRect1 = new GLRect("Rect1");
        glRect1.rect(500, 500, 1000, 1000, Color.RED);
        effectCache1 = EffectCache.createEffectCache()
                .createCache(Rotate.createRotate("Rotate1").zDegree(45).cx(500).cy(500))
                .attachGLObject(glRect1);
    }

    @Override
    public void drawFrame() {
        Rotate rotate1 = ((Rotate) effectCache1.getEffectBase("Rotate1"));
        rotate1.zRadian(rotate1.getZ() + MathHelper.toRadians(10));
        glRect1.draw();
    }

    @Override
    public void stopFrame() {
        glRect1.cleanup();
        effectCache1.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "MainFrame");
    }
}
