package me.hannsi.lfjg.render.openGL.system.model;

import me.hannsi.lfjg.render.openGL.renderers.model.Entity;
import me.hannsi.lfjg.render.openGL.renderers.model.Model;
import me.hannsi.lfjg.render.openGL.system.model.lights.*;
import me.hannsi.lfjg.render.openGL.system.rendering.Mesh;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Collection;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class SceneRender {
    private static final int MAX_POINT_LIGHTS = 5;
    private static final int MAX_SPOT_LIGHTS = 5;

    private ShaderProgram shaderProgram;

    public SceneRender() {
        this.shaderProgram = new ShaderProgram();
        this.shaderProgram.createVertexShader(new ResourcesLocation("shader/scene/model/VertexShader.vsh"));
        this.shaderProgram.createFragmentShader(new ResourcesLocation("shader/scene/model/FragmentShader.fsh"));
        this.shaderProgram.link();
    }

    public void cleanup() {
        shaderProgram.cleanup();
    }

    public void render(Scene scene) {
        shaderProgram.bind();

        shaderProgram.setUniform("projectionMatrix", scene.getProjection().getProjMatrix());
        shaderProgram.setUniform("viewMatrix", scene.getCamera().getViewMatrix());

        shaderProgram.setUniform1i("txtSampler", 0);

        updateLights(scene);

        Collection<Model> models = scene.getModelMap().values();
        TextureModelCache textureCache = scene.getTextureModelCache();
        for (Model model : models) {
            List<Entity> entities = model.getEntitiesList();

            for (Material material : model.getMaterialList()) {
                shaderProgram.setUniform("material.ambient", material.getAmbientColor());
                shaderProgram.setUniform("material.diffuse", material.getDiffuseColor());
                shaderProgram.setUniform("material.specular", material.getSpecularColor());
                shaderProgram.setUniform("material.reflectance", material.getReflectance());

                TextureModel texture = textureCache.getTexture(material.getTexturePath());
                glActiveTexture(GL_TEXTURE0);
                texture.bind();

                for (Mesh mesh : material.getMeshList()) {
                    glBindVertexArray(mesh.getVaoId());
                    for (Entity entity : entities) {
                        shaderProgram.setUniform("modelMatrix", entity.getModelMatrix());
                        glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
                    }
                }

                texture.unbind();
            }
        }

        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    private void updateLights(Scene scene) {
        Matrix4f viewMatrix = scene.getCamera().getViewMatrix();

        SceneLights sceneLights = scene.getSceneLights();
        AmbientLight ambientLight = sceneLights.getAmbientLight();
        shaderProgram.setUniform("ambientLight.factor", ambientLight.getIntensity());
        shaderProgram.setUniform("ambientLight.color", ambientLight.getColor());

        DirLight dirLight = sceneLights.getDirLight();
        Vector4f auxDir = new Vector4f(dirLight.getDirection(), 0);
        auxDir.mul(viewMatrix);
        Vector3f dir = new Vector3f(auxDir.x, auxDir.y, auxDir.z);
        shaderProgram.setUniform("dirLight.color", dirLight.getColor());
        shaderProgram.setUniform("dirLight.direction", dir);
        shaderProgram.setUniform("dirLight.intensity", dirLight.getIntensity());

        List<PointLight> pointLights = sceneLights.getPointLights();
        int numPointLights = pointLights.size();
        PointLight pointLight;
        for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
            if (i < numPointLights) {
                pointLight = pointLights.get(i);
            } else {
                pointLight = null;
            }

            String name = "pointLights[" + i + "]";
            updatePointLight(pointLight, name, viewMatrix);
        }

        List<SpotLight> spotLights = sceneLights.getSpotLights();
        int numSpotLights = spotLights.size();
        SpotLight spotLight;
        for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
            if (i < numSpotLights) {
                spotLight = spotLights.get(i);
            } else {
                spotLight = null;
            }

            String name = "spotLights[" + i + "]";
            updateSpotLight(spotLight, name, viewMatrix);
        }
    }

    private void updatePointLight(PointLight pointLight, String prefix, Matrix4f viewMatrix) {
        Vector4f aux = new Vector4f();
        Vector3f lightPosition = new Vector3f();
        Vector3f color = new Vector3f();

        float intensity = 0.0f;
        float constant = 0.0f;
        float linear = 0.0f;
        float exponent = 0.0f;
        if (pointLight != null) {
            aux.set(pointLight.getPosition(), 1);
            aux.mul(viewMatrix);
            lightPosition.set(aux.x, aux.y, aux.z);
            color.set(pointLight.getColor());
            intensity = pointLight.getIntensity();
            PointLight.Attenuation attenuation = pointLight.getAttenuation();
            constant = attenuation.getConstant();
            linear = attenuation.getLinear();
            exponent = attenuation.getExponent();
        }

        shaderProgram.setUniform(prefix + ".position", lightPosition);
        shaderProgram.setUniform(prefix + ".color", color);
        shaderProgram.setUniform(prefix + ".intensity", intensity);
        shaderProgram.setUniform(prefix + ".att.constant", constant);
        shaderProgram.setUniform(prefix + ".att.linear", linear);
        shaderProgram.setUniform(prefix + ".att.exponent", exponent);
    }

    private void updateSpotLight(SpotLight spotLight, String prefix, Matrix4f viewMatrix) {
        PointLight pointLight = null;
        Vector3f coneDirection = new Vector3f();
        float cutoff = 0.0f;
        if (spotLight != null) {
            coneDirection = spotLight.getConeDirection();
            cutoff = spotLight.getCutOff();
            pointLight = spotLight.getPointLight();
        }

        shaderProgram.setUniform(prefix + ".conedir", coneDirection);
        shaderProgram.setUniform(prefix + ".cutoff", cutoff);

        updatePointLight(pointLight, prefix + ".pl", viewMatrix);
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }
}
