package me.hannsi.lfjg.util.type.types;

import me.hannsi.lfjg.util.type.system.IEnumTypeBase;
import org.lwjgl.opengl.GL11;

public enum DrawType implements IEnumTypeBase {
    POINTS(GL11.GL_POINTS, "Points", 1), LINES(GL11.GL_LINES, "Lines", -1), LINE_STRIP(GL11.GL_LINE_STRIP, "LineStrip", -1), LINE_LOOP(GL11.GL_LINE_LOOP, "LineLoop", -1), TRIANGLES(GL11.GL_TRIANGLES, "Triangles", 3), TRIANGLE_STRIP(GL11.GL_TRIANGLE_STRIP, "TriangleStrip", 3), TRIANGLE_FAN(GL11.GL_TRIANGLE_FAN, "TriangleFan", 3), QUADS(GL11.GL_QUADS, "Quads", 4), QUAD_STRIP(GL11.GL_QUAD_STRIP, "QuadStrip", 4), POLYGON(GL11.GL_POLYGON, "Polygon", -1);

    final int id;
    final String name;
    final int vertices;

    DrawType(int id, String name, int vertices) {
        this.id = id;
        this.name = name;
        this.vertices = vertices;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getVertices() {
        return vertices;
    }
}
