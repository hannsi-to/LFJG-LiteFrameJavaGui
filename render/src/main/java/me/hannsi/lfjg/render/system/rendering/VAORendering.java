package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.system.mesh.DrawElementsIndirectCommand;
import me.hannsi.lfjg.render.system.mesh.TestMesh;

import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static me.hannsi.lfjg.render.RenderSystemSetting.VAO_RENDERING_FRONT_AND_BACK;
import static me.hannsi.lfjg.render.RenderSystemSetting.VAO_RENDERING_FRONT_AND_BACK_LINE_WIDTH;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.glMultiDrawElementsIndirect;

public class VAORendering {
    public void push() {

    }

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

        for (Map.Entry<Integer, TestMesh.Builder> entry : glObjectPool.getObjects().entrySet()) {
            TestMesh.Builder builder = entry.getValue();

            long base = builder.getBaseCommand() * DrawElementsIndirectCommand.BYTES;
            int count = builder.getInstanceData().drawElementsIndirectCommand.count;
            if (!builder.isDraw()) {
                count = 0;
            }
            persistentMappedIBO.update(base, 0, count);

            if (builder.getInstanceData().isDirtyFrag()) {
                mesh.updateInstanceData(entry.getKey(), builder.getInstanceData());

                builder.getInstanceData().resetDirtyFlag();
            }
        }
        
        persistentMappedVBO.syncToGPU();
        persistentMappedEBO.syncToGPU();
        persistentMappedIBO.syncToGPU();
        persistentMappedSSBO.syncToGPU();
        persistentMappedPUBO.syncToGPU();

        glMultiDrawElementsIndirect(
                DrawType.TRIANGLES.getId(),
                GL_UNSIGNED_INT,
                0,
                mesh.getCommandCount(),
                0
        );
    }

    public void pop() {

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
}