package me.hannsi.lfjg.render.openGL.renderers;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.system.Id;
import me.hannsi.lfjg.render.openGL.system.Mesh;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.openGL.system.rendering.VAORendering;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.graphics.GLUtil;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.BlendType;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;

/**
 * Represents an OpenGL object with various properties and methods for rendering.
 */
public class GLObject {
    private final String name;
    private final long objectId;

    private VAORendering vaoRendering;
    private Mesh mesh;

    private FrameBuffer frameBuffer;

    private ShaderProgram shaderProgram;
    private ResourcesLocation vertexShader;
    private ResourcesLocation fragmentShader;

    private Vector2f resolution;
    private Matrix4f projectionMatrix;
    private Matrix4f modelMatrix;
    private Matrix4f viewMatrix;

    private EffectCache effectCache;
    private BlendType blendType;
    private DrawType drawType;
    private GLUtil glUtil;
    private float lineWidth;
    private float pointSize;

    /**
     * Constructs a new GLObject with the specified name.
     *
     * @param name the name of the GLObject
     */
    public GLObject(String name) {
        this.name = name;

        this.vaoRendering = null;
        this.shaderProgram = null;
        this.lineWidth = -1f;
        this.pointSize = -1f;
        this.vertexShader = null;
        this.fragmentShader = null;
        this.resolution = null;
        this.projectionMatrix = null;
        this.modelMatrix = null;
        this.viewMatrix = null;
        this.blendType = null;
        this.drawType = null;
        this.mesh = null;
        this.frameBuffer = null;
        this.objectId = ++Id.glLatestObjectId;
    }

    /**
     * Initializes the GLObject by creating necessary OpenGL resources.
     */
    public void create() {
        vaoRendering = new VAORendering();

        frameBuffer = new FrameBuffer(resolution);
        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();

        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(vertexShader);
        shaderProgram.createFragmentShader(fragmentShader);
        shaderProgram.link();

        modelMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();

        glUtil = new GLUtil();
    }

    /**
     * Draws the GLObject using the specified resolution and projection.
     *
     * @param resolution the resolution to use for drawing
     * @param projection the projection to use for drawing
     */
    public void draw(Vector2f resolution, Projection projection) {
        this.resolution = resolution;
        this.projectionMatrix = projection.getProjMatrix();

        frameBuffer.bindFrameBuffer();
        GL30.glPushMatrix();

        shaderProgram.bind();

        if (lineWidth != -1f) {
            GL30.glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            GL30.glPointSize(pointSize);
        }

        effectCache.push(this);

        shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        shaderProgram.setUniform("modelMatrix", modelMatrix);
        shaderProgram.setUniform("viewMatrix", viewMatrix);
        shaderProgram.setUniform("resolution", resolution);

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

        effectCache.pop(this);

        shaderProgram.unbind();

        GL30.glPopMatrix();
        frameBuffer.unbindFrameBuffer();

        effectCache.frameBuffer(this);
    }

    /**
     * Cleans up the resources used by the GLObject.
     */
    public void cleanup() {
        vaoRendering.cleanup();
        shaderProgram.cleanup();
        effectCache.cleanup();
    }

    /**
     * Gets the effect base at the specified index.
     *
     * @param index the index of the effect base
     * @return the effect base at the specified index
     */
    public EffectBase getEffectBase(int index) {
        return effectCache.getEffectBase(index);
    }

    /**
     * Sets the effect base for the GLObject.
     */
    public void setEffectBase() {
        // Implementation needed
    }

    /**
     * Adds a GL target to the GLObject.
     *
     * @param target the GL target to add
     */
    public void addGLTarget(int target) {
        glUtil.addGLTarget(target);
    }

    /**
     * Gets the name of the GLObject.
     *
     * @return the name of the GLObject
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the VAORendering of the GLObject.
     *
     * @return the VAORendering of the GLObject
     */
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

    /**
     * Gets the Mesh of the GLObject.
     *
     * @return the Mesh of the GLObject
     */
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

