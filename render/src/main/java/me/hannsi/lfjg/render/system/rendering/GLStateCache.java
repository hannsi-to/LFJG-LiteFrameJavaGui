package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.core.event.EventHandler;
import me.hannsi.lfjg.core.event.events.OpenGLStaticMethodHookEvent;
import org.lwjgl.opengl.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static me.hannsi.lfjg.core.Core.EVENT_MANAGER;

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
    private double lastClearDepth = -1.0;
    private int lastClearStencil = -1;
    private boolean lastDepthMask = true;
    private int lastDepthFunc = -1;
    private int lastCullFace = -1;
    private int lastFrontFace = -1;
    private float lastLineWidth = -1f;
    private float lastPointSize = -1f;
    private int lastSampleMaskValue = -1;

    public GLStateCache() {
        EVENT_MANAGER.register(this);
    }

    public void enable(int cap) {
        Boolean enabled = STATE_CACHE.get(cap);
        if (enabled == null || !enabled) {
            GL11.glEnable(cap);
            STATE_CACHE.put(cap, true);
        }
    }

    public void disable(int cap) {
        Boolean enabled = STATE_CACHE.get(cap);
        if (enabled == null || enabled) {
            GL11.glDisable(cap);
            STATE_CACHE.put(cap, false);
        }
    }

    public void blendFunc(int sFactor, int dFactor) {
        if (lastBlendSrc != sFactor || lastBlendDst != dFactor) {
            GL11.glBlendFunc(sFactor, dFactor);
            lastBlendSrc = sFactor;
            lastBlendDst = dFactor;
        }
    }

    public void setBlendEquation(int equation) {
        if (lastBlendEquation != equation) {
            GL14.glBlendEquation(equation);
            lastBlendEquation = equation;
        }
    }

    public void activeTexture(int texture) {
        if (lastActiveTexture != texture) {
            GL13.glActiveTexture(texture);
            lastActiveTexture = texture;
        }
    }

    public void bindTexture(int target, int texture) {
        int lastTarget = lastTexture[0];
        int lastTextureId = lastTexture[1];
        if (lastTarget != target || lastTextureId != texture) {
            GL11.glBindTexture(target, texture);

            lastTexture[0] = target;
            lastTexture[1] = texture;
        }
    }

    public void deleteTexture(int target, int texture) {
        if (lastTexture[0] == target && lastTexture[1] == texture) {
            GL11.glBindTexture(target, 0);
            lastTexture[0] = -1;
            lastTexture[1] = -1;
        }

        GL11.glDeleteTextures(texture);
    }

    public void useProgram(int program) {
        if (lastShaderProgram != program) {
            GL20.glUseProgram(program);
            lastShaderProgram = program;
        }
    }

    public void deleteProgram(int program) {
        if (lastShaderProgram == program) {
            useProgram(0);
            lastShaderProgram = -1;
        }

        GL20.glDeleteProgram(program);
    }

    public void bindFrameBuffer(int frameBuffer) {
        if (lastFrameBuffer != frameBuffer) {
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
            lastFrameBuffer = frameBuffer;
        }
    }

    public void deleteFrameBuffer(int frameBuffer) {
        if (lastFrameBuffer == frameBuffer) {
            bindFrameBuffer(0);
            lastFrameBuffer = -1;
        }

        GL30.glDeleteFramebuffers(frameBuffer);
    }

    public void bindRenderBuffer(int renderBuffer) {
        if (lastRenderBuffer != renderBuffer) {
            GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBuffer);
            lastRenderBuffer = renderBuffer;
        }
    }

    public void deleteRenderBuffer(int renderBuffer) {
        if (lastRenderBuffer == renderBuffer) {
            bindRenderBuffer(0);
            lastRenderBuffer = -1;
        }

        GL30.glDeleteRenderbuffers(renderBuffer);
    }

    public void bindDrawFrameBuffer(int frameBuffer) {
        if (lastDrawFrameBuffer != frameBuffer) {
            GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, frameBuffer);
            lastDrawFrameBuffer = frameBuffer;
        }
    }

    public void bindReadFrameBuffer(int frameBuffer) {
        if (lastReadFrameBuffer != frameBuffer) {
            GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, frameBuffer);
            lastReadFrameBuffer = frameBuffer;
        }
    }

    public void bindVertexArray(int array) {
        if (lastVertexArray != array) {
            GL30.glBindVertexArray(array);
            lastVertexArray = array;
        }
    }

    public void bindVertexArrayForce(int array) {
        GL30.glBindVertexArray(array);
        lastVertexArray = array;
    }

    public void deleteVertexArray(int array) {
        if (lastVertexArray == array) {
            bindVertexArray(0);
            lastVertexArray = -1;
        }

        GL30.glDeleteVertexArrays(array);
    }

    public void bindIndirectBuffer(int buffer) {
        if (lastDrawIndirectBuffer != buffer) {
            GL15.glBindBuffer(GL40.GL_DRAW_INDIRECT_BUFFER, buffer);
            lastDrawIndirectBuffer = buffer;
        }
    }

    public void bindIndirectBufferForce(int buffer) {
        GL15.glBindBuffer(GL40.GL_DRAW_INDIRECT_BUFFER, buffer);
        lastDrawIndirectBuffer = buffer;
    }

    public void deleteIndirectBuffer(int buffer) {
        if (lastDrawIndirectBuffer == buffer) {
            bindIndirectBuffer(0);
            lastDrawIndirectBuffer = -1;
        }

        GL15.glDeleteBuffers(buffer);
    }

    public void bindElementArrayBuffer(int buffer) {
        if (lastElementArrayBuffer != buffer) {
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer);
            lastElementArrayBuffer = buffer;
        }
    }

    public void bindElementArrayBufferForce(int buffer) {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer);
        lastElementArrayBuffer = buffer;
    }

    public void deleteElementArrayBuffer(int buffer) {
        if (lastElementArrayBuffer == buffer) {
            bindElementArrayBuffer(0);
            lastElementArrayBuffer = -1;
        }
        GL15.glDeleteBuffers(buffer);
    }

    public void bindArrayBuffer(int buffer) {
        if (lastArrayBuffer != buffer) {
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
            lastArrayBuffer = buffer;
        }
    }

    public void deleteArrayBuffer(int buffer) {
        if (lastArrayBuffer == buffer) {
            bindArrayBuffer(0);
            lastArrayBuffer = -1;
        }
        GL15.glDeleteBuffers(buffer);
    }

    public void bindUniformBuffer(int buffer) {
        if (lastUniformBuffer != buffer) {
            GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, buffer);
            lastUniformBuffer = buffer;
        }
    }

    public void deleteUniformBuffer(int buffer) {
        if (lastUniformBuffer == buffer) {
            bindUniformBuffer(0);
            lastUniformBuffer = -1;
        }
        GL15.glDeleteBuffers(buffer);
    }

    public void clearColor(float red, float green, float blue, float alpha) {
        float lastRed = lastClearColor[0];
        float lastGreen = lastClearColor[1];
        float lastBlue = lastClearColor[2];
        float lastAlpha = lastClearColor[3];

        if (lastRed != red || lastGreen != green || lastBlue != blue || lastAlpha != alpha) {
            GL11.glClearColor(red, green, blue, alpha);

            lastClearColor[0] = red;
            lastClearColor[1] = green;
            lastClearColor[2] = blue;
            lastClearColor[3] = alpha;
        }
    }

    public void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        boolean lastRed = lastColorMask[0];
        boolean lastGreen = lastColorMask[1];
        boolean lastBlue = lastColorMask[2];
        boolean lastAlpha = lastColorMask[3];

        if (lastRed != red || lastGreen != green || lastBlue != blue || lastAlpha != alpha) {
            GL11.glColorMask(red, green, blue, alpha);

            lastColorMask[0] = red;
            lastColorMask[1] = green;
            lastColorMask[2] = blue;
            lastColorMask[3] = alpha;
        }
    }

    public void stencilFunc(int func, int ref, int mask) {
        int lastFunc = lastStencilFunc[0];
        int lastRef = lastStencilFunc[1];
        int lastMask = lastStencilFunc[2];

        if (lastFunc != func || lastRef != ref || lastMask != mask) {
            GL11.glStencilFunc(func, ref, mask);

            lastStencilFunc[0] = func;
            lastStencilFunc[1] = ref;
            lastStencilFunc[2] = mask;
        }
    }

    public void stencilOp(int sfail, int dpfail, int dppass) {
        int lastSFail = lastStencilOp[0];
        int lastDPFail = lastStencilOp[1];
        int lastDPPass = lastStencilOp[2];

        if (lastSFail != sfail || lastDPFail != dpfail || lastDPPass != dppass) {
            GL11.glStencilOp(sfail, dpfail, dppass);

            lastStencilOp[0] = sfail;
            lastStencilOp[1] = dpfail;
            lastStencilOp[2] = dppass;
        }
    }

    public void clearDepth(double depth) {
        if (lastClearDepth != depth) {
            GL11.glClearDepth(depth);
            lastClearDepth = depth;
        }
    }

    public void clearStencil(int s) {
        if (lastClearStencil != s) {
            GL11.glClearStencil(s);
            lastClearStencil = s;
        }
    }

    public void depthMask(boolean flag) {
        if (lastDepthMask != flag) {
            GL11.glDepthMask(flag);
            lastDepthMask = flag;
        }
    }

    public void depthFunc(int func) {
        if (lastDepthFunc != func) {
            GL11.glDepthFunc(func);
            lastDepthFunc = func;
        }
    }

    public void cullFace(int mode) {
        if (lastCullFace != mode) {
            GL11.glCullFace(mode);
            lastCullFace = mode;
        }
    }

    public void frontFace(int dir) {
        if (lastFrontFace != dir) {
            GL11.glFrontFace(dir);
            lastFrontFace = dir;
        }
    }

    public void polygonMode(int face, int mode) {
        if (lastPolygonMode[0] != face || lastPolygonMode[1] != mode) {
            GL11.glPolygonMode(face, mode);
            lastPolygonMode[0] = face;
            lastPolygonMode[1] = mode;
        }
    }

    public void polygonOffset(float factor, float units) {
        if (lastPolygonOffset[0] != factor || lastPolygonOffset[1] != units) {
            GL11.glPolygonOffset(factor, units);
            lastPolygonOffset[0] = factor;
            lastPolygonOffset[1] = units;
        }
    }

    public void lineWidth(float width) {
        if (lastLineWidth != width) {
            GL11.glLineWidth(width);
            lastLineWidth = width;
        }
    }

    public void pointSize(float size) {
        if (lastPointSize != size) {
            GL11.glPointSize(size);
            lastPointSize = size;
        }
    }

    public void scissor(int x, int y, int width, int height) {
        if (lastScissorBox[0] != x || lastScissorBox[1] != y || lastScissorBox[2] != width || lastScissorBox[3] != height) {
            GL11.glScissor(x, y, width, height);
            lastScissorBox[0] = x;
            lastScissorBox[1] = y;
            lastScissorBox[2] = width;
            lastScissorBox[3] = height;
        }
    }

    public void viewport(int x, int y, int width, int height) {
        if (lastViewport[0] != x || lastViewport[1] != y || lastViewport[2] != width || lastViewport[3] != height) {
            GL11.glViewport(x, y, width, height);
            lastViewport[0] = x;
            lastViewport[1] = y;
            lastViewport[2] = width;
            lastViewport[3] = height;
        }
    }

    public void depthRange(float near, float far) {
        if (lastDepthRange[0] != near || lastDepthRange[1] != far) {
            GL11.glDepthRange(near, far);
            lastDepthRange[0] = near;
            lastDepthRange[1] = far;
        }
    }

    public void sampleMaski(int maskNumber, int mask) {
        if (lastSampleMaskValue != mask) {
            GL32.glSampleMaski(maskNumber, mask);
            lastSampleMaskValue = mask;
        }
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
                if ((int) args[0] == GL30.GL_FRAMEBUFFER) {
                    lastFrameBuffer = (int) args[1];
                } else if ((int) args[0] == GL30.GL_DRAW_FRAMEBUFFER) {
                    lastDrawFrameBuffer = (int) args[1];
                } else if ((int) args[0] == GL30.GL_READ_FRAMEBUFFER) {
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
                if ((int) args[0] == GL40.GL_DRAW_INDIRECT_BUFFER) {
                    lastDrawIndirectBuffer = (int) args[1];
                } else if ((int) args[0] == GL40.GL_ELEMENT_ARRAY_BUFFER) {
                    lastElementArrayBuffer = (int) args[1];
                } else if ((int) args[0] == GL15.GL_ARRAY_BUFFER) {
                    lastArrayBuffer = (int) args[1];
                } else if ((int) args[0] == GL31.GL_UNIFORM_BUFFER) {
                    lastUniformBuffer = (int) args[1];
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
        }
    }
}
