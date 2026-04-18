package me.hannsi.lfjg.testRender.uitl.id;

import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.Cleanup;
import me.hannsi.lfjg.testRender.debug.exceptions.render.mesh.MeshException;
import me.hannsi.lfjg.testRender.system.mesh.MeshBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import static me.hannsi.lfjg.testRender.LFJGRenderContext.mesh;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD;

public class GLObjectPool implements Cleanup {
    private final Map<Integer, MeshBuilder> objects;
    private final Map<Integer, MeshBuilder> deletedObjects;
    private final IdPool idPool;
    private long totalPoolBytes = 0;
    private long deletedBytes = 0;

    public GLObjectPool() {
        this.objects = new LinkedHashMap<>();
        this.deletedObjects = new LinkedHashMap<>();
        this.idPool = new IdPool();
    }

    public int createId(MeshBuilder meshBuilder) {
        int id = idPool.acquire();
        objects.put(id, meshBuilder);

        totalPoolBytes += meshBuilder.getBytes();

        return id;
    }

    public int createObject(int requestedId, MeshBuilder meshBuilder) {
        int id = idPool.acquire(requestedId);
        objects.put(id, meshBuilder);

        totalPoolBytes += meshBuilder.getBytes();

        return id;
    }

    public void createDeletedObject(int id) {
        MeshBuilder meshBuilder = getBuilder(id);

        deletedObjects.put(id, meshBuilder);
        deletedBytes += meshBuilder.getBytes();

        if (totalPoolBytes > 0 && (float) deletedBytes / totalPoolBytes > GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD) {
            mesh.directDeleteObjects();
            clearObjects();
            clearDeletedObjects();
        }
    }

    public void restoreDeletedObject(int id) {
        MeshBuilder meshBuilder = deletedObjects.get(id);
        deletedObjects.remove(id);

        deletedBytes -= meshBuilder.getBytes();
    }

    private void clearObjects() {
        for (Map.Entry<Integer, MeshBuilder> entry : objects.entrySet()) {
            idPool.release(entry.getKey());
        }

        objects.clear();
        totalPoolBytes = 0;
    }

    private void clearDeletedObjects() {
        deletedObjects.clear();
        deletedBytes = 0;
    }

    public Map<Integer, MeshBuilder> getObjects() {
        return objects;
    }

    public Map<Integer, MeshBuilder> getDeletedObjects() {
        return deletedObjects;
    }

    public MeshBuilder getBuilder(int id) {
        return getBuilder(id, true);
    }

    public MeshBuilder getBuilder(int id, boolean exception) {
        MeshBuilder meshBuilder = getObjects().get(id);
        if (meshBuilder == null) {
            if (exception) {
                throw new MeshException("This object ID does not exist. objectId: " + id);
            }
        }

        return meshBuilder;
    }

    public MeshBuilder getDeletedBuilder(int id) {
        return getDeletedBuilder(id, true);
    }

    public MeshBuilder getDeletedBuilder(int id, boolean exception) {
        MeshBuilder meshBuilder = getDeletedObjects().get(id);
        if (meshBuilder == null) {
            if (exception) {
                throw new MeshException("This object ID does not exist. objectId: " + id);
            }
        }

        return meshBuilder;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (Map.Entry<Integer, MeshBuilder> entry : objects.entrySet()) {
            if (count != 0) {
                stringBuilder.append("\n");
            }

            stringBuilder.append("objects{id: ").append(entry.getKey()).append(", ").append(entry.getValue()).append("}");

            count++;
        }

        for (Map.Entry<Integer, MeshBuilder> entry : deletedObjects.entrySet()) {
            stringBuilder.append("\n").append("deletedObjects{id: ").append(entry.getKey()).append(", ").append(entry.getValue()).append("}");

            count++;
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean cleanup(CleanupEvent event) {
        CleanupEvent.CleanupData datum = new CleanupEvent.CleanupData(this.getClass());
        for (Map.Entry<Integer, MeshBuilder> entry : objects.entrySet()) {
            datum.addData(entry.getKey().toString(), entry.getValue().cleanup(event));
        }
        objects.clear();

        return event.debug(this.getClass(), datum);
    }
}
