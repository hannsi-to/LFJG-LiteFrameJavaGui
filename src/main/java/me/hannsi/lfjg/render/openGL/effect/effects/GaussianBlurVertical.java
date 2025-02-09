package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.math.MathUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Class representing a Gaussian Blur Vertical effect in OpenGL.
 */
public class GaussianBlurVertical extends EffectBase {
    private float radiusY;

    /**
     * Constructs a new GaussianBlurVertical effect with the specified resolution and radius.
     *
     * @param radiusY the radius of the blur in the y-direction
     */
    public GaussianBlurVertical(float radiusY) {
        super(new ResourcesLocation("shader/frameBuffer/filter/GaussianBlur.fsh"), true, 6, "GaussianBlurVertical", (Class<GLObject>) null);

        this.radiusY = radiusY;
    }

    /**
     * Constructs a new GaussianBlurVertical effect with the specified resolution and radius.
     *
     * @param radiusY the radius of the blur in the y-direction
     */
    public GaussianBlurVertical(double radiusY) {
        this((float) radiusY);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("direction", new Vector2f(0, 1));
        getFrameBuffer().getShaderProgramFBO().setUniform("radius", radiusY);
        getFrameBuffer().getShaderProgramFBO().setUniform("texelSize", new Vector2f(1.0f / LFJGContext.resolution.x(), 1.0f / LFJGContext.resolution.y()));

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i < radiusY; i++) {
            weightBuffer.put(MathUtil.calculateGaussianValue(i, radiusY / 2));
        }
        weightBuffer.rewind();
        getFrameBuffer().getShaderProgramFBO().setUniform("values", weightBuffer);

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the radius of the blur in the y-direction.
     *
     * @return the radius of the blur in the y-direction
     */
    public float getRadiusY() {
        return radiusY;
    }

    /**
     * Sets the radius of the blur in the y-direction.
     *
     * @param radiusY the radius of the blur in the y-direction
     */
    public void setRadiusY(float radiusY) {
        this.radiusY = radiusY;
    }
}