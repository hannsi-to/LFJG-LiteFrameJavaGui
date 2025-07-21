package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

/**
 * Class representing a Box Blur effect in OpenGL.
 */
@Setter
@Getter
public class BoxBlur extends EffectBase {
    /**
     * -- GETTER --
     * Gets the kernel size in the x-direction.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the kernel size in the x-direction.
     *
     * @return the kernel size in the x-direction
     * @param kernelX the kernel size in the x-direction
     */
    private int kernelX = 10;
    /**
     * -- GETTER --
     * Gets the kernel size in the y-direction.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the kernel size in the y-direction.
     *
     * @return the kernel size in the y-direction
     * @param kernelY the kernel size in the y-direction
     */
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
        getFrameBuffer().getShaderProgramFBO().setUniform("kernelX", kernelX);
        getFrameBuffer().getShaderProgramFBO().setUniform("kernelY", kernelY);

        super.setUniform(baseGLObject);
    }

}