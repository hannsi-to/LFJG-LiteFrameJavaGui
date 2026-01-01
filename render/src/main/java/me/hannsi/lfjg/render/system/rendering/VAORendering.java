package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.render.system.mesh.GLObjectData;

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

        for (Map.Entry<Integer, GLObjectData> entry : glObjectPool.getObjects().entrySet()) {
            GLObjectData glObjectData = entry.getValue();

            long base = persistentMappedIBO.getCommandsSizeByte(glObjectData.baseCommand);
            if (glObjectData.draw) {
                persistentMappedIBO.directWriteCommand(base, 0, glObjectData.elementPair.indices.length);
            } else {
                persistentMappedIBO.directWriteCommand(base, 0, 0);
            }

            if (glObjectData.builder.getInstanceData().isDirtyFrag()) {
                mesh.updateInstanceData(entry.getKey(), glObjectData.builder.getInstanceData());

                glObjectData.builder.getInstanceData().resetDirtyFlag();
            }
        }

        glStateCache.bindVertexArray(mesh.getVaoId());
        glStateCache.bindElementArrayBuffer(persistentMappedEBO.getBufferId());
        glStateCache.bindIndirectBuffer(persistentMappedIBO.getBufferId());

        persistentMappedVBO.syncToGPU();
        persistentMappedEBO.syncToGPU();
        persistentMappedIBO.syncToGPU();
        persistentMappedSSBO.syncToGPU();
        persistentMappedPBO.syncToGPU();

        glMultiDrawElementsIndirect(
                DrawType.TRIANGLES.getId(),
                GL_UNSIGNED_INT,
                0,
                persistentMappedIBO.getCommandCount(),
                0
        );
    }

    public void pop() {

    }
}