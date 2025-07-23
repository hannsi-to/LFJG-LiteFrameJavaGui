package me.hannsi.lfjg.physic;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PhysicWorld {
    public static final Vector2f DEFAULT_GRAVITY = new Vector2f(0, -9.81f);
    public static final float DEFAULT_SCALE = 10f;

    private final List<RigidBody> rigidBodies;
    private Vector2f gravity;
    private float scale;

    PhysicWorld() {
        this.gravity = DEFAULT_GRAVITY;
        this.scale = DEFAULT_SCALE;
        this.rigidBodies = new ArrayList<>();
    }

    public static PhysicWorld initPhysicWorld() {
        return new PhysicWorld();
    }

    public PhysicWorld setGravity(Vector2f gravity) {
        this.gravity = gravity;
        return this;
    }

    public PhysicWorld setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public PhysicWorld addRigidBody(RigidBody rigidBody) {
        this.rigidBodies.add(rigidBody);
        return this;
    }

    public PhysicWorld removeRigidBody(RigidBody rigidBody) {
        this.rigidBodies.remove(rigidBody);
        return this;
    }

    public void simulate(float deltaTime) {
        if (Float.isNaN(deltaTime) || Float.isInfinite(deltaTime) || deltaTime <= 0.000001f) {
            return;
        }

        for (RigidBody mainRigidBody : rigidBodies) {
            for (RigidBody subRigidBody : rigidBodies) {
                if (mainRigidBody == subRigidBody) {
                    continue;
                }
            }

            if (!mainRigidBody.noGravity) {
                mainRigidBody.applyForce(gravity.mul(mainRigidBody.mass, new Vector2f()));
            }
            mainRigidBody.integrate(deltaTime, scale);
        }
    }
}
