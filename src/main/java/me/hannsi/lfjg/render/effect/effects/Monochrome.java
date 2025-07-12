package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.Location;
import org.joml.Vector3f;

/**
 * Class representing a Monochrome effect in OpenGL.
 */
@Getter
@Setter
public class Monochrome extends EffectBase {
    /**
     * -- SETTER --
     * Sets the intensity of the monochrome effect.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the intensity of the monochrome effect.
     *
     * @param intensity the intensity of the monochrome effect
     * @return the intensity of the monochrome effect
     */
    private float intensity = 0.8f;
    /**
     * -- SETTER --
     * Sets the color to be used for the monochrome effect.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the color to be used for the monochrome effect.
     *
     * @param color the color to be used for the monochrome effect
     * @return the color to be used for the monochrome effect
     */
    private Color color = Color.DARK_GRAY;
    /**
     * -- SETTER --
     * Sets whether to preserve the brightness.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the brightness is preserved.
     *
     * @param preserveBrightness true to preserve the brightness, false otherwise
     * @return true if the brightness is preserved, false otherwise
     */
    private boolean preserveBrightness = true;

    Monochrome() {
        super(Location.fromResource("shader/frameBuffer/filter/Monochrome.fsh"), true, 23, "Monochrome");
    }

    public static Monochrome createMonochrome() {
        return new Monochrome();
    }

    public Monochrome intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Monochrome intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Monochrome color(Color color) {
        this.color = color;
        return this;
    }

    public Monochrome preserveBrightness(boolean preserveBrightness) {
        this.preserveBrightness = preserveBrightness;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform("color", new Vector3f(color.getRedF(), color.getGreenF(), color.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("preserveBrightness", preserveBrightness);

        super.setUniform(baseGLObject);
    }

}