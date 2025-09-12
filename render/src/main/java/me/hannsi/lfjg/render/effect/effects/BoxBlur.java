package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

public class BoxBlur extends EffectBase {
    private int kernelX = 10;
    private int kernelY = 10;

    BoxBlur(String name) {
        super(name, false);
    }

    public static BoxBlur createBoxBlur(String name) {
        return new BoxBlur(name);
    }

    public BoxBlur kernelX(int kernelX) {
        this.kernelX = kernelX;
        return this;
    }

    public BoxBlur kernelY(int kernelY) {
        this.kernelY = kernelY;
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        LFJGRenderContext.shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.BOX_BLUR.getId());
        LFJGRenderContext.shaderProgram.setUniform("boxBlurKernelX", UploadUniformType.ON_CHANGE, kernelX);
        LFJGRenderContext.shaderProgram.setUniform("boxBlurKernelY", UploadUniformType.ON_CHANGE, kernelY);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public int getKernelX() {
        return kernelX;
    }

    public void setKernelX(int kernelX) {
        this.kernelX = kernelX;
    }

    public int getKernelY() {
        return kernelY;
    }

    public void setKernelY(int kernelY) {
        this.kernelY = kernelY;
    }
}
