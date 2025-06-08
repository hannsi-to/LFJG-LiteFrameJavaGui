package me.hannsi.lfjg.physic;

import me.hannsi.lfjg.physic.collision.AxisAlignedBoundingBox;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2f;

public class PhysicObject {
    public static boolean DEFAULT_GRAVITY = true;
    public static boolean DEFAULT_MOVE = true;

    public static float DEFAULT_MASS = 1f;
    public static float DEFAULT_RESTITUTION = 1f;

    public GLObject glObject;

    public boolean gravity;
    public boolean move;

    public float mass;
    public Vector2f position;
    public Vector2f velocity;
    public Vector2f acceleration;
    public float restitution;

    public PhysicObject() {
        gravity = DEFAULT_GRAVITY;
        move = DEFAULT_MOVE;

        mass = DEFAULT_MASS;
        position = new Vector2f();
        velocity = new Vector2f();
        acceleration = new Vector2f();
        restitution = DEFAULT_RESTITUTION;
    }

    public PhysicObject linkGLObject(GLObject glObject) {
        this.glObject = glObject;
        this.position = new Vector2f(glObject.getCenterX(), glObject.getCenterY());

        return this;
    }

    public AxisAlignedBoundingBox getAABB() {
        return new AxisAlignedBoundingBox(new Vector2f(glObject.getCenterX() - glObject.getWidth() / 2, glObject.getCenterY() - glObject.getHeight() / 2), new Vector2f(glObject.getCenterX() + glObject.getWidth() / 2, glObject.getCenterY() + glObject.getHeight() / 2));
    }

    public void applyForce(Vector2f force) {
        Vector2f a = new Vector2f(force.x() / mass, force.y() / mass);
        acceleration = acceleration.add(a, new Vector2f());
    }

    public void simulation(float deltaTime, float scale) {
        if (!move) {
            return;
        }

        velocity = velocity.add(acceleration.mul(deltaTime, new Vector2f()), new Vector2f());
        Vector2f deltaMove = velocity.mul(deltaTime, new Vector2f());
        glObject.translate(deltaMove.x() * scale, deltaMove.y() * scale, 0);
        position = new Vector2f(glObject.getCenterX(), glObject.getCenterY());
        acceleration = new Vector2f();
    }

    public GLObject getGlObject() {
        return glObject;
    }

    public void setGlObject(GLObject glObject) {
        this.glObject = glObject;
    }

    public boolean isGravity() {
        return gravity;
    }

    public PhysicObject setGravity(boolean gravity) {
        this.gravity = gravity;

        return this;
    }

    public boolean isMove() {
        return move;
    }

    public PhysicObject setMove(boolean move) {
        this.move = move;

        return this;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public Vector2f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2f acceleration) {
        this.acceleration = acceleration;
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }
}
