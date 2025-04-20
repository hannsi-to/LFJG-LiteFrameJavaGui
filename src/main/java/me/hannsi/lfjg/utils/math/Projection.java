package me.hannsi.lfjg.utils.math;

import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Matrix4f;

/**
 * Class representing a projection matrix for 3D rendering.
 */
public class Projection {
    /**
     * Default field of view (FOV) in radians.
     */
    public static final float DEFAULT_FOV = (float) Math.toRadians(60.0f);

    /**
     * Default far clipping plane distance.
     */
    public static final float DEFAULT_Z_FAR = 1000.0f;

    /**
     * Default near clipping plane distance.
     */
    public static final float DEFAULT_Z_NEAR = 0.01f;

    private ProjectionType projectionType;
    private float fov;
    private int windowWidth;
    private int windowHeight;
    private float zFar;
    private float zNear;

    private Matrix4f projMatrix;

    /**
     * Constructs a Projection instance with specified parameters.
     *
     * @param projectionType the type of projection (orthographic or perspective)
     * @param fov the field of view in radians
     * @param windowWidth the width of the window
     * @param windowHeight the height of the window
     * @param zFar the far clipping plane distance
     * @param zNear the near clipping plane distance
     */
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

    /**
     * Constructs a Projection instance with default FOV, zFar, and zNear values.
     *
     * @param projectionType the type of projection (orthographic or perspective)
     * @param windowWidth the width of the window
     * @param windowHeight the height of the window
     */
    public Projection(ProjectionType projectionType, int windowWidth, int windowHeight) {
        this(projectionType, DEFAULT_FOV, windowWidth, windowHeight, DEFAULT_Z_FAR, DEFAULT_Z_NEAR);
    }

    /**
     * Gets the projection matrix.
     *
     * @return the projection matrix
     */
    public Matrix4f getProjMatrix() {
        return projMatrix;
    }

    /**
     * Sets the projection matrix.
     *
     * @param projMatrix the new projection matrix
     */
    public void setProjMatrix(Matrix4f projMatrix) {
        this.projMatrix = projMatrix;
    }

    /**
     * Updates the projection matrix based on the specified parameters.
     *
     * @param fov the field of view in radians
     * @param windowWidth the width of the window
     * @param windowHeight the height of the window
     * @param zFar the far clipping plane distance
     * @param zNear the near clipping plane distance
     * @return the updated Projection instance
     */
    public Projection updateProjMatrix(float fov, int windowWidth, int windowHeight, float zFar, float zNear) {
        float aspectWindow = (float) windowWidth / windowHeight;

        switch (projectionType) {
            case ORTHOGRAPHIC_PROJECTION -> {
                projMatrix = new Matrix4f().ortho(0, windowWidth, 0, windowHeight, -1f, 1f);
            }
            case PERSPECTIVE_PROJECTION -> {
                projMatrix = new Matrix4f().setPerspective(fov, aspectWindow, zNear, zFar);
            }
            default -> throw new IllegalStateException("Unexpected value: " + projectionType);
        }

        return this;
    }

    /**
     * Gets the projection type.
     *
     * @return the projection type
     */
    public ProjectionType getProjectionType() {
        return projectionType;
    }

    /**
     * Sets the projection type.
     *
     * @param projectionType the new projection type
     */
    public void setProjectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
    }

    /**
     * Gets the field of view.
     *
     * @return the field of view in radians
     */
    public float getFov() {
        return fov;
    }

    /**
     * Sets the field of view.
     *
     * @param fov the new field of view in radians
     */
    public void setFov(float fov) {
        this.fov = fov;
    }

    /**
     * Gets the window width.
     *
     * @return the window width
     */
    public int getWindowWidth() {
        return windowWidth;
    }

    /**
     * Sets the window width.
     *
     * @param windowWidth the new window width
     */
    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    /**
     * Gets the window height.
     *
     * @return the window height
     */
    public int getWindowHeight() {
        return windowHeight;
    }

    /**
     * Sets the window height.
     *
     * @param windowHeight the new window height
     */
    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    /**
     * Gets the far clipping plane distance.
     *
     * @return the far clipping plane distance
     */
    public float getzFar() {
        return zFar;
    }

    /**
     * Sets the far clipping plane distance.
     *
     * @param zFar the new far clipping plane distance
     */
    public void setzFar(float zFar) {
        this.zFar = zFar;
    }

    /**
     * Gets the near clipping plane distance.
     *
     * @return the near clipping plane distance
     */
    public float getzNear() {
        return zNear;
    }

    /**
     * Sets the near clipping plane distance.
     *
     * @param zNear the new near clipping plane distance
     */
    public void setzNear(float zNear) {
        this.zNear = zNear;
    }
}