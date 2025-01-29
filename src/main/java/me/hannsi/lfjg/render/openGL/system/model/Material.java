package me.hannsi.lfjg.render.openGL.system.model;

import me.hannsi.lfjg.render.openGL.system.rendering.Mesh;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Material {
    public static final Vector4f DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);

    private Vector4f ambientColor;
    private Vector4f diffuseColor;
    private final List<Mesh> meshList;
    private float reflectance;
    private Vector4f specularColor;
    private FileLocation texturePath;

    public Material() {
        diffuseColor = DEFAULT_COLOR;
        ambientColor = DEFAULT_COLOR;
        specularColor = DEFAULT_COLOR;

        meshList = new ArrayList<>();
    }

    public void cleanup() {
        meshList.forEach(Mesh::cleanup);
    }

    public Vector4f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public List<Mesh> getMeshList() {
        return meshList;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public Vector4f getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    public FileLocation getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(FileLocation texturePath) {
        this.texturePath = texturePath;
    }
}
