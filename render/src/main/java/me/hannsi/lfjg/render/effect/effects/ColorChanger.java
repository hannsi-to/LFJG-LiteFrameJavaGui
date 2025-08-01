package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

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
        getFrameBuffer().getShaderProgramFBO().setUniform("alpha", UploadUniformType.ON_CHANGE, alpha);
        getFrameBuffer().getShaderProgramFBO().setUniform("targetColor", UploadUniformType.ON_CHANGE, targetColor);
        getFrameBuffer().getShaderProgramFBO().setUniform("newColor", UploadUniformType.ON_CHANGE, newColor);

        super.setUniform(baseGLObject);
    }

    public boolean isAlpha() {
        return alpha;
    }

    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }

    public Color getTargetColor() {
        return targetColor;
    }

    public void setTargetColor(Color targetColor) {
        this.targetColor = targetColor;
    }

    public Color getNewColor() {
        return newColor;
    }

    public void setNewColor(Color newColor) {
        this.newColor = newColor;
    }
}
