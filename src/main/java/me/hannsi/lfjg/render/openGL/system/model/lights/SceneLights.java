package me.hannsi.lfjg.render.openGL.system.model.lights;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SceneLights {
    private AmbientLight ambientLight;
    private DirLight dirLight;
    private List<PointLight> pointLights;
    private List<SpotLight> spotLights;

    public SceneLights() {
        ambientLight = new AmbientLight();
        pointLights = new ArrayList<>();
        spotLights = new ArrayList<>();
        dirLight = new DirLight(new Vector3f(1, 1, 1), new Vector3f(0, 1, 0), 1.0f);
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }

    public DirLight getDirLight() {
        return dirLight;
    }

    public void setDirLight(DirLight dirLight) {
        this.dirLight = dirLight;
    }

    public List<PointLight> getPointLights() {
        return pointLights;
    }

    public void setPointLights(List<PointLight> pointLights) {
        this.pointLights = pointLights;
    }

    public List<SpotLight> getSpotLights() {
        return spotLights;
    }

    public void setSpotLights(List<SpotLight> spotLights) {
        this.spotLights = spotLights;
    }
}
