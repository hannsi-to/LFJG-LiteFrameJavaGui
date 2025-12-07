package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.render.system.mesh.GLObjectData;

import java.util.Map;

import static me.hannsi.lfjg.core.SystemSetting.VAO_RENDERING_FRONT_AND_BACK;
import static me.hannsi.lfjg.core.SystemSetting.VAO_RENDERING_FRONT_AND_BACK_LINE_WIDTH;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.glMultiDrawElementsIndirect;

public class VAORendering {
    public void push() {

    }

    public void draw() {
        if (VAO_RENDERING_FRONT_AND_BACK) {
            GL_STATE_CACHE.polygonMode(GL_FRONT_AND_BACK, GL_LINE);
            GL_STATE_CACHE.lineWidth(VAO_RENDERING_FRONT_AND_BACK_LINE_WIDTH);
        } else {
            GL_STATE_CACHE.polygonMode(GL_FRONT_AND_BACK, GL_FILL);
            GL_STATE_CACHE.lineWidth(1.0f);
        }

        for (Map.Entry<Long, GLObjectData> entry : GL_OBJECT_POOL.getObjects().entrySet()) {
            GLObjectData glObjectData = entry.getValue();

            long base = MESH.getPersistentMappedIBO().getCommandsSizeByte(glObjectData.baseCommand);
            if (glObjectData.draw) {
                MESH.getPersistentMappedIBO().directWriteCommand(base, 0, glObjectData.elementPair.indices.length);
            } else {
                MESH.getPersistentMappedIBO().directWriteCommand(base, 0, 0);
            }
        }

        MESH.getPersistentMappedVBO().syncToGPU();
        MESH.getPersistentMappedEBO().syncToGPU();
        MESH.getPersistentMappedIBO().syncToGPU();

        GL_STATE_CACHE.bindVertexArray(MESH.getVaoId());
        GL_STATE_CACHE.bindElementArrayBuffer(MESH.getPersistentMappedEBO().getBufferId());
        GL_STATE_CACHE.bindIndirectBuffer(MESH.getPersistentMappedIBO().getBufferId());

        glMultiDrawElementsIndirect(
                DrawType.TRIANGLES.getId(),
                GL_UNSIGNED_INT,
                0,
                MESH.getPersistentMappedIBO().getCommandCount(),
                0
        );
    }

    public void pop() {

    }
}