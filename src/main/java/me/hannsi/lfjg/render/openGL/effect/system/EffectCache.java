package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;

import java.util.*;

public class EffectCache {
    private LinkedHashMap<EffectBase, Long> effectBases;

    public EffectCache() {
        this.effectBases = new LinkedHashMap<>();
    }

    private static <K, V> LinkedHashMap<K, V> setHashMap(LinkedHashMap<K, V> originalMap, V value) {
        LinkedHashMap<K, V> resultLinkedHashMap = new LinkedHashMap<>();

        for (Map.Entry<K, V> entry : originalMap.entrySet()) {
            if (entry.getValue() == value) {
                resultLinkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }

        return resultLinkedHashMap;
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
        this.effectBases.put(effectBase, glObject.getObjectId());
    }

    public void push(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            if (glObject.getObjectId() != effectBase.getValue()) {
                continue;
            }

            effectBase.getKey().push(glObject);
        }
    }

    public void pop(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            if (glObject.getObjectId() != effectBase.getValue()) {
                continue;
            }

            effectBase.getKey().pop(glObject);
        }
    }

    public void frameBufferPush(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            if (glObject.getObjectId() != effectBase.getValue()) {
                continue;
            }

            effectBase.getKey().frameBufferPush(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    public void frameBufferPop(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            if (glObject.getObjectId() != effectBase.getValue()) {
                continue;
            }

            effectBase.getKey().frameBufferPop(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    public void frameBuffer(GLObject glObject) {
        int index = 0;

        LinkedHashMap<EffectBase, Long> onlyGlObjectEffectBases = setHashMap(effectBases, glObject.getObjectId());

        int maxSize = new ArrayList<>(onlyGlObjectEffectBases.entrySet()).size() - 1;

        for (Map.Entry<EffectBase, Long> effectBase : onlyGlObjectEffectBases.entrySet()) {
            if (glObject.getObjectId() != effectBase.getValue()) {
                continue;
            }

            if (index == maxSize) {
                effectBase.getKey().setUniform(glObject);
                effectBase.getKey().frameBuffer(glObject);
            } else {
                Map.Entry<EffectBase, Long> nextEffectBase = getLinkedHashMapEntry(onlyGlObjectEffectBases, index + 1);

                nextEffectBase.getKey().frameBufferPush(glObject);
                effectBase.getKey().setUniform(glObject);
                effectBase.getKey().frameBuffer(glObject);
                nextEffectBase.getKey().frameBufferPop(glObject);
            }

            index++;
        }
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
