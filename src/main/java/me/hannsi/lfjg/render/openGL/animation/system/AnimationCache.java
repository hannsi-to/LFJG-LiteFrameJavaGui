package me.hannsi.lfjg.render.openGL.animation.system;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.Id;

import java.util.LinkedHashMap;

public class AnimationCache {
    private LinkedHashMap<AnimationBase, Long> animationBases;

    public AnimationCache() {
        this.animationBases = new LinkedHashMap<>();
    }

    public void createCache(AnimationBase animationBase) {
        this.animationBases.put(animationBase, Id.latestAnimationCacheId++);

        LogGenerator logGenerator = new LogGenerator("AnimationCache Debug Message", "Source: AnimationCache", "Type: Cache Creation", "ID: " + animationBase.getId(), "Severity: Info", "Message: Create animation cache: " + animationBase.getName());
        logGenerator.logging(DebugLevel.DEBUG);
    }

    public void start(GLObject glObject) {
        animationBases.forEach((animationBase, id) -> {
            animationBase.start(glObject);
        });
    }

    public void end(GLObject glObject) {
        animationBases.forEach((animationBase, id) -> {
            animationBase.stop(glObject);
        });
    }

    public void loop(GLObject glObject) {
        animationBases.forEach((animationBase, id) -> {
            animationBase.systemLoop(glObject);
        });
    }

    public void cleanup(GLObject glObject) {
        animationBases.forEach((animationBase, id) -> {
            animationBase.stop(glObject);
            animationBase.cleanup();
        });

        animationBases.clear();
    }

    public LinkedHashMap<AnimationBase, Long> getAnimationBases() {
        return animationBases;
    }

    public void setAnimationBases(LinkedHashMap<AnimationBase, Long> animationBases) {
        this.animationBases = animationBases;
    }
}
