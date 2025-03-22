package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.math.MathHelper;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Class representing a Gaussian Blur Horizontal effect in OpenGL.
 */
public class GaussianBlurHorizontal extends EffectBase {
    private float radiusX;

    /**
     * Constructs a new GaussianBlurHorizontal effect with the specified resolution and radius.
     *
     * @param radiusX the radius of the blur in the x-direction
     */
    public GaussianBlurHorizontal(float radiusX) {
        super(new ResourcesLocation("shader/frameBuffer/filter/GaussianBlur.fsh"), true, 6, "GaussianBlurHorizontal");

        this.radiusX = radiusX;
    }

    /**
     * Constructs a new GaussianBlurHorizontal effect with the specified resolution and radius.
     *
     * @param radiusX the radius of the blur in the x-direction
     */
    public GaussianBlurHorizontal(double radiusX) {
        this((float) radiusX);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("direction", new Vector2f(1, 0));
        getFrameBuffer().getShaderProgramFBO().setUniform("radius", radiusX);
        getFrameBuffer().getShaderProgramFBO().setUniform("texelSize", new Vector2f(1.0f / LFJGContext.resolution.x(), 1.0f / LFJGContext.resolution.y()));

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i < radiusX; i++) {
            weightBuffer.put(MathHelper.calculateGaussianValue(i, radiusX / 2));
        }
        weightBuffer.rewind();
        getFrameBuffer().getShaderProgramFBO().setUniform("values", weightBuffer);

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the radius of the blur in the x-direction.
     *
     * @return the radius of the blur in the x-direction
     */
    public float getRadiusX() {
        return radiusX;
    }

    /**
     * Sets the radius of the blur in the x-direction.
     *
     * @param radiusX the radius of the blur in the x-direction
     */
    public void setRadiusX(float radiusX) {
        this.radiusX = radiusX;
    }
}