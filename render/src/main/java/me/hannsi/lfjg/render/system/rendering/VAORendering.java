package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

public class VAORendering {
    public void draw(GLObject glObject) {
        draw(glObject.getMesh(), glObject.getDrawType().getId());
    }

    public void draw(Mesh mesh) {
        draw(mesh, GL11.GL_POLYGON);
    }

    public void draw(Mesh mesh, int drawType) {
        int vaoId = mesh.getVaoId();

//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
//        GL11.glLineWidth(0.1f);

        GLStateCache.bindVertexArray(vaoId);

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
        GLStateCache.bindIndirectBuffer(indirectBufferId);

        if (mesh.isUseElementBufferObject()) {
            int eboId = mesh.getEboId().getBufferId();
            GLStateCache.bindElementArrayBuffer(eboId);

            int commandCount = mesh.getDrawCommandCount();
            if (commandCount > 1) {
                GL43.glMultiDrawElementsIndirect(drawType, GL11.GL_UNSIGNED_INT, 0, commandCount, 0);
            } else {
                GL40.glDrawElementsIndirect(drawType, GL11.GL_UNSIGNED_INT, 0);
            }
        } else {
            int commandCount = mesh.getDrawCommandCount();
            if (commandCount > 1) {
                GL43.glMultiDrawArraysIndirect(drawType, 0, commandCount, 0);
            } else {
                GL40.glDrawArraysIndirect(drawType, 0);
            }
        }
    }

    private void drawDirect(Mesh mesh, int drawType) {
        if (mesh.isUseElementBufferObject()) {
            int eboId = mesh.getEboId().getBufferId();
            GLStateCache.bindElementArrayBuffer(eboId);
            GL11.glDrawElements(drawType, mesh.getNumVertices(), GL11.GL_UNSIGNED_INT, 0);
        } else {
            GL11.glDrawArrays(drawType, 0, mesh.getCount());
        }
    }

    public void cleanup() {
        new LogGenerator(LogGenerateType.CLEANUP, getClass(), hashCode(), "")
                .logging(DebugLevel.DEBUG);
    }
}