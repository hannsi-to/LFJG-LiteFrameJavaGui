package me.hannsi.lfjg.render;

import me.hannsi.lfjg.render.system.mesh.GLObjectData;

import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.idPool;

public class GLObjectPool {
    private final Map<Long, GLObjectData> objects;
    private final Map<Long, GLObjectData> deletedObjects;

    public GLObjectPool() {
        this.objects = new HashMap<>();
        this.deletedObjects = new HashMap<>();
    }

    public long createObject(GLObjectData glObjectData) {
        long id = idPool.acquire();
        objects.put(id, glObjectData);

        return id;
    }

    public void destroyObject(long id) {
        objects.remove(id);
        idPool.release(id);
    }

    public void createDeletedObject(long id, GLObjectData glObjectData) {
        deletedObjects.put(id, glObjectData);
    }

    public void destroyDeletedObject(long id) {
        deletedObjects.remove(id);
    }

    public Map<Long, GLObjectData> getObjects() {
        return objects;
    }

    public Map<Long, GLObjectData> getDeletedObjects() {
        return deletedObjects;
    }

    public GLObjectData getObjectData(long id) {
        return getObjects().get(id);
    }

    public GLObjectData getDeletedObjectData(long id) {
        return getDeletedObjects().get(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (Map.Entry<Long, GLObjectData> entry : objects.entrySet()) {
            if (count != 0) {
                stringBuilder.append("\n");
            }

            stringBuilder.append("objects{id: ").append(entry.getKey()).append(", ").append(entry.getValue()).append("}");

            count++;
        }

        for (Map.Entry<Long, GLObjectData> entry : deletedObjects.entrySet()) {
            stringBuilder.append("\n").append("deletedObjects{id: ").append(entry.getKey()).append(", ").append(entry.getValue()).append("}");

            count++;
        }

        return stringBuilder.toString();
    }
}
