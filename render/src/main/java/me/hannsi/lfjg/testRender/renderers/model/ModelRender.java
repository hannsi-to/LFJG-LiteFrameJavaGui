package me.hannsi.lfjg.testRender.renderers.model;

import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.graphics.image.TextureLoader;
import me.hannsi.lfjg.testRender.debug.exceptions.model.ModelException;
import me.hannsi.lfjg.testRender.system.mesh.Mesh;
import me.hannsi.lfjg.testRender.system.model.Entity;
import me.hannsi.lfjg.testRender.system.model.Material;
import me.hannsi.lfjg.testRender.system.model.Model;
import me.hannsi.lfjg.testRender.system.model.ModelCache;
import me.hannsi.lfjg.testRender.system.rendering.VAORendering;
import me.hannsi.lfjg.testRender.system.shader.FragmentShaderType;
import me.hannsi.lfjg.testRender.system.shader.UploadUniformType;

import java.util.Collection;
import java.util.List;

import static me.hannsi.lfjg.core.Core.projection3D;
import static me.hannsi.lfjg.testRender.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

public class ModelRender {
    private final VAORendering vaoRendering;

    private ModelCache modelCache;
    private TextureCache textureCache;

    ModelRender() {
        vaoRendering = new VAORendering();
    }

    public static ModelRender createModelRender() {
        return new ModelRender();
    }

    public ModelRender modelCache(ModelCache modelCache) {
        this.modelCache = modelCache;
        return this;
    }

    public ModelRender textureCache(TextureCache textureCache) {
        this.textureCache = textureCache;
        return this;
    }

    public void render() {
        glStateCache.enable(GL_DEPTH_TEST);

        shaderProgram.bind();

        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.MODEL.getId());
        shaderProgram.setUniform("textureSampler", UploadUniformType.ONCE, 0);
        shaderProgram.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, projection3D.getMatrix4f());
        shaderProgram.setUniform("viewMatrix", UploadUniformType.PER_FRAME, mainCamera.getViewMatrix());

        Collection<Model> models = modelCache.getModels().values();
        for (Model model : models) {
            List<Entity> entities = model.getEntities();

            for (Material material : model.getMaterials()) {
                shaderProgram.setUniform("modelMaterialType", UploadUniformType.PER_FRAME, material.getMaterialType().getId());

                switch (material.getMaterialType()) {
                    case NO_MATERIAL:
                    case COLOR:
                        break;
                    case TEXTURE:
                        if (textureCache == null) {
                            throw new ModelException("To use a texture material, TextureCache must be set.");
                        }

                        glStateCache.activeTexture(GL_TEXTURE0);
                        glStateCache.enable(GL_TEXTURE_2D);

                        TextureLoader textureLoader = textureCache.getTexture(material.getTextureLocation().path());
                        if (textureLoader == null) {
                            throw new ModelException("Not found texture: " + material.getTextureLocation().path());
                        }
                        textureLoader.bind();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + material.getMaterialType());
                }

                for (Mesh mesh : material.getMeshes()) {
                    for (Entity entity : entities) {
                        shaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, entity.getModelMatrix());
//                        vaoRendering.draw(mesh, GL_TRIANGLES);
                    }
                }
            }
        }
    }

    public Model getModel(String id) {
        return getModelCache().getModels().get(id);
    }

    public VAORendering getVaoRendering() {
        return vaoRendering;
    }

    public ModelCache getModelCache() {
        return modelCache;
    }

    public void setModelCache(ModelCache modelCache) {
        this.modelCache = modelCache;
    }

    public TextureCache getTextureCache() {
        return textureCache;
    }

    public void setTextureCache(TextureCache textureCache) {
        this.textureCache = textureCache;
    }
}
