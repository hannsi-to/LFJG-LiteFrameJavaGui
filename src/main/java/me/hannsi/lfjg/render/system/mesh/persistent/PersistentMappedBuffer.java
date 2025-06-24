package me.hannsi.lfjg.render.system.mesh.persistent;

public interface PersistentMappedBuffer {
    int getBufferId();

    void cleanup();
}
