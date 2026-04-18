package me.hannsi.lfjg.testRender.system.rendering;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static org.lwjgl.opengl.GL11.*;

public enum DrawType implements IEnumTypeBase {
    POINTS(GL_POINTS, "Points"),
    LINES(GL_LINES, "Lines"),
    LINE_STRIP(GL_LINE_STRIP, "LineStrip"),
    LINE_LOOP(GL_LINE_LOOP, "LineLoop"),
    TRIANGLES(GL_TRIANGLES, "Triangles"),
    TRIANGLE_STRIP(GL_TRIANGLE_STRIP, "TriangleStrip"),
    TRIANGLE_FAN(GL_TRIANGLE_FAN, "TriangleFan"),
    QUADS(GL_TRIANGLES, "Quads"),
    POLYGON(GL_TRIANGLES, "Polygon");

    final int id;
    final String name;

    DrawType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}