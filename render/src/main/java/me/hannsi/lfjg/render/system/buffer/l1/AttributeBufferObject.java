package me.hannsi.lfjg.render.system.buffer.l1;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL45.*;

public class AttributeBufferObject {
    public static void attributeBufferObject(AttributeBufferObjectType attributeBufferObjectType, VertexArrayObject vertexArrayObject, BufferObject bufferObject, VertexLayout vertexLayout, int bindingIndex) {
        if (!attributeBufferObjectType.isSupported()) {
            attributeBufferObjectType.throwUnsupportedGPUDriver();
        }

        switch (attributeBufferObjectType) {
            case LEGACY -> {
                switch (bufferObject.getBufferObjectType()) {
                    case VERTEX_BUFFER_OBJECT -> {
                        if (vertexLayout == null) {
                            throw new IllegalArgumentException("VertexLayout cannot be null when binding VERTEX_BUFFER_OBJECT.");
                        }

                        glStateCache.bindBuffer(GL_ARRAY_BUFFER, bufferObject.getId());

                        for (int i = 0; i < vertexLayout.attributeIndices.length; i++) {
                            int attribIndex = vertexLayout.attributeIndices[i];
                            int size = vertexLayout.attributeSizes[i];
                            int bindingSlot = vertexLayout.attributeBindings[i];
                            int relativeOffset = vertexLayout.relativeOffsets[i];

                            long streamOffset = vertexLayout.streamOffsets[bindingSlot];
                            int stride = vertexLayout.streamStrides[bindingSlot];

                            long absolutePointer = streamOffset + relativeOffset;

                            glEnableVertexAttribArray(attribIndex);
                            glVertexAttribPointer(attribIndex, size, GL_FLOAT, false, stride, absolutePointer);
                        }
                    }
                    case ELEMENT_BUFFER_OBJECT -> {
                        glStateCache.bindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferObject.getId());
                    }
                    case DRAW_INDIRECT_BUFFER_OBJECT -> {
                        glStateCache.bindBuffer(GL_DRAW_INDIRECT_BUFFER, bufferObject.getId());
                    }
                    case UNIFORM_BUFFER_OBJECT -> {
                        glStateCache.bindBuffer(GL_UNIFORM_BUFFER, bufferObject.getId());
                        glBindBufferBase(GL_UNIFORM_BUFFER, bindingIndex, bufferObject.getId());
                    }
                    case SHADER_STORAGE_BUFFER_OBJECT -> {
                        glStateCache.bindBuffer(GL_SHADER_STORAGE_BUFFER, bufferObject.getId());
                        glBindBufferBase(GL_SHADER_STORAGE_BUFFER, bindingIndex, bufferObject.getId());
                    }
                    default ->
                            throw new IllegalStateException("Unexpected value: " + bufferObject.getBufferObjectType());
                }
            }
            case DIRECT_STATE_ACCESS -> {
                switch (bufferObject.getBufferObjectType()) {
                    case VERTEX_BUFFER_OBJECT -> {
                        if (vertexLayout == null) {
                            throw new IllegalArgumentException("VertexLayout cannot be null when binding VERTEX_BUFFER_OBJECT.");
                        }

                        for (int slot = 0; slot < vertexLayout.streamOffsets.length; slot++) {
                            long offset = vertexLayout.streamOffsets[slot];
                            int stride = vertexLayout.streamStrides[slot];

                            glVertexArrayVertexBuffer(vertexArrayObject.getId(), slot, bufferObject.getId(), offset, stride);
                        }

                        for (int i = 0; i < vertexLayout.attributeIndices.length; i++) {
                            int attribIndex = vertexLayout.attributeIndices[i];
                            int size = vertexLayout.attributeSizes[i];
                            int bindingSlot = vertexLayout.attributeBindings[i];
                            int relativeOffset = vertexLayout.relativeOffsets[i];

                            glEnableVertexArrayAttrib(vertexArrayObject.getId(), attribIndex);
                            glVertexArrayAttribFormat(vertexArrayObject.getId(), attribIndex, size, GL_FLOAT, false, relativeOffset);
                            glVertexArrayAttribBinding(vertexArrayObject.getId(), attribIndex, bindingSlot);
                        }
                    }
                    case ELEMENT_BUFFER_OBJECT -> {
                        glVertexArrayElementBuffer(vertexArrayObject.getId(), bufferObject.getId());
                    }
                    case DRAW_INDIRECT_BUFFER_OBJECT -> {
                        glStateCache.bindBuffer(GL_DRAW_INDIRECT_BUFFER, bufferObject.getId());
                    }
                    case UNIFORM_BUFFER_OBJECT -> {
                        glBindBufferBase(GL_UNIFORM_BUFFER, bindingIndex, bufferObject.getId());
                    }
                    case SHADER_STORAGE_BUFFER_OBJECT -> {
                        glBindBufferBase(GL_SHADER_STORAGE_BUFFER, bindingIndex, bufferObject.getId());
                    }
                    default ->
                            throw new IllegalStateException("Unexpected value: " + bufferObject.getBufferObjectType());
                }
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + attributeBufferObjectType);
        }
    }
}
