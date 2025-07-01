package me.hannsi.lfjg.render.system.model;

import lombok.Getter;
import me.hannsi.lfjg.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.utils.reflection.location.Location;

import static org.lwjgl.assimp.Assimp.*;

@Getter
public class ModelLoader {
    private final String modelId;
    private Location modelLocation;
    private TextureCache textureCache;
    private int flags;

    ModelLoader(String modelId) {
        this.modelId = modelId;

        this.modelLocation = null;
        this.textureCache = null;
        this.flags = aiProcess_GenSmoothNormals | aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_CalcTangentSpace | aiProcess_LimitBoneWeights | aiProcess_PreTransformVertices;
    }

    public static ModelLoader createModelLoader(String modelId) {
        return new ModelLoader(modelId);
    }

    public ModelLoader modelLocation(Location modelLocation) {
        this.modelLocation = modelLocation;
        return this;
    }

    public ModelLoader textureCache(TextureCache textureCache) {
        this.textureCache = textureCache;
        return this;
    }

    public ModelLoader flags(int flags) {
        this.flags = flags;
        return this;
    }

    public Model loadModel() {
        return null;
    }
}
