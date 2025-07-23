package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2i;
import org.joml.Vector4f;

/**
 * Class representing a 2D Clipping Rectangle effect in OpenGL.
 */
@Getter
@Setter
public class Clipping2DRect extends EffectBase {
    private Vector2i resolution = new Vector2i();
    /**
     * -- SETTER --
     * Sets the x-coordinate of the first corner.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the x-coordinate of the first corner.
     *
     * @param x1 the x-coordinate of the first corner
     * @return the x-coordinate of the first corner
     */
    private float x1 = 0f;
    /**
     * -- SETTER --
     * Sets the y-coordinate of the first corner.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the y-coordinate of the first corner.
     *
     * @param y1 the y-coordinate of the first corner
     * @return the y-coordinate of the first corner
     */
    private float y1 = 0f;
    /**
     * -- SETTER --
     * Sets the x-coordinate of the opposite corner.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the x-coordinate of the opposite corner.
     *
     * @param x2 the x-coordinate of the opposite corner
     * @return the x-coordinate of the opposite corner
     */
    private float x2 = 0f;
    /**
     * -- SETTER --
     * Sets the y-coordinate of the opposite corner.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the y-coordinate of the opposite corner.
     *
     * @param y2 the y-coordinate of the opposite corner
     * @return the y-coordinate of the opposite corner
     */
    private float y2 = 0f;
    /**
     * -- SETTER --
     * Sets whether the clipping region is inverted.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the clipping region is inverted.
     *
     * @param invert true to invert the clipping region, false otherwise
     * @return true if the clipping region is inverted, false otherwise
     */
    private boolean invert = false;

    Clipping2DRect() {
        super(Location.fromResource("shader/frameBuffer/filter/Clipping2D.fsh"), true, 5, "Clipping2DRect");
    }

    public static Clipping2DRect createClipping2DRect() {
        return new Clipping2DRect();
    }

    public Clipping2DRect resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
    }

    public Clipping2DRect x1(float x1) {
        this.x1 = x1;
        return this;
    }

    public Clipping2DRect x1(double x1) {
        this.x1 = (float) x1;
        return this;
    }

    public Clipping2DRect x1(int x1) {
        this.x1 = x1;
        return this;
    }

    public Clipping2DRect y1(float y1) {
        this.y1 = y1;
        return this;
    }

    public Clipping2DRect y1(double y1) {
        this.y1 = (float) y1;
        return this;
    }

    public Clipping2DRect y1(int y1) {
        this.y1 = y1;
        return this;
    }

    public Clipping2DRect x2(float x2) {
        this.x2 = x2;
        return this;
    }

    public Clipping2DRect x2(double x2) {
        this.x2 = (float) x2;
        return this;
    }

    public Clipping2DRect x2(int x2) {
        this.x2 = x2;
        return this;
    }

    public Clipping2DRect y2(float y2) {
        this.y2 = y2;
        return this;
    }

    public Clipping2DRect y2(double y2) {
        this.y2 = (float) y2;
        return this;
    }

    public Clipping2DRect y2(int y2) {
        this.y2 = y2;
        return this;
    }

    public Clipping2DRect invert(boolean invert) {
        this.invert = invert;
        return this;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("resolution", resolution);
        getFrameBuffer().getShaderProgramFBO().setUniform("clippingRect2DBool", true);
        getFrameBuffer().getShaderProgramFBO().setUniform("clippingRect2DInvert", invert);
        getFrameBuffer().getShaderProgramFBO().setUniform("clippingRect2DSize", new Vector4f(x1, y1, x2, y2));

        super.setUniform(baseGLObject);
    }

}