package me.hannsi.lfjg.utils.math;

import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Matrix4f;

public class Projection {
    public static final float DEFAULT_FOV = (float) Math.toRadians(60.0f);
    public static final float DEFAULT_Z_FAR = 1000.0f;
    public static final float DEFAULT_Z_NEAR = 0.01f;

    private ProjectionType projectionType;
    private float fov;
    private int windowWidth;
    private int windowHeight;
    private float zFar;
    private float zNear;

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

    public Matrix4f getProjMatrix() {
        return projMatrix;
    }

    public void setProjMatrix(Matrix4f projMatrix) {
        this.projMatrix = projMatrix;
    }

    public void updateProjMatrix(float fov, int windowWidth, int windowHeight, float zFar, float zNear) {
        float aspectWindow = (float) windowWidth / windowHeight;

        switch (projectionType) {
            case OrthographicProjection -> {
                projMatrix.ortho(0, windowWidth, 0, windowHeight, -1f, 1f);
            }
            case PerspectiveProjection -> {
                projMatrix.setPerspective(fov, aspectWindow, zNear, zFar);
            }
            default -> throw new IllegalStateException("Unexpected value: " + projectionType);
        }
    }

    public ProjectionType getProjectionType() {
        return projectionType;
    }

    public void setProjectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public float getzFar() {
        return zFar;
    }

    public void setzFar(float zFar) {
        this.zFar = zFar;
    }

    public float getzNear() {
        return zNear;
    }

    public void setzNear(float zNear) {
        this.zNear = zNear;
    }
}
