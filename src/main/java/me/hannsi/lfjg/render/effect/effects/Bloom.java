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
    private float intensity = 1f;
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
    private float spread = 0f;
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
    private float threshold = 1f;

    Bloom() {
        super(Location.fromResource("shader/frameBuffer/filter/Bloom.fsh"), true, 9, "Bloom");
    }

    public static Bloom createBloom() {
        return new Bloom();
    }

    public Bloom intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Bloom intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Bloom spread(float spread) {
        this.spread = spread;
        return this;
    }

    public Bloom spread(double spread) {
        this.spread = (float) spread;
        return this;
    }

    public Bloom threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public Bloom threshold(double threshold) {
        this.threshold = (float) threshold;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("spread", spread);
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", threshold);

        super.setUniform(baseGLObject);
    }

}