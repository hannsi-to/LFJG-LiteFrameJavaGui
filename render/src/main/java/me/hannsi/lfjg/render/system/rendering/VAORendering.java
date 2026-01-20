package me.hannsi.lfjg.render.system.rendering;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static me.hannsi.lfjg.render.RenderSystemSetting.VAO_RENDERING_FRONT_AND_BACK;
import static me.hannsi.lfjg.render.RenderSystemSetting.VAO_RENDERING_FRONT_AND_BACK_LINE_WIDTH;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.glMultiDrawElementsIndirect;

public class VAORendering {
    public void draw() {
        if (VAO_RENDERING_FRONT_AND_BACK) {
            glStateCache.polygonMode(GL_FRONT_AND_BACK, GL_LINE);
            glStateCache.lineWidth(VAO_RENDERING_FRONT_AND_BACK_LINE_WIDTH);
        } else {
            glStateCache.polygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glStateCache.lineWidth(1.0f);
        }

        if (mesh.isNeedRepack()) {
            mesh.build();
            mesh.initBufferObject();
            mesh.setNeedRepack(false);

            persistentMappedSSBO.bindBufferRange();
        }

        mesh.update();

        persistentMappedVBO.syncToGPU();
        persistentMappedEBO.syncToGPU();
        persistentMappedIBO.syncToGPU();
        persistentMappedSSBO.syncToGPU();
        persistentMappedPUBO.syncToGPU();

        for (DrawBatch drawBatch : drawBatches) {
            drawBatch.apply();
            glMultiDrawElementsIndirect(
                    GL_TRIANGLES,
                    GL_UNSIGNED_INT,
                    0,
                    mesh.getCommandCount(),
                    0
            );
        }
    }
}