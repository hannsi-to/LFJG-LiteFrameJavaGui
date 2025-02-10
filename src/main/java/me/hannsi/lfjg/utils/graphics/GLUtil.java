package me.hannsi.lfjg.utils.graphics;

import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for managing OpenGL targets and their states.
 */
public class GLUtil {
    private final Map<Integer, Boolean> targets;

    /**
     * Constructs a GLUtil instance and initializes the targets map.
     */
    public GLUtil() {
        this.targets = new HashMap<>();
    }

    /**
     * Adds an OpenGL target with a default state of false (enabled).
     *
     * @param target the OpenGL target to add
     */
    public void addGLTarget(int target) {
        targets.put(target, false);
    }

    /**
     * Adds an OpenGL target with the specified state.
     *
     * @param target  the OpenGL target to add
     * @param disable the state of the target (true to disable, false to enable)
     */
    public void addGLTarget(int target, boolean disable) {
        targets.put(target, disable);
    }

    /**
     * Enables or disables the OpenGL targets based on their states.
     */
    public void enableTargets() {
        GL11.glPushMatrix();

        for (Map.Entry<Integer, Boolean> entry : targets.entrySet()) {
            if (entry.getValue()) {
                disable(entry.getKey());
            } else {
                enable(entry.getKey());
            }
        }
    }

    /**
     * Reverts the states of the OpenGL targets.
     */
    public void disableTargets() {
        for (Map.Entry<Integer, Boolean> entry : targets.entrySet()) {
            if (entry.getValue()) {
                enable(entry.getKey());
            } else {
                disable(entry.getKey());
            }
        }

        GL11.glPopMatrix();
    }

    /**
     * Enables the specified OpenGL target.
     *
     * @param target the OpenGL target to enable
     */
    private void enable(int target) {
        GL11.glEnable(target);
    }

    /**
     * Disables the specified OpenGL target.
     *
     * @param target the OpenGL target to disable
     */
    private void disable(int target) {
        GL11.glDisable(target);
    }

    /**
     * Clears all OpenGL targets from the map.
     */
    public void cleanup() {
        targets.clear();
    }
}