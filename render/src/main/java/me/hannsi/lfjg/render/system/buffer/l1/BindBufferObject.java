package me.hannsi.lfjg.render.system.buffer.l1;

import me.hannsi.lfjg.render.system.buffer.BufferObjectType;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;

public class BindBufferObject {
    public static void bindBufferObject(BindBufferObjectType bindBufferObjectType, BufferObject bufferObject) {
        if (!bindBufferObjectType.isSupported()) {
            bindBufferObjectType.throwUnsupportedGPUDriver();
        }

        switch (bindBufferObjectType) {
            case LEGACY -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.LEGACY, bindBufferObjectType);
                glStateCache.bindBuffer(GL_ARRAY_BUFFER, bufferObject.getId());
            }
            case DIRECT_STATE_ACCESS -> {
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + bindBufferObjectType);
        }
    }
}
