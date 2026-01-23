package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.render.renderers.BlendType;

public class Pipeline {
    private final BlendType blendType;

    public Pipeline(BlendType blendType) {
        this.blendType = blendType;
    }

    public BlendType getBlendType() {
        return blendType;
    }
}
