package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.system.VAORendering;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VAO;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VBO;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class GLPolygon {
    private Frame frame;
    private DrawType drawType;
    private float lineWidth = -1f;
    private float pointSize = -1f;
    private VBO vboVertex;
    private VBO vboColor;

    public GLPolygon(Frame frame) {
        this.frame = frame;
        this.vboVertex = new VBO(1, 2);
        this.vboColor = new VBO(1, 4);
    }

    public GLPolygon put() {
        return this;
    }

    public GLPolygon vertex(Vector2f vector2f) {
        this.vboVertex.put(vector2f);

        return this;
    }

    public GLPolygon color(Color color) {
        this.vboColor.put(color);

        return this;
    }

    public void end() {
    }

    public GLPolygon rendering() {
        return this;
    }

    public GLPolygon drawType(DrawType drawType) {
        this.drawType = drawType;

        return this;
    }

    public GLPolygon lineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    public GLPolygon pointSize(float pointSize) {
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
