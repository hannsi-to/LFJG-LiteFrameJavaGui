package me.hannsi.lfjg.core.utils.math;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.joml.Matrix4f;

/**
 * Class representing a projection matrix for 3D rendering.
 */
@Getter
@Setter
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

    /**
     * -- SETTER --
     * Sets the projection type.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the projection type.
     *
     * @param projectionType the new projection type
     * @return the projection type
     */
    private ProjectionType projectionType;
    /**
     * -- SETTER --
     * Sets the field of view.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the field of view.
     *
     * @param fov the new field of view in radians
     * @return the field of view in radians
     */
    private float fov;
    /**
     * -- SETTER --
     * Sets the window width.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the window width.
     *
     * @param windowWidth the new window width
     * @return the window width
     */
    private int windowWidth;
    /**
     * -- SETTER --
     * Sets the window height.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the window height.
     *
     * @param windowHeight the new window height
     * @return the window height
     */
    private int windowHeight;
    /**
     * -- SETTER --
     * Sets the far clipping plane distance.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the far clipping plane distance.
     *
     * @param zFar the new far clipping plane distance
     * @return the far clipping plane distance
     */
    private float zFar;
    /**
     * -- SETTER --
     * Sets the near clipping plane distance.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the near clipping plane distance.
     *
     * @param zNear the new near clipping plane distance
     * @return the near clipping plane distance
     */
    private float zNear;

    /**
     * -- SETTER --
     * Sets the projection matrix.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the projection matrix.
     *
     * @param projMatrix the new projection matrix
     * @return the projection matrix
     */
    private Matrix4f projMatrix;

    /**
     * Constructs a Projection instance with specified parameters.
     *
     * @param projectionType the type of projection (orthographic or perspective)
     * @param fov            the field of view in radians
     * @param windowWidth    the width of the window
     * @param windowHeight   the height of the window
     * @param zFar           the far clipping plane distance
     * @param zNear          the near clipping plane distance
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
     * @param windowWidth    the width of the window
     * @param windowHeight   the height of the window
     */
    public Projection(ProjectionType projectionType, int windowWidth, int windowHeight) {
        this(projectionType, DEFAULT_FOV, windowWidth, windowHeight, DEFAULT_Z_FAR, DEFAULT_Z_NEAR);
    }

    /**
     * Updates the projection matrix based on the specified parameters.
     *
     * @param fov          the field of view in radians
     * @param windowWidth  the width of the window
     * @param windowHeight the height of the window
     * @param zFar         the far clipping plane distance
     * @param zNear        the near clipping plane distance
     */
    public void updateProjMatrix(float fov, int windowWidth, int windowHeight, float zFar, float zNear) {
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
    }

}