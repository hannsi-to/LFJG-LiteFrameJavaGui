package me.hannsi.test;

import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.effect.effects.*;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.scene.IScene;
import me.hannsi.lfjg.render.openGL.system.scene.Scene;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.BlendType;

public class TestScene2 implements IScene {
    public Scene scene;

    TextureCache textureCache;

    GLRect glRect1;
    EffectCache glRect1EffectCache;

    public TestScene2() {
        this.scene = new Scene("TestScene2", this);
    }

    @Override
    public void init() {
        glRect1 = new GLRect("GLRect1");
        glRect1.uv(0, 1, 1, 0);
        glRect1.rect(0, 0, LFJGContext.resolution.x(), LFJGContext.resolution.y(), new Color(0, 0, 0, 0));

        textureCache = new TextureCache();
        ResourcesLocation image1 = new ResourcesLocation("texture/test/test_image_3840x2160.jpg");
        textureCache.createCache(image1);

        glRect1EffectCache = new EffectCache();
        glRect1EffectCache.createCache("Texture1", new Texture(textureCache, image1, BlendType.Normal));
        glRect1EffectCache.createCache("DrawObject1", new DrawObject());
        glRect1EffectCache.createCache("Translate1",new Translate(100,100,0));
        glRect1EffectCache.createCache("Scale1",new Scale(0.5,1,1,true));
        glRect1EffectCache.createCache("Rotate1", new Rotate(0, 0, Math.toRadians(45), true));
        glRect1EffectCache.create(glRect1);
        glRect1.setEffectCache(glRect1EffectCache);
    }

    @Override
    public void drawFrame() {
        glRect1.draw();
    }

    @Override
    public void stopFrame() {
        textureCache.cleanup();
        glRect1.cleanup();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
