package me.hannsi.lfjg.render.openGL.renderers;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.render.openGL.effect.shader.ShaderProgram;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.system.Mesh;
import me.hannsi.lfjg.render.openGL.system.UniformDatum;
import me.hannsi.lfjg.render.openGL.system.VAORendering;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.BlendType;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class GLObject {
    private final String name;

    private VAORendering vaoRendering;
    private Mesh mesh;

    private List<UniformDatum<?>> uniformData;
    private ShaderProgram shaderProgram;
    private ResourcesLocation vertexShader;
    private ResourcesLocation fragmentShader;

    private Matrix4f modelMatrix;
    private Matrix4f projectionMatrix;

    private List<EffectBase> effectBases;
    private BlendType blendType;
    private DrawType drawType;
    private float lineWidth;
    private float pointSize;

    public GLObject(String name) {
        this.name = name;

        this.vaoRendering = new VAORendering();
        try {
            this.shaderProgram = new ShaderProgram();
        } catch (Exception e) {
            DebugLog.debug(getClass(), e);
        }
        this.uniformData = new ArrayList<>();
        this.lineWidth = -1f;
        this.pointSize = -1f;
        this.vertexShader = null;
        this.fragmentShader = null;
        this.modelMatrix = null;
        this.projectionMatrix = null;
        this.effectBases = new ArrayList<>();
        this.blendType = null;
        this.drawType = null;
        this.mesh = null;
    }

    public <T> void addUniform(String name, T value) {
        uniformData.add(new UniformDatum<>(name, value));
    }

    public void create() {
        try {
            shaderProgram.createVertexShader(vertexShader);
            shaderProgram.createFragmentShader(fragmentShader);
            shaderProgram.link();

            addUniform("projectionMatrix", projectionMatrix);
        } catch (Exception e) {
            DebugLog.debug(getClass(), e);
        }
    }

    public void draw() {
        GL30.glPushMatrix();

        if (lineWidth != -1f) {
            GL30.glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            GL30.glPointSize(pointSize);
        }

        for (EffectBase effectBase : effectBases) {
            effectBase.push(this);
        }

        shaderProgram.bind();

        for (UniformDatum<?> uniformDatum : uniformData) {
            String name = uniformDatum.getName();
            Object value = uniformDatum.getValue();

            if (value instanceof Float) {
                shaderProgram.setUniform1f(name, (float) value);
            }
            if (value instanceof Integer) {
                shaderProgram.setUniform1i(name, (int) value);
            }
            if (value instanceof Matrix4f) {
                shaderProgram.setUniformMatrix4fv(name, (Matrix4f) value);
            }
        }

        vaoRendering.draw(this);
        shaderProgram.unbind();

        for (EffectBase effectBase : effectBases) {
            effectBase.pop(this);
        }

        GL30.glPopMatrix();
    }

    public void cleanup() {
        vaoRendering.cleanup();
        shaderProgram.cleanup();
    }

    public String getName() {
        return name;
    }

    public VAORendering getVaoRendering() {
        return vaoRendering;
    }

    public GLObject setVaoRendering(VAORendering vaoRendering) {
        this.vaoRendering = vaoRendering;

        return this;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public GLObject setMesh(Mesh mesh) {
        this.mesh = mesh;

        return this;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public GLObject setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;

        return this;
    }

    public ResourcesLocation getVertexShader() {
        return vertexShader;
    }

    public GLObject setVertexShader(ResourcesLocation vertexShader) {
        this.vertexShader = vertexShader;

        return this;
    }

    public ResourcesLocation getFragmentShader() {
        return fragmentShader;
    }

    public GLObject setFragmentShader(ResourcesLocation fragmentShader) {
        this.fragmentShader = fragmentShader;

        return this;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public GLObject setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;

        return this;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public GLObject setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;

        return this;
    }

    public List<EffectBase> getEffectBases() {
        return effectBases;
    }

    public GLObject setEffectBases(List<EffectBase> effectBases) {
        this.effectBases = effectBases;

        return this;
    }

    public BlendType getBlendType() {
        return blendType;
    }

    public GLObject setBlendType(BlendType blendType) {
        this.blendType = blendType;

        return this;
    }

    public DrawType getDrawType() {
        return drawType;
    }

    public GLObject setDrawType(DrawType drawType) {
        this.drawType = drawType;

        return this;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public GLObject setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;

        return this;
    }

    public float getPointSize() {
        return pointSize;
    }

    public GLObject setPointSize(float pointSize) {
        this.pointSize = pointSize;

        return this;
    }

    public List<UniformDatum<?>> getUniformData() {
        return uniformData;
    }

    public GLObject setUniformData(List<UniformDatum<?>> uniformData) {
        this.uniformData = uniformData;

        return this;
    }
}
