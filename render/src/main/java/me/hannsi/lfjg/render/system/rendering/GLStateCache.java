package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.core.event.EventHandler;
import me.hannsi.lfjg.core.event.events.OpenGLStaticMethodHookEvent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static me.hannsi.lfjg.core.Core.EVENT_MANAGER;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.glBlendEquation;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;
import static org.lwjgl.opengl.GL32.glSampleMaski;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;

public class GLStateCache {
    private final Map<Integer, Boolean> STATE_CACHE = new HashMap<>();
    private final float[] lastClearColor = new float[]{-1f, -1f, -1f, -1f};
    private final boolean[] lastColorMask = new boolean[]{true, true, true, true};
    private final int[] lastStencilFunc = new int[]{-1, -1, -1};
    private final int[] lastStencilOp = new int[]{-1, -1, -1};
    private final int[] lastTexture = new int[]{-1, -1};
    private final int[] lastPolygonMode = new int[]{-1, -1};
    private final float[] lastPolygonOffset = new float[]{Float.NaN, Float.NaN};
    private final int[] lastScissorBox = new int[]{-1, -1, -1, -1};
    private final int[] lastViewport = new int[]{-1, -1, -1, -1};
    private final float[] lastDepthRange = new float[]{-1f, -1f};
    private int lastBlendSrc = -1;
    private int lastBlendDst = -1;
    private int lastBlendEquation = -1;
    private int lastActiveTexture = -1;
    private int lastShaderProgram = -1;
    private int lastFrameBuffer = -1;
    private int lastRenderBuffer = -1;
    private int lastDrawFrameBuffer = -1;
    private int lastReadFrameBuffer = -1;
    private int lastVertexArray = -1;
    private int lastDrawIndirectBuffer = -1;
    private int lastElementArrayBuffer = -1;
    private int lastArrayBuffer = -1;
    private int lastUniformBuffer = -1;
    private int lastShaderStorageBuffer = -1;
    private double lastClearDepth = -1.0;
    private int lastClearStencil = -1;
    private boolean lastDepthMask = true;
    private int lastDepthFunc = -1;
    private int lastCullFace = -1;
    private int lastFrontFace = -1;
    private float lastLineWidth = -1f;
    private float lastPointSize = -1f;
    private int lastSampleMaskValue = -1;
    private int lastBufferRangeTarget = -1;
    private int lastBufferRangeIndex = -1;
    private int lastBufferRangeBuffer = -1;
    private long lastBufferRangeOffset = -1;
    private long lastBufferRangeSize = -1;

    public GLStateCache() {
        EVENT_MANAGER.register(this);

        lastFrameBuffer = glGetInteger(GL_DRAW_FRAMEBUFFER_BINDING);
    }

    public void enable(int cap) {
        Boolean enabled = STATE_CACHE.get(cap);
        if (enabled != null && enabled) {
            return;
        }
        glEnable(cap);
        STATE_CACHE.put(cap, true);
    }

    public void disable(int cap) {
        Boolean enabled = STATE_CACHE.get(cap);
        if (enabled != null && !enabled) {
            return;
        }
        glDisable(cap);
        STATE_CACHE.put(cap, false);
    }

    public void blendFunc(int sFactor, int dFactor) {
        if (lastBlendSrc == sFactor && lastBlendDst == dFactor) {
            return;
        }
        glBlendFunc(sFactor, dFactor);
        lastBlendSrc = sFactor;
        lastBlendDst = dFactor;
    }

    public void setBlendEquation(int equation) {
        if (lastBlendEquation == equation) {
            return;
        }
        glBlendEquation(equation);
        lastBlendEquation = equation;
    }

    public void activeTexture(int texture) {
        if (lastActiveTexture == texture) {
            return;
        }
        glActiveTexture(texture);
        lastActiveTexture = texture;
    }

