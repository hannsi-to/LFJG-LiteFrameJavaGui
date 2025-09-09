package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.Id;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.animation.system.AnimationCache;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class GLObject implements Cloneable {
    private String name;
    private long objectId;

    private VAORendering vaoRendering;
    private Mesh mesh;
    private FrameBuffer frameBuffer;

    private Matrix4f viewMatrix;

    private EffectCache effectCache;
    private AnimationCache animationCache;
    private BlendType blendType;
    private DrawType drawType;
    private float lineWidth;
    private float pointSize;

    private Transform transform;

    public GLObject(String name) {
        this.name = name;

        this.vaoRendering = null;
        this.mesh = null;
        this.frameBuffer = null;

        this.viewMatrix = null;
        this.transform = new Transform(this);

        this.lineWidth = -1f;
        this.pointSize = -1f;
        this.blendType = null;
        this.drawType = null;
        this.objectId = ++Id.latestGLObjectId;
    }

    public void cleanup() {
        if (animationCache != null) {
            animationCache.cleanup();
        }
        if (effectCache != null) {
            effectCache.cleanup();
        }
        if (frameBuffer != null) {
            frameBuffer.cleanup();
        }
        if (mesh != null) {
            mesh.cleanup();
        }
        if (vaoRendering != null) {
            vaoRendering.cleanup();
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                String.valueOf(objectId),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public void create() {
        vaoRendering = new VAORendering();

        viewMatrix = new Matrix4f();

        blendType = BlendType.NORMAL;
    }

    public void draw() {
        draw(true);
    }

    public void draw(boolean autoDraw) {
        bindResources();
        setupRenderState();
        uploadUniforms();

        if (effectCache != null) {
            if (!effectCache.isNeedFrameBuffer()) {
                effectCache.push(this);
            } else {
                bindFrameBuffer();
            }
        }

        drawVAORendering();

        if (effectCache != null) {
            if (!effectCache.isNeedFrameBuffer()) {
                effectCache.pop(this);
            }
        }

        if (autoDraw) {
            drawFrameBuffer();
        }
    }

    public void drawVAORendering() {
        vaoRendering.draw(this);
    }

    public void drawFrameBuffer() {
        if (effectCache != null && effectCache.isNeedFrameBuffer()) {
            effectCache.setBaseFrameBuffer(frameBuffer);
            effectCache.drawFrameBuffer(this);
        }

        updateAnimation();
    }

    private void bindFrameBuffer() {
        if (frameBuffer == null) {
            frameBuffer = new FrameBuffer();
            frameBuffer.createFrameBuffer();
            frameBuffer.createMatrix(new Matrix4f(), viewMatrix);
        }

        frameBuffer.bindFrameBuffer();
    }

    private void updateAnimation() {
        if (animationCache != null) {
            animationCache.loop();
        }
    }

    private void setupRenderState() {
        if (lineWidth != -1f) {
            GL11.glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            GL11.glPointSize(pointSize);
        }

        GLStateCache.blendFunc(blendType.getSfactor(), blendType.getDfactor());
        GLStateCache.setBlendEquation(blendType.getEquation());
        GLStateCache.enable(GL11.GL_BLEND);
        GLStateCache.disable(GL11.GL_DEPTH_TEST);
    }

    private void uploadUniforms() {
        LFJGRenderContext.shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.OBJECT.getId());
        LFJGRenderContext.shaderProgram.setUniform("projectionMatrix", UploadUniformType.PER_FRAME, Core.projection2D.getProjMatrix());
        LFJGRenderContext.shaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, transform.getModelMatrix());
        LFJGRenderContext.shaderProgram.setUniform("viewMatrix", UploadUniformType.PER_FRAME, viewMatrix);
        LFJGRenderContext.shaderProgram.setUniform("resolution", UploadUniformType.ON_CHANGE, Core.frameBufferSize);
        if (mesh.getVboIds().get(BufferObjectType.TEXTURE_BUFFER) != null) {
            LFJGRenderContext.shaderProgram.setUniform("textureSampler", UploadUniformType.ONCE, 0);
        }
    }

    private void bindResources() {
        LFJGRenderContext.shaderProgram.bind();
    }

    public GLObject copy(String objectName) {
        GLObject glObject;
        try {
            glObject = (GLObject) clone();
            glObject.setName(objectName);
            glObject.setObjectId(++Id.latestGLObjectId);

            new LogGenerator(
                    getClass().getSimpleName() + " Debug Message",
                    "Source: " + getClass().getName(),
                    "Type: Copy",
                    "ID: " + glObject.getObjectId(),
                    "Severity: Info",
                    "Message: Create object copy: " + glObject.getName()
            ).logging(DebugLevel.INFO);
        } catch (Exception e) {
            new LogGenerator(
                    getClass().getSimpleName() + " Debug Message",
                    "Source: " + getClass().getName(),
                    "Type: Copy",
                    "ID: " + this.getObjectId(),
                    "Severity: Error",
                    "Message: Failed to create object copy: " + this.getName()
            ).logging(DebugLevel.ERROR);
            throw new RuntimeException(e);
        }

        return glObject;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getName() {
        return name;
    }

    @Deprecated
    public void setName(String name) {
        this.name = name;
    }

    public long getObjectId() {
        return objectId;
    }

    @Deprecated
    public void setObjectId(long objectId) {
        this.objectId = objectId;
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

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    public ShaderProgram getShaderProgram() {
        return LFJGRenderContext.shaderProgram;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public EffectCache getEffectCache() {
        return effectCache;
    }

    public void setEffectCache(EffectCache effectCache) {
        this.effectCache = effectCache;
    }

    public AnimationCache getAnimationCache() {
        return animationCache;
    }

    public void setAnimationCache(AnimationCache animationCache) {
        this.animationCache = animationCache;
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

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}