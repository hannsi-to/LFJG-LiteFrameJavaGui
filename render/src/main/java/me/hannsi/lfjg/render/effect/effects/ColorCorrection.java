package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

/**
 * Class representing a Color Correction effect in OpenGL.
 */
@Getter
@Setter
public class ColorCorrection extends EffectBase {
    /**
     * -- SETTER --
     * Sets the brightness level.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the brightness level.
     *
     * @param brightness the brightness level
     * @return the brightness level
     */
    private float brightness = 0.5f;
    /**
     * -- SETTER --
     * Sets the contrast level.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the contrast level.
     *
     * @param contrast the contrast level
     * @return the contrast level
     */
    private float contrast = 0.5f;
    /**
     * -- SETTER --
     * Sets the saturation level.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the saturation level.
     *
     * @param saturation the saturation level
     * @return the saturation level
     */
    private float saturation = 0.5f;
    /**
     * -- SETTER --
     * Sets the hue level.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the hue level.
     *
     * @param hue the hue level
     * @return the hue level
     */
    private float hue = 0.5f;

    ColorCorrection() {
        super(Location.fromResource("shader/frameBuffer/filter/ColorCorrection.fsh"), true, 4, "ColorCorrection");
    }

    public static ColorCorrection createColorCorrection() {
        return new ColorCorrection();
    }

    public ColorCorrection brightness(float brightness) {
        this.brightness = brightness;
        return this;
    }

    public ColorCorrection brightness(double brightness) {
        this.brightness = (float) brightness;
        return this;
    }

    public ColorCorrection brightness(int brightness) {
        this.brightness = brightness / 255f;
        return this;
    }

    public ColorCorrection contrast(float contrast) {
        this.contrast = contrast;
        return this;
    }

    public ColorCorrection contrast(double contrast) {
        this.contrast = (float) contrast;
        return this;
    }

    public ColorCorrection contrast(int contrast) {
        this.contrast = contrast / 255f;
        return this;
    }

    public ColorCorrection saturation(float saturation) {
        this.saturation = saturation;
        return this;
    }

    public ColorCorrection saturation(double saturation) {
        this.saturation = (float) saturation;
        return this;
    }

    public ColorCorrection saturation(int saturation) {
        this.saturation = saturation / 255f;
        return this;
    }

    public ColorCorrection hue(float hue) {
        this.hue = hue;
        return this;
    }

    public ColorCorrection hue(double hue) {
        this.hue = (float) hue;
        return this;
    }

    public ColorCorrection hue(int hue) {
        this.hue = hue / 255f;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("brightness", brightness);
        getFrameBuffer().getShaderProgramFBO().setUniform("contrast", contrast);
        getFrameBuffer().getShaderProgramFBO().setUniform("saturation", saturation);
        getFrameBuffer().getShaderProgramFBO().setUniform("hue", hue);

        super.setUniform(baseGLObject);
    }

}