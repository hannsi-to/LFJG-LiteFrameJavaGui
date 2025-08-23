package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

public class Translate extends EffectBase {
    protected float latestX;
    protected float latestY;
    protected float latestZ;

    private float x = 0f;
    private float y = 0f;
    private float z = 0f;

    Translate() {
        super(2, "Translate", (Class<GLObject>) null);
    }

    public static Translate createTranslate() {
        return new Translate();
    }

    public Translate x(float x) {
        this.x = x;
        return this;
    }

    public Translate x(double x) {
        this.x = (float) x;
        return this;
    }

    public Translate y(float y) {
        this.y = y;
        return this;
    }

    public Translate y(double y) {
        this.y = (float) y;
        return this;
    }

    public Translate z(float z) {
        this.z = z;
        return this;
    }

    public Translate z(double z) {
        this.z = (float) z;
        return this;
    }

    @Override
    public void pop(GLObject baseGLObject) {
        latestX = x;
        latestY = y;
        latestZ = z;

        super.pop(baseGLObject);
    }

    @Override
    public void push(GLObject baseGLObject) {
        baseGLObject.getTransform().translate(-latestX, -latestY, -latestZ).translate(x, y, z);
        super.push(baseGLObject);
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
        super.setUniform(baseGLObject);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}