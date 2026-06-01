package me.hannsi.lfjg.render.system.buffer.l1;

import me.hannsi.lfjg.render.util.IEnumTypeSupportedBase;

public class VertexArrayObject {
    private final int id;
    private final GenerationVertexArrayObjectType generationVertexArrayObjectType;

    public VertexArrayObject(int id, GenerationVertexArrayObjectType generationVertexArrayObjectType) {
        this.id = id;
        this.generationVertexArrayObjectType = generationVertexArrayObjectType;
    }

    public void generationStateCheck(GenerationVertexArrayObjectType needGenerationBufferObjectType, IEnumTypeSupportedBase iEnumTypeSupportedBase) {
        if (needGenerationBufferObjectType != generationVertexArrayObjectType) {
            throw new IllegalStateException(generationVertexArrayObjectType.getName() + " for the buffer object created with " + iEnumTypeSupportedBase.getName() + " cannot be used.");
        }
    }

    public int getId() {
        return id;
    }

    public GenerationVertexArrayObjectType getGenerationVertexArrayObjectType() {
        return generationVertexArrayObjectType;
    }
}
