package me.hannsi.lfjg.render.render;

import me.hannsi.lfjg.render.system.DrawElementsIndirectCommand;
import me.hannsi.lfjg.render.system.batching.DrawBatch;
import me.hannsi.lfjg.render.system.batching.DrawSortKey;

import java.util.Objects;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.glMultiDrawElementsIndirect;

public class DrawCaller {
    public static void call(DrawBatch drawBatch) {
        while (drawBatch.nextBatch()) {
            Objects.requireNonNull(drawBatch, "drawBatch");

            DrawBatch.Batch currentBatch = drawBatch.getCurrentBatch();


            applyRenderState(currentBatch.sortKey);
            glMultiDrawElementsIndirect(
                    GL_TRIANGLES,
                    GL_UNSIGNED_INT,
                    (long) currentBatch.commandOffset * DrawElementsIndirectCommand.BYTES,
                    currentBatch.commandCount,
                    0
            );
        }
    }

    private static void applyRenderState(DrawSortKey sortKey) {
        if (sortKey.blend()) {
            glStateCache.enable(GL_BLEND);
            glStateCache.blendFuncSeparate(sortKey.srcRGB(), sortKey.dstRGB(), sortKey.srcA(), sortKey.dstA());
            glStateCache.blendEquationSeparate(sortKey.eqRGB(), sortKey.eqA());
        } else {
            glStateCache.disable(GL_BLEND);
        }

        if (sortKey.depthTest()) {
            glStateCache.enable(GL_DEPTH_TEST);
        } else {
            glStateCache.disable(GL_DEPTH_TEST);
        }

        glStateCache.depthMask(sortKey.depthWrite());
    }
}
