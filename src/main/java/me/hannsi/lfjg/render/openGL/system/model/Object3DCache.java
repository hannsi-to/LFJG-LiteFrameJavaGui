package me.hannsi.lfjg.render.openGL.system.model;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.system.model.lights.Lights;
import me.hannsi.lfjg.render.openGL.system.model.model.Entity;
import me.hannsi.lfjg.render.openGL.system.model.model.Model;
import me.hannsi.lfjg.render.openGL.system.model.texture.TextureModelCache;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class Object3DCache {
    private Map<String, Model> modelMap;
    private Projection projection;
    private Lights lights;
    private TextureModelCache textureModelCache;

    public Object3DCache(Vector2f resolution) {
        modelMap = new HashMap<>();
        projection = new Projection(ProjectionType.PerspectiveProjection, Projection.DEFAULT_FOV, (int) resolution.x(), (int) resolution.y(), Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
        textureModelCache = new TextureModelCache();
    }

    public void createCache(Model model, Entity entity) {
        model.getEntitiesList().add(entity);
        modelMap.put(model.getId(), model);

        LogGenerator logGenerator = new LogGenerator("Object3DCache Debug Message", "Source: Object3DCache", "Type: Cache Creation", "ID: " + model.getId(), "Severity: Info", "Message: Create 3D Object cache");

        DebugLog.debug(getClass(), logGenerator.createLog());
    }

    public void cleanup() {
        modelMap.values().forEach(Model::cleanup);
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    public void setModelMap(Map<String, Model> modelMap) {
        this.modelMap = modelMap;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public TextureModelCache getTextureModelCache() {
        return textureModelCache;
    }

    public void setTextureModelCache(TextureModelCache textureModelCache) {
        this.textureModelCache = textureModelCache;
    }

    public void resize(int width, int height) {
        projection.updateProjMatrix(Projection.DEFAULT_FOV, width, height, Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
    }

    public Lights getSceneLights() {
        return lights;
    }

    public void setSceneLights(Lights lights) {
        this.lights = lights;
    }
}
