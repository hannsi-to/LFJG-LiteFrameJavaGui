package me.hannsi.test;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.effect.effects.Texture;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.rendering.GLObjectCache;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;

import static me.hannsi.lfjg.render.LFJGRenderContext.textureCache;

public class TestScene5 implements IScene {
    public Scene scene;

    public GLObjectCache glObjectCache;
    public GLRect glRect;
    public GLRect glRect2;
    public GLRect glRect3;
    public GLRect glRect4;

    public EffectCache effectCache;

    public TestScene5(Frame frame) {
        this.scene = new Scene("TestScene5", this);
    }

    @Override
    public void init() {
//        glRect = new GLRect("glRect");
//        glRect.uv(0, 1f, 1f, 0);
//        glRect.rectWH(0, 0, 100, 100, Color.of(0, 0, 0, 0));
//        glRect2 = new GLRect("glRect2");
//        glRect2.uv(0, 1f, 1f, 0);
//        glRect2.rectWH(110, 0, 100, 100, Color.of(0, 0, 0, 0));
//        glRect3 = new GLRect("glRect3");
//        glRect3.uv(0, 1f, 1f, 0);
//        glRect3.rectWH(220, 0, 100, 100, Color.of(0, 0, 0, 0));
//        glRect4 = new GLRect("glRect4");
//        glRect4.uv(0, 1f, 1f, 0);
//        glRect4.rectWH(330, 0, 100, 100, Color.of(0, 0, 0, 0));

        glObjectCache = GLObjectCache.createGLObjectCache()
                .createCache(glRect)
                .createCache(glRect2)
                .createCache(glRect3)
                .createCache(glRect4);


        textureCache.createCache("Texture1", Location.fromResource("texture/test/test1.jpg"));

        effectCache = EffectCache.createEffectCache()
                .createCache(
                        Texture.createTexture("Texture")
                                .textureName("Texture1")
                )
                .attachGLObjectCache(glObjectCache);
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
    public Scene getScene() {
        return scene;
    }
}