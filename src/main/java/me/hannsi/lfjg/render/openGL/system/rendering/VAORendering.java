package me.hannsi.lfjg.render.openGL.system.rendering;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.Mesh;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.glDrawArrays;
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
        glBindVertexArray(glObject.getMesh().getVaoId());

        int count;
        switch (glObject.getMesh().getProjectionType()) {
            case OrthographicProjection -> {
                count = glObject.getMesh().getPositions().length / 2;
            }
            case PerspectiveProjection -> {
                count = glObject.getMesh().getPositions().length / 3;
            }
            default -> throw new IllegalStateException("Unexpected value: " + glObject.getMesh().getProjectionType());
        }

        glDrawArrays(glObject.getDrawType().getId(), 0, count);

        glBindVertexArray(0);
    }

    /**
     * Draws the specified Mesh using its associated VAO.
     *
     * @param mesh the Mesh to draw
     */
    public void draw(Mesh mesh) {
        glBindVertexArray(mesh.getVaoId());

        int count;
        switch (mesh.getProjectionType()) {
            case OrthographicProjection -> {
                count = mesh.getPositions().length / 2;
            }
            case PerspectiveProjection -> {
                count = mesh.getPositions().length / 3;
            }
            default -> throw new IllegalStateException("Unexpected value: " + mesh.getProjectionType());
        }

        glDrawArrays(GL30.GL_POLYGON, 0, count);

        glBindVertexArray(0);
    }

    /**
     * Cleans up the resources associated with the current GLObject.
     */
    public void cleanup() {
        LogGenerator logGenerator = new LogGenerator("VAORendering", "Source: VAORendering", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: VAORendering cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }
}