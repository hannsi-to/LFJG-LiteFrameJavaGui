package me.hannsi.lfjg.render.system.rendering;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.glBlendEquation;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glSampleMaski;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;

public class GLStateCache {
    private static final Map<Integer, Boolean> STATE_CACHE = new HashMap<>();
    private static final float[] lastClearColor = new float[]{-1f, -1f, -1f, -1f};
    private static final boolean[] lastColorMask = new boolean[]{true, true, true, true};
    private static final int[] lastStencilFunc = new int[]{-1, -1, -1};
    private static final int[] lastStencilOp = new int[]{-1, -1, -1};
    private static final int[] lastTexture = new int[]{-1, -1};
    private static final int[] lastPolygonMode = new int[]{-1, -1};
    private static final float[] lastPolygonOffset = new float[]{Float.NaN, Float.NaN};
    private static final int[] lastScissorBox = new int[]{-1, -1, -1, -1};
    private static final int[] lastViewport = new int[]{-1, -1, -1, -1};
    private static final float[] lastDepthRange = new float[]{-1f, -1f};
    private static int lastBlendSrc = -1;
    private static int lastBlendDst = -1;
    private static int lastBlendEquation = -1;
    private static int lastActiveTexture = -1;
    private static int lastShaderProgram = -1;
    private static int lastFrameBuffer = -1;
    private static int lastRenderBuffer = -1;
    private static int lastDrawFrameBuffer = -1;
    private static int lastReadFrameBuffer = -1;
    private static int lastVertexArray = -1;
    private static int lastDrawIndirectBuffer = -1;
    private static int lastElementArrayBuffer = -1;
    private static double lastClearDepth = -1.0;
    private static int lastClearStencil = -1;
    private static boolean lastDepthMask = true;
    private static int lastDepthFunc = -1;
    private static int lastCullFace = -1;
    private static int lastFrontFace = -1;
    private static float lastLineWidth = -1f;
    private static float lastPointSize = -1f;
    private static int lastSampleMaskValue = -1;

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
        int lastTarget = lastTexture[0];
        int lastTextureId = lastTexture[1];
        if (lastTarget != target || lastTextureId != texture) {
            glBindTexture(target, texture);

            lastTexture[0] = target;
            lastTexture[1] = texture;
        }
    }

    public static void useProgram(int program) {
        if (lastShaderProgram != program) {
            glUseProgram(program);
            lastShaderProgram = program;
        }
    }

    public static void bindFrameBuffer(int frameBuffer) {
        if (lastFrameBuffer != frameBuffer) {
            glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
            lastFrameBuffer = frameBuffer;
        }
    }

    public static void bindRenderBuffer(int renderBuffer) {
        if (lastRenderBuffer != renderBuffer) {
            glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
            lastRenderBuffer = renderBuffer;
        }
    }

    public static void bindDrawFrameBuffer(int frameBuffer) {
        if (lastDrawFrameBuffer != frameBuffer) {
            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, frameBuffer);
            lastDrawFrameBuffer = frameBuffer;
        }
    }

    public static void bindReadFrameBuffer(int frameBuffer) {
        if (lastReadFrameBuffer != frameBuffer) {
            glBindFramebuffer(GL_READ_FRAMEBUFFER, frameBuffer);
            lastReadFrameBuffer = frameBuffer;
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

    public static void clearColor(float red, float green, float blue, float alpha) {
        float lastRed = lastClearColor[0];
        float lastGreen = lastClearColor[1];
        float lastBlue = lastClearColor[2];
        float lastAlpha = lastClearColor[3];

        if (lastRed != red || lastGreen != green || lastBlue != blue || lastAlpha != alpha) {
            glClearColor(red, green, blue, alpha);

            lastClearColor[0] = red;
            lastClearColor[1] = green;
            lastClearColor[2] = blue;
            lastClearColor[3] = alpha;
        }
    }

    public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        boolean lastRed = lastColorMask[0];
        boolean lastGreen = lastColorMask[1];
        boolean lastBlue = lastColorMask[2];
        boolean lastAlpha = lastColorMask[3];

        if (lastRed != red || lastGreen != green || lastBlue != blue || lastAlpha != alpha) {
            glColorMask(red, green, blue, alpha);

            lastColorMask[0] = red;
            lastColorMask[1] = green;
            lastColorMask[2] = blue;
            lastColorMask[3] = alpha;
        }
    }

    public static void stencilFunc(int func, int ref, int mask) {
        int lastFunc = lastStencilFunc[0];
        int lastRef = lastStencilFunc[1];
        int lastMask = lastStencilFunc[2];

        if (lastFunc != func || lastRef != ref || lastMask != mask) {
            glStencilFunc(func, ref, mask);

            lastStencilFunc[0] = func;
            lastStencilFunc[1] = ref;
            lastStencilFunc[2] = mask;
        }
    }

    public static void stencilOp(int sfail, int dpfail, int dppass) {
        int lastSFail = lastStencilOp[0];
        int lastDPFail = lastStencilOp[1];
        int lastDPPass = lastStencilOp[2];

        if (lastSFail != sfail || lastDPFail != dpfail || lastDPPass != dppass) {
            glStencilOp(sfail, dpfail, dppass);

            lastStencilOp[0] = sfail;
            lastStencilOp[1] = dpfail;
            lastStencilOp[2] = dppass;
        }
    }

    public static void clearDepth(double depth) {
        if (lastClearDepth != depth) {
            glClearDepth(depth);
            lastClearDepth = depth;
        }
    }

    public static void clearStencil(int s) {
        if (lastClearStencil != s) {
            glClearStencil(s);
            lastClearStencil = s;
        }
    }

    public static void depthMask(boolean flag) {
        if (lastDepthMask != flag) {
            glDepthMask(flag);
            lastDepthMask = flag;
        }
    }

    public static void depthFunc(int func) {
        if (lastDepthFunc != func) {
            glDepthFunc(func);
            lastDepthFunc = func;
        }
    }

    public static void cullFace(int mode) {
        if (lastCullFace != mode) {
            glCullFace(mode);
            lastCullFace = mode;
        }
    }

    public static void frontFace(int dir) {
        if (lastFrontFace != dir) {
            glFrontFace(dir);
            lastFrontFace = dir;
        }
    }

    public static void polygonMode(int face, int mode) {
        if (lastPolygonMode[0] != face || lastPolygonMode[1] != mode) {
            glPolygonMode(face, mode);
            lastPolygonMode[0] = face;
            lastPolygonMode[1] = mode;
        }
    }

    public static void polygonOffset(float factor, float units) {
        if (lastPolygonOffset[0] != factor || lastPolygonOffset[1] != units) {
            glPolygonOffset(factor, units);
            lastPolygonOffset[0] = factor;
            lastPolygonOffset[1] = units;
        }
    }

    public static void lineWidth(float width) {
        if (lastLineWidth != width) {
            glLineWidth(width);
            lastLineWidth = width;
        }
    }

    public static void pointSize(float size) {
        if (lastPointSize != size) {
            glPointSize(size);
            lastPointSize = size;
        }
    }

    public static void scissor(int x, int y, int width, int height) {
        if (lastScissorBox[0] != x || lastScissorBox[1] != y || lastScissorBox[2] != width || lastScissorBox[3] != height) {
            glScissor(x, y, width, height);
            lastScissorBox[0] = x;
            lastScissorBox[1] = y;
            lastScissorBox[2] = width;
            lastScissorBox[3] = height;
        }
    }

    public static void viewport(int x, int y, int width, int height) {
        if (lastViewport[0] != x || lastViewport[1] != y || lastViewport[2] != width || lastViewport[3] != height) {
            glViewport(x, y, width, height);
            lastViewport[0] = x;
            lastViewport[1] = y;
            lastViewport[2] = width;
            lastViewport[3] = height;
        }
    }

    public static void depthRange(float near, float far) {
        if (lastDepthRange[0] != near || lastDepthRange[1] != far) {
            glDepthRange(near, far);
            lastDepthRange[0] = near;
            lastDepthRange[1] = far;
        }
    }

    public static void sampleMaski(int maskNumber, int mask) {
        if (lastSampleMaskValue != mask) {
            glSampleMaski(maskNumber, mask);
            lastSampleMaskValue = mask;
        }
    }
}
