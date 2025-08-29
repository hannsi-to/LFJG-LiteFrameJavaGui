package me.hannsi.example.wikiPage8;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;

public class Scene2 implements IScene {
    Frame frame;
    Scene scene;

    public Scene2(Frame frame) {
        this.frame = frame;
        this.scene = new Scene("Scene2", this);
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
