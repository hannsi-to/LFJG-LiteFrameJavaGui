package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class Pixelate extends EffectBase {
    private final Vector2f resolution;
    private final float mosaicSize;

    public Pixelate(Vector2f resolution, float mosaicSize) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Pixelate.fsh"), true, 8, "Pixelate");

        this.resolution = resolution;
        this.mosaicSize = mosaicSize;
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
        getFrameBuffer().getShaderProgramFBO().bind();

        getFrameBuffer().getShaderProgramFBO().setUniform2f("resolution", resolution);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("mosaicSize", mosaicSize);

        getFrameBuffer().getShaderProgramFBO().unbind();

        super.setUniform(baseGLObject);
    }
}
