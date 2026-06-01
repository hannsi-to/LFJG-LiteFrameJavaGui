package me.hannsi.lfjg.render.system.buffer.l1;

import me.hannsi.lfjg.render.system.buffer.BufferObjectType;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;

public class BindBufferObject {
    public static void bindBufferObject(BindBufferObjectType bindBufferObjectType, BufferObject bufferObject, BufferObjectType bufferObjectType) {
        if (!bindBufferObjectType.isSupported()) {
            bindBufferObjectType.throwUnsupportedGPUDriver();
        }

        switch (bindBufferObjectType) {
            case LEGACY -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.LEGACY, bindBufferObjectType);
                glStateCache.bindBuffer(bufferObjectType.getId(), bufferObject.getId());
                bufferObject.bindBufferObject(bufferObjectType);
            }
            case DIRECT_STATE_ACCESS -> {
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + bindBufferObjectType);
        }
    }
}
