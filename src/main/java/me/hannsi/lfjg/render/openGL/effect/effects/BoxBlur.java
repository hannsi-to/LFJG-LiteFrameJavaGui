package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class BoxBlur extends EffectBase {
    private Vector2f resolution;
    private int kernelX;
    private int kernelY;

    public BoxBlur(Vector2f resolution, int kernelX, int kernelY) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/BoxBlur.fsh"), true, 16, "BoxBlur");

        this.resolution = resolution;
        this.kernelX = kernelX;
        this.kernelY = kernelY;
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

//        getFrameBuffer().getShaderProgramFBO().setUniform2f("resolution", resolution);
        getFrameBuffer().getShaderProgramFBO().setUniform1i("kernelX", kernelX);
        getFrameBuffer().getShaderProgramFBO().setUniform1i("kernelY", kernelY);

        getFrameBuffer().getShaderProgramFBO().unbind();

        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
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
