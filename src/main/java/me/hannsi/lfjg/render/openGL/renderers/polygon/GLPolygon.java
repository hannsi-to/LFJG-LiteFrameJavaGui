package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.shader.ShaderUtil;
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
    private VAO vaoVertex;
    private VAO vaoColor;
    private VAORendering vaoRendering;
    private ResourcesLocation fragmentShader;
    private ShaderUtil shaderUtil;
    private List<EffectBase> effectBaseList;

    public GLPolygon(Frame frame) {
        this.frame = frame;
        this.vboVertex = new VBO(1, 2);
        this.vboColor = new VBO(1, 4);
        this.effectBaseList = new ArrayList<>();
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

    public void addEffect(EffectBase effectBase) {
        this.effectBaseList.add(effectBase);
    }

    public void draw() {
        vaoVertex = new VAO(vboVertex);
        vaoColor = new VAO(vboColor);

        vaoRendering = new VAORendering(frame);

        vaoRendering.setVertex(vaoVertex);
        vaoRendering.setColor(vaoColor);

        GL11.glPushMatrix();

        if (lineWidth != -1f) {
            GL11.glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            GL11.glPointSize(pointSize);
        }

        for (EffectBase effectBase : effectBaseList) {
            effectBase.push(frame, this);
        }

        shaderUtil = new ShaderUtil(new ResourcesLocation("shader/FragmentShader.fsh"));
        shaderUtil.getGlslSandboxShader().useShader();
        vaoRendering.drawArrays(drawType,shaderUtil.getGlslSandboxShader().getProgramId());
        shaderUtil.getGlslSandboxShader().finishShader();

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

    public VAO getVaoVertex() {
        return vaoVertex;
    }

    public void setVaoVertex(VAO vaoVertex) {
        this.vaoVertex = vaoVertex;
    }

    public VAO getVaoColor() {
        return vaoColor;
    }

    public void setVaoColor(VAO vaoColor) {
        this.vaoColor = vaoColor;
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

    public ShaderUtil getShaderUtil() {
        return shaderUtil;
    }

    public void setShaderUtil(ShaderUtil shaderUtil) {
        this.shaderUtil = shaderUtil;
    }
}
