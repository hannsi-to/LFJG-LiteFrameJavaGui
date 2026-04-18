package me.hannsi.lfjg.testRender.system.mesh.persistent;

import me.hannsi.lfjg.testRender.debug.exceptions.render.mesh.persistent.PersistentMappedException;
import me.hannsi.lfjg.testRender.system.mesh.DrawElementsIndirectCommand;
import me.hannsi.lfjg.testRender.uitl.memory.MemoryFrameArena;
import me.hannsi.lfjg.testRender.uitl.memory.MemoryTask;

import static me.hannsi.lfjg.testRender.LFJGRenderContext.glStateCache;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.DRAW_INDIRECT_BUFFER_ALIGNMENT;
import static org.lwjgl.opengl.GL30.glFlushMappedBufferRange;
import static org.lwjgl.opengl.GL30.nglMapBufferRange;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;
import static org.lwjgl.opengl.GL44.GL_MAP_COHERENT_BIT;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class DrawIndirectBuffer {
    private final MemoryFrameArena memoryFrameArena;
    private final int flags;
    private boolean needSync;

    public DrawIndirectBuffer(int flags, long initialSizeBytes) {
        if (initialSizeBytes % DRAW_INDIRECT_BUFFER_ALIGNMENT != 0) {
            throw new PersistentMappedException("capacity must be multiple of " + DRAW_INDIRECT_BUFFER_ALIGNMENT);
        }

        this.flags = flags;
        this.memoryFrameArena = new MemoryFrameArena(new MemoryTask() {
            @Override
            public long allocateMemory(long memorySizeBytes, int bufferId) {
                glStateCache.bindIndirectBufferForce(bufferId);
                glBufferStorage(GL_DRAW_INDIRECT_BUFFER, memorySizeBytes, flags);
                return nglMapBufferRange(
                        GL_DRAW_INDIRECT_BUFFER,
                        0,
                        memorySizeBytes,
                        flags
                );
            }

            @Override
            public void bindBuffer(int bufferId) {
                glStateCache.bindIndirectBufferForce(bufferId);
            }
        }, initialSizeBytes);
    }

    public DrawIndirectBuffer add(DrawElementsIndirectCommand drawElementsIndirectCommand) {
        drawElementsIndirectCommand.putMemory(memoryFrameArena);

        return this;
    }

    public DrawIndirectBuffer syncToGPU() {
        if (!needSync) {
            return this;
        }

        needSync = false;

        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_DRAW_INDIRECT_BUFFER, 0, memoryFrameArena.currentMemoryArenaSystem().getCurrentMemoryArena().getMemorySizeBytes());
        }

        return this;
    }

    public DrawIndirectBuffer link() {
        memoryFrameArena.link();

        return this;
    }

    public void nextFrame() {
        memoryFrameArena.nextFrame();
    }

    public void endFrame() {
        memoryFrameArena.signalGPU();
    }
}
