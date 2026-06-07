package me.hannsi.lfjg.render.system.draw;

import static me.hannsi.lfjg.render.util.GLWrapper.glDrawArrays;
import static me.hannsi.lfjg.render.util.GLWrapper.glDrawArraysIndirect;

public class DrawArraysIndirectCommand implements DrawCommand {
    public int count;
    public int instanceCount;
    public int first;
    public int baseInstance;


    @Override
    public void drawCall() {
        glDrawArraysIndirect();
    }
}
