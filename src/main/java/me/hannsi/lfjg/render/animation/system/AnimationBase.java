package me.hannsi.lfjg.render.animation.system;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;

public class AnimationBase {
    @Getter
    private final String name;
    @Getter
    @Setter
    private int id;
    private boolean isLooping;
    @Getter
    @Setter
    private long initTime;
    @Getter
    @Setter
    private long currentTime;
    @Getter
    @Setter
    private long pauseTime;

    public AnimationBase(String name, int id, long pauseTime) {
        this.name = name;
        this.id = id;

        this.isLooping = false;

        this.initTime = System.currentTimeMillis();
        this.currentTime = 0;
        this.pauseTime = pauseTime;
    }

    public void start(GLObject glObject) {
        if (isLooping) {
            return;
        }

        initTime = System.currentTimeMillis();

        isLooping = true;
    }

    public void systemLoop(GLObject glObject) {
        if (!isLooping) {
            return;
        }

        currentTime = System.currentTimeMillis() - initTime;
        if (currentTime < pauseTime) {
            return;
        }

        loop(currentTime, glObject);
    }

    public void loop(long currentTime, GLObject glObject) {

    }

    public void stop(GLObject glObject) {
        if (!isLooping) {
            return;
        }

        isLooping = false;
        currentTime = 0;
    }

    public void cleanup() {
        isLooping = false;
        initTime = 0;
        currentTime = 0;
        pauseTime = 0;

        LogGenerator logGenerator = new LogGenerator(name, "Source: AnimationBase", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: AnimationBase cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }


    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }

}