    public void bindTexture(int target, int texture) {
        int lastTarget = lastTexture[0];
        int lastTextureId = lastTexture[1];
        if (lastTarget == target && lastTextureId == texture) {
            return;
        }
        glBindTexture(target, texture);

        lastTexture[0] = target;
        lastTexture[1] = texture;
    }

    public void deleteTexture(int target, int texture) {
        if (lastTexture[0] == target && lastTexture[1] == texture) {
            glBindTexture(target, 0);
            lastTexture[0] = -1;
            lastTexture[1] = -1;
        }

        glDeleteTextures(texture);
    }

    public void useProgram(int program) {
        if (lastShaderProgram == program) {
            return;
        }
        glUseProgram(program);
        lastShaderProgram = program;
    }

    public void deleteProgram(int program) {
        if (lastShaderProgram == program) {
            useProgram(0);
            lastShaderProgram = -1;
        }

        glDeleteProgram(program);
    }

    public void bindFrameBuffer(int frameBuffer) {
        if (lastFrameBuffer == frameBuffer) {
            return;
        }
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
        lastFrameBuffer = frameBuffer;
    }

    public void deleteFrameBuffer(int frameBuffer) {
        if (lastFrameBuffer == frameBuffer) {
            bindFrameBuffer(0);
            lastFrameBuffer = -1;
        }

        glDeleteFramebuffers(frameBuffer);
    }

    public void bindRenderBuffer(int renderBuffer) {
        if (lastRenderBuffer == renderBuffer) {
            return;
        }
        glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
        lastRenderBuffer = renderBuffer;
    }

    public void deleteRenderBuffer(int renderBuffer) {
        if (lastRenderBuffer == renderBuffer) {
            bindRenderBuffer(0);
            lastRenderBuffer = -1;
        }

        glDeleteRenderbuffers(renderBuffer);
    }

