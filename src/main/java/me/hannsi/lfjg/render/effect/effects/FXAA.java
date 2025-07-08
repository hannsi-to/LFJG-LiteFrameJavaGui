package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.Location;
import org.joml.Vector2f;

/**
 * Class representing a Fast Approximate Anti-Aliasing (FXAA) effect in OpenGL.
 */
@Getter
@Setter
public class FXAA extends EffectBase {
    /**
     * -- SETTER --
     * Sets whether to use alpha in the effect.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if alpha is used in the effect.
     *
     * @param useAlpha true to use alpha, false otherwise
     * @return true if alpha is used, false otherwise
     */
    private boolean useAlpha;

    /**
     * Constructs a new FXAA effect with the specified resolution and alpha usage.
     *
     * @param useAlpha whether to use alpha in the effect
     */
    public FXAA(boolean useAlpha) {
        super(Location.fromResource("shader/frameBuffer/filter/FXAA.fsh"), true, 17, "FastApproximateAntiAliasing");

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
        getFrameBuffer().getShaderProgramFBO().setUniform("texelStep", new Vector2f(1.0f / LFJGContext.frameBufferSize.x(), 1.0f / LFJGContext.frameBufferSize.y()));
        getFrameBuffer().getShaderProgramFBO().setUniform("useAlpha", useAlpha);

        super.setUniform(baseGLObject);
    }

}