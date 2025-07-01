package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

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
    private float threshold;
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
    private float blurAmount;
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
    private LuminanceMode luminanceMode;

    /**
     * Constructs a new LuminanceKey effect with the specified parameters.
     *
     * @param threshold     the threshold for the luminance key
     * @param blurAmount    the amount of blur to apply
     * @param luminanceMode the mode of the luminance key
     */
    public LuminanceKey(float threshold, float blurAmount, LuminanceMode luminanceMode) {
        super(Location.fromResource("shader/frameBuffer/filter/LuminanceKey.fsh"), true, 13, "LuminanceKey");

        this.threshold = threshold;
        this.blurAmount = blurAmount;
        this.luminanceMode = luminanceMode;
    }

    /**
     * Constructs a new LuminanceKey effect with the specified parameters.
     *
     * @param threshold     the threshold for the luminance key
     * @param blurAmount    the amount of blur to apply
     * @param luminanceMode the mode of the luminance key
     */
    public LuminanceKey(double threshold, double blurAmount, LuminanceMode luminanceMode) {
        this((float) threshold, (float) blurAmount, luminanceMode);
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
        getFrameBuffer().getShaderProgramFBO().setUniform1f("threshold", threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("blurAmount", blurAmount);
        getFrameBuffer().getShaderProgramFBO().setUniform1i("mode", luminanceMode.getId());

        super.setUniform(baseGLObject);
    }

    /**
     * Enum representing the mode of the luminance key.
     */
    public enum LuminanceMode implements IEnumTypeBase {
        OnlyDark("OnlyDark", 0), OnlyLight("OnlyLight", 1), Both("Both", 2);

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