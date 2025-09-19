package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.mesh.DrawCommand;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;

public class PersistentMappedIBO implements PersistentMappedBuffer {
    private final int bufferId;
    private final IntBuffer mappedBuffer;
    private final int sizeInBytes;
    private final int maxCommands;

    private int flags;

    public PersistentMappedIBO(int maxCommands, int flags) {
        this.flags = flags;
        this.maxCommands = maxCommands;
        this.sizeInBytes = DrawCommand.SIZE_BYTE * maxCommands;

        bufferId = GL15.glGenBuffers();

        GLStateCache.bindIndirectBuffer(bufferId);
        GL44.glBufferStorage(GL40.GL_DRAW_INDIRECT_BUFFER, sizeInBytes, flags);

        ByteBuffer byteBuffer = GL30.glMapBufferRange(
                GL40.GL_DRAW_INDIRECT_BUFFER,
                0,
                sizeInBytes,
                flags
        );

        if (byteBuffer == null) {
            throw new NullPointerException("Failed to map indirect buffer");
        }

        mappedBuffer = byteBuffer.asIntBuffer();
    }

    public PersistentMappedIBO update(List<DrawCommand> commands) {
        if (commands.size() > maxCommands) {
            throw new IllegalArgumentException("Too many draw commands for buffer");
        }

        mappedBuffer.clear();
        for (DrawCommand cmd : commands) {
            cmd.putIntoBuffer(mappedBuffer);
        }

        if ((flags & GL44.GL_MAP_COHERENT_BIT) == 0) {
            GL42.glMemoryBarrier(GL44.GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);
        }

        return this;
    }

    @Override
    public void cleanup() {
        GL15.glDeleteBuffers(bufferId);
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getBufferId() {
        return bufferId;
    }

    public IntBuffer getMappedBuffer() {
        return mappedBuffer;
    }

    public int getSizeInBytes() {
        return sizeInBytes;
    }

    public int getMaxCommands() {
        return maxCommands;
    }
}