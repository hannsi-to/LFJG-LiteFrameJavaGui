package me.hannsi.lfjg.render.renderer.vertex;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.renderer.bufferObject.VAO;
import me.hannsi.lfjg.render.renderer.bufferObject.VBO;
import me.hannsi.lfjg.render.rendering.VAORendering;
import me.hannsi.lfjg.utils.type.types.DrawType;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.math.vertex.vector.Vector2f;
import org.lwjgl.opengl.GL11;

public class Polygon {
    private Frame frame;
    private DrawType drawType;
    private float lineWidth = -1f;
    private float pointSize = -1f;
    private VBO vboVertex;
    private VBO vboColor;

    public Polygon(Frame frame) {
        this.frame = frame;
        this.vboVertex = new VBO(1, 2);
        this.vboColor = new VBO(1, 4);
    }

    public Polygon put() {
        return this;
    }

    public Polygon vertex(Vector2f vector2f) {
        this.vboVertex.put(vector2f);

        return this;
    }

    public Polygon color(Color color) {
        this.vboColor.put(color);

        return this;
    }

    public void end() {
    }

    public Polygon rendering() {
        return this;
    }

    public Polygon drawType(DrawType drawType) {
        this.drawType = drawType;

        return this;
    }

    public Polygon lineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    public Polygon pointSize(float pointSize) {
        this.pointSize = pointSize;
        return this;
    }

    public void draw() {
        VAO vaoVertex = new VAO(vboVertex);
        VAO vaoColor = new VAO(vboColor);

        VAORendering vaoRendering = new VAORendering(frame);

        vaoRendering.setVertex(vaoVertex);
        vaoRendering.setColor(vaoColor);

        GL11.glPushMatrix();
        if (lineWidth != -1f) {
            GL11.glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            GL11.glPointSize(pointSize);
        }

        vaoRendering.drawArrays(drawType);

        GL11.glPopMatrix();
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public VBO getVboVertex() {
        return vboVertex;
    }

    public void setVboVertex(VBO vboVertex) {
        this.vboVertex = vboVertex;
    }

    public VBO getVboColor() {
        return vboColor;
    }

    public void setVboColor(VBO vboColor) {
        this.vboColor = vboColor;
    }
}
