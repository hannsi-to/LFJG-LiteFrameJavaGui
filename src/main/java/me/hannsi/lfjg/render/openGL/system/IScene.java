package me.hannsi.lfjg.render.openGL.system;

public interface IScene {
    void init();

    void drawFrame();

    void stopFrame();

    Scene getScene();
}
