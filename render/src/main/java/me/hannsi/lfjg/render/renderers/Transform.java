package me.hannsi.lfjg.render.renderers;

import org.joml.Matrix4f;

public class Transform {
    private final GLObject glObject;
    private final Matrix4f modelMatrix;

    private float x;
    private float y;
    private float z;

    private float angleX;
    private float angleY;
    private float angleZ;

    private float scaleX;
    private float scaleY;
    private float scaleZ;

    public Transform(GLObject glObject) {
        this(new Matrix4f(), glObject);
    }

    public Transform(Matrix4f modelMatrix, GLObject glObject) {
        this.glObject = glObject;
        this.modelMatrix = modelMatrix;
    }

    public Transform(GLObject glObject, Matrix4f modelMatrix, float x, float y, float z, float angleX, float angleY, float angleZ, float scaleX, float scaleY, float scaleZ) {
        this.glObject = glObject;
        this.modelMatrix = modelMatrix;
        this.x = x;
        this.y = y;
        this.z = z;
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    public Transform newInstance() {
        return new Transform(glObject, modelMatrix, x, y, z, angleX, angleY, angleZ, scaleX, scaleY, scaleZ);
    }

    public Transform reset() {
        this.angleX = 0;
        this.angleY = 0;
        this.angleZ = 0;
        this.scaleX = 1;
        this.scaleY = 1;
        this.scaleZ = 1;

        modelMatrix.identity();

        return this;
    }

    public Transform translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        modelMatrix.translate(x, y, z);

        return this;
    }

    public Transform rotateXYZ(float angleX, float angleY, float angleZ) {
        this.angleX += angleX;
        this.angleY += angleY;
        this.angleZ += angleZ;
        modelMatrix.rotateXYZ(angleX, angleY, angleZ);

        return this;
    }

    public Transform scale(float scaleX, float scaleY, float scaleZ) {
        this.scaleX += scaleX;
        this.scaleY += scaleY;
        this.scaleZ += scaleZ;
        modelMatrix.scale(scaleX, scaleY, scaleZ);

        return this;
    }

    public GLObject getGlObject() {
        return glObject;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
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

    public float getAngleX() {
        return angleX;
    }

    public void setAngleX(float angleX) {
        this.angleX = angleX;
    }

    public float getAngleY() {
        return angleY;
    }

    public void setAngleY(float angleY) {
        this.angleY = angleY;
    }

    public float getAngleZ() {
        return angleZ;
    }

    public void setAngleZ(float angleZ) {
        this.angleZ = angleZ;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public void setScaleZ(float scaleZ) {
        this.scaleZ = scaleZ;
    }
}
