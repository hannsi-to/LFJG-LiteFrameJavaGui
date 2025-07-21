package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

/**
 * Class representing a Radial Blur effect in OpenGL.
 */
@Getter
@Setter
public class RadialBlur extends EffectBase {
    /**
     * -- SETTER --
     * Sets the range of the blur.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the range of the blur.
     *
     * @param range the range of the blur
     * @return the range of the blur
     */
    private float range = 1f;
    /**
     * -- SETTER --
     * Sets the X coordinate of the blur center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the X coordinate of the blur center.
     *
     * @param centerX the X coordinate of the blur center
     * @return the X coordinate of the blur center
     */
    private float centerX = 500f;
    /**
     * -- SETTER --
     * Sets the Y coordinate of the blur center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the Y coordinate of the blur center.
     *
     * @param centerY the Y coordinate of the blur center
     * @return the Y coordinate of the blur center
     */
    private float centerY = 500f;

    RadialBlur() {
        super(Location.fromResource("shader/frameBuffer/filter/RadialBlur.fsh"), true, 18, "RadialBlur");
    }

    public static RadialBlur createRadialBlur() {
        return new RadialBlur();
    }

    public RadialBlur range(float range) {
        this.range = range;
        return this;
    }

    public RadialBlur range(double range) {
        this.range = (float) range;
        return this;
    }

    public RadialBlur centerX(float centerX) {
        this.centerX = centerX;
        return this;
    }

    public RadialBlur centerX(double centerX) {
        this.centerX = (float) centerX;
        return this;
    }

    public RadialBlur centerY(float centerY) {
        this.centerY = centerY;
        return this;
    }

    public RadialBlur centerY(double centerY) {
        this.centerY = (float) centerY;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("range", range);
        getFrameBuffer().getShaderProgramFBO().setUniform("centerX", centerX / LFJGContext.frameBufferSize.x);
        getFrameBuffer().getShaderProgramFBO().setUniform("centerY", centerY / LFJGContext.frameBufferSize.y);

        super.setUniform(baseGLObject);
    }

}