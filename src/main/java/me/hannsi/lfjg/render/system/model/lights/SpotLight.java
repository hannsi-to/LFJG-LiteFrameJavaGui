package me.hannsi.lfjg.render.system.model.lights;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;

/**
 * Represents a spotlight in the OpenGL rendering system.
 */
@Getter
public class SpotLight extends Light {
    /**
     * -- GETTER --
     *  Gets the direction of the spotlight's cone.
     *
     * @return the direction of the spotlight's cone
     */
    private Vector3f coneDirection;
    /**
     * -- GETTER --
     *  Gets the cutoff value of the spotlight.
     *
     * @return the cutoff value of the spotlight
     */
    private float cutOff;
    /**
     * -- GETTER --
     *  Gets the cutoff angle of the spotlight.
     *
     * @return the cutoff angle of the spotlight
     */
    private float cutOffAngle;
    /**
     * -- SETTER --
     *  Sets the point light associated with the spotlight.
     *
     *
     * -- GETTER --
     *  Gets the point light associated with the spotlight.
     *
     @param pointLight the new point light associated with the spotlight
      * @return the point light associated with the spotlight
     */
    @Setter
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
     * Sets the direction of the spotlight's cone.
     *
     * @param coneDirection the new direction of the spotlight's cone
     */
    public void setConeDirection(Vector3f coneDirection) {
        this.coneDirection = coneDirection;
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