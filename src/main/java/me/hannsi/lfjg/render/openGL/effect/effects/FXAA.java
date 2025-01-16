package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class FXAA extends EffectBase {
    private Vector2f resolution;
    private boolean useAlpha;

    public FXAA(Vector2f resolution, boolean useAlpha) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/FXAA.fsh"), true, 17, "FastApproximateAntiAliasing");

        this.resolution = resolution;
        this.useAlpha = useAlpha;
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
        getFrameBuffer().getShaderProgramFBO().setUniform2f("texelStep", new Vector2f(1.0f / resolution.x(), 1.0f / resolution.y()));
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("useAlpha", useAlpha);
        getFrameBuffer().getShaderProgramFBO().unbind();
        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
    }

    public boolean isUseAlpha() {
        return useAlpha;
    }

    public void setUseAlpha(boolean useAlpha) {
        this.useAlpha = useAlpha;
    }
}
