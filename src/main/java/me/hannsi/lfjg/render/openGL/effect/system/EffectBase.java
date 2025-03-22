package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;

/**
 * Base class for effects in OpenGL.
 */
public class EffectBase {
    private final ResourcesLocation vertexPath;
    private final ResourcesLocation fragmentPath;
    private final String name;
    private FrameBuffer frameBuffer;
    private int id;
    private Class<GLObject>[] ignoreGLObject;

    /**
     * Constructs a new EffectBase with the specified parameters.
     *
     * @param vertexPath     the path to the vertex shader
     * @param fragmentPath   the path to the fragment shader
     * @param id             the ID of the effect
     * @param name           the name of the effect
     * @param ignoreGLObject the GL objects to ignore
     */
    @SafeVarargs
    public EffectBase(ResourcesLocation vertexPath, ResourcesLocation fragmentPath, int id, String name, Class<GLObject>... ignoreGLObject) {
        this.id = id;
        this.name = name;
        this.ignoreGLObject = ignoreGLObject;
        this.vertexPath = vertexPath;
        this.fragmentPath = fragmentPath;
    }

    /**
     * Constructs a new EffectBase with the specified parameters.
     *
     * @param path           the path to the shader
     * @param isFragmentPath whether the path is for a fragment shader
     * @param id             the ID of the effect
     * @param name           the name of the effect
     */
    public EffectBase(ResourcesLocation path, boolean isFragmentPath, int id, String name) {
        this(isFragmentPath ? null : path, isFragmentPath ? path : null, id, name);
    }

    /**
     * Constructs a new EffectBase with the specified parameters.
     *
     * @param path           the path to the shader
     * @param isFragmentPath whether the path is for a fragment shader
     * @param id             the ID of the effect
     * @param name           the name of the effect
     * @param ignoreGLObject the GL objects to ignore
     */
    @SafeVarargs
    public EffectBase(ResourcesLocation path, boolean isFragmentPath, int id, String name, Class<GLObject>... ignoreGLObject) {
        this(isFragmentPath ? null : path, isFragmentPath ? path : null, id, name, ignoreGLObject);
    }

    /**
     * Constructs a new EffectBase with the specified parameters.
     *
     * @param id             the ID of the effect
     * @param name           the name of the effect
     * @param ignoreGLObject the GL objects to ignore
     */
    @SafeVarargs
    public EffectBase(int id, String name, Class<GLObject>... ignoreGLObject) {
        this(null, null, id, name, ignoreGLObject);
    }

    public void create(GLObject glObject) {
        frameBuffer = new FrameBuffer(glObject.getX(), glObject.getY(), glObject.getWidth(), glObject.getHeight());

        if (vertexPath != null) {
            frameBuffer.setVertexShaderFBO(vertexPath);
        }

        if (fragmentPath != null) {
            frameBuffer.setFragmentShaderFBO(fragmentPath);
        }

        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();
    }

    public void cleanup() {
        frameBuffer.cleanup();
        ignoreGLObject = null;

        LogGenerator logGenerator = new LogGenerator(name, "Source: EffectBase", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: EffectBase cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Draws the effect for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    public void draw(GLObject baseGLObject) {

    }

    /**
     * Pops the effect for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    public void pop(GLObject baseGLObject) {

    }

    /**
     * Pushes the effect for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    public void push(GLObject baseGLObject) {

    }

    /**
     * Pushes the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    public void frameBufferPush(GLObject baseGLObject) {

    }

    /**
     * Pops the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    public void frameBufferPop(GLObject baseGLObject) {

    }

    /**
     * Draws the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    public void frameBuffer(GLObject baseGLObject) {

    }

    /**
     * Sets the uniform variables for the shader program.
     *
     * @param baseGLObject the base GL object
     */
    public void setUniform(GLObject baseGLObject) {

    }

    /**
     * Gets the ID of the effect.
     *
     * @return the ID of the effect
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the effect.
     *
     * @param id the ID of the effect
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the effect.
     *
     * @return the name of the effect
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the GL objects to ignore.
     *
     * @return the GL objects to ignore
     */
    public Class<GLObject>[] getIgnoreGLObject() {
        return ignoreGLObject;
    }

    /**
     * Sets the GL objects to ignore.
     *
     * @param ignoreGLObject the GL objects to ignore
     */
    public void setIgnoreGLObject(Class<GLObject>[] ignoreGLObject) {
        this.ignoreGLObject = ignoreGLObject;
    }

    /**
     * Gets the frame buffer.
     *
     * @return the frame buffer
     */
    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    public ResourcesLocation getVertexPath() {
        return vertexPath;
    }

    public ResourcesLocation getFragmentPath() {
        return fragmentPath;
    }
}