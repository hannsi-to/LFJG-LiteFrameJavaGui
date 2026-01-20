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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pipeline other)) {
            return false;
        }
        return blendType == other.blendType;
    }

    @Override
    public int hashCode() {
        return blendType.hashCode();
    }
}
