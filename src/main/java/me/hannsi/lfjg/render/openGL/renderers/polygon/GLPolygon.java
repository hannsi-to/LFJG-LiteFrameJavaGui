package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.system.VAORendering;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VAO;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VBO;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GLPolygon {
    private Frame frame;
    private DrawType drawType;
    private float lineWidth = -1f;
    private float pointSize = -1f;
    private VBO vboVertex;
    private VBO vboColor;
    private VAO vao;
    private VAORendering vaoRendering;
    private ResourcesLocation fragmentShader;
    private List<EffectBase> effectBaseList;

    public GLPolygon(Frame frame) {
        this.frame = frame;
        this.vboVertex = new VBO(0, 1, 2);
        this.vboColor = new VBO(1, 1, 4);
        this.effectBaseList = new ArrayList<>();
        this.vaoRendering = new VAORendering(frame);
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
        vboVertex.flip();
        vboColor.flip();

        vao = new VAO();

        vao.setVertexVBO(vboVertex);
        vao.setColorVBO(vboColor);

        vao.bindVertexArray();
        vao.configureAttributes();
        vao.unBindVertexArray();

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

    public void addEffect(EffectBase effectBase) {
        this.effectBaseList.add(effectBase);
    }

    public void cleanUp() {
        vaoRendering.cleanUp();
    }

    public void init() {
        vaoRendering.init();
    }

    public void draw() {
        GL11.glPushMatrix();

        vaoRendering.setVao(vao);

        if (lineWidth != -1f) {
            GL11.glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            GL11.glPointSize(pointSize);
        }

        for (EffectBase effectBase : effectBaseList) {
            effectBase.push(frame, this);
        }

        vaoRendering.draw();

        for (EffectBase effectBase : effectBaseList) {
            effectBase.pop(frame, this);
        }

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

    public DrawType getDrawType() {
        return drawType;
    }

    public void setDrawType(DrawType drawType) {
        this.drawType = drawType;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public float getPointSize() {
        return pointSize;
    }

    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
    }

    public List<EffectBase> getEffectBaseList() {
        return effectBaseList;
    }

    public void setEffectBaseList(List<EffectBase> effectBaseList) {
        this.effectBaseList = effectBaseList;
    }

    public VAORendering getVaoRendering() {
        return vaoRendering;
    }

    public void setVaoRendering(VAORendering vaoRendering) {
        this.vaoRendering = vaoRendering;
    }

    public ResourcesLocation getFragmentShader() {
        return fragmentShader;
    }

    public void setFragmentShader(ResourcesLocation fragmentShader) {
        this.fragmentShader = fragmentShader;
    }

    public VAO getVao() {
        return vao;
    }

    public void setVao(VAO vao) {
        this.vao = vao;
    }
}
