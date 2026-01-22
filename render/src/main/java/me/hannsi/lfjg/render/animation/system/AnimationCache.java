package me.hannsi.lfjg.render.animation.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnimationCache {
    private LinkedHashMap<String, AnimationBase> animationBases;
    private GLObject glObject;

    AnimationCache() {
        this.animationBases = new LinkedHashMap<>();
    }

    public static AnimationCache createAnimationCache() {
        return new AnimationCache();
    }

    public AnimationCache attachGLObject(GLObject glObject) {
//        glObject.setAnimationCache(this);
        this.glObject = glObject;

        return this;
    }

    public AnimationCache createCache(AnimationBase animationBase) {
        this.animationBases.put(animationBase.getName(), animationBase);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                animationBase.getName(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);

        return this;
    }

    public AnimationCache start() {
        for (Map.Entry<String, AnimationBase> animationBaseEntry : animationBases.entrySet()) {
            animationBaseEntry.getValue().start(glObject);
        }

        return this;
    }

    public AnimationCache end() {
        for (Map.Entry<String, AnimationBase> animationBaseEntry : animationBases.entrySet()) {
            animationBaseEntry.getValue().stop(glObject);
        }

        return this;
    }

    public AnimationCache loop() {
        for (Map.Entry<String, AnimationBase> animationBaseEntry : animationBases.entrySet()) {
            animationBaseEntry.getValue().systemLoop(glObject);
        }

        return this;
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();

        for (Map.Entry<String, AnimationBase> animationBaseEntry : animationBases.entrySet()) {
            animationBaseEntry.getValue().stop(glObject);
            animationBaseEntry.getValue().cleanup();
            ids.append(animationBaseEntry.getKey()).append(", ");
        }

        animationBases.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.substring(0, ids.length() - 2),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public LinkedHashMap<String, AnimationBase> getAnimationBases() {
        return animationBases;
    }

    public void setAnimationBases(LinkedHashMap<String, AnimationBase> animationBases) {
        this.animationBases = animationBases;
    }
}
