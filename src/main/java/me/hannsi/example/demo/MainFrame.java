package me.hannsi.example.demo;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGFrameContext;
import me.hannsi.lfjg.frame.setting.settings.CheckSeveritiesSetting;
import me.hannsi.lfjg.frame.setting.settings.RefreshRateSetting;
import me.hannsi.lfjg.frame.setting.settings.SeverityType;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.system.scene.SceneSystem;

import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;

public class MainFrame implements LFJGFrame {
    SceneSystem sceneSystem;

    public static void main(String[] args) {
        LFJGFrameContext.args = args;
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
                .addScene(new Demo5(frame).getScene())
                .setCurrentScene("Demo1")
                .initScenes();
    }

    @Override
    public void drawFrame() {
        sceneSystem.drawFrameScenes();

        System.out.println(frame.getFps());
    }

    @Override
    public void stopFrame() {
        sceneSystem.stopFrameScenes();
        sceneSystem.cleanup();
    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
        frame.setFrameSettingValue(CheckSeveritiesSetting.class, new SeverityType[]{SeverityType.LOW, SeverityType.MEDIUM, SeverityType.HIGH});
    }
}
