package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

public class ColorChanger extends EffectBase {
    private boolean alpha = false;
    private Color targetColor = Color.YELLOW;
    private Color newColor = Color.RED;

    ColorChanger(String name) {
        super(name,false);
    }

    public static ColorChanger createColorChanger(String name) {
        return new ColorChanger(name);
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
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.COLOR_CHANGER.getId());
        shaderProgram.setUniform("colorChangerAlpha", UploadUniformType.ON_CHANGE, alpha);
        shaderProgram.setUniform("colorChangerTargetColor", UploadUniformType.ON_CHANGE, targetColor);
        shaderProgram.setUniform("colorChangerNewColor", UploadUniformType.ON_CHANGE, newColor);

        super.drawFrameBuffer(latestFrameBuffer);
    }
}
