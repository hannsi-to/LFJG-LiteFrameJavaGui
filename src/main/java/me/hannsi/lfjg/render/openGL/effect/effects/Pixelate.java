package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

/**
 * Class representing a Pixelate effect in OpenGL.
 */
public class Pixelate extends EffectBase {
    private float mosaicSize;

    /**
     * Constructs a new Pixelate effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param mosaicSize the size of the mosaic
     */
    public Pixelate(Vector2f resolution, float mosaicSize) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Pixelate.fsh"), true, 8, "Pixelate");

        this.mosaicSize = mosaicSize;
    }

    /**
     * Constructs a new Pixelate effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param mosaicSize the size of the mosaic
     */
    public Pixelate(Vector2f resolution, double mosaicSize) {
        this(resolution, (float) mosaicSize);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("resolution", getResolution());
        getFrameBuffer().getShaderProgramFBO().setUniform("mosaicSize", mosaicSize);

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the size of the mosaic.
     *
     * @return the size of the mosaic
     */
    public float getMosaicSize() {
        return mosaicSize;
    }

    /**
     * Sets the size of the mosaic.
     *
     * @param mosaicSize the size of the mosaic
     */
    public void setMosaicSize(float mosaicSize) {
        this.mosaicSize = mosaicSize;
    }
}