package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.UniformDatum;
import org.joml.Matrix4f;

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

    public Translate(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }

    public Translate(float x, float y) {
        this(x, y, 0.0f);
    }

    public Translate(double x, double y) {
        this(x, y, 0.0f);
    }

    @Override
    public void pop(GLObject baseGLObject) {
        @SuppressWarnings("unchecked") UniformDatum<Matrix4f> matrixDatum = (UniformDatum<Matrix4f>) baseGLObject.getUniform("modelMatrix");
        Matrix4f modelMatrix = matrixDatum.getValue();
        matrixDatum.setValue(modelMatrix.translate(-x, -y, -z));

        super.pop(baseGLObject);
    }

    @Override
    public void push(GLObject baseGLObject) {
        @SuppressWarnings("unchecked") UniformDatum<Matrix4f> matrixDatum = (UniformDatum<Matrix4f>) baseGLObject.getUniform("modelMatrix");
        Matrix4f modelMatrix = matrixDatum.getValue();
        matrixDatum.setValue(modelMatrix.translate(x, y, z));

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
