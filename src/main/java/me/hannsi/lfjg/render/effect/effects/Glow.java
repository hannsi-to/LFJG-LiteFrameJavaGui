package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import org.joml.Vector3f;

/**
 * Class representing a Glow effect in OpenGL.
 */
@Getter
@Setter
public class Glow extends EffectBase {
    /**
     * -- SETTER --
     *  Sets the intensity of the glow.
     *
     *
     * -- GETTER --
     *  Gets the intensity of the glow.
     *
     @param intensity the intensity of the glow
      * @return the intensity of the glow
     */
    private float intensity;
    /**
     * -- SETTER --
     *  Sets the threshold for the glow effect.
     *
     *
     * -- GETTER --
     *  Gets the threshold for the glow effect.
     *
     @param threshold the threshold for the glow effect
      * @return the threshold for the glow effect
     */
    private float threshold;
    /**
     * -- SETTER --
     *  Sets the spread of the glow.
     *
     *
     * -- GETTER --
     *  Gets the spread of the glow.
     *
     @param spread the spread of the glow
      * @return the spread of the glow
     */
    private float spread;
    /**
     * -- SETTER --
     *  Sets whether to use the original color.
     *
     *
     * -- GETTER --
     *  Checks if the original color is used.
     *
     @param useOriginalColor true to use the original color, false otherwise
      * @return true if the original color is used, false otherwise
     */
    private boolean useOriginalColor;
    /**
     * -- SETTER --
     *  Sets the color of the glow.
     *
     *
     * -- GETTER --
     *  Gets the color of the glow.
     *
     @param glowColor the color of the glow
      * @return the color of the glow
     */
    private Color glowColor;
    /**
     * -- SETTER --
     *  Sets whether to apply only the glow effect.
     *
     *
     * -- GETTER --
     *  Checks if only the glow effect is applied.
     *
     @param glowOnly true to apply only the glow effect, false otherwise
      * @return true if only the glow effect is applied, false otherwise
     */
    private boolean glowOnly;

    /**
     * Constructs a new Glow effect with the specified parameters.
     *
     * @param intensity        the intensity of the glow
     * @param threshold        the threshold for the glow effect
     * @param spread           the spread of the glow
     * @param useOriginalColor whether to use the original color
     * @param glowColor        the color of the glow
     * @param glowOnly         whether to apply only the glow effect
     */
    public Glow(float intensity, float threshold, float spread, boolean useOriginalColor, Color glowColor, boolean glowOnly) {
        super(new ResourcesLocation("shader/frameBuffer/filter/Glow.fsh"), true, 11, "Glow");

        this.intensity = intensity;
        this.spread = spread;
        this.threshold = threshold;
        this.useOriginalColor = useOriginalColor;
        this.glowColor = glowColor;
        this.glowOnly = glowOnly;
    }

    /**
     * Constructs a new Glow effect with the specified parameters.
     *
     * @param intensity the intensity of the glow
     * @param threshold the threshold for the glow effect
     * @param spread    the spread of the glow
     * @param glowOnly  whether to apply only the glow effect
     */
    public Glow(float intensity, float threshold, float spread, boolean glowOnly) {
        this(intensity, threshold, spread, true, new Color(0, 0, 0, 0), glowOnly);
    }

    /**
     * Constructs a new Glow effect with the specified parameters.
     *
     * @param intensity        the intensity of the glow
     * @param threshold        the threshold for the glow effect
     * @param spread           the spread of the glow
     * @param useOriginalColor whether to use the original color
     * @param glowColor        the color of the glow
     * @param glowOnly         whether to apply only the glow effect
     */
    public Glow(double intensity, double threshold, double spread, boolean useOriginalColor, Color glowColor, boolean glowOnly) {
        super(new ResourcesLocation("shader/frameBuffer/filter/Glow.fsh"), true, 11, "Glow");

        this.intensity = (float) intensity;
        this.spread = (float) spread;
        this.threshold = (float) threshold;
        this.useOriginalColor = useOriginalColor;
        this.glowColor = glowColor;
        this.glowOnly = glowOnly;
    }

    /**
     * Constructs a new Glow effect with the specified parameters.
     *
     * @param intensity the intensity of the glow
     * @param threshold the threshold for the glow effect
     * @param spread    the spread of the glow
     * @param glowOnly  whether to apply only the glow effect
     */
    public Glow(double intensity, double threshold, double spread, boolean glowOnly) {
        this(intensity, threshold, spread, true, new Color(0, 0, 0, 0), glowOnly);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform("spread", spread);
        getFrameBuffer().getShaderProgramFBO().setUniform("glowColor", new Vector3f(glowColor.getRedF(), glowColor.getGreenF(), glowColor.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("useOriginalColor", useOriginalColor);
        getFrameBuffer().getShaderProgramFBO().setUniform("glowOnly", glowOnly);

        super.setUniform(baseGLObject);
    }

}