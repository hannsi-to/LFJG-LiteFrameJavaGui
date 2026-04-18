package me.hannsi.lfjg.testRender.system.rendering;

import java.util.ArrayList;
import java.util.List;

public class DrawBatch {
    protected int index;
    protected Pass currentPass;
    private final List<Pass> passes;

    public DrawBatch() {
        this.passes = new ArrayList<>();
    }

    public void addCommand(int commandOffset, Pipeline pipeline) {
        if (passes.isEmpty() || !passes.getLast().pipeline.equals(pipeline)) {
            passes.add(new Pass(commandOffset, pipeline));
        }

        passes.getLast().commandCount++;
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
        index = 0;
        currentPass = null;
        return this;
    }

    public List<Pass> getPasses() {
        return passes;
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
