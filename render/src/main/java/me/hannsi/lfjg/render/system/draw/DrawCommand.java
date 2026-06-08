package me.hannsi.lfjg.render.system.draw;

public interface DrawCommand {
    void drawCall();

    void put(long address);
}
