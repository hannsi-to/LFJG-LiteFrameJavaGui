package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

public class DirectionalBlur extends EffectBase {
    private float radius = 10f;
    private float angle = MathHelper.toRadians(45);

    DirectionalBlur() {
        super(Location.fromResource("shader/frameBuffer/filter/DirectionalBlur.fsh"), true, 19, "DirectionalBlur");
    }

    public static DirectionalBlur createDirectionBlur() {
        return new DirectionalBlur();
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
        getFrameBuffer().getShaderProgramFBO().setUniform("radius", radius * 10);
        getFrameBuffer().getShaderProgramFBO().setUniform("angle", angle);

        super.setUniform(baseGLObject);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}