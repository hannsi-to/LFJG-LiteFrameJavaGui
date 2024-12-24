package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import org.joml.Vector2f;

public class Size extends EffectBase {
    private float x;
    private float y;
    private float z;
    private float cx;
    private float cy;
    private float cz;

    public Size(Vector2f resolution, float x, float y, float z, float cx, float cy, float cz) {
        super(resolution,0, "Size", (Class<GLObject>) null);
        this.x = x;
        this.y = y;
        this.z = z;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
    }

    public Size(Vector2f resolution, double x, double y, double z, double cx, double cy, double cz) {
        this(resolution,(float) x, (float) y, (float) z, (float) cx, (float) cy, (float) cz);
    }

    public Size(Vector2f resolution, float x, float y) {
        this(resolution,x, y, 1.0f);
    }

    public Size(Vector2f resolution, double x, double y) {
        this(resolution,(float) x, (float) y, 1.0f);
    }

    public Size(Vector2f resolution, float x, float y, float z) {
        this(resolution,x, y, z, 0, 0, 0);
    }

    public Size(Vector2f resolution, double x, double y, double z) {
        this(resolution,x, y, z, 0, 0, 0);
    }

    public Size(Vector2f resolution, float x, float y, float cx, float cy) {
        this(resolution,x, y, 1.0f, cx, cy, 0);
    }

    public Size(Vector2f resolution, double x, double y, double cx, double cy) {
        this(resolution,x, y, 1.0f, cx, cy, 0);
    }

    @Override
    public void pop(GLObject baseGLObject) {
        baseGLObject.setModelMatrix(baseGLObject.getModelMatrix().translate(cx, cy, cz).scale(1 / x, 1 / y, 1 / z).translate(cx, -cy, -cz));

        super.pop(baseGLObject);
    }

    @Override
    public void push(GLObject baseGLObject) {
        baseGLObject.setModelMatrix(baseGLObject.getModelMatrix().translate(cx, cy, cz).scale(x, y, z).translate(cx, -cy, -cz));

        super.push(baseGLObject);
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
}
