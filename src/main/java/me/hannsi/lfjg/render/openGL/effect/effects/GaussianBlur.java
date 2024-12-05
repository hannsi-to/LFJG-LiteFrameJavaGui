package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;

public class GaussianBlur extends EffectBase {
    private float gaussianBlurOffsetX;
    private float gaussianBlurOffsetY;
    private int kernelSize;
    private float sigma;

    public GaussianBlur(float gaussianBlurOffsetX, float gaussianBlurOffsetY, int kernelSize, float sigma) {
        super(6, "GaussianBlur", (Class<GLObject>) null);

        this.gaussianBlurOffsetX = gaussianBlurOffsetX;
        this.gaussianBlurOffsetY = gaussianBlurOffsetY;
        this.kernelSize = kernelSize;
        this.sigma = sigma;
    }

    @Override
    public void pop(GLObject baseGLObject) {
        super.pop(baseGLObject);
    }

    @Override
    public void push(GLObject baseGLObject) {
        baseGLObject.getShaderProgram().setUniformBoolean("gaussianBlur", true);
        baseGLObject.getShaderProgram().setUniform1f("gaussianBlurOffsetX", gaussianBlurOffsetX);
        baseGLObject.getShaderProgram().setUniform1f("gaussianBlurOffsetY", gaussianBlurOffsetY);
        baseGLObject.getShaderProgram().setUniform1i("kernelSiz", kernelSize);
        baseGLObject.getShaderProgram().setUniform1f("sigma", sigma);

        super.push(baseGLObject);
    }

    public float getGaussianBlurOffsetX() {
        return gaussianBlurOffsetX;
    }

    public void setGaussianBlurOffsetX(float gaussianBlurOffsetX) {
        this.gaussianBlurOffsetX = gaussianBlurOffsetX;
    }

    public float getGaussianBlurOffsetY() {
        return gaussianBlurOffsetY;
    }

    public void setGaussianBlurOffsetY(float gaussianBlurOffsetY) {
        this.gaussianBlurOffsetY = gaussianBlurOffsetY;
    }

    public int getKernelSize() {
        return kernelSize;
    }

    public void setKernelSize(int kernelSize) {
        this.kernelSize = kernelSize;
    }

    public float getSigma() {
        return sigma;
    }

    public void setSigma(float sigma) {
        this.sigma = sigma;
    }
}
