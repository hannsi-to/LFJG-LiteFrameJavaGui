package me.hannsi.lfjg.render.openGL.system.rendering;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.Mesh;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

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
     * @param mesh the Mesh to draw
     */
    public void draw(Mesh mesh) {
        drawMesh(mesh, GL30.GL_POLYGON);
    }

    public void drawMesh(Mesh mesh, int drawType) {
        glBindVertexArray(mesh.getVaoId());
        try {
            if (mesh.isUesEBO()) {
                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getEboId());
                glDrawElements(drawType, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
            } else {
                int count = switch (mesh.getProjectionType()) {
                    case OrthographicProjection -> mesh.getPositions().length / 2;
                    case PerspectiveProjection -> mesh.getPositions().length / 3;
                };
                glDrawArrays(drawType, 0, count);
            }
        } finally {
            glBindVertexArray(0);
        }
    }

    /**
     * Cleans up the resources associated with the current GLObject.
     */
    public void cleanup() {
        new LogGenerator("VAORendering", "Source: VAORendering", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: VAORendering cleanup is complete.").logging(DebugLevel.DEBUG);
    }
}