    /**
     * Gets the ShaderProgram of the GLObject.
     *
     * @return the ShaderProgram of the GLObject
     */
    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    /**
     * Sets the ShaderProgram of the GLObject.
     *
     * @param shaderProgram the ShaderProgram to set
     * @return the GLObject instance
     */
    public GLObject setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;

        return this;
    }

    /**
     * Gets the vertex shader location of the GLObject.
     *
     * @return the vertex shader location of the GLObject
     */
    public ResourcesLocation getVertexShader() {
        return vertexShader;
    }

    /**
     * Sets the vertex shader location of the GLObject.
     *
     * @param vertexShader the vertex shader location to set
     * @return the GLObject instance
     */
    public GLObject setVertexShader(ResourcesLocation vertexShader) {
        this.vertexShader = vertexShader;

        return this;
    }

    /**
     * Gets the fragment shader location of the GLObject.
     *
     * @return the fragment shader location of the GLObject
     */
    public ResourcesLocation getFragmentShader() {
        return fragmentShader;
    }

    /**
     * Sets the fragment shader location of the GLObject.
     *
     * @param fragmentShader the fragment shader location to set
     * @return the GLObject instance
     */
    public GLObject setFragmentShader(ResourcesLocation fragmentShader) {
        this.fragmentShader = fragmentShader;

        return this;
    }

    /**
     * Gets the model matrix of the GLObject.
     *
     * @return the model matrix of the GLObject
     */
    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    /**
     * Sets the model matrix of the GLObject.
     *
     * @param modelMatrix the model matrix to set
     * @return the GLObject instance
     */
    public GLObject setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;

        return this;
    }

    /**
     * Gets the projection matrix of the GLObject.
     *
     * @return the projection matrix of the GLObject
     */
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * Sets the projection matrix of the GLObject.
     *
     * @param projectionMatrix the projection matrix to set
     * @return the GLObject instance
     */
    public GLObject setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;

        return this;
    }

    /**
     * Gets the blend type of the GLObject.
     *
     * @return the blend type of the GLObject
     */
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

    /**
     * Gets the draw type of the GLObject.
     *
     * @return the draw type of the GLObject
     */
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

    /**
     * Gets the line width of the GLObject.
     *
     * @return the line width of the GLObject
     */
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

    /**
     * Gets the point size of the GLObject.
     *
     * @return the point size of the GLObject
     */
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

    /**
     * Gets the resolution of the GLObject.
     *
     * @return the resolution of the GLObject
     */
    public Vector2f getResolution() {
        return resolution;
    }

    /**
     * Sets the resolution of the GLObject.
     *
     * @param resolution the resolution to set
     */
    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
    }

    /**
     * Gets the view matrix of the GLObject.
     *
     * @return the view matrix of the GLObject
     */
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    /**
     * Sets the view matrix of the GLObject.
     *
     * @param viewMatrix the view matrix to set
     */
    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    /**
     * Gets the GLUtil instance of the GLObject.
     *
     * @return the GLUtil instance of the GLObject
     */
    public GLUtil getGlUtil() {
        return glUtil;
    }

    /**
     * Sets the GLUtil instance of the GLObject.
     *
     * @param glUtil the GLUtil instance to set
     */
    public void setGlUtil(GLUtil glUtil) {
        this.glUtil = glUtil;
    }

    /**
     * Gets the FrameBuffer of the GLObject.
     *
     * @return the FrameBuffer of the GLObject
     */
    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    /**
     * Sets the FrameBuffer of the GLObject.
     *
     * @param frameBuffer the FrameBuffer to set
     */
    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    /**
     * Gets the EffectCache of the GLObject.
     *
     * @return the EffectCache of the GLObject
     */
    public EffectCache getEffectCache() {
        return effectCache;
    }

    /**
     * Sets the EffectCache of the GLObject.
     *
     * @param effectCache the EffectCache to set
     */
    public void setEffectCache(EffectCache effectCache) {
        this.effectCache = effectCache;
    }

    /**
     * Gets the object ID of the GLObject.
     *
     * @return the object ID of the GLObject
     */
    public long getObjectId() {
        return objectId;
    }
}