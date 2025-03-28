package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.physic.PhysicObject;
import me.hannsi.lfjg.physic.PhysicWorld;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.scene.IScene;
import me.hannsi.lfjg.render.openGL.system.scene.Scene;
import me.hannsi.lfjg.utils.graphics.color.Color;

public class TestScene2 implements IScene {
    public Scene scene;
    public Frame frame;

    PhysicWorld physicWorld;

    GLRect glRect1;
    PhysicObject physicObjectRect1;

    public TestScene2(Frame frame) {
        this.scene = new Scene("TestScene2", this);
        this.frame = frame;
    }

    @Override
    public void init() {
        glRect1 = new GLRect("GLRect1");
        glRect1.rectWH(LFJGContext.resolution.x() / 2f, LFJGContext.resolution.y(), 50, 50, new Color(255, 255, 255, 255));

        physicWorld = new PhysicWorld();

        physicObjectRect1 = new PhysicObject();
        physicObjectRect1.linkGLObject(glRect1);

        physicWorld.createPhysicObject(physicObjectRect1);
    }

    @Override
    public void drawFrame() {
        physicWorld.simulation(frame.getFps());

        glRect1.draw();
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
