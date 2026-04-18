package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.testRender.system.scene.IScene;
import me.hannsi.lfjg.testRender.system.scene.Scene;

public class TestSound1 implements IScene {
    public Scene scene;
    public Frame frame;

    public TestSound1(Frame frame) {
        this.scene = new Scene("TestSound1", this);
        this.frame = frame;
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
