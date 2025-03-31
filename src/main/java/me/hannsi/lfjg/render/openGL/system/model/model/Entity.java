package me.hannsi.lfjg.render.openGL.system.model.model;

import me.hannsi.lfjg.debug.debug.system.DebugLevel;
import me.hannsi.lfjg.debug.debug.logger.LogGenerator;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Represents an entity in the OpenGL rendering system.
 */
public class Entity {
    private final String id;
    private final String modelId;
    private Matrix4f modelMatrix;
    private Vector3f position;
    private Quaternionf rotation;
    private float scale;

    /**
     * Constructs a new Entity with the specified id and modelId.
     *
     * @param id      the unique identifier of the entity
     * @param modelId the identifier of the model associated with the entity
     */
    public Entity(String id, String modelId) {
        this.id = id;
        this.modelId = modelId;
        modelMatrix = new Matrix4f();
        position = new Vector3f();
        rotation = new Quaternionf();
        scale = 1;
    }

    public void cleanup() {
        modelMatrix = null;
        position = null;
        rotation = null;

        LogGenerator logGenerator = new LogGenerator(id, "Source: Entity", "Type: Cleanup", "ID: " + modelId, "Severity: Debug", "Message: Entity cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Gets the unique identifier of the entity.
     *
     * @return the unique identifier of the entity
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the identifier of the model associated with the entity.
     *
     * @return the identifier of the model associated with the entity
     */
    public String getModelId() {
        return modelId;
    }

    /**
     * Gets the model matrix of the entity.
     *
     * @return the model matrix of the entity
     */
    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    /**
     * Sets the model matrix of the entity.
     *
     * @param modelMatrix the new model matrix of the entity
     */
    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    /**
     * Gets the position of the entity.
     *
     * @return the position of the entity
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Sets the position of the entity.
     *
     * @param position the new position of the entity
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Gets the rotation of the entity.
     *
     * @return the rotation of the entity
     */
    public Quaternionf getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation of the entity.
     *
     * @param rotation the new rotation of the entity
     */
    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
    }

    /**
     * Gets the scale of the entity.
     *
     * @return the scale of the entity
     */
    public float getScale() {
        return scale;
    }

    /**
     * Sets the scale of the entity.
     *
     * @param scale the new scale of the entity
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * Sets the position of the entity using individual coordinates.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @param z the z-coordinate of the position
     */
    public final void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    /**
     * Sets the rotation of the entity using individual coordinates and an angle.
     *
     * @param x     the x-coordinate of the rotation axis
     * @param y     the y-coordinate of the rotation axis
     * @param z     the z-coordinate of the rotation axis
     * @param angle the angle of rotation in radians
     */
    public void setRotation(float x, float y, float z, float angle) {
        this.rotation.fromAxisAngleRad(x, y, z, angle);
    }

    /**
     * Updates the model matrix of the entity based on its position, rotation, and scale.
     */
    public void updateModelMatrix() {
        modelMatrix.translationRotateScale(position, rotation, scale);
    }
}