package me.hannsi.lfjg.render.system.model;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Entity {
    private final String id;
    private final Matrix4f modelMatrix;
    private final Vector3f position;
    private final Quaternionf rotation;
    private float scale;

    Entity(String id) {
        this.id = id;
        modelMatrix = new Matrix4f();
        position = new Vector3f();
        rotation = new Quaternionf();
        scale = 1;
    }

    public static Entity createEntity(String id) {
        return new Entity(id);
    }

    public Entity setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
        return this;
    }

    public Entity setRotation(float x, float y, float z, float angle) {
        this.rotation.fromAxisAngleRad(x, y, z, angle);
        return this;
    }

    public Entity updateModelMatrix() {
        modelMatrix.translationRotateScale(position, rotation, scale);
        return this;
    }

    public String getId() {
        return id;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
