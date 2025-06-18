package me.hannsi.lfjg.render.system.rendering;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendEquation;

public class GLStateCache {
    private static final Map<Integer, Boolean> STATE_CACHE = new HashMap<>();
    private static int lastBlendSrc = -1;
    private static int lastBlendDst = -1;
    private static int lastBlendEquation = -1;

    public static void enable(int cap) {
        Boolean enabled = STATE_CACHE.get(cap);
        if (enabled == null || !enabled) {
            glEnable(cap);
            STATE_CACHE.put(cap, true);
        }
    }

    public static void disable(int cap) {
        Boolean enabled = STATE_CACHE.get(cap);
        if (enabled == null || enabled) {
            glDisable(cap);
            STATE_CACHE.put(cap, false);
        }
    }

    public static void setBlendFunc(int sFactor, int dFactor) {
        if (lastBlendSrc != sFactor || lastBlendDst != dFactor) {
            glBlendFunc(sFactor, dFactor);
            lastBlendSrc = sFactor;
            lastBlendDst = dFactor;
        }
    }

    public static void setBlendEquation(int equation) {
        if (lastBlendEquation != equation) {
            glBlendEquation(equation);
            lastBlendEquation = equation;
        }
    }
}
