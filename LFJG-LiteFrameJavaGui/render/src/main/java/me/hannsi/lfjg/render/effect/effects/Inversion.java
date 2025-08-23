package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

public class Inversion extends EffectBase {
    private boolean flipVertical = true;
    private boolean flipHorizontal = true;
    private boolean invertBrightness = true;
    private boolean invertHue = true;
    private boolean invertAlpha = false;

    Inversion() {
        super(Location.fromResource("shader/frameBuffer/filter/Inversion.fsh"), true, 21, "Inversion");
    }

    public static Inversion createInversion() {
        return new Inversion();
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
        getFrameBuffer().getShaderProgramFBO().setUniform("flipVertical", UploadUniformType.ON_CHANGE, flipVertical);
        getFrameBuffer().getShaderProgramFBO().setUniform("flipHorizontal", UploadUniformType.ON_CHANGE, flipHorizontal);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertBrightness", UploadUniformType.ON_CHANGE, invertBrightness);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertHue", UploadUniformType.ON_CHANGE, invertHue);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertAlpha", UploadUniformType.ON_CHANGE, invertAlpha);

        super.setUniform(baseGLObject);
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