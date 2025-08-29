package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

public class Inversion extends EffectBase {
    private boolean flipVertical = true;
    private boolean flipHorizontal = true;
    private boolean invertBrightness = true;
    private boolean invertHue = true;
    private boolean invertAlpha = false;

    Inversion(String name) {
        super(name, false);
    }

    public static Inversion createInversion(String name) {
        return new Inversion(name);
    }

    public Inversion flipVertical(boolean flipVertical) {
        this.flipVertical = flipVertical;
        return this;
    }

    public Inversion flipHorizontal(boolean flipHorizontal) {
        this.flipHorizontal = flipHorizontal;
        return this;
    }

    public Inversion invertBrightness(boolean invertBrightness) {
        this.invertBrightness = invertBrightness;
        return this;
    }

    public Inversion invertHue(boolean invertHue) {
        this.invertHue = invertHue;
        return this;
    }

    public Inversion invertAlpha(boolean invertAlpha) {
        this.invertAlpha = invertAlpha;
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.INVERSION.getId());
        shaderProgram.setUniform("inversionFlipVertical", UploadUniformType.ON_CHANGE, flipVertical);
        shaderProgram.setUniform("inversionFlipHorizontal", UploadUniformType.ON_CHANGE, flipHorizontal);
        shaderProgram.setUniform("inversionInvertBrightness", UploadUniformType.ON_CHANGE, invertBrightness);
        shaderProgram.setUniform("inversionInvertHue", UploadUniformType.ON_CHANGE, invertHue);
        shaderProgram.setUniform("inversionInvertAlpha", UploadUniformType.ON_CHANGE, invertAlpha);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public boolean isFlipVertical() {
        return flipVertical;
    }

    public void setFlipVertical(boolean flipVertical) {
        this.flipVertical = flipVertical;
    }

    public boolean isFlipHorizontal() {
        return flipHorizontal;
    }

    public void setFlipHorizontal(boolean flipHorizontal) {
        this.flipHorizontal = flipHorizontal;
    }

    public boolean isInvertBrightness() {
        return invertBrightness;
    }

    public void setInvertBrightness(boolean invertBrightness) {
        this.invertBrightness = invertBrightness;
    }

    public boolean isInvertHue() {
        return invertHue;
    }

    public void setInvertHue(boolean invertHue) {
        this.invertHue = invertHue;
    }

    public boolean isInvertAlpha() {
        return invertAlpha;
    }

    public void setInvertAlpha(boolean invertAlpha) {
        this.invertAlpha = invertAlpha;
    }
}
