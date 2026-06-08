package me.hannsi.lfjg.render.system.draw;

public class DrawArraysIndirectCommand implements DrawCommand {
    public static final int BYTES = Integer.BYTES * 4;

    public int count;
    public int instanceCount;
    public int first;
    public int baseInstance;

    @Override
    public void drawCall() {
        glDrawArraysIndirect();
    }

    @Override
    public void put(long address) {

    }
}
