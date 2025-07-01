package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.Location;

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
    private float radius;
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
    private float angle;

    /**
     * Constructs a new DirectionalBlur effect with the specified parameters.
     *
     * @param radius the radius of the blur
     * @param angle  the angle of the blur
     */
    public DirectionalBlur(float radius, float angle) {
        super(Location.fromResource("shader/frameBuffer/filter/DirectionalBlur.fsh"), true, 19, "DirectionalBlur");

        this.radius = radius;
        this.angle = angle;
    }

    /**
     * Constructs a new DirectionalBlur effect with the specified parameters.
     *
     * @param radius the radius of the blur
     * @param angle  the angle of the blur
     */
    public DirectionalBlur(double radius, double angle) {
        this((float) radius, (float) angle);
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
        getFrameBuffer().getShaderProgramFBO().setUniform1f("radius", radius * 10);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("angle", angle);

        super.setUniform(baseGLObject);
    }

}