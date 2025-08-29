package me.hannsi.lfjg.render.animation.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;

public class AnimationBase {
    private final String name;

    private boolean isLooping;
    private long initTime;
    private long currentTime;

    public AnimationBase(String name) {
        this.name = name;

        this.isLooping = false;
        this.initTime = System.currentTimeMillis();
        this.currentTime = 0;
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

        LogGenerator logGenerator = new LogGenerator(name, "Source: AnimationBase", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: AnimationBase cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }


    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }

    public String getName() {
        return name;
    }

    public long getInitTime() {
        return initTime;
    }

    public void setInitTime(long initTime) {
        this.initTime = initTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

}
