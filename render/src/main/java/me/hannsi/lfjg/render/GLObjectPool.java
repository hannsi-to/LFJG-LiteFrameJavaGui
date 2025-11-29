package me.hannsi.lfjg.render;

import me.hannsi.lfjg.render.system.mesh.DrawElementsIndirectCommand;
import me.hannsi.lfjg.render.system.mesh.GLObjectData;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;

import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.core.SystemSetting.GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD;
import static me.hannsi.lfjg.render.LFJGRenderContext.ID_POOL;
import static me.hannsi.lfjg.render.LFJGRenderContext.MESH;

public class GLObjectPool {
    private final Map<Long, GLObjectData> objects;
    private final Map<Long, GLObjectData> deletedObjects;
    private long totalPoolBytes = 0;
    private long deletedBytes = 0;

    public GLObjectPool() {
        this.objects = new HashMap<>();
        this.deletedObjects = new HashMap<>();
    }

    public long createObject(GLObjectData glObjectData) {
        long id = ID_POOL.acquire();
        objects.put(id, glObjectData);

        totalPoolBytes += calculateBytes(glObjectData);

        return id;
    }

    public long createObject(long requestedId, GLObjectData glObjectData) {
        long id = ID_POOL.acquire(requestedId);
        objects.put(id, glObjectData);

        totalPoolBytes += calculateBytes(glObjectData);

        return id;
    }

    public void destroyObject(long id) {
        GLObjectData glObjectData = objects.get(id);

        objects.remove(id);
        ID_POOL.release(id);

        totalPoolBytes -= calculateBytes(glObjectData);
    }

    public void createDeletedObject(long id, GLObjectData glObjectData) {
        deletedObjects.put(id, glObjectData);
        deletedBytes += calculateBytes(glObjectData);

        if (totalPoolBytes > 0 && (float) deletedBytes / totalPoolBytes > GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD) {
            MESH.directDeleteObjects();
        }
    }

    public void destroyDeletedObject(long id) {
        GLObjectData glObjectData = deletedObjects.get(id);
        deletedObjects.remove(id);

        deletedBytes -= calculateBytes(glObjectData);
    }

    public void clearObjects() {
        for (Map.Entry<Long, GLObjectData> entry : objects.entrySet()) {
            ID_POOL.release(entry.getKey());
        }

        objects.clear();
        totalPoolBytes = 0;
    }

    public void clearDeletedObjects() {
        deletedObjects.clear();
        deletedBytes = 0;
    }

    private long calculateBytes(GLObjectData data) {
        return (long) data.elementPair.vertices.length * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES +
                (long) data.elementPair.indices.length * Float.BYTES +
                DrawElementsIndirectCommand.BYTES;
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
