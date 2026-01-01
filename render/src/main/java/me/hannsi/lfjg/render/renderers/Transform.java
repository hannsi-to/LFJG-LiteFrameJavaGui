package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.render.LFJGRenderContext.mainCamera;
import static me.hannsi.lfjg.render.system.mesh.InstanceData.NO_ATTACH_TEXTURE;

public class Transform {
    public static final int BYTES = 80;
    protected final Matrix4f TEMP_MATRIX = new Matrix4f();
    protected final Matrix4f MODEL_MATRIX = new Matrix4f();
    private final Quaternionf rotation;
    protected boolean dirtyFlag = true;
    private ProjectionType projectionType;
    private int spriteIndex;
    private long address;
    private float x;
    private float y;
    private float z;
    private float scaleX;
    private float scaleY;
    private float scaleZ;

    Transform() {
        this.projectionType = ProjectionType.ORTHOGRAPHIC_PROJECTION;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.rotation = new Quaternionf();
        this.scaleX = 1;
        this.scaleY = 1;
        this.scaleZ = 1;
        this.spriteIndex = NO_ATTACH_TEXTURE;
    }

    public Transform(ProjectionType projectionType, int spriteIndex, float x, float y, float z, Quaternionf rotation, float scaleY, float scaleX, float scaleZ) {
        this.rotation = rotation;
        this.projectionType = projectionType;
        this.spriteIndex = spriteIndex;
        this.x = x;
        this.y = y;
        this.z = z;
        this.scaleY = scaleY;
        this.scaleX = scaleX;
        this.scaleZ = scaleZ;
    }

    public static Transform createBuilder() {
        return new Transform();
    }

    public Transform projectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;

        return this;
    }

    public Transform x(float x) {
        this.x = x;

        return this;
    }

    public Transform y(float y) {
        this.y = y;

        return this;
    }

    public Transform z(float z) {
        this.z = z;

        return this;
    }

    public Transform angleX(float angleX) {
        this.rotation.rotateX(angleX);

        return this;
    }

    public Transform angleY(float angleY) {
        this.rotation.rotateY(angleY);

        return this;
    }

    public Transform angleZ(float angleZ) {
        this.rotation.rotateZ(angleZ);

        return this;
    }

    public Transform scaleX(float scaleX) {
        this.scaleX = scaleX;

        return this;
    }

    public Transform scaleY(float scaleY) {
        this.scaleY = scaleY;

        return this;
    }

    public Transform scaleZ(float scaleZ) {
        this.scaleZ = scaleZ;

        return this;
    }

    public Transform spriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;

        return this;
    }

    public Transform getToAddress(long address, Matrix4f vpMatrix) {
        if (!dirtyFlag && !mainCamera.isDirtyFlag()) {
            return this;
        }

        this.address = address;
        if (dirtyFlag) {
            MODEL_MATRIX.translationRotateScale(x, y, z, rotation.x, rotation.y, rotation.z, rotation.w, scaleX, scaleY, scaleZ);
            dirtyFlag = false;
        }

        vpMatrix.mul(MODEL_MATRIX, TEMP_MATRIX);

        TEMP_MATRIX.getToAddress(address);
        UNSAFE.putInt(address + 64, spriteIndex);

        mainCamera.setDirtyFlag(false);

        return this;
    }

    public Transform newInstance() {
        return new Transform(projectionType, spriteIndex, x, y, z, rotation, scaleX, scaleY, scaleZ);
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

    public int getSpriteIndex() {
        return spriteIndex;
    }

    public void setSpriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;
    }
}
