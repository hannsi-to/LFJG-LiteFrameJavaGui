package me.hannsi.lfjg.testRender.system.mesh.persistent;

import me.hannsi.lfjg.render.system.memory.MemoryTask;
import me.hannsi.lfjg.testRender.debug.exceptions.render.mesh.persistent.PersistentMappedException;
import me.hannsi.lfjg.testRender.uitl.memory.MemoryFrameArena;

import static me.hannsi.lfjg.testRender.RenderSystemSetting.SHADER_STORAGE_BUFFER_OBJECT_ALIGNMENT;
import static org.lwjgl.opengl.GL30.nglMapBufferRange;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class ShaderStorageBufferObject {
    private final MemoryFrameArena memoryFrameArena;
    private final int flags;
    private final boolean needSync;

    public ShaderStorageBufferObject(int flags, long initialSizeBytes) {
        if (initialSizeBytes % SHADER_STORAGE_BUFFER_OBJECT_ALIGNMENT != 0) {
            throw new PersistentMappedException("capacity must be multiple of " + SHADER_STORAGE_BUFFER_OBJECT_ALIGNMENT);
        }

        this.flags = flags;
        this.memoryFrameArena = new MemoryFrameArena(new MemoryTask() {
            @Override
            public long allocateMemory(long memorySizeBytes) {
//                glStateCache.bindShaderStorageBufferForce(bufferId);
                glBufferStorage(GL_SHADER_STORAGE_BUFFER, memorySizeBytes, flags);
                return nglMapBufferRange(
                        GL_SHADER_STORAGE_BUFFER,
                        0,
                        memorySizeBytes,
                        flags
                );
            }

//            @Override
//            public void bindBuffer(int bufferId) {
//                glStateCache.bindShaderStorageBufferForce(bufferId);
//            }
        }, initialSizeBytes);
        this.needSync = true;
    }

    private static class Binding {
        public long offset;
        public long size;

        public Binding(long offset, long size) {
            this.offset = offset;
            this.size = size;
        }
    }
}
