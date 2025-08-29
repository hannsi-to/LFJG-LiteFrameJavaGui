package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

public class DirectionalBlur extends EffectBase {
    private float radius = 10f;
    private float angle = MathHelper.toRadians(45);

    DirectionalBlur(String name) {
        super(name, false);
    }

    public static DirectionalBlur createDirectionBlur(String name) {
        return new DirectionalBlur(name);
    }

    public DirectionalBlur radius(float radius) {
        this.radius = radius;
        return this;
    }

    public DirectionalBlur radius(double radius) {
        this.radius = (float) radius;
        return this;
    }

    public DirectionalBlur angleRadian(float angleRadian) {
        this.angle = angleRadian;
        return this;
    }

    public DirectionalBlur angleRadian(double angleRadian) {
        this.angle = (float) angleRadian;
        return this;
    }

    public DirectionalBlur angleDegree(float angleDegree) {
        this.angle = MathHelper.toRadians(angleDegree);
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.DIRECTION_BLUR.getId());
        shaderProgram.setUniform("radius", UploadUniformType.ON_CHANGE, radius * 10);
        shaderProgram.setUniform("angle", UploadUniformType.ON_CHANGE, angle);

        super.drawFrameBuffer(latestFrameBuffer);
    }
}
