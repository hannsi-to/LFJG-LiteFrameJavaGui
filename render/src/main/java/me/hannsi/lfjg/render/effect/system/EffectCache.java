package me.hannsi.lfjg.render.effect.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;

import java.util.LinkedHashMap;
import java.util.Map;


public class EffectCache {
    private LinkedHashMap<String, EffectBase> effectBases;

    EffectCache() {
        this.effectBases = new LinkedHashMap<>();
    }

    public static EffectCache createEffectCache() {
        return new EffectCache();
    }


//    public EffectCache attachGLObjectCache(GLObjectCache glObjectCache) {
//
//        return this;
//    }
//
//    public EffectCache attachGLObject(GLObject glObject) {
//        return this;
//    }

    public EffectCache createCache(EffectBase effectBase) {
        this.effectBases.put(effectBase.getName(), effectBase);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getName(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);

        return this;
    }

    public EffectCache createCache(String name, EffectBase effectBase, int index) {
        LinkedHashMap<String, EffectBase> newEffectCache = new LinkedHashMap<>();

        int i = 0;
        for (Map.Entry<String, EffectBase> effectBaseIdentifierEntry : effectBases.entrySet()) {
            if (i == index) {
                newEffectCache.put(name, effectBase);
            }

            newEffectCache.put(effectBaseIdentifierEntry.getKey(), effectBaseIdentifierEntry.getValue());

            i++;
        }

        this.effectBases = newEffectCache;

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getName(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);

        return this;
    }

    public void cleanup() {

    }
}