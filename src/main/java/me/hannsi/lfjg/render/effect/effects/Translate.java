package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

/**
 * Class representing a Translate effect in OpenGL.
 */
public class Translate extends EffectBase {
    protected float latestX;
    protected float latestY;
    protected float latestZ;
    /**
     * -- SETTER --
     *  Sets the translation distance along the X axis.
     *
     *
     * -- GETTER --
     *  Gets the translation distance along the X axis.
     *
     @param x the translation distance along the X axis
      * @return the translation distance along the X axis
     */
    @Getter
    @Setter
    private float x;
    /**
     * -- SETTER --
     *  Sets the translation distance along the Y axis.
     *
     *
     * -- GETTER --
     *  Gets the translation distance along the Y axis.
     *
     @param y the translation distance along the Y axis
      * @return the translation distance along the Y axis
     */
    @Getter
    @Setter
    private float y;
    /**
     * -- SETTER --
     *  Sets the translation distance along the Z axis.
     *
     *
     * -- GETTER --
     *  Gets the translation distance along the Z axis.
     *
     @param z the translation distance along the Z axis
      * @return the translation distance along the Z axis
     */
    @Getter
    @Setter
    private float z;

    /**
     * Constructs a new Translate effect with the specified parameters.
     *
     * @param x the translation distance along the X axis
     * @param y the translation distance along the Y axis
     * @param z the translation distance along the Z axis
     */
    public Translate(float x, float y, float z) {
        super(2, "Translate", (Class<GLObject>) null);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a new Translate effect with the specified parameters.
     *
     * @param x the translation distance along the X axis
     * @param y the translation distance along the Y axis
     * @param z the translation distance along the Z axis
     */
    public Translate(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }

    /**
     * Constructs a new Translate effect with the specified parameters.
     *
     * @param x the translation distance along the X axis
     * @param y the translation distance along the Y axis
     */
    public Translate(float x, float y) {
        this(x, y, 0.0f);
    }

    /**
     * Constructs a new Translate effect with the specified parameters.
     *
     * @param x the translation distance along the X axis
     * @param y the translation distance along the Y axis
     */
    public Translate(double x, double y) {
        this(x, y, 0.0f);
    }

    /**
     * Pops the transformation from the stack and applies the inverse translation to the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void pop(GLObject baseGLObject) {
        latestX = x;
        latestY = y;
        latestZ = z;

        super.pop(baseGLObject);
    }

    /**
     * Pushes the transformation onto the stack and applies the translation to the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void push(GLObject baseGLObject) {
        baseGLObject.translate(-latestX, -latestY, -latestZ).translate(x, y, z);
        super.push(baseGLObject);
    }

    /**
     * Pushes the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();
        super.frameBufferPush(baseGLObject);
    }

    /**
     * Pops the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();
        super.frameBufferPop(baseGLObject);
    }

    /**
     * Draws the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();
        super.frameBuffer(baseGLObject);
    }

    /**
     * Sets the uniform variables for the shader program.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void setUniform(GLObject baseGLObject) {
        super.setUniform(baseGLObject);
    }

}