package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.DisplayUtil;

public class Translate extends EffectBase {
    private float x;
    private float y;
    private float z;

    public Translate(float x, float y, float z) {
        super(2, "Translate", (Class<GLObject>) null);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Translate(float x, float y) {
        this(x, y, 0.0f);
    }

    @Override
    public void pop(GLObject baseGLObject) {
        super.pop(baseGLObject);
    }

    @Override
    public void push(GLObject baseGLObject) {
        float ax = (2.0f * x) / DisplayUtil.getDisplayWidthF();
        float ay = (2.0f * y) / DisplayUtil.getDisplayHeightF();
        float az = z;

        //basePolygon.getModelMatrix().translate(ax, ay, az);

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
}
