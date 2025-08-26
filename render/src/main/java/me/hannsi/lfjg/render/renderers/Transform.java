package me.hannsi.lfjg.render.renderers;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class Transform {
    private final GLObject glObject;
    private final Matrix4f modelMatrix;

    private float x;
    private float y;
    private float width;
    private float height;

    private float centerX;
    private float centerY;
    private float centerZ;

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

    public Transform setBound(Vector4f vector4f) {
        return setBound(vector4f.x(), vector4f.y(), vector4f.z(), vector4f.w());
    }

    public Transform setBound(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public Transform reset(){
        this.centerX = 0;
        this.centerY = 0;
        this.centerZ = 0;
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
        this.centerX += x;
        this.centerY += y;
        this.centerZ += z;
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public float getCenterZ() {
        return centerZ;
    }

    public void setCenterZ(float centerZ) {
        this.centerZ = centerZ;
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
