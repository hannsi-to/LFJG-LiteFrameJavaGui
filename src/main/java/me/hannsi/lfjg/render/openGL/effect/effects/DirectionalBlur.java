package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

/**
 * Class representing a Directional Blur effect in OpenGL.
 */
public class DirectionalBlur extends EffectBase {
    private float radius;
    private float angle;

    /**
     * Constructs a new DirectionalBlur effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param radius the radius of the blur
     * @param angle the angle of the blur
     */
    public DirectionalBlur(Vector2f resolution, float radius, float angle) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/DirectionalBlur.fsh"), true, 19, "DirectionalBlur");

        this.radius = radius;
        this.angle = angle;
    }

    /**
     * Constructs a new DirectionalBlur effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param radius the radius of the blur
     * @param angle the angle of the blur
     */
    public DirectionalBlur(Vector2f resolution, double radius, double angle) {
        this(resolution, (float) radius, (float) angle);
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

    /**
     * Gets the radius of the blur.
     *
     * @return the radius of the blur
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the blur.
     *
     * @param radius the radius of the blur
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * Gets the angle of the blur.
     *
     * @return the angle of the blur
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Sets the angle of the blur.
     *
     * @param angle the angle of the blur
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }
}