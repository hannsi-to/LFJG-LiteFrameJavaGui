package me.hannsi.lfjg.render.system.model.model;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.render.system.Mesh;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a material in the OpenGL rendering system.
 */
@Getter
public class Material {
    public static final Vector4f DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
    /**
     * -- GETTER --
     * Gets the list of meshes associated with the material.
     *
     * @return the list of meshes associated with the material
     */
    private final List<Mesh> meshes;
    /**
     * -- SETTER --
     * Sets the ambient color of the material.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the ambient color of the material.
     *
     * @param ambientColor the new ambient color of the material
     * @return the ambient color of the material
     */
    @Setter
    private Vector4f ambientColor;
    /**
     * -- SETTER --
     * Sets the diffuse color of the material.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the diffuse color of the material.
     *
     * @param diffuseColor the new diffuse color of the material
     * @return the diffuse color of the material
     */
    @Setter
    private Vector4f diffuseColor;
    /**
     * -- SETTER --
     * Sets the reflectance of the material.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the reflectance of the material.
     *
     * @param reflectance the new reflectance of the material
     * @return the reflectance of the material
     */
    @Setter
    private float reflectance;
    /**
     * -- SETTER --
     * Sets the specular color of the material.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the specular color of the material.
     *
     * @param specularColor the new specular color of the material
     * @return the specular color of the material
     */
    @Setter
    private Vector4f specularColor;
    /**
     * -- SETTER --
     * Sets the texture path of the material.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the texture path of the material.
     *
     * @param texturePath the new texture path of the material
     * @return the texture path of the material
     */
    @Setter
    private FileLocation texturePath;

    /**
     * Constructs a new Material with default colors and an empty mesh list.
     */
    public Material() {
        diffuseColor = DEFAULT_COLOR;
        ambientColor = DEFAULT_COLOR;
        specularColor = DEFAULT_COLOR;

        meshes = new ArrayList<>();
    }

    /**
     * Cleans up the material by cleaning up all meshes in the mesh list.
     */
    public void cleanup() {
        ambientColor = null;
        diffuseColor = null;
        meshes.forEach(Mesh::cleanup);
        specularColor = null;
        texturePath.cleanup();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                hashCode(),
                ""
        ).logging(DebugLevel.DEBUG);
    }

}