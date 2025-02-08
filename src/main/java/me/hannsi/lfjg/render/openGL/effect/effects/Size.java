package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import org.joml.Vector2f;

/**
 * Class representing a Size effect in OpenGL.
 */
public class Size extends EffectBase {
    private float x;
    private float y;
    private float z;
    private float cx;
    private float cy;
    private float cz;

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     * @param z the scaling factor along the Z axis
     * @param cx the X coordinate of the scaling center
     * @param cy the Y coordinate of the scaling center
     * @param cz the Z coordinate of the scaling center
     */
    public Size(Vector2f resolution, float x, float y, float z, float cx, float cy, float cz) {
        super(resolution, 0, "Size", (Class<GLObject>) null);
        this.x = x;
        this.y = y;
        this.z = z;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     * @param z the scaling factor along the Z axis
     * @param cx the X coordinate of the scaling center
     * @param cy the Y coordinate of the scaling center
     * @param cz the Z coordinate of the scaling center
     */
    public Size(Vector2f resolution, double x, double y, double z, double cx, double cy, double cz) {
        this(resolution, (float) x, (float) y, (float) z, (float) cx, (float) cy, (float) cz);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     */
    public Size(Vector2f resolution, float x, float y) {
        this(resolution, x, y, 1.0f);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     */
    public Size(Vector2f resolution, double x, double y) {
        this(resolution, (float) x, (float) y, 1.0f);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     * @param z the scaling factor along the Z axis
     */
    public Size(Vector2f resolution, float x, float y, float z) {
        this(resolution, x, y, z, 0, 0, 0);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     * @param z the scaling factor along the Z axis
     */
    public Size(Vector2f resolution, double x, double y, double z) {
        this(resolution, x, y, z, 0, 0, 0);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     * @param cx the X coordinate of the scaling center
     * @param cy the Y coordinate of the scaling center
     */
    public Size(Vector2f resolution, float x, float y, float cx, float cy) {
        this(resolution, x, y, 1.0f, cx, cy, 0);
    }

    /**
     * Constructs a new Size effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param x the scaling factor along the X axis
     * @param y the scaling factor along the Y axis
     * @param cx the X coordinate of the scaling center
     * @param cy the Y coordinate of the scaling center
     */
    public Size(Vector2f resolution, double x, double y, double cx, double cy) {
        this(resolution, x, y, 1.0f, cx, cy, 0);
    }

    /**
     * Pops the transformation from the stack and applies the inverse scaling to the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void pop(GLObject baseGLObject) {
        baseGLObject.setModelMatrix(baseGLObject.getModelMatrix().translate(cx, cy, cz).scale(1 / x, 1 / y, 1 / z).translate(cx, -cy, -cz));

        super.pop(baseGLObject);
    }

    /**
     * Pushes the transformation onto the stack and applies the scaling to the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void push(GLObject baseGLObject) {
        baseGLObject.setModelMatrix(baseGLObject.getModelMatrix().translate(cx, cy, cz).scale(x, y, z).translate(cx, -cy, -cz));

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
     * Gets the scaling factor along the X axis.
     *
     * @return the scaling factor along the X axis
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the scaling factor along the X axis.
     *
     * @param x the scaling factor along the X axis
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the scaling factor along the Y axis.
     *
     * @return the scaling factor along the Y axis
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the scaling factor along the Y axis.
     *
     * @param y the scaling factor along the Y axis
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets the scaling factor along the Z axis.
     *
     * @return the scaling factor along the Z axis
     */
    public float getZ() {
        return z;
    }

    /**
     * Sets the scaling factor along the Z axis.
     *
     * @param z the scaling factor along the Z axis
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * Gets the X coordinate of the scaling center.
     *
     * @return the X coordinate of the scaling center
     */
    public float getCx() {
        return cx;
    }

    /**
     * Sets the X coordinate of the scaling center.
     *
     * @param cx the X coordinate of the scaling center
     */
    public void setCx(float cx) {
        this.cx = cx;
    }

    /**
     * Gets the Y coordinate of the scaling center.
     *
     * @return the Y coordinate of the scaling center
     */
    public float getCy() {
        return cy;
    }

    /**
     * Sets the Y coordinate of the scaling center.
     *
     * @param cy the Y coordinate of the scaling center
     */
    public void setCy(float cy) {
        this.cy = cy;
    }

    /**
     * Gets the Z coordinate of the scaling center.
     *
     * @return the Z coordinate of the scaling center
     */
    public float getCz() {
        return cz;
    }

    /**
     * Sets the Z coordinate of the scaling center.
     *
     * @param cz the Z coordinate of the scaling center
     */
    public void setCz(float cz) {
        this.cz = cz;
    }
}