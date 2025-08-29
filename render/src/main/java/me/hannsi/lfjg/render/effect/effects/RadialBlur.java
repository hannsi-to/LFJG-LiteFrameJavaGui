package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

public class RadialBlur extends EffectBase {
    private float range = 1f;
    private float centerX = 500f;
    private float centerY = 500f;

    RadialBlur(String name) {
        super(name, false);
    }

    public static RadialBlur createRadialBlur(String name) {
        return new RadialBlur(name);
    }

    public RadialBlur range(float range) {
        this.range = range;
        return this;
    }

    public RadialBlur range(double range) {
        this.range = (float) range;
        return this;
    }

    public RadialBlur centerX(float centerX) {
        this.centerX = centerX;
        return this;
    }

    public RadialBlur centerX(double centerX) {
        this.centerX = (float) centerX;
        return this;
    }

    public RadialBlur centerY(float centerY) {
        this.centerY = centerY;
        return this;
    }

    public RadialBlur centerY(double centerY) {
        this.centerY = (float) centerY;
        return this;
    }


    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.RADIAL_BLUR.getId());
        shaderProgram.setUniform("radialBlurRange", UploadUniformType.ON_CHANGE, range);
        shaderProgram.setUniform("radialBlurCenterX", UploadUniformType.ON_CHANGE, centerX);
        shaderProgram.setUniform("radialBlurCenterY", UploadUniformType.ON_CHANGE, centerY);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }
}
