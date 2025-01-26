package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.rendering.Id;

import java.util.*;

public class EffectCache {
    public static long latestEffectCacheId;
    private LinkedHashMap<EffectBase, Long> effectBases;

    public EffectCache() {
        this.effectBases = new LinkedHashMap<>();
        latestEffectCacheId = Id.initialEffectCacheId;
    }

    private static <K, V> LinkedHashMap<K, V> setHashMap(LinkedHashMap<K, V> originalMap, V value) {

        return new LinkedHashMap<>(originalMap);
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

    public void createCache(EffectBase effectBase, GLObject glObject) {
        effectBase.getFrameBuffer().setUesStencil(true);
        effectBase.getFrameBuffer().setGlObject(glObject);
        this.effectBases.put(effectBase, latestEffectCacheId++);

        String logMessage = "\n---------- EffectCache Debug Message ----------" + "\n\tSource: EffectCache" + "\n\tType: Cache Creation" + "\n\tID: " + glObject.getObjectId() + "\n\tSeverity: Info" + "\n\tMessage: Create effect cache: " + effectBase.getName() + " | Object name: " + glObject.getName() + "\n------------------------------------------\n";

        DebugLog.debug(getClass(), logMessage);
    }

    public void push(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            effectBase.getKey().push(glObject);
        }
    }

    public void pop(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            effectBase.getKey().pop(glObject);
        }
    }

    public void frameBufferPush(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            effectBase.getKey().frameBufferPush(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    public void frameBufferPop(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            effectBase.getKey().frameBufferPop(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    public void frameBuffer(GLObject glObject) {
        int index = 0;

        int maxSize = new ArrayList<>(effectBases.entrySet()).size() - 1;

        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            if (index == maxSize) {
                effectBase.getKey().setUniform(glObject);
                effectBase.getKey().frameBuffer(glObject);
            } else {
                Map.Entry<EffectBase, Long> nextEffectBase = getLinkedHashMapEntry(effectBases, index + 1);

                nextEffectBase.getKey().frameBufferPush(glObject);
                effectBase.getKey().setUniform(glObject);
                effectBase.getKey().frameBuffer(glObject);
                nextEffectBase.getKey().frameBufferPop(glObject);
            }

            index++;
        }
    }

    public EffectBase getEffectBase(int index) {
        List<EffectBase> keys = new ArrayList<>(effectBases.keySet());

        return keys.get(index);
    }

    public void cleanup(long objectId) {
        effectBases.clear();
    }

    public LinkedHashMap<EffectBase, Long> getEffectBases() {
        return effectBases;
    }

    public void setEffectBases(LinkedHashMap<EffectBase, Long> effectBases) {
        this.effectBases = effectBases;
    }
}
