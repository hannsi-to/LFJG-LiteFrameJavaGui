package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;

public enum BufferObjectType implements IEnumTypeBase {
    VERTEX_BUFFER_OBJECT(GL_ARRAY_BUFFER, "VertexBufferObject", VERTEX_BUFFER_OBJECT_ALIGNMENT, Group.RENDER_DATA),
    ELEMENT_BUFFER_OBJECT(GL_ELEMENT_ARRAY_BUFFER, "ElementBufferObject", ELEMENT_BUFFER_OBJECT_ALIGNMENT, Group.RENDER_DATA),
    DRAW_INDIRECT_BUFFER_OBJECT(GL_DRAW_INDIRECT_BUFFER, "DrawIndirectBufferObject", DRAW_INDIRECT_BUFFER_OBJECT_ALIGNMENT, Group.RENDER_DATA),
    UNIFORM_BUFFER_OBJECT(GL_UNIFORM_BUFFER, "UniformBufferObject", UNIFORM_BUFFER_OBJECT_ALIGNMENT, Group.SHADER_RESOURCE),
    SHADER_STORAGE_BUFFER_OBJECT(GL_SHADER_STORAGE_BUFFER, "ShaderStorageBufferObject", SHADER_STORAGE_BUFFER_OBJECT_ALIGNMENT, Group.SHADER_RESOURCE);

    final int id;
    final String name;
    final int alignment;
    final Group type;

    BufferObjectType(int id, String name, int alignment, Group type) {
        this.id = id;
        this.name = name;
        this.alignment = alignment;
        this.type = type;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAlignment() {
        return alignment;
    }

    public Group getType() {
        return type;
    }

    public enum Group implements IEnumTypeBase {
        RENDER_DATA(0, "RenderData"),
        SHADER_RESOURCE(1, "ShaderResource"),
        ASYNCHRONOUS_TRANSFER(2, "AsynchronousTransfer");

        final int id;
        final String name;

        Group(int id, String name) {
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
}