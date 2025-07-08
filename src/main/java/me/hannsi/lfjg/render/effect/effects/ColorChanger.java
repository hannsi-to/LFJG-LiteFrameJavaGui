package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.Location;

@Getter
@Setter
public class ColorChanger extends EffectBase {
    private boolean alpha;
    private Color targetColor;
    private Color newColor;

    public ColorChanger(boolean alpha, Color targetColor, Color newColor) {
        super(Location.fromResource("shader/frameBuffer/filter/ColorChanger.fsh"), true, 28, "ColorChanger");

        this.alpha = alpha;
        this.targetColor = targetColor;
        this.newColor = newColor;
    }

    public ColorChanger(Color targetColor, Color newColor) {
        this(false, targetColor, newColor);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("alpha", alpha);
        getFrameBuffer().getShaderProgramFBO().setUniform("targetColor", targetColor);
        getFrameBuffer().getShaderProgramFBO().setUniform("newColor", newColor);

        super.setUniform(baseGLObject);
    }

}
