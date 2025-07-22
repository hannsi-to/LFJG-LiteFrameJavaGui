package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Class representing a Gaussian Blur Horizontal effect in OpenGL.
 */
@Getter
@Setter
public class GaussianBlurHorizontal extends EffectBase {
    public Vector2i resolution = new Vector2i();
    /**
     * -- SETTER --
     * Sets the radius of the blur in the x-direction.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the radius of the blur in the x-direction.
     *
     * @param radiusX the radius of the blur in the x-direction
     * @return the radius of the blur in the x-direction
     */
    private float radiusX = 30f;

    GaussianBlurHorizontal() {
        super(Location.fromResource("shader/frameBuffer/filter/GaussianBlur.fsh"), true, 6, "GaussianBlurHorizontal");
    }

    public static GaussianBlurHorizontal createGaussianBlurHorizontal() {
        return new GaussianBlurHorizontal();
    }

    public GaussianBlurHorizontal resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
    }

    public GaussianBlurHorizontal radiusX(float radiusX) {
        this.radiusX = radiusX;
        return this;
    }

    public GaussianBlurHorizontal radiusX(double radiusX) {
        this.radiusX = (float) radiusX;
        return this;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("texelSize", new Vector2f(1.0f / resolution.x(), 1.0f / resolution.y()));

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i < radiusX; i++) {
            weightBuffer.put(MathHelper.calculateGaussianValue(i, radiusX / 2));
        }
        weightBuffer.rewind();
        getFrameBuffer().getShaderProgramFBO().setUniform("values", weightBuffer);

        super.setUniform(baseGLObject);
    }

}