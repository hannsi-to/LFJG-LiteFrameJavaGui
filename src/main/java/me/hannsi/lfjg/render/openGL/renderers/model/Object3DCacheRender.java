package me.hannsi.lfjg.render.openGL.renderers.model;

import me.hannsi.lfjg.render.openGL.system.Mesh;
import me.hannsi.lfjg.render.openGL.system.model.Object3DCache;
import me.hannsi.lfjg.render.openGL.system.model.lights.*;
import me.hannsi.lfjg.render.openGL.system.model.model.Entity;
import me.hannsi.lfjg.render.openGL.system.model.model.Material;
import me.hannsi.lfjg.render.openGL.system.model.model.Model;
import me.hannsi.lfjg.render.openGL.system.model.texture.TextureModel;
import me.hannsi.lfjg.render.openGL.system.model.texture.TextureModelCache;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.graphics.GLUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.toolkit.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Collection;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

/**
 * Class responsible for rendering 3D objects from a cache in OpenGL.
 */
public class Object3DCacheRender {
    private static final int MAX_POINT_LIGHTS = 5;
    private static final int MAX_SPOT_LIGHTS = 5;

    private Object3DCache object3DCache;
    private ShaderProgram shaderProgram;
    private GLUtil glUtil;

    /**
     * Constructs a new Object3DCacheRender.
     */
    public Object3DCacheRender() {
        this.glUtil = new GLUtil();
        this.glUtil.addGLTarget(GL_DEPTH_TEST);
        this.glUtil.addGLTarget(GL_CULL_FACE);

        this.shaderProgram = new ShaderProgram();
        this.shaderProgram.createVertexShader(new ResourcesLocation("shader/scene/model/VertexShader.vsh"));
        this.shaderProgram.createFragmentShader(new ResourcesLocation("shader/scene/model/FragmentShader.fsh"));
        this.shaderProgram.link();
    }

    /**
     * Cleans up resources used by the renderer.
     */
    public void cleanup() {
        object3DCache.cleanup();
        shaderProgram.cleanup();
        glUtil.cleanup();
    }

    /**
     * Renders the 3D objects using the specified camera.
     *
     * @param camera the camera to use for rendering
     */
    public void render(Camera camera) {
        glUtil.enableTargets();
        glCullFace(GL_BACK);

        shaderProgram.bind();

        shaderProgram.setUniform("projectionMatrix", object3DCache.getProjection().getProjMatrix());
        shaderProgram.setUniform("viewMatrix", camera.getViewMatrix());

        shaderProgram.setUniform1i("txtSampler", 0);

        updateLights(object3DCache, camera);

        Collection<Model> models = object3DCache.getModelMap().values();
        TextureModelCache textureCache = object3DCache.getTextureModelCache();
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

        glUtil.disableTargets();
    }

    /**
     * Updates the lights in the shader program.
     *
     * @param object3DCache the cache containing the lights
     * @param camera        the camera to use for view matrix
     */
    private void updateLights(Object3DCache object3DCache, Camera camera) {
        Matrix4f viewMatrix = camera.getViewMatrix();

        Lights lights = object3DCache.getSceneLights();
        AmbientLight ambientLight = lights.getAmbientLight();
        shaderProgram.setUniform("ambientLight.factor", ambientLight.getIntensity());
        shaderProgram.setUniform("ambientLight.color", ambientLight.getColor());

        DirLight dirLight = lights.getDirLight();
        Vector4f auxDir = new Vector4f(dirLight.getDirection(), 0);
        auxDir.mul(viewMatrix);
        Vector3f dir = new Vector3f(auxDir.x, auxDir.y, auxDir.z);
        shaderProgram.setUniform("dirLight.color", dirLight.getColor());
        shaderProgram.setUniform("dirLight.direction", dir);
        shaderProgram.setUniform("dirLight.intensity", dirLight.getIntensity());

        List<PointLight> pointLights = lights.getPointLights();
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

        List<SpotLight> spotLights = lights.getSpotLights();
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

    /**
     * Updates a point light in the shader program.
     *
     * @param pointLight the point light to update
     * @param prefix     the prefix for the uniform names
     * @param viewMatrix the view matrix to transform the light position
     */
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

    /**
     * Updates a spot light in the shader program.
     *
     * @param spotLight  the spot light to update
     * @param prefix     the prefix for the uniform names
     * @param viewMatrix the view matrix to transform the light position
     */
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

    /**
     * Gets the shader program used by the renderer.
     *
     * @return the shader program
     */
    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    /**
     * Sets the shader program used by the renderer.
     *
     * @param shaderProgram the shader program to set
     */
    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    /**
     * Gets the 3D object cache used by the renderer.
     *
     * @return the 3D object cache
     */
    public Object3DCache getScene() {
        return object3DCache;
    }

    /**
     * Sets the 3D object cache used by the renderer.
     *
     * @param object3DCache the 3D object cache to set
     */
    public void setScene(Object3DCache object3DCache) {
        this.object3DCache = object3DCache;
    }

    /**
     * Gets the GL utility used by the renderer.
     *
     * @return the GL utility
     */
    public GLUtil getGlUtil() {
        return glUtil;
    }

    /**
     * Sets the GL utility used by the renderer.
     *
     * @param glUtil the GL utility to set
     */
    public void setGlUtil(GLUtil glUtil) {
        this.glUtil = glUtil;
    }
}