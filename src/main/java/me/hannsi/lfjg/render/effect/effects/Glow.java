package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector3f;

/**
 * Class representing a Glow effect in OpenGL.
 */
@Getter
@Setter
public class Glow extends EffectBase {
    /**
     * -- SETTER --
     * Sets the intensity of the glow.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the intensity of the glow.
     *
     * @param intensity the intensity of the glow
     * @return the intensity of the glow
     */
    private float intensity = 1.0f;
    /**
     * -- SETTER --
     * Sets the threshold for the glow effect.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the threshold for the glow effect.
     *
     * @param threshold the threshold for the glow effect
     * @return the threshold for the glow effect
     */
    private float threshold = 0.7f;
    /**
     * -- SETTER --
     * Sets the spread of the glow.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the spread of the glow.
     *
     * @param spread the spread of the glow
     * @return the spread of the glow
     */
    private float spread = 3.0f;
    /**
     * -- SETTER --
     * Sets whether to use the original color.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the original color is used.
     *
     * @param useOriginalColor true to use the original color, false otherwise
     * @return true if the original color is used, false otherwise
     */
    private boolean useOriginalColor = true;
    /**
     * -- SETTER --
     * Sets the color of the glow.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the color of the glow.
     *
     * @param glowColor the color of the glow
     * @return the color of the glow
     */
    private Color glowColor = Color.GOLD;
    /**
     * -- SETTER --
     * Sets whether to apply only the glow effect.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if only the glow effect is applied.
     *
     * @param glowOnly true to apply only the glow effect, false otherwise
     * @return true if only the glow effect is applied, false otherwise
     */
    private boolean glowOnly = false;

    Glow() {
        super(Location.fromResource("shader/frameBuffer/filter/Glow.fsh"), true, 11, "Glow");
    }

    public static Glow createGlow() {
        return new Glow();
    }

    public Glow intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Glow intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Glow threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public Glow threshold(double threshold) {
        this.threshold = (float) threshold;
        return this;
    }

    public Glow spread(float spread) {
        this.spread = spread;
        return this;
    }

    public Glow spread(double spread) {
        this.spread = (float) spread;
        return this;
    }

    public Glow useOriginalColor(boolean useOriginalColor) {
        this.useOriginalColor = useOriginalColor;
        return this;
    }

    public Glow glowColor(Color glowColor) {
        this.glowColor = glowColor;
        return this;
    }

    public Glow glowOnly(boolean glowOnly) {
        this.glowOnly = glowOnly;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform("spread", spread);
        getFrameBuffer().getShaderProgramFBO().setUniform("glowColor", new Vector3f(glowColor.getRedF(), glowColor.getGreenF(), glowColor.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("useOriginalColor", useOriginalColor);
        getFrameBuffer().getShaderProgramFBO().setUniform("glowOnly", glowOnly);
        getFrameBuffer().getShaderProgramFBO().setUniform("texelSize", LFJGContext.frameBufferSize);

        super.setUniform(baseGLObject);
    }

}