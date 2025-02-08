package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import org.joml.Vector2f;

/**
 * Class representing a Luminance Key effect in OpenGL.
 */
public class LuminanceKey extends EffectBase {
    private float threshold;
    private float blurAmount;
    private LuminanceMode luminanceMode;

    /**
     * Constructs a new LuminanceKey effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param threshold the threshold for the luminance key
     * @param blurAmount the amount of blur to apply
     * @param luminanceMode the mode of the luminance key
     */
    public LuminanceKey(Vector2f resolution, float threshold, float blurAmount, LuminanceMode luminanceMode) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/LuminanceKey.fsh"), true, 13, "LuminanceKey");

        this.threshold = threshold;
        this.blurAmount = blurAmount;
        this.luminanceMode = luminanceMode;
    }

    /**
     * Constructs a new LuminanceKey effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param threshold the threshold for the luminance key
     * @param blurAmount the amount of blur to apply
     * @param luminanceMode the mode of the luminance key
     */
    public LuminanceKey(Vector2f resolution, double threshold, double blurAmount, LuminanceMode luminanceMode) {
        this(resolution, (float) threshold, (float) blurAmount, luminanceMode);
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
        getFrameBuffer().getShaderProgramFBO().setUniform1i("mode", luminanceMode.getId());

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the threshold for the luminance key.
     *
     * @return the threshold for the luminance key
     */
    public float getThreshold() {
        return threshold;
    }

    /**
     * Sets the threshold for the luminance key.
     *
     * @param threshold the threshold for the luminance key
     */
    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    /**
     * Gets the amount of blur to apply.
     *
     * @return the amount of blur to apply
     */
    public float getBlurAmount() {
        return blurAmount;
    }

    /**
     * Sets the amount of blur to apply.
     *
     * @param blurAmount the amount of blur to apply
     */
    public void setBlurAmount(float blurAmount) {
        this.blurAmount = blurAmount;
    }

    /**
     * Gets the mode of the luminance key.
     *
     * @return the mode of the luminance key
     */
    public LuminanceMode getLuminanceMode() {
        return luminanceMode;
    }

    /**
     * Sets the mode of the luminance key.
     *
     * @param luminanceMode the mode of the luminance key
     */
    public void setLuminanceMode(LuminanceMode luminanceMode) {
        this.luminanceMode = luminanceMode;
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