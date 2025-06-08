package me.hannsi.lfjg.render.system.model.lights;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages different types of lights in the OpenGL rendering system.
 */
public class Lights {
    private AmbientLight ambientLight;
    private DirLight dirLight;
    private List<PointLight> pointLights;
    private List<SpotLight> spotLights;

    /**
     * Constructs a new Lights object with default ambient and directional lights.
     */
    public Lights() {
        ambientLight = new AmbientLight();
        pointLights = new ArrayList<>();
        spotLights = new ArrayList<>();
        dirLight = new DirLight(new Vector3f(1, 1, 1), new Vector3f(0, 1, 0), 1.0f);
    }

    public void cleanup() {
        ambientLight = null;
        dirLight = null;
        pointLights.clear();
        spotLights.clear();
    }

    /**
     * Gets the ambient light.
     *
     * @return the ambient light
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * Sets the ambient light.
     *
     * @param ambientLight the new ambient light
     */
    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }

    /**
     * Gets the directional light.
     *
     * @return the directional light
     */
    public DirLight getDirLight() {
        return dirLight;
    }

    /**
     * Sets the directional light.
     *
     * @param dirLight the new directional light
     */
    public void setDirLight(DirLight dirLight) {
        this.dirLight = dirLight;
    }

    /**
     * Gets the list of point lights.
     *
     * @return the list of point lights
     */
    public List<PointLight> getPointLights() {
        return pointLights;
    }

    /**
     * Sets the list of point lights.
     *
     * @param pointLights the new list of point lights
     */
    public void setPointLights(List<PointLight> pointLights) {
        this.pointLights = pointLights;
    }

    /**
     * Gets the list of spot lights.
     *
     * @return the list of spot lights
     */
    public List<SpotLight> getSpotLights() {
        return spotLights;
    }

    /**
     * Sets the list of spot lights.
     *
     * @param spotLights the new list of spot lights
     */
    public void setSpotLights(List<SpotLight> spotLights) {
        this.spotLights = spotLights;
    }
}