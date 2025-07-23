package me.hannsi.lfjg.render.renderers;

import lombok.Data;
import org.joml.Matrix4f;
import org.joml.Vector4f;

@Data
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
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
        modelMatrix.scale(scaleX, scaleY, scaleZ);

        return this;
    }
}
