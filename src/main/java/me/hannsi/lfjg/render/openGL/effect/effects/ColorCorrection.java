package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;

/**
 * Class representing a Color Correction effect in OpenGL.
 */
public class ColorCorrection extends EffectBase {
    private float brightness;
    private float contrast;
    private float saturation;
    private float hue;

    /**
     * Constructs a new ColorCorrection effect with the specified parameters.
     *
     * @param brightness the brightness level
     * @param contrast   the contrast level
     * @param saturation the saturation level
     * @param hue        the hue level
     */
    public ColorCorrection(float brightness, float contrast, float saturation, float hue) {
        super(new ResourcesLocation("shader/frameBuffer/filter/ColorCorrection.fsh"), true, 4, "ColorCorrection");

        this.brightness = brightness;
        this.contrast = contrast;
        this.saturation = saturation;
        this.hue = hue;
    }

    /**
     * Constructs a new ColorCorrection effect with the specified parameters.
     *
     * @param brightness the brightness level
     * @param contrast   the contrast level
     * @param saturation the saturation level
     * @param hue        the hue level
     */
    public ColorCorrection(double brightness, double contrast, double saturation, double hue) {
        this((float) brightness, (float) contrast, (float) saturation, (float) hue);
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

    /**
     * Gets the brightness level.
     *
     * @return the brightness level
     */
    public float getBrightness() {
        return brightness;
    }

    /**
     * Sets the brightness level.
     *
     * @param brightness the brightness level
     */
    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    /**
     * Gets the contrast level.
     *
     * @return the contrast level
     */
    public float getContrast() {
        return contrast;
    }

    /**
     * Sets the contrast level.
     *
     * @param contrast the contrast level
     */
    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    /**
     * Gets the saturation level.
     *
     * @return the saturation level
     */
    public float getSaturation() {
        return saturation;
    }

    /**
     * Sets the saturation level.
     *
     * @param saturation the saturation level
     */
    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    /**
     * Gets the hue level.
     *
     * @return the hue level
     */
    public float getHue() {
        return hue;
    }

    /**
     * Sets the hue level.
     *
     * @param hue the hue level
     */
    public void setHue(float hue) {
        this.hue = hue;
    }
}