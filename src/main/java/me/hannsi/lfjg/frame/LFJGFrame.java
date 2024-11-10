package me.hannsi.lfjg.frame;

public interface LFJGFrame {
    void init();

    void drawFrame(long nvg);

    void stopFrame();

    void setFrameSetting();
}