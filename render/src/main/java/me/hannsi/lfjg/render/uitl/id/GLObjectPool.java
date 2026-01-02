package me.hannsi.lfjg.render.uitl.id;

import me.hannsi.lfjg.render.system.mesh.DrawElementsIndirectCommand;
import me.hannsi.lfjg.render.system.mesh.GLObjectData;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;

import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.mesh;
import static me.hannsi.lfjg.render.RenderSystemSetting.GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD;

public class GLObjectPool {
    private final Map<Integer, GLObjectData> objects;
    private final Map<Integer, GLObjectData> deletedObjects;
    private final IdPool idPool;
    private long totalPoolBytes = 0;
    private long deletedBytes = 0;

    public GLObjectPool() {
        this.objects = new HashMap<>();
        this.deletedObjects = new HashMap<>();
        this.idPool = new IdPool();
    }

    public int createId(GLObjectData glObjectData) {
        int id = idPool.acquire();
        objects.put(id, glObjectData);

        totalPoolBytes += calculateBytes(glObjectData);

        return id;
    }

    public int createObject(int requestedId, GLObjectData glObjectData) {
        int id = idPool.acquire(requestedId);
        objects.put(id, glObjectData);

        totalPoolBytes += calculateBytes(glObjectData);

        return id;
    }

    public void destroyObject(int id) {
        GLObjectData glObjectData = objects.get(id);

        objects.remove(id);
        idPool.release(id);

        totalPoolBytes -= calculateBytes(glObjectData);
    }

    public void createDeletedObject(int id, GLObjectData glObjectData) {
        deletedObjects.put(id, glObjectData);
        deletedBytes += calculateBytes(glObjectData);

        if (totalPoolBytes > 0 && (float) deletedBytes / totalPoolBytes > GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD) {
            mesh.directDeleteObjects();
        }
    }

    public void destroyDeletedObject(int id) {
        GLObjectData glObjectData = deletedObjects.get(id);
        deletedObjects.remove(id);

        deletedBytes -= calculateBytes(glObjectData);
    }

    public void clearObjects() {
        for (Map.Entry<Integer, GLObjectData> entry : objects.entrySet()) {
            idPool.release(entry.getKey());
        }

        objects.clear();
        totalPoolBytes = 0;
    }

    public void clearDeletedObjects() {
        deletedObjects.clear();
        deletedBytes = 0;
    }

    private long calculateBytes(GLObjectData data) {
        return (long) data.elementPair.vertices.length * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES + (long) data.elementPair.indices.length * Float.BYTES + DrawElementsIndirectCommand.BYTES;
    }

    public Map<Integer, GLObjectData> getObjects() {
        return objects;
    }

    public Map<Integer, GLObjectData> getDeletedObjects() {
        return deletedObjects;
    }

    public GLObjectData getObjectData(int id) {
        return getObjects().get(id);
    }

    public GLObjectData getDeletedObjectData(int id) {
        return getDeletedObjects().get(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (Map.Entry<Integer, GLObjectData> entry : objects.entrySet()) {
            if (count != 0) {
                stringBuilder.append("\n");
            }

            stringBuilder.append("objects{id: ").append(entry.getKey()).append(", ").append(entry.getValue()).append("}");

            count++;
        }

        for (Map.Entry<Integer, GLObjectData> entry : deletedObjects.entrySet()) {
            stringBuilder.append("\n").append("deletedObjects{id: ").append(entry.getKey()).append(", ").append(entry.getValue()).append("}");

            count++;
        }

        return stringBuilder.toString();
    }
}
