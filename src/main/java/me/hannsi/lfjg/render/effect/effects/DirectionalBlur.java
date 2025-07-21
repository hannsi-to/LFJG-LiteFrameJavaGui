package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

/**
 * Class representing a Directional Blur effect in OpenGL.
 */
@Getter
@Setter
public class DirectionalBlur extends EffectBase {
    /**
     * -- SETTER --
     * Sets the radius of the blur.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the radius of the blur.
     *
     * @param radius the radius of the blur
     * @return the radius of the blur
     */
    private float radius = 10f;
    /**
     * -- SETTER --
     * Sets the angle of the blur.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the angle of the blur.
     *
     * @param angle the angle of the blur
     * @return the angle of the blur
     */
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

    /**
     * Pushes the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();
        super.frameBufferPush(baseGLObject);
    }

    /**
     * Pops the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();
        super.frameBufferPop(baseGLObject);
    }

    /**
     * Draws the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();
        super.frameBuffer(baseGLObject);
    }

    /**
     * Sets the uniform variables for the shader program.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().getShaderProgramFBO().setUniform("radius", radius * 10);
        getFrameBuffer().getShaderProgramFBO().setUniform("angle", angle);

        super.setUniform(baseGLObject);
    }

}