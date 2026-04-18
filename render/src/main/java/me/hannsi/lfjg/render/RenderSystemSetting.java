package me.hannsi.lfjg.render;

import me.hannsi.lfjg.render.system.batching.DrawBatchComparable;
import me.hannsi.lfjg.render.system.batching.DrawSortKey;

public class RenderSystemSetting {
    public static boolean RENDER_DEBUG_THROW_ERROR = true;
    public static DrawBatchComparable<DrawSortKey> DRAW_SORT_KEY_COMPARABLE = (main, other) -> {
        int compare = Integer.compare(main.renderQueue().getOrder(), other.renderQueue().getOrder());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(main.uiLayer().getOrder(), other.uiLayer().getOrder());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(main.drawOrder(), other.drawOrder());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(main.scissorId(), other.scissorId());
        if (compare != 0) {
            return compare;
        }

        compare = Boolean.compare(main.blend(), other.blend());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(main.srcRGB(), other.srcRGB());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(main.dstRGB(), other.dstRGB());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(main.srcA(), other.srcA());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(main.dstA(), other.dstA());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(main.eqRGB(), other.eqRGB());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(main.eqA(), other.eqA());
        if (compare != 0) {
            return compare;
        }

        compare = Boolean.compare(main.depthWrite(), other.depthWrite());
        if (compare != 0) {
            return compare;
        }

        compare = Boolean.compare(main.depthTest(), other.depthTest());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(main.shaderId(), other.shaderId());
        if (compare != 0) {
            return compare;
        }

        return Integer.compare(main.textureId(), other.textureId());
    };

}
