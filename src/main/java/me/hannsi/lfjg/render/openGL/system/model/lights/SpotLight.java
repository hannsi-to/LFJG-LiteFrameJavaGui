package me.hannsi.lfjg.render.openGL.system.model.lights;

import org.joml.Vector3f;

/**
 * Represents a spotlight in the OpenGL rendering system.
 */
public class SpotLight extends Light {
    private Vector3f coneDirection;
    private float cutOff;
    private float cutOffAngle;
    private PointLight pointLight;

    /**
     * Constructs a new SpotLight with the specified point light, cone direction, and cutoff angle.
     *
     * @param pointLight the point light associated with the spotlight
     * @param coneDirection the direction of the spotlight's cone
     * @param cutOffAngle the cutoff angle of the spotlight
     */
    public SpotLight(PointLight pointLight, Vector3f coneDirection, float cutOffAngle) {
        super("SpotLight", 3);

        this.pointLight = pointLight;
        this.coneDirection = coneDirection;
        this.cutOffAngle = cutOffAngle;
        setCutOffAngle(cutOffAngle);
    }

    /**
     * Gets the direction of the spotlight's cone.
     *
     * @return the direction of the spotlight's cone
     */
    public Vector3f getConeDirection() {
        return coneDirection;
    }

    /**
     * Sets the direction of the spotlight's cone.
     *
     * @param coneDirection the new direction of the spotlight's cone
     */
    public void setConeDirection(Vector3f coneDirection) {
        this.coneDirection = coneDirection;
    }

    /**
     * Gets the cutoff value of the spotlight.
     *
     * @return the cutoff value of the spotlight
     */
    public float getCutOff() {
        return cutOff;
    }

    /**
     * Gets the cutoff angle of the spotlight.
     *
     * @return the cutoff angle of the spotlight
     */
    public float getCutOffAngle() {
        return cutOffAngle;
    }

    /**
     * Sets the cutoff angle of the spotlight.
     *
     * @param cutOffAngle the new cutoff angle of the spotlight
     */
    public final void setCutOffAngle(float cutOffAngle) {
        this.cutOffAngle = cutOffAngle;
        cutOff = (float) Math.cos(Math.toRadians(cutOffAngle));
    }

    /**
     * Gets the point light associated with the spotlight.
     *
     * @return the point light associated with the spotlight
     */
    public PointLight getPointLight() {
        return pointLight;
    }

    /**
     * Sets the point light associated with the spotlight.
     *
     * @param pointLight the new point light associated with the spotlight
     */
    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    /**
     * Sets the direction of the spotlight's cone using individual coordinates.
     *
     * @param x the x-coordinate of the cone direction
     * @param y the y-coordinate of the cone direction
     * @param z the z-coordinate of the cone direction
     */
    public void setConeDirection(float x, float y, float z) {
        coneDirection.set(x, y, z);
    }
}