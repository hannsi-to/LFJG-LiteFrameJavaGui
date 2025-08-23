package me.hannsi.lfjg.render.system.scene;

public interface IScene {
    void init();

    void drawFrame();

    void stopFrame();

    Scene getScene();
}
