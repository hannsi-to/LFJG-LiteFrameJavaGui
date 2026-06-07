package me.hannsi.lfjg.render.system.buffer.l1;

import me.hannsi.lfjg.render.system.buffer.BufferObjectType;
import me.hannsi.lfjg.render.util.IEnumTypeSupportedBase;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.render.RenderSystemSetting.BUFFER_OBJECT_MAX_ALLOW_GAP_SIZE;

public class BufferObject {
    private final int id;
    private final GenerationBufferObjectType generationBufferObjectType;
    private final List<MemoryBlock> memoryBlocks;
    private final List<BufferObjectData> bufferObjectDatum;
    private int flags;
    private Allocation allocation;
    private long offset;

    public BufferObject(int id, GenerationBufferObjectType generationBufferObjectType) {
        this.id = id;
        this.generationBufferObjectType = generationBufferObjectType;
        this.memoryBlocks = new ArrayList<>();
        this.bufferObjectDatum = new ArrayList<>();
        this.offset = 0;
    }

    public void generationStateCheck(GenerationBufferObjectType needGenerationBufferObjectType, IEnumTypeSupportedBase iEnumTypeSupportedBase) {
        if (needGenerationBufferObjectType != generationBufferObjectType) {
            throw new IllegalStateException(generationBufferObjectType.getName() + " for the buffer object created with " + iEnumTypeSupportedBase.getName() + " cannot be used.");
        }
    }

    public void addBufferObjectData(BufferObjectType bufferObjectType,long size) {
        bufferObjectDatum.add(new BufferObjectData(bufferObjectType,offset, size));
        offset += size;
    }

    public void addMemoryBlock(long offset, long size) {
        if (size <= 0) {
            return;
        }

        long newStart = offset;
        long newEnd = offset + size;

        int i = 0;
        while (i < memoryBlocks.size()) {
            MemoryBlock current = memoryBlocks.get(i);
            long curStart = current.offset;
            long curEnd = current.offset + current.size;

            long gap = 0;
            if (newStart > curEnd) {
                gap = newStart - curEnd;
            } else if (curStart > newEnd) {
                gap = curStart - newEnd;
            }

            if ((newStart <= curEnd && newEnd >= curStart) || gap <= BUFFER_OBJECT_MAX_ALLOW_GAP_SIZE) {
                newStart = Math.min(newStart, curStart);
                newEnd = Math.max(newEnd, curEnd);

                memoryBlocks.remove(i);

                continue;
            }

            if (curStart < newStart) {
                i++;
            } else {
                break;
            }
        }

        memoryBlocks.add(i, new MemoryBlock(newStart, newEnd - newStart));
    }

    public int getId() {
        return id;
    }

    public GenerationBufferObjectType getGenerationBufferObjectType() {
        return generationBufferObjectType;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public Allocation getAllocation() {
        return allocation;
    }

    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }

    public List<MemoryBlock> getMemoryBlocks() {
        return memoryBlocks;
    }

    public List<BufferObjectData> getBufferObjectDatum() {
        return bufferObjectDatum;
    }

    public long getOffset() {
        return offset;
    }

    public static class BufferObjectData{
        public final BufferObjectType bufferObjectType;
        public final long offset;
        public final long size;

        public BufferObjectData(BufferObjectType bufferObjectType,long offset, long size) {
            this.bufferObjectType = bufferObjectType;
            this.offset = offset;
            this.size = size;
        }
    }

    public static class MemoryBlock {
        public final long offset;
        public final long size;

        public MemoryBlock(long offset, long size) {
            this.offset = offset;
            this.size = size;
        }
    }
}
