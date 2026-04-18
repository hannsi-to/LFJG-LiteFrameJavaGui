package me.hannsi.lfjg.testRender.system.scene;

public interface IScene {
    void init();

    void drawFrame();

    void stopFrame();

    Scene getScene();
}
