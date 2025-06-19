package me.hannsi.lfjg.render.system.model.lights;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages different types of lights in the OpenGL rendering system.
 */
@Getter
@Setter
public class Lights {
    /**
     * -- SETTER --
     *  Sets the ambient light.
     *
     *
     * -- GETTER --
     *  Gets the ambient light.
     *
     @param ambientLight the new ambient light
      * @return the ambient light
     */
    private AmbientLight ambientLight;
    /**
     * -- SETTER --
     *  Sets the directional light.
     *
     *
     * -- GETTER --
     *  Gets the directional light.
     *
     @param dirLight the new directional light
      * @return the directional light
     */
    private DirLight dirLight;
    /**
     * -- SETTER --
     *  Sets the list of point lights.
     *
     *
     * -- GETTER --
     *  Gets the list of point lights.
     *
     @param pointLights the new list of point lights
      * @return the list of point lights
     */
    private List<PointLight> pointLights;
    /**
     * -- SETTER --
     *  Sets the list of spot lights.
     *
     *
     * -- GETTER --
     *  Gets the list of spot lights.
     *
     @param spotLights the new list of spot lights
      * @return the list of spot lights
     */
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

}