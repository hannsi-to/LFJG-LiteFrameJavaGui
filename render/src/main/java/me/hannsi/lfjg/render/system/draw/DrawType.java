package me.hannsi.lfjg.render.system.draw;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.GL_PATCHES;

public enum DrawType implements IEnumTypeBase {
    POINTS(GL_POINTS, "Points"),
    LINE_STRIP(GL_LINE_STRIP, "LineStrip"),
    LINE_LOOP(GL_LINE_LOOP, "LineLoop"),
    LINES(GL_LINES, "Lines"),
    LINES_ADJACENCY(GL_LINES_ADJACENCY, "LinesAdjacency"),
    LINE_STRIP_ADJACENCY(GL_LINE_STRIP_ADJACENCY, "LineStripAdjacency"),
    LINE_TRIANGLES_ADJACENCY(GL_TRIANGLES_ADJACENCY, "TrianglesAdjacency"),
    TRIANGLE_STRIP_ADJACENCY(GL_TRIANGLE_STRIP_ADJACENCY, "TriangleStripAdjacency"),
    TRIANGLE_STRIP(GL_TRIANGLE_STRIP, "TriangleStrip"),
    TRIANGLE_FAN(GL_TRIANGLE_FAN, "TriangleFan"),
    TRIANGLES(GL_TRIANGLES, "Triangles"),
    QUADS(GL_QUADS, "Quads"),
    QUADS_STRIP(GL_QUAD_STRIP, "Quads"),
    POLYGON(GL_POLYGON, "Polygon"),
    PATCHES(GL_PATCHES, "Polygon");

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