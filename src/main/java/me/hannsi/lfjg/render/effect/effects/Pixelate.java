package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

/**
 * Class representing a Pixelate effect in OpenGL.
 */
@Getter
@Setter
public class Pixelate extends EffectBase {
    /**
     * -- SETTER --
     * Sets the size of the mosaic.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the size of the mosaic.
     *
     * @param mosaicSize the size of the mosaic
     * @return the size of the mosaic
     */
    private float mosaicSize = 10f;

    Pixelate() {
        super(Location.fromResource("shader/frameBuffer/filter/Pixelate.fsh"), true, 8, "Pixelate");
    }

    public static Pixelate createPixelate() {
        return new Pixelate();
    }

    public Pixelate mosaicSize(float mosaicSize) {
        this.mosaicSize = mosaicSize;
        return this;
    }

    public Pixelate mosaicSize(double mosaicSize) {
        this.mosaicSize = (float) mosaicSize;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("resolution", LFJGContext.frameBufferSize);
        getFrameBuffer().getShaderProgramFBO().setUniform("mosaicSize", mosaicSize);

        super.setUniform(baseGLObject);
    }

}