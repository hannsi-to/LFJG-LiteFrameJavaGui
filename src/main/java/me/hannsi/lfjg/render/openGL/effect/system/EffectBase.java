package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

/**
 * Base class for effects in OpenGL.
 */
public class EffectBase {
    private final FrameBuffer frameBuffer;
    private final Vector2f resolution;
    private int id;
    private String name;
    private Class<GLObject>[] ignoreGLObject;

    /**
     * Constructs a new EffectBase with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param vertexPath the path to the vertex shader
     * @param fragmentPath the path to the fragment shader
     * @param id the ID of the effect
     * @param name the name of the effect
     * @param ignoreGLObject the GL objects to ignore
     */
    @SafeVarargs
    public EffectBase(Vector2f resolution, ResourcesLocation vertexPath, ResourcesLocation fragmentPath, int id, String name, Class<GLObject>... ignoreGLObject) {
        this.resolution = resolution;
        this.id = id;
        this.name = name;
        this.ignoreGLObject = ignoreGLObject;

        frameBuffer = new FrameBuffer(resolution);

        if (vertexPath != null) {
            frameBuffer.setVertexShaderFBO(vertexPath);
        }

        if (fragmentPath != null) {
            frameBuffer.setFragmentShaderFBO(fragmentPath);
        }

        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();
    }

    /**
     * Constructs a new EffectBase with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param path the path to the shader
     * @param isFragmentPath whether the path is for a fragment shader
     * @param id the ID of the effect
     * @param name the name of the effect
     */
    public EffectBase(Vector2f resolution, ResourcesLocation path, boolean isFragmentPath, int id, String name) {
        this(resolution, isFragmentPath ? null : path, isFragmentPath ? path : null, id, name);
    }

    /**
     * Constructs a new EffectBase with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param path the path to the shader
     * @param isFragmentPath whether the path is for a fragment shader
     * @param id the ID of the effect
     * @param name the name of the effect
     * @param ignoreGLObject the GL objects to ignore
     */
    @SafeVarargs
    public EffectBase(Vector2f resolution, ResourcesLocation path, boolean isFragmentPath, int id, String name, Class<GLObject>... ignoreGLObject) {
        this(resolution, isFragmentPath ? null : path, isFragmentPath ? path : null, id, name, ignoreGLObject);
    }

    /**
     * Constructs a new EffectBase with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param id the ID of the effect
     * @param name the name of the effect
     * @param ignoreGLObject the GL objects to ignore
     */
    @SafeVarargs
    public EffectBase(Vector2f resolution, int id, String name, Class<GLObject>... ignoreGLObject) {
        this(resolution, null, null, id, name, ignoreGLObject);
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
     * Gets the resolution of the effect.
     *
     * @return the resolution of the effect
     */
    public Vector2f getResolution() {
        return resolution;
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
     * Sets the name of the effect.
     *
     * @param name the name of the effect
     */
    public void setName(String name) {
        this.name = name;
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
}