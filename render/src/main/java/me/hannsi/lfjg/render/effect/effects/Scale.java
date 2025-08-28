package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

public class Scale extends EffectBase {
    protected float latestX = 1f;
    protected float latestY = 1f;
    protected float latestZ = 1f;

    private float x = 1f;
    private float y = 1f;
    private float z = 1f;
    private boolean autoCenter = true;
    private float cx = 0f;
    private float cy = 0f;
    private float cz = 0f;

    Scale(String name) {
        super(name, true);
    }

    public static Scale createScale(String name) {
        return new Scale(name);
    }

    public Scale x(float x) {
        this.x = x;
        return this;
    }

    public Scale x(double x) {
        this.x = (float) x;
        return this;
    }

    public Scale y(float y) {
        this.y = y;
        return this;
    }

    public Scale y(double y) {
        this.y = (float) y;
        return this;
    }

    public Scale z(float z) {
        this.z = z;
        return this;
    }

    public Scale z(double z) {
        this.z = (float) z;
        return this;
    }

    public Scale autoCenter(boolean autoCenter) {
        this.autoCenter = autoCenter;
        return this;
    }

    public Scale cx(float cx) {
        this.cx = cx;
        return this;
    }

    public Scale cx(double cx) {
        this.cx = (float) cx;
        return this;
    }

    public Scale cy(float cy) {
        this.cy = cy;
        return this;
    }

    public Scale cy(double cy) {
        this.cy = (float) cy;
        return this;
    }

    public Scale cz(float cz) {
        this.cz = cz;
        return this;
    }

    public Scale cz(double cz) {
        this.cz = (float) cz;
        return this;
    }

    @Override
    public void push(GLObject baseGLObject) {
        if (autoCenter) {
            cx = baseGLObject.getTransform().getCenterX();
            cy = baseGLObject.getTransform().getCenterY();
        }

        baseGLObject.getTransform()
                .translate(cx, cy, cz)
                .scale(x / latestX, y / latestY, z / latestZ)
                .translate(-cx, -cy, -cz);

        super.push(baseGLObject);
    }

    @Override
    public void pop(GLObject baseGLObject) {
        latestX = x;
        latestY = y;
        latestZ = z;

        super.pop(baseGLObject);
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

    public boolean isAutoCenter() {
        return autoCenter;
    }

    public void setAutoCenter(boolean autoCenter) {
        this.autoCenter = autoCenter;
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
