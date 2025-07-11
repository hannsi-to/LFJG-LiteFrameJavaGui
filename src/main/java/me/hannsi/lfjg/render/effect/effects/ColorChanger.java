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
    private boolean alpha = false;
    private Color targetColor = Color.YELLOW;
    private Color newColor = Color.RED;

    ColorChanger() {
        super(Location.fromResource("shader/frameBuffer/filter/ColorChanger.fsh"), true, 28, "ColorChanger");
    }

    public static ColorChanger createColorChanger() {
        return new ColorChanger();
    }

    public ColorChanger alpha(boolean alpha) {
        this.alpha = alpha;
        return this;
    }

    public ColorChanger targetColor(Color targetColor) {
        this.targetColor = targetColor;
        return this;
    }

    public ColorChanger newColor(Color newColor) {
        this.newColor = newColor;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("alpha", alpha);
        getFrameBuffer().getShaderProgramFBO().setUniform("targetColor", targetColor);
        getFrameBuffer().getShaderProgramFBO().setUniform("newColor", newColor);

        super.setUniform(baseGLObject);
    }

}
