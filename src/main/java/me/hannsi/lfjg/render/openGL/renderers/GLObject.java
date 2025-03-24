package me.hannsi.lfjg.render.openGL.renderers;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.animation.system.AnimationCache;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.system.Id;
import me.hannsi.lfjg.render.openGL.system.Mesh;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.openGL.system.rendering.VAORendering;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.graphics.GLUtil;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.type.types.BlendType;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

/**
 * Represents an OpenGL object with various properties and methods for rendering.
 */
public class GLObject implements Cloneable {
    private String name;
    private long objectId;

    private VAORendering vaoRendering;
    private Mesh mesh;

    private FrameBuffer frameBuffer;

    private ShaderProgram shaderProgram;
    private FileLocation vertexShader;
    private FileLocation fragmentShader;

    private Matrix4f modelMatrix;
    private Matrix4f viewMatrix;

    private EffectCache effectCache;
    private AnimationCache animationCache;
    private BlendType blendType;
    private DrawType drawType;
    private GLUtil glUtil;
    private float lineWidth;
    private float pointSize;

    private float x;
    private float y;
    private float width;
    private float height;

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
        this.modelMatrix = null;
        this.viewMatrix = null;
        this.blendType = null;
        this.drawType = null;
        this.mesh = null;
        this.frameBuffer = null;
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
        }
        mesh.cleanup();
        frameBuffer.cleanup();
        shaderProgram.cleanup();
        vertexShader.cleanup();
        modelMatrix = null;
        viewMatrix = null;
        vaoRendering.cleanup();
        glUtil.cleanup();
        blendType = null;

        LogGenerator logGenerator = new LogGenerator(name, "Source: GLObject", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: GLObject cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Initializes the GLObject by creating necessary OpenGL resources.
     */
    public void create() {
        vaoRendering = new VAORendering();

        frameBuffer = new FrameBuffer();
        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();

        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(vertexShader);
        shaderProgram.createFragmentShader(fragmentShader);
        shaderProgram.link();

        modelMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();

        glUtil = new GLUtil();
        blendType = BlendType.Normal;
    }

    /**
     * Draws the GLObject using the specified resolution and projection.
     */
    public void draw() {
        draw(true);
    }

    public void draw(boolean autoDraw) {
        frameBuffer.bindFrameBuffer();
        GL30.glPushMatrix();

        shaderProgram.bind();

        if (lineWidth != -1f) {
            GL30.glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            GL30.glPointSize(pointSize);
        }

        if (effectCache != null) {
            effectCache.push(this);
        }

        shaderProgram.setUniform("projectionMatrix", LFJGContext.projection.getProjMatrix());
        shaderProgram.setUniform("modelMatrix", modelMatrix);
        shaderProgram.setUniform("viewMatrix", viewMatrix);
        shaderProgram.setUniform("resolution", LFJGContext.resolution);

        if (mesh.getTexture() != null) {
            shaderProgram.setUniform1i("textureSampler", 0);
        }

        glUtil.addGLTarget(GL30.GL_BLEND);
        glUtil.addGLTarget(GL30.GL_DEPTH_TEST, true);
        GL30.glBlendFunc(blendType.getSfactor(), blendType.getDfactor());
        GL30.glBlendEquation(blendType.getEquation());
        glUtil.enableTargets();

        vaoRendering.draw(this);

        glUtil.disableTargets();
        glUtil.cleanup();

        if (effectCache != null) {
            effectCache.pop(this);
        }

        shaderProgram.unbind();

        GL30.glPopMatrix();
        frameBuffer.unbindFrameBuffer();

        if (effectCache != null) {
            effectCache.frameBuffer(this);
        }

        if (animationCache != null) {
            animationCache.loop(this);
        }

        if (autoDraw) {
            drawFrameBuffer();
        }
    }

    public void drawFrameBuffer() {
        if (effectCache != null) {
            effectCache.getEndFrameBuffer().drawFrameBuffer();
        } else {
            frameBuffer.drawFrameBuffer();
        }
    }

    public GLObject copy(String objectName) {
        GLObject glObject;
        try {
            glObject = (GLObject) clone();
            glObject.setName(objectName);
            long copyId = this.getObjectId();
            glObject.setObjectId(++copyId);

            LogGenerator logGenerator = new LogGenerator("GLObject Debug Message", "Source: GLObject", "Type: Copy", "ID: " + glObject.getObjectId(), "Severity: Info", "Message: Create object copy: " + glObject.getName());
            logGenerator.logging(DebugLevel.INFO);
        } catch (Exception e) {
            LogGenerator logGenerator = new LogGenerator("GLObject Debug Message", "Source: GLObject", "Type: Copy", "ID: " + this.getObjectId(), "Severity: Error", "Message: Failed to create object copy: " + this.getName());
            logGenerator.logging(DebugLevel.ERROR);

            throw new RuntimeException(e);
        }

        return glObject;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    @Deprecated
    public void setName(String name) {
        this.name = name;
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
    public FileLocation getVertexShader() {
        return vertexShader;
    }

    /**
     * Sets the vertex shader location of the GLObject.
     *
     * @param vertexShader the vertex shader location to set
     * @return the GLObject instance
     */
    public GLObject setVertexShader(FileLocation vertexShader) {
        this.vertexShader = vertexShader;

        return this;
    }

    /**
     * Gets the fragment shader location of the GLObject.
     *
     * @return the fragment shader location of the GLObject
     */
    public FileLocation getFragmentShader() {
        return fragmentShader;
    }

    /**
     * Sets the fragment shader location of the GLObject.
     *
     * @param fragmentShader the fragment shader location to set
     * @return the GLObject instance
     */
    public GLObject setFragmentShader(FileLocation fragmentShader) {
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

    @Deprecated
    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public AnimationCache getAnimationCache() {
        return animationCache;
    }

    public void setAnimationCache(AnimationCache animationCache) {
        this.animationCache = animationCache;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}