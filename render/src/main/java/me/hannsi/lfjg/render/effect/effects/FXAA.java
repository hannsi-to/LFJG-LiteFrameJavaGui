package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.frameBuffer.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.SHADER_PROGRAM;

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
        SHADER_PROGRAM.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.FXAA.getId());
        SHADER_PROGRAM.setUniform("fxaaUseAlpha", UploadUniformType.ON_CHANGE, useAlpha);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public boolean isUseAlpha() {
        return useAlpha;
    }

    public void setUseAlpha(boolean useAlpha) {
        this.useAlpha = useAlpha;
    }
}
