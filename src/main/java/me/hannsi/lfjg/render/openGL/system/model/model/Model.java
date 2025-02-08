package me.hannsi.lfjg.render.openGL.system.model.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a model in the OpenGL rendering system.
 */
public class Model {
    private final String id;
    private final List<Entity> entitiesList;
    private final List<Material> materialList;

    /**
     * Constructs a new Model with the specified id and material list.
     *
     * @param id the unique identifier of the model
     * @param materialList the list of materials associated with the model
     */
    public Model(String id, List<Material> materialList) {
        this.id = id;
        entitiesList = new ArrayList<>();
        this.materialList = materialList;
    }

    /**
     * Cleans up the model by cleaning up all materials in the material list.
     */
    public void cleanup() {
        materialList.forEach(Material::cleanup);
    }

    /**
     * Gets the list of entities associated with the model.
     *
     * @return the list of entities associated with the model
     */
    public List<Entity> getEntitiesList() {
        return entitiesList;
    }

    /**
     * Gets the unique identifier of the model.
     *
     * @return the unique identifier of the model
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the list of materials associated with the model.
     *
     * @return the list of materials associated with the model
     */
    public List<Material> getMaterialList() {
        return materialList;
    }
}