package me.hannsi.lfjg.render.system.model.lights;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;

/**
 * Represents a directional light in the OpenGL rendering system.
 */
@Getter
public class DirLight extends Light {
    /**
     * -- GETTER --
     *  Gets the color of the light.
     *
     * @return the color of the light
     */
    private Vector3f color;
    /**
     * -- SETTER --
     *  Sets the direction of the light.
     *
     *
     * -- GETTER --
     *  Gets the direction of the light.
     *
     @param direction the new direction of the light
      * @return the direction of the light
     */
    @Setter
    private Vector3f direction;
    /**
     * -- SETTER --
     *  Sets the intensity of the light.
     *
     *
     * -- GETTER --
     *  Gets the intensity of the light.
     *
     @param intensity the new intensity of the light
      * @return the intensity of the light
     */
    @Setter
    private float intensity;

    /**
     * Constructs a new DirLight with the specified color, direction, and intensity.
     *
     * @param color the color of the light
     * @param direction the direction of the light
     * @param intensity the intensity of the light
     */
    public DirLight(Vector3f color, Vector3f direction, float intensity) {
        super("DirLight", 1);

        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
    }

    /**
     * Sets the color of the light.
     *
     * @param color the new color of the light
     */
    public void setColor(Vector3f color) {
        this.color = color;
    }

    /**
     * Sets the color of the light using individual RGB components.
     *
     * @param r the red component of the color
     * @param g the green component of the color
     * @param b the blue component of the color
     */
    public void setColor(float r, float g, float b) {
        color.set(r, g, b);
    }

    /**
     * Sets the position of the light using individual coordinates.
     *
     * @param x the x-coordinate of the direction
     * @param y the y-coordinate of the direction
     * @param z the z-coordinate of the direction
     */
    public void setPosition(float x, float y, float z) {
        direction.set(x, y, z);
    }
}