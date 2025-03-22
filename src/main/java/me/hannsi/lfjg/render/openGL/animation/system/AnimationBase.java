package me.hannsi.lfjg.render.openGL.animation.system;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;

public class AnimationBase {
    private final String name;
    private int id;
    private boolean isLooping;
    private long initTime;
    private long currentTime;
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

    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
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

    public long getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(long pauseTime) {
        this.pauseTime = pauseTime;
    }
}
