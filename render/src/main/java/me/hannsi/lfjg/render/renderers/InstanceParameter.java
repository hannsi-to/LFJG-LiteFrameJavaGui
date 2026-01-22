package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.Cleanup;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.core.utils.math.MathHelper.isMatrix4fIdentity;
import static me.hannsi.lfjg.core.utils.math.MathHelper.isQuaternionfIdentity;
import static me.hannsi.lfjg.render.LFJGRenderContext.mainCamera;
import static me.hannsi.lfjg.render.RenderSystemSetting.*;

public class InstanceParameter implements Cleanup {
    public static final int BYTES = 16 * Float.BYTES + 4 * Float.BYTES + Integer.BYTES + 3 * Integer.BYTES;
    protected final Matrix4f TEMP_MATRIX = new Matrix4f();
    protected final Matrix4f MODEL_MATRIX = new Matrix4f();
    protected boolean dirtyFlag;
    private final Quaternionf rotation;
    private ProjectionType projectionType;
    private int spriteIndex;
    private int objectId;
    private float x;
    private float y;
    private float z;
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private Color color;

    InstanceParameter() {
        this.dirtyFlag = false;
        this.projectionType = INSTANCE_PARAMETER_DEFAULT_PROJECTION_TYPE;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.rotation = new Quaternionf();
        this.scaleX = 1;
        this.scaleY = 1;
        this.scaleZ = 1;
        this.spriteIndex = INSTANCE_PARAMETER_DEFAULT_SPRITE_INDEX;
        this.objectId = -1;
        this.color = INSTANCE_PARAMETER_DEFAULT_COLOR;
    }

    public static InstanceParameter createBuilder() {
        return new InstanceParameter();
    }

    public InstanceParameter projectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter x(float x) {
        this.x = x;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter y(float y) {
        this.y = y;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter z(float z) {
        this.z = z;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter angleX(float angleX) {
        this.rotation.rotateX(angleX);

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter angleY(float angleY) {
        this.rotation.rotateY(angleY);

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter angleZ(float angleZ) {
        this.rotation.rotateZ(angleZ);

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter scaleX(float scaleX) {
        this.scaleX = scaleX;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter scaleY(float scaleY) {
        this.scaleY = scaleY;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter scaleZ(float scaleZ) {
        this.scaleZ = scaleZ;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter spriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter objectId(int objectId) {
        this.objectId = objectId;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter color(Color color) {
        this.color = color;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter getToAddress(long address, Matrix4f vpMatrix) {
        if (!dirtyFlag && !mainCamera.isDirtyFlag()) {
            return this;
        }

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
        address += Float.BYTES;
        UNSAFE.putInt(address, objectId);

        mainCamera.setDirtyFlag(false);

        return this;
    }

    public InstanceParameter reset() {
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

    public InstanceParameter translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter rotateXYZ(float angleX, float angleY, float angleZ) {
        rotation
                .rotateX(angleX)
                .rotateY(angleY)
                .rotateZ(angleZ);

        dirtyFlag = true;

        return this;
    }

    public InstanceParameter scale(float scaleX, float scaleY, float scaleZ) {
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
        dirtyFlag = true;

        return rotation;
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

    public void setProjectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;

        dirtyFlag = true;
    }

    public int getSpriteIndex() {
        return spriteIndex;
    }

    public void setSpriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;

        dirtyFlag = true;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;

        dirtyFlag = true;
    }

    public Color getColor() {
        dirtyFlag = true;

        return color;
    }

    public void setColor(Color color) {
        this.color = color;

        dirtyFlag = true;
    }

    public void setDirtyFlag(boolean dirtyFlag) {
        this.dirtyFlag = dirtyFlag;
    }

    @Override
    public boolean cleanup(CleanupEvent event) {
        TEMP_MATRIX.identity();
        MODEL_MATRIX.identity();
        rotation.identity();
        color.identity();

        return event.debug(this.getClass(), new CleanupEvent.CleanupData(this.getClass().getSimpleName())
                .addData("TEMP_MATRIX", isMatrix4fIdentity(TEMP_MATRIX), TEMP_MATRIX)
                .addData("MODEL_MATRIX", isMatrix4fIdentity(MODEL_MATRIX), MODEL_MATRIX)
                .addData("rotation", isQuaternionfIdentity(rotation), rotation)
                .addData("color", color.isZero(), color)
        );
    }
}
