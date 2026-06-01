package me.hannsi.lfjg.render.system.buffer.l1;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;

public class BindVertexArrayObject {
    public static void bindVertexArrayObject(BindVertexArrayObjectType bindVertexArrayObjectType, VertexArrayObject vertexArrayObject) {
        if (!bindVertexArrayObjectType.isSupported()) {
            bindVertexArrayObjectType.throwUnsupportedGPUDriver();
        }

        switch (bindVertexArrayObjectType) {
            case LEGACY -> {
                vertexArrayObject.generationStateCheck(GenerationVertexArrayObjectType.LEGACY, bindVertexArrayObjectType);
                glStateCache.bindVertexArray(vertexArrayObject.getId());
            }
            case DIRECT_STATE_ACCESS -> {
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + bindVertexArrayObjectType);
        }
    }
}
