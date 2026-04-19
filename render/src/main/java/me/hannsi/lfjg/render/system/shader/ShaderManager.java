package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.testRender.debug.exceptions.shader.ShaderManagerException;

import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;

public class ShaderManager {
    private final Map<String, ShaderProgram> shaders;

    public ShaderManager() {
        this.shaders = new HashMap<>();
    }

    public ShaderManager buildShader(String name, Location vertexShader, Location fragmentShader) {
        if (shaders.get(name) != null) {
            throw new ShaderManagerException("Shader already exists: " + name);
        }

        ShaderProgram shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(vertexShader);
        shaderProgram.createFragmentShader(fragmentShader);
        shaderProgram.link();

        shaders.put(name, shaderProgram);

        return this;
    }

    public void bind(String name) {
        ShaderProgram shader = getShaderProgram(name);

        shader.bind();
    }

    public void unbind() {
        glStateCache.useProgram(0);
    }

    public void remove(String name) {
        ShaderProgram shader = shaders.remove(name);
        if (shader != null) {
            shader.cleanup();
        }
    }

    public ShaderProgram getShaderProgram(String name) {
        ShaderProgram shader = shaders.get(name);
        if (shader == null) {
            throw new ShaderManagerException("Shader not found: " + name);
        }
        return shader;
    }
}
