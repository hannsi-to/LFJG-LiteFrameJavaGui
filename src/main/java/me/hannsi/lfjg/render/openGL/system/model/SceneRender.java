package me.hannsi.lfjg.render.openGL.system.model;

import me.hannsi.lfjg.render.openGL.renderers.model.Entity;
import me.hannsi.lfjg.render.openGL.renderers.model.Model;
import me.hannsi.lfjg.render.openGL.system.rendering.Mesh;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;

import java.util.Collection;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class SceneRender {
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

        shaderProgram.setUniformMatrix4fv("projectionMatrix", scene.getProjection().getProjMatrix());
        shaderProgram.setUniformMatrix4fv("viewMatrix", scene.getCamera().getViewMatrix());

        shaderProgram.setUniform1i("txtSampler", 0);

        Collection<Model> models = scene.getModelMap().values();
        TextureModelCache textureCache = scene.getTextureModelCache();
        for (Model model : models) {
            List<Entity> entities = model.getEntitiesList();

            for (Material material : model.getMaterialList()) {
                shaderProgram.setUniform4f("material.diffuse", material.getDiffuseColor());
                TextureModel texture = textureCache.getTexture(material.getTexturePath());
                glActiveTexture(GL_TEXTURE0);
                texture.bind();

                for (Mesh mesh : material.getMeshList()) {
                    glBindVertexArray(mesh.getVaoId());
                    for (Entity entity : entities) {
                        shaderProgram.setUniformMatrix4fv("modelMatrix", entity.getModelMatrix());
                        glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
                    }
                }

                texture.unbind();
            }
        }

        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }
}
