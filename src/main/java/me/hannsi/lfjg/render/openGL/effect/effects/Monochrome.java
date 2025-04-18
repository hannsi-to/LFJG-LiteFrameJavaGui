package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import org.joml.Vector3f;

/**
 * Class representing a Monochrome effect in OpenGL.
 */
public class Monochrome extends EffectBase {
    private float intensity;
    private Color color;
    private boolean preserveBrightness;

    /**
     * Constructs a new Monochrome effect with the specified parameters.
     *
     * @param intensity          the intensity of the monochrome effect
     * @param color              the color to be used for the monochrome effect
     * @param preserveBrightness whether to preserve the brightness
     */
    public Monochrome(float intensity, Color color, boolean preserveBrightness) {
        super(new ResourcesLocation("shader/frameBuffer/filter/Monochrome.fsh"), true, 23, "Monochrome");

        this.intensity = intensity;
        this.color = color;
        this.preserveBrightness = preserveBrightness;
    }

    /**
     * Constructs a new Monochrome effect with the specified parameters.
     *
     * @param intensity          the intensity of the monochrome effect
     * @param color              the color to be used for the monochrome effect
     * @param preserveBrightness whether to preserve the brightness
     */
    public Monochrome(double intensity, Color color, boolean preserveBrightness) {
        this((float) intensity, color, preserveBrightness);
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

    /**
     * Gets the intensity of the monochrome effect.
     *
     * @return the intensity of the monochrome effect
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity of the monochrome effect.
     *
     * @param intensity the intensity of the monochrome effect
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the color to be used for the monochrome effect.
     *
     * @return the color to be used for the monochrome effect
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color to be used for the monochrome effect.
     *
     * @param color the color to be used for the monochrome effect
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Checks if the brightness is preserved.
     *
     * @return true if the brightness is preserved, false otherwise
     */
    public boolean isPreserveBrightness() {
        return preserveBrightness;
    }

    /**
     * Sets whether to preserve the brightness.
     *
     * @param preserveBrightness true to preserve the brightness, false otherwise
     */
    public void setPreserveBrightness(boolean preserveBrightness) {
        this.preserveBrightness = preserveBrightness;
    }
}