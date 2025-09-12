package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

public class Pixelate extends EffectBase {
    private float mosaicSize = 10f;

    Pixelate(String name) {
        super(name, false);
    }

    public static Pixelate createPixelate(String name) {
        return new Pixelate(name);
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
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        LFJGRenderContext.shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.PIXELATE.getId());
        LFJGRenderContext.shaderProgram.setUniform("pixelateMosaicSize", UploadUniformType.ON_CHANGE, mosaicSize);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public float getMosaicSize() {
        return mosaicSize;
    }

    public void setMosaicSize(float mosaicSize) {
        this.mosaicSize = mosaicSize;
    }
}
