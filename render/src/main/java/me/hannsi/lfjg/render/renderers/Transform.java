package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.render.LFJGRenderContext.MAIN_CAMERA;

public class Transform {
    public static final int BYTES = 80;
    protected final Matrix4f TEMP_MATRIX = new Matrix4f();
    protected final Matrix4f MODEL_MATRIX = new Matrix4f();
    private final Quaternionf rotation;
    private final ProjectionType projectionType;
    protected boolean dirtyFlag = true;
    private int layer;
    private long address;
    private float x;
    private float y;
    private float z;
    private float scaleX;
    private float scaleY;
    private float scaleZ;

    public Transform(int layer) {
        this(0, 0, 0, 0, 0, 0, 1, 1, 1, ProjectionType.ORTHOGRAPHIC_PROJECTION, layer);
    }

    public Transform(float x, float y, float z, float angleX, float angleY, float angleZ, float scaleX, float scaleY, float scaleZ, ProjectionType projectionType, int layer) {
        this(x, y, z, new Quaternionf().rotateX(angleX).rotateY(angleY).rotateZ(angleZ), scaleX, scaleY, scaleZ, projectionType, layer);
    }

    public Transform(float x, float y, float z, Quaternionf rotation, float scaleX, float scaleY, float scaleZ, ProjectionType projectionType, int layer) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
        this.projectionType = projectionType;
        this.layer = layer;
    }

    public Transform getToAddress(long address, Matrix4f vpMatrix) {
        if (!dirtyFlag && !MAIN_CAMERA.isDirtyFlag()) {
            return this;
        }

        this.address = address;
        if (dirtyFlag) {
            MODEL_MATRIX.translationRotateScale(x, y, z, rotation.x, rotation.y, rotation.z, rotation.w, scaleX, scaleY, scaleZ);
            dirtyFlag = false;
        }

        vpMatrix.mul(MODEL_MATRIX, TEMP_MATRIX);

        TEMP_MATRIX.getToAddress(address);
        UNSAFE.putInt(address + 64, layer);

        MAIN_CAMERA.setDirtyFlag(false);

        return this;
    }

    public Transform newInstance() {
        return new Transform(x, y, z, new Quaternionf(rotation), scaleX, scaleY, scaleZ, projectionType, layer);
    }

    public Transform reset() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.rotation.identity();
        this.scaleX = 1;
        this.scaleY = 1;
        this.scaleZ = 1;

        dirtyFlag = true;

        return this;
    }

    public Transform translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;

        dirtyFlag = true;

        return this;
    }

    public Transform rotateXYZ(float angleX, float angleY, float angleZ) {
        rotation
                .rotateX(angleX)
                .rotateY(angleY)
                .rotateZ(angleZ);

        dirtyFlag = true;

        return this;
    }

    public Transform scale(float scaleX, float scaleY, float scaleZ) {
        this.scaleX *= scaleX;
        this.scaleY *= scaleY;
        this.scaleZ *= scaleZ;

        dirtyFlag = true;

        return this;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;

        dirtyFlag = true;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;

        dirtyFlag = true;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;

        dirtyFlag = true;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public long getAddress() {
        return address;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;

        dirtyFlag = true;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;

        dirtyFlag = true;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public void setScaleZ(float scaleZ) {
        this.scaleZ = scaleZ;

        dirtyFlag = true;
    }

    public ProjectionType getProjectionType() {
        return projectionType;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }
}
