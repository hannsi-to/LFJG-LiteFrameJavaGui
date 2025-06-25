package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

/**
 * Class representing a Size effect in OpenGL.
 */
public class Scale extends EffectBase {
    @Getter
    private final boolean autoCenter;
    protected float latestX;
    protected float latestY;
    protected float latestZ;
    /**
     * -- SETTER --
     * Sets the scaling factor along the X axis.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the scaling factor along the X axis.
     *
     * @param x the scaling factor along the X axis
     * @return the scaling factor along the X axis
     */
    @Getter
    @Setter
    private float x;
    /**
     * -- SETTER --
     * Sets the scaling factor along the Y axis.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the scaling factor along the Y axis.
     *
     * @param y the scaling factor along the Y axis
     * @return the scaling factor along the Y axis
     */
    @Getter
    @Setter
    private float y;
    /**
     * -- SETTER --
     * Sets the scaling factor along the Z axis.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the scaling factor along the Z axis.
     *
     * @param z the scaling factor along the Z axis
     * @return the scaling factor along the Z axis
     */
    @Getter
    @Setter
    private float z;
    /**
     * -- SETTER --
     * Sets the X coordinate of the scaling center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the X coordinate of the scaling center.
     *
     * @param cx the X coordinate of the scaling center
     * @return the X coordinate of the scaling center
     */
    @Getter
    @Setter
    private float cx;
    /**
     * -- SETTER --
     * Sets the Y coordinate of the scaling center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the Y coordinate of the scaling center.
     *
     * @param cy the Y coordinate of the scaling center
     * @return the Y coordinate of the scaling center
     */
    @Getter
    @Setter
    private float cy;
    /**
     * -- SETTER --
     * Sets the Z coordinate of the scaling center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the Z coordinate of the scaling center.
     *
     * @param cz the Z coordinate of the scaling center
     * @return the Z coordinate of the scaling center
     */
    @Getter
    @Setter
    private float cz;

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param x  the scaling factor along the X axis
     * @param y  the scaling factor along the Y axis
     * @param z  the scaling factor along the Z axis
     * @param cx the X coordinate of the scaling center
     * @param cy the Y coordinate of the scaling center
     * @param cz the Z coordinate of the scaling center
     */
    public Scale(float x, float y, float z, float cx, float cy, float cz, boolean autoCenter) {
        super(0, "Scale", (Class<GLObject>) null);
        this.x = x;
        this.y = y;
        this.z = z;
        this.autoCenter = autoCenter;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;

        this.latestX = 1;
        this.latestY = 1;
        this.latestZ = 1;
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param x  the scaling factor along the X axis
     * @param y  the scaling factor along the Y axis
     * @param z  the scaling factor along the Z axis
     * @param cx the X coordinate of the scaling center
     * @param cy the Y coordinate of the scaling center
     * @param cz the Z coordinate of the scaling center
     */
    public Scale(double x, double y, double z, double cx, double cy, double cz) {
        this((float) x, (float) y, (float) z, (float) cx, (float) cy, (float) cz, false);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     */
    public Scale(float x, float y) {
        this(x, y, 1.0f);
    }

    public Scale(float x, float y, boolean autoCenter) {
        this(x, y, 1.0f, autoCenter);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     */
    public Scale(double x, double y) {
        this((float) x, (float) y, 1.0f);
    }

    public Scale(double x, double y, boolean autoCenter) {
        this((float) x, (float) y, 1.0f, autoCenter);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     * @param z the scaling factor along the Z axis
     */
    public Scale(float x, float y, float z) {
        this(x, y, z, 0, 0, 0);
    }

    public Scale(float x, float y, float z, boolean autoCenter) {
        this(x, y, z, 0, 0, 0, autoCenter);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     * @param z the scaling factor along the Z axis
     */
    public Scale(double x, double y, double z) {
        this(x, y, z, 0, 0, 0);
    }

    public Scale(double x, double y, double z, boolean autoCenter) {
        this((float) x, (float) y, (float) z, 0, 0, 0, autoCenter);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param x  the scaling factor along the X axis
     * @param y  the scaling factor along the Y axis
     * @param cx the X coordinate of the scaling center
     * @param cy the Y coordinate of the scaling center
     */
    public Scale(float x, float y, float cx, float cy) {
        this(x, y, 1.0f, cx, cy, 0);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param x  the scaling factor along the X axis
     * @param y  the scaling factor along the Y axis
     * @param cx the X coordinate of the scaling center
     * @param cy the Y coordinate of the scaling center
     */
    public Scale(double x, double y, double cx, double cy) {
        this(x, y, 1.0f, cx, cy, 0);
    }

    /**
     * Pops the transformation from the stack and applies the inverse scaling to the base GL object.
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
     * Pushes the transformation onto the stack and applies the scaling to the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void push(GLObject baseGLObject) {
        if (autoCenter) {
            cx = baseGLObject.getTransform().getCenterX();
            cy = baseGLObject.getTransform().getCenterY();
        }

        baseGLObject.getTransform().translate(cx, cy, cz).scale(1 / latestX, 1 / latestY, 1 / latestZ).scale(x, y, z).translate(-cx, -cy, -cz);

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