package me.hannsi.test;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.frame.setting.settings.TimeSourceSetting;
import me.hannsi.lfjg.physic.PhysicObject;
import me.hannsi.lfjg.physic.PhysicWorld;
import me.hannsi.lfjg.render.openGL.renderers.font.GLFont;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.font.FontCache;
import me.hannsi.lfjg.render.openGL.system.scene.IScene;
import me.hannsi.lfjg.render.openGL.system.scene.Scene;
import me.hannsi.lfjg.utils.graphics.color.Color;
import org.joml.Vector2f;

public class TestScene2 implements IScene {
    public Scene scene;
    public Frame frame;

    PhysicWorld physicWorld;

    GLRect glGround1;
    PhysicObject physicObjectGround1;

    GLRect glRect1;
    PhysicObject physicObjectRect1;

    GLFont glFont1;

    FontCache fontCache;

    public TestScene2(Frame frame) {
        this.scene = new Scene("TestScene2", this);
        this.frame = frame;
    }

    @Override
    public void init() {
        fontCache = new FontCache();
        fontCache.createCache(FontCache.DEFAULT_TEXTURE, 64);

        glGround1 = new GLRect("GLGround1");
        glGround1.rect(0, 0, LFJGContext.resolution.x(), 5, new Color(255, 255, 255, 255));

        glRect1 = new GLRect("GLRect1");
        glRect1.rectWH(0, 0, 1000, 500, new Color(255, 255, 255, 255));

        glFont1 = new GLFont("GLFont1");
        glFont1.setFont(fontCache, FontCache.DEFAULT_TEXTURE, 64);
        glFont1.font("AAAASSSASAS", 1000, 500, 1f, Color.of(255, 255, 255, 255));

        physicWorld = new PhysicWorld();

        physicObjectGround1 = new PhysicObject();
        physicObjectGround1.linkGLObject(glGround1);
        physicObjectGround1.gravity = false;
        physicObjectGround1.move = false;

        physicObjectRect1 = new PhysicObject();
        physicObjectRect1.linkGLObject(glRect1);

        physicWorld.createPhysicObject(physicObjectGround1);
        physicWorld.createPhysicObject(physicObjectRect1);
    }

    @Override
    public void drawFrame() {
        physicWorld.simulation(frame.getFrameSettingValue(TimeSourceSetting.class));

        physicObjectRect1.applyForce(new Vector2f(3f, 0));
        glGround1.draw();
        glRect1.draw();
        glFont1.draw();
    }

    @Override
    public void stopFrame() {
        glRect1.cleanup();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
