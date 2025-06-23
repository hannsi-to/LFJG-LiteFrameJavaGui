package me.hannsi.lfjg.render.system.mesh;

import lombok.Getter;
import me.hannsi.lfjg.utils.type.types.AttributeType;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL44.*;

@Getter
public class PersistentMappedVBO {
    private final int bufferId;
    private final FloatBuffer mappedBuffer;
    private final int sizeInBytes;

    public PersistentMappedVBO(int size, int flags) {
        this.sizeInBytes = size * Float.BYTES;

        bufferId = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        glBufferStorage(
                GL_ARRAY_BUFFER,
                sizeInBytes,
                flags
        );

        ByteBuffer byteBuffer = glMapBufferRange(
                GL_ARRAY_BUFFER,
                0,
                sizeInBytes,
                flags
        );

        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        mappedBuffer = byteBuffer.asFloatBuffer();

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public PersistentMappedVBO update(float[] newData) {
        mappedBuffer.clear();
        mappedBuffer.put(newData);
        mappedBuffer.flip();

        glMemoryBarrier(GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);

        return this;
    }

    public PersistentMappedVBO attribute(AttributeType attributeType) {
        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        glEnableVertexAttribArray(attributeType.getIndex());
        glVertexAttribPointer(attributeType.getIndex(), attributeType.getSize(), GL_FLOAT, false, 0, 0);

        return this;
    }

    public void cleanup() {
        glDeleteBuffers(bufferId);
    }
}
