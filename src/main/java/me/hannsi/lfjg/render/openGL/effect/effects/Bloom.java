package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

/**
 * Class representing a Bloom effect in OpenGL.
 */
public class Bloom extends EffectBase {
    private float intensity;
    private float spread;
    private float threshold;

    /**
     * Constructs a new Bloom effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param intensity the intensity of the bloom
     * @param spread the spread of the bloom
     * @param threshold the threshold for the bloom
     */
    public Bloom(Vector2f resolution, float intensity, float spread, float threshold) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Bloom.fsh"), true, 9, "Bloom");

        this.intensity = intensity;
        this.spread = spread;
        this.threshold = threshold;
    }

    /**
     * Constructs a new Bloom effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param intensity the intensity of the bloom
     * @param spread the spread of the bloom
     * @param threshold the threshold for the bloom
     */
    public Bloom(Vector2f resolution, double intensity, double spread, double threshold) {
        this(resolution, (float) intensity, (float) spread, (float) threshold);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("spread", spread);
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", threshold);

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the intensity of the bloom.
     *
     * @return the intensity of the bloom
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity of the bloom.
     *
     * @param intensity the intensity of the bloom
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the spread of the bloom.
     *
     * @return the spread of the bloom
     */
    public float getSpread() {
        return spread;
    }

    /**
     * Sets the spread of the bloom.
     *
     * @param spread the spread of the bloom
     */
    public void setSpread(float spread) {
        this.spread = spread;
    }

    /**
     * Gets the threshold for the bloom.
     *
     * @return the threshold for the bloom
     */
    public float getThreshold() {
        return threshold;
    }

    /**
     * Sets the threshold for the bloom.
     *
     * @param threshold the threshold for the bloom
     */
    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
}