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
            if (mesh.isUseIndirect()) {
                if (mesh.isUseElementBufferObject()) {
                    glBindBuffer(GL_DRAW_INDIRECT_BUFFER, mesh.getIndirectBufferId());
                    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getEboId().getBufferId());
                    glDrawElementsIndirect(drawType, GL_UNSIGNED_INT, 0);
                    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
                    glBindBuffer(GL_DRAW_INDIRECT_BUFFER, 0);
                } else {
                    glBindBuffer(GL_DRAW_INDIRECT_BUFFER, mesh.getIndirectBufferId());
                    glDrawArraysIndirect(drawType, 0);
                    glBindBuffer(GL_DRAW_INDIRECT_BUFFER, 0);
                }
            } else {
                if (mesh.isUseElementBufferObject()) {
                    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getEboId().getBufferId());
                    glDrawElements(drawType, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
                    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
                } else {
                    glDrawArrays(drawType, 0, mesh.getCount());
                }
            }
        } finally {
            glBindVertexArray(0);
        }
    }

    /**
     * Cleans up the resources associated with the current GLObject.
     */
    public void cleanup() {
        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                hashCode(), ""
        ).logging(DebugLevel.DEBUG);
    }
}