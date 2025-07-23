package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class FXAA extends EffectBase {
    private Vector2i resolution = new Vector2i();
    private boolean useAlpha = false;

    FXAA() {
        super(Location.fromResource("shader/frameBuffer/filter/FXAA.fsh"), true, 17, "FastApproximateAntiAliasing");
    }

    public static FXAA createFXAA() {
        return new FXAA();
    }

    public FXAA resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
    }

    public FXAA useAlpha(boolean useAlpha) {
        this.useAlpha = useAlpha;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("texelStep", new Vector2f(1.0f / resolution.x(), 1.0f / resolution.y()));
        getFrameBuffer().getShaderProgramFBO().setUniform("useAlpha", useAlpha);

        super.setUniform(baseGLObject);
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public void setResolution(Vector2i resolution) {
        this.resolution = resolution;
    }

    public boolean isUseAlpha() {
        return useAlpha;
    }

    public void setUseAlpha(boolean useAlpha) {
        this.useAlpha = useAlpha;
    }
}