package me.hannsi.lfjg.physic;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import org.joml.Vector2f;

public class PhysicObject {
    public static float DEFAULT_MASS = 1f;

    public GLObject glObject;

    public float mass;
    public Vector2f velocity;
    public Vector2f acceleration;

    public PhysicObject() {
        velocity = new Vector2f();
        acceleration = new Vector2f();
        mass = DEFAULT_MASS;
    }

    public void linkGLObject(GLObject glObject) {
        this.glObject = glObject;
    }

    public void applyForce(Vector2f force) {

    }

    public void applyAcceleration(Vector2f acceleration) {
        this.acceleration.add(acceleration.x(), acceleration.y());
    }

    public void simulation(float deltaTime) {
        glObject.translate(velocity.x() * deltaTime, velocity.y() * deltaTime, 0);
    }
}
