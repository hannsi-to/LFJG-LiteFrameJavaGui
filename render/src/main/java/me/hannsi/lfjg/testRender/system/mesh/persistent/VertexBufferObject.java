package me.hannsi.lfjg.testRender.system.mesh.persistent;

import me.hannsi.lfjg.render.render.Vertex;
import me.hannsi.lfjg.render.system.buffer.BufferObjectType;
import me.hannsi.lfjg.render.system.memory.MemoryTask;
import me.hannsi.lfjg.testRender.debug.exceptions.render.mesh.persistent.PersistentMappedException;
import me.hannsi.lfjg.testRender.uitl.memory.MemoryFrameArena;

import static me.hannsi.lfjg.testRender.RenderSystemSetting.VERTEX_BUFFER_OBJECT_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glFlushMappedBufferRange;
import static org.lwjgl.opengl.GL30.nglMapBufferRange;
import static org.lwjgl.opengl.GL44.GL_MAP_COHERENT_BIT;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class VertexBufferObject {
    private final MemoryFrameArena memoryFrameArena;
    private final int flags;
    private boolean needSync;

    public VertexBufferObject(int flags, long initialSizeBytes) {
        if (initialSizeBytes % VERTEX_BUFFER_OBJECT_ALIGNMENT != 0) {
            throw new PersistentMappedException("capacity must be multiple of " + VERTEX_BUFFER_OBJECT_ALIGNMENT);
        }

        this.flags = flags;
        this.memoryFrameArena = new MemoryFrameArena(new MemoryTask() {
            @Override
            public long allocateMemory(long memorySizeBytes) {
//                glStateCache.bindArrayBufferForce(bufferId);
                glBufferStorage(GL_ARRAY_BUFFER, memorySizeBytes, flags);
                return nglMapBufferRange(
                        GL_ARRAY_BUFFER,
                        0,
                        memorySizeBytes,
                        flags
                );
            }

//            @Override
//            public void bindBuffer(int bufferId) {
//                glStateCache.bindArrayBufferForce(bufferId);
//            }
        }, initialSizeBytes);
        this.needSync = true;
    }

    public VertexBufferObject add(Vertex v) {
        v.putMemory(memoryFrameArena);

        return this;
    }

    public VertexBufferObject syncToGPU() {
        if (!needSync) {
            return this;
        }

        needSync = false;

        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_ARRAY_BUFFER, 0, memoryFrameArena.currentMemoryArenaSystem().getCurrentMemoryArena().getMemorySizeBytes());
        }

        return this;
    }

    public VertexBufferObject link() {
        memoryFrameArena.link();

        int stride = Vertex.BYTES;
        int offset = 0;

        for (BufferObjectType type : new BufferObjectType[]{
                BufferObjectType.POSITION_BUFFER,
                BufferObjectType.COLOR_BUFFER,
                BufferObjectType.TEXTURE_BUFFER,
                BufferObjectType.NORMAL_BUFFER
        }) {

            glEnableVertexAttribArray(type.getAttributeIndex());

            glVertexAttribPointer(
                    type.getAttributeIndex(),
                    type.getAttributeSize(),
                    GL_FLOAT,
                    false,
                    stride,
                    offset
            );

            offset += type.getAttributeSize() * Float.BYTES;
        }

        return this;
    }

    public void nextFrame() {
        memoryFrameArena.nextFrame();
    }

    public void endFrame() {
        memoryFrameArena.signalGPU();
    }
}
