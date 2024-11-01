package me.hannsi.lfjg.frame;

public interface LFJGFrame {
    default void drawFrameWithOpenGL() {
    }

    default void drawFrameWithNanoVG(long nvg) {
    }

    void init();

    void keyPress(int key, int scancode, int mods, long window);

    void keyReleased(int key, int scancode, int mods, long window);

    void cursorPos(double xpos, double ypos, long window);

    void mouseButtonPress(int button, int mods, long window);

    void mouseButtonReleased(int button, int mods, long window);

    void setFrameSetting();
}

