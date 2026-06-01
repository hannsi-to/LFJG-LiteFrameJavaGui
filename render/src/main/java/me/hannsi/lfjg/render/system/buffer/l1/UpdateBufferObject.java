package me.hannsi.lfjg.render.system.buffer.l1;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL15.nglBufferSubData;
import static org.lwjgl.opengl.GL30.nglMapBufferRange;
import static org.lwjgl.opengl.GL44.GL_MAP_PERSISTENT_BIT;
import static org.lwjgl.opengl.GL45.*;

public class UpdateBufferObject {
    public static void beginUpdateBufferObject(UpdateBufferObjectType updateBufferObjectType, BufferObject bufferObject, long size) {
        if (!updateBufferObjectType.isSupported()) {
            updateBufferObjectType.throwUnsupportedGPUDriver();
        }

        Allocation currentAllocation = bufferObject.getAllocation();
        if (currentAllocation != null) {
            if (currentAllocation.getSize() >= size) {
                return;
            } else {
                currentAllocation.freed();
                bufferObject.setAllocation(null);
            }
        }

        Allocation allocation;
        switch (updateBufferObjectType) {
            case LEGACY -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.LEGACY, updateBufferObjectType);
                allocation = new Allocation(size);
            }
            case DIRECT_STATE_ACCESS -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.DIRECT_STATE_ACCESS, updateBufferObjectType);
                allocation = new Allocation(size);
            }
            case MAP -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.LEGACY, updateBufferObjectType);
                glStateCache.bindBuffer(bufferObject.getBufferObjectType().getId(), bufferObject.getId());
                long address = nglMapBufferRange(bufferObject.getBufferObjectType().getId(), 0L, size, bufferObject.getFlags());
                if ((bufferObject.getFlags() & GL_MAP_PERSISTENT_BIT) != 0) {
                    allocation = new Allocation(address, size, (ignore) -> {
                        glStateCache.bindBuffer(bufferObject.getBufferObjectType().getId(), bufferObject.getId());
                        glUnmapBuffer(bufferObject.getBufferObjectType().getId());
                    });
                } else {
                    allocation = new Allocation(address, size, (ignore) -> {});
                }
            }
            case DIRECT_STATE_ACCESS_MAP -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.DIRECT_STATE_ACCESS, updateBufferObjectType);
                long address = nglMapNamedBufferRange(bufferObject.getId(), 0L, size, bufferObject.getFlags());
                if ((bufferObject.getFlags() & GL_MAP_PERSISTENT_BIT) != 0) {
                    allocation = new Allocation(address, size, (ignore) -> {
                        glUnmapNamedBuffer(bufferObject.getId());
                    });
                } else {
                    allocation = new Allocation(address, size, (ignore) -> {});
                }
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + updateBufferObjectType);
        }

        bufferObject.setAllocation(allocation);
    }

    public static void endUpdateBufferObject(UpdateBufferObjectType updateBufferObjectType, BufferObject bufferObject) {
        if (!updateBufferObjectType.isSupported()) {
            updateBufferObjectType.throwUnsupportedGPUDriver();
        }

        Allocation allocation = bufferObject.getAllocation();
        if (allocation == null) {
            throw new IllegalStateException("BufferObject allocation is null. Call beginUpdateBufferObject first.");
        }
        switch (updateBufferObjectType) {
            case LEGACY -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.LEGACY, updateBufferObjectType);
                glStateCache.bindBuffer(bufferObject.getBufferObjectType().getId(), bufferObject.getId());
                nglBufferSubData(bufferObject.getBufferObjectType().getId(), 0L, bufferObject.getAllocation().getSize(), bufferObject.getAllocation().getAddress());
            }
            case DIRECT_STATE_ACCESS -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.DIRECT_STATE_ACCESS, updateBufferObjectType);
                nglNamedBufferSubData(bufferObject.getId(), 0L, bufferObject.getAllocation().getSize(), bufferObject.getAllocation().getAddress());
            }
            case MAP -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.LEGACY, updateBufferObjectType);
                if ((bufferObject.getFlags() & GL_MAP_FLUSH_EXPLICIT_BIT) != 0) {
                    for (BufferObject.MemoryBlock memoryBlock : bufferObject.getMemoryBlocks()) {
                        glFlushMappedBufferRange(bufferObject.getBufferObjectType().getId(), memoryBlock.offset, memoryBlock.size);
                    }
                    bufferObject.getMemoryBlocks().clear();
                }
                if ((bufferObject.getFlags() & GL_MAP_PERSISTENT_BIT) == 0) {
                    glStateCache.bindBuffer(bufferObject.getBufferObjectType().getId(), bufferObject.getId());
                    glUnmapBuffer(bufferObject.getBufferObjectType().getId());

                    bufferObject.getAllocation().freed();
                    bufferObject.setAllocation(null);
                }
            }
            case DIRECT_STATE_ACCESS_MAP -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.DIRECT_STATE_ACCESS, updateBufferObjectType);
                if ((bufferObject.getFlags() & GL_MAP_FLUSH_EXPLICIT_BIT) != 0) {
                    for (BufferObject.MemoryBlock memoryBlock : bufferObject.getMemoryBlocks()) {
                        glFlushMappedNamedBufferRange(bufferObject.getId(), memoryBlock.offset, memoryBlock.size);
                    }
                    bufferObject.getMemoryBlocks().clear();
                }
                if ((bufferObject.getFlags() & GL_MAP_PERSISTENT_BIT) == 0) {
                    glUnmapNamedBuffer(bufferObject.getId());
                    bufferObject.getAllocation().freed();
                    bufferObject.setAllocation(null);
                }
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + updateBufferObjectType);
        }
    }
}
