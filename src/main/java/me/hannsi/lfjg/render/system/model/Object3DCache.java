package me.hannsi.lfjg.render.system.model;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.render.system.model.lights.Lights;
import me.hannsi.lfjg.render.system.model.model.Entity;
import me.hannsi.lfjg.render.system.model.model.Model;
import me.hannsi.lfjg.render.system.model.texture.TextureModelCache;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.Map;

/**
 * Caches 3D objects in the OpenGL rendering system.
 */
public class Object3DCache {
    private Map<String, Model> modelMap;
    private Projection projection;
    private Lights lights;
    private TextureModelCache textureModelCache;

    /**
     * Constructs a new Object3DCache with the specified resolution.
     *
     * @param resolution the resolution of the cache
     */
    public Object3DCache(Vector2f resolution) {
        modelMap = new HashMap<>();
        projection = new Projection(ProjectionType.PERSPECTIVE_PROJECTION, Projection.DEFAULT_FOV, (int) resolution.x(), (int) resolution.y(), Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
        textureModelCache = new TextureModelCache();
    }

    /**
     * Creates a cache for the specified model and entity.
     *
     * @param model  the model to cache
     * @param entity the entity to cache
     */
    public void createCache(Model model, Entity entity) {
        model.getEntitiesList().add(entity);
        modelMap.put(model.getId(), model);

        new LogGenerator("Object3DCache Debug Message", "Source: Object3DCache", "Type: Cache Creation", "ID: " + model.getId(), "Severity: Info", "Message: Create 3D Object cache").logging(DebugLevel.DEBUG);
    }

    /**
     * Cleans up the cache by cleaning up all models in the model map.
     */
    public void cleanup() {
        lights.cleanup();
        textureModelCache.cleanup();
        modelMap.values().forEach(Model::cleanup);

        new LogGenerator("Object3DCache", "Source: Object3DCache", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: Object3DCache cleanup is complete.").logging(DebugLevel.DEBUG);
    }

    /**
     * Gets the model map.
     *
     * @return the model map
     */
    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    /**
     * Sets the model map.
     *
     * @param modelMap the new model map
     */
    public void setModelMap(Map<String, Model> modelMap) {
        this.modelMap = modelMap;
    }

    /**
     * Gets the projection.
     *
     * @return the projection
     */
    public Projection getProjection() {
        return projection;
    }

    /**
     * Sets the projection.
     *
     * @param projection the new projection
     */
    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    /**
     * Gets the texture model cache.
     *
     * @return the texture model cache
     */
    public TextureModelCache getTextureModelCache() {
        return textureModelCache;
    }

    /**
     * Sets the texture model cache.
     *
     * @param textureModelCache the new texture model cache
     */
    public void setTextureModelCache(TextureModelCache textureModelCache) {
        this.textureModelCache = textureModelCache;
    }

    /**
     * Resizes the projection with the specified width and height.
     *
     * @param width  the new width
     * @param height the new height
     */
    public void resize(int width, int height) {
        projection.updateProjMatrix(Projection.DEFAULT_FOV, width, height, Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
    }

    /**
     * Gets the scene lights.
     *
     * @return the scene lights
     */
    public Lights getSceneLights() {
        return lights;
    }

    /**
     * Sets the scene lights.
     *
     * @param lights the new scene lights
     */
    public void setSceneLights(Lights lights) {
        this.lights = lights;
    }
}