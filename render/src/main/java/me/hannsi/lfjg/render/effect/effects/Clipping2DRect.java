package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2i;
import org.joml.Vector4f;

public class Clipping2DRect extends EffectBase {
    private Vector2i resolution = new Vector2i();
    private float x1 = 0f;
    private float y1 = 0f;
    private float x2 = 0f;
    private float y2 = 0f;
    private boolean invert = false;

    Clipping2DRect() {
        super(Location.fromResource("shader/frameBuffer/filter/Clipping2D.fsh"), true, 5, "Clipping2DRect");
    }

    public static Clipping2DRect createClipping2DRect() {
        return new Clipping2DRect();
    }

    public Clipping2DRect resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
    }

    public Clipping2DRect x1(float x1) {
        this.x1 = x1;
        return this;
    }

    public Clipping2DRect x1(double x1) {
        this.x1 = (float) x1;
        return this;
    }

    public Clipping2DRect x1(int x1) {
        this.x1 = x1;
        return this;
    }

    public Clipping2DRect y1(float y1) {
        this.y1 = y1;
        return this;
    }

    public Clipping2DRect y1(double y1) {
        this.y1 = (float) y1;
        return this;
    }

    public Clipping2DRect y1(int y1) {
        this.y1 = y1;
        return this;
    }

    public Clipping2DRect x2(float x2) {
        this.x2 = x2;
        return this;
    }

    public Clipping2DRect x2(double x2) {
        this.x2 = (float) x2;
        return this;
    }

    public Clipping2DRect x2(int x2) {
        this.x2 = x2;
        return this;
    }

    public Clipping2DRect y2(float y2) {
        this.y2 = y2;
        return this;
    }

    public Clipping2DRect y2(double y2) {
        this.y2 = (float) y2;
        return this;
    }

    public Clipping2DRect y2(int y2) {
        this.y2 = y2;
        return this;
    }

    public Clipping2DRect invert(boolean invert) {
        this.invert = invert;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("resolution", resolution);
        getFrameBuffer().getShaderProgramFBO().setUniform("clippingRect2DBool", true);
        getFrameBuffer().getShaderProgramFBO().setUniform("clippingRect2DInvert", invert);
        getFrameBuffer().getShaderProgramFBO().setUniform("clippingRect2DSize", new Vector4f(x1, y1, x2, y2));

        super.setUniform(baseGLObject);
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public void setResolution(Vector2i resolution) {
        this.resolution = resolution;
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
}