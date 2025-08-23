package me.hannsi.lfjg.render.effect.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.Id;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;

import java.util.*;

public class EffectCache {
    private LinkedHashMap<EffectBase, Identifier> effectBases;
    private FrameBuffer endFrameBuffer;

    EffectCache() {
        this.effectBases = new LinkedHashMap<>();

        this.endFrameBuffer = new FrameBuffer();
        this.endFrameBuffer.createFrameBuffer();
        this.endFrameBuffer.createShaderProgram();
    }

    public static EffectCache initEffectCache() {
        return new EffectCache();
    }

    private static <K, V> Map.Entry<K, V> getLinkedHashMapEntry(LinkedHashMap<K, V> originalMap, int index) {
        List<Map.Entry<K, V>> entryList = new ArrayList<>(originalMap.entrySet());
        return entryList.get(index);
    }

    private static <K, V> LinkedHashMap<K, V> reverseLinkedHashMap(LinkedHashMap<K, V> originalMap) {
        List<Map.Entry<K, V>> entryList = new ArrayList<>(originalMap.entrySet());
        Collections.reverse(entryList);

        LinkedHashMap<K, V> reversedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : entryList) {
            reversedMap.put(entry.getKey(), entry.getValue());
        }

        return reversedMap;
    }

    public EffectCache create(GLObject glObject) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().create(glObject);
        }

        glObject.setEffectCache(this);

        return this;
    }

    public EffectCache create(FrameBuffer frameBuffer) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().create(frameBuffer);
        }

        return this;
    }

    public void updateFrameBufferSize(FrameBuffer frameBuffer) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().getFrameBuffer().setX(frameBuffer.getX());
            effectBase.getKey().getFrameBuffer().setY(frameBuffer.getY());
            effectBase.getKey().getFrameBuffer().setWidth(frameBuffer.getWidth());
            effectBase.getKey().getFrameBuffer().setHeight(frameBuffer.getHeight());
        }
    }

    public EffectCache createCache(String name, EffectBase effectBase) {
        this.effectBases.put(effectBase, new Identifier(name, Id.latestEffectCacheId++));

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getId(),
                ""
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public EffectCache createCache(String name, EffectBase effectBase, int index) {
        LinkedHashMap<EffectBase, Identifier> newEffectCache = new LinkedHashMap<>();

        int i = 0;
        for (Map.Entry<EffectBase, Identifier> effectBaseIdentifierEntry : effectBases.entrySet()) {
            if (i == index) {
                newEffectCache.put(effectBase, new Identifier(name, Id.latestEffectCacheId++));
            }

            newEffectCache.put(effectBaseIdentifierEntry.getKey(), effectBaseIdentifierEntry.getValue());

            i++;
        }

        this.effectBases = newEffectCache;

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getId(),
                ""
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public void push(GLObject glObject) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().push(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    public void pop(GLObject glObject) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().pop(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    public void frameBufferPush(GLObject glObject) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().frameBufferPush(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    public void frameBufferPop(GLObject glObject) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().frameBufferPop(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    public void frameBuffer(GLObject glObject) {
        int index = 0;
        int maxSize = new ArrayList<>(effectBases.entrySet()).size() - 1;

        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            applyEffect(index, maxSize, effectBase.getKey(), glObject);
            index++;
        }
    }

    public void applyEffect(int index, int maxSize, EffectBase effectBase, GLObject glObject) {
        if (index == maxSize) {
            effectBase.getFrameBuffer().getShaderProgramFBO().bind();
            effectBase.setUniform(glObject);
            effectBase.getFrameBuffer().getShaderProgramFBO().unbind();

            endFrameBuffer.bindFrameBuffer();
            effectBase.frameBuffer(glObject);
            endFrameBuffer.unbindFrameBuffer();
        } else {
            Map.Entry<EffectBase, Identifier> nextEffectBase = getLinkedHashMapEntry(effectBases, index + 1);

            effectBase.getFrameBuffer().getShaderProgramFBO().bind();
            effectBase.setUniform(glObject);
            effectBase.getFrameBuffer().getShaderProgramFBO().unbind();

            nextEffectBase.getKey().frameBufferPush(glObject);
            effectBase.frameBuffer(glObject);
            nextEffectBase.getKey().frameBufferPop(glObject);
        }

    }

    public EffectBase getEffectBase(int index) {
        List<EffectBase> keys = new ArrayList<>(effectBases.keySet());
        return keys.get(index);
    }

    public EffectBase getEffectBase(String name) {
        return effectBases.entrySet().stream().filter(entry -> entry.getValue().name().equals(name)).findFirst().map(Map.Entry::getKey).orElse(null);
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        for (Map.Entry<EffectBase, Identifier> entry : effectBases.entrySet()) {
            EffectBase effectBase = entry.getKey();
            Identifier id = entry.getValue();
            effectBase.cleanup();
            ids.append(id).append(", ");
        }
        endFrameBuffer.cleanup();
        effectBases.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.length() == 0 ? "" : ids.substring(0, ids.length() - 2),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public LinkedHashMap<EffectBase, Identifier> getEffectBases() {
        return effectBases;
    }

    public void setEffectBases(LinkedHashMap<EffectBase, Identifier> effectBases) {
        this.effectBases = effectBases;
    }

    public FrameBuffer getEndFrameBuffer() {
        return endFrameBuffer;
    }

    public void setEndFrameBuffer(FrameBuffer endFrameBuffer) {
        this.endFrameBuffer = endFrameBuffer;
    }

    public final class Identifier {
        private final String name;
        private final long id;

        public Identifier(String name, long id) {
            this.name = name;
            this.id = id;
        }

        public String name() {
            return name;
        }

        public long id() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            Identifier that = (Identifier) obj;
            return Objects.equals(this.name, that.name) &&
                    this.id == that.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, id);
        }

        @Override
        public String toString() {
            return "Identifier[" +
                    "name=" + name + ", " +
                    "id=" + id + ']';
        }

    }
}