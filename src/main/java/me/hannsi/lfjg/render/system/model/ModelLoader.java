package me.hannsi.lfjg.render.system.model;

import lombok.Getter;
import me.hannsi.lfjg.render.debug.exceptions.model.ModelLoaderException;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.type.types.MaterialType;
import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private static float[] processTextureUV(AIMesh aiMesh) {
        AIVector3D.Buffer buffer = aiMesh.mTextureCoords(0);
        if (buffer == null) {
            return new float[]{};
        }

        float[] data = new float[buffer.remaining() * 2];
        int pos = 0;
        while (buffer.remaining() > 0) {
            AIVector3D uv = buffer.get();
            data[pos++] = uv.x();
            data[pos++] = 1 - uv.y();
        }

        return data;
    }

    private static float[] processVertices(AIMesh aiMesh) {
        AIVector3D.Buffer buffer = aiMesh.mVertices();
        float[] data = new float[buffer.remaining() * 3];
        int pos = 0;
        while (buffer.remaining() > 0) {
            AIVector3D uv = buffer.get();
            data[pos++] = uv.x();
            data[pos++] = uv.y();
            data[pos++] = uv.z();
        }
        return data;
    }

    private static int[] processIndices(AIMesh aiMesh) {
        List<Integer> indices = new ArrayList<>();
        AIFace.Buffer aiFaces = aiMesh.mFaces();

        int numFaces = aiMesh.mNumFaces();
        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer buffer = aiFace.mIndices();
            while (buffer.remaining() > 0) {
                indices.add(buffer.get());
            }
        }

        return indices.stream().mapToInt(Integer::intValue).toArray();
    }

    private static Mesh processMesh(AIMesh aiMesh) {
        float[] vertices = processVertices(aiMesh);
        float[] textCoords = processTextureUV(aiMesh);
        int[] indices = processIndices(aiMesh);

        if (textCoords.length == 0) {
            int numElements = (vertices.length / 3) * 2;
            textCoords = new float[numElements];
        }

        return Mesh.initMesh().createBufferObject3D(vertices, indices, null, textCoords, null);
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
        File file = modelLocation.getFile();
        if (!file.exists()) {
            throw new ModelLoaderException("Model path dose not exist: " + modelLocation.path());
        }

        String modelDir = file.getParent();
        AIScene aiScene = aiImportFile(modelLocation.path(), flags);
        if (aiScene == null) {
            throw new ModelLoaderException("Error loading model: " + modelLocation.path());
        }

        int numMaterials = aiScene.mNumMaterials();
        List<Material> materialList = new ArrayList<>();
        for (int i = 0; i < numMaterials; i++) {
            AIMaterial aiMaterial = AIMaterial.create(Objects.requireNonNull(aiScene.mMaterials()).get(i));
            materialList.add(processMaterial(aiMaterial, modelDir, textureCache));
        }

        int numMeshes = aiScene.mNumMeshes();
        PointerBuffer aiMeshes = aiScene.mMeshes();
        Material defaultMaterial = Material.createMaterial();
        for (int i = 0; i < numMeshes; i++) {
            assert aiMeshes != null;
            AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
            Mesh mesh = processMesh(aiMesh);
            int materialIdx = aiMesh.mMaterialIndex();
            Material material;
            if (materialIdx >= 0 && materialIdx < materialList.size()) {
                material = materialList.get(materialIdx);
            } else {
                material = defaultMaterial;
            }
            material.getMeshes().add(mesh);
        }

        if (!defaultMaterial.getMeshes().isEmpty()) {
            materialList.add(defaultMaterial);
        }

        return Model.createModel(modelId, materialList);
    }

    private Material processMaterial(AIMaterial aiMaterial, String modelDir, TextureCache textureCache) {
        Material material = Material.createMaterial();
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            AIColor4D color4D = AIColor4D.create();
            int result = aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_DIFFUSE, aiTextureType_NONE, 0, color4D);
            if (result == aiReturn_SUCCESS) {
                material.setMaterialType(MaterialType.COLOR);
                material.setDiffuseColor(new Vector4f(color4D.r(), color4D.g(), color4D.b(), color4D.a()));
            }

            AIString aiTexturePath = AIString.calloc(memoryStack);
            aiGetMaterialTexture(aiMaterial, aiTextureType_DIFFUSE, 0, aiTexturePath, (IntBuffer) null, null, null, null, null, null);
            String texturePath = aiTexturePath.dataString();
            if (!texturePath.isEmpty()) {
                material.setMaterialType(MaterialType.TEXTURE);
                material.setTextureLocation(Location.fromFile(modelDir + File.separator + new File(texturePath).getName()));
                textureCache.createCache(material.getTextureLocation());
                material.setDiffuseColor(Material.DEFAULT_COLOR);
            }

            return material;
        }
    }
}
