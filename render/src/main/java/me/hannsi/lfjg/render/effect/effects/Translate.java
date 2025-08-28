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

    Translate(String name) {
        super(name,true);
    }

    public static Translate createTranslate(String name){
        return new Translate(name);
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
    public void push(GLObject baseGLObject) {
        baseGLObject.getTransform().translate(-latestX, -latestY, -latestZ).translate(x, y, z);

        super.push(baseGLObject);
    }

    @Override
    public void pop(GLObject baseGLObject) {
        latestX = x;
        latestY = y;
        latestZ = z;

        super.pop(baseGLObject);
    }


}
