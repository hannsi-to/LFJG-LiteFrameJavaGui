package me.hannsi.lfjg.render.effect.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.Id;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;

import java.util.*;

public class EffectCache {
    private LinkedHashMap<String,EffectBase> effectBases;
    private GLObject glObject;
    private FrameBuffer currentFrameBuffer;

    EffectCache() {
        this.effectBases = new LinkedHashMap<>();
    }

    public static EffectCache createEffectCache() {
        return new EffectCache();
    }

    public EffectCache attachGLObject(GLObject glObject){
        this.glObject = glObject;
        this.glObject.setEffectCache(this);

        for (Map.Entry<String,EffectBase> effectBasesEntry : effectBases.entrySet()) {
            effectBasesEntry.getValue().create();
        }

        return this;
    }

    public EffectCache createCache(EffectBase effectBase) {
        this.effectBases.put(effectBase.getName(),effectBase);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getName(),
                ""
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public EffectCache createCache(String name, EffectBase effectBase, int index) {
        LinkedHashMap<String,EffectBase> newEffectCache = new LinkedHashMap<>();

        int i = 0;
        for (Map.Entry<String,EffectBase> effectBaseIdentifierEntry : effectBases.entrySet()) {
            if (i == index) {
                newEffectCache.put(name,effectBase);
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
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public void push(GLObject glObject) {
        for (Map.Entry<String,EffectBase> effectBasesEntry : effectBases.entrySet()) {
            effectBasesEntry.getValue().push(glObject);
        }
    }

    public void pop(GLObject glObject) {
        for (Map.Entry<String,EffectBase> effectBasesEntry : effectBases.entrySet()) {
            effectBasesEntry.getValue().pop(glObject);
        }
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        for (Map.Entry<String,EffectBase> entry : effectBases.entrySet()) {
            EffectBase effectBase = entry.getValue();
            String name = entry.getKey();
            effectBase.cleanup();
            ids.append(name).append(", ");
        }
        effectBases.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.length() == 0 ? "" : ids.substring(0, ids.length() - 2),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public EffectBase getEffectBase(String name) {
        return effectBases.get(name);
    }
}