package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;

public class BufferManagerConfig {
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

    public enum BufferType implements IEnumTypeBase {
        ULTRA_BINDLESS(0, "UltraBindless", GL_CAPABILITIES.GL_NV_shader_buffer_load && GL_CAPABILITIES.GL_NV_vertex_buffer_unified_memory && GL_CAPABILITIES.OpenGL45, true),
        PERSISTENT_COHERENT_STORAGE(1, "PersistentCoherentStorage", GL_CAPABILITIES.GL_ARB_buffer_storage && GL_CAPABILITIES.OpenGL44, true),
        PERSISTENT_CLIENT_STORAGE(2, "PersistentClientStorage", GL_CAPABILITIES.GL_ARB_buffer_storage && GL_CAPABILITIES.OpenGL44, true),
        DIRECT_STATE_IMMUTABLE(3, "DirectStateImmutable", GL_CAPABILITIES.GL_ARB_direct_state_access && GL_CAPABILITIES.GL_ARB_buffer_storage && GL_CAPABILITIES.OpenGL45, true),
        DIRECT_STATE_MUTABLE(4, "DirectStateMutable", GL_CAPABILITIES.GL_ARB_direct_state_access && GL_CAPABILITIES.OpenGL45, false),
        LEGACY_IMMUTABLE(5, "LegacyImmutable", GL_CAPABILITIES.OpenGL44, false),
        EXPLICIT_INVALIDATION(6, "ExplicitInvalidation", GL_CAPABILITIES.OpenGL43, false),
        MAP(7, "Map", GL_CAPABILITIES.OpenGL30, false),
        LEGACY_FALLBACK(8, "LegacyFallback", GL_CAPABILITIES.OpenGL15, false);

        final int id;
        final String name;
        final boolean needCapabilities;
        final boolean supportsSubAllocation;

        BufferType(int id, String name, boolean needCapabilities, boolean supportsSubAllocation) {
            this.id = id;
            this.name = name;
            this.needCapabilities = needCapabilities;
            this.supportsSubAllocation = supportsSubAllocation;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        public boolean isNeedCapabilities() {
            return needCapabilities;
        }

        public boolean isSupportsSubAllocation() {
            return supportsSubAllocation;
        }
    }

    public enum AllocationPolicy implements IEnumTypeBase {
        BALANCE(0, "Balance") {
            @Override
            public BufferType selectBufferType() {
                BufferType[] candidates = {
                        BufferType.PERSISTENT_COHERENT_STORAGE,
                        BufferType.DIRECT_STATE_MUTABLE,
                        BufferType.MAP,
                        BufferType.LEGACY_FALLBACK
                };
                return firstAvailable(candidates);
            }
        },
        PERFORMANCE(1, "Performance") {
            @Override
            public BufferType selectBufferType() {
                BufferType[] candidates = {
                        BufferType.ULTRA_BINDLESS,
                        BufferType.PERSISTENT_COHERENT_STORAGE,
                        BufferType.DIRECT_STATE_IMMUTABLE,
                        BufferType.LEGACY_FALLBACK
                };
                return firstAvailable(candidates);
            }
        },
        MEMORY_SAVING(2, "MemorySaving") {
            @Override
            public BufferType selectBufferType() {
                BufferType[] candidates = {
                        BufferType.EXPLICIT_INVALIDATION,
                        BufferType.MAP,
                        BufferType.LEGACY_FALLBACK
                };
                return firstAvailable(candidates);
            }
        };

        final int id;
        final String name;

        AllocationPolicy(int id, String name) {
            this.id = id;
            this.name = name;
        }

        protected BufferType firstAvailable(BufferType[] candidates) {
            for (BufferType type : candidates) {
                if (type.isNeedCapabilities()) {
                    return type;
                }
            }
            return BufferType.LEGACY_FALLBACK;
        }

        public abstract BufferType selectBufferType();

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
