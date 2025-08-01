package me.hannsi.lfjg.render.renderers.model;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.graphics.image.TextureLoader;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.Camera;
import me.hannsi.lfjg.render.debug.exceptions.model.ModelException;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.model.Entity;
import me.hannsi.lfjg.render.system.model.Material;
import me.hannsi.lfjg.render.system.model.Model;
import me.hannsi.lfjg.render.system.model.ModelCache;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import java.util.Collection;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL13.*;

public class ModelRender {
    private final ShaderProgram shaderProgram;
    private final VAORendering vaoRendering;

    private ModelCache modelCache;
    private TextureCache textureCache;
    private Camera camera;

    ModelRender() {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Location.fromResource("shader/scene/model/VertexShader.vsh"));
        shaderProgram.createFragmentShader(Location.fromResource("shader/scene/model/FragmentShader.fsh"));
        shaderProgram.link();

        vaoRendering = new VAORendering();

        camera = new Camera();
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

    public ModelRender camera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public void render() {
        GLStateCache.enable(GL_DEPTH_TEST);

        shaderProgram.bind();

        shaderProgram.setUniform("textureSampler", UploadUniformType.ONCE, 0);
        shaderProgram.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, Core.projection3D.getProjMatrix());
        shaderProgram.setUniform("viewMatrix", UploadUniformType.PER_FRAME, camera.getViewMatrix());

        Collection<Model> models = modelCache.getModels().values();
        for (Model model : models) {
            List<Entity> entities = model.getEntities();

            for (Material material : model.getMaterials()) {
                shaderProgram.setUniform("materialType", UploadUniformType.PER_FRAME, material.getMaterialType().getId());

                switch (material.getMaterialType()) {
                    case NO_MATERIAL, COLOR -> {
                    }
                    case TEXTURE -> {
                        if (textureCache == null) {
                            throw new ModelException("To use a texture material, TextureCache must be set.");
                        }

                        glActiveTexture(GL_TEXTURE0);
                        GLStateCache.enable(GL_TEXTURE_2D);

                        TextureLoader textureLoader = textureCache.getTexture(material.getTextureLocation().path());
                        if (textureLoader == null) {
                            throw new ModelException("Not found texture: " + material.getTextureLocation().path());
                        }
                        textureLoader.bind();
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + material.getMaterialType());
                }

                for (Mesh mesh : material.getMeshes()) {
                    for (Entity entity : entities) {
                        shaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, entity.getModelMatrix());
                        vaoRendering.draw(mesh, GL_TRIANGLES);
                    }
                }
            }
        }

        shaderProgram.unbind();
    }

    public Model getModel(String id) {
        return getModelCache().getModels().get(id);
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
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

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
