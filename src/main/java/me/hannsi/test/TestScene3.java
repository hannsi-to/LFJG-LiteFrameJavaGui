package me.hannsi.test;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.render.openGL.system.scene.IScene;
import me.hannsi.lfjg.render.openGL.system.scene.Scene;

public class TestScene3 implements IScene {
    public Scene scene;
    public Frame frame;

    public TestScene3(Frame frame) {
        this.scene = new Scene("TestScene3", this);
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
