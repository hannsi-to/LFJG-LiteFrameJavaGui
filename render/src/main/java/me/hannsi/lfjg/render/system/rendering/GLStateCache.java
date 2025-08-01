package me.hannsi.lfjg.render.system.rendering;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.glBlendEquation;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;

public class GLStateCache {
    private static final Map<Integer, Boolean> STATE_CACHE = new HashMap<>();
    private static int lastBlendSrc = -1;
    private static int lastBlendDst = -1;
    private static int lastBlendEquation = -1;
    private static int lastActiveTexture = -1;
    private static int lastTextureId = -1;
    private static int lastShaderProgram = -1;
    private static int lastVertexArray = -1;
    private static int lastDrawIndirectBuffer = -1;
    private static int lastElementArrayBuffer = -1;

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

    public static void blendFunc(int sFactor, int dFactor) {
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

    public static void activeTexture(int texture) {
        if (lastActiveTexture != texture) {
            glActiveTexture(texture);
            lastActiveTexture = texture;
        }
    }

    public static void bindTexture(int target, int texture) {
        if (lastTextureId != texture) {
            glBindTexture(target, texture);
            lastTextureId = texture;
        }
    }

    public static void useProgram(int program) {
        if (lastShaderProgram != program) {
            glUseProgram(program);
            lastShaderProgram = program;
        }
    }

    public static void bindVertexArray(int array) {
        if (lastVertexArray != array) {
            glBindVertexArray(array);
            lastVertexArray = array;
        }
    }

    public static void bindIndirectBuffer(int buffer) {
        if (lastDrawIndirectBuffer != buffer) {
            glBindBuffer(GL_DRAW_INDIRECT_BUFFER, buffer);
            lastDrawIndirectBuffer = buffer;
        }
    }

    public static void bindElementArrayBuffer(int buffer) {
        if (lastElementArrayBuffer != buffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer);
            lastElementArrayBuffer = buffer;
        }
    }
}
