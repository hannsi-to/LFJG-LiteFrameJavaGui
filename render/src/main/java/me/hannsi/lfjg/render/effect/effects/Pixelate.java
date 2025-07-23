package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2i;

public class Pixelate extends EffectBase {
    private Vector2i resolution = new Vector2i();
    private float mosaicSize = 10f;

    Pixelate() {
        super(Location.fromResource("shader/frameBuffer/filter/Pixelate.fsh"), true, 8, "Pixelate");
    }

    public static Pixelate createPixelate() {
        return new Pixelate();
    }

    public Pixelate resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
    }

    public Pixelate mosaicSize(float mosaicSize) {
        this.mosaicSize = mosaicSize;
        return this;
    }

    public Pixelate mosaicSize(double mosaicSize) {
        this.mosaicSize = (float) mosaicSize;
        return this;
    }

    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();

        super.frameBufferPush(baseGLObject);
    }

    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();

        super.frameBufferPop(baseGLObject);
    }

    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();

        super.frameBuffer(baseGLObject);
    }

    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().getShaderProgramFBO().setUniform("resolution", resolution);
        getFrameBuffer().getShaderProgramFBO().setUniform("mosaicSize", mosaicSize);

        super.setUniform(baseGLObject);
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public void setResolution(Vector2i resolution) {
        this.resolution = resolution;
    }

    public float getMosaicSize() {
        return mosaicSize;
    }

    public void setMosaicSize(float mosaicSize) {
        this.mosaicSize = mosaicSize;
    }
}