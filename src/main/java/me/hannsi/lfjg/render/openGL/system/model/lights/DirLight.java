package me.hannsi.lfjg.render.openGL.system.model.lights;

import org.joml.Vector3f;

public class DirLight extends Light {
    private Vector3f color;
    private Vector3f direction;
    private float intensity;

    public DirLight(Vector3f color, Vector3f direction, float intensity) {
        super("DirLight", 1);

        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
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

    public void setPosition(float x, float y, float z) {
        direction.set(x, y, z);
    }
}