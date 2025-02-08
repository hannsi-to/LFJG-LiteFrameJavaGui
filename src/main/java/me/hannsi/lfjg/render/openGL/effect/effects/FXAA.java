package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

/**
 * Class representing a Fast Approximate Anti-Aliasing (FXAA) effect in OpenGL.
 */
public class FXAA extends EffectBase {
    private boolean useAlpha;

    /**
     * Constructs a new FXAA effect with the specified resolution and alpha usage.
     *
     * @param resolution the resolution of the effect
     * @param useAlpha whether to use alpha in the effect
     */
    public FXAA(Vector2f resolution, boolean useAlpha) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/FXAA.fsh"), true, 17, "FastApproximateAntiAliasing");

        this.useAlpha = useAlpha;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("texelStep", new Vector2f(1.0f / getResolution().x(), 1.0f / getResolution().y()));
        getFrameBuffer().getShaderProgramFBO().setUniform("useAlpha", useAlpha);

        super.setUniform(baseGLObject);
    }

    /**
     * Checks if alpha is used in the effect.
     *
     * @return true if alpha is used, false otherwise
     */
    public boolean isUseAlpha() {
        return useAlpha;
    }

    /**
     * Sets whether to use alpha in the effect.
     *
     * @param useAlpha true to use alpha, false otherwise
     */
    public void setUseAlpha(boolean useAlpha) {
        this.useAlpha = useAlpha;
    }
}