package me.hannsi.lfjg.render.system.model.lights;

import org.joml.Vector3f;

/**
 * Represents a point light in the OpenGL rendering system.
 */
public class PointLight extends Light {
    private Attenuation attenuation;
    private Vector3f color;
    private float intensity;
    private Vector3f position;

    /**
     * Constructs a new PointLight with the specified color, position, and intensity.
     *
     * @param color the color of the light
     * @param position the position of the light
     * @param intensity the intensity of the light
     */
    public PointLight(Vector3f color, Vector3f position, float intensity) {
        super("PointLight", 2);

        attenuation = new Attenuation(0, 0, 1);
        this.color = color;
        this.position = position;
        this.intensity = intensity;
    }

    /**
     * Gets the attenuation of the light.
     *
     * @return the attenuation of the light
     */
    public Attenuation getAttenuation() {
        return attenuation;
    }

    /**
     * Sets the attenuation of the light.
     *
     * @param attenuation the new attenuation of the light
     */
    public void setAttenuation(Attenuation attenuation) {
        this.attenuation = attenuation;
    }

    /**
     * Gets the color of the light.
     *
     * @return the color of the light
     */
    public Vector3f getColor() {
        return color;
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
     * Gets the intensity of the light.
     *
     * @return the intensity of the light
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity of the light.
     *
     * @param intensity the new intensity of the light
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the position of the light.
     *
     * @return the position of the light
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Sets the position of the light.
     *
     * @param position the new position of the light
     */
    public void setPosition(Vector3f position) {
        this.position = position;
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
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @param z the z-coordinate of the position
     */
    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
    }

    /**
     * Represents the attenuation of a point light.
     */
    public static class Attenuation {

        private float constant;
        private float exponent;
        private float linear;

        /**
         * Constructs a new Attenuation with the specified constant, linear, and exponent values.
         *
         * @param constant the constant attenuation factor
         * @param linear the linear attenuation factor
         * @param exponent the exponent attenuation factor
         */
        public Attenuation(float constant, float linear, float exponent) {
            this.constant = constant;
            this.linear = linear;
            this.exponent = exponent;
        }

        /**
         * Gets the constant attenuation factor.
         *
         * @return the constant attenuation factor
         */
        public float getConstant() {
            return constant;
        }

        /**
         * Sets the constant attenuation factor.
         *
         * @param constant the new constant attenuation factor
         */
        public void setConstant(float constant) {
            this.constant = constant;
        }

        /**
         * Gets the exponent attenuation factor.
         *
         * @return the exponent attenuation factor
         */
        public float getExponent() {
            return exponent;
        }

        /**
         * Sets the exponent attenuation factor.
         *
         * @param exponent the new exponent attenuation factor
         */
        public void setExponent(float exponent) {
            this.exponent = exponent;
        }

        /**
         * Gets the linear attenuation factor.
         *
         * @return the linear attenuation factor
         */
        public float getLinear() {
            return linear;
        }

        /**
         * Sets the linear attenuation factor.
         *
         * @param linear the new linear attenuation factor
         */
        public void setLinear(float linear) {
            this.linear = linear;
        }
    }
}