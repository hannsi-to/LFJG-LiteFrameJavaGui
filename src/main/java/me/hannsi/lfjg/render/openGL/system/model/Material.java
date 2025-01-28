package me.hannsi.lfjg.render.openGL.system.model;

import me.hannsi.lfjg.render.openGL.system.rendering.Mesh;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Material {
    public static final Vector4f DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);

    private Vector4f diffuseColor;
    private List<Mesh> meshList;
    private FileLocation texturePath;

    public Material() {
        diffuseColor = DEFAULT_COLOR;
        meshList = new ArrayList<>();
    }

    public void cleanup() {
        meshList.forEach(Mesh::cleanup);
    }

    public List<Mesh> getMeshList() {
        return meshList;
    }

    public void setMeshList(List<Mesh> meshList) {
        this.meshList = meshList;
    }

    public FileLocation getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(FileLocation texturePath) {
        this.texturePath = texturePath;
    }

    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }
}
