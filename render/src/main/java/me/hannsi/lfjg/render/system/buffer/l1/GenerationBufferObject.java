package me.hannsi.lfjg.render.system.buffer.l1;

import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

public class GenerationBufferObject {
    public static BufferObject generationBufferObject(GenerationBufferObjectType generationBufferObjectType) {
        BufferObject bufferObject;
        if (!generationBufferObjectType.isSupported()) {
            generationBufferObjectType.throwUnsupportedGPUDriver();
        }
        switch (generationBufferObjectType) {
            case LEGACY -> {
                bufferObject = new BufferObject(glGenBuffers(), generationBufferObjectType);
            }
            case DIRECT_STATE_ACCESS -> {
                bufferObject = new BufferObject(glCreateBuffers(), generationBufferObjectType);
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + generationBufferObjectType);
        }

        return bufferObject;
    }
}
