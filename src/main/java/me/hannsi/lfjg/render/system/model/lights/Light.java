package me.hannsi.lfjg.render.system.model.lights;

import lombok.Getter;

/**
 * Represents a generic light in the OpenGL rendering system.
 */
@Getter
public class Light {
    /**
     * -- GETTER --
     *  Gets the name of the light.
     *
     * @return the name of the light
     */
    final String name;
    /**
     * -- GETTER --
     *  Gets the id of the light.
     *
     * @return the id of the light
     */
    final int id;

    /**
     * Constructs a new Light with the specified name and id.
     *
     * @param name the name of the light
     * @param id the id of the light
     */
    public Light(String name, int id) {
        this.name = name;
        this.id = id;
    }

}