package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

public class BoxBlur extends EffectBase {
    private int kernelX = 10;
    private int kernelY = 10;

    BoxBlur() {
        super(Location.fromResource("shader/frameBuffer/filter/BoxBlur.fsh"), true, 16, "BoxBlur");
    }

    public static BoxBlur createBoxBlur() {
        return new BoxBlur();
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
        getFrameBuffer().getShaderProgramFBO().setUniform("kernelX", kernelX);
        getFrameBuffer().getShaderProgramFBO().setUniform("kernelY", kernelY);

        super.setUniform(baseGLObject);
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