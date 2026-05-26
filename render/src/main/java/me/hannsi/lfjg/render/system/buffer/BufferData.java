package me.hannsi.lfjg.render.system.buffer;

public class BufferData {
    public final BufferObjectType bufferObjectType;
    public final BufferUpdateType bufferUpdateType;
    public final long address;
    public final long offset;
    public final long size;

    public BufferData(BufferObjectType bufferObjectType, BufferUpdateType bufferUpdateType, long address, long offset, long size) {
        this.bufferObjectType = bufferObjectType;
        this.bufferUpdateType = bufferUpdateType;
        this.address = address;
        this.offset = offset;
        this.size = size;
    }
}
