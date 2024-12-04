package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import org.joml.Vector4f;

public class Clipping2D extends EffectBase {
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private boolean invert;

    public Clipping2D(float x1, float y1, float x2, float y2, boolean invert) {
        super(5, "Clipping", (Class<GLObject>) null);

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.invert = invert;
    }

    public Clipping2D(float x1, float y1, float x2, float y2) {
        this(x1, y1, x2, y2, false);
    }

    @Override
    public void draw(GLObject baseGLObject) {


        super.draw(baseGLObject);
    }

    @Override
    public void pop(GLObject baseGLObject) {
        super.pop(baseGLObject);
    }

    @Override
    public void push(GLObject baseGLObject) {
        baseGLObject.getShaderProgram().setUniformBoolean("clippingRect2DBool", true);
        baseGLObject.getShaderProgram().setUniformBoolean("clippingRect2DInvert", invert);
        baseGLObject.getShaderProgram().setUniform4f("clippingRect2DSize", new Vector4f(x1, y1, x2, y2));

        super.push(baseGLObject);
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
