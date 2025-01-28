package me.hannsi.lfjg.render.openGL.system.model;

import me.hannsi.lfjg.render.openGL.system.rendering.Mesh;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;

import java.util.ArrayList;
import java.util.List;

public class Material {
    private List<Mesh> meshList;
    private ResourcesLocation texturePath;

    public Material() {
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

    public ResourcesLocation getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(ResourcesLocation texturePath) {
        this.texturePath = texturePath;
    }
}
