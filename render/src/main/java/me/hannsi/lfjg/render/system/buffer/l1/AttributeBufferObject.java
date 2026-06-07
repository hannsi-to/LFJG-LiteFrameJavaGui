package me.hannsi.lfjg.render.system.buffer.l1;

import me.hannsi.lfjg.render.system.buffer.BufferObjectType;
import me.hannsi.lfjg.testRender.system.mesh.persistent.VertexBufferObject;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL45.*;

public class AttributeBufferObject {
    public static void attributeDrawIndirectBufferObject(AttributeBufferObjectType attributeBufferObjectType,VertexArrayObject vertexArrayObject,BufferObject bufferObject){

    }

    public static void attributeIndexBufferObject(AttributeBufferObjectType attributeBufferObjectType, VertexArrayObject vertexArrayObject,BufferObject bufferObject,int indexCount) {
        if(!attributeBufferObjectType.isSupported()) {
            attributeBufferObjectType.throwUnsupportedGPUDriver();
        }

        switch (attributeBufferObjectType) {
            case LEGACY -> {
                glStateCache.bindVertexArray(vertexArrayObject.getId());
                glStateCache.bindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferObject.getId());
            }
            case DIRECT_STATE_ACCESS -> {
                glVertexArrayElementBuffer(vertexArrayObject.getId(), bufferObject.getId());
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + attributeBufferObjectType);
        }

        bufferObject.addBufferObjectData(BufferObjectType.ELEMENT_BUFFER_OBJECT, ((long) Integer.BYTES * (long) indexCount));
    }

    public static void attributeVertexBufferObject(AttributeBufferObjectType attributeBufferObjectType, VertexArrayObject vertexArrayObject, BufferObject bufferObject, VertexLayout vertexLayout, int vertexCount) {
        if(!attributeBufferObjectType.isSupported()){
            attributeBufferObjectType.throwUnsupportedGPUDriver();
        }

        long baseOffset = bufferObject.getOffset();
        long totalVBOByteSize = 0;

        switch (attributeBufferObjectType) {
            case LEGACY -> {
                glStateCache.bindVertexArray(vertexArrayObject.getId());

                for (int i = 0; i < vertexLayout.attributeIndices.length; i++) {
                    int attributeIndex = vertexLayout.attributeIndices[i];
                    int attributeSize = vertexLayout.attributeSizes[i];
                    int attributeBinding = vertexLayout.attributeBindings[i];
                    int relativeOffset = vertexLayout.relativeOffsets[i];

                    long streamOffset = vertexLayout.streamOffsets[attributeBinding];
                    int stride = vertexLayout.streamStrides[attributeBinding];

                    long absolutePointer = baseOffset + streamOffset + relativeOffset;

                    long streamSize = (long) stride * vertexCount;
                    if (streamOffset + streamSize > totalVBOByteSize) {
                        totalVBOByteSize = streamOffset + streamSize;
                    }

                    glStateCache.bindBuffer(GL_ARRAY_BUFFER, bufferObject.getId());
                    glEnableVertexAttribArray(attributeIndex);
                    glVertexAttribPointer(attributeIndex, attributeSize, GL_FLOAT, false, stride, absolutePointer);
                }

                bufferObject.addBufferObjectData(BufferObjectType.VERTEX_BUFFER_OBJECT, totalVBOByteSize);
            }
            case DIRECT_STATE_ACCESS -> {
                for (int slot = 0; slot < vertexLayout.streamOffsets.length; slot++) {
                    long streamOffset = vertexLayout.streamOffsets[slot];
                    int stride = vertexLayout.streamStrides[slot];

                    long absoluteStreamOffset = baseOffset + streamOffset;

                    glVertexArrayVertexBuffer(vertexArrayObject.getId(), slot, bufferObject.getId(), absoluteStreamOffset, stride);

                    long streamSize = (long) stride * vertexCount;
                    if (streamOffset + streamSize > totalVBOByteSize) {
                        totalVBOByteSize = streamOffset + streamSize;
                    }
                }

                for (int i = 0; i < vertexLayout.attributeIndices.length; i++) {
                    int attributeIndex = vertexLayout.attributeIndices[i];
                    int attributeSize = vertexLayout.attributeSizes[i];
                    int attributeBinding = vertexLayout.attributeBindings[i];
                    int relativeOffset = vertexLayout.relativeOffsets[i];

                    glEnableVertexArrayAttrib(vertexArrayObject.getId(), attributeIndex);
                    glVertexArrayAttribFormat(vertexArrayObject.getId(), attributeIndex, attributeSize, GL_FLOAT, false, relativeOffset);
                    glVertexArrayAttribBinding(vertexArrayObject.getId(), attributeIndex, attributeBinding);
                }

                bufferObject.addBufferObjectData(BufferObjectType.VERTEX_BUFFER_OBJECT, totalVBOByteSize);
            }
            default -> throw new IllegalStateException("Unexpected value: " + attributeBufferObjectType);
        }
    }
}
