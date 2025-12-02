package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Mesh;

import static me.hannsi.lfjg.render.LFJGRenderContext.GL_STATE_CACHE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL40.glDrawArraysIndirect;
import static org.lwjgl.opengl.GL40.glDrawElementsIndirect;
import static org.lwjgl.opengl.GL43.glMultiDrawArraysIndirect;
import static org.lwjgl.opengl.GL43.glMultiDrawElementsIndirect;

public class VAORendering {
    public void draw(GLObject glObject) {
//        draw(glObject.getMesh(), glObject.getDrawType().getId());
    }

    public void draw(Mesh mesh) {
        draw(mesh, GL_POLYGON);
    }

    public void draw(Mesh mesh, int drawType) {
        int vaoId = mesh.getVaoId();

//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
//        GL11.glLineWidth(0.1f);

        GL_STATE_CACHE.bindVertexArray(vaoId);

        mesh.startFrame();

        if (mesh.isUseIndirect()) {
            drawIndirect(mesh, drawType);
        } else {
            drawDirect(mesh, drawType);
        }

        mesh.endFrame();
    }

    private void drawIndirect(Mesh mesh, int drawType) {
        int indirectBufferId = mesh.getIboId().getBufferId();
        GL_STATE_CACHE.bindIndirectBuffer(indirectBufferId);

        if (mesh.isUseElementBufferObject()) {
            int eboId = mesh.getEboId().getBufferId();
            GL_STATE_CACHE.bindElementArrayBuffer(eboId);

            int commandCount = mesh.getDrawCommandCount();
            if (commandCount > 1) {
                glMultiDrawElementsIndirect(drawType, GL_UNSIGNED_INT, 0, commandCount, 0);
            } else {
                glDrawElementsIndirect(drawType, GL_UNSIGNED_INT, 0);
            }
        } else {
            int commandCount = mesh.getDrawCommandCount();
            if (commandCount > 1) {
                glMultiDrawArraysIndirect(drawType, 0, commandCount, 0);
            } else {
                glDrawArraysIndirect(drawType, 0);
            }
        }
    }

    private void drawDirect(Mesh mesh, int drawType) {
        if (mesh.isUseElementBufferObject()) {
            int eboId = mesh.getEboId().getBufferId();
            GL_STATE_CACHE.bindElementArrayBuffer(eboId);
            glDrawElements(drawType, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
        } else {
            glDrawArrays(drawType, 0, mesh.getCount());
        }
    }

    public void cleanup() {
        new LogGenerator(LogGenerateType.CLEANUP, getClass(), hashCode(), "")
                .logging(getClass(), DebugLevel.DEBUG);
    }
}