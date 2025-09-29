package me.hannsi.example.demo;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;

public class Demo5 implements IScene {
    Frame frame;
    Scene scene;

    public Demo5(Frame frame) {
        this.frame = frame;
        this.scene = new Scene("Demo5", this);
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
