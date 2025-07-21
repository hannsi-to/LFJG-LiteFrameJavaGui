package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.core.utils.math.MathHelper;

/**
 * Class representing a Rotate effect in OpenGL.
 */
public class Rotate extends EffectBase {
    protected float latestX;
    protected float latestY;
    protected float latestZ;
    /**
     * -- SETTER --
     * Sets the rotation angle around the X axis.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the rotation angle around the X axis.
     *
     * @param x the rotation angle around the X axis
     * @return the rotation angle around the X axis
     */
    @Getter
    @Setter
    private float x = 0f;
    /**
     * -- SETTER --
     * Sets the rotation angle around the Y axis.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the rotation angle around the Y axis.
     *
     * @param y the rotation angle around the Y axis
     * @return the rotation angle around the Y axis
     */
    @Getter
    @Setter
    private float y = 0f;
    /**
     * -- SETTER --
     * Sets the rotation angle around the Z axis.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the rotation angle around the Z axis.
     *
     * @param z the rotation angle around the Z axis
     * @return the rotation angle around the Z axis
     */
    @Getter
    @Setter
    private float z = MathHelper.toRadians(45);
    @Getter
    @Setter
    private boolean autoCenter = true;
    /**
     * -- SETTER --
     * Sets the X coordinate of the rotation center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the X coordinate of the rotation center.
     *
     * @param cx the X coordinate of the rotation center
     * @return the X coordinate of the rotation center
     */
    @Getter
    @Setter
    private float cx = 500f;
    /**
     * -- SETTER --
     * Sets the Y coordinate of the rotation center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the Y coordinate of the rotation center.
     *
     * @param cy the Y coordinate of the rotation center
     * @return the Y coordinate of the rotation center
     */
    @Getter
    @Setter
    private float cy = 500f;
    /**
     * -- SETTER --
     * Sets the Z coordinate of the rotation center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the Z coordinate of the rotation center.
     *
     * @param cz the Z coordinate of the rotation center
     * @return the Z coordinate of the rotation center
     */
    @Getter
    @Setter
    private float cz = 0f;

    Rotate() {
        super(1, "Rotate", (Class<GLObject>) null);
    }

    public static Rotate createRotate() {
        return new Rotate();
    }

    public Rotate xRadian(float xRadian) {
        this.x = xRadian;
        return this;
    }

    public Rotate xRadian(double xRadian) {
        this.x = (float) xRadian;
        return this;
    }

    public Rotate xDegree(int xDegree) {
        this.x = MathHelper.toRadians(xDegree);
        return this;
    }

    public Rotate yRadian(float yRadian) {
        this.y = yRadian;
        return this;
    }

    public Rotate yRadian(double yRadian) {
        this.y = (float) yRadian;
        return this;
    }

    public Rotate yDegree(int yDegree) {
        this.y = MathHelper.toRadians(yDegree);
        return this;
    }

    public Rotate zRadian(float zRadian) {
        this.z = zRadian;
        return this;
    }

    public Rotate zRadian(double zRadian) {
        this.z = (float) zRadian;
        return this;
    }

    public Rotate zDegree(int zDegree) {
        this.z = MathHelper.toRadians(zDegree);
        return this;
    }

    public Rotate autoCenter(boolean autoCenter) {
        this.autoCenter = autoCenter;
        return this;
    }

    public Rotate cx(float cx) {
        this.cx = cx;
        return this;
    }

    public Rotate cx(double cx) {
        this.cx = (float) cx;
        return this;
    }

    public Rotate cy(float cy) {
        this.cy = cy;
        return this;
    }

    public Rotate cy(double cy) {
        this.cy = (float) cy;
        return this;
    }

    public Rotate cz(float cz) {
        this.cz = cz;
        return this;
    }

    public Rotate cz(double cz) {
        this.cz = (float) cz;
        return this;
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
            cx = baseGLObject.getTransform().getCenterX();
            cy = baseGLObject.getTransform().getCenterY();
        }

        baseGLObject.getTransform().translate(cx, cy, cz).rotateXYZ(-latestX, -latestY, -latestZ).rotateXYZ(x, y, z).translate(-cx, -cy, -cz);

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