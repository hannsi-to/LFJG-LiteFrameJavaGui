package me.hannsi.lfjg.render;

import me.hannsi.lfjg.render.system.mesh.GLObjectData;

import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.idPool;

public class GLObjectPool {
    private final Map<Long, GLObjectData> objects;

    public GLObjectPool() {
        this.objects = new HashMap<>();
    }

    public long create(GLObjectData glObjectData) {
        long id = idPool.acquire();
        objects.put(id, glObjectData);

        return id;
    }

    public void destroy(long id) {
        objects.remove(id);
        idPool.release(id);
    }

    public Map<Long, GLObjectData> getObjects() {
        return objects;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (Map.Entry<Long, GLObjectData> entry : objects.entrySet()) {
            if (count != 0) {
                stringBuilder.append("\n");
            }

            stringBuilder.append("GLObjectPool{id: ").append(entry.getKey()).append(", ").append(entry.getValue()).append("}");

            count++;
        }

        return stringBuilder.toString();
    }
}
