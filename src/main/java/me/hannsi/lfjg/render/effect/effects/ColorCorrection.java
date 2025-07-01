package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.Location;

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
    private float brightness;
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
    private float contrast;
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
    private float saturation;
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
        super(Location.fromResource("shader/frameBuffer/filter/ColorCorrection.fsh"), true, 4, "ColorCorrection");

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
        getFrameBuffer().getShaderProgramFBO().setUniform1f("brightness", brightness);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("contrast", contrast);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("saturation", saturation);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("hue", hue);

        super.setUniform(baseGLObject);
    }

}