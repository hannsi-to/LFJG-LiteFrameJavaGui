package me.hannsi.lfjg.render.system.mesh.persistent;

import java.nio.ByteBuffer;

public class PersistentMappedUBO implements PersistentMappedBuffer {
    private ByteBuffer mappedBuffer;
    private int bufferID;

    @Override
    public void cleanup() {

    }
}