    public void bindDrawFrameBuffer(int frameBuffer) {
        if (lastDrawFrameBuffer == frameBuffer) {
            return;
        }
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, frameBuffer);
        lastDrawFrameBuffer = frameBuffer;
    }

    public void bindReadFrameBuffer(int frameBuffer) {
        if (lastReadFrameBuffer == frameBuffer) {
            return;
        }
        glBindFramebuffer(GL_READ_FRAMEBUFFER, frameBuffer);
        lastReadFrameBuffer = frameBuffer;
    }

    public void bindVertexArray(int array) {
        if (lastVertexArray == array) {
            return;
        }
        glBindVertexArray(array);
        lastVertexArray = array;
    }

    public void bindVertexArrayForce(int array) {
        glBindVertexArray(array);
        lastVertexArray = array;
    }

    public void deleteVertexArray(int array) {
        if (lastVertexArray == array) {
            bindVertexArray(0);
            lastVertexArray = -1;
        }

        glDeleteVertexArrays(array);
    }

    public void bindIndirectBuffer(int buffer) {
        if (lastDrawIndirectBuffer == buffer) {
            return;
        }
        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, buffer);
        lastDrawIndirectBuffer = buffer;
    }

    public void bindIndirectBufferForce(int buffer) {
        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, buffer);
        lastDrawIndirectBuffer = buffer;
    }

    public void deleteIndirectBuffer(int buffer) {
        if (lastDrawIndirectBuffer == buffer) {
            bindIndirectBuffer(0);
            lastDrawIndirectBuffer = -1;
        }

        glDeleteBuffers(buffer);
    }

    public void bindElementArrayBuffer(int buffer) {
        if (lastElementArrayBuffer == buffer) {
            return;
        }
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer);
        lastElementArrayBuffer = buffer;
    }

    public void bindElementArrayBufferForce(int buffer) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer);
        lastElementArrayBuffer = buffer;
    }

    public void deleteElementArrayBuffer(int buffer) {
        if (lastElementArrayBuffer == buffer) {
            bindElementArrayBuffer(0);
            lastElementArrayBuffer = -1;
        }
        glDeleteBuffers(buffer);
    }

    public void bindArrayBuffer(int buffer) {
        if (lastArrayBuffer == buffer) {
            return;
        }
        glBindBuffer(GL_ARRAY_BUFFER, buffer);
        lastArrayBuffer = buffer;
    }

    public void deleteArrayBuffer(int buffer) {
        if (lastArrayBuffer == buffer) {
            bindArrayBuffer(0);
            lastArrayBuffer = -1;
        }
        glDeleteBuffers(buffer);
    }

    public void bindUniformBuffer(int buffer) {
        if (lastUniformBuffer == buffer) {
            return;
        }
        glBindBuffer(GL_UNIFORM_BUFFER, buffer);
        lastUniformBuffer = buffer;
    }

    public void deleteUniformBuffer(int buffer) {
        if (lastUniformBuffer == buffer) {
            bindUniformBuffer(0);
            lastUniformBuffer = -1;
        }

        if (lastBufferRangeBuffer == buffer) {
            lastBufferRangeBuffer = -1;
            lastBufferRangeOffset = -1;
            lastBufferRangeSize = -1;
        }

        glDeleteBuffers(buffer);
    }

    public void bindShaderStorageBuffer(int buffer) {
        if (lastShaderStorageBuffer == buffer) {
            return;
        }
        glBindBuffer(GL_SHADER_STORAGE_BUFFER, buffer);
        lastShaderStorageBuffer = buffer;
    }

    public void deleteShaderStorageBuffer(int buffer) {
        if (lastShaderStorageBuffer == buffer) {
            bindShaderStorageBuffer(0);
            lastShaderStorageBuffer = -1;
        }

        if (lastBufferRangeBuffer == buffer) {
            lastBufferRangeBuffer = -1;
            lastBufferRangeOffset = -1;
            lastBufferRangeSize = -1;
        }

        glDeleteBuffers(buffer);
    }

    public void clearColor(float red, float green, float blue, float alpha) {
        float lastRed = lastClearColor[0];
        float lastGreen = lastClearColor[1];
        float lastBlue = lastClearColor[2];
        float lastAlpha = lastClearColor[3];

        if (lastRed == red && lastGreen == green && lastBlue == blue && lastAlpha == alpha) {
            return;
        }
        glClearColor(red, green, blue, alpha);

        lastClearColor[0] = red;
        lastClearColor[1] = green;
        lastClearColor[2] = blue;
        lastClearColor[3] = alpha;
    }

    public void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        boolean lastRed = lastColorMask[0];
        boolean lastGreen = lastColorMask[1];
        boolean lastBlue = lastColorMask[2];
        boolean lastAlpha = lastColorMask[3];

        if (lastRed == red && lastGreen == green && lastBlue == blue && lastAlpha == alpha) {
            return;
        }
        glColorMask(red, green, blue, alpha);

        lastColorMask[0] = red;
        lastColorMask[1] = green;
        lastColorMask[2] = blue;
        lastColorMask[3] = alpha;
    }

    public void stencilFunc(int func, int ref, int mask) {
        int lastFunc = lastStencilFunc[0];
        int lastRef = lastStencilFunc[1];
        int lastMask = lastStencilFunc[2];

        if (lastFunc == func && lastRef == ref && lastMask == mask) {
            return;
        }
        glStencilFunc(func, ref, mask);

        lastStencilFunc[0] = func;
        lastStencilFunc[1] = ref;
        lastStencilFunc[2] = mask;
    }

    public void stencilOp(int sfail, int dpfail, int dppass) {
        int lastSFail = lastStencilOp[0];
        int lastDPFail = lastStencilOp[1];
        int lastDPPass = lastStencilOp[2];

        if (lastSFail == sfail && lastDPFail == dpfail && lastDPPass == dppass) {
            return;
        }
        glStencilOp(sfail, dpfail, dppass);

        lastStencilOp[0] = sfail;
        lastStencilOp[1] = dpfail;
        lastStencilOp[2] = dppass;
    }

    public void clearDepth(double depth) {
        if (lastClearDepth == depth) {
            return;
        }
        glClearDepth(depth);
        lastClearDepth = depth;
    }

    public void clearStencil(int s) {
        if (lastClearStencil == s) {
            return;
        }
        glClearStencil(s);
        lastClearStencil = s;
    }

    public void depthMask(boolean flag) {
        if (lastDepthMask == flag) {
            return;
        }
        glDepthMask(flag);
        lastDepthMask = flag;
    }

    public void depthFunc(int func) {
        if (lastDepthFunc == func) {
            return;
        }
        glDepthFunc(func);
        lastDepthFunc = func;
    }

    public void cullFace(int mode) {
        if (lastCullFace == mode) {
            return;
        }
        glCullFace(mode);
        lastCullFace = mode;
    }

    public void frontFace(int dir) {
        if (lastFrontFace == dir) {
            return;
        }
        glFrontFace(dir);
        lastFrontFace = dir;
    }

    public void polygonMode(int face, int mode) {
        if (lastPolygonMode[0] == face && lastPolygonMode[1] == mode) {
            return;
        }
        glPolygonMode(face, mode);
        lastPolygonMode[0] = face;
        lastPolygonMode[1] = mode;
    }

    public void polygonOffset(float factor, float units) {
        if (lastPolygonOffset[0] == factor && lastPolygonOffset[1] == units) {
            return;
        }
        glPolygonOffset(factor, units);
        lastPolygonOffset[0] = factor;
        lastPolygonOffset[1] = units;
    }

    public void lineWidth(float width) {
        if (lastLineWidth == width) {
            return;
        }
        glLineWidth(width);
        lastLineWidth = width;
    }

    public void pointSize(float size) {
        if (lastPointSize == size) {
            return;
        }
        glPointSize(size);
        lastPointSize = size;
    }

    public void scissor(int x, int y, int width, int height) {
        if (lastScissorBox[0] == x && lastScissorBox[1] == y && lastScissorBox[2] == width && lastScissorBox[3] == height) {
            return;
        }
        glScissor(x, y, width, height);
        lastScissorBox[0] = x;
        lastScissorBox[1] = y;
        lastScissorBox[2] = width;
        lastScissorBox[3] = height;
    }

    public void viewport(int x, int y, int width, int height) {
        if (lastViewport[0] == x && lastViewport[1] == y && lastViewport[2] == width && lastViewport[3] == height) {
            return;
        }
        glViewport(x, y, width, height);
        lastViewport[0] = x;
        lastViewport[1] = y;
        lastViewport[2] = width;
        lastViewport[3] = height;
    }

    public void depthRange(float near, float far) {
        if (lastDepthRange[0] == near && lastDepthRange[1] == far) {
            return;
        }
        glDepthRange(near, far);
        lastDepthRange[0] = near;
        lastDepthRange[1] = far;
    }

    public void sampleMaski(int maskNumber, int mask) {
        if (lastSampleMaskValue == mask) {
            return;
        }
        glSampleMaski(maskNumber, mask);
        lastSampleMaskValue = mask;
    }

    public void bindBufferRange(int target, int index, int buffer, long offset, long size) {
        if (lastBufferRangeTarget == target && lastBufferRangeIndex == index && lastBufferRangeBuffer == buffer && lastBufferRangeOffset == offset && lastBufferRangeSize == size) {
            return;
        }

        glBindBufferRange(target, index, buffer, offset, size);

        lastBufferRangeTarget = target;
        lastBufferRangeIndex = index;
        lastBufferRangeBuffer = buffer;
        lastBufferRangeOffset = offset;
        lastBufferRangeSize = size;
    }

    @EventHandler
    public void onOpenGLStaticMethodHookEvent(OpenGLStaticMethodHookEvent event) {
        Method method = event.getMethod();
        Object[] args = event.getArgs();
        Callable<?> zuper = event.getSuper();

        switch (method.getName()) {
            case "glEnable":
                STATE_CACHE.put((int) args[0], true);
                break;
            case "glDisable":
                STATE_CACHE.put((int) args[0], false);
                break;
            case "glClearColor":
                lastClearColor[0] = (float) args[0];
                lastClearColor[1] = (float) args[1];
                lastClearColor[2] = (float) args[2];
                lastClearColor[3] = (float) args[3];
                break;
            case "glColorMash":
                lastColorMask[0] = (boolean) args[0];
                lastColorMask[1] = (boolean) args[1];
                lastColorMask[2] = (boolean) args[2];
                lastColorMask[3] = (boolean) args[3];
                break;
            case "glStencilFunc":
                lastStencilFunc[0] = (int) args[0];
                lastStencilFunc[1] = (int) args[1];
                lastStencilFunc[2] = (int) args[2];
                break;
            case "glStencilOp":
                lastStencilOp[0] = (int) args[0];
                lastStencilOp[1] = (int) args[1];
                lastStencilOp[2] = (int) args[2];
                break;
            case "glBindTexture":
                lastTexture[0] = (int) args[0];
                lastTexture[1] = (int) args[1];
                break;
            case "glPolygonMode":
                lastPolygonMode[0] = (int) args[0];
                lastPolygonMode[1] = (int) args[1];
                break;
            case "glPolygonOffset":
                lastPolygonOffset[0] = (float) args[0];
                lastPolygonOffset[1] = (float) args[1];
                break;
            case "glScissor":
                lastScissorBox[0] = (int) args[0];
                lastScissorBox[1] = (int) args[1];
                lastScissorBox[2] = (int) args[2];
                lastScissorBox[3] = (int) args[3];
                break;
            case "glViewport":
                lastViewport[0] = (int) args[0];
                lastViewport[1] = (int) args[1];
                lastViewport[2] = (int) args[2];
                lastViewport[3] = (int) args[3];
                break;
            case "glDepthRange":
                lastDepthRange[0] = (int) args[0];
                lastDepthRange[1] = (int) args[1];
                lastDepthRange[2] = (int) args[2];
                break;
            case "glBlendFunc":
                lastBlendSrc = (int) args[0];
                lastBlendDst = (int) args[1];
                break;
            case "glBlendEquation":
                lastBlendEquation = (int) args[0];
                break;
            case "glActiveTexture":
                lastActiveTexture = (int) args[0];
                break;
            case "glUseProgram":
                lastShaderProgram = (int) args[0];
                break;
            case "glBindFramebuffer":
                if ((int) args[0] == GL_FRAMEBUFFER) {
                    lastFrameBuffer = (int) args[1];
                } else if ((int) args[0] == GL_DRAW_FRAMEBUFFER) {
                    lastDrawFrameBuffer = (int) args[1];
                } else if ((int) args[0] == GL_READ_FRAMEBUFFER) {
                    lastReadFrameBuffer = (int) args[1];
                }
                break;
            case "glBindRenderbuffer":
                lastRenderBuffer = (int) args[0];
                break;
            case "glBindVertexArray":
                lastVertexArray = (int) args[0];
                break;
            case "glBindBuffer":
                if ((int) args[0] == GL_DRAW_INDIRECT_BUFFER) {
                    lastDrawIndirectBuffer = (int) args[1];
                } else if ((int) args[0] == GL_ELEMENT_ARRAY_BUFFER) {
                    lastElementArrayBuffer = (int) args[1];
                } else if ((int) args[0] == GL_ARRAY_BUFFER) {
                    lastArrayBuffer = (int) args[1];
                } else if ((int) args[0] == GL_UNIFORM_BUFFER) {
                    lastUniformBuffer = (int) args[1];
                } else if ((int) args[0] == GL_SHADER_STORAGE_BUFFER) {
                    lastShaderStorageBuffer = (int) args[1];
                }
                break;
            case "glClearDepth":
                lastClearDepth = (int) args[0];
                break;
            case "glClearStencil":
                lastClearStencil = (int) args[0];
                break;
            case "glDepthMask":
                lastDepthMask = (boolean) args[0];
                break;
            case "glDepthFunc":
                lastDepthFunc = (int) args[0];
                break;
            case "glCullFace":
                lastCullFace = (int) args[0];
                break;
            case "glFrontFace":
                lastFrontFace = (int) args[0];
                break;
            case "glLineWidth":
                lastLineWidth = (float) args[0];
                break;
            case "glPointSize":
                lastPointSize = (float) args[0];
                break;
            case "glSampleMaski":
                lastSampleMaskValue = (int) args[1];
                break;
            case "glBindBufferRange":
                lastBufferRangeTarget = (int) args[0];
                lastBufferRangeIndex = (int) args[1];
                lastBufferRangeBuffer = (int) args[2];
                lastBufferRangeOffset = (long) args[3];
                lastBufferRangeSize = (long) args[4];
                break;
        }
    }

    public Map<Integer, Boolean> getSTATE_CACHE() {
        return STATE_CACHE;
    }

    public float[] getLastClearColor() {
        return lastClearColor;
    }

    public boolean[] getLastColorMask() {
        return lastColorMask;
    }

    public int[] getLastStencilFunc() {
        return lastStencilFunc;
    }

    public int[] getLastStencilOp() {
        return lastStencilOp;
    }

    public int[] getLastTexture() {
        return lastTexture;
    }

    public int[] getLastPolygonMode() {
        return lastPolygonMode;
    }

    public float[] getLastPolygonOffset() {
        return lastPolygonOffset;
    }

    public int[] getLastScissorBox() {
        return lastScissorBox;
    }

    public int[] getLastViewport() {
        return lastViewport;
    }

    public float[] getLastDepthRange() {
        return lastDepthRange;
    }

    public int getLastBlendSrc() {
        return lastBlendSrc;
    }

    public int getLastBlendDst() {
        return lastBlendDst;
    }

    public int getLastBlendEquation() {
        return lastBlendEquation;
    }

    public int getLastActiveTexture() {
        return lastActiveTexture;
    }

    public int getLastShaderProgram() {
        return lastShaderProgram;
    }

    public int getLastFrameBuffer() {
        return lastFrameBuffer;
    }

    public int getLastRenderBuffer() {
        return lastRenderBuffer;
    }

    public int getLastDrawFrameBuffer() {
        return lastDrawFrameBuffer;
    }

    public int getLastReadFrameBuffer() {
        return lastReadFrameBuffer;
    }

    public int getLastVertexArray() {
        return lastVertexArray;
    }

    public int getLastDrawIndirectBuffer() {
        return lastDrawIndirectBuffer;
    }

    public int getLastElementArrayBuffer() {
        return lastElementArrayBuffer;
    }

    public int getLastArrayBuffer() {
        return lastArrayBuffer;
    }

    public int getLastUniformBuffer() {
        return lastUniformBuffer;
    }

    public int getLastShaderStorageBuffer() {
        return lastShaderStorageBuffer;
    }

    public double getLastClearDepth() {
        return lastClearDepth;
    }

    public int getLastClearStencil() {
        return lastClearStencil;
    }

    public boolean isLastDepthMask() {
        return lastDepthMask;
    }

    public int getLastDepthFunc() {
        return lastDepthFunc;
    }

    public int getLastCullFace() {
        return lastCullFace;
    }

    public int getLastFrontFace() {
        return lastFrontFace;
    }

    public float getLastLineWidth() {
        return lastLineWidth;
    }

    public float getLastPointSize() {
        return lastPointSize;
    }

    public int getLastSampleMaskValue() {
        return lastSampleMaskValue;
    }

    public int getLastBufferRangeTarget() {
        return lastBufferRangeTarget;
    }

    public int getLastBufferRangeIndex() {
        return lastBufferRangeIndex;
    }

    public int getLastBufferRangeBuffer() {
        return lastBufferRangeBuffer;
    }

    public long getLastBufferRangeOffset() {
        return lastBufferRangeOffset;
    }

    public long getLastBufferRangeSize() {
        return lastBufferRangeSize;
    }
}
