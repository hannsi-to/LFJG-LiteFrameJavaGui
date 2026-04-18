package me.hannsi.lfjg.render.system.batching;

import java.util.Objects;

public record DrawSortKey(
        RenderQueue renderQueue,
        UILayer uiLayer,
        int scissorId,
        boolean blend,
        int srcRGB,
        int dstRGB,
        int srcA,
        int dstA,
        int eqRGB,
        int eqA,
        boolean depthWrite,
        boolean depthTest,
        int shaderId,
        int textureId,
        int drawOrder
) {
    public DrawSortKey {
        Objects.requireNonNull(renderQueue, "renderQueue");
        Objects.requireNonNull(uiLayer, "uiLayer");
    }

    public boolean hasSameBatchState(DrawSortKey other) {
        return scissorId == other.scissorId
                && blend == other.blend
                && srcRGB == other.srcRGB
                && dstRGB == other.dstRGB
                && srcA == other.srcA
                && dstA == other.dstA
                && eqRGB == other.eqRGB
                && eqA == other.eqA
                && depthWrite == other.depthWrite
                && depthTest == other.depthTest
                && shaderId == other.shaderId
                && textureId == other.textureId;
    }
}
