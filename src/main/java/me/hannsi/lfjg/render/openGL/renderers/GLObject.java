package me.hannsi.lfjg.render.openGL.renderers;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.system.Mesh;
import me.hannsi.lfjg.render.openGL.system.VAORendering;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.graphics.GLUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.BlendType;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class GLObject {
    private final String name;

    private VAORendering vaoRendering;
    private Mesh mesh;

    private ShaderProgram shaderProgram;
    private ResourcesLocation vertexShader;
    private ResourcesLocation fragmentShader;

    private Vector2f resolution;
    private Matrix4f projectionMatrix;
    private Matrix4f modelMatrix;
    private Matrix4f viewMatrix;

    private List<EffectBase> effectBases;
    private BlendType blendType;
    private DrawType drawType;
    private GLUtil glUtil;
    private float lineWidth;
    private float pointSize;

    public GLObject(String name) {
        this.name = name;

        this.vaoRendering = new VAORendering();
        this.shaderProgram = new ShaderProgram();
        this.lineWidth = -1f;
        this.pointSize = -1f;
        this.vertexShader = null;
        this.fragmentShader = null;
        this.resolution = null;
        this.projectionMatrix = null;
        this.modelMatrix = null;
        this.viewMatrix = null;
        this.effectBases = new ArrayList<>();
        this.blendType = null;
        this.drawType = null;
        this.mesh = null;
    }

    public void create() {
        try {
            shaderProgram.createVertexShader(vertexShader);
            shaderProgram.createFragmentShader(fragmentShader);
            shaderProgram.link();

            modelMatrix = new Matrix4f();
            viewMatrix = new Matrix4f();
        } catch (Exception e) {
            DebugLog.debug(getClass(), e);
        }
    }

    public void draw() {
        GL30.glPushMatrix();

        glUtil = new GLUtil();

        shaderProgram.bind();

        if (lineWidth != -1f) {
            GL30.glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            GL30.glPointSize(pointSize);
        }

        for (EffectBase effectBase : effectBases) {
            effectBase.push(this);
        }

        shaderProgram.setUniformMatrix4fv("projectionMatrix", projectionMatrix);
        shaderProgram.setUniformMatrix4fv("modelMatrix", modelMatrix);
        shaderProgram.setUniformMatrix4fv("viewMatrix", viewMatrix);
        shaderProgram.setUniform2f("resolution", resolution);

        if (mesh.getTexture() != null) {
            shaderProgram.setUniform1i("textureSampler", 0);
        }

        glUtil.addGLTarget(GL30.GL_BLEND);
        glUtil.addGLTarget(GL30.GL_DEPTH_TEST, true);
        GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        glUtil.enableTargets();

        vaoRendering.draw(this);

        glUtil.disableTargets();
        glUtil.finish();

        for (EffectBase effectBase : effectBases) {
            effectBase.pop(this);
        }

        effectBases = new ArrayList<>();
        shaderProgram.unbind();

        GL30.glPopMatrix();
    }

    public void cleanup() {
        vaoRendering.cleanup();
        shaderProgram.cleanup();
    }

    public void addEffectBase(EffectBase effectBase) {
        effectBases.add(effectBase);
    }

    public void addGLTarget(int target) {
        glUtil.addGLTarget(target);
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

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public GLUtil getGlUtil() {
        return glUtil;
    }

    public void setGlUtil(GLUtil glUtil) {
        this.glUtil = glUtil;
    }
}
