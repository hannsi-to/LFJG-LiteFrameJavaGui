package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static org.lwjgl.opengl.GL11.*;

public enum DrawType implements IEnumTypeBase {
    POINTS(GL_POINTS, "Points", 1),
    LINES(GL_LINES, "Lines", -1),
    LINE_STRIP(GL_LINE_STRIP, "LineStrip", -1),
    LINE_LOOP(GL_LINE_LOOP, "LineLoop", -1),
    TRIANGLES(GL_TRIANGLES, "Triangles", 3),
    TRIANGLE_FAN(GL_TRIANGLE_FAN, "TriangleFan", 3),
    QUADS(GL_TRIANGLES, "Quads", 4),
    POLYGON(GL_TRIANGLES, "Polygon", -1);

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