package me.hannsi.lfjg.render.system.rendering;

import java.util.ArrayList;
import java.util.List;

public class DrawBatch {
    protected int index;
    protected Pass currentPass;
    private final List<Pass> passes;

    public DrawBatch() {
        this.passes = new ArrayList<>();
    }

    public DrawBatch addPass(Pass pass) {
        passes.add(pass);

        return this;
    }

    public boolean nextPass() {
        if (index >= passes.size()) {
            index = 0;
            currentPass = null;
            return false;
        }

        currentPass = passes.get(index++);
        return true;
    }

    public Pass getCurrentPass() {
        return currentPass;
    }

    public DrawBatch clear() {
        passes.clear();

        return this;
    }

    public List<Pass> getPasses() {
        return passes;
    }

    public void increment() {
        passes.getLast().commandCount++;
    }

    public static class Pass {
        public final int commandOffset;
        public final Pipeline pipeline;
        public int commandCount;

        public Pass(int commandOffset, Pipeline pipeline) {
            this.commandOffset = commandOffset;
            this.pipeline = pipeline;
            this.commandCount = 0;
        }
    }
}
