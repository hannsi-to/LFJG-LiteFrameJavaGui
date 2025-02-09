package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;

/**
 * Class representing a Rotate effect in OpenGL.
 */
public class Rotate extends EffectBase {
    private float x;
    private float y;
    private float z;
    private float cx;
    private float cy;
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
    public Rotate(float x, float y, float z, float cx, float cy, float cz) {
        super(1, "Rotate", (Class<GLObject>) null);

        this.x = x;
        this.y = y;
        this.z = z;
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
        this((float) x, (float) y, (float) z, (float) cx, (float) cy, (float) cz);
    }

    /**
     * Pops the transformation from the stack and applies the inverse rotation to the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void pop(GLObject baseGLObject) {
        baseGLObject.setModelMatrix(baseGLObject.getModelMatrix().translate(cx, cy, cz).rotateXYZ(-x, -y, -z).translate(cx, -cy, -cz));

        super.pop(baseGLObject);
    }

    /**
     * Pushes the transformation onto the stack and applies the rotation to the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void push(GLObject baseGLObject) {
        baseGLObject.setModelMatrix(baseGLObject.getModelMatrix().translate(cx, cy, cz).rotateXYZ(x, y, z).translate(cx, -cy, -cz));

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

    /**
     * Gets the rotation angle around the X axis.
     *
     * @return the rotation angle around the X axis
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the rotation angle around the X axis.
     *
     * @param x the rotation angle around the X axis
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the rotation angle around the Y axis.
     *
     * @return the rotation angle around the Y axis
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the rotation angle around the Y axis.
     *
     * @param y the rotation angle around the Y axis
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets the rotation angle around the Z axis.
     *
     * @return the rotation angle around the Z axis
     */
    public float getZ() {
        return z;
    }

    /**
     * Sets the rotation angle around the Z axis.
     *
     * @param z the rotation angle around the Z axis
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * Gets the X coordinate of the rotation center.
     *
     * @return the X coordinate of the rotation center
     */
    public float getCx() {
        return cx;
    }

    /**
     * Sets the X coordinate of the rotation center.
     *
     * @param cx the X coordinate of the rotation center
     */
    public void setCx(float cx) {
        this.cx = cx;
    }

    /**
     * Gets the Y coordinate of the rotation center.
     *
     * @return the Y coordinate of the rotation center
     */
    public float getCy() {
        return cy;
    }

    /**
     * Sets the Y coordinate of the rotation center.
     *
     * @param cy the Y coordinate of the rotation center
     */
    public void setCy(float cy) {
        this.cy = cy;
    }

    /**
     * Gets the Z coordinate of the rotation center.
     *
     * @return the Z coordinate of the rotation center
     */
    public float getCz() {
        return cz;
    }

    /**
     * Sets the Z coordinate of the rotation center.
     *
     * @param cz the Z coordinate of the rotation center
     */
    public void setCz(float cz) {
        this.cz = cz;
    }
}