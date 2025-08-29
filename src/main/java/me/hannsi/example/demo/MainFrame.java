package me.hannsi.example.demo;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.system.scene.SceneSystem;

import static me.hannsi.lfjg.frame.LFJGContext.frame;

public class MainFrame implements LFJGFrame {
    SceneSystem sceneSystem;

    public static void main(String[] args) {
        LFJGContext.args = args;
        new MainFrame().setFrame();
    }

    public void setFrame() {
        frame = new Frame(this, "TestGuiFrame");
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        sceneSystem = SceneSystem.initSceneSystem()
                .addScene(new Demo1(frame).getScene())
                .addScene(new Demo2(frame).getScene())
                .addScene(new Demo3(frame).getScene())
                .addScene(new Demo4(frame).getScene())
                .setCurrentScene("Demo4")
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
}
