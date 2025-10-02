package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.*;

public class TestPersistentMappedVBO implements PersistentMappedBuffer {
    private final List<Vertex> vertices = new ArrayList<>();
    private final Set<Integer> changedIndices = new HashSet<>();
    private final int flags;

    private FloatBuffer mappedBuffer;
    private int maxVertices;
    private int bufferId;
    private boolean dirty = false;
    private boolean fullUpdate = false;

    public TestPersistentMappedVBO(int flags, int initialVerticesCapacity) {
        this.flags = flags;
        updateBufferStorage(initialVerticesCapacity);
    }

    private void updateBufferStorage(int maxVertices) {
        this.maxVertices = maxVertices;

        int sizeInBytes = maxVertices * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES;
        if (bufferId != 0) {
            GLStateCache.deleteArrayBuffer(bufferId);
        }

        bufferId = GL15.glGenBuffers();
        GLStateCache.bindArrayBuffer(bufferId);
        GL44.glBufferStorage(GL15.GL_ARRAY_BUFFER, sizeInBytes, flags);

        ByteBuffer byteBuffer = GL30.glMapBufferRange(
                GL15.GL_ARRAY_BUFFER,
                0,
                sizeInBytes,
                flags
        );
        if (byteBuffer == null) {
            throw new RuntimeException("glMapBufferRange failed");
        }
        mappedBuffer = byteBuffer.asFloatBuffer();

        fullUpdate = true;
        dirty = true;
    }

    public TestPersistentMappedVBO createVertexAttribute(int[] vaoIds, BufferObjectType... bufferObjectType) {
        for (int vaoId : vaoIds) {
            GLStateCache.bindVertexArray(vaoId);
            GLStateCache.bindArrayBuffer(bufferId);

            int stride = MeshConstants.FLOATS_PER_VERTEX * Float.BYTES;
            int pointer = 0;
            for (BufferObjectType objectType : bufferObjectType) {
                GL20.glEnableVertexAttribArray(objectType.getAttributeIndex());
                GL20.glVertexAttribPointer(
                        objectType.getAttributeIndex(),
                        objectType.getAttributeSize(),
                        GL11.GL_FLOAT,
                        false,
                        stride,
                        (long) pointer * Float.BYTES
                );
                pointer += objectType.getAttributeSize();
            }
        }

        return this;
    }

    public TestPersistentMappedVBO setVertex(int index, Vertex v) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(index + " < 0");
        }
        if (index >= vertices.size()) {
            throw new IndexOutOfBoundsException(index + " >= " + vertices.size());
        }

        vertices.set(index, v);
        if (!fullUpdate) {
            changedIndices.add(index);
        }
        dirty = true;

        return this;
    }

    public TestPersistentMappedVBO add(Vertex v) {
        vertices.add(v);
        if (!fullUpdate) {
            changedIndices.add(vertices.size() - 1);
        }
        dirty = true;

        return this;
    }

    public TestPersistentMappedVBO insert(int index, Vertex v) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(index + " < 0");
        }
        if (index > vertices.size()) {
            throw new IndexOutOfBoundsException(index + " > " + vertices.size());
        }

        changedIndices.clear();
        vertices.add(index, v);
        fullUpdate = true;
        dirty = true;

        return this;
    }

    public TestPersistentMappedVBO remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(index + " < 0");
        }
        if (index >= vertices.size()) {
            throw new IndexOutOfBoundsException(index + " >= " + vertices.size());
        }

        vertices.remove(index);
        changedIndices.clear();
        fullUpdate = true;
        dirty = true;

        return this;
    }

    public void syncToGPU() {
        if (!dirty) {
            return;
        }
        if (vertices.size() > maxVertices) {
            updateBufferStorage(maxVertices * 2);
        }

        boolean doFull = fullUpdate || changedIndices.isEmpty() || changedIndices.size() > Math.max(1, vertices.size() / 2);
        if (doFull) {
            mappedBuffer.position(0);
            for (Vertex v : vertices) {
                writeVertex(mappedBuffer, v);
            }

            flushMappedRange(0, (long) vertices.size() * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES);
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
                    writeVertexRange(start, end);
                    flushMappedRange(
                            (long) start * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES,
                            (long) (end - start) * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES
                    );
                    start = idx;
                    end = idx + 1;
                }
            }
            if (start != -1) {
                writeVertexRange(start, end);
                flushMappedRange(
                        (long) start * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES,
                        (long) (end - start) * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES
                );
            }
        }

        changedIndices.clear();
        fullUpdate = false;
        dirty = false;
    }

    private void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) != GL_MAP_COHERENT_BIT) {
            GL44.glFlushMappedBufferRange(GL15.GL_ARRAY_BUFFER, byteOffset, byteLength);
        }
    }

    private void writeVertex(FloatBuffer buf, Vertex v) {
        buf.put(v.x).put(v.y).put(v.z);
        buf.put(v.red).put(v.green).put(v.blue).put(v.alpha);
        buf.put(v.u).put(v.v);
        buf.put(v.normalsX).put(v.normalsY).put(v.normalsZ);
    }

    private void writeVertex(FloatBuffer buf, int base, Vertex v) {
        buf.put(base, v.x)
                .put(base + 1, v.y)
                .put(base + 2, v.z)
                .put(base + 3, v.red)
                .put(base + 4, v.green)
                .put(base + 5, v.blue)
                .put(base + 6, v.alpha)
                .put(base + 7, v.u)
                .put(base + 8, v.v)
                .put(base + 9, v.normalsX)
                .put(base + 10, v.normalsY)
                .put(base + 11, v.normalsZ);
    }

    private void writeVertexRange(int startVertex, int endVertexExclusive) {
        for (int vi = startVertex; vi < endVertexExclusive; vi++) {
            Vertex v = vertices.get(vi);
            int base = vi * MeshConstants.FLOATS_PER_VERTEX;
            writeVertex(mappedBuffer, base, v);
        }
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getBufferId() {
        return bufferId;
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            GLStateCache.deleteArrayBuffer(bufferId);
            bufferId = 0;
        }
        mappedBuffer = null;
        vertices.clear();
        changedIndices.clear();
        dirty = false;
        fullUpdate = false;
    }
}
