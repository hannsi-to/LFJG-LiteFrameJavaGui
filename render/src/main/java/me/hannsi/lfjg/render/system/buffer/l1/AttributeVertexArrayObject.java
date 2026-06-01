package me.hannsi.lfjg.render.system.buffer.l1;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL45.glEnableVertexArrayAttrib;

public class AttributeVertexArrayObject {
    public static void enableAttributeVertexArrayObject(AttributeVertexArrayObjectType attributeVertexArrayObjectType, VertexArrayObject vertexArrayObject, int location) {
        if (!attributeVertexArrayObjectType.isSupported()) {
            attributeVertexArrayObjectType.throwUnsupportedGPUDriver();
        }

        switch (attributeVertexArrayObjectType) {
            case LEGACY -> {
                vertexArrayObject.generationStateCheck(GenerationVertexArrayObjectType.LEGACY, attributeVertexArrayObjectType);
                glEnableVertexAttribArray(location);
            }
            case DIRECT_STATE_ACCESS -> {
                vertexArrayObject.generationStateCheck(GenerationVertexArrayObjectType.DIRECT_STATE_ACCESS, attributeVertexArrayObjectType);
                glEnableVertexArrayAttrib(vertexArrayObject.getId(), location);
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + attributeVertexArrayObjectType);
        }
    }
}
