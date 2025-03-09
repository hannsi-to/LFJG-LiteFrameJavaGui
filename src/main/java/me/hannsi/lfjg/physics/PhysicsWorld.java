package me.hannsi.lfjg.physics;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PhysicsWorld {
    public static final float DEFAULT_GRAVITY = 9.8f;

    private final List<PhysicsObject> physicsObjects;
    private final Vector2f gravity;
    private long latestTime;

    public PhysicsWorld() {
        this(DEFAULT_GRAVITY);
    }

    public PhysicsWorld(float gravityY) {
        this.physicsObjects = new ArrayList<>();
        this.gravity = new Vector2f(0, gravityY);
        this.latestTime = 0L;
    }

    public void simulate() {
        float deltaTime = (float) ((System.currentTimeMillis() - this.latestTime) / 1000.0);

        for (PhysicsObject physicsObject : physicsObjects) {
            physicsObject.applyForce(gravity);
            for (PhysicsObject other : physicsObjects) {
                physicsObject.resolveCollision(other);
            }
            physicsObject.update(deltaTime);
        }

        this.latestTime = System.currentTimeMillis();
    }

    public List<PhysicsObject> getPhysicsObjects() {
        return physicsObjects;
    }

    public Vector2f getGravity() {
        return gravity;
    }

    public long getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(long latestTime) {
        this.latestTime = latestTime;
    }
}
