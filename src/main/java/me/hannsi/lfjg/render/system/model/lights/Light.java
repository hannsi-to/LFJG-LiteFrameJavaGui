package me.hannsi.lfjg.render.system.model.lights;

/**
 * Represents a generic light in the OpenGL rendering system.
 */
public class Light {
    final String name;
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

    /**
     * Gets the name of the light.
     *
     * @return the name of the light
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the id of the light.
     *
     * @return the id of the light
     */
    public int getId() {
        return id;
    }
}