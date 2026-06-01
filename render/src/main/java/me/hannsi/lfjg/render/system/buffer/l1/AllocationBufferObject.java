package me.hannsi.lfjg.render.system.buffer.l1;

import static org.lwjgl.opengl.GL15.nglBufferData;
import static org.lwjgl.opengl.GL44.nglBufferStorage;
import static org.lwjgl.opengl.GL45.nglNamedBufferData;
import static org.lwjgl.opengl.GL45.nglNamedBufferStorage;

public class AllocationBufferObject {
    public static void allocationBufferObject(AllocationBufferObjectType allocationBufferObjectType, BufferObject bufferObject, long size, int flags) {
        if (!allocationBufferObjectType.isSupported()) {
            allocationBufferObjectType.throwUnsupportedGPUDriver();
        }

        bufferObject.setFlags(flags);
        switch (allocationBufferObjectType) {
            case LEGACY -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.LEGACY, allocationBufferObjectType);
                nglBufferData(bufferObject.getBufferObjectType().getId(), size, 0L, bufferObject.getFlags());
            }
            case DIRECT_STATE_ACCESS -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.DIRECT_STATE_ACCESS, allocationBufferObjectType);
                nglNamedBufferData(bufferObject.getId(), size, 0L, bufferObject.getFlags());
            }
            case STORAGE -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.LEGACY, allocationBufferObjectType);
                nglBufferStorage(bufferObject.getBufferObjectType().getId(), size, 0L, bufferObject.getFlags());
            }
            case DIRECT_STATE_ACCESS_STORAGE -> {
                bufferObject.generationStateCheck(GenerationBufferObjectType.DIRECT_STATE_ACCESS, allocationBufferObjectType);
                nglNamedBufferStorage(bufferObject.getId(), size, 0L, bufferObject.getFlags());
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + allocationBufferObjectType);
        }
    }
}
