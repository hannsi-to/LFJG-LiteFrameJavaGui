package me.hannsi.lfjg.testRender.system.mesh.persistent;

import me.hannsi.lfjg.testRender.debug.exceptions.render.mesh.persistent.PersistentMappedException;
import me.hannsi.lfjg.testRender.uitl.memory.MemoryFrameArena;
import me.hannsi.lfjg.testRender.uitl.memory.MemoryTask;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.testRender.LFJGRenderContext.glStateCache;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.ELEMENT_ARRAY_BUFFER_ALIGNMENT;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL30.glFlushMappedBufferRange;
import static org.lwjgl.opengl.GL30.nglMapBufferRange;
import static org.lwjgl.opengl.GL44.GL_MAP_COHERENT_BIT;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class ElementArrayBuffer {
    private final MemoryFrameArena memoryFrameArena;
    private final int flags;
    private boolean needSync;

    public ElementArrayBuffer(int flags, long initialSizeBytes) {
        if (initialSizeBytes % ELEMENT_ARRAY_BUFFER_ALIGNMENT != 0) {
            throw new PersistentMappedException("capacity must be multiple of " + ELEMENT_ARRAY_BUFFER_ALIGNMENT);
        }

        this.flags = flags;
        this.memoryFrameArena = new MemoryFrameArena(new MemoryTask() {
            @Override
            public long allocateMemory(long memorySizeBytes, int bufferId) {
                glStateCache.bindElementArrayBufferForce(bufferId);
                glBufferStorage(GL_ELEMENT_ARRAY_BUFFER, memorySizeBytes, flags);
                return nglMapBufferRange(
                        GL_ELEMENT_ARRAY_BUFFER,
                        0,
                        memorySizeBytes,
                        flags
                );
            }

            @Override
            public void bindBuffer(int bufferId) {
                glStateCache.bindElementArrayBufferForce(bufferId);
            }
        }, initialSizeBytes);
        this.needSync = true;
    }

    public ElementArrayBuffer add(int index) {
        long address = memoryFrameArena.alloc(Integer.BYTES, Integer.BYTES);
        UNSAFE.putInt(address, index);

        return this;
    }

    public ElementArrayBuffer syncToGPU() {
        if (!needSync) {
            return this;
        }

        needSync = false;

        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_ELEMENT_ARRAY_BUFFER, 0, memoryFrameArena.currentMemoryArenaSystem().getCurrentMemoryArena().getMemorySizeBytes());
        }

        return this;
    }

    public ElementArrayBuffer link() {
        glStateCache.bindElementArrayBufferForce(memoryFrameArena.currentMemoryFrame().bufferId);
        return this;
    }

    public void nextFrame() {
        memoryFrameArena.nextFrame();
    }

    public void endFrame() {
        memoryFrameArena.signalGPU();
    }
}
