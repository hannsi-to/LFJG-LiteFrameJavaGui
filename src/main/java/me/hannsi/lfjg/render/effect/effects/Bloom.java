package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.Location;

/**
 * Class representing a Bloom effect in OpenGL.
 */
@Setter
@Getter
public class Bloom extends EffectBase {
    /**
     * -- GETTER --
     * Gets the intensity of the bloom.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the intensity of the bloom.
     *
     * @return the intensity of the bloom
     * @param intensity the intensity of the bloom
     */
    private float intensity;
    /**
     * -- GETTER --
     * Gets the spread of the bloom.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the spread of the bloom.
     *
     * @return the spread of the bloom
     * @param spread the spread of the bloom
     */
    private float spread;
    /**
     * -- GETTER --
     * Gets the threshold for the bloom.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the threshold for the bloom.
     *
     * @return the threshold for the bloom
     * @param threshold the threshold for the bloom
     */
    private float threshold;

    /**
     * Constructs a new Bloom effect with the specified parameters.
     *
     * @param intensity the intensity of the bloom
     * @param spread    the spread of the bloom
     * @param threshold the threshold for the bloom
     */
    public Bloom(float intensity, float spread, float threshold) {
        super(Location.fromResource("shader/frameBuffer/filter/Bloom.fsh"), true, 9, "Bloom");

        this.intensity = intensity;
        this.spread = spread;
        this.threshold = threshold;
    }

    /**
     * Constructs a new Bloom effect with the specified parameters.
     *
     * @param intensity the intensity of the bloom
     * @param spread    the spread of the bloom
     * @param threshold the threshold for the bloom
     */
    public Bloom(double intensity, double spread, double threshold) {
        this((float) intensity, (float) spread, (float) threshold);
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
        getFrameBuffer().getShaderProgramFBO().setUniform1f("intensity", intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("spread", spread);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("threshold", threshold);

        super.setUniform(baseGLObject);
    }

}