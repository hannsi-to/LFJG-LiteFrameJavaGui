package me.hannsi.lfjg.render.effect.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;

public class EffectBase {
    private final String name;

    public EffectBase(String name) {
        this.name = name;
    }

    public void create(GLObject glObject) {

    }

    public void cleanup() {
        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                name,
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public String getName() {
        return name;
    }
}