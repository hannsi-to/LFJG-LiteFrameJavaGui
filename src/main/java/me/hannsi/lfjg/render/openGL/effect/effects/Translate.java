package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;
import org.lwjgl.opengl.GL11;

public class Translate extends EffectBase {
    private float x;
    private float y;
    private float z;

    public Translate(float x, float y, float z) {
        super(2, "Translate", null);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Translate(float x, float y) {
        this(x, y, 0.0f);
    }

    @Override
    public void pop(Frame frame, GLPolygon basePolygon) {
        super.pop(frame, basePolygon);
    }

    @Override
    public void push(Frame frame, GLPolygon basePolygon) {
        GL11.glTranslatef(x, y, z);

        super.push(frame, basePolygon);
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
