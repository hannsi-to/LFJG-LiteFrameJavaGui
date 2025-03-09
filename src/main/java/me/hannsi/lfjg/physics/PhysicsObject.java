package me.hannsi.lfjg.physics;

import me.hannsi.lfjg.utils.math.MathHelper;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PhysicsObject {
    private static final float DEFAULT_MASS = 1f;
    private static final float DEFAULT_RESTITUTION = 0.5f;
    private static final float DEFAULT_FRICTION = 0.5f;

    private List<Vector2f> positions;
    private Vector2f velocity;
    private Vector2f acceleration;
    private float angularVelocity;
    private float mass;
    private float inertia;
    private float restitution;
    private float friction;
    private SATCollision satCollision;
    private AABBCollision aabbCollision;

    public PhysicsObject(float mass, float restitution, float friction) {
        this.positions = new ArrayList<>();
        this.velocity = new Vector2f();
        this.acceleration = new Vector2f();
        this.angularVelocity = 0;
        this.mass = mass;
        this.restitution = restitution;
        this.friction = friction;
    }

    public PhysicsObject(float mass) {
        this(mass, DEFAULT_RESTITUTION, DEFAULT_FRICTION);
    }

    public PhysicsObject() {
        this(DEFAULT_MASS);
    }

    public PhysicsObject addPos(Vector2f pos) {
        positions.add(pos);
        return this;
    }

    public void calculateInertia() {
        float sum = 0;
        Vector2f center = getCenter();
        for (Vector2f position : positions) {
            float dx = position.x() - center.x();
            float dy = position.y() - center.y();
            sum += dx * dx + dy * dy;
        }

        inertia = mass * sum / positions.size();
    }

    private Vector2f getCenter() {
        float sumX = 0;
        float sumY = 0;
        for (Vector2f position : positions) {
            sumX += position.x();
            sumY += position.y();
        }

        return new Vector2f(sumX / positions.size(), sumY / positions.size());
    }

    public void applyForce(Vector2f force) {
        acceleration = new Vector2f(force.x() / mass, force.y() / mass);
    }

    public void update(double deltaTime) {
        velocity.add(acceleration.x() * (float) deltaTime, acceleration.y() * (float) deltaTime);

        for (Vector2f position : positions) {
            float relativeX = position.x - getCenter().x;
            float relativeY = position.y - getCenter().y;
            position.x += -angularVelocity * relativeY * (float) deltaTime;
            position.y += angularVelocity * relativeX * (float) deltaTime;
        }

        positions.forEach(position -> position.add(velocity.x() * (float) deltaTime, velocity.y() * (float) deltaTime));
    }

    public void resolveCollision(PhysicsObject other) {
        if (!CollisionUtil.checkCollision(this, other)) {
            return;
        }

        Vector2f collisionNormal = CollisionUtil.getCollisionNormal(this, other);
        if (collisionNormal == null) {
            return;
        }

        Vector2f relativeVelocity = new Vector2f(velocity).sub(other.velocity);
        float velocityAlongNormal = relativeVelocity.dot(collisionNormal);
        if (velocityAlongNormal > 0) {
            return;
        }

        float e = MathHelper.min(restitution, other.getRestitution());

        float impulseMagnitude = -(1 + e) * velocityAlongNormal / (1 / mass + 1 / other.getMass());
        Vector2f impulse = new Vector2f(collisionNormal).mul(impulseMagnitude);

        velocity.add(impulse.x / mass, impulse.y / mass);
        other.velocity.sub(impulse.x / other.mass, impulse.y / other.mass);

        Vector2f collisionPoint = CollisionUtil.getCollisionPoint(this, other);
        Vector2f rA = new Vector2f(collisionPoint).sub(getCenter());
        Vector2f rB = new Vector2f(collisionPoint).sub(other.getCenter());

        float angularImpulseA = MathHelper.crossProduct(rA, impulse) / inertia;
        float angularImpulseB = MathHelper.crossProduct(rB, impulse) / other.inertia;

        angularVelocity += angularImpulseA;
        other.angularVelocity -= angularImpulseB;

        velocity.mul(1 - friction);
        angularVelocity *= (1 - friction);
    }

    public List<Vector2f> getPositions() {
        return positions;
    }

    public void setPositions(List<Vector2f> positions) {
        this.positions = positions;
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

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public SATCollision getSatCollision() {
        return satCollision;
    }

    public void setSatCollision(SATCollision satCollision) {
        this.satCollision = satCollision;
    }

    public AABBCollision getAabbCollision() {
        return aabbCollision;
    }

    public void setAabbCollision(AABBCollision aabbCollision) {
        this.aabbCollision = aabbCollision;
    }

    public float getInertia() {
        return inertia;
    }

    public void setInertia(float inertia) {
        this.inertia = inertia;
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
}
