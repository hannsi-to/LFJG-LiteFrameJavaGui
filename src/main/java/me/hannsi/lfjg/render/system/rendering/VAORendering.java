package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL43.glMultiDrawArraysIndirect;
import static org.lwjgl.opengl.GL43.glMultiDrawElementsIndirect;

/**
 * Handles rendering of VAOs (Vertex Array Objects) in the OpenGL rendering system.
 */
public class VAORendering {
    /**
     * Draws the specified GLObject using its associated VAO.
     *
     * @param glObject the GLObject to draw
     */
    public void draw(GLObject glObject) {
        drawMesh(glObject.getMesh(), glObject.getDrawType().getId());
    }

    /**
     * Draws the specified Mesh using its associated VAO.
     *
     * @param mesh the Mesh to draw;
     */
    public void draw(Mesh mesh) {
        drawMesh(mesh, GL30.GL_POLYGON);
    }

    public void drawMesh(Mesh mesh, int drawType) {
        glBindVertexArray(mesh.getVaoId());
        try {
            mesh.startFrame();
            if (mesh.isUseIndirect()) {
                drawIndirect(mesh, drawType);
            } else {
                drawDirect(mesh, drawType);
            }
            mesh.endFrame();
        } finally {
            glBindVertexArray(0);
        }
    }

    private void drawIndirect(Mesh mesh, int drawType) {
        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, mesh.getIboId().getBufferId());

        if (mesh.isUseElementBufferObject()) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getEboId().getBufferId());

            if (mesh.getDrawCommandCount() > 1) {
                glMultiDrawElementsIndirect(drawType, GL_UNSIGNED_INT, 0, mesh.getDrawCommandCount(), 0);
            } else {
                glDrawElementsIndirect(drawType, GL_UNSIGNED_INT, 0);
            }

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        } else {
            if (mesh.getDrawCommandCount() > 1) {
                glMultiDrawArraysIndirect(drawType, 0, mesh.getDrawCommandCount(), 0);
            } else {
                glDrawArraysIndirect(drawType, 0);
            }
        }

        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, 0);
    }

    private void drawDirect(Mesh mesh, int drawType) {
        if (mesh.isUseElementBufferObject()) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getEboId().getBufferId());
            glDrawElements(drawType, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        } else {
            glDrawArrays(drawType, 0, mesh.getCount());
        }
    }

    public void cleanup() {
        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                hashCode(), ""
        ).logging(DebugLevel.DEBUG);
    }
}