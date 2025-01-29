package me.hannsi.lfjg.render.openGL.system.model;

import me.hannsi.lfjg.render.openGL.renderers.model.Entity;
import me.hannsi.lfjg.render.openGL.renderers.model.Model;
import me.hannsi.lfjg.render.openGL.system.Camera;
import me.hannsi.lfjg.render.openGL.system.model.lights.SceneLights;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class Scene {
    private Camera camera;
    private Map<String, Model> modelMap;
    private Projection projection;
    private SceneLights sceneLights;
    private TextureModelCache textureModelCache;

    public Scene(Vector2f resolution) {
        modelMap = new HashMap<>();
        projection = new Projection(ProjectionType.PerspectiveProjection, Projection.DEFAULT_FOV, (int) resolution.x(), (int) resolution.y(), Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
        textureModelCache = new TextureModelCache();

        camera = new Camera();
    }

    public void addEntity(Entity entity) {
        String modelId = entity.getModelId();
        Model model = modelMap.get(modelId);
        if (model == null) {
            throw new RuntimeException("Could not find model [" + modelId + "]");
        }
        model.getEntitiesList().add(entity);
    }

    public void addModel(Model model) {
        modelMap.put(model.getId(), model);
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

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public SceneLights getSceneLights() {
        return sceneLights;
    }

    public void setSceneLights(SceneLights sceneLights) {
        this.sceneLights = sceneLights;
    }
}
