package me.hannsi.lfjg.render.openGL.system.model.lights;

public class Light {
    final String name;
    final int id;

    public Light(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
