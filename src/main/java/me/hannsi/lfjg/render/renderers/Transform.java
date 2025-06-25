package me.hannsi.lfjg.render.renderers;

import lombok.Data;
import org.joml.Matrix4f;

@Data
public class Transform {
    private final Matrix4f modelMatrix;

    private float centerX;
    private float centerY;

    private float angleX;
    private float angleY;
    private float angleZ;

    private float scaleX;
    private float scaleY;
    private float scaleZ;

    public Transform() {
        this(new Matrix4f());
    }

    public Transform(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    public Transform translate(float x, float y, float z) {
        centerX += x;
        centerY += y;
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
