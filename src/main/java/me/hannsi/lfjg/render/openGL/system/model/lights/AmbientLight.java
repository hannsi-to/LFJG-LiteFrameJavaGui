package me.hannsi.lfjg.render.openGL.system.model.lights;

import org.joml.Vector3f;

/**
 * Represents an ambient light in the OpenGL rendering system.
 */
public class AmbientLight extends Light {
    private Vector3f color;
    private float intensity;

    /**
     * Constructs an AmbientLight with the specified intensity and color.
     *
     * @param intensity the intensity of the ambient light
     * @param color the color of the ambient light
     */
    public AmbientLight(float intensity, Vector3f color) {
        super("AmbientLight", 0);

        this.intensity = intensity;
        this.color = color;
    }

    /**
     * Constructs an AmbientLight with default intensity and color.
     */
    public AmbientLight() {
        this(1.0f, new Vector3f(1.0f, 1.0f, 1.0f));
    }

    /**
     * Gets the color of the ambient light.
     *
     * @return the color of the ambient light
     */
    public Vector3f getColor() {
        return color;
    }

    /**
     * Sets the color of the ambient light.
     *
     * @param color the new color of the ambient light
     */
    public void setColor(Vector3f color) {
        this.color = color;
    }

    /**
     * Gets the intensity of the ambient light.
     *
     * @return the intensity of the ambient light
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity of the ambient light.
     *
     * @param intensity the new intensity of the ambient light
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * Sets the color of the ambient light using individual RGB components.
     *
     * @param r the red component of the color
     * @param g the green component of the color
     * @param b the blue component of the color
     */
    public void setColor(float r, float g, float b) {
        color.set(r, g, b);
    }
}