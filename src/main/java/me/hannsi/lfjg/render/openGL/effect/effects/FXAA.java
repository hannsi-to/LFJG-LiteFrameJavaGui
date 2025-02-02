package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class FXAA extends EffectBase {
    private boolean useAlpha;

    public FXAA(Vector2f resolution, boolean useAlpha) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/FXAA.fsh"), true, 17, "FastApproximateAntiAliasing");

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
        getFrameBuffer().getShaderProgramFBO().setUniform("texelStep", new Vector2f(1.0f / getResolution().x(), 1.0f / getResolution().y()));
        getFrameBuffer().getShaderProgramFBO().setUniform("useAlpha", useAlpha);

        super.setUniform(baseGLObject);
    }

    public boolean isUseAlpha() {
        return useAlpha;
    }

    public void setUseAlpha(boolean useAlpha) {
        this.useAlpha = useAlpha;
    }
}
