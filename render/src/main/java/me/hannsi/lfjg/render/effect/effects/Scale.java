package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

/**
 * Class representing a Size effect in OpenGL.
 */
public class Scale extends EffectBase {
    protected float latestX = 1f;
    protected float latestY = 1f;
    protected float latestZ = 1f;
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
    private float x = 2f;
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
    private float y = 2f;
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
    private float z = 2f;
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
    private boolean autoCenter = true;
    @Getter
    @Setter
    private float cx = 500f;
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
    private float cy = 500f;
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
    private float cz = 0f;

    Scale() {
        super(0, "Scale", (Class<GLObject>) null);
    }

    public static Scale createScale() {
        return new Scale();
    }

    public Scale x(float x) {
        this.x = x;
        return this;
    }

    public Scale x(double x) {
        this.x = (float) x;
        return this;
    }

    public Scale y(float y) {
        this.y = y;
        return this;
    }

    public Scale y(double y) {
        this.y = (float) y;
        return this;
    }

    public Scale z(float z) {
        this.z = z;
        return this;
    }

    public Scale z(double z) {
        this.z = (float) z;
        return this;
    }

    public Scale autoCenter(boolean autoCenter) {
        this.autoCenter = autoCenter;
        return this;
    }

    public Scale cx(float cx) {
        this.cx = cx;
        return this;
    }

    public Scale cx(double cx) {
        this.cx = (float) cx;
        return this;
    }

    public Scale cy(float cy) {
        this.cy = cy;
        return this;
    }

    public Scale cy(double cy) {
        this.cy = (float) cy;
        return this;
    }

    public Scale cz(float cz) {
        this.cz = cz;
        return this;
    }

    public Scale cz(double cz) {
        this.cz = (float) cz;
        return this;
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

        baseGLObject.getTransform()
                .translate(cx, cy, cz)
                .scale(x / latestX, y / latestY, z / latestZ)
                .translate(-cx, -cy, -cz);

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