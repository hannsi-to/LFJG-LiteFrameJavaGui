package me.hannsi.lfjg.render.effect.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;

import java.util.ArrayList;
import java.util.List;


public class EffectCache {
    private final List<EffectBase> effectBases;

    EffectCache() {
        this.effectBases = new ArrayList<>();
    }

    public static EffectCache createEffectCache() {
        return new EffectCache();
    }

    public EffectCache createCache(EffectBase effectBase) {
        effectBases.add(effectBase);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getName(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);

        return this;
    }

    public EffectCache createCache(EffectBase effectBase, int index) {
        effectBases.add(index, effectBase);

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

    public List<EffectBase> getEffectBases() {
        return effectBases;
    }
}