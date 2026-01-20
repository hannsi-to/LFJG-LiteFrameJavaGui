package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.render.renderers.BlendType;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

public class DrawBatch {
    private Pipeline pipeline;
    private int commandOffset;
    private int commandCount;

    public DrawBatch(Pipeline pipeline, int commandOffset) {
        this.pipeline = pipeline;
        this.commandOffset = commandOffset;
        this.commandCount = 0;
    }

    public void apply() {
        applyBlendState(pipeline.getBlendType());
    }

    private void applyBlendState(BlendType type) {
        if (type.isBlend()) {
            glStateCache.enable(GL_BLEND);
            glStateCache.blendFuncSeparate(type.getSrcRGB(), type.getDstRGB(), type.getSrcA(), type.getDstA());
            glStateCache.blendEquationSeparate(type.getEqRGB(), type.getEqA());
        } else {
            glStateCache.disable(GL_BLEND);
        }

        if (type.isDepthTest()) {
            glStateCache.enable(GL_DEPTH_TEST);
        } else {
            glStateCache.disable(GL_DEPTH_TEST);
        }

        glStateCache.depthMask(type.isDepthWrite());
    }

    public void incrementCommandCount() {
        commandCount++;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public int getCommandOffset() {
        return commandOffset;
    }

    public void setCommandOffset(int commandOffset) {
        this.commandOffset = commandOffset;
    }

    public int getCommandCount() {
        return commandCount;
    }

    public void setCommandCount(int commandCount) {
        this.commandCount = commandCount;
    }
}
