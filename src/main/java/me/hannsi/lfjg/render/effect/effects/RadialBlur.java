package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;

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
    private float range;
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
    private float centerX;
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
    private float centerY;

    /**
     * Constructs a new RadialBlur effect with the specified parameters.
     *
     * @param range   the range of the blur
     * @param centerX the X coordinate of the blur center
     * @param centerY the Y coordinate of the blur center
     */
    public RadialBlur(float range, float centerX, float centerY) {
        super(new ResourcesLocation("shader/frameBuffer/filter/RadialBlur.fsh"), true, 18, "RadialBlur");

        this.range = range;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * Constructs a new RadialBlur effect with the specified parameters.
     *
     * @param range   the range of the blur
     * @param centerX the X coordinate of the blur center
     * @param centerY the Y coordinate of the blur center
     */
    public RadialBlur(double range, double centerX, double centerY) {
        super(new ResourcesLocation("shader/frameBuffer/filter/RadialBlur.fsh"), true, 18, "RadialBlur");

        this.range = (float) range;
        this.centerX = (float) centerX;
        this.centerY = (float) centerY;
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