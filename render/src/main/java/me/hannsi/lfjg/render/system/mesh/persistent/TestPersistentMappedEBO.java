package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL44;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.*;

public class TestPersistentMappedEBO implements PersistentMappedBuffer {
    private final int flags;
    private final List<Integer> indices = new ArrayList<>();
    private final Set<Integer> changedIndices = new HashSet<>();
    private IntBuffer mappedBuffer;
    private int maxIndices;
    private int bufferId;
    private boolean dirty = false;
    private boolean fullUpdate = false;

    public TestPersistentMappedEBO(int flags, int initialCapacity) {
        this.flags = flags;
        updateBufferStorage(initialCapacity);
    }

    private void updateBufferStorage(int maxIndices) {
        this.maxIndices = maxIndices;

        int sizeInBytes = maxIndices * Integer.BYTES;
        if (bufferId != 0) {
            GLStateCache.deleteElementArrayBuffer(bufferId);
        }

        bufferId = GL15.glGenBuffers();
        GLStateCache.bindElementArrayBuffer(bufferId);
        GL44.glBufferStorage(GL15.GL_ELEMENT_ARRAY_BUFFER, sizeInBytes, flags);

        ByteBuffer byteBuffer = GL30.glMapBufferRange(
                GL15.GL_ELEMENT_ARRAY_BUFFER,
                0,
                sizeInBytes,
                flags
        );
        if (byteBuffer == null) {
            throw new RuntimeException("glMapBufferRange failed");
        }
        mappedBuffer = byteBuffer.asIntBuffer();

        fullUpdate = true;
        dirty = true;
    }

    public TestPersistentMappedEBO setIndex(int index, int value) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(index + " < 0");
        }
        if (index >= indices.size()) {
            throw new IndexOutOfBoundsException(index + " >= " + indices.size());
        }

        indices.set(index, value);
        if (!fullUpdate) {
            changedIndices.add(index);
        }
        dirty = true;

        return this;
    }

    public TestPersistentMappedEBO add(int index) {
        indices.add(index);
        if (!fullUpdate) {
            changedIndices.add(indices.size() - 1);
        }
        dirty = true;

        return this;
    }

    public TestPersistentMappedEBO insert(int index, int value) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(index + " < 0");
        }
        if (index > indices.size()) {
            throw new IndexOutOfBoundsException(index + " > " + indices.size());
        }

        changedIndices.clear();
        indices.add(index, value);
        fullUpdate = true;
        dirty = true;

        return this;
    }

    public TestPersistentMappedEBO remove(int index) {
        if (index < 0 || index >= indices.size()) {
            throw new IndexOutOfBoundsException(index + " out of bounds");
        }
        indices.remove(index);
        changedIndices.clear();
        fullUpdate = true;
        dirty = true;
        return this;
    }

    public TestPersistentMappedEBO linkVertexArrayObject(int vaoId) {
        GLStateCache.bindVertexArray(vaoId);
        GLStateCache.bindElementArrayBuffer(bufferId);
        GL30.glBindVertexArray(0);

        return this;
    }

    public void syncToGPU() {
        if (!dirty) {
            return;
        }
        if (indices.size() > maxIndices) {
            updateBufferStorage(maxIndices * 2);
        }

        boolean doFull = fullUpdate || changedIndices.isEmpty() || changedIndices.size() > Math.max(1, indices.size() / 2);
        if (doFull) {
            mappedBuffer.position(0);
            for (int i : indices) {
                mappedBuffer.put(i);
            }

            flushMappedRange(0, (long) indices.size() * Integer.BYTES);
        } else {
            int[] idxs = changedIndices.stream().mapToInt(Integer::intValue).toArray();
            Arrays.sort(idxs);

            int start = -1;
            int end = -1;
            for (int idx : idxs) {
                if (start == -1) {
                    start = idx;
                    end = idx + 1;
                } else if (idx == end) {
                    end = idx + 1;
                } else {
                    writeIndexRange(start, end);
                    flushMappedRange(
                            (long) start * Integer.BYTES,
                            (long) (end - start) * Integer.BYTES
                    );
                    start = idx;
                    end = idx + 1;
                }
            }
            if (start != -1) {
                writeIndexRange(start, end);
                flushMappedRange(
                        (long) start * Integer.BYTES,
                        (long) (end - start) * Integer.BYTES
                );
            }
        }

        changedIndices.clear();
        fullUpdate = false;
        dirty = false;
    }

    private void writeIndexRange(int start, int endExclusive) {
        for (int i = start; i < endExclusive; i++) {
            mappedBuffer.put(i, indices.get(i));
        }
    }

    private void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) != GL_MAP_COHERENT_BIT) {
            GL44.glFlushMappedBufferRange(GL15.GL_ELEMENT_ARRAY_BUFFER, byteOffset, byteLength);
        }
    }

    public int getIndexCount() {
        return indices.size();
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
            GLStateCache.deleteElementArrayBuffer(bufferId);
            bufferId = 0;
        }
        mappedBuffer = null;
        indices.clear();
        changedIndices.clear();
        dirty = false;
        fullUpdate = false;
    }
}
