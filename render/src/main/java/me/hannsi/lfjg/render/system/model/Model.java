package me.hannsi.lfjg.render.system.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Model {
    private final String id;
    private final List<Entity> entities;
    private final List<Material> materials;

    Model(String id, List<Material> materials) {
        this.id = id;
        this.materials = materials;
        this.entities = new ArrayList<>();
    }

    public static Model createModel(String id, List<Material> materials) {
        return new Model(id, materials);
    }

    public Model addEntity(Entity entity) {
        entities.add(entity);
        return this;
    }

    public void cleanup() {
        materials.forEach(Material::cleanup);
    }
}
