package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

public enum ShaderType implements IEnumTypeBase {
    VERTEX_SHADER(GL_VERTEX_SHADER, "VertexShader"),
    FRAGMENT_SHADER(GL_FRAGMENT_SHADER, "FragmentShader"),
    GEOMETRY_SHADER(GL_GEOMETRY_SHADER, "GeometryShader"),
    COMPUTE_SHADER(GL_COMPUTE_SHADER, "ComputeShader");

    final int id;
    final String name;

    ShaderType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
