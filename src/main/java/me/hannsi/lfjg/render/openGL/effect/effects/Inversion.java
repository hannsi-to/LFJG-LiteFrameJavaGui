package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class Inversion extends EffectBase {
    private Vector2f resolution;
    private boolean flipVertical;
    private boolean flipHorizontal;
    private boolean invertBrightness;
    private boolean invertHue;
    private boolean invertAlpha;

    public Inversion(Vector2f resolution, boolean flipVertical, boolean flipHorizontal, boolean invertBrightness, boolean invertHue, boolean invertAlpha) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Inversion.fsh"), true, 21, "Inversion");

        this.resolution = resolution;
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
        getFrameBuffer().getShaderProgramFBO().bind();
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("flipVertical", flipVertical);
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("flipHorizontal", flipHorizontal);
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("invertBrightness", invertBrightness);
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("invertHue", invertHue);
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("invertAlpha", invertAlpha);
        getFrameBuffer().getShaderProgramFBO().unbind();
        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
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
