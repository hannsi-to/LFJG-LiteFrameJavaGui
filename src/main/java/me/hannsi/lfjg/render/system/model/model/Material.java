package me.hannsi.lfjg.render.system.model.model;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.render.system.MeshBuilder;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a material in the OpenGL rendering system.
 */
public class Material {
    public static final Vector4f DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
    private final List<MeshBuilder> meshBuilders;
    private Vector4f ambientColor;
    private Vector4f diffuseColor;
    private float reflectance;
    private Vector4f specularColor;
    private FileLocation texturePath;

    /**
     * Constructs a new Material with default colors and an empty mesh list.
     */
    public Material() {
        diffuseColor = DEFAULT_COLOR;
        ambientColor = DEFAULT_COLOR;
        specularColor = DEFAULT_COLOR;

        meshBuilders = new ArrayList<>();
    }

    /**
     * Cleans up the material by cleaning up all meshes in the mesh list.
     */
    public void cleanup() {
        ambientColor = null;
        diffuseColor = null;
        meshBuilders.forEach(MeshBuilder::cleanup);
        specularColor = null;
        texturePath.cleanup();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                hashCode(),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    /**
     * Gets the ambient color of the material.
     *
     * @return the ambient color of the material
     */
    public Vector4f getAmbientColor() {
        return ambientColor;
    }

    /**
     * Sets the ambient color of the material.
     *
     * @param ambientColor the new ambient color of the material
     */
    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    /**
     * Gets the diffuse color of the material.
     *
     * @return the diffuse color of the material
     */
    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    /**
     * Sets the diffuse color of the material.
     *
     * @param diffuseColor the new diffuse color of the material
     */
    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    /**
     * Gets the list of meshes associated with the material.
     *
     * @return the list of meshes associated with the material
     */
    public List<MeshBuilder> getMeshBuilders() {
        return meshBuilders;
    }

    /**
     * Gets the reflectance of the material.
     *
     * @return the reflectance of the material
     */
    public float getReflectance() {
        return reflectance;
    }

    /**
     * Sets the reflectance of the material.
     *
     * @param reflectance the new reflectance of the material
     */
    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    /**
     * Gets the specular color of the material.
     *
     * @return the specular color of the material
     */
    public Vector4f getSpecularColor() {
        return specularColor;
    }

    /**
     * Sets the specular color of the material.
     *
     * @param specularColor the new specular color of the material
     */
    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    /**
     * Gets the texture path of the material.
     *
     * @return the texture path of the material
     */
    public FileLocation getTexturePath() {
        return texturePath;
    }

    /**
     * Sets the texture path of the material.
     *
     * @param texturePath the new texture path of the material
     */
    public void setTexturePath(FileLocation texturePath) {
        this.texturePath = texturePath;
    }
}