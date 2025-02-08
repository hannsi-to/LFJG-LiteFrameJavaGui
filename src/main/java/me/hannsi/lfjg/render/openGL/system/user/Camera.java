package me.hannsi.lfjg.render.openGL.system.user;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Represents a camera in the OpenGL rendering system.
 * Handles camera position, rotation, and view matrix calculations.
 */
public class Camera {
    protected Vector3f direction;
    protected Matrix4f invViewMatrix;
    protected Vector3f position;
    protected Vector3f right;
    protected Vector2f rotation;
    protected Vector3f up;
    protected Matrix4f viewMatrix;

    /**
     * Constructs a new Camera instance with default values.
     */
    public Camera() {
        direction = new Vector3f();
        right = new Vector3f();
        up = new Vector3f();
        position = new Vector3f();
        viewMatrix = new Matrix4f();
        invViewMatrix = new Matrix4f();
        rotation = new Vector2f();
    }

    /**
     * Adds rotation to the camera.
     *
     * @param x the rotation around the X axis
     * @param y the rotation around the Y axis
     */
    public void addRotation(float x, float y) {
        rotation.add(x, y);
        recalculate();
    }

    /**
     * Gets the inverse view matrix of the camera.
     *
     * @return the inverse view matrix
     */
    public Matrix4f getInvViewMatrix() {
        return invViewMatrix;
    }

    /**
     * Gets the position of the camera.
     *
     * @return the position vector
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Gets the view matrix of the camera.
     *
     * @return the view matrix
     */
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    /**
     * Moves the camera backwards by the specified increment.
     *
     * @param inc the increment to move backwards
     */
    public void moveBackwards(float inc) {
        viewMatrix.positiveZ(direction).negate().mul(inc);
        position.sub(direction);
        recalculate();
    }

    /**
     * Moves the camera down by the specified increment.
     *
     * @param inc the increment to move down
     */
    public void moveDown(float inc) {
        viewMatrix.positiveY(up).mul(inc);
        position.sub(up);
        recalculate();
    }

    /**
     * Moves the camera forward by the specified increment.
     *
     * @param inc the increment to move forward
     */
    public void moveForward(float inc) {
        viewMatrix.positiveZ(direction).negate().mul(inc);
        position.add(direction);
        recalculate();
    }

    /**
     * Moves the camera left by the specified increment.
     *
     * @param inc the increment to move left
     */
    public void moveLeft(float inc) {
        viewMatrix.positiveX(right).mul(inc);
        position.sub(right);
        recalculate();
    }

    /**
     * Moves the camera right by the specified increment.
     *
     * @param inc the increment to move right
     */
    public void moveRight(float inc) {
        viewMatrix.positiveX(right).mul(inc);
        position.add(right);
        recalculate();
    }

    /**
     * Moves the camera up by the specified increment.
     *
     * @param inc the increment to move up
     */
    public void moveUp(float inc) {
        viewMatrix.positiveY(up).mul(inc);
        position.add(up);
        recalculate();
    }

    /**
     * Recalculates the view matrix and inverse view matrix based on the current position and rotation.
     */
    private void recalculate() {
        viewMatrix.identity().rotateX(rotation.x).rotateY(rotation.y).translate(-position.x, -position.y, -position.z);
        invViewMatrix.set(viewMatrix).invert();
    }

    /**
     * Sets the position of the camera.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     */
    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        recalculate();
    }

    /**
     * Sets the rotation of the camera.
     *
     * @param x the rotation around the X axis
     * @param y the rotation around the Y axis
     */
    public void setRotation(float x, float y) {
        rotation.set(x, y);
        recalculate();
    }
}