package me.hannsi.lfjg.render.system.model;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.debug.exceptions.model.MaterialException;
import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Material {
    public static final Vector4f DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
    private final List<Mesh> meshes;
    private Vector4f diffuseColor;
    private MaterialType materialType;
    private Location textureLocation;

    Material() {
        diffuseColor = DEFAULT_COLOR;
        meshes = new ArrayList<>();
    }

    public static Material createMaterial() {
        return new Material();
    }

    public Material materialType(MaterialType materialType) {
        this.materialType = materialType;
        return this;
    }

    public Material texturePath(Location textureLocation) {
        this.textureLocation = textureLocation;
        return this;
    }

    public Material createMesh(Mesh mesh) {
        MaterialException materialException = checkException(materialType, mesh);
        if (materialException != null) {
            throw materialException;
        }

        String performanceMessage = checkPerformance(mesh);
        if (!performanceMessage.isEmpty()) {
            new LogGenerator(
                    "Material Performance Message",
                    "Source: " + getClass().getSimpleName(),
                    "ID: " + mesh.getIds(),
                    "Severity: Low",
                    "Message: " + performanceMessage
            ).logging(DebugLevel.WARNING);
        }

        meshes.add(mesh);
        return this;
    }

    private String checkPerformance(Mesh mesh) {
        String message = "";

        boolean color = mesh.getVboIds().get(BufferObjectType.COLORS_BUFFER) != null;
        boolean texture = mesh.getVboIds().get(BufferObjectType.TEXTURE_BUFFER) != null;
        switch (materialType) {
            case NO_MATERIAL -> {
                if (color || texture) {
                    message = "An extra VertexBufferObject is generated for the set MaterialType. CurrentMaterialType: " + materialType.getName() + " | Created Color: " + color + " | Created Texture: " + texture;
                }
            }
            case COLOR -> {
                if (texture) {
                    message = "An extra VertexBufferObject is generated for the set MaterialType. CurrentMaterialType: " + materialType.getName() + " | Created Color: " + color + " | Created Texture: " + true;
                }
            }
            case TEXTURE -> {
                if (color) {
                    message = "An extra VertexBufferObject is generated for the set MaterialType. CurrentMaterialType: " + materialType.getName() + " | Created Color: " + true + " | Created Texture: " + texture;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + materialType);
        }

        return message;
    }

    private MaterialException checkException(MaterialType needMaterial, Mesh mesh) {
        MaterialException materialException = null;

        if (materialType == null) {
            materialException = new MaterialException("MaterialType is not set.");
        }
        if (materialType != needMaterial) {
            materialException = new MaterialException("The MaterialType you set does not match the value you entered. Need: " + needMaterial.getName() + " | Current: " + materialType.getName());
        }

        BufferObjectType bufferObjectType;
        switch (materialType) {
            case NO_MATERIAL -> bufferObjectType = BufferObjectType.POSITIONS_BUFFER;
            case COLOR -> bufferObjectType = BufferObjectType.COLORS_BUFFER;
            case TEXTURE -> bufferObjectType = BufferObjectType.TEXTURE_BUFFER;
            default -> throw new IllegalStateException("Unexpected value: " + materialType);
        }

        if (mesh.getVboIds().get(bufferObjectType) == null) {
            materialException = new MaterialException("VertexBufferObject of Mesh object is not set properly. Need: " + bufferObjectType.getName());
        }

        return materialException;
    }

    public void cleanup() {
        meshes.forEach(Mesh::cleanup);
    }

    public List<Mesh> getMeshes() {
        return meshes;
    }

    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public Location getTextureLocation() {
        return textureLocation;
    }

    public void setTextureLocation(Location textureLocation) {
        this.textureLocation = textureLocation;
    }
}
