package me.hannsi.lfjg.render.system.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ModelCache {
    private final Map<String, Model> models;

    ModelCache() {
        models = new HashMap<>();
    }

    public static ModelCache createModelCache() {
        return new ModelCache();
    }

    public ModelCache createModelCache(Model model) {
        models.put(model.getId(), model);
        return this;
    }

    public void cleanup() {
        models.values().forEach(Model::cleanup);
    }
}
