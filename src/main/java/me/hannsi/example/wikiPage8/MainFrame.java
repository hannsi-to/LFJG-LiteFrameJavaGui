package me.hannsi.example.wikiPage8;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.system.scene.SceneSystem;

public class MainFrame implements LFJGFrame {
    SceneSystem sceneSystem;
    Frame frame;

    public static void main(String[] args) {
        new MainFrame().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        sceneSystem = SceneSystem.initSceneSystem()
                .addScene(new Scene1(frame).getScene())
                .addScene(new Scene2(frame).getScene())
                .setCurrentScene("Scene1")
                .initScenes();
    }

    @Override
    public void drawFrame() {
        sceneSystem.drawFrameScenes();
    }

    @Override
    public void stopFrame() {
        sceneSystem.stopFrameScenes();
        sceneSystem.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "MainFrame");
    }
}
