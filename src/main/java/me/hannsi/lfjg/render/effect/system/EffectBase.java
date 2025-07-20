package me.hannsi.lfjg.render.effect.system;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.utils.reflection.location.Location;

/**
 * Base class for effects in OpenGL.
 */
@Getter
public class EffectBase {
    private final Location vertexPath;
    private final Location fragmentPath;
    /**
     * -- GETTER --
     * Gets the name of the effect.
     *
     * @return the name of the effect
     */
    private final String name;
    /**
     * -- GETTER --
     * Gets the frame buffer.
     *
     * @return the frame buffer
     */
    @Setter
    private FrameBuffer frameBuffer;
    /**
     * -- GETTER --
     * Gets the ID of the effect.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the ID of the effect.
     *
     * @return the ID of the effect
     * @param id the ID of the effect
     */
    @Setter
    private int id;
    /**
     * -- GETTER --
     * Gets the GL objects to ignore.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the GL objects to ignore.
     *
     * @return the GL objects to ignore
     * @param ignoreGLObject the GL objects to ignore
     */
    @Setter
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
    public EffectBase(Location vertexPath, Location fragmentPath, int id, String name, Class<GLObject>... ignoreGLObject) {
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
    public EffectBase(Location path, boolean isFragmentPath, int id, String name) {
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
    public EffectBase(Location path, boolean isFragmentPath, int id, String name, Class<GLObject>... ignoreGLObject) {
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
        frameBuffer = new FrameBuffer(glObject.getTransform().getX(), glObject.getTransform().getY(), glObject.getTransform().getWidth(), glObject.getTransform().getHeight());

        if (vertexPath != null) {
            frameBuffer.setVertexShaderFBO(vertexPath);
        }

        if (fragmentPath != null) {
            frameBuffer.setFragmentShaderFBO(fragmentPath);
        }

        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();
    }

    public void create(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;

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
        if (frameBuffer != null) {
            frameBuffer.cleanup();
        }
        ignoreGLObject = null;

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                id,
                ""
        ).logging(DebugLevel.DEBUG);
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
}