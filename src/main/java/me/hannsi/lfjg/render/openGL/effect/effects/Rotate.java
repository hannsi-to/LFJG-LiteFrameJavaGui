package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import org.joml.Vector2f;

public class Rotate extends EffectBase {
    private final Vector2f resolution;
    private float x;
    private float y;
    private float z;
    private float cx;
    private float cy;
    private float cz;

    public Rotate(Vector2f resolution, float x, float y, float z) {
        this(resolution, x, y, z, 0, 0, 0);
    }

    public Rotate(Vector2f resolution, double x, double y, double z) {
        this(resolution, (float) x, (float) y, (float) z, 0, 0, 0);
    }

    public Rotate(Vector2f resolution, float x, float y, float z, float cx, float cy) {
        this(resolution, x, y, z, cx, cy, 0);
    }

    public Rotate(Vector2f resolution, double x, double y, double z, double cx, double cy) {
        this(resolution, (float) x, (float) y, (float) z, (float) cx, (float) cy, 0);
    }

    public Rotate(Vector2f resolution, float x, float y, float z, float cx, float cy, float cz) {
        super(resolution, 1, "Rotate", (Class<GLObject>) null);

        this.resolution = resolution;
        this.x = x;
        this.y = y;
        this.z = z;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
    }

    public Rotate(Vector2f resolution, double x, double y, double z, double cx, double cy, double cz) {
        this(resolution, (float) x, (float) y, (float) z, (float) cx, (float) cy, (float) cz);
    }

    @Override
    public void pop(GLObject baseGLObject) {
        baseGLObject.setModelMatrix(baseGLObject.getModelMatrix().translate(cx, cy, cz).rotateXYZ(-x, -y, -z).translate(cx, -cy, -cz));

        super.pop(baseGLObject);
    }

    @Override
    public void push(GLObject baseGLObject) {
        baseGLObject.setModelMatrix(baseGLObject.getModelMatrix().translate(cx, cy, cz).rotateXYZ(x, y, z).translate(cx, -cy, -cz));

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

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getCz() {
        return cz;
    }

    public void setCz(float cz) {
        this.cz = cz;
    }

    public Vector2f getResolution() {
        return resolution;
    }
}
