package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;

/**
 * Class representing an Inversion effect in OpenGL.
 */
public class Inversion extends EffectBase {
    private boolean flipVertical;
    private boolean flipHorizontal;
    private boolean invertBrightness;
    private boolean invertHue;
    private boolean invertAlpha;

    /**
     * Constructs a new Inversion effect with the specified parameters.
     *
     * @param flipVertical     whether to flip the image vertically
     * @param flipHorizontal   whether to flip the image horizontally
     * @param invertBrightness whether to invert the brightness
     * @param invertHue        whether to invert the hue
     * @param invertAlpha      whether to invert the alpha
     */
    public Inversion(boolean flipVertical, boolean flipHorizontal, boolean invertBrightness, boolean invertHue, boolean invertAlpha) {
        super(new ResourcesLocation("shader/frameBuffer/filter/Inversion.fsh"), true, 21, "Inversion");

        this.flipVertical = flipVertical;
        this.flipHorizontal = flipHorizontal;
        this.invertBrightness = invertBrightness;
        this.invertHue = invertHue;
        this.invertAlpha = invertAlpha;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("flipVertical", flipVertical);
        getFrameBuffer().getShaderProgramFBO().setUniform("flipHorizontal", flipHorizontal);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertBrightness", invertBrightness);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertHue", invertHue);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertAlpha", invertAlpha);

        super.setUniform(baseGLObject);
    }

    /**
     * Checks if the image is flipped vertically.
     *
     * @return true if the image is flipped vertically, false otherwise
     */
    public boolean isFlipVertical() {
        return flipVertical;
    }

    /**
     * Sets whether to flip the image vertically.
     *
     * @param flipVertical true to flip the image vertically, false otherwise
     */
    public void setFlipVertical(boolean flipVertical) {
        this.flipVertical = flipVertical;
    }

    /**
     * Checks if the image is flipped horizontally.
     *
     * @return true if the image is flipped horizontally, false otherwise
     */
    public boolean isFlipHorizontal() {
        return flipHorizontal;
    }

    /**
     * Sets whether to flip the image horizontally.
     *
     * @param flipHorizontal true to flip the image horizontally, false otherwise
     */
    public void setFlipHorizontal(boolean flipHorizontal) {
        this.flipHorizontal = flipHorizontal;
    }

    /**
     * Checks if the brightness is inverted.
     *
     * @return true if the brightness is inverted, false otherwise
     */
    public boolean isInvertBrightness() {
        return invertBrightness;
    }

    /**
     * Sets whether to invert the brightness.
     *
     * @param invertBrightness true to invert the brightness, false otherwise
     */
    public void setInvertBrightness(boolean invertBrightness) {
        this.invertBrightness = invertBrightness;
    }

    /**
     * Checks if the hue is inverted.
     *
     * @return true if the hue is inverted, false otherwise
     */
    public boolean isInvertHue() {
        return invertHue;
    }

    /**
     * Sets whether to invert the hue.
     *
     * @param invertHue true to invert the hue, false otherwise
     */
    public void setInvertHue(boolean invertHue) {
        this.invertHue = invertHue;
    }

    /**
     * Checks if the alpha is inverted.
     *
     * @return true if the alpha is inverted, false otherwise
     */
    public boolean isInvertAlpha() {
        return invertAlpha;
    }

    /**
     * Sets whether to invert the alpha.
     *
     * @param invertAlpha true to invert the alpha, false otherwise
     */
    public void setInvertAlpha(boolean invertAlpha) {
        this.invertAlpha = invertAlpha;
    }
}