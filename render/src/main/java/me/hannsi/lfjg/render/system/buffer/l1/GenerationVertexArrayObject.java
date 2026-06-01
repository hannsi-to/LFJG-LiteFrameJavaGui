package me.hannsi.lfjg.render.system.buffer.l1;

import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL45.glCreateVertexArrays;

public class GenerationVertexArrayObject {
    public static VertexArrayObject generationBufferObject(GenerationVertexArrayObjectType generationVertexArrayObjectType) {
        VertexArrayObject vertexArrayObject;
        if (!generationVertexArrayObjectType.isSupported()) {
            generationVertexArrayObjectType.throwUnsupportedGPUDriver();
        }
        switch (generationVertexArrayObjectType) {
            case LEGACY -> {
                vertexArrayObject = new VertexArrayObject(glGenVertexArrays(), generationVertexArrayObjectType);
            }
            case DIRECT_STATE_ACCESS -> {
                vertexArrayObject = new VertexArrayObject(glCreateVertexArrays(), generationVertexArrayObjectType);
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + generationVertexArrayObjectType);
        }

        return vertexArrayObject;
    }
}
