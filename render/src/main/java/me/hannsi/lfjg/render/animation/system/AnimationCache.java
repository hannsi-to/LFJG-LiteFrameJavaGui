package me.hannsi.lfjg.render.animation.system;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.Id;
import me.hannsi.lfjg.render.renderers.GLObject;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
public class AnimationCache {
    private LinkedHashMap<AnimationBase, Long> animationBases;

    AnimationCache() {
        this.animationBases = new LinkedHashMap<>();
    }

    public static AnimationCache initAnimationCache() {
        return new AnimationCache();
    }

    public AnimationCache createCache(AnimationBase animationBase) {
        this.animationBases.put(animationBase, Id.latestAnimationCacheId++);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                animationBase.getId(),
                ""
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public void start(GLObject glObject) {
        animationBases.forEach((animationBase, id) -> animationBase.start(glObject));
    }

    public void end(GLObject glObject) {
        animationBases.forEach((animationBase, id) -> animationBase.stop(glObject));
    }

    public void loop(GLObject glObject) {
        animationBases.forEach((animationBase, id) -> animationBase.systemLoop(glObject));
    }

    public void cleanup(GLObject glObject) {
        AtomicReference<String> ids = new AtomicReference<>();
        animationBases.forEach((animationBase, id) -> {
            animationBase.stop(glObject);
            animationBase.cleanup();
            ids.set(ids.get() + ", ");
        });

        animationBases.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.get().substring(0, ids.get().length() - 2),
                ""
        ).logging(DebugLevel.DEBUG);
    }
}
