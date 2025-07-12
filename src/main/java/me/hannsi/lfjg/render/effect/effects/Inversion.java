package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.Location;

/**
 * Class representing an Inversion effect in OpenGL.
 */
@Getter
@Setter
public class Inversion extends EffectBase {
    /**
     * -- SETTER --
     * Sets whether to flip the image vertically.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the image is flipped vertically.
     *
     * @param flipVertical true to flip the image vertically, false otherwise
     * @return true if the image is flipped vertically, false otherwise
     */
    private boolean flipVertical = true;
    /**
     * -- SETTER --
     * Sets whether to flip the image horizontally.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the image is flipped horizontally.
     *
     * @param flipHorizontal true to flip the image horizontally, false otherwise
     * @return true if the image is flipped horizontally, false otherwise
     */
    private boolean flipHorizontal = true;
    /**
     * -- SETTER --
     * Sets whether to invert the brightness.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the brightness is inverted.
     *
     * @param invertBrightness true to invert the brightness, false otherwise
     * @return true if the brightness is inverted, false otherwise
     */
    private boolean invertBrightness = true;
    /**
     * -- SETTER --
     * Sets whether to invert the hue.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the hue is inverted.
     *
     * @param invertHue true to invert the hue, false otherwise
     * @return true if the hue is inverted, false otherwise
     */
    private boolean invertHue = true;
    /**
     * -- SETTER --
     * Sets whether to invert the alpha.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the alpha is inverted.
     *
     * @param invertAlpha true to invert the alpha, false otherwise
     * @return true if the alpha is inverted, false otherwise
     */
    private boolean invertAlpha = false;

    Inversion() {
        super(Location.fromResource("shader/frameBuffer/filter/Inversion.fsh"), true, 21, "Inversion");
    }

    public static Inversion createInversion() {
        return new Inversion();
    }

    public Inversion flipVertical(boolean flipVertical) {
        this.flipVertical = flipVertical;
        return this;
    }

    public Inversion flipHorizontal(boolean flipHorizontal) {
        this.flipHorizontal = flipHorizontal;
        return this;
    }

    public Inversion invertBrightness(boolean invertBrightness) {
        this.invertBrightness = invertBrightness;
        return this;
    }

    public Inversion invertHue(boolean invertHue) {
        this.invertHue = invertHue;
        return this;
    }

    public Inversion invertAlpha(boolean invertAlpha) {
        this.invertAlpha = invertAlpha;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("flipVertical", flipVertical);
        getFrameBuffer().getShaderProgramFBO().setUniform("flipHorizontal", flipHorizontal);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertBrightness", invertBrightness);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertHue", invertHue);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertAlpha", invertAlpha);

        super.setUniform(baseGLObject);
    }

}