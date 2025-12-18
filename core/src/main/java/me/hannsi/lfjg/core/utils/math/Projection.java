package me.hannsi.lfjg.core.utils.math;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.joml.Matrix4f;

public class Projection {
    public static final float DEFAULT_FOV = (float) Math.toRadians(60.0f);
    public static final float DEFAULT_Z_FAR = 1000.0f;
    public static final float DEFAULT_Z_NEAR = 0.01f;

    private Matrix4f matrix4f;

    public Projection(ProjectionType projectionType, float fov, int windowWidth, int windowHeight, float zFar, float zNear) {
        this.matrix4f = new Matrix4f();

        updateProjMatrix(projectionType, fov, windowWidth, windowHeight, zFar, zNear);
    }

    public void updateProjMatrix(ProjectionType projectionType, float fov, int windowWidth, int windowHeight, float zFar, float zNear) {
        switch (projectionType) {
            case ORTHOGRAPHIC_PROJECTION -> matrix4f = new Matrix4f().ortho(0, windowWidth, 0, windowHeight, -1f, 1f);
            case PERSPECTIVE_PROJECTION -> {
                float aspectWindow = (float) windowWidth / windowHeight;
                matrix4f = new Matrix4f().setPerspective(fov, aspectWindow, zNear, zFar);
            }
            default -> throw new IllegalStateException("Unexpected value: " + projectionType);
        }

    }

    public Matrix4f getMatrix4f() {
        return matrix4f;
    }

    public void setMatrix4f(Matrix4f matrix4f) {
        this.matrix4f = matrix4f;
    }
}