package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

public class FXAA extends EffectBase {
    private boolean useAlpha = true;

    FXAA(String name) {
        super(name, false);
    }

    public static FXAA createFXAA(String name) {
        return new FXAA(name);
    }

    public FXAA useAlpha(boolean useAlpha) {
        this.useAlpha = useAlpha;
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.FXAA.getId());
        shaderProgram.setUniform("fxaaUseAlpha", UploadUniformType.ON_CHANGE, useAlpha);

        super.drawFrameBuffer(latestFrameBuffer);
    }
}
