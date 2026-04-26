package me.hannsi.lfjg.render.system;

import me.hannsi.lfjg.core.debug.DebugLog;

import static org.lwjgl.opengl.GL32.*;

public class GLFenceTracker {
    private final long[] fences;
    private final int ringCount;
    private int currentFrame = 0;

    public GLFenceTracker(int ringCount) {
        this.fences = new long[ringCount];
        this.ringCount = ringCount;
    }

    public int getCompletedSlot() {
        int nextSlot = currentFrame % ringCount;

        if (fences[nextSlot] == 0) {
            return nextSlot;
        }

        int result = glClientWaitSync(fences[nextSlot], 0, 0);
        if (result == GL_ALREADY_SIGNALED || result == GL_CONDITION_SATISFIED) {
            glDeleteSync(fences[nextSlot]);
            fences[nextSlot] = 0;
            return nextSlot;
        }

        int waitResult = glClientWaitSync(fences[nextSlot], GL_SYNC_FLUSH_COMMANDS_BIT, 1_000_000_000L);
        if (waitResult == GL_TIMEOUT_EXPIRED) {
            DebugLog.warning(getClass(), "GLFenceTracker: GPU sync timed out. " + "The CPU is submitting frames faster than the GPU can process them. " + "Consider increasing ringCount (current: " + ringCount + ").");
        } else if (waitResult == GL_WAIT_FAILED) {
            DebugLog.error(getClass(), "GLFenceTracker: glClientWaitSync failed (GL error).");
        }
        glDeleteSync(fences[nextSlot]);
        fences[nextSlot] = 0;
        return nextSlot;
    }

    public void endFrame() {
        int slot = currentFrame % ringCount;
        if (fences[slot] != 0) {
            glDeleteSync(fences[slot]);
        }
        fences[slot] = glFenceSync(GL_SYNC_GPU_COMMANDS_COMPLETE, 0);
        currentFrame++;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }
}
