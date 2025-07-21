package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

/**
 * Class representing a Frame Buffer Contents effect in OpenGL.
 */
@Setter
@Getter
public class FrameBufferContents extends EffectBase {
    private float translateX = 0f;
    private float translateY = 0f;

    FrameBufferContents() {
        super(Location.fromResource("shader/frameBuffer/filter/FrameBufferContents.fsh"), true, 25, "FrameBufferContents");
    }

    public static FrameBufferContents createFrameBufferContents() {
        return new FrameBufferContents();
    }

    public FrameBufferContents translateX(float translateX) {
        this.translateX = translateX;
        return this;
    }

    public FrameBufferContents translateX(double translateX) {
        this.translateX = (float) translateX;
        return this;
    }

    public FrameBufferContents translateY(float translateY) {
        this.translateY = translateY;
        return this;
    }

    public FrameBufferContents translateY(double translateY) {
        this.translateY = (float) translateY;
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
        getFrameBuffer().getModelMatrix().translate(translateX, translateY, 0);
        getFrameBuffer().drawFrameBuffer();
        getFrameBuffer().getModelMatrix().translate(-translateX, -translateY, 0);

        super.frameBuffer(baseGLObject);
    }

    /**
     * Sets the uniform variables for the shader program.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();

        super.setUniform(baseGLObject);
    }
}