package me.hannsi.lfjg.render.openGL.system.model.lights;

import org.joml.Vector3f;

public class AmbientLight extends Light {
    private Vector3f color;
    private float intensity;

    public AmbientLight(float intensity, Vector3f color) {
        super("AmbientLight", 0);

        this.intensity = intensity;
        this.color = color;
    }

    public AmbientLight() {
        this(1.0f, new Vector3f(1.0f, 1.0f, 1.0f));
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public void setColor(float r, float g, float b) {
        color.set(r, g, b);
    }
}