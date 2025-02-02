package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class Inversion extends EffectBase {
    private boolean flipVertical;
    private boolean flipHorizontal;
    private boolean invertBrightness;
    private boolean invertHue;
    private boolean invertAlpha;

    public Inversion(Vector2f resolution, boolean flipVertical, boolean flipHorizontal, boolean invertBrightness, boolean invertHue, boolean invertAlpha) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Inversion.fsh"), true, 21, "Inversion");

        this.flipVertical = flipVertical;
        this.flipHorizontal = flipHorizontal;
        this.invertBrightness = invertBrightness;
        this.invertHue = invertHue;
        this.invertAlpha = invertAlpha;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("flipVertical", flipVertical);
        getFrameBuffer().getShaderProgramFBO().setUniform("flipHorizontal", flipHorizontal);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertBrightness", invertBrightness);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertHue", invertHue);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertAlpha", invertAlpha);

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
