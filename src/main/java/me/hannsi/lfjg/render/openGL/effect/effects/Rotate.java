package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.UniformDatum;
import org.joml.Matrix4f;

public class Rotate extends EffectBase {
    private float x;
    private float y;
    private float z;
    private float cx;
    private float cy;
    private float cz;

    public Rotate(float x, float y, float z) {
        this(x, y, z, 0, 0, 0);
    }

    public Rotate(double x, double y, double z) {
        this((float) x, (float) y, (float) z, 0, 0, 0);
    }

    public Rotate(float x, float y, float z, float cx, float cy) {
        this(x, y, z, cx, cy, 0);
    }

    public Rotate(double x, double y, double z, double cx, double cy) {
        this((float) x, (float) y, (float) z, (float) cx, (float) cy, 0);
    }

    public Rotate(float x, float y, float z, float cx, float cy, float cz) {
        super(1, "Rotate", (Class<GLObject>) null);

        this.x = x;
        this.y = y;
        this.z = z;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
    }

    public Rotate(double x, double y, double z, double cx, double cy, double cz) {
        this((float) x, (float) y, (float) z, (float) cx, (float) cy, (float) cz);
    }

    @Override
    public void pop(GLObject baseGLObject) {
        @SuppressWarnings("unchecked") UniformDatum<Matrix4f> matrixDatum = (UniformDatum<Matrix4f>) baseGLObject.getUniform("modelMatrix");
        Matrix4f modelMatrix = matrixDatum.getValue();
        matrixDatum.setValue(modelMatrix.translate(cx, cy, cz).rotateXYZ(-x, -y, -z).translate(cx, -cy, -cz));
        //NullPointerException
        super.pop(baseGLObject);
    }

    @Override
    public void push(GLObject baseGLObject) {
        @SuppressWarnings("unchecked") UniformDatum<Matrix4f> matrixDatum = (UniformDatum<Matrix4f>) baseGLObject.getUniform("modelMatrix");
        Matrix4f modelMatrix = matrixDatum.getValue();
        matrixDatum.setValue(modelMatrix.translate(cx, cy, cz).rotateXYZ(x, y, z).translate(cx, -cy, -cz));

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
