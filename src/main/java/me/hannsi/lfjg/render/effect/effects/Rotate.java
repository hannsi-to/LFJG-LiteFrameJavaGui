package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

/**
 * Class representing a Rotate effect in OpenGL.
 */
public class Rotate extends EffectBase {
    protected float latestX;
    protected float latestY;
    protected float latestZ;
    /**
     * -- SETTER --
     *  Sets the rotation angle around the X axis.
     *
     *
     * -- GETTER --
     *  Gets the rotation angle around the X axis.
     *
     @param x the rotation angle around the X axis
      * @return the rotation angle around the X axis
     */
    @Getter
    @Setter
    private float x;
    /**
     * -- SETTER --
     *  Sets the rotation angle around the Y axis.
     *
     *
     * -- GETTER --
     *  Gets the rotation angle around the Y axis.
     *
     @param y the rotation angle around the Y axis
      * @return the rotation angle around the Y axis
     */
    @Getter
    @Setter
    private float y;
    /**
     * -- SETTER --
     *  Sets the rotation angle around the Z axis.
     *
     *
     * -- GETTER --
     *  Gets the rotation angle around the Z axis.
     *
     @param z the rotation angle around the Z axis
      * @return the rotation angle around the Z axis
     */
    @Getter
    @Setter
    private float z;
    @Getter
    @Setter
    private boolean autoCenter;
    /**
     * -- SETTER --
     *  Sets the X coordinate of the rotation center.
     *
     *
     * -- GETTER --
     *  Gets the X coordinate of the rotation center.
     *
     @param cx the X coordinate of the rotation center
      * @return the X coordinate of the rotation center
     */
    @Getter
    @Setter
    private float cx;
    /**
     * -- SETTER --
     *  Sets the Y coordinate of the rotation center.
     *
     *
     * -- GETTER --
     *  Gets the Y coordinate of the rotation center.
     *
     @param cy the Y coordinate of the rotation center
      * @return the Y coordinate of the rotation center
     */
    @Getter
    @Setter
    private float cy;
    /**
     * -- SETTER --
     *  Sets the Z coordinate of the rotation center.
     *
     *
     * -- GETTER --
     *  Gets the Z coordinate of the rotation center.
     *
     @param cz the Z coordinate of the rotation center
      * @return the Z coordinate of the rotation center
     */
    @Getter
    @Setter
    private float cz;

    /**
     * Constructs a new Rotate effect with the specified parameters.
     *
     * @param x the rotation angle around the X axis
     * @param y the rotation angle around the Y axis
     * @param z the rotation angle around the Z axis
     */
    public Rotate(float x, float y, float z) {
        this(x, y, z, 0, 0, 0);
    }

    public Rotate(float x, float y, float z, boolean autoCenter) {
        this(x, y, z, 0, 0, 0, autoCenter);
    }

    /**
     * Constructs a new Rotate effect with the specified parameters.
     *
     * @param x the rotation angle around the X axis
     * @param y the rotation angle around the Y axis
     * @param z the rotation angle around the Z axis
     */
    public Rotate(double x, double y, double z) {
        this((float) x, (float) y, (float) z, 0, 0, 0);
    }

    public Rotate(double x, double y, double z, boolean autoCenter) {
        this((float) x, (float) y, (float) z, 0, 0, 0, autoCenter);
    }

    /**
     * Constructs a new Rotate effect with the specified parameters.
     *
     * @param x  the rotation angle around the X axis
     * @param y  the rotation angle around the Y axis
     * @param z  the rotation angle around the Z axis
     * @param cx the X coordinate of the rotation center
     * @param cy the Y coordinate of the rotation center
     */
    public Rotate(float x, float y, float z, float cx, float cy) {
        this(x, y, z, cx, cy, 0);
    }

    /**
     * Constructs a new Rotate effect with the specified parameters.
     *
     * @param x  the rotation angle around the X axis
     * @param y  the rotation angle around the Y axis
     * @param z  the rotation angle around the Z axis
     * @param cx the X coordinate of the rotation center
     * @param cy the Y coordinate of the rotation center
     */
    public Rotate(double x, double y, double z, double cx, double cy) {
        this((float) x, (float) y, (float) z, (float) cx, (float) cy, 0);
    }

    /**
     * Constructs a new Rotate effect with the specified parameters.
     *
     * @param x  the rotation angle around the X axis
     * @param y  the rotation angle around the Y axis
     * @param z  the rotation angle around the Z axis
     * @param cx the X coordinate of the rotation center
     * @param cy the Y coordinate of the rotation center
     * @param cz the Z coordinate of the rotation center
     */
    public Rotate(float x, float y, float z, float cx, float cy, float cz, boolean autoCenter) {
        super(1, "Rotate", (Class<GLObject>) null);

        this.x = x;
        this.y = y;
        this.z = z;
        this.autoCenter = autoCenter;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
    }

    /**
     * Constructs a new Rotate effect with the specified parameters.
     *
     * @param x  the rotation angle around the X axis
     * @param y  the rotation angle around the Y axis
     * @param z  the rotation angle around the Z axis
     * @param cx the X coordinate of the rotation center
     * @param cy the Y coordinate of the rotation center
     * @param cz the Z coordinate of the rotation center
     */
    public Rotate(double x, double y, double z, double cx, double cy, double cz) {
        this((float) x, (float) y, (float) z, (float) cx, (float) cy, (float) cz, false);
    }

    /**
     * Pops the transformation from the stack and applies the inverse rotation to the base GL object.
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
     * Pushes the transformation onto the stack and applies the rotation to the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void push(GLObject baseGLObject) {
        if (autoCenter) {
            cx = baseGLObject.getCenterX();
            cy = baseGLObject.getCenterY();
        }

        baseGLObject.translate(cx, cy, cz).rotateXYZ(-latestX, -latestY, -latestZ).rotateXYZ(x, y, z).translate(-cx, -cy, -cz);

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