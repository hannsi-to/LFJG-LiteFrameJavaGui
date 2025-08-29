package me.hannsi.example.wikiPage7;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.effect.effects.FXAA;
import me.hannsi.lfjg.render.effect.effects.Texture;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;

import static me.hannsi.lfjg.render.LFJGRenderContext.textureCache;

public class EffectFXAA implements LFJGFrame {
    Frame frame;
    GLRect glRect1;
    EffectCache effectCache1;

    public static void main(String[] args) {
        new EffectFXAA().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        textureCache.createCache("Test1", Location.fromResource("texture/test/test1.jpg"));

        glRect1 = new GLRect("GLRect1");
        glRect1.uv(0, 1, 1, 0);
        glRect1.rect(0, 0, frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), Color.of(0, 0, 0, 0));
        effectCache1 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Test1"))
                .createCache(FXAA.createFXAA("FXAA"))
                .attachGLObject(glRect1);
    }

    @Override
    public void drawFrame() {
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
        frame = new Frame(this, "EffectFXAA");
    }
}
