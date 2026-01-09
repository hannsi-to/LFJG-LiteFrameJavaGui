package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.render.LFJGRenderContext.mainCamera;
import static me.hannsi.lfjg.render.RenderSystemSetting.*;

public class ObjectParameter {
    public static final int BYTES = 16 * Float.BYTES + 4 * Float.BYTES + Float.BYTES + 3 * Float.BYTES;
    protected final Matrix4f TEMP_MATRIX = new Matrix4f();
    protected final Matrix4f MODEL_MATRIX = new Matrix4f();
    protected boolean dirtyFlag = true;
    private final Quaternionf rotation;
    private ProjectionType projectionType;
    private int spriteIndex;
    private long address;
    private float x;
    private float y;
    private float z;
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private Color color;

    ObjectParameter() {
        this.projectionType = OBJECT_PARAMETER_DEFAULT_PROJECTION_TYPE;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.rotation = new Quaternionf();
        this.scaleX = 1;
        this.scaleY = 1;
        this.scaleZ = 1;
        this.spriteIndex = OBJECT_PARAMETER_DEFAULT_SPRITE_INDEX;
        this.color = OBJECT_PARAMETER_DEFAULT_COLOR;
    }

    public ObjectParameter(ProjectionType projectionType, int spriteIndex, float x, float y, float z, Quaternionf rotation, float scaleY, float scaleX, float scaleZ, Color color) {
        this.rotation = rotation;
        this.projectionType = projectionType;
        this.spriteIndex = spriteIndex;
        this.x = x;
        this.y = y;
        this.z = z;
        this.scaleY = scaleY;
        this.scaleX = scaleX;
        this.scaleZ = scaleZ;
        this.color = color;
    }

    public static ObjectParameter createBuilder() {
        return new ObjectParameter();
    }

    public ObjectParameter projectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;

        return this;
    }

    public ObjectParameter x(float x) {
        this.x = x;

        return this;
    }

    public ObjectParameter y(float y) {
        this.y = y;

        return this;
    }

    public ObjectParameter z(float z) {
        this.z = z;

        return this;
    }

    public ObjectParameter angleX(float angleX) {
        this.rotation.rotateX(angleX);

        return this;
    }

    public ObjectParameter angleY(float angleY) {
        this.rotation.rotateY(angleY);

        return this;
    }

    public ObjectParameter angleZ(float angleZ) {
        this.rotation.rotateZ(angleZ);

        return this;
    }

    public ObjectParameter scaleX(float scaleX) {
        this.scaleX = scaleX;

        return this;
    }

    public ObjectParameter scaleY(float scaleY) {
        this.scaleY = scaleY;

        return this;
    }

    public ObjectParameter scaleZ(float scaleZ) {
        this.scaleZ = scaleZ;

        return this;
    }

    public ObjectParameter spriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;

        return this;
    }

    public ObjectParameter color(Color color) {
        this.color = color;

        return this;
    }

    public ObjectParameter getToAddress(long address, Matrix4f vpMatrix) {
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
        address += 64;
        color.getToAddress(address);
        address += 4 * Float.BYTES;
        UNSAFE.putInt(address, spriteIndex);

        mainCamera.setDirtyFlag(false);

        return this;
    }

    public ObjectParameter newInstance() {
        return new ObjectParameter(projectionType, spriteIndex, x, y, z, rotation, scaleX, scaleY, scaleZ, color);
    }

    public ObjectParameter reset() {
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

    public ObjectParameter translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;

        dirtyFlag = true;

        return this;
    }

    public ObjectParameter rotateXYZ(float angleX, float angleY, float angleZ) {
        rotation
                .rotateX(angleX)
                .rotateY(angleY)
                .rotateZ(angleZ);

        dirtyFlag = true;

        return this;
    }

    public ObjectParameter scale(float scaleX, float scaleY, float scaleZ) {
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

    public void setDirtyFlag(boolean dirtyFlag) {
        this.dirtyFlag = dirtyFlag;
    }
}
