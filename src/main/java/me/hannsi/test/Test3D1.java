package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;

public class Test3D1 implements IScene {
    Scene scene;
    Frame frame;
    TestGuiFrame testGuiFrame;

    public Test3D1(Frame frame, TestGuiFrame testGuiFrame) {
        this.scene = new Scene("Test3D1", this);
        this.frame = frame;
        this.testGuiFrame = testGuiFrame;
    }

    @Override
    public void init() {

    }

    @Override
    public void drawFrame() {

    }

    @Override
    public void stopFrame() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
