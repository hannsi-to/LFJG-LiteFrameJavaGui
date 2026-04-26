package me.hannsi.lfjg.render.system;

import me.hannsi.lfjg.render.render.Vertex;
import me.hannsi.lfjg.render.system.buffer.BufferObjectType;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.render.LFJGRenderContext.BUFFER_BINDING_MODE;
import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL43.glVertexAttribBinding;
import static org.lwjgl.opengl.GL43.glVertexAttribFormat;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.NVVertexBufferUnifiedMemory.*;

public class Mesh {
    private final List<MeshBuilder> pendingMeshBuilders;
    private final int vaoId;
    private final int vboId;
    private final int eboId;
    private final int iboId;
    private boolean needRepack;

    Mesh(int vboId, int eboId, int iboId) {
        this.pendingMeshBuilders = new ArrayList<>();
        this.vboId = vboId;
        this.eboId = eboId;
        this.iboId = iboId;
        this.needRepack = false;

        int stride = Vertex.BYTES;
        BufferObjectType[] bufferObjectTypes = new BufferObjectType[]{
                BufferObjectType.POSITION_BUFFER,
                BufferObjectType.COLOR_BUFFER,
                BufferObjectType.TEXTURE_BUFFER,
                BufferObjectType.NORMAL_BUFFER
        };

        switch (BUFFER_BINDING_MODE) {
            case LEGACY -> {
                vaoId = glGenVertexArrays();
                glStateCache.bindVertexArrayForce(vaoId);
                glStateCache.bindArrayBufferForce(vboId);
                glStateCache.bindElementArrayBufferForce(eboId);

                int offset = 0;
                for (BufferObjectType type : bufferObjectTypes) {
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
            }
            case ATTRIB_BINDING -> {
                vaoId = glGenVertexArrays();
                glStateCache.bindVertexArrayForce(vaoId);

                int bindingIndex = 0;
                int offset = 0;
                for (BufferObjectType bufferObjectType : bufferObjectTypes) {
                    int index = bufferObjectType.getAttributeIndex();
                    glEnableVertexAttribArray(index);
                    glVertexAttribFormat(index, bufferObjectType.getAttributeSize(), GL_FLOAT, false, offset);
                    glVertexAttribBinding(index, bindingIndex);
                    offset += bufferObjectType.getAttributeSize() * Float.BYTES;
                }

//                glBindVertexBuffer(bindingIndex, vboId, 0, stride);
                glStateCache.bindElementArrayBufferForce(eboId);
            }
            case DSA -> {
                vaoId = glCreateVertexArrays();

                int bindingIndex = 0;
                int offset = 0;
                for (BufferObjectType type : bufferObjectTypes) {
                    int index = type.getAttributeIndex();
                    glEnableVertexArrayAttrib(vaoId, index);
                    glVertexArrayAttribFormat(vaoId, index, type.getAttributeSize(), GL_FLOAT, false, offset);
                    glVertexArrayAttribBinding(vaoId, index, bindingIndex);
                    offset += type.getAttributeSize() * Float.BYTES;
                }

                glVertexArrayVertexBuffer(vaoId, bindingIndex, vboId, 0, stride);
                glVertexArrayElementBuffer(vaoId, eboId);
            }
            case NV_UNIFIED_MEMORY -> {
                vaoId = glGenVertexArrays();
                glStateCache.bindVertexArrayForce(vaoId);
                glEnableClientState(GL_VERTEX_ATTRIB_ARRAY_UNIFIED_NV);
                glEnableClientState(GL_ELEMENT_ARRAY_UNIFIED_NV);

                for (BufferObjectType type : bufferObjectTypes) {
                    int index = type.getAttributeIndex();
                    glEnableVertexAttribArray(index);
                    glVertexAttribFormatNV(index, type.getAttributeSize(), GL_FLOAT, false, stride);
                }
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + BUFFER_BINDING_MODE);
        }
    }

    public static Mesh createMesh(int vboId, int eboId, int iboId) {
        return new Mesh(vboId, eboId, iboId);
    }

    public Mesh addObject(MeshBuilder meshBuilder) {
        pendingMeshBuilders.add(meshBuilder);

        needRepack = true;

        return this;
    }
}
