package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

/**
 * Class representing a Luminance Key effect in OpenGL.
 */
@Getter
@Setter
public class LuminanceKey extends EffectBase {
    /**
     * -- SETTER --
     * Sets the threshold for the luminance key.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the threshold for the luminance key.
     *
     * @param threshold the threshold for the luminance key
     * @return the threshold for the luminance key
     */
    private float threshold = 0.6f;
    /**
     * -- SETTER --
     * Sets the amount of blur to apply.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the amount of blur to apply.
     *
     * @param blurAmount the amount of blur to apply
     * @return the amount of blur to apply
     */
    private float blurAmount = 0.1f;
    /**
     * -- SETTER --
     * Sets the mode of the luminance key.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the mode of the luminance key.
     *
     * @param luminanceMode the mode of the luminance key
     * @return the mode of the luminance key
     */
    private LuminanceMode luminanceMode = LuminanceMode.BOTH;

    LuminanceKey() {
        super(Location.fromResource("shader/frameBuffer/filter/LuminanceKey.fsh"), true, 13, "LuminanceKey");
    }

    public static LuminanceKey createLuminanceKey() {
        return new LuminanceKey();
    }

    public LuminanceKey threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public LuminanceKey threshold(double threshold) {
        this.threshold = (float) threshold;
        return this;
    }

    public LuminanceKey blurAmount(float blurAmount) {
        this.blurAmount = blurAmount;
        return this;
    }

    public LuminanceKey blurAmount(double blurAmount) {
        this.blurAmount = (float) blurAmount;
        return this;
    }

    public LuminanceKey luminanceMode(LuminanceMode luminanceMode) {
        this.luminanceMode = luminanceMode;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform("blurAmount", blurAmount);
        getFrameBuffer().getShaderProgramFBO().setUniform("mode", luminanceMode.getId());

        super.setUniform(baseGLObject);
    }

    /**
     * Enum representing the mode of the luminance key.
     */
    public enum LuminanceMode implements IEnumTypeBase {
        ONLY_DARK("OnlyDark", 0),
        ONLY_Light("OnlyLight", 1),
        BOTH("Both", 2);

        final String name;
        final int id;

        LuminanceMode(String name, int id) {
            this.name = name;
            this.id = id;
        }

        /**
         * Gets the ID of the luminance mode.
         *
         * @return the ID of the luminance mode
         */
        @Override
        public int getId() {
            return id;
        }

        /**
         * Gets the name of the luminance mode.
         *
         * @return the name of the luminance mode
         */
        @Override
        public String getName() {
            return name;
        }
    }
}