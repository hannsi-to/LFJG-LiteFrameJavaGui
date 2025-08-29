package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.Id;
import me.hannsi.lfjg.render.animation.system.AnimationCache;
import me.hannsi.lfjg.render.effect.system.EffectBase;
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

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;
import static org.lwjgl.opengl.GL11.*;

/**
 * Represents an OpenGL object with various properties and methods for rendering.
 */
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

    /**
     * Constructs a new GLObject with the specified name.
     *
     * @param name the name of the GLObject
     */
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

    /**
     * Cleans up the resources used by the GLObject.
     */
    public void cleanup() {
        if (animationCache != null) {
            animationCache.cleanup(this);
        }
        if (effectCache != null) {
            effectCache.cleanup();
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

    /**
     * Initializes the GLObject by creating necessary OpenGL resources.
     */
    public void create() {
        vaoRendering = new VAORendering();

        viewMatrix = new Matrix4f();

        blendType = BlendType.NORMAL;
    }

    /**
     * Draws the GLObject using the specified resolution and projection.
     */
    public void draw() {
        draw(true);
    }

    public void draw(boolean autoDraw) {
        bindResources();
        setupRenderState();
        uploadUniforms();

        if(effectCache != null){
            if(frameBuffer == null){
                frameBuffer = new FrameBuffer();
                frameBuffer.createFrameBuffer();
                frameBuffer.createMatrix(transform.getModelMatrix(), viewMatrix);
            }

            effectCache.push(this);
            if(effectCache.isNeedFrameBuffer()){
                frameBuffer.bindFrameBuffer();
            }
        }
        drawVAORendering();
        if(effectCache != null){
//            if(effectCache.isNeedFrameBuffer()){
//                GLStateCache.bindFrameBuffer(0);
//            }
            effectCache.pop(this);
        }

        uploadCache();

        if (autoDraw) {
            drawFrameBuffer();
        }
    }

    public void drawVAORendering(){
        vaoRendering.draw(this);
    }

    public void drawFrameBuffer() {
        if(effectCache != null && effectCache.isNeedFrameBuffer()){
            effectCache.setBaseFrameBuffer(frameBuffer);
            effectCache.drawFrameBuffer(this);
        }
    }

    private void uploadCache() {
        if (animationCache != null) {
            animationCache.loop(this);
        }
    }

    private void setupRenderState() {
        if (lineWidth != -1f) {
            glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            glPointSize(pointSize);
        }

        GLStateCache.blendFunc(blendType.getSfactor(), blendType.getDfactor());
        GLStateCache.setBlendEquation(blendType.getEquation());
        GLStateCache.enable(GL_BLEND);
        GLStateCache.disable(GL_DEPTH_TEST);
    }

    private void uploadUniforms() {
        shaderProgram.setUniform("fragmentShaderType",UploadUniformType.PER_FRAME, FragmentShaderType.OBJECT.getId());
        shaderProgram.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, Core.projection2D.getProjMatrix());
        shaderProgram.setUniform("modelMatrix", UploadUniformType.ON_CHANGE, transform.getModelMatrix());
        shaderProgram.setUniform("viewMatrix", UploadUniformType.ON_CHANGE, viewMatrix);
        shaderProgram.setUniform("resolution",UploadUniformType.ON_CHANGE,Core.frameBufferSize);
        if (mesh.getVboIds().get(BufferObjectType.TEXTURE_BUFFER) != null) {
            shaderProgram.setUniform("textureSampler", UploadUniformType.ONCE, 0);
        }
    }

    private void bindResources() {
        shaderProgram.bind();
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

    /**
     * Sets the VAORendering of the GLObject.
     *
     * @param vaoRendering the VAORendering to set
     * @return the GLObject instance
     */
    public GLObject setVaoRendering(VAORendering vaoRendering) {
        this.vaoRendering = vaoRendering;

        return this;
    }

    public Mesh getMesh() {
        return mesh;
    }

    /**
     * Sets the Mesh of the GLObject.
     *
     * @param mesh the Mesh to set
     * @return the GLObject instance
     */
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
        return shaderProgram;
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

    /**
     * Sets the blend type of the GLObject.
     *
     * @param blendType the blend type to set
     * @return the GLObject instance
     */
    public GLObject setBlendType(BlendType blendType) {
        this.blendType = blendType;

        return this;
    }

    public DrawType getDrawType() {
        return drawType;
    }

    /**
     * Sets the draw type of the GLObject.
     *
     * @param drawType the draw type to set
     * @return the GLObject instance
     */
    public GLObject setDrawType(DrawType drawType) {
        this.drawType = drawType;

        return this;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets the line width of the GLObject.
     *
     * @param lineWidth the line width to set
     * @return the GLObject instance
     */
    public GLObject setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;

        return this;
    }

    public float getPointSize() {
        return pointSize;
    }

    /**
     * Sets the point size of the GLObject.
     *
     * @param pointSize the point size to set
     * @return the GLObject instance
     */
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