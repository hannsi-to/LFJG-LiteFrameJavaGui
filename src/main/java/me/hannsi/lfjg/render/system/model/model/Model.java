package me.hannsi.lfjg.render.system.model.model;

import lombok.Getter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a model in the OpenGL rendering system.
 */
@Getter
public class Model {
    /**
     * -- GETTER --
     *  Gets the unique identifier of the model.
     *
     * @return the unique identifier of the model
     */
    private final String id;
    /**
     * -- GETTER --
     *  Gets the list of entities associated with the model.
     *
     * @return the list of entities associated with the model
     */
    private final List<Entity> entitiesList;
    /**
     * -- GETTER --
     *  Gets the list of materials associated with the model.
     *
     * @return the list of materials associated with the model
     */
    private final List<Material> materialList;

    /**
     * Constructs a new Model with the specified id and material list.
     *
     * @param id           the unique identifier of the model
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
        entitiesList.forEach(Entity::cleanup);
        materialList.forEach(Material::cleanup);
        entitiesList.clear();
        materialList.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                id,
                ""
        ).logging(DebugLevel.DEBUG);
    }

}