package me.hannsi.lfjg.render.uitl.id;

import me.hannsi.lfjg.render.debug.exceptions.render.mesh.MeshException;
import me.hannsi.lfjg.render.system.mesh.TestMesh;

import java.util.LinkedHashMap;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.mesh;
import static me.hannsi.lfjg.render.RenderSystemSetting.GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD;

public class GLObjectPool {
    private final Map<Integer, TestMesh.Builder> objects;
    private final Map<Integer, TestMesh.Builder> deletedObjects;
    private final IdPool idPool;
    private long totalPoolBytes = 0;
    private long deletedBytes = 0;

    public GLObjectPool() {
        this.objects = new LinkedHashMap<>();
        this.deletedObjects = new LinkedHashMap<>();
        this.idPool = new IdPool();
    }

    public int createId(TestMesh.Builder builder) {
        int id = idPool.acquire();
        objects.put(id, builder);

        totalPoolBytes += builder.getBytes();

        return id;
    }

    public int createObject(int requestedId, TestMesh.Builder builder) {
        int id = idPool.acquire(requestedId);
        objects.put(id, builder);

        totalPoolBytes += builder.getBytes();

        return id;
    }

    public void createDeletedObject(int id) {
        TestMesh.Builder builder = getBuilder(id);

        deletedObjects.put(id, builder);
        deletedBytes += builder.getBytes();

        if (totalPoolBytes > 0 && (float) deletedBytes / totalPoolBytes > GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD) {
            mesh.directDeleteObjects();
            clearObjects();
            clearDeletedObjects();
        }
    }

    public void restoreDeletedObject(int id) {
        TestMesh.Builder builder = deletedObjects.get(id);
        deletedObjects.remove(id);

        deletedBytes -= builder.getBytes();
    }

    private void clearObjects() {
        for (Map.Entry<Integer, TestMesh.Builder> entry : objects.entrySet()) {
            idPool.release(entry.getKey());
        }

        objects.clear();
        totalPoolBytes = 0;
    }

    private void clearDeletedObjects() {
        deletedObjects.clear();
        deletedBytes = 0;
    }

    public Map<Integer, TestMesh.Builder> getObjects() {
        return objects;
    }

    public Map<Integer, TestMesh.Builder> getDeletedObjects() {
        return deletedObjects;
    }

    public TestMesh.Builder getBuilder(int id) {
        TestMesh.Builder builder = getObjects().get(id);
        if (builder == null) {
            throw new MeshException("This object ID does not exist. objectId: " + id);
        }

        return builder;
    }

    public TestMesh.Builder getDeletedBuilder(int id) {
        return getDeletedObjects().get(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (Map.Entry<Integer, TestMesh.Builder> entry : objects.entrySet()) {
            if (count != 0) {
                stringBuilder.append("\n");
            }

            stringBuilder.append("objects{id: ").append(entry.getKey()).append(", ").append(entry.getValue()).append("}");

            count++;
        }

        for (Map.Entry<Integer, TestMesh.Builder> entry : deletedObjects.entrySet()) {
            stringBuilder.append("\n").append("deletedObjects{id: ").append(entry.getKey()).append(", ").append(entry.getValue()).append("}");

            count++;
        }

        return stringBuilder.toString();
    }
}
