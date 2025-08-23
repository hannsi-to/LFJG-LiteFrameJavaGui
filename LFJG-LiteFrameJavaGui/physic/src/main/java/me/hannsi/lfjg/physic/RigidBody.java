package me.hannsi.lfjg.physic;

import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2f;

public class RigidBody {
    public GLObject glObject;
    public float mass;
    public boolean noGravity;

    public Vector2f velocity;
    public Vector2f acceleration;

    RigidBody() {
        this.mass = 1;
        this.noGravity = false;

        this.velocity = new Vector2f();
        this.acceleration = new Vector2f();
    }

    public static RigidBody createRigidBody() {
        return new RigidBody();
    }

    public RigidBody setMass(float mass) {
        this.mass = mass;
        return this;
    }

    public RigidBody setNoGravity(boolean noGravity) {
        this.noGravity = noGravity;
        return this;
    }

    public RigidBody attachGLObject(GLObject glObject) {
        this.glObject = glObject;
        return this;
    }

    public void applyForce(Vector2f force) {
        acceleration.add(force.x() / mass, force.y() / mass);
    }

    public void integrate(float dt, float scale) {
        velocity.add(acceleration.mul(dt, new Vector2f()));
        Vector2f deltaMove = velocity.mul(dt, new Vector2f());
        glObject.getTransform().translate(deltaMove.x() * scale, deltaMove.y() * scale, 0);
        acceleration = new Vector2f();
    }
}
