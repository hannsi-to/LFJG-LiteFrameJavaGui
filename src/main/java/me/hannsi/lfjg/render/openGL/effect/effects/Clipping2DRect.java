package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Clipping2DRect extends EffectBase {
    private Vector2f resolution;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private boolean invert;

    public Clipping2DRect(Vector2f resolution, float x1, float y1, float x2, float y2, boolean invert) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Clipping2D.fsh"), true, 5, "Clipping2DRect", (Class<GLObject>) null);

        this.resolution = resolution;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.invert = invert;
    }

    public Clipping2DRect(Vector2f resolution, float x1, float y1, float x2, float y2) {
        this(resolution, x1, y1, x2, y2, false);
    }

    public Clipping2DRect(Vector2f resolution, double x1, double y1, double x2, double y2, boolean invert) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Clipping2D.fsh"), true, 5, "Clipping2DRect", (Class<GLObject>) null);

        this.resolution = resolution;
        this.x1 = (float) x1;
        this.y1 = (float) y1;
        this.x2 = (float) x2;
        this.y2 = (float) y2;
        this.invert = invert;
    }

    public Clipping2DRect(Vector2f resolution, double x1, double y1, double x2, double y2) {
        this(resolution, x1, y1, x2, y2, false);
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
        getFrameBuffer().getShaderProgramFBO().setUniform2f("resolution", resolution);
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("clippingRect2DBool", true);
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("clippingRect2DInvert", invert);
        getFrameBuffer().getShaderProgramFBO().setUniform4f("clippingRect2DSize", new Vector4f(x1, y1, x2, y2));

        super.setUniform(baseGLObject);
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public boolean isInvert() {
        return invert;
    }

    public void setInvert(boolean invert) {
        this.invert = invert;
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
    }
}
