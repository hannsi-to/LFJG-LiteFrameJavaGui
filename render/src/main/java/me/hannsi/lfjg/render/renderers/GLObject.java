package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.render.animation.system.AnimationCache;
import me.hannsi.lfjg.render.system.rendering.frameBuffer.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;

import static me.hannsi.lfjg.core.Core.frameBufferSize;
import static me.hannsi.lfjg.core.Core.projection2D;
import static me.hannsi.lfjg.render.LFJGRenderContext.MESH;
import static me.hannsi.lfjg.render.LFJGRenderContext.SHADER_PROGRAM;

public class GLObject implements Cloneable {
    private final IntRef objectId;
    private String name;

    private FrameBuffer frameBuffer;

    private Transform transform;
    private Matrix4f viewMatrix;

    //    private EffectCache effectCache;
    private AnimationCache animationCache;

    public GLObject(String name) {
        this.name = name;
        this.objectId = new IntRef();

        this.frameBuffer = null;

        this.viewMatrix = null;
        this.transform = new Transform(this);
    }

    public void cleanup() {
        if (animationCache != null) {
            animationCache.cleanup();
        }
//        if (effectCache != null) {
//            effectCache.cleanup();
//        }
        if (frameBuffer != null) {
            frameBuffer.cleanup();
        }

        MESH.deleteObject(objectId.getValue());

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                String.valueOf(objectId.getValue()),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public void create(int id) {
        viewMatrix = new Matrix4f();

        objectId.setValue(id);
    }

    public void draw() {
        draw(true);
    }

    public void draw(boolean autoDraw) {
        bindResources();
        uploadUniforms();
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

    private void uploadUniforms() {
        SHADER_PROGRAM.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.OBJECT.getId());
        SHADER_PROGRAM.updateMatrixUniformBlock(projection2D.getProjMatrix(), viewMatrix, transform.getModelMatrix());
        SHADER_PROGRAM.setUniform("resolution", UploadUniformType.ON_CHANGE, frameBufferSize);
        SHADER_PROGRAM.setUniform("textureSampler", UploadUniformType.ONCE, 0);
    }

    private void bindResources() {
        SHADER_PROGRAM.bind();
    }

    public GLObject copy(String objectName) {
        GLObject glObject;
        try {
            glObject = (GLObject) clone();
            glObject.name = objectName;

            new LogGenerator(
                    getClass().getSimpleName() + " Debug Message",
                    "Source: " + getClass().getName(),
                    "Type: Copy",
                    "ID: " + glObject.getObjectId(),
                    "Severity: Info",
                    "Message: Create object copy: " + glObject.getName()
            ).logging(getClass(), DebugLevel.INFO);
        } catch (Exception e) {
            new LogGenerator(
                    getClass().getSimpleName() + " Debug Message",
                    "Source: " + getClass().getName(),
                    "Type: Copy",
                    "ID: " + this.getObjectId(),
                    "Severity: Error",
                    "Message: Failed to create object copy: " + this.getName()
            ).logging(getClass(), DebugLevel.ERROR);
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

    public int getObjectId() {
        return objectId.getValue();
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    public ShaderProgram getShaderProgram() {
        return SHADER_PROGRAM;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

//    public EffectCache getEffectCache() {
//        return effectCache;
//    }
//
//    public void setEffectCache(EffectCache effectCache) {
//        this.effectCache = effectCache;
//    }

    public AnimationCache getAnimationCache() {
        return animationCache;
    }

    public void setAnimationCache(AnimationCache animationCache) {
        this.animationCache = animationCache;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}