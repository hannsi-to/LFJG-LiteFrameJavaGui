package me.hannsi.lfjg.core.utils.math;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.joml.Matrix4f;

public class Projection {
    public static final float DEFAULT_FOV = (float) Math.toRadians(60.0f);
    public static final float DEFAULT_Z_FAR = 1000.0f;
    public static final float DEFAULT_Z_NEAR = 0.01f;

    private final ProjectionType projectionType;
    private final float fov;
    private final int windowWidth;
    private final int windowHeight;
    private final float zFar;
    private final float zNear;
    private Matrix4f projMatrix;

    public Projection(ProjectionType projectionType, float fov, int windowWidth, int windowHeight, float zFar, float zNear) {
        this.projectionType = projectionType;
        this.fov = fov;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.zFar = zFar;
        this.zNear = zNear;
        this.projMatrix = new Matrix4f();

        updateProjMatrix(this.fov, this.windowWidth, this.windowHeight, this.zFar, this.zNear);
    }

    public Projection(ProjectionType projectionType, int windowWidth, int windowHeight) {
        this(projectionType, DEFAULT_FOV, windowWidth, windowHeight, DEFAULT_Z_FAR, DEFAULT_Z_NEAR);
    }

    public void updateProjMatrix(float fov, int windowWidth, int windowHeight, float zFar, float zNear) {
        float aspectWindow = (float) windowWidth / windowHeight;

        switch (projectionType) {
            case ORTHOGRAPHIC_PROJECTION:
                projMatrix = new Matrix4f().ortho(0, windowWidth, 0, windowHeight, -1f, 1f);
                break;
            case PERSPECTIVE_PROJECTION:
                projMatrix = new Matrix4f().setPerspective(fov, aspectWindow, zNear, zFar);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + projectionType);
        }
    }

    public ProjectionType getProjectionType() {
        return projectionType;
    }

    public float getFov() {
        return fov;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public float getzFar() {
        return zFar;
    }

    public float getzNear() {
        return zNear;
    }

    public Matrix4f getProjMatrix() {
        return projMatrix;
    }

    public void setProjMatrix(Matrix4f projMatrix) {
        this.projMatrix = projMatrix;
    }
}