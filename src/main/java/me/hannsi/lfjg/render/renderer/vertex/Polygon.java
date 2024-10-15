package me.hannsi.lfjg.render.renderer.vertex;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.renderer.bufferObject.VAO;
import me.hannsi.lfjg.render.renderer.bufferObject.VBO;
import me.hannsi.lfjg.render.rendering.VAORendering;
import me.hannsi.lfjg.util.type.types.DrawType;
import me.hannsi.lfjg.util.vertex.Color;
import me.hannsi.lfjg.util.vertex.vector.Vector2f;
import org.lwjgl.opengl.GL11;

public class Polygon {
    private Frame frame;
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

    public void draw(DrawType drawType) {
        VAO vaoVertex = new VAO(vboVertex);
        VAO vaoColor = new VAO(vboColor);

        VAORendering vaoRendering = new VAORendering(frame);

        vaoRendering.setVertex(vaoVertex);
        vaoRendering.setColor(vaoColor);

        GL11.glPushMatrix();
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
