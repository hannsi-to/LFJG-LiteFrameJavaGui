package me.hannsi.lfjg.utils.type.types;

import lombok.Getter;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import org.lwjgl.opengl.GL11;

/**
 * Enumeration representing different types of drawing modes in OpenGL.
 */
public enum DrawType implements IEnumTypeBase {
    POINTS(GL11.GL_POINTS, "Points", 1),
    LINES(GL11.GL_LINES, "Lines", -1),
    LINE_STRIP(GL11.GL_LINE_STRIP, "LineStrip", -1),
    LINE_LOOP(GL11.GL_LINE_LOOP, "LineLoop", -1),
    TRIANGLES(GL11.GL_TRIANGLES, "Triangles", 3),
    TRIANGLE_STRIP(GL11.GL_TRIANGLE_STRIP, "TriangleStrip", 3),
    TRIANGLE_FAN(GL11.GL_TRIANGLE_FAN, "TriangleFan", 3),
    QUADS(GL11.GL_QUADS, "Quads", 4),
    QUAD_STRIP(GL11.GL_QUAD_STRIP, "QuadStrip", 4),
    POLYGON(GL11.GL_POLYGON, "Polygon", -1);

    final int id;
    final String name;
    /**
     * -- GETTER --
     *  Gets the number of vertices required for the drawing mode.
     *
     * @return the number of vertices required for the drawing mode
     */
    @Getter
    final int vertices;

    /**
     * Constructs a new DrawType enumeration value.
     *
     * @param id the unique identifier of the drawing mode
     * @param name the name of the drawing mode
     * @param vertices the number of vertices required for the drawing mode
     */
    DrawType(int id, String name, int vertices) {
        this.id = id;
        this.name = name;
        this.vertices = vertices;
    }

    /**
     * Gets the unique identifier of the drawing mode.
     *
     * @return the unique identifier of the drawing mode
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the drawing mode.
     *
     * @return the name of the drawing mode
     */
    @Override
    public String getName() {
        return name;
    }

}