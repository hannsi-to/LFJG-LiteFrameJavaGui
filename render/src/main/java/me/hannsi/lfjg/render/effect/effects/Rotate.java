package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

public class Rotate extends EffectBase {
    protected float latestX;
    protected float latestY;
    protected float latestZ;

    private float x = 0f;
    private float y = 0f;
    private float z = MathHelper.toRadians(45);
    private boolean autoCenter = true;
    private float cx = 500f;
    private float cy = 500f;
    private float cz = 0f;

    Rotate(String name) {
        super(name, true);
    }

    public static Rotate createRotate(String name) {
        return new Rotate(name);
    }

    public Rotate xRadian(float xRadian) {
        this.x = xRadian;
        return this;
    }

    public Rotate xRadian(double xRadian) {
        this.x = (float) xRadian;
        return this;
    }

    public Rotate xDegree(int xDegree) {
        this.x = MathHelper.toRadians(xDegree);
        return this;
    }

    public Rotate yRadian(float yRadian) {
        this.y = yRadian;
        return this;
    }

    public Rotate yRadian(double yRadian) {
        this.y = (float) yRadian;
        return this;
    }

    public Rotate yDegree(int yDegree) {
        this.y = MathHelper.toRadians(yDegree);
        return this;
    }

    public Rotate zRadian(float zRadian) {
        this.z = zRadian;
        return this;
    }

    public Rotate zRadian(double zRadian) {
        this.z = (float) zRadian;
        return this;
    }

    public Rotate zDegree(int zDegree) {
        this.z = MathHelper.toRadians(zDegree);
        return this;
    }

    public Rotate autoCenter(boolean autoCenter) {
        this.autoCenter = autoCenter;
        return this;
    }

    public Rotate cx(float cx) {
        this.cx = cx;
        return this;
    }

    public Rotate cx(double cx) {
        this.cx = (float) cx;
        return this;
    }

    public Rotate cy(float cy) {
        this.cy = cy;
        return this;
    }

    public Rotate cy(double cy) {
        this.cy = (float) cy;
        return this;
    }

    public Rotate cz(float cz) {
        this.cz = cz;
        return this;
    }

    public Rotate cz(double cz) {
        this.cz = (float) cz;
        return this;
    }

    @Override
    public void push(GLObject baseGLObject) {
        if (autoCenter) {
            cx = baseGLObject.getTransform().getCenterX();
            cy = baseGLObject.getTransform().getCenterY();
        }

        baseGLObject.getTransform().translate(cx, cy, cz).rotateXYZ(-latestX, -latestY, -latestZ).rotateXYZ(x, y, z).translate(-cx, -cy, -cz);

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
