package me.hannsi.example.demo;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.frame.setting.settings.RefreshRateSetting;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.system.scene.SceneSystem;

public class MainFrame implements LFJGFrame {
    SceneSystem sceneSystem;

    public static void main(String[] args) {
        LFJGContext.args = args;
        new MainFrame().setFrame();
    }

    public void setFrame() {
        LFJGContext.frame = new Frame(this, "TestGuiFrame");
    }

    @Override
    public void init() {
        LFJGContext.frame.updateLFJGLContext();

        sceneSystem = SceneSystem.initSceneSystem()
                .addScene(new Demo1(LFJGContext.frame).getScene())
                .addScene(new Demo2(LFJGContext.frame).getScene())
                .addScene(new Demo3(LFJGContext.frame).getScene())
                .addScene(new Demo4(LFJGContext.frame).getScene())
                .addScene(new Demo5(LFJGContext.frame).getScene())
                .setCurrentScene("Demo5")
                .initScenes();
    }

    @Override
    public void drawFrame() {
        sceneSystem.drawFrameScenes();

        System.out.println(LFJGContext.frame.getFps());
    }

    @Override
    public void stopFrame() {
        sceneSystem.stopFrameScenes();
        sceneSystem.cleanup();
    }

    @Override
    public void setFrameSetting() {
        LFJGContext.frame.setFrameSettingValue(RefreshRateSetting.class, -1);
    }
}
