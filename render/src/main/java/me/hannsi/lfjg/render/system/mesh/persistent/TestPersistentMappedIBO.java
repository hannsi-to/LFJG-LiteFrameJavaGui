package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.mesh.DrawElementsIndirectCommand;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL44;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class TestPersistentMappedIBO implements PersistentMappedBuffer {
    private final int flags;
    private final List<DrawElementsIndirectCommand> commands = new ArrayList<>();
    private IntBuffer mappedBuffer;
    private int maxCommands;
    private int bufferId;
    private boolean dirty = false;

    public TestPersistentMappedIBO(int flags, int initialCapacity) {
        this.flags = flags;
        updateBufferStorage(initialCapacity);
    }

    private void updateBufferStorage(int maxCommands) {
        this.maxCommands = maxCommands;
        int sizeInBytes = maxCommands * DrawElementsIndirectCommand.BYTES;

        if (bufferId != 0) {
            GLStateCache.deleteIndirectBuffer(bufferId);
        }

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
            throw new RuntimeException("glMapBufferRange failed");
        }
        mappedBuffer = byteBuffer.asIntBuffer();
        dirty = true;
    }

    public TestPersistentMappedIBO addCommand(DrawElementsIndirectCommand cmd) {
        commands.add(cmd);
        dirty = true;

        return this;
    }

    public void syncToGPU() {
        if (!dirty) {
            return;
        }
        if (commands.size() > maxCommands) {
            updateBufferStorage(maxCommands * 2);
        }

        mappedBuffer.position(0);
        for (DrawElementsIndirectCommand cmd : commands) {
            mappedBuffer.put(cmd.count);
            mappedBuffer.put(cmd.instanceCount);
            mappedBuffer.put(cmd.firstIndex);
            mappedBuffer.put(cmd.baseVertex);
            mappedBuffer.put(cmd.baseInstance);
        }

        flushMappedRange(0, (long) commands.size() * DrawElementsIndirectCommand.BYTES);
        dirty = false;
    }

    private void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) != GL_MAP_COHERENT_BIT) {
            GL44.glFlushMappedBufferRange(GL40.GL_DRAW_INDIRECT_BUFFER, byteOffset, byteLength);
        }
    }

    public int getCommandCount() {
        return commands.size();
    }

    public int getBufferId() {
        return bufferId;
    }

    public IntBuffer getMappedBuffer() {
        return mappedBuffer;
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            GLStateCache.deleteIndirectBuffer(bufferId);
            bufferId = 0;
        }
        mappedBuffer = null;
        commands.clear();
        dirty = false;
    }
}