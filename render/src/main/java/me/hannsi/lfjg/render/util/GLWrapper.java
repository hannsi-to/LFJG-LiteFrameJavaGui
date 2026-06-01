package me.hannsi.lfjg.render.util;

import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.*;

import java.nio.*;

import static me.hannsi.lfjg.render.LFJGRenderContext.GL_CAPABILITIES;

public class GLWrapper {
    private static void throwUnsupportedOperationException(String functionName) {
        throw new UnsupportedOperationException("Method " + functionName + " not available");
    }

    // --- GL11 ---
    public static void glGetIntegerv(int pname, IntBuffer data) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetIntegerv(pname, data);
        } else {
            throw new UnsupportedOperationException("glGetIntegerv");
        }
    }

    public static void glGetIntegerv(int pname, int[] data) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetIntegerv(pname, data);
        } else {
            throw new UnsupportedOperationException("glGetIntegerv");
        }
    }

    public static String glGetString(int name) {
        if (GL_CAPABILITIES.OpenGL11) {
            return GL11.glGetString(name);
        } else {
            throw new UnsupportedOperationException("glGetString");
        }
    }

    public static int glGetError() {
        if (GL_CAPABILITIES.OpenGL11) {
            return GL11.glGetError();
        } else {
            throw new UnsupportedOperationException("glGetError");
        }
    }

    public static void glEdgeFlag(boolean flag) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEdgeFlag(flag);
        } else {
            throw new UnsupportedOperationException("glEdgeFlag");
        }
    }

    public static void glColor4f(float red, float green, float blue, float alpha) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4f(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glColor4f");
        }
    }

    public static void glEnd() {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEnd();
        } else {
            throw new UnsupportedOperationException("glEnd");
        }
    }

    public static void glAccum(int op, float value) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glAccum(op, value);
        } else {
            throw new UnsupportedOperationException("glAccum");
        }
    }

    public static void glColor3bv(ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3bv(v);
        } else {
            throw new UnsupportedOperationException("glColor3bv");
        }
    }

    public static void glColor3s(short red, short green, short blue) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3s(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glColor3s");
        }
    }

    public static void glColor3us(short red, short green, short blue) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3us(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glColor3us");
        }
    }

    public static void glBitmap(int width, int height, float xorig, float yorig, float xmove, float ymove, long bitmap) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glBitmap(width, height, xorig, yorig, xmove, ymove, bitmap);
        } else {
            throw new UnsupportedOperationException("glBitmap");
        }
    }

    public static void glBitmap(int width, int height, float xorig, float yorig, float xmove, float ymove, ByteBuffer bitmap) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glBitmap(width, height, xorig, yorig, xmove, ymove, bitmap);
        } else {
            throw new UnsupportedOperationException("glBitmap");
        }
    }

    public static void glColor3b(byte red, byte green, byte blue) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3b(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glColor3b");
        }
    }

    public static void glColor3fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3fv(v);
        } else {
            throw new UnsupportedOperationException("glColor3fv");
        }
    }

    public static void glColor3fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3fv(v);
        } else {
            throw new UnsupportedOperationException("glColor3fv");
        }
    }

    public static void glColor4s(short red, short green, short blue, short alpha) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4s(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glColor4s");
        }
    }

    public static void glColor4i(int red, int green, int blue, int alpha) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4i(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glColor4i");
        }
    }

    public static void glColor4us(short red, short green, short blue, short alpha) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4us(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glColor4us");
        }
    }

    public static void glColor4bv(ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4bv(v);
        } else {
            throw new UnsupportedOperationException("glColor4bv");
        }
    }

    public static void glColor4fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4fv(v);
        } else {
            throw new UnsupportedOperationException("glColor4fv");
        }
    }

    public static void glColor4fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4fv(v);
        } else {
            throw new UnsupportedOperationException("glColor4fv");
        }
    }

    public static void glColor4ui(int red, int green, int blue, int alpha) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4ui(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glColor4ui");
        }
    }

    public static void glCallList(int list) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glCallList(list);
        } else {
            throw new UnsupportedOperationException("glCallList");
        }
    }

    public static void glColor3d(double red, double green, double blue) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3d(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glColor3d");
        }
    }

    public static void glColor3ub(byte red, byte green, byte blue) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3ub(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glColor3ub");
        }
    }

    public static void glColor3dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3dv(v);
        } else {
            throw new UnsupportedOperationException("glColor3dv");
        }
    }

    public static void glColor3dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3dv(v);
        } else {
            throw new UnsupportedOperationException("glColor3dv");
        }
    }

    public static void glColor4d(double red, double green, double blue, double alpha) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4d(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glColor4d");
        }
    }

    public static void glDisable(int cap) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDisable(cap);
        } else {
            throw new UnsupportedOperationException("glDisable");
        }
    }

    public static void glColor4sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4sv(v);
        } else {
            throw new UnsupportedOperationException("glColor4sv");
        }
    }

    public static void glColor4sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4sv(v);
        } else {
            throw new UnsupportedOperationException("glColor4sv");
        }
    }

    public static void glColor4iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4iv(v);
        } else {
            throw new UnsupportedOperationException("glColor4iv");
        }
    }

    public static void glColor4iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4iv(v);
        } else {
            throw new UnsupportedOperationException("glColor4iv");
        }
    }

    public static void glClear(int mask) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glClear(mask);
        } else {
            throw new UnsupportedOperationException("glClear");
        }
    }

    public static void glEnable(int cap) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glEnable(cap);
        } else {
            throw new UnsupportedOperationException("glEnable");
        }
    }

    public static void glBegin(int mode) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glBegin(mode);
        } else {
            throw new UnsupportedOperationException("glBegin");
        }
    }

    public static void glColor3f(float red, float green, float blue) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3f(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glColor3f");
        }
    }

    public static void glColor3sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3sv(v);
        } else {
            throw new UnsupportedOperationException("glColor3sv");
        }
    }

    public static void glColor3sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3sv(v);
        } else {
            throw new UnsupportedOperationException("glColor3sv");
        }
    }

    public static void glColor4b(byte red, byte green, byte blue, byte alpha) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4b(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glColor4b");
        }
    }

    public static void glColor3ui(int red, int green, int blue) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3ui(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glColor3ui");
        }
    }

    public static void glColor4ub(byte red, byte green, byte blue, byte alpha) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4ub(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glColor4ub");
        }
    }

    public static void glColor4dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4dv(v);
        } else {
            throw new UnsupportedOperationException("glColor4dv");
        }
    }

    public static void glColor4dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4dv(v);
        } else {
            throw new UnsupportedOperationException("glColor4dv");
        }
    }

    public static void glCullFace(int mode) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glCullFace(mode);
        } else {
            throw new UnsupportedOperationException("glCullFace");
        }
    }

    public static void glColor3i(int red, int green, int blue) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3i(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glColor3i");
        }
    }

    public static void glColor3iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3iv(v);
        } else {
            throw new UnsupportedOperationException("glColor3iv");
        }
    }

    public static void glColor3iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3iv(v);
        } else {
            throw new UnsupportedOperationException("glColor3iv");
        }
    }

    public static void glMap2d(int target, double u1, double u2, int ustride, int uorder, double v1, double v2, int vstride, int vorder, DoubleBuffer points) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMap2d(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, points);
        } else {
            throw new UnsupportedOperationException("glMap2d");
        }
    }

    public static void glMap2d(int target, double u1, double u2, int ustride, int uorder, double v1, double v2, int vstride, int vorder, double[] points) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMap2d(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, points);
        } else {
            throw new UnsupportedOperationException("glMap2d");
        }
    }

    public static void glFogiv(int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glFogiv(pname, params);
        } else {
            throw new UnsupportedOperationException("glFogiv");
        }
    }

    public static void glFogiv(int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glFogiv(pname, params);
        } else {
            throw new UnsupportedOperationException("glFogiv");
        }
    }

    public static void glGetMapdv(int target, int query, double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetMapdv(target, query, v);
        } else {
            throw new UnsupportedOperationException("glGetMapdv");
        }
    }

    public static void glGetMapdv(int target, int query, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetMapdv(target, query, v);
        } else {
            throw new UnsupportedOperationException("glGetMapdv");
        }
    }

    public static void glIndexi(int c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexi(c);
        } else {
            throw new UnsupportedOperationException("glIndexi");
        }
    }

    public static boolean glIsList(int list) {
        if (GL_CAPABILITIES.OpenGL32) {
            return GL11.glIsList(list);
        } else {
            throw new UnsupportedOperationException("glIsList");
        }
    }

    public static void glLightiv(int light, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightiv(light, pname, params);
        } else {
            throw new UnsupportedOperationException("glLightiv");
        }
    }

    public static void glLightiv(int light, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightiv(light, pname, params);
        } else {
            throw new UnsupportedOperationException("glLightiv");
        }
    }

    public static void glFogfv(int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glFogfv(pname, params);
        } else {
            throw new UnsupportedOperationException("glFogfv");
        }
    }

    public static void glFogfv(int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glFogfv(pname, params);
        } else {
            throw new UnsupportedOperationException("glFogfv");
        }
    }

    public static float glGetMapf(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetMapf has no caps");
    }

    public static void glIndexd(double c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexd(c);
        } else {
            throw new UnsupportedOperationException("glIndexd");
        }
    }

    public static void glFlush() {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glFlush();
        } else {
            throw new UnsupportedOperationException("glFlush");
        }
    }

    public static void glLightfv(int light, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightfv(light, pname, params);
        } else {
            throw new UnsupportedOperationException("glLightfv");
        }
    }

    public static void glLightfv(int light, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightfv(light, pname, params);
        } else {
            throw new UnsupportedOperationException("glLightfv");
        }
    }

    public static void glIndexs(short c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexs(c);
        } else {
            throw new UnsupportedOperationException("glIndexs");
        }
    }

    public static void glFinish() {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glFinish();
        } else {
            throw new UnsupportedOperationException("glFinish");
        }
    }

    public static void glNewList(int list, int mode) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNewList(list, mode);
        } else {
            throw new UnsupportedOperationException("glNewList");
        }
    }

    public static void glFogi(int pname, int param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glFogi(pname, param);
        } else {
            throw new UnsupportedOperationException("glFogi");
        }
    }

    public static void glEndList() {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEndList();
        } else {
            throw new UnsupportedOperationException("glEndList");
        }
    }

    public static void glNormal3f(float nx, float ny, float nz) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3f(nx, ny, nz);
        } else {
            throw new UnsupportedOperationException("glNormal3f");
        }
    }

    public static int glGenLists(int range) {
        if (GL_CAPABILITIES.OpenGL32) {
            return GL11.glGenLists(range);
        } else {
            throw new UnsupportedOperationException("glGenLists");
        }
    }

    public static void glLoadName(int name) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLoadName(name);
        } else {
            throw new UnsupportedOperationException("glLoadName");
        }
    }

    public static void glNormal3b(byte nx, byte ny, byte nz) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3b(nx, ny, nz);
        } else {
            throw new UnsupportedOperationException("glNormal3b");
        }
    }

    public static void glLighti(int light, int pname, int param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLighti(light, pname, param);
        } else {
            throw new UnsupportedOperationException("glLighti");
        }
    }

    public static void glFrustum(double left, double right, double bottom, double top, double zNear, double zFar) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glFrustum(left, right, bottom, top, zNear, zFar);
        } else {
            throw new UnsupportedOperationException("glFrustum");
        }
    }

    public static void glIndexub(byte c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexub(c);
        } else {
            throw new UnsupportedOperationException("glIndexub");
        }
    }

    public static int glGetMapi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetMapi has no caps");
    }

    public static void glGetMapfv(int target, int query, float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetMapfv(target, query, v);
        } else {
            throw new UnsupportedOperationException("glGetMapfv");
        }
    }

    public static void glGetMapfv(int target, int query, FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetMapfv(target, query, v);
        } else {
            throw new UnsupportedOperationException("glGetMapfv");
        }
    }

    public static void glMap1d(int target, double u1, double u2, int stride, int order, double[] points) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMap1d(target, u1, u2, stride, order, points);
        } else {
            throw new UnsupportedOperationException("glMap1d");
        }
    }

    public static void glMap1d(int target, double u1, double u2, int stride, int order, DoubleBuffer points) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMap1d(target, u1, u2, stride, order, points);
        } else {
            throw new UnsupportedOperationException("glMap1d");
        }
    }

    public static void glIndexfv(FloatBuffer c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexfv(c);
        } else {
            throw new UnsupportedOperationException("glIndexfv");
        }
    }

    public static void glIndexfv(float[] c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexfv(c);
        } else {
            throw new UnsupportedOperationException("glIndexfv");
        }
    }

    public static void glGetMapiv(int target, int query, int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetMapiv(target, query, v);
        } else {
            throw new UnsupportedOperationException("glGetMapiv");
        }
    }

    public static void glGetMapiv(int target, int query, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetMapiv(target, query, v);
        } else {
            throw new UnsupportedOperationException("glGetMapiv");
        }
    }

    public static void glIndexf(float c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexf(c);
        } else {
            throw new UnsupportedOperationException("glIndexf");
        }
    }

    public static float glGetFloat(int arg0) {
        throw new UnsupportedOperationException("glGetFloat has no caps");
    }

    public static void glListBase(int base) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glListBase(base);
        } else {
            throw new UnsupportedOperationException("glListBase");
        }
    }

    public static void glIndexubv(ByteBuffer c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexubv(c);
        } else {
            throw new UnsupportedOperationException("glIndexubv");
        }
    }

    public static void glIndexiv(IntBuffer c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexiv(c);
        } else {
            throw new UnsupportedOperationException("glIndexiv");
        }
    }

    public static void glIndexiv(int[] c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexiv(c);
        } else {
            throw new UnsupportedOperationException("glIndexiv");
        }
    }

    public static double glGetMapd(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetMapd has no caps");
    }

    public static void glHint(int target, int mode) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glHint(target, mode);
        } else {
            throw new UnsupportedOperationException("glHint");
        }
    }

    public static void glIndexsv(short[] c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexsv(c);
        } else {
            throw new UnsupportedOperationException("glIndexsv");
        }
    }

    public static void glIndexsv(ShortBuffer c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexsv(c);
        } else {
            throw new UnsupportedOperationException("glIndexsv");
        }
    }

    public static void glLogicOp(int opcode) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glLogicOp(opcode);
        } else {
            throw new UnsupportedOperationException("glLogicOp");
        }
    }

    public static void glMap2f(int target, float u1, float u2, int ustride, int uorder, float v1, float v2, int vstride, int vorder, FloatBuffer points) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMap2f(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, points);
        } else {
            throw new UnsupportedOperationException("glMap2f");
        }
    }

    public static void glMap2f(int target, float u1, float u2, int ustride, int uorder, float v1, float v2, int vstride, int vorder, float[] points) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMap2f(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, points);
        } else {
            throw new UnsupportedOperationException("glMap2f");
        }
    }

    public static void glFogf(int pname, float param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glFogf(pname, param);
        } else {
            throw new UnsupportedOperationException("glFogf");
        }
    }

    public static void glIndexdv(double[] c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexdv(c);
        } else {
            throw new UnsupportedOperationException("glIndexdv");
        }
    }

    public static void glIndexdv(DoubleBuffer c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexdv(c);
        } else {
            throw new UnsupportedOperationException("glIndexdv");
        }
    }

    public static void glLightf(int light, int pname, float param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightf(light, pname, param);
        } else {
            throw new UnsupportedOperationException("glLightf");
        }
    }

    public static void glMap1f(int target, float u1, float u2, int stride, int order, FloatBuffer points) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMap1f(target, u1, u2, stride, order, points);
        } else {
            throw new UnsupportedOperationException("glMap1f");
        }
    }

    public static void glMap1f(int target, float u1, float u2, int stride, int order, float[] points) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMap1f(target, u1, u2, stride, order, points);
        } else {
            throw new UnsupportedOperationException("glMap1f");
        }
    }

    public static void glTexEnvi(int target, int pname, int param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexEnvi(target, pname, param);
        } else {
            throw new UnsupportedOperationException("glTexEnvi");
        }
    }

    public static void glRectsv(ShortBuffer v1, ShortBuffer v2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRectsv(v1, v2);
        } else {
            throw new UnsupportedOperationException("glRectsv");
        }
    }

    public static void glRectsv(short[] v1, short[] v2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRectsv(v1, v2);
        } else {
            throw new UnsupportedOperationException("glRectsv");
        }
    }

    public static void glRotatef(float angle, float x, float y, float z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRotatef(angle, x, y, z);
        } else {
            throw new UnsupportedOperationException("glRotatef");
        }
    }

    public static void glVertex2s(short x, short y) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2s(x, y);
        } else {
            throw new UnsupportedOperationException("glVertex2s");
        }
    }

    public static void glTexGenfv(int coord, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexGenfv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexGenfv");
        }
    }

    public static void glTexGenfv(int coord, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexGenfv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexGenfv");
        }
    }

    public static void glTexGendv(int coord, int pname, double[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexGendv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexGendv");
        }
    }

    public static void glTexGendv(int coord, int pname, DoubleBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexGendv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexGendv");
        }
    }

    public static void glVertex3i(int x, int y, int z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3i(x, y, z);
        } else {
            throw new UnsupportedOperationException("glVertex3i");
        }
    }

    public static void glTexGenf(int coord, int pname, float param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexGenf(coord, pname, param);
        } else {
            throw new UnsupportedOperationException("glTexGenf");
        }
    }

    public static void glVertex4f(float x, float y, float z, float w) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4f(x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertex4f");
        }
    }

    public static void glRectf(float x1, float y1, float x2, float y2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRectf(x1, y1, x2, y2);
        } else {
            throw new UnsupportedOperationException("glRectf");
        }
    }

    public static void glVertex4i(int x, int y, int z, int w) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4i(x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertex4i");
        }
    }

    public static void glViewport(int x, int y, int width, int height) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glViewport(x, y, width, height);
        } else {
            throw new UnsupportedOperationException("glViewport");
        }
    }

    public static void glPushName(int name) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPushName(name);
        } else {
            throw new UnsupportedOperationException("glPushName");
        }
    }

    public static void glVertex3s(short x, short y, short z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3s(x, y, z);
        } else {
            throw new UnsupportedOperationException("glVertex3s");
        }
    }

    public static void glNormal3i(int nx, int ny, int nz) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3i(nx, ny, nz);
        } else {
            throw new UnsupportedOperationException("glNormal3i");
        }
    }

    public static void glTexEnviv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexEnviv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexEnviv");
        }
    }

    public static void glTexEnviv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexEnviv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexEnviv");
        }
    }

    public static void glVertex4s(short x, short y, short z, short w) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4s(x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertex4s");
        }
    }

    public static void glRects(short x1, short y1, short x2, short y2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRects(x1, y1, x2, y2);
        } else {
            throw new UnsupportedOperationException("glRects");
        }
    }

    public static void glTexGeni(int coord, int pname, int param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexGeni(coord, pname, param);
        } else {
            throw new UnsupportedOperationException("glTexGeni");
        }
    }

    public static void glRectfv(FloatBuffer v1, FloatBuffer v2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRectfv(v1, v2);
        } else {
            throw new UnsupportedOperationException("glRectfv");
        }
    }

    public static void glRectfv(float[] v1, float[] v2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRectfv(v1, v2);
        } else {
            throw new UnsupportedOperationException("glRectfv");
        }
    }

    public static void glTexGeniv(int coord, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexGeniv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexGeniv");
        }
    }

    public static void glTexGeniv(int coord, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexGeniv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexGeniv");
        }
    }

    public static void glScaled(double x, double y, double z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glScaled(x, y, z);
        } else {
            throw new UnsupportedOperationException("glScaled");
        }
    }

    public static void glTexEnvfv(int target, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexEnvfv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexEnvfv");
        }
    }

    public static void glTexEnvfv(int target, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexEnvfv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexEnvfv");
        }
    }

    public static void glVertex2f(float x, float y) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2f(x, y);
        } else {
            throw new UnsupportedOperationException("glVertex2f");
        }
    }

    public static void glNormal3d(double nx, double ny, double nz) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3d(nx, ny, nz);
        } else {
            throw new UnsupportedOperationException("glNormal3d");
        }
    }

    public static void glVertex2d(double x, double y) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2d(x, y);
        } else {
            throw new UnsupportedOperationException("glVertex2d");
        }
    }

    public static void glVertex3f(float x, float y, float z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3f(x, y, z);
        } else {
            throw new UnsupportedOperationException("glVertex3f");
        }
    }

    public static void glVertex3d(double x, double y, double z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3d(x, y, z);
        } else {
            throw new UnsupportedOperationException("glVertex3d");
        }
    }

    public static void glVertex4d(double x, double y, double z, double w) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4d(x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertex4d");
        }
    }

    public static void glScalef(float x, float y, float z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glScalef(x, y, z);
        } else {
            throw new UnsupportedOperationException("glScalef");
        }
    }

    public static void glRectd(double x1, double y1, double x2, double y2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRectd(x1, y1, x2, y2);
        } else {
            throw new UnsupportedOperationException("glRectd");
        }
    }

    public static void glRectiv(int[] v1, int[] v2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRectiv(v1, v2);
        } else {
            throw new UnsupportedOperationException("glRectiv");
        }
    }

    public static void glRectiv(IntBuffer v1, IntBuffer v2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRectiv(v1, v2);
        } else {
            throw new UnsupportedOperationException("glRectiv");
        }
    }

    public static void glRecti(int x1, int y1, int x2, int y2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRecti(x1, y1, x2, y2);
        } else {
            throw new UnsupportedOperationException("glRecti");
        }
    }

    public static void glTexGend(int coord, int pname, double param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexGend(coord, pname, param);
        } else {
            throw new UnsupportedOperationException("glTexGend");
        }
    }

    public static void glNormal3s(short nx, short ny, short nz) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3s(nx, ny, nz);
        } else {
            throw new UnsupportedOperationException("glNormal3s");
        }
    }

    public static void glOrtho(double left, double right, double bottom, double top, double zNear, double zFar) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glOrtho(left, right, bottom, top, zNear, zFar);
        } else {
            throw new UnsupportedOperationException("glOrtho");
        }
    }

    public static void glRotated(double angle, double x, double y, double z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRotated(angle, x, y, z);
        } else {
            throw new UnsupportedOperationException("glRotated");
        }
    }

    public static void glVertex2i(int x, int y) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2i(x, y);
        } else {
            throw new UnsupportedOperationException("glVertex2i");
        }
    }

    public static void glPopName() {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPopName();
        } else {
            throw new UnsupportedOperationException("glPopName");
        }
    }

    public static void glScissor(int x, int y, int width, int height) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glScissor(x, y, width, height);
        } else {
            throw new UnsupportedOperationException("glScissor");
        }
    }

    public static void glTexEnvf(int target, int pname, float param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexEnvf(target, pname, param);
        } else {
            throw new UnsupportedOperationException("glTexEnvf");
        }
    }

    public static void glRectdv(DoubleBuffer v1, DoubleBuffer v2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRectdv(v1, v2);
        } else {
            throw new UnsupportedOperationException("glRectdv");
        }
    }

    public static void glRectdv(double[] v1, double[] v2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRectdv(v1, v2);
        } else {
            throw new UnsupportedOperationException("glRectdv");
        }
    }

    public static void glInterleavedArrays(int format, int stride, float[] pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInterleavedArrays(format, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glInterleavedArrays");
        }
    }

    public static void glInterleavedArrays(int format, int stride, int[] pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInterleavedArrays(format, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glInterleavedArrays");
        }
    }

    public static void glInterleavedArrays(int format, int stride, double[] pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInterleavedArrays(format, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glInterleavedArrays");
        }
    }

    public static void glInterleavedArrays(int format, int stride, short[] pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInterleavedArrays(format, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glInterleavedArrays");
        }
    }

    public static void glInterleavedArrays(int format, int stride, ShortBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInterleavedArrays(format, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glInterleavedArrays");
        }
    }

    public static void glInterleavedArrays(int format, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInterleavedArrays(format, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glInterleavedArrays");
        }
    }

    public static void glInterleavedArrays(int format, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInterleavedArrays(format, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glInterleavedArrays");
        }
    }

    public static void glInterleavedArrays(int format, int stride, IntBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInterleavedArrays(format, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glInterleavedArrays");
        }
    }

    public static void glInterleavedArrays(int format, int stride, FloatBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInterleavedArrays(format, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glInterleavedArrays");
        }
    }

    public static void glInterleavedArrays(int format, int stride, DoubleBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInterleavedArrays(format, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glInterleavedArrays");
        }
    }

    public static void glEnableClientState(int array) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEnableClientState(array);
        } else {
            throw new UnsupportedOperationException("glEnableClientState");
        }
    }

    public static void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexParameterfv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexParameterfv");
        }
    }

    public static void glGetTexParameterfv(int target, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexParameterfv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexParameterfv");
        }
    }

    public static void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
        } else {
            throw new UnsupportedOperationException("glCopyTexSubImage2D");
        }
    }

    public static void glGetTexLevelParameteriv(int target, int level, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexLevelParameteriv(target, level, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexLevelParameteriv");
        }
    }

    public static void glGetTexLevelParameteriv(int target, int level, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexLevelParameteriv(target, level, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexLevelParameteriv");
        }
    }

    public static float glGetTexLevelParameterf(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetTexLevelParameterf has no caps");
    }

    public static void glPrioritizeTextures(IntBuffer n, FloatBuffer textures) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPrioritizeTextures(n, textures);
        } else {
            throw new UnsupportedOperationException("glPrioritizeTextures");
        }
    }

    public static void glPrioritizeTextures(int[] n, float[] textures) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPrioritizeTextures(n, textures);
        } else {
            throw new UnsupportedOperationException("glPrioritizeTextures");
        }
    }

    public static void glGetTexLevelParameterfv(int target, int level, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexLevelParameterfv(target, level, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexLevelParameterfv");
        }
    }

    public static void glGetTexLevelParameterfv(int target, int level, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexLevelParameterfv(target, level, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexLevelParameterfv");
        }
    }

    public static void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glCopyTexSubImage1D(target, level, xoffset, x, y, width);
        } else {
            throw new UnsupportedOperationException("glCopyTexSubImage1D");
        }
    }

    public static void glGetTexParameteriv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexParameteriv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexParameteriv");
        }
    }

    public static void glGetTexParameteriv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexParameteriv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexParameteriv");
        }
    }

    public static void glGetPolygonStipple(long mask) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPolygonStipple(mask);
        } else {
            throw new UnsupportedOperationException("glGetPolygonStipple");
        }
    }

    public static void glGetPolygonStipple(ByteBuffer mask) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPolygonStipple(mask);
        } else {
            throw new UnsupportedOperationException("glGetPolygonStipple");
        }
    }

    public static boolean glAreTexturesResident(int[] n, ByteBuffer textures) {
        if (GL_CAPABILITIES.OpenGL32) {
            return GL11.glAreTexturesResident(n, textures);
        } else {
            throw new UnsupportedOperationException("glAreTexturesResident");
        }
    }

    public static boolean glAreTexturesResident(IntBuffer n, ByteBuffer textures) {
        if (GL_CAPABILITIES.OpenGL32) {
            return GL11.glAreTexturesResident(n, textures);
        } else {
            throw new UnsupportedOperationException("glAreTexturesResident");
        }
    }

    public static boolean glAreTexturesResident(int n, ByteBuffer textures) {
        if (GL_CAPABILITIES.OpenGL32) {
            return GL11.glAreTexturesResident(n, textures);
        } else {
            throw new UnsupportedOperationException("glAreTexturesResident");
        }
    }

    public static void glDisableClientState(int array) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glDisableClientState(array);
        } else {
            throw new UnsupportedOperationException("glDisableClientState");
        }
    }

    public static void glClearIndex(float c) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glClearIndex(c);
        } else {
            throw new UnsupportedOperationException("glClearIndex");
        }
    }

    public static void glClearStencil(int s) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glClearStencil(s);
        } else {
            throw new UnsupportedOperationException("glClearStencil");
        }
    }

    public static void glClearColor(float red, float green, float blue, float alpha) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glClearColor(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glClearColor");
        }
    }

    public static void glBindTexture(int target, int texture) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glBindTexture(target, texture);
        } else {
            throw new UnsupportedOperationException("glBindTexture");
        }
    }

    public static void glArrayElement(int i) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glArrayElement(i);
        } else {
            throw new UnsupportedOperationException("glArrayElement");
        }
    }

    public static void glAlphaFunc(int func, float ref) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glAlphaFunc(func, ref);
        } else {
            throw new UnsupportedOperationException("glAlphaFunc");
        }
    }

    public static void glBlendFunc(int sfactor, int dfactor) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glBlendFunc(sfactor, dfactor);
        } else {
            throw new UnsupportedOperationException("glBlendFunc");
        }
    }

    public static void glCallLists(ShortBuffer n) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glCallLists(n);
        } else {
            throw new UnsupportedOperationException("glCallLists");
        }
    }

    public static void glCallLists(int n, ByteBuffer type) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glCallLists(n, type);
        } else {
            throw new UnsupportedOperationException("glCallLists");
        }
    }

    public static void glCallLists(ByteBuffer n) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glCallLists(n);
        } else {
            throw new UnsupportedOperationException("glCallLists");
        }
    }

    public static void glCallLists(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glCallLists(n);
        } else {
            throw new UnsupportedOperationException("glCallLists");
        }
    }

    public static void glClearAccum(float red, float green, float blue, float alpha) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glClearAccum(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glClearAccum");
        }
    }

    public static void glClearDepth(double depth) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glClearDepth(depth);
        } else {
            throw new UnsupportedOperationException("glClearDepth");
        }
    }

    public static void glDepthFunc(int func) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDepthFunc(func);
        } else {
            throw new UnsupportedOperationException("glDepthFunc");
        }
    }

    public static void glColor3ubv(ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3ubv(v);
        } else {
            throw new UnsupportedOperationException("glColor3ubv");
        }
    }

    public static void glColor3uiv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3uiv(v);
        } else {
            throw new UnsupportedOperationException("glColor3uiv");
        }
    }

    public static void glColor3uiv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3uiv(v);
        } else {
            throw new UnsupportedOperationException("glColor3uiv");
        }
    }

    public static void glColorPointer(int size, int type, int stride, ShortBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColorPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glColorPointer");
        }
    }

    public static void glColorPointer(int size, int type, int stride, FloatBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColorPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glColorPointer");
        }
    }

    public static void glColorPointer(int size, int type, int stride, IntBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColorPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glColorPointer");
        }
    }

    public static void glColorPointer(int size, int type, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColorPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glColorPointer");
        }
    }

    public static void glColorPointer(int size, int type, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColorPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glColorPointer");
        }
    }

    public static void glDepthRange(double n, double f) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDepthRange(n, f);
        } else {
            throw new UnsupportedOperationException("glDepthRange");
        }
    }

    public static void glDrawPixels(int width, int height, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glDrawPixels(width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glDrawPixels");
        }
    }

    public static void glDrawPixels(int width, int height, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glDrawPixels(width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glDrawPixels");
        }
    }

    public static void glDrawPixels(int width, int height, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glDrawPixels(width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glDrawPixels");
        }
    }

    public static void glDrawPixels(int width, int height, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glDrawPixels(width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glDrawPixels");
        }
    }

    public static void glDrawPixels(int width, int height, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glDrawPixels(width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glDrawPixels");
        }
    }

    public static void glDrawPixels(int width, int height, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glDrawPixels(width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glDrawPixels");
        }
    }

    public static void glDrawPixels(int width, int height, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glDrawPixels(width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glDrawPixels");
        }
    }

    public static void glDrawPixels(int width, int height, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glDrawPixels(width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glDrawPixels");
        }
    }

    public static void glEdgeFlagv(ByteBuffer flag) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEdgeFlagv(flag);
        } else {
            throw new UnsupportedOperationException("glEdgeFlagv");
        }
    }

    public static void glEvalCoord1dv(double[] u) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord1dv(u);
        } else {
            throw new UnsupportedOperationException("glEvalCoord1dv");
        }
    }

    public static void glEvalCoord1dv(DoubleBuffer u) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord1dv(u);
        } else {
            throw new UnsupportedOperationException("glEvalCoord1dv");
        }
    }

    public static void glEvalCoord2f(float u, float v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord2f(u, v);
        } else {
            throw new UnsupportedOperationException("glEvalCoord2f");
        }
    }

    public static void glColor4uiv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4uiv(v);
        } else {
            throw new UnsupportedOperationException("glColor4uiv");
        }
    }

    public static void glColor4uiv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4uiv(v);
        } else {
            throw new UnsupportedOperationException("glColor4uiv");
        }
    }

    public static void glDeleteLists(int list, int range) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glDeleteLists(list, range);
        } else {
            throw new UnsupportedOperationException("glDeleteLists");
        }
    }

    public static void glClipPlane(int plane, DoubleBuffer equation) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glClipPlane(plane, equation);
        } else {
            throw new UnsupportedOperationException("glClipPlane");
        }
    }

    public static void glClipPlane(int plane, double[] equation) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glClipPlane(plane, equation);
        } else {
            throw new UnsupportedOperationException("glClipPlane");
        }
    }

    public static void glDrawElements(int mode, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDrawElements(mode, count);
        } else {
            throw new UnsupportedOperationException("glDrawElements");
        }
    }

    public static void glDrawElements(int mode, int count, ByteBuffer type) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDrawElements(mode, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawElements");
        }
    }

    public static void glDrawElements(int mode, ShortBuffer count) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDrawElements(mode, count);
        } else {
            throw new UnsupportedOperationException("glDrawElements");
        }
    }

    public static void glDrawElements(int mode, int count, int type, long indices) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDrawElements(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawElements");
        }
    }

    public static void glDrawElements(int mode, ByteBuffer count) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDrawElements(mode, count);
        } else {
            throw new UnsupportedOperationException("glDrawElements");
        }
    }

    public static void glEvalCoord2d(double u, double v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord2d(u, v);
        } else {
            throw new UnsupportedOperationException("glEvalCoord2d");
        }
    }

    public static void glEvalMesh2(int mode, int i1, int i2, int j1, int j2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalMesh2(mode, i1, i2, j1, j2);
        } else {
            throw new UnsupportedOperationException("glEvalMesh2");
        }
    }

    public static void glEvalPoint2(int i, int j) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalPoint2(i, j);
        } else {
            throw new UnsupportedOperationException("glEvalPoint2");
        }
    }

    public static void glFeedbackBuffer(int size, float[] type) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glFeedbackBuffer(size, type);
        } else {
            throw new UnsupportedOperationException("glFeedbackBuffer");
        }
    }

    public static void glFeedbackBuffer(int size, FloatBuffer type) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glFeedbackBuffer(size, type);
        } else {
            throw new UnsupportedOperationException("glFeedbackBuffer");
        }
    }

    public static void glCopyPixels(int x, int y, int width, int height, int type) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glCopyPixels(x, y, width, height, type);
        } else {
            throw new UnsupportedOperationException("glCopyPixels");
        }
    }

    public static void glEvalCoord1d(double u) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord1d(u);
        } else {
            throw new UnsupportedOperationException("glEvalCoord1d");
        }
    }

    public static void glEvalCoord2dv(DoubleBuffer u) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord2dv(u);
        } else {
            throw new UnsupportedOperationException("glEvalCoord2dv");
        }
    }

    public static void glEvalCoord2dv(double[] u) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord2dv(u);
        } else {
            throw new UnsupportedOperationException("glEvalCoord2dv");
        }
    }

    public static void glColor3usv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3usv(v);
        } else {
            throw new UnsupportedOperationException("glColor3usv");
        }
    }

    public static void glColor3usv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor3usv(v);
        } else {
            throw new UnsupportedOperationException("glColor3usv");
        }
    }

    public static void glDrawBuffer(int buf) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDrawBuffer(buf);
        } else {
            throw new UnsupportedOperationException("glDrawBuffer");
        }
    }

    public static void glEvalMesh1(int mode, int i1, int i2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalMesh1(mode, i1, i2);
        } else {
            throw new UnsupportedOperationException("glEvalMesh1");
        }
    }

    public static void glEvalCoord1fv(FloatBuffer u) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord1fv(u);
        } else {
            throw new UnsupportedOperationException("glEvalCoord1fv");
        }
    }

    public static void glEvalCoord1fv(float[] u) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord1fv(u);
        } else {
            throw new UnsupportedOperationException("glEvalCoord1fv");
        }
    }

    public static void glFrontFace(int mode) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glFrontFace(mode);
        } else {
            throw new UnsupportedOperationException("glFrontFace");
        }
    }

    public static void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glColorMask(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glColorMask");
        }
    }

    public static void glColorMaterial(int face, int mode) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColorMaterial(face, mode);
        } else {
            throw new UnsupportedOperationException("glColorMaterial");
        }
    }

    public static void glColor4usv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4usv(v);
        } else {
            throw new UnsupportedOperationException("glColor4usv");
        }
    }

    public static void glColor4usv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4usv(v);
        } else {
            throw new UnsupportedOperationException("glColor4usv");
        }
    }

    public static void glDrawArrays(int mode, int first, int count) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDrawArrays(mode, first, count);
        } else {
            throw new UnsupportedOperationException("glDrawArrays");
        }
    }

    public static void glEvalCoord1f(float u) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord1f(u);
        } else {
            throw new UnsupportedOperationException("glEvalCoord1f");
        }
    }

    public static void glEvalCoord2fv(float[] u) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord2fv(u);
        } else {
            throw new UnsupportedOperationException("glEvalCoord2fv");
        }
    }

    public static void glEvalCoord2fv(FloatBuffer u) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalCoord2fv(u);
        } else {
            throw new UnsupportedOperationException("glEvalCoord2fv");
        }
    }

    public static void glEvalPoint1(int i) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEvalPoint1(i);
        } else {
            throw new UnsupportedOperationException("glEvalPoint1");
        }
    }

    public static void glEdgeFlagPointer(int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEdgeFlagPointer(stride, pointer);
        } else {
            throw new UnsupportedOperationException("glEdgeFlagPointer");
        }
    }

    public static void glEdgeFlagPointer(int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glEdgeFlagPointer(stride, pointer);
        } else {
            throw new UnsupportedOperationException("glEdgeFlagPointer");
        }
    }

    public static void glColor4ubv(ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glColor4ubv(v);
        } else {
            throw new UnsupportedOperationException("glColor4ubv");
        }
    }

    public static void glDepthMask(boolean flag) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDepthMask(flag);
        } else {
            throw new UnsupportedOperationException("glDepthMask");
        }
    }

    public static float glGetTexParameterf(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTexParameterf has no caps");
    }

    public static void glDeleteTextures(int[] n) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDeleteTextures(n);
        } else {
            throw new UnsupportedOperationException("glDeleteTextures");
        }
    }

    public static void glDeleteTextures(int n) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDeleteTextures(n);
        } else {
            throw new UnsupportedOperationException("glDeleteTextures");
        }
    }

    public static void glDeleteTextures(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glDeleteTextures(n);
        } else {
            throw new UnsupportedOperationException("glDeleteTextures");
        }
    }

    public static int glGetLighti(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetLighti has no caps");
    }

    public static void glIndexMask(int mask) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexMask(mask);
        } else {
            throw new UnsupportedOperationException("glIndexMask");
        }
    }

    public static int glGetTexEnvi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTexEnvi has no caps");
    }

    public static void glGetTexEnviv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetTexEnviv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexEnviv");
        }
    }

    public static void glGetTexEnviv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetTexEnviv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexEnviv");
        }
    }

    public static void glGetPixelMapusv(int map, short[] values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPixelMapusv(map, values);
        } else {
            throw new UnsupportedOperationException("glGetPixelMapusv");
        }
    }

    public static void glGetPixelMapusv(int map, ShortBuffer values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPixelMapusv(map, values);
        } else {
            throw new UnsupportedOperationException("glGetPixelMapusv");
        }
    }

    public static void glGetPixelMapusv(int map, long values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPixelMapusv(map, values);
        } else {
            throw new UnsupportedOperationException("glGetPixelMapusv");
        }
    }

    public static void glGetMaterialiv(int face, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetMaterialiv(face, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetMaterialiv");
        }
    }

    public static void glGetMaterialiv(int face, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetMaterialiv(face, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetMaterialiv");
        }
    }

    public static void glIndexPointer(int type, ShortBuffer stride) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexPointer(type, stride);
        } else {
            throw new UnsupportedOperationException("glIndexPointer");
        }
    }

    public static void glIndexPointer(int type, IntBuffer stride) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexPointer(type, stride);
        } else {
            throw new UnsupportedOperationException("glIndexPointer");
        }
    }

    public static void glIndexPointer(int type, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glIndexPointer");
        }
    }

    public static void glIndexPointer(int type, ByteBuffer stride) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexPointer(type, stride);
        } else {
            throw new UnsupportedOperationException("glIndexPointer");
        }
    }

    public static void glIndexPointer(int type, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glIndexPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glIndexPointer");
        }
    }

    public static void glGetDoublev(int pname, DoubleBuffer data) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetDoublev(pname, data);
        } else {
            throw new UnsupportedOperationException("glGetDoublev");
        }
    }

    public static void glGetDoublev(int pname, double[] data) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetDoublev(pname, data);
        } else {
            throw new UnsupportedOperationException("glGetDoublev");
        }
    }

    public static long glGetPointer(int arg0) {
        throw new UnsupportedOperationException("glGetPointer has no caps");
    }

    public static boolean glGetBoolean(int arg0) {
        throw new UnsupportedOperationException("glGetBoolean has no caps");
    }

    public static void glGetBooleanv(int pname, ByteBuffer data) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetBooleanv(pname, data);
        } else {
            throw new UnsupportedOperationException("glGetBooleanv");
        }
    }

    public static void glGetPixelMapuiv(int map, long values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPixelMapuiv(map, values);
        } else {
            throw new UnsupportedOperationException("glGetPixelMapuiv");
        }
    }

    public static void glGetPixelMapuiv(int map, IntBuffer values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPixelMapuiv(map, values);
        } else {
            throw new UnsupportedOperationException("glGetPixelMapuiv");
        }
    }

    public static void glGetPixelMapuiv(int map, int[] values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPixelMapuiv(map, values);
        } else {
            throw new UnsupportedOperationException("glGetPixelMapuiv");
        }
    }

    public static float glGetTexGenf(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTexGenf has no caps");
    }

    public static double glGetTexGend(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTexGend has no caps");
    }

    public static void glGetTexImage(int target, int level, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexImage(target, level, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTexImage");
        }
    }

    public static void glGetTexImage(int target, int level, int format, int type, double[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexImage(target, level, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTexImage");
        }
    }

    public static void glGetTexImage(int target, int level, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexImage(target, level, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTexImage");
        }
    }

    public static void glGetTexImage(int target, int level, int format, int type, DoubleBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexImage(target, level, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTexImage");
        }
    }

    public static void glGetTexImage(int target, int level, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexImage(target, level, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTexImage");
        }
    }

    public static void glGetTexImage(int target, int level, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexImage(target, level, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTexImage");
        }
    }

    public static void glGetTexImage(int target, int level, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexImage(target, level, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTexImage");
        }
    }

    public static void glGetTexImage(int target, int level, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexImage(target, level, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTexImage");
        }
    }

    public static void glGetTexImage(int target, int level, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexImage(target, level, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTexImage");
        }
    }

    public static void glGetTexImage(int target, int level, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetTexImage(target, level, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTexImage");
        }
    }

    public static void glGetLightfv(int light, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetLightfv(light, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetLightfv");
        }
    }

    public static void glGetLightfv(int light, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetLightfv(light, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetLightfv");
        }
    }

    public static void glInitNames() {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glInitNames();
        } else {
            throw new UnsupportedOperationException("glInitNames");
        }
    }

    public static void glGetMaterialfv(int face, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetMaterialfv(face, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetMaterialfv");
        }
    }

    public static void glGetMaterialfv(int face, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetMaterialfv(face, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetMaterialfv");
        }
    }

    public static boolean glIsEnabled(int cap) {
        if (GL_CAPABILITIES.OpenGL11) {
            return GL11.glIsEnabled(cap);
        } else {
            throw new UnsupportedOperationException("glIsEnabled");
        }
    }

    public static boolean glIsTexture(int texture) {
        if (GL_CAPABILITIES.OpenGL11) {
            return GL11.glIsTexture(texture);
        } else {
            throw new UnsupportedOperationException("glIsTexture");
        }
    }

    public static void glGetPointerv(int pname, PointerBuffer params) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL11.glGetPointerv(pname, params);
        } else {
            throw new UnsupportedOperationException("glGetPointerv");
        }
    }

    public static void glGetClipPlane(int plane, DoubleBuffer equation) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetClipPlane(plane, equation);
        } else {
            throw new UnsupportedOperationException("glGetClipPlane");
        }
    }

    public static void glGetClipPlane(int plane, double[] equation) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetClipPlane(plane, equation);
        } else {
            throw new UnsupportedOperationException("glGetClipPlane");
        }
    }

    public static void glGenTextures(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGenTextures(n);
        } else {
            throw new UnsupportedOperationException("glGenTextures");
        }
    }

    public static void glGenTextures(int[] n) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGenTextures(n);
        } else {
            throw new UnsupportedOperationException("glGenTextures");
        }
    }

    public static int glGenTextures() {
        if (GL_CAPABILITIES.OpenGL11) {
            return GL11.glGenTextures();
        } else {
            throw new UnsupportedOperationException("glGenTextures");
        }
    }

    public static int glGetInteger(int arg0) {
        throw new UnsupportedOperationException("glGetInteger has no caps");
    }

    public static void glGetLightiv(int light, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetLightiv(light, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetLightiv");
        }
    }

    public static void glGetLightiv(int light, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetLightiv(light, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetLightiv");
        }
    }

    public static double glGetDouble(int arg0) {
        throw new UnsupportedOperationException("glGetDouble has no caps");
    }

    public static void glGetPixelMapfv(int map, FloatBuffer values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPixelMapfv(map, values);
        } else {
            throw new UnsupportedOperationException("glGetPixelMapfv");
        }
    }

    public static void glGetPixelMapfv(int map, long values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPixelMapfv(map, values);
        } else {
            throw new UnsupportedOperationException("glGetPixelMapfv");
        }
    }

    public static void glGetPixelMapfv(int map, float[] values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetPixelMapfv(map, values);
        } else {
            throw new UnsupportedOperationException("glGetPixelMapfv");
        }
    }

    public static void glGetTexEnvfv(int target, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetTexEnvfv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexEnvfv");
        }
    }

    public static void glGetTexEnvfv(int target, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetTexEnvfv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexEnvfv");
        }
    }

    public static void glGetFloatv(int pname, float[] data) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetFloatv(pname, data);
        } else {
            throw new UnsupportedOperationException("glGetFloatv");
        }
    }

    public static void glGetFloatv(int pname, FloatBuffer data) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glGetFloatv(pname, data);
        } else {
            throw new UnsupportedOperationException("glGetFloatv");
        }
    }

    public static float glGetTexEnvf(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTexEnvf has no caps");
    }

    public static int glGetTexGeni(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTexGeni has no caps");
    }

    public static void glGetTexGeniv(int coord, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetTexGeniv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexGeniv");
        }
    }

    public static void glGetTexGeniv(int coord, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetTexGeniv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexGeniv");
        }
    }

    public static void glGetTexGenfv(int coord, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetTexGenfv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexGenfv");
        }
    }

    public static void glGetTexGenfv(int coord, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetTexGenfv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexGenfv");
        }
    }

    public static void glGetTexGendv(int coord, int pname, DoubleBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetTexGendv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexGendv");
        }
    }

    public static void glGetTexGendv(int coord, int pname, double[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glGetTexGendv(coord, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexGendv");
        }
    }

    public static float glGetLightf(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetLightf has no caps");
    }

    public static int glGetTexParameteri(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTexParameteri has no caps");
    }

    public static void glPixelTransferi(int pname, int param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelTransferi(pname, param);
        } else {
            throw new UnsupportedOperationException("glPixelTransferi");
        }
    }

    public static void glPixelTransferf(int pname, float param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelTransferf(pname, param);
        } else {
            throw new UnsupportedOperationException("glPixelTransferf");
        }
    }

    public static void glPointSize(float size) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glPointSize(size);
        } else {
            throw new UnsupportedOperationException("glPointSize");
        }
    }

    public static void glPolygonMode(int face, int mode) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glPolygonMode(face, mode);
        } else {
            throw new UnsupportedOperationException("glPolygonMode");
        }
    }

    public static void glMaterialf(int face, int pname, float param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMaterialf(face, pname, param);
        } else {
            throw new UnsupportedOperationException("glMaterialf");
        }
    }

    public static void glPushAttrib(int mask) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPushAttrib(mask);
        } else {
            throw new UnsupportedOperationException("glPushAttrib");
        }
    }

    public static void glPopAttrib() {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPopAttrib();
        } else {
            throw new UnsupportedOperationException("glPopAttrib");
        }
    }

    public static void glMaterialiv(int face, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMaterialiv(face, pname, params);
        } else {
            throw new UnsupportedOperationException("glMaterialiv");
        }
    }

    public static void glMaterialiv(int face, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMaterialiv(face, pname, params);
        } else {
            throw new UnsupportedOperationException("glMaterialiv");
        }
    }

    public static void glPopMatrix() {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPopMatrix();
        } else {
            throw new UnsupportedOperationException("glPopMatrix");
        }
    }

    public static void glNormalPointer(int type, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormalPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glNormalPointer");
        }
    }

    public static void glNormalPointer(int type, int stride, FloatBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormalPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glNormalPointer");
        }
    }

    public static void glNormalPointer(int type, int stride, IntBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormalPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glNormalPointer");
        }
    }

    public static void glNormalPointer(int type, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormalPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glNormalPointer");
        }
    }

    public static void glNormalPointer(int type, int stride, ShortBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormalPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glNormalPointer");
        }
    }

    public static void glPopClientAttrib() {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPopClientAttrib();
        } else {
            throw new UnsupportedOperationException("glPopClientAttrib");
        }
    }

    public static void glMapGrid2d(int un, double u1, double u2, int vn, double v1, double v2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMapGrid2d(un, u1, u2, vn, v1, v2);
        } else {
            throw new UnsupportedOperationException("glMapGrid2d");
        }
    }

    public static void glLoadMatrixd(double[] m) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLoadMatrixd(m);
        } else {
            throw new UnsupportedOperationException("glLoadMatrixd");
        }
    }

    public static void glLoadMatrixd(DoubleBuffer m) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLoadMatrixd(m);
        } else {
            throw new UnsupportedOperationException("glLoadMatrixd");
        }
    }

    public static void glNormal3bv(ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3bv(v);
        } else {
            throw new UnsupportedOperationException("glNormal3bv");
        }
    }

    public static void glLoadMatrixf(FloatBuffer m) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLoadMatrixf(m);
        } else {
            throw new UnsupportedOperationException("glLoadMatrixf");
        }
    }

    public static void glLoadMatrixf(float[] m) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLoadMatrixf(m);
        } else {
            throw new UnsupportedOperationException("glLoadMatrixf");
        }
    }

    public static void glNormal3fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3fv(v);
        } else {
            throw new UnsupportedOperationException("glNormal3fv");
        }
    }

    public static void glNormal3fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3fv(v);
        } else {
            throw new UnsupportedOperationException("glNormal3fv");
        }
    }

    public static void glLightModelf(int pname, float param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightModelf(pname, param);
        } else {
            throw new UnsupportedOperationException("glLightModelf");
        }
    }

    public static void glMatrixMode(int mode) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMatrixMode(mode);
        } else {
            throw new UnsupportedOperationException("glMatrixMode");
        }
    }

    public static void glPixelMapuiv(int map, IntBuffer mapsize) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelMapuiv(map, mapsize);
        } else {
            throw new UnsupportedOperationException("glPixelMapuiv");
        }
    }

    public static void glPixelMapuiv(int map, int mapsize, long values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelMapuiv(map, mapsize, values);
        } else {
            throw new UnsupportedOperationException("glPixelMapuiv");
        }
    }

    public static void glPixelMapuiv(int map, int[] mapsize) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelMapuiv(map, mapsize);
        } else {
            throw new UnsupportedOperationException("glPixelMapuiv");
        }
    }

    public static void glPixelStoref(int pname, float param) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glPixelStoref(pname, param);
        } else {
            throw new UnsupportedOperationException("glPixelStoref");
        }
    }

    public static void glPolygonOffset(float factor, float units) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glPolygonOffset(factor, units);
        } else {
            throw new UnsupportedOperationException("glPolygonOffset");
        }
    }

    public static void glPushClientAttrib(int mask) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPushClientAttrib(mask);
        } else {
            throw new UnsupportedOperationException("glPushClientAttrib");
        }
    }

    public static void glLineWidth(float width) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glLineWidth(width);
        } else {
            throw new UnsupportedOperationException("glLineWidth");
        }
    }

    public static void glLightModeliv(int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightModeliv(pname, params);
        } else {
            throw new UnsupportedOperationException("glLightModeliv");
        }
    }

    public static void glLightModeliv(int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightModeliv(pname, params);
        } else {
            throw new UnsupportedOperationException("glLightModeliv");
        }
    }

    public static void glMateriali(int face, int pname, int param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMateriali(face, pname, param);
        } else {
            throw new UnsupportedOperationException("glMateriali");
        }
    }

    public static void glMultMatrixf(float[] m) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMultMatrixf(m);
        } else {
            throw new UnsupportedOperationException("glMultMatrixf");
        }
    }

    public static void glMultMatrixf(FloatBuffer m) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMultMatrixf(m);
        } else {
            throw new UnsupportedOperationException("glMultMatrixf");
        }
    }

    public static void glLightModelfv(int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightModelfv(pname, params);
        } else {
            throw new UnsupportedOperationException("glLightModelfv");
        }
    }

    public static void glLightModelfv(int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightModelfv(pname, params);
        } else {
            throw new UnsupportedOperationException("glLightModelfv");
        }
    }

    public static void glMultMatrixd(DoubleBuffer m) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMultMatrixd(m);
        } else {
            throw new UnsupportedOperationException("glMultMatrixd");
        }
    }

    public static void glMultMatrixd(double[] m) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMultMatrixd(m);
        } else {
            throw new UnsupportedOperationException("glMultMatrixd");
        }
    }

    public static void glNormal3sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3sv(v);
        } else {
            throw new UnsupportedOperationException("glNormal3sv");
        }
    }

    public static void glNormal3sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3sv(v);
        } else {
            throw new UnsupportedOperationException("glNormal3sv");
        }
    }

    public static void glPassThrough(float token) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPassThrough(token);
        } else {
            throw new UnsupportedOperationException("glPassThrough");
        }
    }

    public static void glPixelMapusv(int map, short[] mapsize) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelMapusv(map, mapsize);
        } else {
            throw new UnsupportedOperationException("glPixelMapusv");
        }
    }

    public static void glPixelMapusv(int map, ShortBuffer mapsize) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelMapusv(map, mapsize);
        } else {
            throw new UnsupportedOperationException("glPixelMapusv");
        }
    }

    public static void glPixelMapusv(int map, int mapsize, long values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelMapusv(map, mapsize, values);
        } else {
            throw new UnsupportedOperationException("glPixelMapusv");
        }
    }

    public static void glPixelStorei(int pname, int param) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glPixelStorei(pname, param);
        } else {
            throw new UnsupportedOperationException("glPixelStorei");
        }
    }

    public static void glMapGrid2f(int un, float u1, float u2, int vn, float v1, float v2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMapGrid2f(un, u1, u2, vn, v1, v2);
        } else {
            throw new UnsupportedOperationException("glMapGrid2f");
        }
    }

    public static void glPolygonStipple(ByteBuffer mask) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPolygonStipple(mask);
        } else {
            throw new UnsupportedOperationException("glPolygonStipple");
        }
    }

    public static void glPolygonStipple(long mask) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPolygonStipple(mask);
        } else {
            throw new UnsupportedOperationException("glPolygonStipple");
        }
    }

    public static void glPixelZoom(float xfactor, float yfactor) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelZoom(xfactor, yfactor);
        } else {
            throw new UnsupportedOperationException("glPixelZoom");
        }
    }

    public static void glNormal3iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3iv(v);
        } else {
            throw new UnsupportedOperationException("glNormal3iv");
        }
    }

    public static void glNormal3iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3iv(v);
        } else {
            throw new UnsupportedOperationException("glNormal3iv");
        }
    }

    public static void glLightModeli(int pname, int param) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLightModeli(pname, param);
        } else {
            throw new UnsupportedOperationException("glLightModeli");
        }
    }

    public static void glMaterialfv(int face, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMaterialfv(face, pname, params);
        } else {
            throw new UnsupportedOperationException("glMaterialfv");
        }
    }

    public static void glMaterialfv(int face, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMaterialfv(face, pname, params);
        } else {
            throw new UnsupportedOperationException("glMaterialfv");
        }
    }

    public static void glLoadIdentity() {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLoadIdentity();
        } else {
            throw new UnsupportedOperationException("glLoadIdentity");
        }
    }

    public static void glLineStipple(int factor, short pattern) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glLineStipple(factor, pattern);
        } else {
            throw new UnsupportedOperationException("glLineStipple");
        }
    }

    public static void glMapGrid1f(int un, float u1, float u2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMapGrid1f(un, u1, u2);
        } else {
            throw new UnsupportedOperationException("glMapGrid1f");
        }
    }

    public static void glMapGrid1d(int un, double u1, double u2) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glMapGrid1d(un, u1, u2);
        } else {
            throw new UnsupportedOperationException("glMapGrid1d");
        }
    }

    public static void glNormal3dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3dv(v);
        } else {
            throw new UnsupportedOperationException("glNormal3dv");
        }
    }

    public static void glNormal3dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glNormal3dv(v);
        } else {
            throw new UnsupportedOperationException("glNormal3dv");
        }
    }

    public static void glPixelMapfv(int map, int mapsize, long values) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelMapfv(map, mapsize, values);
        } else {
            throw new UnsupportedOperationException("glPixelMapfv");
        }
    }

    public static void glPixelMapfv(int map, FloatBuffer mapsize) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelMapfv(map, mapsize);
        } else {
            throw new UnsupportedOperationException("glPixelMapfv");
        }
    }

    public static void glPixelMapfv(int map, float[] mapsize) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPixelMapfv(map, mapsize);
        } else {
            throw new UnsupportedOperationException("glPixelMapfv");
        }
    }

    public static int glRenderMode(int mode) {
        if (GL_CAPABILITIES.OpenGL32) {
            return GL11.glRenderMode(mode);
        } else {
            throw new UnsupportedOperationException("glRenderMode");
        }
    }

    public static void glRasterPos3f(float x, float y, float z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3f(x, y, z);
        } else {
            throw new UnsupportedOperationException("glRasterPos3f");
        }
    }

    public static void glRasterPos2i(int x, int y) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2i(x, y);
        } else {
            throw new UnsupportedOperationException("glRasterPos2i");
        }
    }

    public static void glRasterPos3iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3iv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos3iv");
        }
    }

    public static void glRasterPos3iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3iv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos3iv");
        }
    }

    public static void glRasterPos4i(int x, int y, int z, int w) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4i(x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glRasterPos4i");
        }
    }

    public static void glRasterPos4d(double x, double y, double z, double w) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4d(x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glRasterPos4d");
        }
    }

    public static void glRasterPos3sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3sv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos3sv");
        }
    }

    public static void glRasterPos3sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3sv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos3sv");
        }
    }

    public static void glRasterPos2fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2fv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos2fv");
        }
    }

    public static void glRasterPos2fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2fv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos2fv");
        }
    }

    public static void glShadeModel(int mode) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glShadeModel(mode);
        } else {
            throw new UnsupportedOperationException("glShadeModel");
        }
    }

    public static void glRasterPos4fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4fv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos4fv");
        }
    }

    public static void glRasterPos4fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4fv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos4fv");
        }
    }

    public static void glStencilOp(int fail, int zfail, int zpass) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glStencilOp(fail, zfail, zpass);
        } else {
            throw new UnsupportedOperationException("glStencilOp");
        }
    }

    public static void glTexCoord1f(float s) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1f(s);
        } else {
            throw new UnsupportedOperationException("glTexCoord1f");
        }
    }

    public static void glRasterPos2d(double x, double y) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2d(x, y);
        } else {
            throw new UnsupportedOperationException("glRasterPos2d");
        }
    }

    public static void glRasterPos2s(short x, short y) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2s(x, y);
        } else {
            throw new UnsupportedOperationException("glRasterPos2s");
        }
    }

    public static void glTexCoord1d(double s) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1d(s);
        } else {
            throw new UnsupportedOperationException("glTexCoord1d");
        }
    }

    public static void glTexCoord1sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1sv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord1sv");
        }
    }

    public static void glTexCoord1sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1sv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord1sv");
        }
    }

    public static void glTexCoord1dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1dv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord1dv");
        }
    }

    public static void glTexCoord1dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1dv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord1dv");
        }
    }

    public static void glTexCoord2f(float s, float t) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2f(s, t);
        } else {
            throw new UnsupportedOperationException("glTexCoord2f");
        }
    }

    public static void glRasterPos2sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2sv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos2sv");
        }
    }

    public static void glRasterPos2sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2sv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos2sv");
        }
    }

    public static void glRasterPos4s(short x, short y, short z, short w) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4s(x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glRasterPos4s");
        }
    }

    public static void glRasterPos4f(float x, float y, float z, float w) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4f(x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glRasterPos4f");
        }
    }

    public static void glRasterPos4sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4sv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos4sv");
        }
    }

    public static void glRasterPos4sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4sv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos4sv");
        }
    }

    public static void glRasterPos3fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3fv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos3fv");
        }
    }

    public static void glRasterPos3fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3fv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos3fv");
        }
    }

    public static void glStencilFunc(int func, int ref, int mask) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glStencilFunc(func, ref, mask);
        } else {
            throw new UnsupportedOperationException("glStencilFunc");
        }
    }

    public static void glTexCoord1iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1iv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord1iv");
        }
    }

    public static void glTexCoord1iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1iv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord1iv");
        }
    }

    public static void glRasterPos4iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4iv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos4iv");
        }
    }

    public static void glRasterPos4iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4iv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos4iv");
        }
    }

    public static void glRasterPos4dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4dv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos4dv");
        }
    }

    public static void glRasterPos4dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos4dv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos4dv");
        }
    }

    public static void glRasterPos3i(int x, int y, int z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3i(x, y, z);
        } else {
            throw new UnsupportedOperationException("glRasterPos3i");
        }
    }

    public static void glStencilMask(int mask) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glStencilMask(mask);
        } else {
            throw new UnsupportedOperationException("glStencilMask");
        }
    }

    public static void glTexCoord1fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1fv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord1fv");
        }
    }

    public static void glTexCoord1fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1fv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord1fv");
        }
    }

    public static void glTexCoord1s(short s) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1s(s);
        } else {
            throw new UnsupportedOperationException("glTexCoord1s");
        }
    }

    public static void glTexCoord2s(short s, short t) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2s(s, t);
        } else {
            throw new UnsupportedOperationException("glTexCoord2s");
        }
    }

    public static void glTexCoord2i(int s, int t) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2i(s, t);
        } else {
            throw new UnsupportedOperationException("glTexCoord2i");
        }
    }

    public static void glRasterPos2f(float x, float y) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2f(x, y);
        } else {
            throw new UnsupportedOperationException("glRasterPos2f");
        }
    }

    public static void glRasterPos2iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2iv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos2iv");
        }
    }

    public static void glRasterPos2iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2iv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos2iv");
        }
    }

    public static void glRasterPos2dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2dv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos2dv");
        }
    }

    public static void glRasterPos2dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos2dv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos2dv");
        }
    }

    public static void glRasterPos3dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3dv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos3dv");
        }
    }

    public static void glRasterPos3dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3dv(v);
        } else {
            throw new UnsupportedOperationException("glRasterPos3dv");
        }
    }

    public static void glRasterPos3d(double x, double y, double z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3d(x, y, z);
        } else {
            throw new UnsupportedOperationException("glRasterPos3d");
        }
    }

    public static void glSelectBuffer(int[] size) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glSelectBuffer(size);
        } else {
            throw new UnsupportedOperationException("glSelectBuffer");
        }
    }

    public static void glSelectBuffer(IntBuffer size) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glSelectBuffer(size);
        } else {
            throw new UnsupportedOperationException("glSelectBuffer");
        }
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glReadPixels(x, y, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glReadPixels");
        }
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glReadPixels(x, y, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glReadPixels");
        }
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glReadPixels(x, y, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glReadPixels");
        }
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glReadPixels(x, y, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glReadPixels");
        }
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glReadPixels(x, y, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glReadPixels");
        }
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glReadPixels(x, y, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glReadPixels");
        }
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glReadPixels(x, y, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glReadPixels");
        }
    }

    public static void glReadPixels(int x, int y, int width, int height, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glReadPixels(x, y, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glReadPixels");
        }
    }

    public static void glPushMatrix() {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glPushMatrix();
        } else {
            throw new UnsupportedOperationException("glPushMatrix");
        }
    }

    public static void glTexCoord1i(int s) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord1i(s);
        } else {
            throw new UnsupportedOperationException("glTexCoord1i");
        }
    }

    public static void glTexCoord2d(double s, double t) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2d(s, t);
        } else {
            throw new UnsupportedOperationException("glTexCoord2d");
        }
    }

    public static void glRasterPos3s(short x, short y, short z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glRasterPos3s(x, y, z);
        } else {
            throw new UnsupportedOperationException("glRasterPos3s");
        }
    }

    public static void glReadBuffer(int src) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glReadBuffer(src);
        } else {
            throw new UnsupportedOperationException("glReadBuffer");
        }
    }

    public static void glVertex2sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2sv(v);
        } else {
            throw new UnsupportedOperationException("glVertex2sv");
        }
    }

    public static void glVertex2sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2sv(v);
        } else {
            throw new UnsupportedOperationException("glVertex2sv");
        }
    }

    public static void glTexCoord3iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3iv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord3iv");
        }
    }

    public static void glTexCoord3iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3iv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord3iv");
        }
    }

    public static void glVertex2iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2iv(v);
        } else {
            throw new UnsupportedOperationException("glVertex2iv");
        }
    }

    public static void glVertex2iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2iv(v);
        } else {
            throw new UnsupportedOperationException("glVertex2iv");
        }
    }

    public static void glVertex2dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2dv(v);
        } else {
            throw new UnsupportedOperationException("glVertex2dv");
        }
    }

    public static void glVertex2dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2dv(v);
        } else {
            throw new UnsupportedOperationException("glVertex2dv");
        }
    }

    public static void glTexCoord3f(float s, float t, float r) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3f(s, t, r);
        } else {
            throw new UnsupportedOperationException("glTexCoord3f");
        }
    }

    public static void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border);
        } else {
            throw new UnsupportedOperationException("glCopyTexImage2D");
        }
    }

    public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage1D");
        }
    }

    public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage1D");
        }
    }

    public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage1D");
        }
    }

    public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage1D");
        }
    }

    public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage1D");
        }
    }

    public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage1D");
        }
    }

    public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, double[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage1D");
        }
    }

    public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage1D");
        }
    }

    public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage1D");
        }
    }

    public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, DoubleBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage1D");
        }
    }

    public static void glCopyTexImage1D(int target, int level, int internalformat, int x, int y, int width, int border) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glCopyTexImage1D(target, level, internalformat, x, y, width, border);
        } else {
            throw new UnsupportedOperationException("glCopyTexImage1D");
        }
    }

    public static void glTexCoord3d(double s, double t, double r) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3d(s, t, r);
        } else {
            throw new UnsupportedOperationException("glTexCoord3d");
        }
    }

    public static void glTexCoord4iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4iv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord4iv");
        }
    }

    public static void glTexCoord4iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4iv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord4iv");
        }
    }

    public static void glTexCoord2iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2iv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord2iv");
        }
    }

    public static void glTexCoord2iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2iv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord2iv");
        }
    }

    public static void glTexCoord4i(int s, int t, int r, int q) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4i(s, t, r, q);
        } else {
            throw new UnsupportedOperationException("glTexCoord4i");
        }
    }

    public static void glTexCoord4dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4dv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord4dv");
        }
    }

    public static void glTexCoord4dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4dv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord4dv");
        }
    }

    public static void glTexCoord4s(short s, short t, short r, short q) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4s(s, t, r, q);
        } else {
            throw new UnsupportedOperationException("glTexCoord4s");
        }
    }

    public static void glTexParameterf(int target, int pname, float param) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexParameterf(target, pname, param);
        } else {
            throw new UnsupportedOperationException("glTexParameterf");
        }
    }

    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage2D");
        }
    }

    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage2D");
        }
    }

    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, DoubleBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage2D");
        }
    }

    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage2D");
        }
    }

    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, double[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage2D");
        }
    }

    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage2D");
        }
    }

    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage2D");
        }
    }

    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage2D");
        }
    }

    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage2D");
        }
    }

    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage2D");
        }
    }

    public static void glTexCoord3i(int s, int t, int r) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3i(s, t, r);
        } else {
            throw new UnsupportedOperationException("glTexCoord3i");
        }
    }

    public static void glTexCoord2sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2sv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord2sv");
        }
    }

    public static void glTexCoord2sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2sv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord2sv");
        }
    }

    public static void glTexCoord2fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2fv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord2fv");
        }
    }

    public static void glTexCoord2fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2fv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord2fv");
        }
    }

    public static void glTexCoord3fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3fv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord3fv");
        }
    }

    public static void glTexCoord3fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3fv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord3fv");
        }
    }

    public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage1D");
        }
    }

    public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, double[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage1D");
        }
    }

    public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage1D");
        }
    }

    public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage1D");
        }
    }

    public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage1D");
        }
    }

    public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage1D");
        }
    }

    public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage1D");
        }
    }

    public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage1D");
        }
    }

    public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage1D");
        }
    }

    public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, DoubleBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage1D");
        }
    }

    public static void glTexCoord3dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3dv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord3dv");
        }
    }

    public static void glTexCoord3dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3dv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord3dv");
        }
    }

    public static void glTexCoord4f(float s, float t, float r, float q) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4f(s, t, r, q);
        } else {
            throw new UnsupportedOperationException("glTexCoord4f");
        }
    }

    public static void glTexCoord3sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3sv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord3sv");
        }
    }

    public static void glTexCoord3sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3sv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord3sv");
        }
    }

    public static void glTexCoord4sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4sv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord4sv");
        }
    }

    public static void glTexCoord4sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4sv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord4sv");
        }
    }

    public static void glTexCoordPointer(int size, int type, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoordPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glTexCoordPointer");
        }
    }

    public static void glTexCoordPointer(int size, int type, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoordPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glTexCoordPointer");
        }
    }

    public static void glTexCoordPointer(int size, int type, int stride, IntBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoordPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glTexCoordPointer");
        }
    }

    public static void glTexCoordPointer(int size, int type, int stride, ShortBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoordPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glTexCoordPointer");
        }
    }

    public static void glTexCoordPointer(int size, int type, int stride, FloatBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoordPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glTexCoordPointer");
        }
    }

    public static void glTexCoord4fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4fv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord4fv");
        }
    }

    public static void glTexCoord4fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4fv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord4fv");
        }
    }

    public static void glTexCoord4d(double s, double t, double r, double q) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord4d(s, t, r, q);
        } else {
            throw new UnsupportedOperationException("glTexCoord4d");
        }
    }

    public static void glTexCoord2dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2dv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord2dv");
        }
    }

    public static void glTexCoord2dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord2dv(v);
        } else {
            throw new UnsupportedOperationException("glTexCoord2dv");
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage2D");
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage2D");
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage2D");
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, DoubleBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage2D");
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage2D");
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage2D");
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, double[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage2D");
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage2D");
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage2D");
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage2D");
        }
    }

    public static void glTexParameteri(int target, int pname, int param) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexParameteri(target, pname, param);
        } else {
            throw new UnsupportedOperationException("glTexParameteri");
        }
    }

    public static void glTexCoord3s(short s, short t, short r) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTexCoord3s(s, t, r);
        } else {
            throw new UnsupportedOperationException("glTexCoord3s");
        }
    }

    public static void glTexParameteriv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexParameteriv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexParameteriv");
        }
    }

    public static void glTexParameteriv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexParameteriv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexParameteriv");
        }
    }

    public static void glTexParameterfv(int target, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexParameterfv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexParameterfv");
        }
    }

    public static void glTexParameterfv(int target, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL11) {
            GL11.glTexParameterfv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexParameterfv");
        }
    }

    public static void glTranslatef(float x, float y, float z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTranslatef(x, y, z);
        } else {
            throw new UnsupportedOperationException("glTranslatef");
        }
    }

    public static void glTranslated(double x, double y, double z) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glTranslated(x, y, z);
        } else {
            throw new UnsupportedOperationException("glTranslated");
        }
    }

    public static void glVertex2fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2fv(v);
        } else {
            throw new UnsupportedOperationException("glVertex2fv");
        }
    }

    public static void glVertex2fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex2fv(v);
        } else {
            throw new UnsupportedOperationException("glVertex2fv");
        }
    }

    public static void glVertex4sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4sv(v);
        } else {
            throw new UnsupportedOperationException("glVertex4sv");
        }
    }

    public static void glVertex4sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4sv(v);
        } else {
            throw new UnsupportedOperationException("glVertex4sv");
        }
    }

    public static void glVertex3iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3iv(v);
        } else {
            throw new UnsupportedOperationException("glVertex3iv");
        }
    }

    public static void glVertex3iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3iv(v);
        } else {
            throw new UnsupportedOperationException("glVertex3iv");
        }
    }

    public static void glVertex4dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4dv(v);
        } else {
            throw new UnsupportedOperationException("glVertex4dv");
        }
    }

    public static void glVertex4dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4dv(v);
        } else {
            throw new UnsupportedOperationException("glVertex4dv");
        }
    }

    public static void glVertex4iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4iv(v);
        } else {
            throw new UnsupportedOperationException("glVertex4iv");
        }
    }

    public static void glVertex4iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4iv(v);
        } else {
            throw new UnsupportedOperationException("glVertex4iv");
        }
    }

    public static void glVertex3fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3fv(v);
        } else {
            throw new UnsupportedOperationException("glVertex3fv");
        }
    }

    public static void glVertex3fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3fv(v);
        } else {
            throw new UnsupportedOperationException("glVertex3fv");
        }
    }

    public static void glVertexPointer(int size, int type, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertexPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexPointer");
        }
    }

    public static void glVertexPointer(int size, int type, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertexPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexPointer");
        }
    }

    public static void glVertexPointer(int size, int type, int stride, FloatBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertexPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexPointer");
        }
    }

    public static void glVertexPointer(int size, int type, int stride, ShortBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertexPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexPointer");
        }
    }

    public static void glVertexPointer(int size, int type, int stride, IntBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertexPointer(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexPointer");
        }
    }

    public static void glVertex3sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3sv(v);
        } else {
            throw new UnsupportedOperationException("glVertex3sv");
        }
    }

    public static void glVertex3sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3sv(v);
        } else {
            throw new UnsupportedOperationException("glVertex3sv");
        }
    }

    public static void glVertex4fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4fv(v);
        } else {
            throw new UnsupportedOperationException("glVertex4fv");
        }
    }

    public static void glVertex4fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex4fv(v);
        } else {
            throw new UnsupportedOperationException("glVertex4fv");
        }
    }

    public static void glVertex3dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3dv(v);
        } else {
            throw new UnsupportedOperationException("glVertex3dv");
        }
    }

    public static void glVertex3dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL11.glVertex3dv(v);
        } else {
            throw new UnsupportedOperationException("glVertex3dv");
        }
    }

    public static int glGetTexLevelParameteri(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetTexLevelParameteri has no caps");
    }

    // --- GL12 ---
    public static void glDrawRangeElements(int mode, int start, int end, int count, ByteBuffer type) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glDrawRangeElements(mode, start, end, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawRangeElements");
        }
    }

    public static void glDrawRangeElements(int mode, int start, int end, int count, int type, long indices) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glDrawRangeElements(mode, start, end, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawRangeElements");
        }
    }

    public static void glDrawRangeElements(int mode, int start, int end, ShortBuffer count) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glDrawRangeElements(mode, start, end, count);
        } else {
            throw new UnsupportedOperationException("glDrawRangeElements");
        }
    }

    public static void glDrawRangeElements(int mode, int start, int end, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glDrawRangeElements(mode, start, end, count);
        } else {
            throw new UnsupportedOperationException("glDrawRangeElements");
        }
    }

    public static void glDrawRangeElements(int mode, int start, int end, ByteBuffer count) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glDrawRangeElements(mode, start, end, count);
        } else {
            throw new UnsupportedOperationException("glDrawRangeElements");
        }
    }

    public static void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
        } else {
            throw new UnsupportedOperationException("glCopyTexSubImage3D");
        }
    }

    public static void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage3D");
        }
    }

    public static void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage3D");
        }
    }

    public static void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage3D");
        }
    }

    public static void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage3D");
        }
    }

    public static void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, DoubleBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage3D");
        }
    }

    public static void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, double[] pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage3D");
        }
    }

    public static void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage3D");
        }
    }

    public static void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage3D");
        }
    }

    public static void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage3D");
        }
    }

    public static void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexImage3D");
        }
    }

    public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage3D");
        }
    }

    public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage3D");
        }
    }

    public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage3D");
        }
    }

    public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, double[] pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage3D");
        }
    }

    public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage3D");
        }
    }

    public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, DoubleBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage3D");
        }
    }

    public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage3D");
        }
    }

    public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage3D");
        }
    }

    public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage3D");
        }
    }

    public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL12) {
            GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTexSubImage3D");
        }
    }

    // --- GL13 ---
    public static void glMultTransposeMatrixd(double[] m) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultTransposeMatrixd(m);
        } else if (GL_CAPABILITIES.GL_ARB_transpose_matrix) {
            ARBTransposeMatrix.glMultTransposeMatrixdARB(m);
        } else {
            throw new UnsupportedOperationException("glMultTransposeMatrixd");
        }
    }

    public static void glMultTransposeMatrixd(DoubleBuffer m) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultTransposeMatrixd(m);
        } else if (GL_CAPABILITIES.GL_ARB_transpose_matrix) {
            ARBTransposeMatrix.glMultTransposeMatrixdARB(m);
        } else {
            throw new UnsupportedOperationException("glMultTransposeMatrixd");
        }
    }

    public static void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, ByteBuffer imageSize) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, imageSize);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glCompressedTexSubImage2DARB(target, level, xoffset, yoffset, width, height, format, imageSize);
        } else {
            throw new UnsupportedOperationException("glCompressedTexSubImage2D");
        }
    }

    public static void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, long data) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, imageSize, data);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glCompressedTexSubImage2DARB(target, level, xoffset, yoffset, width, height, format, imageSize, data);
        } else {
            throw new UnsupportedOperationException("glCompressedTexSubImage2D");
        }
    }

    public static void glClientActiveTexture(int texture) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glClientActiveTexture(texture);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glClientActiveTextureARB(texture);
        } else {
            throw new UnsupportedOperationException("glClientActiveTexture");
        }
    }

    public static void glCompressedTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, ByteBuffer imageSize) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glCompressedTexSubImage3DARB(target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize);
        } else {
            throw new UnsupportedOperationException("glCompressedTexSubImage3D");
        }
    }

    public static void glCompressedTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, long data) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glCompressedTexSubImage3DARB(target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data);
        } else {
            throw new UnsupportedOperationException("glCompressedTexSubImage3D");
        }
    }

    public static void glCompressedTexImage1D(int target, int level, int internalformat, int width, int border, ByteBuffer imageSize) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexImage1D(target, level, internalformat, width, border, imageSize);
        } else {
            throw new UnsupportedOperationException("glCompressedTexImage1D");
        }
    }

    public static void glCompressedTexImage1D(int target, int level, int internalformat, int width, int border, int imageSize, long data) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexImage1D(target, level, internalformat, width, border, imageSize, data);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glCompressedTexImage1DARB(target, level, internalformat, width, border, imageSize, data);
        } else {
            throw new UnsupportedOperationException("glCompressedTexImage1D");
        }
    }

    public static void glGetCompressedTexImage(int target, int level, long img) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glGetCompressedTexImage(target, level, img);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glGetCompressedTexImageARB(target, level, img);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTexImage");
        }
    }

    public static void glGetCompressedTexImage(int target, int level, ByteBuffer img) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glGetCompressedTexImage(target, level, img);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glGetCompressedTexImageARB(target, level, img);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTexImage");
        }
    }

    public static void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, long data) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexImage2D(target, level, internalformat, width, height, border, imageSize, data);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glCompressedTexImage2DARB(target, level, internalformat, width, height, border, imageSize, data);
        } else {
            throw new UnsupportedOperationException("glCompressedTexImage2D");
        }
    }

    public static void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, ByteBuffer imageSize) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexImage2D(target, level, internalformat, width, height, border, imageSize);
        } else {
            throw new UnsupportedOperationException("glCompressedTexImage2D");
        }
    }

    public static void glCompressedTexSubImage1D(int target, int level, int xoffset, int width, int format, ByteBuffer imageSize) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexSubImage1D(target, level, xoffset, width, format, imageSize);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glCompressedTexSubImage1DARB(target, level, xoffset, width, format, imageSize);
        } else {
            throw new UnsupportedOperationException("glCompressedTexSubImage1D");
        }
    }

    public static void glCompressedTexSubImage1D(int target, int level, int xoffset, int width, int format, int imageSize, long data) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexSubImage1D(target, level, xoffset, width, format, imageSize, data);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glCompressedTexSubImage1DARB(target, level, xoffset, width, format, imageSize, data);
        } else {
            throw new UnsupportedOperationException("glCompressedTexSubImage1D");
        }
    }

    public static void glLoadTransposeMatrixf(FloatBuffer m) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glLoadTransposeMatrixf(m);
        } else if (GL_CAPABILITIES.GL_ARB_transpose_matrix) {
            ARBTransposeMatrix.glLoadTransposeMatrixfARB(m);
        } else {
            throw new UnsupportedOperationException("glLoadTransposeMatrixf");
        }
    }

    public static void glLoadTransposeMatrixf(float[] m) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glLoadTransposeMatrixf(m);
        } else if (GL_CAPABILITIES.GL_ARB_transpose_matrix) {
            ARBTransposeMatrix.glLoadTransposeMatrixfARB(m);
        } else {
            throw new UnsupportedOperationException("glLoadTransposeMatrixf");
        }
    }

    public static void glLoadTransposeMatrixd(double[] m) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glLoadTransposeMatrixd(m);
        } else if (GL_CAPABILITIES.GL_ARB_transpose_matrix) {
            ARBTransposeMatrix.glLoadTransposeMatrixdARB(m);
        } else {
            throw new UnsupportedOperationException("glLoadTransposeMatrixd");
        }
    }

    public static void glLoadTransposeMatrixd(DoubleBuffer m) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glLoadTransposeMatrixd(m);
        } else if (GL_CAPABILITIES.GL_ARB_transpose_matrix) {
            ARBTransposeMatrix.glLoadTransposeMatrixdARB(m);
        } else {
            throw new UnsupportedOperationException("glLoadTransposeMatrixd");
        }
    }

    public static void glMultTransposeMatrixf(float[] m) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultTransposeMatrixf(m);
        } else if (GL_CAPABILITIES.GL_ARB_transpose_matrix) {
            ARBTransposeMatrix.glMultTransposeMatrixfARB(m);
        } else {
            throw new UnsupportedOperationException("glMultTransposeMatrixf");
        }
    }

    public static void glMultTransposeMatrixf(FloatBuffer m) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultTransposeMatrixf(m);
        } else if (GL_CAPABILITIES.GL_ARB_transpose_matrix) {
            ARBTransposeMatrix.glMultTransposeMatrixfARB(m);
        } else {
            throw new UnsupportedOperationException("glMultTransposeMatrixf");
        }
    }

    public static void glCompressedTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, ByteBuffer imageSize) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexImage3D(target, level, internalformat, width, height, depth, border, imageSize);
        } else {
            throw new UnsupportedOperationException("glCompressedTexImage3D");
        }
    }

    public static void glCompressedTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int imageSize, long data) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glCompressedTexImage3D(target, level, internalformat, width, height, depth, border, imageSize, data);
        } else if (GL_CAPABILITIES.GL_ARB_texture_compression) {
            ARBTextureCompression.glCompressedTexImage3DARB(target, level, internalformat, width, height, depth, border, imageSize, data);
        } else {
            throw new UnsupportedOperationException("glCompressedTexImage3D");
        }
    }

    public static void glMultiTexCoord2dv(int target, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2dv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2dvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2dv");
        }
    }

    public static void glMultiTexCoord2dv(int target, double[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2dv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2dvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2dv");
        }
    }

    public static void glMultiTexCoord3s(int target, short s, short t, short r) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3s(target, s, t, r);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3sARB(target, s, t, r);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3s");
        }
    }

    public static void glMultiTexCoord3d(int target, double s, double t, double r) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3d(target, s, t, r);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3dARB(target, s, t, r);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3d");
        }
    }

    public static void glMultiTexCoord1dv(int target, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1dv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1dvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1dv");
        }
    }

    public static void glMultiTexCoord1dv(int target, double[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1dv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1dvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1dv");
        }
    }

    public static void glMultiTexCoord1f(int target, float s) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1f(target, s);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1fARB(target, s);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1f");
        }
    }

    public static void glMultiTexCoord2f(int target, float s, float t) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2f(target, s, t);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2fARB(target, s, t);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2f");
        }
    }

    public static void glMultiTexCoord3fv(int target, FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3fv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3fvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3fv");
        }
    }

    public static void glMultiTexCoord3fv(int target, float[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3fv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3fvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3fv");
        }
    }

    public static void glMultiTexCoord1sv(int target, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1sv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1svARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1sv");
        }
    }

    public static void glMultiTexCoord1sv(int target, short[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1sv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1svARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1sv");
        }
    }

    public static void glMultiTexCoord1i(int target, int s) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1i(target, s);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1iARB(target, s);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1i");
        }
    }

    public static void glMultiTexCoord2iv(int target, int[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2iv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2ivARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2iv");
        }
    }

    public static void glMultiTexCoord2iv(int target, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2iv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2ivARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2iv");
        }
    }

    public static void glMultiTexCoord3f(int target, float s, float t, float r) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3f(target, s, t, r);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3fARB(target, s, t, r);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3f");
        }
    }

    public static void glMultiTexCoord3i(int target, int s, int t, int r) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3i(target, s, t, r);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3iARB(target, s, t, r);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3i");
        }
    }

    public static void glMultiTexCoord1iv(int target, int[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1iv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1ivARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1iv");
        }
    }

    public static void glMultiTexCoord1iv(int target, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1iv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1ivARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1iv");
        }
    }

    public static void glSampleCoverage(float value, boolean invert) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glSampleCoverage(value, invert);
        } else if (GL_CAPABILITIES.GL_ARB_multisample) {
            ARBMultisample.glSampleCoverageARB(value, invert);
        } else {
            throw new UnsupportedOperationException("glSampleCoverage");
        }
    }

    public static void glActiveTexture(int texture) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glActiveTexture(texture);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glActiveTextureARB(texture);
        } else {
            throw new UnsupportedOperationException("glActiveTexture");
        }
    }

    public static void glMultiTexCoord1s(int target, short s) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1s(target, s);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1sARB(target, s);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1s");
        }
    }

    public static void glMultiTexCoord1d(int target, double s) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1d(target, s);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1dARB(target, s);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1d");
        }
    }

    public static void glMultiTexCoord1fv(int target, float[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1fv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1fvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1fv");
        }
    }

    public static void glMultiTexCoord1fv(int target, FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord1fv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord1fvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord1fv");
        }
    }

    public static void glMultiTexCoord2s(int target, short s, short t) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2s(target, s, t);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2sARB(target, s, t);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2s");
        }
    }

    public static void glMultiTexCoord2i(int target, int s, int t) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2i(target, s, t);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2iARB(target, s, t);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2i");
        }
    }

    public static void glMultiTexCoord2d(int target, double s, double t) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2d(target, s, t);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2dARB(target, s, t);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2d");
        }
    }

    public static void glMultiTexCoord2fv(int target, float[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2fv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2fvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2fv");
        }
    }

    public static void glMultiTexCoord2fv(int target, FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2fv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2fvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2fv");
        }
    }

    public static void glMultiTexCoord2sv(int target, short[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2sv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2svARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2sv");
        }
    }

    public static void glMultiTexCoord2sv(int target, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord2sv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord2svARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord2sv");
        }
    }

    public static void glMultiTexCoord4d(int target, double s, double t, double r, double q) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4d(target, s, t, r, q);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4dARB(target, s, t, r, q);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4d");
        }
    }

    public static void glMultiTexCoord4sv(int target, short[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4sv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4svARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4sv");
        }
    }

    public static void glMultiTexCoord4sv(int target, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4sv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4svARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4sv");
        }
    }

    public static void glMultiTexCoord4f(int target, float s, float t, float r, float q) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4f(target, s, t, r, q);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4fARB(target, s, t, r, q);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4f");
        }
    }

    public static void glMultiTexCoord4fv(int target, float[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4fv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4fvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4fv");
        }
    }

    public static void glMultiTexCoord4fv(int target, FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4fv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4fvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4fv");
        }
    }

    public static void glMultiTexCoord3dv(int target, double[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3dv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3dvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3dv");
        }
    }

    public static void glMultiTexCoord3dv(int target, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3dv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3dvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3dv");
        }
    }

    public static void glMultiTexCoord3sv(int target, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3sv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3svARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3sv");
        }
    }

    public static void glMultiTexCoord3sv(int target, short[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3sv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3svARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3sv");
        }
    }

    public static void glMultiTexCoord4s(int target, short s, short t, short r, short q) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4s(target, s, t, r, q);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4sARB(target, s, t, r, q);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4s");
        }
    }

    public static void glMultiTexCoord3iv(int target, int[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3iv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3ivARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3iv");
        }
    }

    public static void glMultiTexCoord3iv(int target, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord3iv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord3ivARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord3iv");
        }
    }

    public static void glMultiTexCoord4iv(int target, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4iv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4ivARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4iv");
        }
    }

    public static void glMultiTexCoord4iv(int target, int[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4iv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4ivARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4iv");
        }
    }

    public static void glMultiTexCoord4dv(int target, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4dv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4dvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4dv");
        }
    }

    public static void glMultiTexCoord4dv(int target, double[] v) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4dv(target, v);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4dvARB(target, v);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4dv");
        }
    }

    public static void glMultiTexCoord4i(int target, int s, int t, int r, int q) {
        if (GL_CAPABILITIES.OpenGL13) {
            GL13.glMultiTexCoord4i(target, s, t, r, q);
        } else if (GL_CAPABILITIES.GL_ARB_multitexture) {
            ARBMultitexture.glMultiTexCoord4iARB(target, s, t, r, q);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoord4i");
        }
    }

    // --- GL14 ---
    public static void glBlendEquation(int mode) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glBlendEquation(mode);
        } else if (GL_CAPABILITIES.GL_EXT_blend_minmax) {
            EXTBlendMinmax.glBlendEquationEXT(mode);
        } else if (GL_CAPABILITIES.GL_ARB_imaging) {
            ARBImaging.glBlendEquation(mode);
        } else {
            throw new UnsupportedOperationException("glBlendEquation");
        }
    }

    public static void glBlendColor(float red, float green, float blue, float alpha) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glBlendColor(red, green, blue, alpha);
        } else if (GL_CAPABILITIES.GL_EXT_blend_color) {
            EXTBlendColor.glBlendColorEXT(red, green, blue, alpha);
        } else if (GL_CAPABILITIES.GL_ARB_imaging) {
            ARBImaging.glBlendColor(red, green, blue, alpha);
        } else {
            throw new UnsupportedOperationException("glBlendColor");
        }
    }

    public static void glSecondaryColor3uiv(int[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3uiv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3uivEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3uiv");
        }
    }

    public static void glSecondaryColor3uiv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3uiv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3uivEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3uiv");
        }
    }

    public static void glSecondaryColor3ub(byte red, byte green, byte blue) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3ub(red, green, blue);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3ubEXT(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3ub");
        }
    }

    public static void glSecondaryColor3ubv(ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3ubv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3ubvEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3ubv");
        }
    }

    public static void glSecondaryColorPointer(int size, int type, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColorPointer(size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColorPointerEXT(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glSecondaryColorPointer");
        }
    }

    public static void glSecondaryColorPointer(int size, int type, int stride, FloatBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColorPointer(size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColorPointerEXT(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glSecondaryColorPointer");
        }
    }

    public static void glSecondaryColorPointer(int size, int type, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColorPointer(size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColorPointerEXT(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glSecondaryColorPointer");
        }
    }

    public static void glSecondaryColorPointer(int size, int type, int stride, IntBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColorPointer(size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColorPointerEXT(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glSecondaryColorPointer");
        }
    }

    public static void glSecondaryColorPointer(int size, int type, int stride, ShortBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColorPointer(size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColorPointerEXT(size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glSecondaryColorPointer");
        }
    }

    public static void glSecondaryColor3us(short red, short green, short blue) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3us(red, green, blue);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3usEXT(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3us");
        }
    }

    public static void glSecondaryColor3ui(int red, int green, int blue) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3ui(red, green, blue);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3uiEXT(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3ui");
        }
    }

    public static void glMultiDrawElements(int mode, IntBuffer count, int type, PointerBuffer indices) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glMultiDrawElements(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElements");
        }
    }

    public static void glMultiDrawElements(int mode, int[] count, int type, PointerBuffer indices) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glMultiDrawElements(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElements");
        }
    }

    public static void glSecondaryColor3iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3iv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3ivEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3iv");
        }
    }

    public static void glSecondaryColor3iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3iv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3ivEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3iv");
        }
    }

    public static void glSecondaryColor3usv(short[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3usv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3usvEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3usv");
        }
    }

    public static void glSecondaryColor3usv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3usv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3usvEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3usv");
        }
    }

    public static void glSecondaryColor3fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3fv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3fvEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3fv");
        }
    }

    public static void glSecondaryColor3fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3fv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3fvEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3fv");
        }
    }

    public static void glSecondaryColor3dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3dv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3dvEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3dv");
        }
    }

    public static void glSecondaryColor3dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3dv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3dvEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3dv");
        }
    }

    public static void glSecondaryColor3sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3sv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3svEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3sv");
        }
    }

    public static void glSecondaryColor3sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3sv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3svEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3sv");
        }
    }

    public static void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
        } else if (GL_CAPABILITIES.GL_EXT_blend_func_separate) {
            EXTBlendFuncSeparate.glBlendFuncSeparateEXT(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
        } else {
            throw new UnsupportedOperationException("glBlendFuncSeparate");
        }
    }

    public static void glSecondaryColor3bv(ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3bv(v);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3bvEXT(v);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3bv");
        }
    }

    public static void glWindowPos2s(short x, short y) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2s(x, y);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2sARB(x, y);
        } else {
            throw new UnsupportedOperationException("glWindowPos2s");
        }
    }

    public static void glWindowPos3iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3iv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3ivARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos3iv");
        }
    }

    public static void glWindowPos3iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3iv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3ivARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos3iv");
        }
    }

    public static void glFogCoordfv(FloatBuffer coord) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glFogCoordfv(coord);
        } else {
            throw new UnsupportedOperationException("glFogCoordfv");
        }
    }

    public static void glFogCoordfv(float[] coord) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glFogCoordfv(coord);
        } else {
            throw new UnsupportedOperationException("glFogCoordfv");
        }
    }

    public static void glPointParameterfv(int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glPointParameterfv(pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_point_parameters) {
            ARBPointParameters.glPointParameterfvARB(pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_point_parameters) {
            EXTPointParameters.glPointParameterfvEXT(pname, params);
        } else {
            throw new UnsupportedOperationException("glPointParameterfv");
        }
    }

    public static void glPointParameterfv(int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glPointParameterfv(pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_point_parameters) {
            ARBPointParameters.glPointParameterfvARB(pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_point_parameters) {
            EXTPointParameters.glPointParameterfvEXT(pname, params);
        } else {
            throw new UnsupportedOperationException("glPointParameterfv");
        }
    }

    public static void glWindowPos2iv(int[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2iv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2ivARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos2iv");
        }
    }

    public static void glWindowPos2iv(IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2iv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2ivARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos2iv");
        }
    }

    public static void glSecondaryColor3s(short red, short green, short blue) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3s(red, green, blue);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3sEXT(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3s");
        }
    }

    public static void glFogCoorddv(double[] coord) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glFogCoorddv(coord);
        } else {
            throw new UnsupportedOperationException("glFogCoorddv");
        }
    }

    public static void glFogCoorddv(DoubleBuffer coord) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glFogCoorddv(coord);
        } else {
            throw new UnsupportedOperationException("glFogCoorddv");
        }
    }

    public static void glPointParameterf(int pname, float param) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glPointParameterf(pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_point_parameters) {
            ARBPointParameters.glPointParameterfARB(pname, param);
        } else if (GL_CAPABILITIES.GL_EXT_point_parameters) {
            EXTPointParameters.glPointParameterfEXT(pname, param);
        } else {
            throw new UnsupportedOperationException("glPointParameterf");
        }
    }

    public static void glSecondaryColor3b(byte red, byte green, byte blue) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3b(red, green, blue);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3bEXT(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3b");
        }
    }

    public static void glWindowPos2dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2dv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2dvARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos2dv");
        }
    }

    public static void glWindowPos2dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2dv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2dvARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos2dv");
        }
    }

    public static void glWindowPos3d(double x, double y, double z) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3d(x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3dARB(x, y, z);
        } else {
            throw new UnsupportedOperationException("glWindowPos3d");
        }
    }

    public static void glFogCoordd(double coord) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glFogCoordd(coord);
        } else {
            throw new UnsupportedOperationException("glFogCoordd");
        }
    }

    public static void glSecondaryColor3f(float red, float green, float blue) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3f(red, green, blue);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3fEXT(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3f");
        }
    }

    public static void glSecondaryColor3d(double red, double green, double blue) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3d(red, green, blue);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3dEXT(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3d");
        }
    }

    public static void glWindowPos2f(float x, float y) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2f(x, y);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2fARB(x, y);
        } else {
            throw new UnsupportedOperationException("glWindowPos2f");
        }
    }

    public static void glWindowPos3f(float x, float y, float z) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3f(x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3fARB(x, y, z);
        } else {
            throw new UnsupportedOperationException("glWindowPos3f");
        }
    }

    public static void glFogCoordf(float coord) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glFogCoordf(coord);
        } else {
            throw new UnsupportedOperationException("glFogCoordf");
        }
    }

    public static void glPointParameteriv(int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glPointParameteriv(pname, params);
        } else if (GL_CAPABILITIES.GL_NV_point_sprite) {
            NVPointSprite.glPointParameterivNV(pname, params);
        } else {
            throw new UnsupportedOperationException("glPointParameteriv");
        }
    }

    public static void glPointParameteriv(int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glPointParameteriv(pname, params);
        } else if (GL_CAPABILITIES.GL_NV_point_sprite) {
            NVPointSprite.glPointParameterivNV(pname, params);
        } else {
            throw new UnsupportedOperationException("glPointParameteriv");
        }
    }

    public static void glSecondaryColor3i(int red, int green, int blue) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glSecondaryColor3i(red, green, blue);
        } else if (GL_CAPABILITIES.GL_EXT_secondary_color) {
            EXTSecondaryColor.glSecondaryColor3iEXT(red, green, blue);
        } else {
            throw new UnsupportedOperationException("glSecondaryColor3i");
        }
    }

    public static void glWindowPos2d(double x, double y) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2d(x, y);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2dARB(x, y);
        } else {
            throw new UnsupportedOperationException("glWindowPos2d");
        }
    }

    public static void glWindowPos2fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2fv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2fvARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos2fv");
        }
    }

    public static void glWindowPos2fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2fv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2fvARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos2fv");
        }
    }

    public static void glWindowPos3i(int x, int y, int z) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3i(x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3iARB(x, y, z);
        } else {
            throw new UnsupportedOperationException("glWindowPos3i");
        }
    }

    public static void glMultiDrawArrays(int mode, IntBuffer first, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glMultiDrawArrays(mode, first, count);
        } else {
            throw new UnsupportedOperationException("glMultiDrawArrays");
        }
    }

    public static void glMultiDrawArrays(int mode, int[] first, int[] count) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glMultiDrawArrays(mode, first, count);
        } else {
            throw new UnsupportedOperationException("glMultiDrawArrays");
        }
    }

    public static void glPointParameteri(int pname, int param) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glPointParameteri(pname, param);
        } else if (GL_CAPABILITIES.GL_NV_point_sprite) {
            NVPointSprite.glPointParameteriNV(pname, param);
        } else {
            throw new UnsupportedOperationException("glPointParameteri");
        }
    }

    public static void glWindowPos2i(int x, int y) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2i(x, y);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2iARB(x, y);
        } else {
            throw new UnsupportedOperationException("glWindowPos2i");
        }
    }

    public static void glWindowPos3s(short x, short y, short z) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3s(x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3sARB(x, y, z);
        } else {
            throw new UnsupportedOperationException("glWindowPos3s");
        }
    }

    public static void glWindowPos2sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2sv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2svARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos2sv");
        }
    }

    public static void glWindowPos2sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos2sv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos2svARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos2sv");
        }
    }

    public static void glFogCoordPointer(int type, int stride, ShortBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glFogCoordPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glFogCoordPointer");
        }
    }

    public static void glFogCoordPointer(int type, int stride, FloatBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glFogCoordPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glFogCoordPointer");
        }
    }

    public static void glFogCoordPointer(int type, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glFogCoordPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glFogCoordPointer");
        }
    }

    public static void glFogCoordPointer(int type, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glFogCoordPointer(type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glFogCoordPointer");
        }
    }

    public static void glWindowPos3dv(DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3dv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3dvARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos3dv");
        }
    }

    public static void glWindowPos3dv(double[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3dv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3dvARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos3dv");
        }
    }

    public static void glWindowPos3sv(short[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3sv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3svARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos3sv");
        }
    }

    public static void glWindowPos3sv(ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3sv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3svARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos3sv");
        }
    }

    public static void glWindowPos3fv(FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3fv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3fvARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos3fv");
        }
    }

    public static void glWindowPos3fv(float[] v) {
        if (GL_CAPABILITIES.OpenGL14) {
            GL14.glWindowPos3fv(v);
        } else if (GL_CAPABILITIES.GL_ARB_window_pos) {
            ARBWindowPos.glWindowPos3fvARB(v);
        } else {
            throw new UnsupportedOperationException("glWindowPos3fv");
        }
    }

    // --- GL15 ---
    public static boolean glIsBuffer(int buffer) {
        if (GL_CAPABILITIES.OpenGL15) {
            return GL15.glIsBuffer(buffer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            return ARBVertexBufferObject.glIsBufferARB(buffer);
        } else {
            throw new UnsupportedOperationException("glIsBuffer");
        }
    }

    public static boolean glIsQuery(int id) {
        if (GL_CAPABILITIES.OpenGL15) {
            return GL15.glIsQuery(id);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            return ARBOcclusionQuery.glIsQueryARB(id);
        } else {
            throw new UnsupportedOperationException("glIsQuery");
        }
    }

    public static void glEndQuery(int target) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glEndQuery(target);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glEndQueryARB(target);
        } else {
            throw new UnsupportedOperationException("glEndQuery");
        }
    }

    public static int glGetBufferParameteri(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetBufferParameteri has no caps");
    }

    public static void glGetBufferParameteriv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferParameteriv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferParameterivARB(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetBufferParameteriv");
        }
    }

    public static void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferParameteriv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferParameterivARB(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetBufferParameteriv");
        }
    }

    public static void glGetBufferPointerv(int target, int pname, PointerBuffer params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferPointerv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferPointervARB(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetBufferPointerv");
        }
    }

    public static void glGetQueryObjectuiv(int id, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetQueryObjectuiv(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glGetQueryObjectuivARB(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjectuiv");
        }
    }

    public static void glGetQueryObjectuiv(int id, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetQueryObjectuiv(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glGetQueryObjectuivARB(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjectuiv");
        }
    }

    public static void glGetQueryObjectuiv(int id, int pname, long params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetQueryObjectuiv(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glGetQueryObjectuivARB(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjectuiv");
        }
    }

    public static long glGetBufferPointer(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetBufferPointer has no caps");
    }

    public static ByteBuffer glMapBuffer(int target, int access, ByteBuffer arg2) {
        if (GL_CAPABILITIES.OpenGL15) {
            return GL15.glMapBuffer(target, access, arg2);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            return ARBVertexBufferObject.glMapBufferARB(target, access, arg2);
        } else {
            throw new UnsupportedOperationException("glMapBuffer");
        }
    }

    public static ByteBuffer glMapBuffer(int target, int access) {
        if (GL_CAPABILITIES.OpenGL15) {
            return GL15.glMapBuffer(target, access);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            return ARBVertexBufferObject.glMapBufferARB(target, access);
        } else {
            throw new UnsupportedOperationException("glMapBuffer");
        }
    }

    public static ByteBuffer glMapBuffer(int target, int access, long arg2, ByteBuffer arg3) {
        if (GL_CAPABILITIES.OpenGL15) {
            return GL15.glMapBuffer(target, access, arg2, arg3);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            return ARBVertexBufferObject.glMapBufferARB(target, access, arg2, arg3);
        } else {
            throw new UnsupportedOperationException("glMapBuffer");
        }
    }

    public static int glGetQueryi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetQueryi has no caps");
    }

    public static void glBufferData(int target, double[] size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferDataARB(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, int[] size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferDataARB(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, float[] size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferDataARB(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, long[] size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, short[] size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferDataARB(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, long size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferDataARB(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, ByteBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferDataARB(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, ShortBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferDataARB(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, IntBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferDataARB(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, LongBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, FloatBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferDataARB(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glBufferData(int target, DoubleBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferData(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferDataARB(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, float[] size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, short[] size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, double[] size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, long[] size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, int[] size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, ByteBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, ShortBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, IntBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, DoubleBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, FloatBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGetBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static void glGetBufferSubData(int target, long offset, LongBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetBufferSubData(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetBufferSubData");
        }
    }

    public static boolean glUnmapBuffer(int target) {
        if (GL_CAPABILITIES.OpenGL15) {
            return GL15.glUnmapBuffer(target);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            return ARBVertexBufferObject.glUnmapBufferARB(target);
        } else {
            throw new UnsupportedOperationException("glUnmapBuffer");
        }
    }

    public static void glBufferSubData(int target, long offset, long[] size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBufferSubData(int target, long offset, LongBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBufferSubData(int target, long offset, FloatBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBufferSubData(int target, long offset, DoubleBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBufferSubData(int target, long offset, int[] size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBufferSubData(int target, long offset, short[] size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBufferSubData(int target, long offset, ByteBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBufferSubData(int target, long offset, ShortBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBufferSubData(int target, long offset, IntBuffer size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBufferSubData(int target, long offset, float[] size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBufferSubData(int target, long offset, double[] size) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBufferSubData(target, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBufferSubDataARB(target, offset, size);
        } else {
            throw new UnsupportedOperationException("glBufferSubData");
        }
    }

    public static void glBindBuffer(int target, int buffer) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBindBuffer(target, buffer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBindBufferARB(target, buffer);
        } else {
            throw new UnsupportedOperationException("glBindBuffer");
        }
    }

    public static void glGenBuffers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGenBuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGenBuffersARB(n);
        } else {
            throw new UnsupportedOperationException("glGenBuffers");
        }
    }

    public static int glGenBuffers() {
        if (GL_CAPABILITIES.OpenGL15) {
            return GL15.glGenBuffers();
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            return ARBVertexBufferObject.glGenBuffersARB();
        } else {
            throw new UnsupportedOperationException("glGenBuffers");
        }
    }

    public static void glGenBuffers(int[] n) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGenBuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glGenBuffersARB(n);
        } else {
            throw new UnsupportedOperationException("glGenBuffers");
        }
    }

    public static void glGenQueries(int[] n) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGenQueries(n);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glGenQueriesARB(n);
        } else {
            throw new UnsupportedOperationException("glGenQueries");
        }
    }

    public static void glGenQueries(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGenQueries(n);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glGenQueriesARB(n);
        } else {
            throw new UnsupportedOperationException("glGenQueries");
        }
    }

    public static int glGenQueries() {
        if (GL_CAPABILITIES.OpenGL15) {
            return GL15.glGenQueries();
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            return ARBOcclusionQuery.glGenQueriesARB();
        } else {
            throw new UnsupportedOperationException("glGenQueries");
        }
    }

    public static void glDeleteQueries(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glDeleteQueries(n);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glDeleteQueriesARB(n);
        } else {
            throw new UnsupportedOperationException("glDeleteQueries");
        }
    }

    public static void glDeleteQueries(int n) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glDeleteQueries(n);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glDeleteQueriesARB(n);
        } else {
            throw new UnsupportedOperationException("glDeleteQueries");
        }
    }

    public static void glDeleteQueries(int[] n) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glDeleteQueries(n);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glDeleteQueriesARB(n);
        } else {
            throw new UnsupportedOperationException("glDeleteQueries");
        }
    }

    public static void glBeginQuery(int target, int id) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glBeginQuery(target, id);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glBeginQueryARB(target, id);
        } else {
            throw new UnsupportedOperationException("glBeginQuery");
        }
    }

    public static void glDeleteBuffers(int n) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glDeleteBuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glDeleteBuffersARB(n);
        } else {
            throw new UnsupportedOperationException("glDeleteBuffers");
        }
    }

    public static void glDeleteBuffers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glDeleteBuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glDeleteBuffersARB(n);
        } else {
            throw new UnsupportedOperationException("glDeleteBuffers");
        }
    }

    public static void glDeleteBuffers(int[] n) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glDeleteBuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glDeleteBuffersARB(n);
        } else {
            throw new UnsupportedOperationException("glDeleteBuffers");
        }
    }

    public static void glGetQueryiv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetQueryiv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glGetQueryivARB(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryiv");
        }
    }

    public static void glGetQueryiv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetQueryiv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glGetQueryivARB(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryiv");
        }
    }

    public static void glGetQueryObjectiv(int id, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetQueryObjectiv(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glGetQueryObjectivARB(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjectiv");
        }
    }

    public static void glGetQueryObjectiv(int id, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetQueryObjectiv(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glGetQueryObjectivARB(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjectiv");
        }
    }

    public static void glGetQueryObjectiv(int id, int pname, long params) {
        if (GL_CAPABILITIES.OpenGL15) {
            GL15.glGetQueryObjectiv(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_occlusion_query) {
            ARBOcclusionQuery.glGetQueryObjectivARB(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjectiv");
        }
    }

    public static int glGetQueryObjecti(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetQueryObjecti has no caps");
    }

    public static int glGetQueryObjectui(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetQueryObjectui has no caps");
    }

    // --- GL20 ---
    public static boolean glIsShader(int shader) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glIsShader(shader);
        } else {
            throw new UnsupportedOperationException("glIsShader");
        }
    }

    public static void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetVertexAttribiv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glGetVertexAttribivARB(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glGetVertexAttribivARB(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribiv");
        }
    }

    public static void glGetVertexAttribiv(int index, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetVertexAttribiv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glGetVertexAttribivARB(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glGetVertexAttribivARB(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribiv");
        }
    }

    public static String glGetProgramInfoLog(int program) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetProgramInfoLog(program);
        } else {
            throw new UnsupportedOperationException("glGetProgramInfoLog");
        }
    }

    public static String glGetProgramInfoLog(int program, int bufSize) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetProgramInfoLog(program, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetProgramInfoLog");
        }
    }

    public static void glGetProgramInfoLog(int program, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetProgramInfoLog(program, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetProgramInfoLog");
        }
    }

    public static void glGetProgramInfoLog(int program, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetProgramInfoLog(program, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetProgramInfoLog");
        }
    }

    public static void glVertexAttrib4Nusv(int index, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Nusv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NusvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NusvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Nusv");
        }
    }

    public static void glVertexAttrib4Nusv(int index, short[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Nusv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NusvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NusvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Nusv");
        }
    }

    public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, FloatBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribPointer");
        }
    }

    public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribPointer");
        }
    }

    public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribPointer");
        }
    }

    public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, ShortBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribPointer");
        }
    }

    public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, IntBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribPointer");
        }
    }

    public static void glBindAttribLocation(int program, int index, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glBindAttribLocation(program, index, name);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexShader.glBindAttribLocationARB(program, index, name);
        } else {
            throw new UnsupportedOperationException("glBindAttribLocation");
        }
    }

    public static void glBindAttribLocation(int program, int index, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glBindAttribLocation(program, index, name);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexShader.glBindAttribLocationARB(program, index, name);
        } else {
            throw new UnsupportedOperationException("glBindAttribLocation");
        }
    }

    public static void glGetVertexAttribfv(int index, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetVertexAttribfv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glGetVertexAttribfvARB(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glGetVertexAttribfvARB(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribfv");
        }
    }

    public static void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetVertexAttribfv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glGetVertexAttribfvARB(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glGetVertexAttribfvARB(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribfv");
        }
    }

    public static void glVertexAttrib4Nuiv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Nuiv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NuivARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NuivARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Nuiv");
        }
    }

    public static void glVertexAttrib4Nuiv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Nuiv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NuivARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NuivARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Nuiv");
        }
    }

    public static int glGetUniformLocation(int program, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetUniformLocation(program, name);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            return ARBShaderObjects.glGetUniformLocationARB(program, name);
        } else {
            throw new UnsupportedOperationException("glGetUniformLocation");
        }
    }

    public static int glGetUniformLocation(int program, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetUniformLocation(program, name);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            return ARBShaderObjects.glGetUniformLocationARB(program, name);
        } else {
            throw new UnsupportedOperationException("glGetUniformLocation");
        }
    }

    public static void glVertexAttrib4Nubv(int index, ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Nubv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NubvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NubvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Nubv");
        }
    }

    public static void glEnableVertexAttribArray(int index) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glEnableVertexAttribArray(index);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glEnableVertexAttribArrayARB(index);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glEnableVertexAttribArrayARB(index);
        } else {
            throw new UnsupportedOperationException("glEnableVertexAttribArray");
        }
    }

    public static void glGetAttachedShaders(int program, IntBuffer maxCount, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetAttachedShaders(program, maxCount, count);
        } else {
            throw new UnsupportedOperationException("glGetAttachedShaders");
        }
    }

    public static void glGetAttachedShaders(int program, int[] maxCount, int[] count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetAttachedShaders(program, maxCount, count);
        } else {
            throw new UnsupportedOperationException("glGetAttachedShaders");
        }
    }

    public static void glDisableVertexAttribArray(int index) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glDisableVertexAttribArray(index);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glDisableVertexAttribArrayARB(index);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glDisableVertexAttribArrayARB(index);
        } else {
            throw new UnsupportedOperationException("glDisableVertexAttribArray");
        }
    }

    public static int glGetAttribLocation(int program, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetAttribLocation(program, name);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            return ARBVertexShader.glGetAttribLocationARB(program, name);
        } else {
            throw new UnsupportedOperationException("glGetAttribLocation");
        }
    }

    public static int glGetAttribLocation(int program, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetAttribLocation(program, name);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            return ARBVertexShader.glGetAttribLocationARB(program, name);
        } else {
            throw new UnsupportedOperationException("glGetAttribLocation");
        }
    }

    public static void glGetVertexAttribPointerv(int index, int pname, PointerBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetVertexAttribPointerv(index, pname, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glGetVertexAttribPointervARB(index, pname, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glGetVertexAttribPointervARB(index, pname, pointer);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribPointerv");
        }
    }

    public static long glGetVertexAttribPointer(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetVertexAttribPointer has no caps");
    }

    public static void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glStencilOpSeparate(face, sfail, dpfail, dppass);
        } else {
            throw new UnsupportedOperationException("glStencilOpSeparate");
        }
    }

    public static void glGetVertexAttribdv(int index, int pname, DoubleBuffer params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetVertexAttribdv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glGetVertexAttribdvARB(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glGetVertexAttribdvARB(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribdv");
        }
    }

    public static void glGetVertexAttribdv(int index, int pname, double[] params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetVertexAttribdv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glGetVertexAttribdvARB(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glGetVertexAttribdvARB(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribdv");
        }
    }

    public static void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glBlendEquationSeparate(modeRGB, modeAlpha);
        } else if (GL_CAPABILITIES.GL_EXT_blend_equation_separate) {
            EXTBlendEquationSeparate.glBlendEquationSeparateEXT(modeRGB, modeAlpha);
        } else {
            throw new UnsupportedOperationException("glBlendEquationSeparate");
        }
    }

    public static void glStencilMaskSeparate(int face, int mask) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glStencilMaskSeparate(face, mask);
        } else {
            throw new UnsupportedOperationException("glStencilMaskSeparate");
        }
    }

    public static void glStencilFuncSeparate(int face, int func, int ref, int mask) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glStencilFuncSeparate(face, func, ref, mask);
        } else {
            throw new UnsupportedOperationException("glStencilFuncSeparate");
        }
    }

    public static void glUniform3i(int location, int v0, int v1, int v2) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform3i(location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform3iARB(location, v0, v1, v2);
        } else {
            throw new UnsupportedOperationException("glUniform3i");
        }
    }

    public static void glUniform4i(int location, int v0, int v1, int v2, int v3) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform4i(location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform4iARB(location, v0, v1, v2, v3);
        } else {
            throw new UnsupportedOperationException("glUniform4i");
        }
    }

    public static void glUniform1fv(int location, FloatBuffer count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform1fv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform1fvARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform1fv");
        }
    }

    public static void glUniform1fv(int location, float[] count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform1fv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform1fvARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform1fv");
        }
    }

    public static void glAttachShader(int program, int shader) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glAttachShader(program, shader);
        } else {
            throw new UnsupportedOperationException("glAttachShader");
        }
    }

    public static void glShaderSource(int shader, PointerBuffer count, int[] string) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glShaderSource(shader, count, string);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glShaderSourceARB(shader, count, string);
        } else {
            throw new UnsupportedOperationException("glShaderSource");
        }
    }

    public static void glShaderSource(int shader, CharSequence count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glShaderSource(shader, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glShaderSourceARB(shader, count);
        } else {
            throw new UnsupportedOperationException("glShaderSource");
        }
    }

    public static void glShaderSource(int shader, CharSequence[] count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glShaderSource(shader, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glShaderSourceARB(shader, count);
        } else {
            throw new UnsupportedOperationException("glShaderSource");
        }
    }

    public static void glShaderSource(int shader, PointerBuffer count, IntBuffer string) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glShaderSource(shader, count, string);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glShaderSourceARB(shader, count, string);
        } else {
            throw new UnsupportedOperationException("glShaderSource");
        }
    }

    public static void glDeleteProgram(int program) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glDeleteProgram(program);
        } else {
            throw new UnsupportedOperationException("glDeleteProgram");
        }
    }

    public static void glDeleteShader(int shader) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glDeleteShader(shader);
        } else {
            throw new UnsupportedOperationException("glDeleteShader");
        }
    }

    public static void glUniform2f(int location, float v0, float v1) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform2f(location, v0, v1);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform2fARB(location, v0, v1);
        } else {
            throw new UnsupportedOperationException("glUniform2f");
        }
    }

    public static void glUniform3f(int location, float v0, float v1, float v2) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform3f(location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform3fARB(location, v0, v1, v2);
        } else {
            throw new UnsupportedOperationException("glUniform3f");
        }
    }

    public static void glLinkProgram(int program) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glLinkProgram(program);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glLinkProgramARB(program);
        } else {
            throw new UnsupportedOperationException("glLinkProgram");
        }
    }

    public static void glDetachShader(int program, int shader) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glDetachShader(program, shader);
        } else {
            throw new UnsupportedOperationException("glDetachShader");
        }
    }

    public static void glCompileShader(int shader) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glCompileShader(shader);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glCompileShaderARB(shader);
        } else {
            throw new UnsupportedOperationException("glCompileShader");
        }
    }

    public static void glUseProgram(int program) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUseProgram(program);
        } else {
            throw new UnsupportedOperationException("glUseProgram");
        }
    }

    public static void glValidateProgram(int program) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glValidateProgram(program);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glValidateProgramARB(program);
        } else {
            throw new UnsupportedOperationException("glValidateProgram");
        }
    }

    public static int glCreateProgram() {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glCreateProgram();
        } else {
            throw new UnsupportedOperationException("glCreateProgram");
        }
    }

    public static int glCreateShader(int type) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glCreateShader(type);
        } else {
            throw new UnsupportedOperationException("glCreateShader");
        }
    }

    public static void glUniform1f(int location, float v0) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform1f(location, v0);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform1fARB(location, v0);
        } else {
            throw new UnsupportedOperationException("glUniform1f");
        }
    }

    public static void glUniform4f(int location, float v0, float v1, float v2, float v3) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform4f(location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform4fARB(location, v0, v1, v2, v3);
        } else {
            throw new UnsupportedOperationException("glUniform4f");
        }
    }

    public static boolean glIsProgram(int program) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glIsProgram(program);
        } else {
            throw new UnsupportedOperationException("glIsProgram");
        }
    }

    public static void glUniform1i(int location, int v0) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform1i(location, v0);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform1iARB(location, v0);
        } else {
            throw new UnsupportedOperationException("glUniform1i");
        }
    }

    public static void glUniform2i(int location, int v0, int v1) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform2i(location, v0, v1);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform2iARB(location, v0, v1);
        } else {
            throw new UnsupportedOperationException("glUniform2i");
        }
    }

    public static void glVertexAttrib1fv(int index, FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib1fv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib1fvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib1fvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib1fv");
        }
    }

    public static void glVertexAttrib1fv(int index, float[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib1fv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib1fvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib1fvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib1fv");
        }
    }

    public static void glVertexAttrib1sv(int index, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib1sv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib1svARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib1svARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib1sv");
        }
    }

    public static void glVertexAttrib1sv(int index, short[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib1sv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib1svARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib1svARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib1sv");
        }
    }

    public static void glUniformMatrix2fv(int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniformMatrix2fv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniformMatrix2fvARB(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2fv");
        }
    }

    public static void glUniformMatrix2fv(int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniformMatrix2fv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniformMatrix2fvARB(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2fv");
        }
    }

    public static void glVertexAttrib1dv(int index, double[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib1dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib1dvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib1dvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib1dv");
        }
    }

    public static void glVertexAttrib1dv(int index, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib1dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib1dvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib1dvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib1dv");
        }
    }

    public static void glVertexAttrib2dv(int index, double[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib2dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib2dvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib2dvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib2dv");
        }
    }

    public static void glVertexAttrib2dv(int index, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib2dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib2dvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib2dvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib2dv");
        }
    }

    public static void glUniformMatrix3fv(int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniformMatrix3fv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniformMatrix3fvARB(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3fv");
        }
    }

    public static void glUniformMatrix3fv(int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniformMatrix3fv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniformMatrix3fvARB(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3fv");
        }
    }

    public static void glVertexAttrib3fv(int index, float[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib3fv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib3fvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib3fvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib3fv");
        }
    }

    public static void glVertexAttrib3fv(int index, FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib3fv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib3fvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib3fvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib3fv");
        }
    }

    public static void glVertexAttrib1s(int index, short x) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib1s(index, x);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib1sARB(index, x);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib1sARB(index, x);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib1s");
        }
    }

    public static void glUniform4iv(int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform4iv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform4ivARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform4iv");
        }
    }

    public static void glUniform4iv(int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform4iv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform4ivARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform4iv");
        }
    }

    public static void glGetUniformfv(int program, int location, float[] params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetUniformfv(program, location, params);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glGetUniformfvARB(program, location, params);
        } else {
            throw new UnsupportedOperationException("glGetUniformfv");
        }
    }

    public static void glGetUniformfv(int program, int location, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetUniformfv(program, location, params);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glGetUniformfvARB(program, location, params);
        } else {
            throw new UnsupportedOperationException("glGetUniformfv");
        }
    }

    public static void glGetActiveUniform(int program, int index, int[] bufSize, int[] length, int[] size, ByteBuffer type) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetActiveUniform(program, index, bufSize, length, size, type);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glGetActiveUniformARB(program, index, bufSize, length, size, type);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniform");
        }
    }

    public static void glGetActiveUniform(int program, int index, IntBuffer bufSize, IntBuffer length, IntBuffer size, ByteBuffer type) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetActiveUniform(program, index, bufSize, length, size, type);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glGetActiveUniformARB(program, index, bufSize, length, size, type);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniform");
        }
    }

    public static String glGetActiveUniform(int program, int index, int bufSize, IntBuffer length, IntBuffer size) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetActiveUniform(program, index, bufSize, length, size);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            return ARBShaderObjects.glGetActiveUniformARB(program, index, bufSize, length, size);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniform");
        }
    }

    public static String glGetActiveUniform(int program, int index, IntBuffer bufSize, IntBuffer length) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetActiveUniform(program, index, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            return ARBShaderObjects.glGetActiveUniformARB(program, index, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniform");
        }
    }

    public static void glVertexAttrib2s(int index, short x, short y) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib2s(index, x, y);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib2sARB(index, x, y);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib2sARB(index, x, y);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib2s");
        }
    }

    public static void glVertexAttrib4f(int index, float x, float y, float z, float w) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4f(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4fARB(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4fARB(index, x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4f");
        }
    }

    public static void glVertexAttrib4d(int index, double x, double y, double z, double w) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4d(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4dARB(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4dARB(index, x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4d");
        }
    }

    public static void glVertexAttrib3dv(int index, double[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib3dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib3dvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib3dvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib3dv");
        }
    }

    public static void glVertexAttrib3dv(int index, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib3dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib3dvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib3dvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib3dv");
        }
    }

    public static int glGetShaderi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetShaderi has no caps");
    }

    public static void glVertexAttrib4fv(int index, FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4fv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4fvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4fvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4fv");
        }
    }

    public static void glVertexAttrib4fv(int index, float[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4fv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4fvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4fvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4fv");
        }
    }

    public static void glGetShaderiv(int shader, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetShaderiv(shader, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetShaderiv");
        }
    }

    public static void glGetShaderiv(int shader, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetShaderiv(shader, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetShaderiv");
        }
    }

    public static void glVertexAttrib4s(int index, short x, short y, short z, short w) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4s(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4sARB(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4sARB(index, x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4s");
        }
    }

    public static void glVertexAttrib2sv(int index, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib2sv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib2svARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib2svARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib2sv");
        }
    }

    public static void glVertexAttrib2sv(int index, short[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib2sv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib2svARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib2svARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib2sv");
        }
    }

    public static void glGetProgramiv(int program, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetProgramiv(program, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetProgramiv");
        }
    }

    public static void glGetProgramiv(int program, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetProgramiv(program, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetProgramiv");
        }
    }

    public static void glVertexAttrib3s(int index, short x, short y, short z) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib3s(index, x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib3sARB(index, x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib3sARB(index, x, y, z);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib3s");
        }
    }

    public static void glVertexAttrib2fv(int index, float[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib2fv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib2fvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib2fvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib2fv");
        }
    }

    public static void glVertexAttrib2fv(int index, FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib2fv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib2fvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib2fvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib2fv");
        }
    }

    public static void glVertexAttrib2f(int index, float x, float y) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib2f(index, x, y);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib2fARB(index, x, y);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib2fARB(index, x, y);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib2f");
        }
    }

    public static void glVertexAttrib3sv(int index, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib3sv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib3svARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib3svARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib3sv");
        }
    }

    public static void glVertexAttrib3sv(int index, short[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib3sv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib3svARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib3svARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib3sv");
        }
    }

    public static void glUniform1iv(int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform1iv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform1ivARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform1iv");
        }
    }

    public static void glUniform1iv(int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform1iv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform1ivARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform1iv");
        }
    }

    public static void glVertexAttrib3f(int index, float x, float y, float z) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib3f(index, x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib3fARB(index, x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib3fARB(index, x, y, z);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib3f");
        }
    }

    public static void glUniform3iv(int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform3iv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform3ivARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform3iv");
        }
    }

    public static void glUniform3iv(int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform3iv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform3ivARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform3iv");
        }
    }

    public static void glUniform3fv(int location, float[] count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform3fv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform3fvARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform3fv");
        }
    }

    public static void glUniform3fv(int location, FloatBuffer count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform3fv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform3fvARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform3fv");
        }
    }

    public static void glUniform2fv(int location, float[] count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform2fv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform2fvARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform2fv");
        }
    }

    public static void glUniform2fv(int location, FloatBuffer count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform2fv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform2fvARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform2fv");
        }
    }

    public static void glUniformMatrix4fv(int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniformMatrix4fv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniformMatrix4fvARB(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4fv");
        }
    }

    public static void glUniformMatrix4fv(int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniformMatrix4fv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniformMatrix4fvARB(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4fv");
        }
    }

    public static void glUniform4fv(int location, FloatBuffer count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform4fv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform4fvARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform4fv");
        }
    }

    public static void glUniform4fv(int location, float[] count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform4fv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform4fvARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform4fv");
        }
    }

    public static void glGetUniformiv(int program, int location, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetUniformiv(program, location, params);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glGetUniformivARB(program, location, params);
        } else {
            throw new UnsupportedOperationException("glGetUniformiv");
        }
    }

    public static void glGetUniformiv(int program, int location, int[] params) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetUniformiv(program, location, params);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glGetUniformivARB(program, location, params);
        } else {
            throw new UnsupportedOperationException("glGetUniformiv");
        }
    }

    public static float glGetUniformf(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetUniformf has no caps");
    }

    public static int glGetUniformi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetUniformi has no caps");
    }

    public static void glVertexAttrib1f(int index, float x) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib1f(index, x);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib1fARB(index, x);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib1fARB(index, x);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib1f");
        }
    }

    public static void glVertexAttrib1d(int index, double x) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib1d(index, x);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib1dARB(index, x);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib1dARB(index, x);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib1d");
        }
    }

    public static void glVertexAttrib2d(int index, double x, double y) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib2d(index, x, y);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib2dARB(index, x, y);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib2dARB(index, x, y);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib2d");
        }
    }

    public static void glUniform2iv(int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform2iv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform2ivARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform2iv");
        }
    }

    public static void glUniform2iv(int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glUniform2iv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glUniform2ivARB(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform2iv");
        }
    }

    public static void glGetShaderInfoLog(int shader, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetShaderInfoLog(shader, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetShaderInfoLog");
        }
    }

    public static String glGetShaderInfoLog(int shader, int bufSize) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetShaderInfoLog(shader, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetShaderInfoLog");
        }
    }

    public static void glGetShaderInfoLog(int shader, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetShaderInfoLog(shader, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetShaderInfoLog");
        }
    }

    public static String glGetShaderInfoLog(int shader) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetShaderInfoLog(shader);
        } else {
            throw new UnsupportedOperationException("glGetShaderInfoLog");
        }
    }

    public static String glGetShaderSource(int shader, int bufSize) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetShaderSource(shader, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            return ARBShaderObjects.glGetShaderSourceARB(shader, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetShaderSource");
        }
    }

    public static void glGetShaderSource(int shader, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetShaderSource(shader, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glGetShaderSourceARB(shader, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetShaderSource");
        }
    }

    public static void glGetShaderSource(int shader, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetShaderSource(shader, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            ARBShaderObjects.glGetShaderSourceARB(shader, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetShaderSource");
        }
    }

    public static String glGetShaderSource(int shader) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetShaderSource(shader);
        } else if (GL_CAPABILITIES.GL_ARB_shader_objects) {
            return ARBShaderObjects.glGetShaderSourceARB(shader);
        } else {
            throw new UnsupportedOperationException("glGetShaderSource");
        }
    }

    public static void glVertexAttrib3d(int index, double x, double y, double z) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib3d(index, x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib3dARB(index, x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib3dARB(index, x, y, z);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib3d");
        }
    }

    public static void glVertexAttrib4Nub(int index, byte x, byte y, byte z, byte w) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Nub(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NubARB(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NubARB(index, x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Nub");
        }
    }

    public static void glVertexAttrib4Niv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Niv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NivARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NivARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Niv");
        }
    }

    public static void glVertexAttrib4Niv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Niv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NivARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NivARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Niv");
        }
    }

    public static void glVertexAttrib4ubv(int index, ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4ubv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4ubvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4ubvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4ubv");
        }
    }

    public static void glVertexAttrib4uiv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4uiv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4uivARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4uivARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4uiv");
        }
    }

    public static void glVertexAttrib4uiv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4uiv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4uivARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4uivARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4uiv");
        }
    }

    public static void glVertexAttrib4Nbv(int index, ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Nbv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NbvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NbvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Nbv");
        }
    }

    public static void glVertexAttrib4iv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4iv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4ivARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4ivARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4iv");
        }
    }

    public static void glVertexAttrib4iv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4iv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4ivARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4ivARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4iv");
        }
    }

    public static void glGetActiveAttrib(int program, int index, IntBuffer bufSize, IntBuffer length, IntBuffer size, ByteBuffer type) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetActiveAttrib(program, index, bufSize, length, size, type);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexShader.glGetActiveAttribARB(program, index, bufSize, length, size, type);
        } else {
            throw new UnsupportedOperationException("glGetActiveAttrib");
        }
    }

    public static String glGetActiveAttrib(int program, int index, IntBuffer bufSize, IntBuffer length) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetActiveAttrib(program, index, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            return ARBVertexShader.glGetActiveAttribARB(program, index, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetActiveAttrib");
        }
    }

    public static String glGetActiveAttrib(int program, int index, int bufSize, IntBuffer length, IntBuffer size) {
        if (GL_CAPABILITIES.OpenGL20) {
            return GL20.glGetActiveAttrib(program, index, bufSize, length, size);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            return ARBVertexShader.glGetActiveAttribARB(program, index, bufSize, length, size);
        } else {
            throw new UnsupportedOperationException("glGetActiveAttrib");
        }
    }

    public static void glGetActiveAttrib(int program, int index, int[] bufSize, int[] length, int[] size, ByteBuffer type) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glGetActiveAttrib(program, index, bufSize, length, size, type);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexShader.glGetActiveAttribARB(program, index, bufSize, length, size, type);
        } else {
            throw new UnsupportedOperationException("glGetActiveAttrib");
        }
    }

    public static void glVertexAttrib4bv(int index, ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4bv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4bvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4bvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4bv");
        }
    }

    public static void glDrawBuffers(int n) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glDrawBuffers(n);
        } else {
            throw new UnsupportedOperationException("glDrawBuffers");
        }
    }

    public static void glDrawBuffers(int[] n) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glDrawBuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_draw_buffers) {
            ARBDrawBuffers.glDrawBuffersARB(n);
        } else {
            throw new UnsupportedOperationException("glDrawBuffers");
        }
    }

    public static void glDrawBuffers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glDrawBuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_draw_buffers) {
            ARBDrawBuffers.glDrawBuffersARB(n);
        } else {
            throw new UnsupportedOperationException("glDrawBuffers");
        }
    }

    public static void glVertexAttrib4usv(int index, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4usv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4usvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4usvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4usv");
        }
    }

    public static void glVertexAttrib4usv(int index, short[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4usv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4usvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4usvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4usv");
        }
    }

    public static void glVertexAttrib4sv(int index, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4sv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4svARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4svARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4sv");
        }
    }

    public static void glVertexAttrib4sv(int index, short[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4sv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4svARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4svARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4sv");
        }
    }

    public static int glGetVertexAttribi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetVertexAttribi has no caps");
    }

    public static void glVertexAttrib4Nsv(int index, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Nsv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NsvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NsvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Nsv");
        }
    }

    public static void glVertexAttrib4Nsv(int index, short[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4Nsv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4NsvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4NsvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4Nsv");
        }
    }

    public static void glVertexAttrib4dv(int index, double[] v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4dvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4dvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4dv");
        }
    }

    public static void glVertexAttrib4dv(int index, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL20) {
            GL20.glVertexAttrib4dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_program) {
            ARBVertexProgram.glVertexAttrib4dvARB(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_shader) {
            ARBVertexProgram.glVertexAttrib4dvARB(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttrib4dv");
        }
    }

    public static int glGetProgrami(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetProgrami has no caps");
    }

    // --- GL21 ---
    public static void glUniformMatrix4x2fv(int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix4x2fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4x2fv");
        }
    }

    public static void glUniformMatrix4x2fv(int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix4x2fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4x2fv");
        }
    }

    public static void glUniformMatrix2x3fv(int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix2x3fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2x3fv");
        }
    }

    public static void glUniformMatrix2x3fv(int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix2x3fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2x3fv");
        }
    }

    public static void glUniformMatrix3x4fv(int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix3x4fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3x4fv");
        }
    }

    public static void glUniformMatrix3x4fv(int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix3x4fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3x4fv");
        }
    }

    public static void glUniformMatrix3x2fv(int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix3x2fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3x2fv");
        }
    }

    public static void glUniformMatrix3x2fv(int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix3x2fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3x2fv");
        }
    }

    public static void glUniformMatrix4x3fv(int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix4x3fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4x3fv");
        }
    }

    public static void glUniformMatrix4x3fv(int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix4x3fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4x3fv");
        }
    }

    public static void glUniformMatrix2x4fv(int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix2x4fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2x4fv");
        }
    }

    public static void glUniformMatrix2x4fv(int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL21) {
            GL21.glUniformMatrix2x4fv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2x4fv");
        }
    }

    // --- GL30 ---
    public static String glGetStringi(int name, int index) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glGetStringi(name, index);
        } else {
            throw new UnsupportedOperationException("glGetStringi");
        }
    }

    public static void glDisablei(int target, int index) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glDisablei(target, index);
        } else {
            throw new UnsupportedOperationException("glDisablei");
        }
    }

    public static void glEnablei(int target, int index) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glEnablei(target, index);
        } else {
            throw new UnsupportedOperationException("glEnablei");
        }
    }

    public static void glBindFramebuffer(int target, int framebuffer) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glBindFramebuffer(target, framebuffer);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glBindFramebuffer(target, framebuffer);
        } else {
            throw new UnsupportedOperationException("glBindFramebuffer");
        }
    }

    public static int glGenRenderbuffers() {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glGenRenderbuffers();
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            return ARBFramebufferObject.glGenRenderbuffers();
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            return EXTFramebufferObject.glGenRenderbuffersEXT();
        } else {
            throw new UnsupportedOperationException("glGenRenderbuffers");
        }
    }

    public static void glGenRenderbuffers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGenRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glGenRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glGenRenderbuffersEXT(n);
        } else {
            throw new UnsupportedOperationException("glGenRenderbuffers");
        }
    }

    public static void glGenRenderbuffers(int[] n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGenRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glGenRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glGenRenderbuffersEXT(n);
        } else {
            throw new UnsupportedOperationException("glGenRenderbuffers");
        }
    }

    public static boolean glIsFramebuffer(int framebuffer) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glIsFramebuffer(framebuffer);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            return ARBFramebufferObject.glIsFramebuffer(framebuffer);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            return EXTFramebufferObject.glIsFramebufferEXT(framebuffer);
        } else {
            throw new UnsupportedOperationException("glIsFramebuffer");
        }
    }

    public static boolean glIsRenderbuffer(int renderbuffer) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glIsRenderbuffer(renderbuffer);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            return ARBFramebufferObject.glIsRenderbuffer(renderbuffer);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            return EXTFramebufferObject.glIsRenderbufferEXT(renderbuffer);
        } else {
            throw new UnsupportedOperationException("glIsRenderbuffer");
        }
    }

    public static void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_blit) {
            EXTFramebufferBlit.glBlitFramebufferEXT(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
        } else {
            throw new UnsupportedOperationException("glBlitFramebuffer");
        }
    }

    public static void glGenerateMipmap(int target) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGenerateMipmap(target);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glGenerateMipmap(target);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glGenerateMipmapEXT(target);
        } else {
            throw new UnsupportedOperationException("glGenerateMipmap");
        }
    }

    public static void glBindRenderbuffer(int target, int renderbuffer) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glBindRenderbuffer(target, renderbuffer);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glBindRenderbuffer(target, renderbuffer);
        } else {
            throw new UnsupportedOperationException("glBindRenderbuffer");
        }
    }

    public static int glGenFramebuffers() {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glGenFramebuffers();
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            return ARBFramebufferObject.glGenFramebuffers();
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            return EXTFramebufferObject.glGenFramebuffersEXT();
        } else {
            throw new UnsupportedOperationException("glGenFramebuffers");
        }
    }

    public static void glGenFramebuffers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGenFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glGenFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glGenFramebuffersEXT(n);
        } else {
            throw new UnsupportedOperationException("glGenFramebuffers");
        }
    }

    public static void glGenFramebuffers(int[] n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGenFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glGenFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glGenFramebuffersEXT(n);
        } else {
            throw new UnsupportedOperationException("glGenFramebuffers");
        }
    }

    public static void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glFramebufferTextureLayer(target, attachment, texture, level, layer);
        } else if (GL_CAPABILITIES.GL_ARB_geometry_shader4) {
            ARBGeometryShader4.glFramebufferTextureLayerARB(target, attachment, texture, level, layer);
        } else if (GL_CAPABILITIES.GL_EXT_texture_array) {
            EXTGeometryShader4.glFramebufferTextureLayerEXT(target, attachment, texture, level, layer);
        } else {
            throw new UnsupportedOperationException("glFramebufferTextureLayer");
        }
    }

    public static int glCheckFramebufferStatus(int target) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glCheckFramebufferStatus(target);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            return ARBFramebufferObject.glCheckFramebufferStatus(target);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            return EXTFramebufferObject.glCheckFramebufferStatusEXT(target);
        } else {
            throw new UnsupportedOperationException("glCheckFramebufferStatus");
        }
    }

    public static void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glFramebufferRenderbufferEXT(target, attachment, renderbuffertarget, renderbuffer);
        } else {
            throw new UnsupportedOperationException("glFramebufferRenderbuffer");
        }
    }

    public static void glDeleteRenderbuffers(int n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glDeleteRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glDeleteRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glDeleteRenderbuffersEXT(n);
        } else {
            throw new UnsupportedOperationException("glDeleteRenderbuffers");
        }
    }

    public static void glDeleteRenderbuffers(int[] n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glDeleteRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glDeleteRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glDeleteRenderbuffersEXT(n);
        } else {
            throw new UnsupportedOperationException("glDeleteRenderbuffers");
        }
    }

    public static void glDeleteRenderbuffers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glDeleteRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glDeleteRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glDeleteRenderbuffersEXT(n);
        } else {
            throw new UnsupportedOperationException("glDeleteRenderbuffers");
        }
    }

    public static void glRenderbufferStorage(int target, int internalformat, int width, int height) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glRenderbufferStorage(target, internalformat, width, height);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glRenderbufferStorage(target, internalformat, width, height);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glRenderbufferStorageEXT(target, internalformat, width, height);
        } else {
            throw new UnsupportedOperationException("glRenderbufferStorage");
        }
    }

    public static void glDeleteFramebuffers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glDeleteFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glDeleteFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(n);
        } else {
            throw new UnsupportedOperationException("glDeleteFramebuffers");
        }
    }

    public static void glDeleteFramebuffers(int[] n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glDeleteFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glDeleteFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(n);
        } else {
            throw new UnsupportedOperationException("glDeleteFramebuffers");
        }
    }

    public static void glDeleteFramebuffers(int n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glDeleteFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glDeleteFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(n);
        } else {
            throw new UnsupportedOperationException("glDeleteFramebuffers");
        }
    }

    public static void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glFramebufferTexture1D(target, attachment, textarget, texture, level);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glFramebufferTexture1D(target, attachment, textarget, texture, level);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glFramebufferTexture1DEXT(target, attachment, textarget, texture, level);
        } else {
            throw new UnsupportedOperationException("glFramebufferTexture1D");
        }
    }

    public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glFramebufferTexture2D(target, attachment, textarget, texture, level);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
        } else {
            throw new UnsupportedOperationException("glFramebufferTexture2D");
        }
    }

    public static void glFramebufferTexture3D(int target, int attachment, int textarget, int texture, int level, int zoffset) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glFramebufferTexture3D(target, attachment, textarget, texture, level, zoffset);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glFramebufferTexture3D(target, attachment, textarget, texture, level, zoffset);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glFramebufferTexture3DEXT(target, attachment, textarget, texture, level, zoffset);
        } else {
            throw new UnsupportedOperationException("glFramebufferTexture3D");
        }
    }

    public static ByteBuffer glMapBufferRange(int target, long offset, long length, int access, ByteBuffer arg4) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glMapBufferRange(target, offset, length, access, arg4);
        } else if (GL_CAPABILITIES.GL_ARB_map_buffer_range) {
            return ARBMapBufferRange.glMapBufferRange(target, offset, length, access, arg4);
        } else {
            throw new UnsupportedOperationException("glMapBufferRange");
        }
    }

    public static ByteBuffer glMapBufferRange(int target, long offset, long length, int access) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glMapBufferRange(target, offset, length, access);
        } else if (GL_CAPABILITIES.GL_ARB_map_buffer_range) {
            return ARBMapBufferRange.glMapBufferRange(target, offset, length, access);
        } else {
            throw new UnsupportedOperationException("glMapBufferRange");
        }
    }

    public static boolean glIsVertexArray(int array) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glIsVertexArray(array);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_array_object) {
            return ARBVertexArrayObject.glIsVertexArray(array);
        } else {
            throw new UnsupportedOperationException("glIsVertexArray");
        }
    }

    public static int glGetIntegeri(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetIntegeri has no caps");
    }

    public static int glGenVertexArrays() {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glGenVertexArrays();
        } else if (GL_CAPABILITIES.GL_ARB_vertex_array_object) {
            return ARBVertexArrayObject.glGenVertexArrays();
        } else {
            throw new UnsupportedOperationException("glGenVertexArrays");
        }
    }

    public static void glGenVertexArrays(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGenVertexArrays(n);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_array_object) {
            ARBVertexArrayObject.glGenVertexArrays(n);
        } else {
            throw new UnsupportedOperationException("glGenVertexArrays");
        }
    }

    public static void glGenVertexArrays(int[] n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGenVertexArrays(n);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_array_object) {
            ARBVertexArrayObject.glGenVertexArrays(n);
        } else {
            throw new UnsupportedOperationException("glGenVertexArrays");
        }
    }

    public static void glGetIntegeri_v(int target, int index, IntBuffer data) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL30.glGetIntegeri_v(target, index, data);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetIntegeri_v(target, index, data);
        } else {
            throw new UnsupportedOperationException("glGetIntegeri_v");
        }
    }

    public static void glGetIntegeri_v(int target, int index, int[] data) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL30.glGetIntegeri_v(target, index, data);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetIntegeri_v(target, index, data);
        } else {
            throw new UnsupportedOperationException("glGetIntegeri_v");
        }
    }

    public static void glBindVertexArray(int array) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glBindVertexArray(array);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_array_object) {
            ARBVertexArrayObject.glBindVertexArray(array);
        } else {
            throw new UnsupportedOperationException("glBindVertexArray");
        }
    }

    public static void glBindBufferRange(int target, int index, int buffer, long offset, long size) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL30.glBindBufferRange(target, index, buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback) {
            NVTransformFeedback.glBindBufferRangeNV(target, index, buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            EXTTransformFeedback.glBindBufferRangeEXT(target, index, buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glBindBufferRange(target, index, buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glBindBufferRange");
        }
    }

    public static void glBindBufferBase(int target, int index, int buffer) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL30.glBindBufferBase(target, index, buffer);
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback) {
            NVTransformFeedback.glBindBufferBaseNV(target, index, buffer);
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            EXTTransformFeedback.glBindBufferBaseEXT(target, index, buffer);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glBindBufferBase(target, index, buffer);
        } else {
            throw new UnsupportedOperationException("glBindBufferBase");
        }
    }

    public static void glTransformFeedbackVaryings(int program, CharSequence count, int varyings) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glTransformFeedbackVaryings(program, count, varyings);
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            EXTTransformFeedback.glTransformFeedbackVaryingsEXT(program, count, varyings);
        } else {
            throw new UnsupportedOperationException("glTransformFeedbackVaryings");
        }
    }

    public static void glTransformFeedbackVaryings(int program, CharSequence[] count, int varyings) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glTransformFeedbackVaryings(program, count, varyings);
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            EXTTransformFeedback.glTransformFeedbackVaryingsEXT(program, count, varyings);
        } else {
            throw new UnsupportedOperationException("glTransformFeedbackVaryings");
        }
    }

    public static void glTransformFeedbackVaryings(int program, PointerBuffer count, int varyings) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glTransformFeedbackVaryings(program, count, varyings);
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            EXTTransformFeedback.glTransformFeedbackVaryingsEXT(program, count, varyings);
        } else {
            throw new UnsupportedOperationException("glTransformFeedbackVaryings");
        }
    }

    public static void glGetTransformFeedbackVarying(int program, int index, int[] bufSize, int[] length, int[] size, ByteBuffer type) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetTransformFeedbackVarying(program, index, bufSize, length, size, type);
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            EXTTransformFeedback.glGetTransformFeedbackVaryingEXT(program, index, bufSize, length, size, type);
        } else {
            throw new UnsupportedOperationException("glGetTransformFeedbackVarying");
        }
    }

    public static String glGetTransformFeedbackVarying(int program, int index, int bufSize, IntBuffer length, IntBuffer size) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glGetTransformFeedbackVarying(program, index, bufSize, length, size);
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            return EXTTransformFeedback.glGetTransformFeedbackVaryingEXT(program, index, bufSize, length, size);
        } else {
            throw new UnsupportedOperationException("glGetTransformFeedbackVarying");
        }
    }

    public static void glGetTransformFeedbackVarying(int program, int index, IntBuffer bufSize, IntBuffer length, IntBuffer size, ByteBuffer type) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetTransformFeedbackVarying(program, index, bufSize, length, size, type);
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            EXTTransformFeedback.glGetTransformFeedbackVaryingEXT(program, index, bufSize, length, size, type);
        } else {
            throw new UnsupportedOperationException("glGetTransformFeedbackVarying");
        }
    }

    public static String glGetTransformFeedbackVarying(int program, int index, IntBuffer bufSize, IntBuffer length) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glGetTransformFeedbackVarying(program, index, bufSize, length);
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            return EXTTransformFeedback.glGetTransformFeedbackVaryingEXT(program, index, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetTransformFeedbackVarying");
        }
    }

    public static void glVertexAttribIPointer(int index, int size, int type, int stride, IntBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribIPointer(index, size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribIPointerEXT(index, size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribIPointer");
        }
    }

    public static void glVertexAttribIPointer(int index, int size, int type, int stride, ShortBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribIPointer(index, size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribIPointerEXT(index, size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribIPointer");
        }
    }

    public static void glVertexAttribIPointer(int index, int size, int type, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribIPointer(index, size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribIPointerEXT(index, size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribIPointer");
        }
    }

    public static void glVertexAttribIPointer(int index, int size, int type, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribIPointer(index, size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribIPointerEXT(index, size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribIPointer");
        }
    }

    public static void glVertexAttribI4uiv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4uiv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4uivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4uiv");
        }
    }

    public static void glVertexAttribI4uiv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4uiv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4uivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4uiv");
        }
    }

    public static void glVertexAttribI4ubv(int index, ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4ubv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4ubvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4ubv");
        }
    }

    public static void glVertexAttribI2uiv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI2uiv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI2uivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI2uiv");
        }
    }

    public static void glVertexAttribI2uiv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI2uiv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI2uivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI2uiv");
        }
    }

    public static int glGetVertexAttribIi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetVertexAttribIi has no caps");
    }

    public static void glBindFragDataLocation(int program, int color, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glBindFragDataLocation(program, color, name);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glBindFragDataLocationEXT(program, color, name);
        } else {
            throw new UnsupportedOperationException("glBindFragDataLocation");
        }
    }

    public static void glBindFragDataLocation(int program, int color, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glBindFragDataLocation(program, color, name);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glBindFragDataLocationEXT(program, color, name);
        } else {
            throw new UnsupportedOperationException("glBindFragDataLocation");
        }
    }

    public static void glVertexAttribI3uiv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI3uiv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI3uivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI3uiv");
        }
    }

    public static void glVertexAttribI3uiv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI3uiv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI3uivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI3uiv");
        }
    }

    public static int glGetFragDataLocation(int program, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glGetFragDataLocation(program, name);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            return EXTGPUShader4.glGetFragDataLocationEXT(program, name);
        } else {
            throw new UnsupportedOperationException("glGetFragDataLocation");
        }
    }

    public static int glGetFragDataLocation(int program, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glGetFragDataLocation(program, name);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            return EXTGPUShader4.glGetFragDataLocationEXT(program, name);
        } else {
            throw new UnsupportedOperationException("glGetFragDataLocation");
        }
    }

    public static void glVertexAttribI1uiv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI1uiv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI1uivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI1uiv");
        }
    }

    public static void glVertexAttribI1uiv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI1uiv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI1uivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI1uiv");
        }
    }

    public static void glVertexAttribI4usv(int index, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4usv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4usvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4usv");
        }
    }

    public static void glVertexAttribI4usv(int index, short[] v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4usv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4usvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4usv");
        }
    }

    public static void glGetVertexAttribIiv(int index, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetVertexAttribIiv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glGetVertexAttribIivEXT(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribIiv");
        }
    }

    public static void glGetVertexAttribIiv(int index, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetVertexAttribIiv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glGetVertexAttribIivEXT(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribIiv");
        }
    }

    public static void glGetVertexAttribIuiv(int index, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetVertexAttribIuiv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glGetVertexAttribIuivEXT(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribIuiv");
        }
    }

    public static void glGetVertexAttribIuiv(int index, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetVertexAttribIuiv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glGetVertexAttribIuivEXT(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribIuiv");
        }
    }

    public static int glGetVertexAttribIui(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetVertexAttribIui has no caps");
    }

    public static void glBeginConditionalRender(int id, int mode) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glBeginConditionalRender(id, mode);
        } else if (GL_CAPABILITIES.GL_NV_conditional_render) {
            NVConditionalRender.glBeginConditionalRenderNV(id, mode);
        } else {
            throw new UnsupportedOperationException("glBeginConditionalRender");
        }
    }

    public static void glEndConditionalRender() {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glEndConditionalRender();
        } else if (GL_CAPABILITIES.GL_NV_conditional_render) {
            NVConditionalRender.glEndConditionalRenderNV();
        } else {
            throw new UnsupportedOperationException("glEndConditionalRender");
        }
    }

    public static void glGetTexParameterIiv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetTexParameterIiv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_texture_integer) {
            EXTTextureInteger.glGetTexParameterIivEXT(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexParameterIiv");
        }
    }

    public static void glGetTexParameterIiv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetTexParameterIiv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_texture_integer) {
            EXTTextureInteger.glGetTexParameterIivEXT(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexParameterIiv");
        }
    }

    public static void glBeginTransformFeedback(int primitiveMode) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glBeginTransformFeedback(primitiveMode);
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback) {
            NVTransformFeedback.glBeginTransformFeedbackNV(primitiveMode);
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            EXTTransformFeedback.glBeginTransformFeedbackEXT(primitiveMode);
        } else {
            throw new UnsupportedOperationException("glBeginTransformFeedback");
        }
    }

    public static int glGetTexParameterIui(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTexParameterIui has no caps");
    }

    public static int glGetTexParameterIi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTexParameterIi has no caps");
    }

    public static void glEndTransformFeedback() {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glEndTransformFeedback();
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback) {
            NVTransformFeedback.glEndTransformFeedbackNV();
        } else if (GL_CAPABILITIES.GL_EXT_transform_feedback) {
            EXTTransformFeedback.glEndTransformFeedbackEXT();
        } else {
            throw new UnsupportedOperationException("glEndTransformFeedback");
        }
    }

    public static void glGetTexParameterIuiv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetTexParameterIuiv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_texture_integer) {
            EXTTextureInteger.glGetTexParameterIuivEXT(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexParameterIuiv");
        }
    }

    public static void glGetTexParameterIuiv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetTexParameterIuiv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_texture_integer) {
            EXTTextureInteger.glGetTexParameterIuivEXT(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTexParameterIuiv");
        }
    }

    public static int glGetRenderbufferParameteri(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetRenderbufferParameteri has no caps");
    }

    public static void glRenderbufferStorageMultisample(int target, int samples, int internalformat, int width, int height) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_multisample) {
            EXTFramebufferMultisample.glRenderbufferStorageMultisampleEXT(target, samples, internalformat, width, height);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
        } else {
            throw new UnsupportedOperationException("glRenderbufferStorageMultisample");
        }
    }

    public static void glGetRenderbufferParameteriv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetRenderbufferParameteriv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glGetRenderbufferParameteriv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glGetRenderbufferParameterivEXT(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetRenderbufferParameteriv");
        }
    }

    public static void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetRenderbufferParameteriv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glGetRenderbufferParameteriv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glGetRenderbufferParameterivEXT(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetRenderbufferParameteriv");
        }
    }

    public static void glClearBufferiv(int buffer, int drawbuffer, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glClearBufferiv(buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearBufferiv");
        }
    }

    public static void glClearBufferiv(int buffer, int drawbuffer, int[] value) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glClearBufferiv(buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearBufferiv");
        }
    }

    public static void glUniform3uiv(int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform3uiv(location, count);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform3uivEXT(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform3uiv");
        }
    }

    public static void glUniform3uiv(int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform3uiv(location, count);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform3uivEXT(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform3uiv");
        }
    }

    public static void glVertexAttribI4i(int index, int x, int y, int z, int w) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4i(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4iEXT(index, x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4i");
        }
    }

    public static void glVertexAttribI3ui(int index, int x, int y, int z) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI3ui(index, x, y, z);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI3uiEXT(index, x, y, z);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI3ui");
        }
    }

    public static boolean glIsEnabledi(int target, int index) {
        if (GL_CAPABILITIES.OpenGL30) {
            return GL30.glIsEnabledi(target, index);
        } else {
            throw new UnsupportedOperationException("glIsEnabledi");
        }
    }

    public static void glTexParameterIiv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glTexParameterIiv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_texture_integer) {
            EXTTextureInteger.glTexParameterIivEXT(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexParameterIiv");
        }
    }

    public static void glTexParameterIiv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glTexParameterIiv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_texture_integer) {
            EXTTextureInteger.glTexParameterIivEXT(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexParameterIiv");
        }
    }

    public static void glGetBooleani_v(int target, int index, ByteBuffer data) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetBooleani_v(target, index, data);
        } else {
            throw new UnsupportedOperationException("glGetBooleani_v");
        }
    }

    public static void glVertexAttribI1ui(int index, int x) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI1ui(index, x);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI1uiEXT(index, x);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI1ui");
        }
    }

    public static void glVertexAttribI4ui(int index, int x, int y, int z, int w) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4ui(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4uiEXT(index, x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4ui");
        }
    }

    public static void glVertexAttribI2iv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI2iv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI2ivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI2iv");
        }
    }

    public static void glVertexAttribI2iv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI2iv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI2ivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI2iv");
        }
    }

    public static void glGetUniformuiv(int program, int location, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetUniformuiv(program, location, params);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glGetUniformuivEXT(program, location, params);
        } else {
            throw new UnsupportedOperationException("glGetUniformuiv");
        }
    }

    public static void glGetUniformuiv(int program, int location, int[] params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetUniformuiv(program, location, params);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glGetUniformuivEXT(program, location, params);
        } else {
            throw new UnsupportedOperationException("glGetUniformuiv");
        }
    }

    public static void glVertexAttribI4bv(int index, ByteBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4bv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4bvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4bv");
        }
    }

    public static void glUniform4uiv(int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform4uiv(location, count);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform4uivEXT(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform4uiv");
        }
    }

    public static void glUniform4uiv(int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform4uiv(location, count);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform4uivEXT(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform4uiv");
        }
    }

    public static void glUniform2ui(int location, int v0, int v1) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform2ui(location, v0, v1);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform2uiEXT(location, v0, v1);
        } else {
            throw new UnsupportedOperationException("glUniform2ui");
        }
    }

    public static void glVertexAttribI4sv(int index, ShortBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4sv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4svEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4sv");
        }
    }

    public static void glVertexAttribI4sv(int index, short[] v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4sv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4svEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4sv");
        }
    }

    public static void glUniform4ui(int location, int v0, int v1, int v2, int v3) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform4ui(location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform4uiEXT(location, v0, v1, v2, v3);
        } else {
            throw new UnsupportedOperationException("glUniform4ui");
        }
    }

    public static void glVertexAttribI3i(int index, int x, int y, int z) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI3i(index, x, y, z);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI3iEXT(index, x, y, z);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI3i");
        }
    }

    public static void glVertexAttribI2ui(int index, int x, int y) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI2ui(index, x, y);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI2uiEXT(index, x, y);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI2ui");
        }
    }

    public static void glVertexAttribI3iv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI3iv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI3ivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI3iv");
        }
    }

    public static void glVertexAttribI3iv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI3iv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI3ivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI3iv");
        }
    }

    public static void glVertexAttribI2i(int index, int x, int y) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI2i(index, x, y);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI2iEXT(index, x, y);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI2i");
        }
    }

    public static void glUniform3ui(int location, int v0, int v1, int v2) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform3ui(location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform3uiEXT(location, v0, v1, v2);
        } else {
            throw new UnsupportedOperationException("glUniform3ui");
        }
    }

    public static int glGetUniformui(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetUniformui has no caps");
    }

    public static void glUniform2uiv(int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform2uiv(location, count);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform2uivEXT(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform2uiv");
        }
    }

    public static void glUniform2uiv(int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform2uiv(location, count);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform2uivEXT(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform2uiv");
        }
    }

    public static void glTexParameterIi(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glTexParameterIi has no caps");
    }

    public static void glClearBufferfv(int buffer, int drawbuffer, FloatBuffer value) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glClearBufferfv(buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearBufferfv");
        }
    }

    public static void glClearBufferfv(int buffer, int drawbuffer, float[] value) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glClearBufferfv(buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearBufferfv");
        }
    }

    public static void glVertexAttribI1i(int index, int x) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI1i(index, x);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI1iEXT(index, x);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI1i");
        }
    }

    public static void glUniform1ui(int location, int v0) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform1ui(location, v0);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform1uiEXT(location, v0);
        } else {
            throw new UnsupportedOperationException("glUniform1ui");
        }
    }

    public static void glUniform1uiv(int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform1uiv(location, count);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform1uivEXT(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform1uiv");
        }
    }

    public static void glUniform1uiv(int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glUniform1uiv(location, count);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glUniform1uivEXT(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform1uiv");
        }
    }

    public static void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glClearBufferfi(buffer, drawbuffer, depth, stencil);
        } else {
            throw new UnsupportedOperationException("glClearBufferfi");
        }
    }

    public static void glClampColor(int target, int clamp) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glClampColor(target, clamp);
        } else if (GL_CAPABILITIES.GL_ARB_color_buffer_float) {
            ARBColorBufferFloat.glClampColorARB(target, clamp);
        } else {
            throw new UnsupportedOperationException("glClampColor");
        }
    }

    public static void glTexParameterIui(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glTexParameterIui has no caps");
    }

    public static void glColorMaski(int index, boolean r, boolean g, boolean b, boolean a) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glColorMaski(index, r, g, b, a);
        } else {
            throw new UnsupportedOperationException("glColorMaski");
        }
    }

    public static boolean glGetBooleani(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetBooleani has no caps");
    }

    public static void glVertexAttribI1iv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI1iv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI1ivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI1iv");
        }
    }

    public static void glVertexAttribI1iv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI1iv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI1ivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI1iv");
        }
    }

    public static void glVertexAttribI4iv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4iv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4ivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4iv");
        }
    }

    public static void glVertexAttribI4iv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glVertexAttribI4iv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_gpu_shader4) {
            EXTGPUShader4.glVertexAttribI4ivEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribI4iv");
        }
    }

    public static void glTexParameterIuiv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glTexParameterIuiv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_texture_integer) {
            EXTTextureInteger.glTexParameterIuivEXT(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexParameterIuiv");
        }
    }

    public static void glTexParameterIuiv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glTexParameterIuiv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_texture_integer) {
            EXTTextureInteger.glTexParameterIuivEXT(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glTexParameterIuiv");
        }
    }

    public static void glClearBufferuiv(int buffer, int drawbuffer, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glClearBufferuiv(buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearBufferuiv");
        }
    }

    public static void glClearBufferuiv(int buffer, int drawbuffer, int[] value) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glClearBufferuiv(buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearBufferuiv");
        }
    }

    public static int glGetFramebufferAttachmentParameteri(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetFramebufferAttachmentParameteri has no caps");
    }

    public static void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glGetFramebufferAttachmentParameterivEXT(target, attachment, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetFramebufferAttachmentParameteriv");
        }
    }

    public static void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_object) {
            ARBFramebufferObject.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_framebuffer_object) {
            EXTFramebufferObject.glGetFramebufferAttachmentParameterivEXT(target, attachment, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetFramebufferAttachmentParameteriv");
        }
    }

    public static void glFlushMappedBufferRange(int target, long offset, long length) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glFlushMappedBufferRange(target, offset, length);
        } else if (GL_CAPABILITIES.GL_ARB_map_buffer_range) {
            ARBMapBufferRange.glFlushMappedBufferRange(target, offset, length);
        } else {
            throw new UnsupportedOperationException("glFlushMappedBufferRange");
        }
    }

    public static void glDeleteVertexArrays(int n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glDeleteVertexArrays(n);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_array_object) {
            ARBVertexArrayObject.glDeleteVertexArrays(n);
        } else {
            throw new UnsupportedOperationException("glDeleteVertexArrays");
        }
    }

    public static void glDeleteVertexArrays(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glDeleteVertexArrays(n);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_array_object) {
            ARBVertexArrayObject.glDeleteVertexArrays(n);
        } else {
            throw new UnsupportedOperationException("glDeleteVertexArrays");
        }
    }

    public static void glDeleteVertexArrays(int[] n) {
        if (GL_CAPABILITIES.OpenGL30) {
            GL30.glDeleteVertexArrays(n);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_array_object) {
            ARBVertexArrayObject.glDeleteVertexArrays(n);
        } else {
            throw new UnsupportedOperationException("glDeleteVertexArrays");
        }
    }

    // --- GL31 ---
    public static void glCopyBufferSubData(int readTarget, int writeTarget, long readOffset, long writeOffset, long size) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glCopyBufferSubData(readTarget, writeTarget, readOffset, writeOffset, size);
        } else if (GL_CAPABILITIES.GL_ARB_copy_buffer) {
            ARBCopyBuffer.glCopyBufferSubData(readTarget, writeTarget, readOffset, writeOffset, size);
        } else {
            throw new UnsupportedOperationException("glCopyBufferSubData");
        }
    }

    public static void glDrawArraysInstanced(int mode, int first, int count, int instancecount) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glDrawArraysInstanced(mode, first, count, instancecount);
        } else if (GL_CAPABILITIES.GL_EXT_draw_instanced) {
            EXTDrawInstanced.glDrawArraysInstancedEXT(mode, first, count, instancecount);
        } else if (GL_CAPABILITIES.GL_ARB_draw_instanced) {
            ARBDrawInstanced.glDrawArraysInstancedARB(mode, first, count, instancecount);
        } else {
            throw new UnsupportedOperationException("glDrawArraysInstanced");
        }
    }

    public static void glPrimitiveRestartIndex(int index) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glPrimitiveRestartIndex(index);
        } else {
            throw new UnsupportedOperationException("glPrimitiveRestartIndex");
        }
    }

    public static void glDrawElementsInstanced(int mode, IntBuffer count, int type) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glDrawElementsInstanced(mode, count, type);
        } else if (GL_CAPABILITIES.GL_EXT_draw_instanced) {
            EXTDrawInstanced.glDrawElementsInstancedEXT(mode, count, type);
        } else if (GL_CAPABILITIES.GL_ARB_draw_instanced) {
            ARBDrawInstanced.glDrawElementsInstancedARB(mode, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstanced");
        }
    }

    public static void glDrawElementsInstanced(int mode, ShortBuffer count, int type) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glDrawElementsInstanced(mode, count, type);
        } else if (GL_CAPABILITIES.GL_EXT_draw_instanced) {
            EXTDrawInstanced.glDrawElementsInstancedEXT(mode, count, type);
        } else if (GL_CAPABILITIES.GL_ARB_draw_instanced) {
            ARBDrawInstanced.glDrawElementsInstancedARB(mode, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstanced");
        }
    }

    public static void glDrawElementsInstanced(int mode, int count, int type, long indices, int instancecount) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glDrawElementsInstanced(mode, count, type, indices, instancecount);
        } else if (GL_CAPABILITIES.GL_EXT_draw_instanced) {
            EXTDrawInstanced.glDrawElementsInstancedEXT(mode, count, type, indices, instancecount);
        } else if (GL_CAPABILITIES.GL_ARB_draw_instanced) {
            ARBDrawInstanced.glDrawElementsInstancedARB(mode, count, type, indices, instancecount);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstanced");
        }
    }

    public static void glDrawElementsInstanced(int mode, int count, ByteBuffer type, int indices) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glDrawElementsInstanced(mode, count, type, indices);
        } else if (GL_CAPABILITIES.GL_EXT_draw_instanced) {
            EXTDrawInstanced.glDrawElementsInstancedEXT(mode, count, type, indices);
        } else if (GL_CAPABILITIES.GL_ARB_draw_instanced) {
            ARBDrawInstanced.glDrawElementsInstancedARB(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstanced");
        }
    }

    public static void glDrawElementsInstanced(int mode, ByteBuffer count, int type) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glDrawElementsInstanced(mode, count, type);
        } else if (GL_CAPABILITIES.GL_EXT_draw_instanced) {
            EXTDrawInstanced.glDrawElementsInstancedEXT(mode, count, type);
        } else if (GL_CAPABILITIES.GL_ARB_draw_instanced) {
            ARBDrawInstanced.glDrawElementsInstancedARB(mode, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstanced");
        }
    }

    public static void glGetActiveUniformBlockName(int program, int uniformBlockIndex, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformBlockName");
        }
    }

    public static String glGetActiveUniformBlockName(int program, int uniformBlockIndex) {
        if (GL_CAPABILITIES.OpenGL31) {
            return GL31.glGetActiveUniformBlockName(program, uniformBlockIndex);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            return ARBUniformBufferObject.glGetActiveUniformBlockName(program, uniformBlockIndex);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformBlockName");
        }
    }

    public static void glGetActiveUniformBlockName(int program, int uniformBlockIndex, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformBlockName");
        }
    }

    public static String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize) {
        if (GL_CAPABILITIES.OpenGL31) {
            return GL31.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            return ARBUniformBufferObject.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformBlockName");
        }
    }

    public static void glTexBuffer(int target, int internalformat, int buffer) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glTexBuffer(target, internalformat, buffer);
        } else if (GL_CAPABILITIES.GL_ARB_texture_buffer_object) {
            ARBTextureBufferObject.glTexBufferARB(target, internalformat, buffer);
        } else if (GL_CAPABILITIES.GL_EXT_texture_buffer_object) {
            EXTTextureBufferObject.glTexBufferEXT(target, internalformat, buffer);
        } else {
            throw new UnsupportedOperationException("glTexBuffer");
        }
    }

    public static void glGetUniformIndices(int program, PointerBuffer uniformCount, int[] uniformNames) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetUniformIndices(program, uniformCount, uniformNames);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetUniformIndices(program, uniformCount, uniformNames);
        } else {
            throw new UnsupportedOperationException("glGetUniformIndices");
        }
    }

    public static int glGetUniformIndices(int program, CharSequence uniformCount) {
        if (GL_CAPABILITIES.OpenGL31) {
            return GL31.glGetUniformIndices(program, uniformCount);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            return ARBUniformBufferObject.glGetUniformIndices(program, uniformCount);
        } else {
            throw new UnsupportedOperationException("glGetUniformIndices");
        }
    }

    public static void glGetUniformIndices(int program, PointerBuffer uniformCount, IntBuffer uniformNames) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetUniformIndices(program, uniformCount, uniformNames);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetUniformIndices(program, uniformCount, uniformNames);
        } else {
            throw new UnsupportedOperationException("glGetUniformIndices");
        }
    }

    public static void glGetUniformIndices(int program, CharSequence[] uniformCount, IntBuffer uniformNames) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetUniformIndices(program, uniformCount, uniformNames);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetUniformIndices(program, uniformCount, uniformNames);
        } else {
            throw new UnsupportedOperationException("glGetUniformIndices");
        }
    }

    public static void glGetActiveUniformBlockiv(int program, int uniformBlockIndex, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformBlockiv");
        }
    }

    public static void glGetActiveUniformBlockiv(int program, int uniformBlockIndex, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformBlockiv");
        }
    }

    public static void glGetActiveUniformName(int program, int uniformIndex, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetActiveUniformName(program, uniformIndex, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetActiveUniformName(program, uniformIndex, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformName");
        }
    }

    public static void glGetActiveUniformName(int program, int uniformIndex, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetActiveUniformName(program, uniformIndex, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetActiveUniformName(program, uniformIndex, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformName");
        }
    }

    public static String glGetActiveUniformName(int program, int uniformIndex) {
        if (GL_CAPABILITIES.OpenGL31) {
            return GL31.glGetActiveUniformName(program, uniformIndex);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            return ARBUniformBufferObject.glGetActiveUniformName(program, uniformIndex);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformName");
        }
    }

    public static String glGetActiveUniformName(int program, int uniformIndex, int bufSize) {
        if (GL_CAPABILITIES.OpenGL31) {
            return GL31.glGetActiveUniformName(program, uniformIndex, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            return ARBUniformBufferObject.glGetActiveUniformName(program, uniformIndex, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformName");
        }
    }

    public static int glGetUniformBlockIndex(int program, ByteBuffer uniformBlockName) {
        if (GL_CAPABILITIES.OpenGL31) {
            return GL31.glGetUniformBlockIndex(program, uniformBlockName);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            return ARBUniformBufferObject.glGetUniformBlockIndex(program, uniformBlockName);
        } else {
            throw new UnsupportedOperationException("glGetUniformBlockIndex");
        }
    }

    public static int glGetUniformBlockIndex(int program, CharSequence uniformBlockName) {
        if (GL_CAPABILITIES.OpenGL31) {
            return GL31.glGetUniformBlockIndex(program, uniformBlockName);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            return ARBUniformBufferObject.glGetUniformBlockIndex(program, uniformBlockName);
        } else {
            throw new UnsupportedOperationException("glGetUniformBlockIndex");
        }
    }

    public static int glGetActiveUniformsi(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetActiveUniformsi has no caps");
    }

    public static void glGetActiveUniformsiv(int program, IntBuffer uniformCount, int uniformIndices, IntBuffer pname) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetActiveUniformsiv(program, uniformCount, uniformIndices, pname);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetActiveUniformsiv(program, uniformCount, uniformIndices, pname);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformsiv");
        }
    }

    public static void glGetActiveUniformsiv(int program, int[] uniformCount, int uniformIndices, int[] pname) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glGetActiveUniformsiv(program, uniformCount, uniformIndices, pname);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glGetActiveUniformsiv(program, uniformCount, uniformIndices, pname);
        } else {
            throw new UnsupportedOperationException("glGetActiveUniformsiv");
        }
    }

    public static int glGetActiveUniformBlocki(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetActiveUniformBlocki has no caps");
    }

    public static void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding) {
        if (GL_CAPABILITIES.OpenGL31) {
            GL31.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
        } else if (GL_CAPABILITIES.GL_ARB_uniform_buffer_object) {
            ARBUniformBufferObject.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
        } else {
            throw new UnsupportedOperationException("glUniformBlockBinding");
        }
    }

    // --- GL32 ---
    public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, IntBuffer count, int type) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawRangeElementsBaseVertex(mode, start, end, count, type);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawRangeElementsBaseVertex(mode, start, end, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawRangeElementsBaseVertex");
        }
    }

    public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, ShortBuffer count, int type) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawRangeElementsBaseVertex(mode, start, end, count, type);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawRangeElementsBaseVertex(mode, start, end, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawRangeElementsBaseVertex");
        }
    }

    public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, int count, int type, long indices, int basevertex) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawRangeElementsBaseVertex(mode, start, end, count, type, indices, basevertex);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawRangeElementsBaseVertex(mode, start, end, count, type, indices, basevertex);
        } else {
            throw new UnsupportedOperationException("glDrawRangeElementsBaseVertex");
        }
    }

    public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, int count, ByteBuffer type, int indices) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawRangeElementsBaseVertex(mode, start, end, count, type, indices);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawRangeElementsBaseVertex(mode, start, end, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawRangeElementsBaseVertex");
        }
    }

    public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, ByteBuffer count, int type) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawRangeElementsBaseVertex(mode, start, end, count, type);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawRangeElementsBaseVertex(mode, start, end, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawRangeElementsBaseVertex");
        }
    }

    public static void glDrawElementsInstancedBaseVertex(int mode, IntBuffer count, int type, int indices) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawElementsInstancedBaseVertex(mode, count, type, indices);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawElementsInstancedBaseVertex(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseVertex");
        }
    }

    public static void glDrawElementsInstancedBaseVertex(int mode, ShortBuffer count, int type, int indices) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawElementsInstancedBaseVertex(mode, count, type, indices);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawElementsInstancedBaseVertex(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseVertex");
        }
    }

    public static void glDrawElementsInstancedBaseVertex(int mode, ByteBuffer count, int type, int indices) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawElementsInstancedBaseVertex(mode, count, type, indices);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawElementsInstancedBaseVertex(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseVertex");
        }
    }

    public static void glDrawElementsInstancedBaseVertex(int mode, int count, ByteBuffer type, int indices, int instancecount) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawElementsInstancedBaseVertex(mode, count, type, indices, instancecount);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawElementsInstancedBaseVertex(mode, count, type, indices, instancecount);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseVertex");
        }
    }

    public static void glDrawElementsInstancedBaseVertex(int mode, int count, int type, long indices, int instancecount, int basevertex) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawElementsInstancedBaseVertex(mode, count, type, indices, instancecount, basevertex);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawElementsInstancedBaseVertex(mode, count, type, indices, instancecount, basevertex);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseVertex");
        }
    }

    public static void glWaitSync(long sync, int flags, long timeout) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glWaitSync(sync, flags, timeout);
        } else if (GL_CAPABILITIES.GL_ARB_sync) {
            ARBSync.glWaitSync(sync, flags, timeout);
        } else {
            throw new UnsupportedOperationException("glWaitSync");
        }
    }

    public static int glGetSynci(long arg0, int arg1, IntBuffer arg2) {
        throw new UnsupportedOperationException("glGetSynci has no caps");
    }

    public static boolean glIsSync(long sync) {
        if (GL_CAPABILITIES.OpenGL32) {
            return GL32.glIsSync(sync);
        } else if (GL_CAPABILITIES.GL_ARB_sync) {
            return ARBSync.glIsSync(sync);
        } else {
            throw new UnsupportedOperationException("glIsSync");
        }
    }

    public static void glDrawElementsBaseVertex(int mode, IntBuffer count, int type) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawElementsBaseVertex(mode, count, type);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawElementsBaseVertex(mode, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawElementsBaseVertex");
        }
    }

    public static void glDrawElementsBaseVertex(int mode, ShortBuffer count, int type) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawElementsBaseVertex(mode, count, type);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawElementsBaseVertex(mode, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawElementsBaseVertex");
        }
    }

    public static void glDrawElementsBaseVertex(int mode, ByteBuffer count, int type) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawElementsBaseVertex(mode, count, type);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawElementsBaseVertex(mode, count, type);
        } else {
            throw new UnsupportedOperationException("glDrawElementsBaseVertex");
        }
    }

    public static void glDrawElementsBaseVertex(int mode, int count, int type, long indices, int basevertex) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawElementsBaseVertex(mode, count, type, indices, basevertex);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawElementsBaseVertex(mode, count, type, indices, basevertex);
        } else {
            throw new UnsupportedOperationException("glDrawElementsBaseVertex");
        }
    }

    public static void glDrawElementsBaseVertex(int mode, int count, ByteBuffer type, int indices) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDrawElementsBaseVertex(mode, count, type, indices);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glDrawElementsBaseVertex(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawElementsBaseVertex");
        }
    }

    public static void glProvokingVertex(int mode) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glProvokingVertex(mode);
        } else if (GL_CAPABILITIES.GL_ARB_provoking_vertex) {
            ARBProvokingVertex.glProvokingVertex(mode);
        } else if (GL_CAPABILITIES.GL_EXT_provoking_vertex) {
            EXTProvokingVertex.glProvokingVertexEXT(mode);
        } else {
            throw new UnsupportedOperationException("glProvokingVertex");
        }
    }

    public static void glDeleteSync(long sync) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glDeleteSync(sync);
        } else if (GL_CAPABILITIES.GL_ARB_sync) {
            ARBSync.glDeleteSync(sync);
        } else {
            throw new UnsupportedOperationException("glDeleteSync");
        }
    }

    public static void glGetInteger64v(int pname, long[] data) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glGetInteger64v(pname, data);
        } else if (GL_CAPABILITIES.GL_ARB_sync) {
            ARBSync.glGetInteger64v(pname, data);
        } else {
            throw new UnsupportedOperationException("glGetInteger64v");
        }
    }

    public static void glGetInteger64v(int pname, LongBuffer data) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glGetInteger64v(pname, data);
        } else if (GL_CAPABILITIES.GL_ARB_sync) {
            ARBSync.glGetInteger64v(pname, data);
        } else {
            throw new UnsupportedOperationException("glGetInteger64v");
        }
    }

    public static long glGetInteger64(int arg0) {
        throw new UnsupportedOperationException("glGetInteger64 has no caps");
    }

    public static void glGetSynciv(long sync, int pname, int[] count, int[] length) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glGetSynciv(sync, pname, count, length);
        } else if (GL_CAPABILITIES.GL_ARB_sync) {
            ARBSync.glGetSynciv(sync, pname, count, length);
        } else {
            throw new UnsupportedOperationException("glGetSynciv");
        }
    }

    public static void glGetSynciv(long sync, int pname, IntBuffer count, IntBuffer length) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glGetSynciv(sync, pname, count, length);
        } else if (GL_CAPABILITIES.GL_ARB_sync) {
            ARBSync.glGetSynciv(sync, pname, count, length);
        } else {
            throw new UnsupportedOperationException("glGetSynciv");
        }
    }

    public static long glFenceSync(int condition, int flags) {
        if (GL_CAPABILITIES.OpenGL32) {
            return GL32.glFenceSync(condition, flags);
        } else if (GL_CAPABILITIES.GL_ARB_sync) {
            return ARBSync.glFenceSync(condition, flags);
        } else {
            throw new UnsupportedOperationException("glFenceSync");
        }
    }

    public static int glClientWaitSync(long sync, int flags, long timeout) {
        if (GL_CAPABILITIES.OpenGL32) {
            return GL32.glClientWaitSync(sync, flags, timeout);
        } else if (GL_CAPABILITIES.GL_ARB_sync) {
            return ARBSync.glClientWaitSync(sync, flags, timeout);
        } else {
            throw new UnsupportedOperationException("glClientWaitSync");
        }
    }

    public static void glGetMultisamplefv(int pname, int index, FloatBuffer val) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glGetMultisamplefv(pname, index, val);
        } else if (GL_CAPABILITIES.GL_NV_explicit_multisample) {
            NVExplicitMultisample.glGetMultisamplefvNV(pname, index, val);
        } else if (GL_CAPABILITIES.GL_ARB_texture_multisample) {
            ARBTextureMultisample.glGetMultisamplefv(pname, index, val);
        } else {
            throw new UnsupportedOperationException("glGetMultisamplefv");
        }
    }

    public static void glGetMultisamplefv(int pname, int index, float[] val) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glGetMultisamplefv(pname, index, val);
        } else if (GL_CAPABILITIES.GL_NV_explicit_multisample) {
            NVExplicitMultisample.glGetMultisamplefvNV(pname, index, val);
        } else if (GL_CAPABILITIES.GL_ARB_texture_multisample) {
            ARBTextureMultisample.glGetMultisamplefv(pname, index, val);
        } else {
            throw new UnsupportedOperationException("glGetMultisamplefv");
        }
    }

    public static void glSampleMaski(int maskNumber, int mask) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glSampleMaski(maskNumber, mask);
        } else if (GL_CAPABILITIES.GL_ARB_texture_multisample) {
            ARBTextureMultisample.glSampleMaski(maskNumber, mask);
        } else {
            throw new UnsupportedOperationException("glSampleMaski");
        }
    }

    public static float glGetMultisamplef(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetMultisamplef has no caps");
    }

    public static long glGetBufferParameteri64(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetBufferParameteri64 has no caps");
    }

    public static void glGetBufferParameteri64v(int target, int pname, long[] params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glGetBufferParameteri64v(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetBufferParameteri64v");
        }
    }

    public static void glGetBufferParameteri64v(int target, int pname, LongBuffer params) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glGetBufferParameteri64v(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetBufferParameteri64v");
        }
    }

    public static void glFramebufferTexture(int target, int attachment, int texture, int level) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glFramebufferTexture(target, attachment, texture, level);
        } else if (GL_CAPABILITIES.GL_ARB_geometry_shader4) {
            ARBGeometryShader4.glFramebufferTextureARB(target, attachment, texture, level);
        } else {
            throw new UnsupportedOperationException("glFramebufferTexture");
        }
    }

    public static void glMultiDrawElementsBaseVertex(int mode, int[] count, int type, PointerBuffer indices, int[] drawcount) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glMultiDrawElementsBaseVertex(mode, count, type, indices, drawcount);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glMultiDrawElementsBaseVertex(mode, count, type, indices, drawcount);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElementsBaseVertex");
        }
    }

    public static void glMultiDrawElementsBaseVertex(int mode, IntBuffer count, int type, PointerBuffer indices, IntBuffer drawcount) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glMultiDrawElementsBaseVertex(mode, count, type, indices, drawcount);
        } else if (GL_CAPABILITIES.GL_ARB_draw_elements_base_vertex) {
            ARBDrawElementsBaseVertex.glMultiDrawElementsBaseVertex(mode, count, type, indices, drawcount);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElementsBaseVertex");
        }
    }

    public static void glGetInteger64i_v(int target, int index, long[] data) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glGetInteger64i_v(target, index, data);
        } else {
            throw new UnsupportedOperationException("glGetInteger64i_v");
        }
    }

    public static void glGetInteger64i_v(int target, int index, LongBuffer data) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glGetInteger64i_v(target, index, data);
        } else {
            throw new UnsupportedOperationException("glGetInteger64i_v");
        }
    }

    public static long glGetInteger64i(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetInteger64i has no caps");
    }

    public static void glTexImage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glTexImage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
        } else if (GL_CAPABILITIES.GL_ARB_texture_multisample) {
            ARBTextureMultisample.glTexImage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
        } else {
            throw new UnsupportedOperationException("glTexImage3DMultisample");
        }
    }

    public static void glTexImage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations) {
        if (GL_CAPABILITIES.OpenGL32) {
            GL32.glTexImage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
        } else if (GL_CAPABILITIES.GL_ARB_texture_multisample) {
            ARBTextureMultisample.glTexImage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
        } else {
            throw new UnsupportedOperationException("glTexImage2DMultisample");
        }
    }

    // --- GL33 ---
    public static int glGetFragDataIndex(int program, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL33) {
            return GL33.glGetFragDataIndex(program, name);
        } else if (GL_CAPABILITIES.GL_ARB_blend_func_extended) {
            return ARBBlendFuncExtended.glGetFragDataIndex(program, name);
        } else {
            throw new UnsupportedOperationException("glGetFragDataIndex");
        }
    }

    public static int glGetFragDataIndex(int program, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL33) {
            return GL33.glGetFragDataIndex(program, name);
        } else if (GL_CAPABILITIES.GL_ARB_blend_func_extended) {
            return ARBBlendFuncExtended.glGetFragDataIndex(program, name);
        } else {
            throw new UnsupportedOperationException("glGetFragDataIndex");
        }
    }

    public static void glBindFragDataLocationIndexed(int program, int colorNumber, int index, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glBindFragDataLocationIndexed(program, colorNumber, index, name);
        } else if (GL_CAPABILITIES.GL_ARB_blend_func_extended) {
            ARBBlendFuncExtended.glBindFragDataLocationIndexed(program, colorNumber, index, name);
        } else {
            throw new UnsupportedOperationException("glBindFragDataLocationIndexed");
        }
    }

    public static void glBindFragDataLocationIndexed(int program, int colorNumber, int index, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glBindFragDataLocationIndexed(program, colorNumber, index, name);
        } else if (GL_CAPABILITIES.GL_ARB_blend_func_extended) {
            ARBBlendFuncExtended.glBindFragDataLocationIndexed(program, colorNumber, index, name);
        } else {
            throw new UnsupportedOperationException("glBindFragDataLocationIndexed");
        }
    }

    public static void glTexCoordP3uiv(int type, IntBuffer coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP3uiv(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP3uiv(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP3uiv");
        }
    }

    public static void glTexCoordP3uiv(int type, int[] coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP3uiv(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP3uiv(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP3uiv");
        }
    }

    public static void glVertexP3uiv(int type, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexP3uiv(type, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexP3uiv(type, value);
        } else {
            throw new UnsupportedOperationException("glVertexP3uiv");
        }
    }

    public static void glVertexP3uiv(int type, int[] value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexP3uiv(type, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexP3uiv(type, value);
        } else {
            throw new UnsupportedOperationException("glVertexP3uiv");
        }
    }

    public static void glVertexP3ui(int type, int value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexP3ui(type, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexP3ui(type, value);
        } else {
            throw new UnsupportedOperationException("glVertexP3ui");
        }
    }

    public static void glTexCoordP1uiv(int type, int[] coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP1uiv(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP1uiv(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP1uiv");
        }
    }

    public static void glTexCoordP1uiv(int type, IntBuffer coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP1uiv(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP1uiv(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP1uiv");
        }
    }

    public static void glTexCoordP4uiv(int type, int[] coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP4uiv(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP4uiv(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP4uiv");
        }
    }

    public static void glTexCoordP4uiv(int type, IntBuffer coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP4uiv(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP4uiv(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP4uiv");
        }
    }

    public static void glNormalP3uiv(int type, int[] coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glNormalP3uiv(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glNormalP3uiv(type, coords);
        } else {
            throw new UnsupportedOperationException("glNormalP3uiv");
        }
    }

    public static void glNormalP3uiv(int type, IntBuffer coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glNormalP3uiv(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glNormalP3uiv(type, coords);
        } else {
            throw new UnsupportedOperationException("glNormalP3uiv");
        }
    }

    public static void glColorP3ui(int type, int color) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glColorP3ui(type, color);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glColorP3ui(type, color);
        } else {
            throw new UnsupportedOperationException("glColorP3ui");
        }
    }

    public static void glVertexP4uiv(int type, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexP4uiv(type, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexP4uiv(type, value);
        } else {
            throw new UnsupportedOperationException("glVertexP4uiv");
        }
    }

    public static void glVertexP4uiv(int type, int[] value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexP4uiv(type, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexP4uiv(type, value);
        } else {
            throw new UnsupportedOperationException("glVertexP4uiv");
        }
    }

    public static void glTexCoordP4ui(int type, int coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP4ui(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP4ui(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP4ui");
        }
    }

    public static void glTexCoordP2uiv(int type, IntBuffer coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP2uiv(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP2uiv(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP2uiv");
        }
    }

    public static void glTexCoordP2uiv(int type, int[] coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP2uiv(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP2uiv(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP2uiv");
        }
    }

    public static void glVertexP2ui(int type, int value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexP2ui(type, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexP2ui(type, value);
        } else {
            throw new UnsupportedOperationException("glVertexP2ui");
        }
    }

    public static void glTexCoordP3ui(int type, int coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP3ui(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP3ui(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP3ui");
        }
    }

    public static void glVertexP2uiv(int type, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexP2uiv(type, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexP2uiv(type, value);
        } else {
            throw new UnsupportedOperationException("glVertexP2uiv");
        }
    }

    public static void glVertexP2uiv(int type, int[] value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexP2uiv(type, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexP2uiv(type, value);
        } else {
            throw new UnsupportedOperationException("glVertexP2uiv");
        }
    }

    public static void glTexCoordP2ui(int type, int coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP2ui(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP2ui(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP2ui");
        }
    }

    public static void glColorP4ui(int type, int color) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glColorP4ui(type, color);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glColorP4ui(type, color);
        } else {
            throw new UnsupportedOperationException("glColorP4ui");
        }
    }

    public static void glVertexP4ui(int type, int value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexP4ui(type, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexP4ui(type, value);
        } else {
            throw new UnsupportedOperationException("glVertexP4ui");
        }
    }

    public static void glNormalP3ui(int type, int coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glNormalP3ui(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glNormalP3ui(type, coords);
        } else {
            throw new UnsupportedOperationException("glNormalP3ui");
        }
    }

    public static void glTexCoordP1ui(int type, int coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glTexCoordP1ui(type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glTexCoordP1ui(type, coords);
        } else {
            throw new UnsupportedOperationException("glTexCoordP1ui");
        }
    }

    public static void glDeleteSamplers(int[] count) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glDeleteSamplers(count);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glDeleteSamplers(count);
        } else {
            throw new UnsupportedOperationException("glDeleteSamplers");
        }
    }

    public static void glDeleteSamplers(IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glDeleteSamplers(count);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glDeleteSamplers(count);
        } else {
            throw new UnsupportedOperationException("glDeleteSamplers");
        }
    }

    public static void glDeleteSamplers(int count) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glDeleteSamplers(count);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glDeleteSamplers(count);
        } else {
            throw new UnsupportedOperationException("glDeleteSamplers");
        }
    }

    public static void glBindSampler(int unit, int sampler) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glBindSampler(unit, sampler);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glBindSampler(unit, sampler);
        } else {
            throw new UnsupportedOperationException("glBindSampler");
        }
    }

    public static boolean glIsSampler(int sampler) {
        if (GL_CAPABILITIES.OpenGL33) {
            return GL33.glIsSampler(sampler);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            return ARBSamplerObjects.glIsSampler(sampler);
        } else {
            throw new UnsupportedOperationException("glIsSampler");
        }
    }

    public static void glGenSamplers(int[] count) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGenSamplers(count);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glGenSamplers(count);
        } else {
            throw new UnsupportedOperationException("glGenSamplers");
        }
    }

    public static int glGenSamplers() {
        if (GL_CAPABILITIES.OpenGL33) {
            return GL33.glGenSamplers();
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            return ARBSamplerObjects.glGenSamplers();
        } else {
            throw new UnsupportedOperationException("glGenSamplers");
        }
    }

    public static void glGenSamplers(IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGenSamplers(count);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glGenSamplers(count);
        } else {
            throw new UnsupportedOperationException("glGenSamplers");
        }
    }

    public static void glQueryCounter(int id, int target) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glQueryCounter(id, target);
        } else if (GL_CAPABILITIES.GL_ARB_timer_query) {
            ARBTimerQuery.glQueryCounter(id, target);
        } else {
            throw new UnsupportedOperationException("glQueryCounter");
        }
    }

    public static void glVertexAttribDivisor(int index, int divisor) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribDivisor(index, divisor);
        } else if (GL_CAPABILITIES.GL_ARB_instanced_arrays) {
            ARBInstancedArrays.glVertexAttribDivisorARB(index, divisor);
        } else {
            throw new UnsupportedOperationException("glVertexAttribDivisor");
        }
    }

    public static void glSamplerParameteri(int sampler, int pname, int param) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSamplerParameteri(sampler, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glSamplerParameteri(sampler, pname, param);
        } else {
            throw new UnsupportedOperationException("glSamplerParameteri");
        }
    }

    public static void glSamplerParameteriv(int sampler, int pname, int[] param) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSamplerParameteriv(sampler, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glSamplerParameteriv(sampler, pname, param);
        } else {
            throw new UnsupportedOperationException("glSamplerParameteriv");
        }
    }

    public static void glSamplerParameteriv(int sampler, int pname, IntBuffer param) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSamplerParameteriv(sampler, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glSamplerParameteriv(sampler, pname, param);
        } else {
            throw new UnsupportedOperationException("glSamplerParameteriv");
        }
    }

    public static float glGetSamplerParameterf(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetSamplerParameterf has no caps");
    }

    public static void glGetSamplerParameterIuiv(int sampler, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetSamplerParameterIuiv(sampler, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glGetSamplerParameterIuiv(sampler, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetSamplerParameterIuiv");
        }
    }

    public static void glGetSamplerParameterIuiv(int sampler, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetSamplerParameterIuiv(sampler, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glGetSamplerParameterIuiv(sampler, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetSamplerParameterIuiv");
        }
    }

    public static int glGetSamplerParameterIui(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetSamplerParameterIui has no caps");
    }

    public static void glSamplerParameterfv(int sampler, int pname, float[] param) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSamplerParameterfv(sampler, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glSamplerParameterfv(sampler, pname, param);
        } else {
            throw new UnsupportedOperationException("glSamplerParameterfv");
        }
    }

    public static void glSamplerParameterfv(int sampler, int pname, FloatBuffer param) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSamplerParameterfv(sampler, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glSamplerParameterfv(sampler, pname, param);
        } else {
            throw new UnsupportedOperationException("glSamplerParameterfv");
        }
    }

    public static void glGetSamplerParameteriv(int sampler, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetSamplerParameteriv(sampler, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glGetSamplerParameteriv(sampler, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetSamplerParameteriv");
        }
    }

    public static void glGetSamplerParameteriv(int sampler, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetSamplerParameteriv(sampler, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glGetSamplerParameteriv(sampler, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetSamplerParameteriv");
        }
    }

    public static int glGetSamplerParameteri(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetSamplerParameteri has no caps");
    }

    public static void glGetSamplerParameterfv(int sampler, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetSamplerParameterfv(sampler, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glGetSamplerParameterfv(sampler, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetSamplerParameterfv");
        }
    }

    public static void glGetSamplerParameterfv(int sampler, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetSamplerParameterfv(sampler, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glGetSamplerParameterfv(sampler, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetSamplerParameterfv");
        }
    }

    public static void glSamplerParameterIiv(int sampler, int pname, IntBuffer param) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSamplerParameterIiv(sampler, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glSamplerParameterIiv(sampler, pname, param);
        } else {
            throw new UnsupportedOperationException("glSamplerParameterIiv");
        }
    }

    public static void glSamplerParameterIiv(int sampler, int pname, int[] param) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSamplerParameterIiv(sampler, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glSamplerParameterIiv(sampler, pname, param);
        } else {
            throw new UnsupportedOperationException("glSamplerParameterIiv");
        }
    }

    public static void glSamplerParameterIuiv(int sampler, int pname, IntBuffer param) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSamplerParameterIuiv(sampler, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glSamplerParameterIuiv(sampler, pname, param);
        } else {
            throw new UnsupportedOperationException("glSamplerParameterIuiv");
        }
    }

    public static void glSamplerParameterIuiv(int sampler, int pname, int[] param) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSamplerParameterIuiv(sampler, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glSamplerParameterIuiv(sampler, pname, param);
        } else {
            throw new UnsupportedOperationException("glSamplerParameterIuiv");
        }
    }

    public static void glGetSamplerParameterIiv(int sampler, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetSamplerParameterIiv(sampler, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glGetSamplerParameterIiv(sampler, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetSamplerParameterIiv");
        }
    }

    public static void glGetSamplerParameterIiv(int sampler, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetSamplerParameterIiv(sampler, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glGetSamplerParameterIiv(sampler, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetSamplerParameterIiv");
        }
    }

    public static void glSamplerParameterf(int sampler, int pname, float param) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSamplerParameterf(sampler, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_sampler_objects) {
            ARBSamplerObjects.glSamplerParameterf(sampler, pname, param);
        } else {
            throw new UnsupportedOperationException("glSamplerParameterf");
        }
    }

    public static int glGetSamplerParameterIi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetSamplerParameterIi has no caps");
    }

    public static long glGetQueryObjecti64(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetQueryObjecti64 has no caps");
    }

    public static long glGetQueryObjectui64(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetQueryObjectui64 has no caps");
    }

    public static void glGetQueryObjecti64v(int id, int pname, LongBuffer params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetQueryObjecti64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_timer_query) {
            ARBTimerQuery.glGetQueryObjecti64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_timer_query) {
            EXTTimerQuery.glGetQueryObjecti64vEXT(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjecti64v");
        }
    }

    public static void glGetQueryObjecti64v(int id, int pname, long params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetQueryObjecti64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_timer_query) {
            ARBTimerQuery.glGetQueryObjecti64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_timer_query) {
            EXTTimerQuery.glGetQueryObjecti64vEXT(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjecti64v");
        }
    }

    public static void glGetQueryObjecti64v(int id, int pname, long[] params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetQueryObjecti64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_timer_query) {
            ARBTimerQuery.glGetQueryObjecti64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_timer_query) {
            EXTTimerQuery.glGetQueryObjecti64vEXT(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjecti64v");
        }
    }

    public static void glGetQueryObjectui64v(int id, int pname, LongBuffer params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetQueryObjectui64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_timer_query) {
            ARBTimerQuery.glGetQueryObjectui64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_timer_query) {
            EXTTimerQuery.glGetQueryObjectui64vEXT(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjectui64v");
        }
    }

    public static void glGetQueryObjectui64v(int id, int pname, long[] params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetQueryObjectui64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_timer_query) {
            ARBTimerQuery.glGetQueryObjectui64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_timer_query) {
            EXTTimerQuery.glGetQueryObjectui64vEXT(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjectui64v");
        }
    }

    public static void glGetQueryObjectui64v(int id, int pname, long params) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glGetQueryObjectui64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_timer_query) {
            ARBTimerQuery.glGetQueryObjectui64v(id, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_timer_query) {
            EXTTimerQuery.glGetQueryObjectui64vEXT(id, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryObjectui64v");
        }
    }

    public static void glSecondaryColorP3ui(int type, int color) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSecondaryColorP3ui(type, color);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glSecondaryColorP3ui(type, color);
        } else {
            throw new UnsupportedOperationException("glSecondaryColorP3ui");
        }
    }

    public static void glMultiTexCoordP4ui(int texture, int type, int coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP4ui(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP4ui(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP4ui");
        }
    }

    public static void glMultiTexCoordP4uiv(int texture, int type, IntBuffer coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP4uiv(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP4uiv(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP4uiv");
        }
    }

    public static void glMultiTexCoordP4uiv(int texture, int type, int[] coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP4uiv(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP4uiv(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP4uiv");
        }
    }

    public static void glVertexAttribP2uiv(int index, int type, boolean normalized, int[] value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP2uiv(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP2uiv(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP2uiv");
        }
    }

    public static void glVertexAttribP2uiv(int index, int type, boolean normalized, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP2uiv(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP2uiv(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP2uiv");
        }
    }

    public static void glSecondaryColorP3uiv(int type, IntBuffer color) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSecondaryColorP3uiv(type, color);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glSecondaryColorP3uiv(type, color);
        } else {
            throw new UnsupportedOperationException("glSecondaryColorP3uiv");
        }
    }

    public static void glSecondaryColorP3uiv(int type, int[] color) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glSecondaryColorP3uiv(type, color);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glSecondaryColorP3uiv(type, color);
        } else {
            throw new UnsupportedOperationException("glSecondaryColorP3uiv");
        }
    }

    public static void glVertexAttribP1uiv(int index, int type, boolean normalized, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP1uiv(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP1uiv(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP1uiv");
        }
    }

    public static void glVertexAttribP1uiv(int index, int type, boolean normalized, int[] value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP1uiv(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP1uiv(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP1uiv");
        }
    }

    public static void glMultiTexCoordP1ui(int texture, int type, int coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP1ui(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP1ui(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP1ui");
        }
    }

    public static void glVertexAttribP4uiv(int index, int type, boolean normalized, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP4uiv(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP4uiv(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP4uiv");
        }
    }

    public static void glVertexAttribP4uiv(int index, int type, boolean normalized, int[] value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP4uiv(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP4uiv(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP4uiv");
        }
    }

    public static void glMultiTexCoordP3uiv(int texture, int type, IntBuffer coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP3uiv(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP3uiv(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP3uiv");
        }
    }

    public static void glMultiTexCoordP3uiv(int texture, int type, int[] coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP3uiv(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP3uiv(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP3uiv");
        }
    }

    public static void glMultiTexCoordP1uiv(int texture, int type, int[] coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP1uiv(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP1uiv(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP1uiv");
        }
    }

    public static void glMultiTexCoordP1uiv(int texture, int type, IntBuffer coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP1uiv(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP1uiv(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP1uiv");
        }
    }

    public static void glMultiTexCoordP3ui(int texture, int type, int coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP3ui(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP3ui(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP3ui");
        }
    }

    public static void glMultiTexCoordP2uiv(int texture, int type, int[] coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP2uiv(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP2uiv(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP2uiv");
        }
    }

    public static void glMultiTexCoordP2uiv(int texture, int type, IntBuffer coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP2uiv(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP2uiv(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP2uiv");
        }
    }

    public static void glMultiTexCoordP2ui(int texture, int type, int coords) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glMultiTexCoordP2ui(texture, type, coords);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glMultiTexCoordP2ui(texture, type, coords);
        } else {
            throw new UnsupportedOperationException("glMultiTexCoordP2ui");
        }
    }

    public static void glVertexAttribP3uiv(int index, int type, boolean normalized, int[] value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP3uiv(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP3uiv(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP3uiv");
        }
    }

    public static void glVertexAttribP3uiv(int index, int type, boolean normalized, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP3uiv(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP3uiv(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP3uiv");
        }
    }

    public static void glVertexAttribP1ui(int index, int type, boolean normalized, int value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP1ui(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP1ui(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP1ui");
        }
    }

    public static void glVertexAttribP4ui(int index, int type, boolean normalized, int value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP4ui(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP4ui(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP4ui");
        }
    }

    public static void glColorP3uiv(int type, IntBuffer color) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glColorP3uiv(type, color);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glColorP3uiv(type, color);
        } else {
            throw new UnsupportedOperationException("glColorP3uiv");
        }
    }

    public static void glColorP3uiv(int type, int[] color) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glColorP3uiv(type, color);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glColorP3uiv(type, color);
        } else {
            throw new UnsupportedOperationException("glColorP3uiv");
        }
    }

    public static void glColorP4uiv(int type, int[] color) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glColorP4uiv(type, color);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glColorP4uiv(type, color);
        } else {
            throw new UnsupportedOperationException("glColorP4uiv");
        }
    }

    public static void glColorP4uiv(int type, IntBuffer color) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glColorP4uiv(type, color);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glColorP4uiv(type, color);
        } else {
            throw new UnsupportedOperationException("glColorP4uiv");
        }
    }

    public static void glVertexAttribP3ui(int index, int type, boolean normalized, int value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP3ui(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP3ui(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP3ui");
        }
    }

    public static void glVertexAttribP2ui(int index, int type, boolean normalized, int value) {
        if (GL_CAPABILITIES.OpenGL33) {
            GL33.glVertexAttribP2ui(index, type, normalized, value);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_type_2_10_10_10_rev) {
            ARBVertexType2_10_10_10_REV.glVertexAttribP2ui(index, type, normalized, value);
        } else {
            throw new UnsupportedOperationException("glVertexAttribP2ui");
        }
    }

    // --- GL40 ---
    public static void glUniformMatrix2dv(int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix2dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix2dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2dv");
        }
    }

    public static void glUniformMatrix2dv(int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix2dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix2dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2dv");
        }
    }

    public static void glUniformMatrix4dv(int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix4dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix4dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4dv");
        }
    }

    public static void glUniformMatrix4dv(int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix4dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix4dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4dv");
        }
    }

    public static void glUniform1d(int location, double x) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform1d(location, x);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform1d(location, x);
        } else {
            throw new UnsupportedOperationException("glUniform1d");
        }
    }

    public static void glUniform2dv(int location, DoubleBuffer count) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform2dv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform2dv(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform2dv");
        }
    }

    public static void glUniform2dv(int location, double[] count) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform2dv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform2dv(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform2dv");
        }
    }

    public static void glUniform3dv(int location, DoubleBuffer count) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform3dv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform3dv(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform3dv");
        }
    }

    public static void glUniform3dv(int location, double[] count) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform3dv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform3dv(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform3dv");
        }
    }

    public static void glUniform1dv(int location, double[] count) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform1dv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform1dv(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform1dv");
        }
    }

    public static void glUniform1dv(int location, DoubleBuffer count) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform1dv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform1dv(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform1dv");
        }
    }

    public static void glUniform3d(int location, double x, double y, double z) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform3d(location, x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform3d(location, x, y, z);
        } else {
            throw new UnsupportedOperationException("glUniform3d");
        }
    }

    public static void glUniform4dv(int location, double[] count) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform4dv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform4dv(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform4dv");
        }
    }

    public static void glUniform4dv(int location, DoubleBuffer count) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform4dv(location, count);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform4dv(location, count);
        } else {
            throw new UnsupportedOperationException("glUniform4dv");
        }
    }

    public static void glUniform4d(int location, double x, double y, double z, double w) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform4d(location, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform4d(location, x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glUniform4d");
        }
    }

    public static void glUniform2d(int location, double x, double y) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniform2d(location, x, y);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniform2d(location, x, y);
        } else {
            throw new UnsupportedOperationException("glUniform2d");
        }
    }

    public static void glGetUniformdv(int program, int location, DoubleBuffer params) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetUniformdv(program, location, params);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glGetUniformdv(program, location, params);
        } else {
            throw new UnsupportedOperationException("glGetUniformdv");
        }
    }

    public static void glGetUniformdv(int program, int location, double[] params) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetUniformdv(program, location, params);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glGetUniformdv(program, location, params);
        } else {
            throw new UnsupportedOperationException("glGetUniformdv");
        }
    }

    public static double glGetUniformd(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetUniformd has no caps");
    }

    public static void glUniformMatrix3dv(int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix3dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix3dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3dv");
        }
    }

    public static void glUniformMatrix3dv(int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix3dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix3dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3dv");
        }
    }

    public static void glUniformMatrix4x3dv(int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix4x3dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix4x3dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4x3dv");
        }
    }

    public static void glUniformMatrix4x3dv(int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix4x3dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix4x3dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4x3dv");
        }
    }

    public static void glUniformMatrix3x4dv(int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix3x4dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix3x4dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3x4dv");
        }
    }

    public static void glUniformMatrix3x4dv(int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix3x4dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix3x4dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3x4dv");
        }
    }

    public static void glUniformMatrix2x3dv(int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix2x3dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix2x3dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2x3dv");
        }
    }

    public static void glUniformMatrix2x3dv(int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix2x3dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix2x3dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2x3dv");
        }
    }

    public static void glUniformMatrix2x4dv(int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix2x4dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix2x4dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2x4dv");
        }
    }

    public static void glUniformMatrix2x4dv(int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix2x4dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix2x4dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix2x4dv");
        }
    }

    public static void glUniformMatrix4x2dv(int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix4x2dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix4x2dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4x2dv");
        }
    }

    public static void glUniformMatrix4x2dv(int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix4x2dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix4x2dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix4x2dv");
        }
    }

    public static void glUniformMatrix3x2dv(int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix3x2dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix3x2dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3x2dv");
        }
    }

    public static void glUniformMatrix3x2dv(int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformMatrix3x2dv(location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_gpu_shader_fp64) {
            ARBGPUShaderFP64.glUniformMatrix3x2dv(location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glUniformMatrix3x2dv");
        }
    }

    public static void glDrawArraysIndirect(int mode, long indirect) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDrawArraysIndirect(mode, indirect);
        } else if (GL_CAPABILITIES.GL_ARB_draw_indirect) {
            ARBDrawIndirect.glDrawArraysIndirect(mode, indirect);
        } else {
            throw new UnsupportedOperationException("glDrawArraysIndirect");
        }
    }

    public static void glDrawArraysIndirect(int mode, ByteBuffer indirect) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDrawArraysIndirect(mode, indirect);
        } else if (GL_CAPABILITIES.GL_ARB_draw_indirect) {
            ARBDrawIndirect.glDrawArraysIndirect(mode, indirect);
        } else {
            throw new UnsupportedOperationException("glDrawArraysIndirect");
        }
    }

    public static void glDrawArraysIndirect(int mode, int[] indirect) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDrawArraysIndirect(mode, indirect);
        } else if (GL_CAPABILITIES.GL_ARB_draw_indirect) {
            ARBDrawIndirect.glDrawArraysIndirect(mode, indirect);
        } else {
            throw new UnsupportedOperationException("glDrawArraysIndirect");
        }
    }

    public static void glDrawArraysIndirect(int mode, IntBuffer indirect) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDrawArraysIndirect(mode, indirect);
        } else if (GL_CAPABILITIES.GL_ARB_draw_indirect) {
            ARBDrawIndirect.glDrawArraysIndirect(mode, indirect);
        } else {
            throw new UnsupportedOperationException("glDrawArraysIndirect");
        }
    }

    public static void glDrawElementsIndirect(int mode, int type, IntBuffer indirect) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDrawElementsIndirect(mode, type, indirect);
        } else if (GL_CAPABILITIES.GL_ARB_draw_indirect) {
            ARBDrawIndirect.glDrawElementsIndirect(mode, type, indirect);
        } else {
            throw new UnsupportedOperationException("glDrawElementsIndirect");
        }
    }

    public static void glDrawElementsIndirect(int mode, int type, int[] indirect) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDrawElementsIndirect(mode, type, indirect);
        } else if (GL_CAPABILITIES.GL_ARB_draw_indirect) {
            ARBDrawIndirect.glDrawElementsIndirect(mode, type, indirect);
        } else {
            throw new UnsupportedOperationException("glDrawElementsIndirect");
        }
    }

    public static void glDrawElementsIndirect(int mode, int type, long indirect) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDrawElementsIndirect(mode, type, indirect);
        } else if (GL_CAPABILITIES.GL_ARB_draw_indirect) {
            ARBDrawIndirect.glDrawElementsIndirect(mode, type, indirect);
        } else {
            throw new UnsupportedOperationException("glDrawElementsIndirect");
        }
    }

    public static void glDrawElementsIndirect(int mode, int type, ByteBuffer indirect) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDrawElementsIndirect(mode, type, indirect);
        } else if (GL_CAPABILITIES.GL_ARB_draw_indirect) {
            ARBDrawIndirect.glDrawElementsIndirect(mode, type, indirect);
        } else {
            throw new UnsupportedOperationException("glDrawElementsIndirect");
        }
    }

    public static int glGetProgramStagei(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetProgramStagei has no caps");
    }

    public static void glPatchParameteri(int pname, int value) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glPatchParameteri(pname, value);
        } else if (GL_CAPABILITIES.GL_ARB_tessellation_shader) {
            ARBTessellationShader.glPatchParameteri(pname, value);
        } else {
            throw new UnsupportedOperationException("glPatchParameteri");
        }
    }

    public static void glPatchParameterfv(int pname, FloatBuffer values) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glPatchParameterfv(pname, values);
        } else if (GL_CAPABILITIES.GL_ARB_tessellation_shader) {
            ARBTessellationShader.glPatchParameterfv(pname, values);
        } else {
            throw new UnsupportedOperationException("glPatchParameterfv");
        }
    }

    public static void glPatchParameterfv(int pname, float[] values) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glPatchParameterfv(pname, values);
        } else if (GL_CAPABILITIES.GL_ARB_tessellation_shader) {
            ARBTessellationShader.glPatchParameterfv(pname, values);
        } else {
            throw new UnsupportedOperationException("glPatchParameterfv");
        }
    }

    public static void glEndQueryIndexed(int target, int index) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glEndQueryIndexed(target, index);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback3) {
            ARBTransformFeedback3.glEndQueryIndexed(target, index);
        } else {
            throw new UnsupportedOperationException("glEndQueryIndexed");
        }
    }

    public static int glGetQueryIndexedi(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetQueryIndexedi has no caps");
    }

    public static void glBlendEquationSeparatei(int buf, int modeRGB, int modeAlpha) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glBlendEquationSeparatei(buf, modeRGB, modeAlpha);
        } else if (GL_CAPABILITIES.GL_ARB_draw_buffers_blend) {
            ARBDrawBuffersBlend.glBlendEquationSeparateiARB(buf, modeRGB, modeAlpha);
        } else {
            throw new UnsupportedOperationException("glBlendEquationSeparatei");
        }
    }

    public static void glBlendFuncSeparatei(int buf, int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glBlendFuncSeparatei(buf, srcRGB, dstRGB, srcAlpha, dstAlpha);
        } else if (GL_CAPABILITIES.GL_ARB_draw_buffers_blend) {
            ARBDrawBuffersBlend.glBlendFuncSeparateiARB(buf, srcRGB, dstRGB, srcAlpha, dstAlpha);
        } else {
            throw new UnsupportedOperationException("glBlendFuncSeparatei");
        }
    }

    public static void glDrawTransformFeedbackStream(int mode, int id, int stream) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDrawTransformFeedbackStream(mode, id, stream);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback3) {
            ARBTransformFeedback3.glDrawTransformFeedbackStream(mode, id, stream);
        } else {
            throw new UnsupportedOperationException("glDrawTransformFeedbackStream");
        }
    }

    public static int glGetActiveSubroutineUniformi(int arg0, int arg1, int arg2, int arg3) {
        throw new UnsupportedOperationException("glGetActiveSubroutineUniformi has no caps");
    }

    public static void glGetActiveSubroutineUniformName(int program, int shadertype, int index, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetActiveSubroutineUniformName(program, shadertype, index, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glGetActiveSubroutineUniformName(program, shadertype, index, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetActiveSubroutineUniformName");
        }
    }

    public static String glGetActiveSubroutineUniformName(int program, int shadertype, int index) {
        if (GL_CAPABILITIES.OpenGL40) {
            return GL40.glGetActiveSubroutineUniformName(program, shadertype, index);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            return ARBShaderSubroutine.glGetActiveSubroutineUniformName(program, shadertype, index);
        } else {
            throw new UnsupportedOperationException("glGetActiveSubroutineUniformName");
        }
    }

    public static void glGetActiveSubroutineUniformName(int program, int shadertype, int index, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetActiveSubroutineUniformName(program, shadertype, index, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glGetActiveSubroutineUniformName(program, shadertype, index, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetActiveSubroutineUniformName");
        }
    }

    public static String glGetActiveSubroutineUniformName(int program, int shadertype, int index, int bufSize) {
        if (GL_CAPABILITIES.OpenGL40) {
            return GL40.glGetActiveSubroutineUniformName(program, shadertype, index, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            return ARBShaderSubroutine.glGetActiveSubroutineUniformName(program, shadertype, index, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetActiveSubroutineUniformName");
        }
    }

    public static int glGetSubroutineUniformLocation(int program, int shadertype, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL40) {
            return GL40.glGetSubroutineUniformLocation(program, shadertype, name);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            return ARBShaderSubroutine.glGetSubroutineUniformLocation(program, shadertype, name);
        } else {
            throw new UnsupportedOperationException("glGetSubroutineUniformLocation");
        }
    }

    public static int glGetSubroutineUniformLocation(int program, int shadertype, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL40) {
            return GL40.glGetSubroutineUniformLocation(program, shadertype, name);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            return ARBShaderSubroutine.glGetSubroutineUniformLocation(program, shadertype, name);
        } else {
            throw new UnsupportedOperationException("glGetSubroutineUniformLocation");
        }
    }

    public static void glGetActiveSubroutineUniformiv(int program, int shadertype, int index, int pname, int[] values) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetActiveSubroutineUniformiv(program, shadertype, index, pname, values);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glGetActiveSubroutineUniformiv(program, shadertype, index, pname, values);
        } else {
            throw new UnsupportedOperationException("glGetActiveSubroutineUniformiv");
        }
    }

    public static void glGetActiveSubroutineUniformiv(int program, int shadertype, int index, int pname, IntBuffer values) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetActiveSubroutineUniformiv(program, shadertype, index, pname, values);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glGetActiveSubroutineUniformiv(program, shadertype, index, pname, values);
        } else {
            throw new UnsupportedOperationException("glGetActiveSubroutineUniformiv");
        }
    }

    public static void glBlendEquationi(int buf, int mode) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glBlendEquationi(buf, mode);
        } else if (GL_CAPABILITIES.GL_ARB_draw_buffers_blend) {
            ARBDrawBuffersBlend.glBlendEquationiARB(buf, mode);
        } else {
            throw new UnsupportedOperationException("glBlendEquationi");
        }
    }

    public static void glBlendFunci(int buf, int src, int dst) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glBlendFunci(buf, src, dst);
        } else if (GL_CAPABILITIES.GL_ARB_draw_buffers_blend) {
            ARBDrawBuffersBlend.glBlendFunciARB(buf, src, dst);
        } else {
            throw new UnsupportedOperationException("glBlendFunci");
        }
    }

    public static void glGetUniformSubroutineuiv(int shadertype, int location, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetUniformSubroutineuiv(shadertype, location, params);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glGetUniformSubroutineuiv(shadertype, location, params);
        } else {
            throw new UnsupportedOperationException("glGetUniformSubroutineuiv");
        }
    }

    public static void glGetUniformSubroutineuiv(int shadertype, int location, int[] params) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetUniformSubroutineuiv(shadertype, location, params);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glGetUniformSubroutineuiv(shadertype, location, params);
        } else {
            throw new UnsupportedOperationException("glGetUniformSubroutineuiv");
        }
    }

    public static void glUniformSubroutinesuiv(int shadertype, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformSubroutinesuiv(shadertype, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glUniformSubroutinesuiv(shadertype, count);
        } else {
            throw new UnsupportedOperationException("glUniformSubroutinesuiv");
        }
    }

    public static void glUniformSubroutinesuiv(int shadertype, int[] count) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glUniformSubroutinesuiv(shadertype, count);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glUniformSubroutinesuiv(shadertype, count);
        } else {
            throw new UnsupportedOperationException("glUniformSubroutinesuiv");
        }
    }

    public static int glGetSubroutineIndex(int program, int shadertype, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL40) {
            return GL40.glGetSubroutineIndex(program, shadertype, name);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            return ARBShaderSubroutine.glGetSubroutineIndex(program, shadertype, name);
        } else {
            throw new UnsupportedOperationException("glGetSubroutineIndex");
        }
    }

    public static int glGetSubroutineIndex(int program, int shadertype, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL40) {
            return GL40.glGetSubroutineIndex(program, shadertype, name);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            return ARBShaderSubroutine.glGetSubroutineIndex(program, shadertype, name);
        } else {
            throw new UnsupportedOperationException("glGetSubroutineIndex");
        }
    }

    public static String glGetActiveSubroutineName(int program, int shadertype, int index, int bufSize) {
        if (GL_CAPABILITIES.OpenGL40) {
            return GL40.glGetActiveSubroutineName(program, shadertype, index, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            return ARBShaderSubroutine.glGetActiveSubroutineName(program, shadertype, index, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetActiveSubroutineName");
        }
    }

    public static String glGetActiveSubroutineName(int program, int shadertype, int index) {
        if (GL_CAPABILITIES.OpenGL40) {
            return GL40.glGetActiveSubroutineName(program, shadertype, index);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            return ARBShaderSubroutine.glGetActiveSubroutineName(program, shadertype, index);
        } else {
            throw new UnsupportedOperationException("glGetActiveSubroutineName");
        }
    }

    public static void glGetActiveSubroutineName(int program, int shadertype, int index, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetActiveSubroutineName(program, shadertype, index, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glGetActiveSubroutineName(program, shadertype, index, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetActiveSubroutineName");
        }
    }

    public static void glGetActiveSubroutineName(int program, int shadertype, int index, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetActiveSubroutineName(program, shadertype, index, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glGetActiveSubroutineName(program, shadertype, index, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetActiveSubroutineName");
        }
    }

    public static int glGetUniformSubroutineui(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetUniformSubroutineui has no caps");
    }

    public static void glGetProgramStageiv(int program, int shadertype, int pname, IntBuffer values) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetProgramStageiv(program, shadertype, pname, values);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glGetProgramStageiv(program, shadertype, pname, values);
        } else {
            throw new UnsupportedOperationException("glGetProgramStageiv");
        }
    }

    public static void glGetProgramStageiv(int program, int shadertype, int pname, int[] values) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetProgramStageiv(program, shadertype, pname, values);
        } else if (GL_CAPABILITIES.GL_ARB_shader_subroutine) {
            ARBShaderSubroutine.glGetProgramStageiv(program, shadertype, pname, values);
        } else {
            throw new UnsupportedOperationException("glGetProgramStageiv");
        }
    }

    public static void glUniformSubroutinesui(int arg0, int arg1) {
        throw new UnsupportedOperationException("glUniformSubroutinesui has no caps");
    }

    public static void glResumeTransformFeedback() {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glResumeTransformFeedback();
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            ARBTransformFeedback2.glResumeTransformFeedback();
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback2) {
            NVTransformFeedback2.glResumeTransformFeedbackNV();
        } else {
            throw new UnsupportedOperationException("glResumeTransformFeedback");
        }
    }

    public static void glDeleteTransformFeedbacks(int n) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDeleteTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            ARBTransformFeedback2.glDeleteTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback2) {
            NVTransformFeedback2.glDeleteTransformFeedbacksNV(n);
        } else {
            throw new UnsupportedOperationException("glDeleteTransformFeedbacks");
        }
    }

    public static void glDeleteTransformFeedbacks(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDeleteTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            ARBTransformFeedback2.glDeleteTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback2) {
            NVTransformFeedback2.glDeleteTransformFeedbacksNV(n);
        } else {
            throw new UnsupportedOperationException("glDeleteTransformFeedbacks");
        }
    }

    public static void glDeleteTransformFeedbacks(int[] n) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDeleteTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            ARBTransformFeedback2.glDeleteTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback2) {
            NVTransformFeedback2.glDeleteTransformFeedbacksNV(n);
        } else {
            throw new UnsupportedOperationException("glDeleteTransformFeedbacks");
        }
    }

    public static void glBindTransformFeedback(int target, int id) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glBindTransformFeedback(target, id);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            ARBTransformFeedback2.glBindTransformFeedback(target, id);
        } else {
            throw new UnsupportedOperationException("glBindTransformFeedback");
        }
    }

    public static void glBeginQueryIndexed(int target, int index, int id) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glBeginQueryIndexed(target, index, id);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback3) {
            ARBTransformFeedback3.glBeginQueryIndexed(target, index, id);
        } else {
            throw new UnsupportedOperationException("glBeginQueryIndexed");
        }
    }

    public static int glGenTransformFeedbacks() {
        if (GL_CAPABILITIES.OpenGL40) {
            return GL40.glGenTransformFeedbacks();
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            return ARBTransformFeedback2.glGenTransformFeedbacks();
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback2) {
            return NVTransformFeedback2.glGenTransformFeedbacksNV();
        } else {
            throw new UnsupportedOperationException("glGenTransformFeedbacks");
        }
    }

    public static void glGenTransformFeedbacks(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGenTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            ARBTransformFeedback2.glGenTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback2) {
            NVTransformFeedback2.glGenTransformFeedbacksNV(n);
        } else {
            throw new UnsupportedOperationException("glGenTransformFeedbacks");
        }
    }

    public static void glGenTransformFeedbacks(int[] n) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGenTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            ARBTransformFeedback2.glGenTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback2) {
            NVTransformFeedback2.glGenTransformFeedbacksNV(n);
        } else {
            throw new UnsupportedOperationException("glGenTransformFeedbacks");
        }
    }

    public static void glGetQueryIndexediv(int target, int index, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetQueryIndexediv(target, index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback3) {
            ARBTransformFeedback3.glGetQueryIndexediv(target, index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryIndexediv");
        }
    }

    public static void glGetQueryIndexediv(int target, int index, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glGetQueryIndexediv(target, index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback3) {
            ARBTransformFeedback3.glGetQueryIndexediv(target, index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetQueryIndexediv");
        }
    }

    public static void glPauseTransformFeedback() {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glPauseTransformFeedback();
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            ARBTransformFeedback2.glPauseTransformFeedback();
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback2) {
            NVTransformFeedback2.glPauseTransformFeedbackNV();
        } else {
            throw new UnsupportedOperationException("glPauseTransformFeedback");
        }
    }

    public static void glDrawTransformFeedback(int mode, int id) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glDrawTransformFeedback(mode, id);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            ARBTransformFeedback2.glDrawTransformFeedback(mode, id);
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback2) {
            NVTransformFeedback2.glDrawTransformFeedbackNV(mode, id);
        } else {
            throw new UnsupportedOperationException("glDrawTransformFeedback");
        }
    }

    public static boolean glIsTransformFeedback(int id) {
        if (GL_CAPABILITIES.OpenGL40) {
            return GL40.glIsTransformFeedback(id);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback2) {
            return ARBTransformFeedback2.glIsTransformFeedback(id);
        } else if (GL_CAPABILITIES.GL_NV_transform_feedback2) {
            return NVTransformFeedback2.glIsTransformFeedbackNV(id);
        } else {
            throw new UnsupportedOperationException("glIsTransformFeedback");
        }
    }

    public static void glMinSampleShading(float value) {
        if (GL_CAPABILITIES.OpenGL40) {
            GL40.glMinSampleShading(value);
        } else if (GL_CAPABILITIES.GL_ARB_sample_shading) {
            ARBSampleShading.glMinSampleShadingARB(value);
        } else {
            throw new UnsupportedOperationException("glMinSampleShading");
        }
    }

    // --- GL41 ---
    public static void glShaderBinary(int[] count, int shaders, ByteBuffer binaryFormat) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glShaderBinary(count, shaders, binaryFormat);
        } else if (GL_CAPABILITIES.GL_ARB_ES2_compatibility) {
            ARBES2Compatibility.glShaderBinary(count, shaders, binaryFormat);
        } else {
            throw new UnsupportedOperationException("glShaderBinary");
        }
    }

    public static void glShaderBinary(IntBuffer count, int shaders, ByteBuffer binaryFormat) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glShaderBinary(count, shaders, binaryFormat);
        } else if (GL_CAPABILITIES.GL_ARB_ES2_compatibility) {
            ARBES2Compatibility.glShaderBinary(count, shaders, binaryFormat);
        } else {
            throw new UnsupportedOperationException("glShaderBinary");
        }
    }

    public static void glClearDepthf(float d) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glClearDepthf(d);
        } else if (GL_CAPABILITIES.GL_ARB_ES2_compatibility) {
            ARBES2Compatibility.glClearDepthf(d);
        } else {
            throw new UnsupportedOperationException("glClearDepthf");
        }
    }

    public static void glDepthRangef(float n, float f) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glDepthRangef(n, f);
        } else if (GL_CAPABILITIES.GL_ARB_ES2_compatibility) {
            ARBES2Compatibility.glDepthRangef(n, f);
        } else {
            throw new UnsupportedOperationException("glDepthRangef");
        }
    }

    public static void glGetProgramBinary(int program, IntBuffer bufSize, IntBuffer length, ByteBuffer binaryFormat) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetProgramBinary(program, bufSize, length, binaryFormat);
        } else if (GL_CAPABILITIES.GL_ARB_get_program_binary) {
            ARBGetProgramBinary.glGetProgramBinary(program, bufSize, length, binaryFormat);
        } else {
            throw new UnsupportedOperationException("glGetProgramBinary");
        }
    }

    public static void glGetProgramBinary(int program, int[] bufSize, int[] length, ByteBuffer binaryFormat) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetProgramBinary(program, bufSize, length, binaryFormat);
        } else if (GL_CAPABILITIES.GL_ARB_get_program_binary) {
            ARBGetProgramBinary.glGetProgramBinary(program, bufSize, length, binaryFormat);
        } else {
            throw new UnsupportedOperationException("glGetProgramBinary");
        }
    }

    public static void glProgramBinary(int program, int binaryFormat, ByteBuffer binary) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramBinary(program, binaryFormat, binary);
        } else if (GL_CAPABILITIES.GL_ARB_get_program_binary) {
            ARBGetProgramBinary.glProgramBinary(program, binaryFormat, binary);
        } else {
            throw new UnsupportedOperationException("glProgramBinary");
        }
    }

    public static void glProgramParameteri(int program, int pname, int value) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramParameteri(program, pname, value);
        } else if (GL_CAPABILITIES.GL_ARB_get_program_binary) {
            ARBGetProgramBinary.glProgramParameteri(program, pname, value);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBGetProgramBinary.glProgramParameteri(program, pname, value);
        } else if (GL_CAPABILITIES.GL_EXT_geometry_shader4) {
            EXTGeometryShader4.glProgramParameteriEXT(program, pname, value);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTGeometryShader4.glProgramParameteriEXT(program, pname, value);
        } else if (GL_CAPABILITIES.GL_ARB_geometry_shader4) {
            ARBGeometryShader4.glProgramParameteriARB(program, pname, value);
        } else {
            throw new UnsupportedOperationException("glProgramParameteri");
        }
    }

    public static void glReleaseShaderCompiler() {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glReleaseShaderCompiler();
        } else if (GL_CAPABILITIES.GL_ARB_ES2_compatibility) {
            ARBES2Compatibility.glReleaseShaderCompiler();
        } else {
            throw new UnsupportedOperationException("glReleaseShaderCompiler");
        }
    }

    public static void glGetShaderPrecisionFormat(int shadertype, int precisiontype, int[] range, int[] precision) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
        } else if (GL_CAPABILITIES.GL_ARB_ES2_compatibility) {
            ARBES2Compatibility.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
        } else {
            throw new UnsupportedOperationException("glGetShaderPrecisionFormat");
        }
    }

    public static void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
        } else if (GL_CAPABILITIES.GL_ARB_ES2_compatibility) {
            ARBES2Compatibility.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
        } else {
            throw new UnsupportedOperationException("glGetShaderPrecisionFormat");
        }
    }

    public static int glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range) {
        if (GL_CAPABILITIES.OpenGL41) {
            return GL41.glGetShaderPrecisionFormat(shadertype, precisiontype, range);
        } else if (GL_CAPABILITIES.GL_ARB_ES2_compatibility) {
            return ARBES2Compatibility.glGetShaderPrecisionFormat(shadertype, precisiontype, range);
        } else {
            throw new UnsupportedOperationException("glGetShaderPrecisionFormat");
        }
    }

    public static void glUseProgramStages(int pipeline, int stages, int program) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glUseProgramStages(pipeline, stages, program);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glUseProgramStages(pipeline, stages, program);
        } else {
            throw new UnsupportedOperationException("glUseProgramStages");
        }
    }

    public static void glProgramUniform3i(int program, int location, int v0, int v1, int v2) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3i(program, location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3i(program, location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform3iEXT(program, location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform3iEXT(program, location, v0, v1, v2);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3i");
        }
    }

    public static void glProgramUniform1i(int program, int location, int v0) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1i(program, location, v0);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1i(program, location, v0);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform1iEXT(program, location, v0);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform1iEXT(program, location, v0);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1i");
        }
    }

    public static void glProgramUniform2i(int program, int location, int v0, int v1) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2i(program, location, v0, v1);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2i(program, location, v0, v1);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform2iEXT(program, location, v0, v1);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform2iEXT(program, location, v0, v1);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2i");
        }
    }

    public static void glProgramUniform4i(int program, int location, int v0, int v1, int v2, int v3) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4i(program, location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4i(program, location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform4iEXT(program, location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform4iEXT(program, location, v0, v1, v2, v3);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4i");
        }
    }

    public static void glProgramUniform1f(int program, int location, float v0) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1f(program, location, v0);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1f(program, location, v0);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform1fEXT(program, location, v0);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform1fEXT(program, location, v0);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1f");
        }
    }

    public static void glProgramUniform1d(int program, int location, double v0) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1d(program, location, v0);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1d(program, location, v0);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1d");
        }
    }

    public static void glProgramUniform2f(int program, int location, float v0, float v1) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2f(program, location, v0, v1);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2f(program, location, v0, v1);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform2fEXT(program, location, v0, v1);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform2fEXT(program, location, v0, v1);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2f");
        }
    }

    public static void glProgramUniform3f(int program, int location, float v0, float v1, float v2) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3f(program, location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3f(program, location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform3fEXT(program, location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform3fEXT(program, location, v0, v1, v2);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3f");
        }
    }

    public static void glProgramUniform3d(int program, int location, double v0, double v1, double v2) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3d(program, location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3d(program, location, v0, v1, v2);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3d");
        }
    }

    public static void glProgramUniform4f(int program, int location, float v0, float v1, float v2, float v3) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4f(program, location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4f(program, location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform4fEXT(program, location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform4fEXT(program, location, v0, v1, v2, v3);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4f");
        }
    }

    public static void glProgramUniform4d(int program, int location, double v0, double v1, double v2, double v3) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4d(program, location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4d(program, location, v0, v1, v2, v3);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4d");
        }
    }

    public static void glProgramUniform2d(int program, int location, double v0, double v1) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2d(program, location, v0, v1);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2d(program, location, v0, v1);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2d");
        }
    }

    public static void glVertexAttribL3d(int index, double x, double y, double z) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL3d(index, x, y, z);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL3d(index, x, y, z);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL3dEXT(index, x, y, z);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL3d");
        }
    }

    public static void glVertexAttribL4dv(int index, double[] v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL4dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL4dv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL4dvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL4dv");
        }
    }

    public static void glVertexAttribL4dv(int index, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL4dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL4dv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL4dvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL4dv");
        }
    }

    public static void glVertexAttribL4d(int index, double x, double y, double z, double w) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL4d(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL4d(index, x, y, z, w);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL4dEXT(index, x, y, z, w);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL4d");
        }
    }

    public static void glVertexAttribL1dv(int index, double[] v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL1dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL1dv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL1dvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL1dv");
        }
    }

    public static void glVertexAttribL1dv(int index, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL1dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL1dv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL1dvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL1dv");
        }
    }

    public static void glVertexAttribL2d(int index, double x, double y) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL2d(index, x, y);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL2d(index, x, y);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL2dEXT(index, x, y);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL2d");
        }
    }

    public static void glVertexAttribL3dv(int index, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL3dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL3dv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL3dvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL3dv");
        }
    }

    public static void glVertexAttribL3dv(int index, double[] v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL3dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL3dv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL3dvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL3dv");
        }
    }

    public static void glVertexAttribL2dv(int index, double[] v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL2dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL2dv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL2dvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL2dv");
        }
    }

    public static void glVertexAttribL2dv(int index, DoubleBuffer v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL2dv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL2dv(index, v);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL2dvEXT(index, v);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL2dv");
        }
    }

    public static void glVertexAttribL1d(int index, double x) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribL1d(index, x);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribL1d(index, x);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribL1dEXT(index, x);
        } else {
            throw new UnsupportedOperationException("glVertexAttribL1d");
        }
    }

    public static void glProgramUniformMatrix3x2fv(int program, int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3x2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3x2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix3x2fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix3x2fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3x2fv");
        }
    }

    public static void glProgramUniformMatrix3x2fv(int program, int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3x2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3x2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix3x2fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix3x2fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3x2fv");
        }
    }

    public static void glProgramUniformMatrix3x4fv(int program, int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3x4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3x4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix3x4fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix3x4fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3x4fv");
        }
    }

    public static void glProgramUniformMatrix3x4fv(int program, int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3x4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3x4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix3x4fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix3x4fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3x4fv");
        }
    }

    public static void glProgramUniformMatrix2x4fv(int program, int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2x4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2x4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix2x4fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix2x4fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2x4fv");
        }
    }

    public static void glProgramUniformMatrix2x4fv(int program, int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2x4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2x4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix2x4fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix2x4fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2x4fv");
        }
    }

    public static void glProgramUniformMatrix2x3dv(int program, int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2x3dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2x3dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2x3dv");
        }
    }

    public static void glProgramUniformMatrix2x3dv(int program, int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2x3dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2x3dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2x3dv");
        }
    }

    public static void glProgramUniformMatrix2x3fv(int program, int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2x3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2x3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix2x3fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix2x3fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2x3fv");
        }
    }

    public static void glProgramUniformMatrix2x3fv(int program, int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2x3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2x3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix2x3fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix2x3fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2x3fv");
        }
    }

    public static void glProgramUniformMatrix4x2dv(int program, int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4x2dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4x2dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4x2dv");
        }
    }

    public static void glProgramUniformMatrix4x2dv(int program, int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4x2dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4x2dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4x2dv");
        }
    }

    public static void glProgramUniformMatrix3x2dv(int program, int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3x2dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3x2dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3x2dv");
        }
    }

    public static void glProgramUniformMatrix3x2dv(int program, int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3x2dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3x2dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3x2dv");
        }
    }

    public static String glGetProgramPipelineInfoLog(int pipeline, int bufSize) {
        if (GL_CAPABILITIES.OpenGL41) {
            return GL41.glGetProgramPipelineInfoLog(pipeline, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            return ARBSeparateShaderObjects.glGetProgramPipelineInfoLog(pipeline, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetProgramPipelineInfoLog");
        }
    }

    public static String glGetProgramPipelineInfoLog(int pipeline) {
        if (GL_CAPABILITIES.OpenGL41) {
            return GL41.glGetProgramPipelineInfoLog(pipeline);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            return ARBSeparateShaderObjects.glGetProgramPipelineInfoLog(pipeline);
        } else {
            throw new UnsupportedOperationException("glGetProgramPipelineInfoLog");
        }
    }

    public static void glGetProgramPipelineInfoLog(int pipeline, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetProgramPipelineInfoLog(pipeline, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glGetProgramPipelineInfoLog(pipeline, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetProgramPipelineInfoLog");
        }
    }

    public static void glGetProgramPipelineInfoLog(int pipeline, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetProgramPipelineInfoLog(pipeline, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glGetProgramPipelineInfoLog(pipeline, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetProgramPipelineInfoLog");
        }
    }

    public static void glProgramUniformMatrix4x3dv(int program, int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4x3dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4x3dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4x3dv");
        }
    }

    public static void glProgramUniformMatrix4x3dv(int program, int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4x3dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4x3dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4x3dv");
        }
    }

    public static void glProgramUniformMatrix4x3fv(int program, int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4x3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4x3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix4x3fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix4x3fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4x3fv");
        }
    }

    public static void glProgramUniformMatrix4x3fv(int program, int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4x3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4x3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix4x3fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix4x3fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4x3fv");
        }
    }

    public static void glProgramUniformMatrix2x4dv(int program, int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2x4dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2x4dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2x4dv");
        }
    }

    public static void glProgramUniformMatrix2x4dv(int program, int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2x4dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2x4dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2x4dv");
        }
    }

    public static void glProgramUniformMatrix4x2fv(int program, int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4x2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4x2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix4x2fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix4x2fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4x2fv");
        }
    }

    public static void glProgramUniformMatrix4x2fv(int program, int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4x2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4x2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix4x2fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix4x2fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4x2fv");
        }
    }

    public static void glProgramUniformMatrix3x4dv(int program, int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3x4dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3x4dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3x4dv");
        }
    }

    public static void glProgramUniformMatrix3x4dv(int program, int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3x4dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3x4dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3x4dv");
        }
    }

    public static void glBindProgramPipeline(int pipeline) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glBindProgramPipeline(pipeline);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glBindProgramPipeline(pipeline);
        } else {
            throw new UnsupportedOperationException("glBindProgramPipeline");
        }
    }

    public static void glProgramUniform3uiv(int program, int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform3uivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform3uivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3uiv");
        }
    }

    public static void glProgramUniform3uiv(int program, int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform3uivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform3uivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3uiv");
        }
    }

    public static void glProgramUniform4uiv(int program, int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform4uivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform4uivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4uiv");
        }
    }

    public static void glProgramUniform4uiv(int program, int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform4uivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform4uivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4uiv");
        }
    }

    public static void glActiveShaderProgram(int pipeline, int program) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glActiveShaderProgram(pipeline, program);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glActiveShaderProgram(pipeline, program);
        } else {
            throw new UnsupportedOperationException("glActiveShaderProgram");
        }
    }

    public static void glDeleteProgramPipelines(int n) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glDeleteProgramPipelines(n);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glDeleteProgramPipelines(n);
        } else {
            throw new UnsupportedOperationException("glDeleteProgramPipelines");
        }
    }

    public static void glDeleteProgramPipelines(int[] n) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glDeleteProgramPipelines(n);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glDeleteProgramPipelines(n);
        } else {
            throw new UnsupportedOperationException("glDeleteProgramPipelines");
        }
    }

    public static void glDeleteProgramPipelines(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glDeleteProgramPipelines(n);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glDeleteProgramPipelines(n);
        } else {
            throw new UnsupportedOperationException("glDeleteProgramPipelines");
        }
    }

    public static void glProgramUniform4ui(int program, int location, int v0, int v1, int v2, int v3) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4ui(program, location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4ui(program, location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform4uiEXT(program, location, v0, v1, v2, v3);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform4uiEXT(program, location, v0, v1, v2, v3);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4ui");
        }
    }

    public static void glProgramUniform3ui(int program, int location, int v0, int v1, int v2) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3ui(program, location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3ui(program, location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform3uiEXT(program, location, v0, v1, v2);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform3uiEXT(program, location, v0, v1, v2);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3ui");
        }
    }

    public static int glCreateShaderProgramv(int type, CharSequence count) {
        if (GL_CAPABILITIES.OpenGL41) {
            return GL41.glCreateShaderProgramv(type, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            return ARBSeparateShaderObjects.glCreateShaderProgramv(type, count);
        } else {
            throw new UnsupportedOperationException("glCreateShaderProgramv");
        }
    }

    public static int glCreateShaderProgramv(int type, CharSequence[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            return GL41.glCreateShaderProgramv(type, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            return ARBSeparateShaderObjects.glCreateShaderProgramv(type, count);
        } else {
            throw new UnsupportedOperationException("glCreateShaderProgramv");
        }
    }

    public static int glCreateShaderProgramv(int type, PointerBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            return GL41.glCreateShaderProgramv(type, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            return ARBSeparateShaderObjects.glCreateShaderProgramv(type, count);
        } else {
            throw new UnsupportedOperationException("glCreateShaderProgramv");
        }
    }

    public static void glGenProgramPipelines(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGenProgramPipelines(n);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glGenProgramPipelines(n);
        } else {
            throw new UnsupportedOperationException("glGenProgramPipelines");
        }
    }

    public static void glGenProgramPipelines(int[] n) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGenProgramPipelines(n);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glGenProgramPipelines(n);
        } else {
            throw new UnsupportedOperationException("glGenProgramPipelines");
        }
    }

    public static int glGenProgramPipelines() {
        if (GL_CAPABILITIES.OpenGL41) {
            return GL41.glGenProgramPipelines();
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            return ARBSeparateShaderObjects.glGenProgramPipelines();
        } else {
            throw new UnsupportedOperationException("glGenProgramPipelines");
        }
    }

    public static boolean glIsProgramPipeline(int pipeline) {
        if (GL_CAPABILITIES.OpenGL41) {
            return GL41.glIsProgramPipeline(pipeline);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            return ARBSeparateShaderObjects.glIsProgramPipeline(pipeline);
        } else {
            throw new UnsupportedOperationException("glIsProgramPipeline");
        }
    }

    public static void glProgramUniform2ui(int program, int location, int v0, int v1) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2ui(program, location, v0, v1);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2ui(program, location, v0, v1);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform2uiEXT(program, location, v0, v1);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform2uiEXT(program, location, v0, v1);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2ui");
        }
    }

    public static void glProgramUniform1iv(int program, int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform1ivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform1ivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1iv");
        }
    }

    public static void glProgramUniform1iv(int program, int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform1ivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform1ivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1iv");
        }
    }

    public static void glProgramUniform2iv(int program, int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform2ivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform2ivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2iv");
        }
    }

    public static void glProgramUniform2iv(int program, int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform2ivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform2ivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2iv");
        }
    }

    public static void glProgramUniform3iv(int program, int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform3ivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform3ivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3iv");
        }
    }

    public static void glProgramUniform3iv(int program, int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform3ivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform3ivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3iv");
        }
    }

    public static void glProgramUniform1ui(int program, int location, int v0) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1ui(program, location, v0);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1ui(program, location, v0);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform1uiEXT(program, location, v0);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform1uiEXT(program, location, v0);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1ui");
        }
    }

    public static void glProgramUniform4iv(int program, int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform4ivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform4ivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4iv");
        }
    }

    public static void glProgramUniform4iv(int program, int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4iv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform4ivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform4ivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4iv");
        }
    }

    public static void glProgramUniform1uiv(int program, int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform1uivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform1uivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1uiv");
        }
    }

    public static void glProgramUniform1uiv(int program, int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform1uivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform1uivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1uiv");
        }
    }

    public static void glGetProgramPipelineiv(int pipeline, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetProgramPipelineiv(pipeline, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glGetProgramPipelineiv(pipeline, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetProgramPipelineiv");
        }
    }

    public static void glGetProgramPipelineiv(int pipeline, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetProgramPipelineiv(pipeline, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glGetProgramPipelineiv(pipeline, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetProgramPipelineiv");
        }
    }

    public static void glProgramUniform2uiv(int program, int location, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform2uivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform2uivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2uiv");
        }
    }

    public static void glProgramUniform2uiv(int program, int location, int[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2uiv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform2uivEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform2uivEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2uiv");
        }
    }

    public static int glGetProgramPipelinei(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetProgramPipelinei has no caps");
    }

    public static void glProgramUniform2dv(int program, int location, double[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2dv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2dv(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2dv");
        }
    }

    public static void glProgramUniform2dv(int program, int location, DoubleBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2dv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2dv(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2dv");
        }
    }

    public static void glProgramUniform4fv(int program, int location, FloatBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform4fvEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform4fvEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4fv");
        }
    }

    public static void glProgramUniform4fv(int program, int location, float[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform4fvEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform4fvEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4fv");
        }
    }

    public static void glProgramUniformMatrix4dv(int program, int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4dv");
        }
    }

    public static void glProgramUniformMatrix4dv(int program, int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4dv");
        }
    }

    public static void glProgramUniformMatrix2fv(int program, int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix2fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix2fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2fv");
        }
    }

    public static void glProgramUniformMatrix2fv(int program, int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix2fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix2fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2fv");
        }
    }

    public static void glProgramUniformMatrix2dv(int program, int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2dv");
        }
    }

    public static void glProgramUniformMatrix2dv(int program, int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix2dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix2dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix2dv");
        }
    }

    public static void glProgramUniform2fv(int program, int location, FloatBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform2fvEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform2fvEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2fv");
        }
    }

    public static void glProgramUniform2fv(int program, int location, float[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform2fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform2fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform2fvEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform2fvEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform2fv");
        }
    }

    public static void glProgramUniform3dv(int program, int location, DoubleBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3dv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3dv(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3dv");
        }
    }

    public static void glProgramUniform3dv(int program, int location, double[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3dv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3dv(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3dv");
        }
    }

    public static void glProgramUniformMatrix3dv(int program, int location, boolean count, DoubleBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3dv");
        }
    }

    public static void glProgramUniformMatrix3dv(int program, int location, boolean count, double[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3dv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3dv(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3dv");
        }
    }

    public static void glProgramUniform3fv(int program, int location, FloatBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform3fvEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform3fvEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3fv");
        }
    }

    public static void glProgramUniform3fv(int program, int location, float[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform3fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform3fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform3fvEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform3fvEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform3fv");
        }
    }

    public static void glProgramUniformMatrix4fv(int program, int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix4fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix4fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4fv");
        }
    }

    public static void glProgramUniformMatrix4fv(int program, int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix4fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix4fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix4fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix4fv");
        }
    }

    public static void glProgramUniform4dv(int program, int location, double[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4dv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4dv(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4dv");
        }
    }

    public static void glProgramUniform4dv(int program, int location, DoubleBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform4dv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform4dv(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform4dv");
        }
    }

    public static void glProgramUniform1fv(int program, int location, FloatBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform1fvEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform1fvEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1fv");
        }
    }

    public static void glProgramUniform1fv(int program, int location, float[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1fv(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniform1fvEXT(program, location, count);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniform1fvEXT(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1fv");
        }
    }

    public static void glProgramUniformMatrix3fv(int program, int location, boolean count, float[] transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix3fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix3fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3fv");
        }
    }

    public static void glProgramUniformMatrix3fv(int program, int location, boolean count, FloatBuffer transpose) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniformMatrix3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniformMatrix3fv(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glProgramUniformMatrix3fvEXT(program, location, count, transpose);
        } else if (GL_CAPABILITIES.GL_EXT_separate_shader_objects) {
            EXTDirectStateAccess.glProgramUniformMatrix3fvEXT(program, location, count, transpose);
        } else {
            throw new UnsupportedOperationException("glProgramUniformMatrix3fv");
        }
    }

    public static void glProgramUniform1dv(int program, int location, double[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1dv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1dv(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1dv");
        }
    }

    public static void glProgramUniform1dv(int program, int location, DoubleBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glProgramUniform1dv(program, location, count);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glProgramUniform1dv(program, location, count);
        } else {
            throw new UnsupportedOperationException("glProgramUniform1dv");
        }
    }

    public static void glValidateProgramPipeline(int pipeline) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glValidateProgramPipeline(pipeline);
        } else if (GL_CAPABILITIES.GL_ARB_separate_shader_objects) {
            ARBSeparateShaderObjects.glValidateProgramPipeline(pipeline);
        } else {
            throw new UnsupportedOperationException("glValidateProgramPipeline");
        }
    }

    public static void glGetVertexAttribLdv(int index, int pname, DoubleBuffer params) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetVertexAttribLdv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glGetVertexAttribLdv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glGetVertexAttribLdvEXT(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribLdv");
        }
    }

    public static void glGetVertexAttribLdv(int index, int pname, double[] params) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetVertexAttribLdv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glGetVertexAttribLdv(index, pname, params);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glGetVertexAttribLdvEXT(index, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetVertexAttribLdv");
        }
    }

    public static void glVertexAttribLPointer(int index, int size, int type, int stride, ByteBuffer pointer) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribLPointer(index, size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribLPointer(index, size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribLPointerEXT(index, size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribLPointer");
        }
    }

    public static void glVertexAttribLPointer(int index, int size, int type, int stride, long pointer) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribLPointer(index, size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribLPointer(index, size, type, stride, pointer);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribLPointerEXT(index, size, type, stride, pointer);
        } else {
            throw new UnsupportedOperationException("glVertexAttribLPointer");
        }
    }

    public static void glVertexAttribLPointer(int index, int size, int type, DoubleBuffer stride) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glVertexAttribLPointer(index, size, type, stride);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_64bit) {
            ARBVertexAttrib64Bit.glVertexAttribLPointer(index, size, type, stride);
        } else if (GL_CAPABILITIES.GL_EXT_vertex_attrib_64bit) {
            EXTVertexAttrib64bit.glVertexAttribLPointerEXT(index, size, type, stride);
        } else {
            throw new UnsupportedOperationException("glVertexAttribLPointer");
        }
    }

    public static void glDepthRangeIndexed(int index, double n, double f) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glDepthRangeIndexed(index, n, f);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glDepthRangeIndexed(index, n, f);
        } else {
            throw new UnsupportedOperationException("glDepthRangeIndexed");
        }
    }

    public static void glViewportIndexedfv(int index, FloatBuffer v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glViewportIndexedfv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glViewportIndexedfv(index, v);
        } else {
            throw new UnsupportedOperationException("glViewportIndexedfv");
        }
    }

    public static void glViewportIndexedfv(int index, float[] v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glViewportIndexedfv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glViewportIndexedfv(index, v);
        } else {
            throw new UnsupportedOperationException("glViewportIndexedfv");
        }
    }

    public static float glGetFloati(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetFloati has no caps");
    }

    public static void glViewportIndexedf(int index, float x, float y, float w, float h) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glViewportIndexedf(index, x, y, w, h);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glViewportIndexedf(index, x, y, w, h);
        } else {
            throw new UnsupportedOperationException("glViewportIndexedf");
        }
    }

    public static void glGetFloati_v(int target, int index, FloatBuffer data) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetFloati_v(target, index, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glGetFloati_vEXT(target, index, data);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glGetFloati_v(target, index, data);
        } else {
            throw new UnsupportedOperationException("glGetFloati_v");
        }
    }

    public static void glGetFloati_v(int target, int index, float[] data) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetFloati_v(target, index, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glGetFloati_vEXT(target, index, data);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glGetFloati_v(target, index, data);
        } else {
            throw new UnsupportedOperationException("glGetFloati_v");
        }
    }

    public static void glGetDoublei_v(int target, int index, DoubleBuffer data) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetDoublei_v(target, index, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glGetDoublei_vEXT(target, index, data);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glGetDoublei_v(target, index, data);
        } else {
            throw new UnsupportedOperationException("glGetDoublei_v");
        }
    }

    public static void glGetDoublei_v(int target, int index, double[] data) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glGetDoublei_v(target, index, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glGetDoublei_vEXT(target, index, data);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glGetDoublei_v(target, index, data);
        } else {
            throw new UnsupportedOperationException("glGetDoublei_v");
        }
    }

    public static void glScissorIndexed(int index, int left, int bottom, int width, int height) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glScissorIndexed(index, left, bottom, width, height);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glScissorIndexed(index, left, bottom, width, height);
        } else {
            throw new UnsupportedOperationException("glScissorIndexed");
        }
    }

    public static void glDepthRangeArrayv(int first, double[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glDepthRangeArrayv(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glDepthRangeArrayv(first, count);
        } else {
            throw new UnsupportedOperationException("glDepthRangeArrayv");
        }
    }

    public static void glDepthRangeArrayv(int first, DoubleBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glDepthRangeArrayv(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glDepthRangeArrayv(first, count);
        } else {
            throw new UnsupportedOperationException("glDepthRangeArrayv");
        }
    }

    public static void glScissorIndexedv(int index, IntBuffer v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glScissorIndexedv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glScissorIndexedv(index, v);
        } else {
            throw new UnsupportedOperationException("glScissorIndexedv");
        }
    }

    public static void glScissorIndexedv(int index, int[] v) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glScissorIndexedv(index, v);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glScissorIndexedv(index, v);
        } else {
            throw new UnsupportedOperationException("glScissorIndexedv");
        }
    }

    public static void glScissorArrayv(int first, int[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glScissorArrayv(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glScissorArrayv(first, count);
        } else {
            throw new UnsupportedOperationException("glScissorArrayv");
        }
    }

    public static void glScissorArrayv(int first, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glScissorArrayv(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glScissorArrayv(first, count);
        } else {
            throw new UnsupportedOperationException("glScissorArrayv");
        }
    }

    public static void glViewportArrayv(int first, FloatBuffer count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glViewportArrayv(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glViewportArrayv(first, count);
        } else {
            throw new UnsupportedOperationException("glViewportArrayv");
        }
    }

    public static void glViewportArrayv(int first, float[] count) {
        if (GL_CAPABILITIES.OpenGL41) {
            GL41.glViewportArrayv(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_viewport_array) {
            ARBViewportArray.glViewportArrayv(first, count);
        } else {
            throw new UnsupportedOperationException("glViewportArrayv");
        }
    }

    public static double glGetDoublei(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetDoublei has no caps");
    }

    // --- GL42 ---
    public static void glDrawTransformFeedbackStreamInstanced(int mode, int id, int stream, int instancecount) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawTransformFeedbackStreamInstanced(mode, id, stream, instancecount);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback_instanced) {
            ARBTransformFeedbackInstanced.glDrawTransformFeedbackStreamInstanced(mode, id, stream, instancecount);
        } else {
            throw new UnsupportedOperationException("glDrawTransformFeedbackStreamInstanced");
        }
    }

    public static void glDrawArraysInstancedBaseInstance(int mode, int first, int count, int instancecount, int baseinstance) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawArraysInstancedBaseInstance(mode, first, count, instancecount, baseinstance);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawArraysInstancedBaseInstance(mode, first, count, instancecount, baseinstance);
        } else {
            throw new UnsupportedOperationException("glDrawArraysInstancedBaseInstance");
        }
    }

    public static void glDrawElementsInstancedBaseInstance(int mode, IntBuffer count, int type, int indices) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawElementsInstancedBaseInstance(mode, count, type, indices);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawElementsInstancedBaseInstance(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseInstance");
        }
    }

    public static void glDrawElementsInstancedBaseInstance(int mode, int count, int type, long indices, int instancecount, int baseinstance) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawElementsInstancedBaseInstance(mode, count, type, indices, instancecount, baseinstance);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawElementsInstancedBaseInstance(mode, count, type, indices, instancecount, baseinstance);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseInstance");
        }
    }

    public static void glDrawElementsInstancedBaseInstance(int mode, ShortBuffer count, int type, int indices) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawElementsInstancedBaseInstance(mode, count, type, indices);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawElementsInstancedBaseInstance(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseInstance");
        }
    }

    public static void glDrawElementsInstancedBaseInstance(int mode, ByteBuffer count, int type, int indices) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawElementsInstancedBaseInstance(mode, count, type, indices);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawElementsInstancedBaseInstance(mode, count, type, indices);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseInstance");
        }
    }

    public static void glDrawElementsInstancedBaseInstance(int mode, int count, ByteBuffer type, int indices, int instancecount) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawElementsInstancedBaseInstance(mode, count, type, indices, instancecount);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawElementsInstancedBaseInstance(mode, count, type, indices, instancecount);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseInstance");
        }
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, IntBuffer count, int type, int indices, int instancecount) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, instancecount);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, instancecount);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseVertexBaseInstance");
        }
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, int count, int type, long indices, int instancecount, int basevertex, int baseinstance) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, instancecount, basevertex, baseinstance);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, instancecount, basevertex, baseinstance);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseVertexBaseInstance");
        }
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, int count, ByteBuffer type, int indices, int instancecount, int basevertex) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, instancecount, basevertex);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, instancecount, basevertex);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseVertexBaseInstance");
        }
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, ByteBuffer count, int type, int indices, int instancecount) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, instancecount);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, instancecount);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseVertexBaseInstance");
        }
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, ShortBuffer count, int type, int indices, int instancecount) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, instancecount);
        } else if (GL_CAPABILITIES.GL_ARB_base_instance) {
            ARBBaseInstance.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, instancecount);
        } else {
            throw new UnsupportedOperationException("glDrawElementsInstancedBaseVertexBaseInstance");
        }
    }

    public static void glBindImageTexture(int unit, int texture, int level, boolean layered, int layer, int access, int format) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glBindImageTexture(unit, texture, level, layered, layer, access, format);
        } else if (GL_CAPABILITIES.GL_ARB_shader_image_load_store) {
            ARBShaderImageLoadStore.glBindImageTexture(unit, texture, level, layered, layer, access, format);
        } else {
            throw new UnsupportedOperationException("glBindImageTexture");
        }
    }

    public static void glMemoryBarrier(int barriers) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glMemoryBarrier(barriers);
        } else if (GL_CAPABILITIES.GL_EXT_shader_image_load_store) {
            EXTShaderImageLoadStore.glMemoryBarrierEXT(barriers);
        } else if (GL_CAPABILITIES.GL_ARB_shader_image_load_store) {
            ARBShaderImageLoadStore.glMemoryBarrier(barriers);
        } else {
            throw new UnsupportedOperationException("glMemoryBarrier");
        }
    }

    public static void glTexStorage1D(int target, int levels, int internalformat, int width) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glTexStorage1D(target, levels, internalformat, width);
        } else if (GL_CAPABILITIES.GL_ARB_texture_storage) {
            ARBTextureStorage.glTexStorage1D(target, levels, internalformat, width);
        } else if (GL_CAPABILITIES.GL_EXT_texture_storage) {
            EXTTextureStorage.glTexStorage1DEXT(target, levels, internalformat, width);
        } else {
            throw new UnsupportedOperationException("glTexStorage1D");
        }
    }

    public static void glTexStorage2D(int target, int levels, int internalformat, int width, int height) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glTexStorage2D(target, levels, internalformat, width, height);
        } else if (GL_CAPABILITIES.GL_ARB_texture_storage) {
            ARBTextureStorage.glTexStorage2D(target, levels, internalformat, width, height);
        } else if (GL_CAPABILITIES.GL_EXT_texture_storage) {
            EXTTextureStorage.glTexStorage2DEXT(target, levels, internalformat, width, height);
        } else {
            throw new UnsupportedOperationException("glTexStorage2D");
        }
    }

    public static void glTexStorage3D(int target, int levels, int internalformat, int width, int height, int depth) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glTexStorage3D(target, levels, internalformat, width, height, depth);
        } else if (GL_CAPABILITIES.GL_ARB_texture_storage) {
            ARBTextureStorage.glTexStorage3D(target, levels, internalformat, width, height, depth);
        } else if (GL_CAPABILITIES.GL_EXT_texture_storage) {
            EXTTextureStorage.glTexStorage3DEXT(target, levels, internalformat, width, height, depth);
        } else {
            throw new UnsupportedOperationException("glTexStorage3D");
        }
    }

    public static void glDrawTransformFeedbackInstanced(int mode, int id, int instancecount) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glDrawTransformFeedbackInstanced(mode, id, instancecount);
        } else if (GL_CAPABILITIES.GL_ARB_transform_feedback_instanced) {
            ARBTransformFeedbackInstanced.glDrawTransformFeedbackInstanced(mode, id, instancecount);
        } else {
            throw new UnsupportedOperationException("glDrawTransformFeedbackInstanced");
        }
    }

    public static int glGetActiveAtomicCounterBufferi(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetActiveAtomicCounterBufferi has no caps");
    }

    public static void glGetActiveAtomicCounterBufferiv(int program, int bufferIndex, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glGetActiveAtomicCounterBufferiv(program, bufferIndex, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_shader_atomic_counters) {
            ARBShaderAtomicCounters.glGetActiveAtomicCounterBufferiv(program, bufferIndex, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetActiveAtomicCounterBufferiv");
        }
    }

    public static void glGetActiveAtomicCounterBufferiv(int program, int bufferIndex, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glGetActiveAtomicCounterBufferiv(program, bufferIndex, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_shader_atomic_counters) {
            ARBShaderAtomicCounters.glGetActiveAtomicCounterBufferiv(program, bufferIndex, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetActiveAtomicCounterBufferiv");
        }
    }

    public static int glGetInternalformati(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetInternalformati has no caps");
    }

    public static void glGetInternalformativ(int target, int internalformat, int pname, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glGetInternalformativ(target, internalformat, pname, count);
        } else if (GL_CAPABILITIES.GL_ARB_internalformat_query) {
            ARBInternalformatQuery.glGetInternalformativ(target, internalformat, pname, count);
        } else {
            throw new UnsupportedOperationException("glGetInternalformativ");
        }
    }

    public static void glGetInternalformativ(int target, int internalformat, int pname, int[] count) {
        if (GL_CAPABILITIES.OpenGL42) {
            GL42.glGetInternalformativ(target, internalformat, pname, count);
        } else if (GL_CAPABILITIES.GL_ARB_internalformat_query) {
            ARBInternalformatQuery.glGetInternalformativ(target, internalformat, pname, count);
        } else {
            throw new UnsupportedOperationException("glGetInternalformativ");
        }
    }

    // --- GL43 ---
    public static void glClearBufferSubData(int target, int internalformat, long offset, long size, int format, int type, FloatBuffer data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferSubData");
        }
    }

    public static void glClearBufferSubData(int target, int internalformat, long offset, long size, int format, int type, IntBuffer data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferSubData");
        }
    }

    public static void glClearBufferSubData(int target, int internalformat, long offset, long size, int format, int type, ShortBuffer data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferSubData");
        }
    }

    public static void glClearBufferSubData(int target, int internalformat, long offset, long size, int format, int type, float[] data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferSubData");
        }
    }

    public static void glClearBufferSubData(int target, int internalformat, long offset, long size, int format, int type, short[] data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferSubData");
        }
    }

    public static void glClearBufferSubData(int target, int internalformat, long offset, long size, int format, int type, int[] data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferSubData");
        }
    }

    public static void glClearBufferSubData(int target, int internalformat, long offset, long size, int format, int type, ByteBuffer data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferSubData");
        }
    }

    public static void glDispatchComputeIndirect(long indirect) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glDispatchComputeIndirect(indirect);
        } else if (GL_CAPABILITIES.GL_ARB_compute_shader) {
            ARBComputeShader.glDispatchComputeIndirect(indirect);
        } else {
            throw new UnsupportedOperationException("glDispatchComputeIndirect");
        }
    }

    public static void glClearBufferData(int target, int internalformat, int format, int type, float[] data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferData(target, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferData(target, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferData");
        }
    }

    public static void glClearBufferData(int target, int internalformat, int format, int type, IntBuffer data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferData(target, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferData(target, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferData");
        }
    }

    public static void glClearBufferData(int target, int internalformat, int format, int type, ShortBuffer data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferData(target, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferData(target, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferData");
        }
    }

    public static void glClearBufferData(int target, int internalformat, int format, int type, ByteBuffer data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferData(target, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferData(target, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferData");
        }
    }

    public static void glClearBufferData(int target, int internalformat, int format, int type, int[] data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferData(target, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferData(target, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferData");
        }
    }

    public static void glClearBufferData(int target, int internalformat, int format, int type, short[] data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferData(target, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferData(target, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferData");
        }
    }

    public static void glClearBufferData(int target, int internalformat, int format, int type, FloatBuffer data) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glClearBufferData(target, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_buffer_object) {
            ARBClearBufferObject.glClearBufferData(target, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearBufferData");
        }
    }

    public static void glCopyImageSubData(int srcName, int srcTarget, int srcLevel, int srcX, int srcY, int srcZ, int dstName, int dstTarget, int dstLevel, int dstX, int dstY, int dstZ, int srcWidth, int srcHeight, int srcDepth) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glCopyImageSubData(srcName, srcTarget, srcLevel, srcX, srcY, srcZ, dstName, dstTarget, dstLevel, dstX, dstY, dstZ, srcWidth, srcHeight, srcDepth);
        } else if (GL_CAPABILITIES.GL_ARB_copy_image) {
            ARBCopyImage.glCopyImageSubData(srcName, srcTarget, srcLevel, srcX, srcY, srcZ, dstName, dstTarget, dstLevel, dstX, dstY, dstZ, srcWidth, srcHeight, srcDepth);
        } else {
            throw new UnsupportedOperationException("glCopyImageSubData");
        }
    }

    public static void glDispatchCompute(int num_groups_x, int num_groups_y, int num_groups_z) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glDispatchCompute(num_groups_x, num_groups_y, num_groups_z);
        } else if (GL_CAPABILITIES.GL_ARB_compute_shader) {
            ARBComputeShader.glDispatchCompute(num_groups_x, num_groups_y, num_groups_z);
        } else {
            throw new UnsupportedOperationException("glDispatchCompute");
        }
    }

    public static int glGetFramebufferParameteri(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetFramebufferParameteri has no caps");
    }

    public static void glFramebufferParameteri(int target, int pname, int param) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glFramebufferParameteri(target, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_no_attachments) {
            ARBFramebufferNoAttachments.glFramebufferParameteri(target, pname, param);
        } else {
            throw new UnsupportedOperationException("glFramebufferParameteri");
        }
    }

    public static void glTexBufferRange(int target, int internalformat, int buffer, long offset, long size) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glTexBufferRange(target, internalformat, buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_texture_buffer_range) {
            ARBTextureBufferRange.glTexBufferRange(target, internalformat, buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glTexBufferRange");
        }
    }

    public static void glTextureView(int texture, int target, int origtexture, int internalformat, int minlevel, int numlevels, int minlayer, int numlayers) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glTextureView(texture, target, origtexture, internalformat, minlevel, numlevels, minlayer, numlayers);
        } else if (GL_CAPABILITIES.GL_ARB_texture_view) {
            ARBTextureView.glTextureView(texture, target, origtexture, internalformat, minlevel, numlevels, minlayer, numlayers);
        } else {
            throw new UnsupportedOperationException("glTextureView");
        }
    }

    public static void glBindVertexBuffer(int bindingindex, int buffer, long offset, int stride) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glBindVertexBuffer(bindingindex, buffer, offset, stride);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_binding) {
            ARBVertexAttribBinding.glBindVertexBuffer(bindingindex, buffer, offset, stride);
        } else {
            throw new UnsupportedOperationException("glBindVertexBuffer");
        }
    }

    public static void glDebugMessageControl(int source, int type, int severity, IntBuffer count, boolean ids) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glDebugMessageControl(source, type, severity, count, ids);
        } else if (GL_CAPABILITIES.GL_ARB_debug_output) {
            ARBDebugOutput.glDebugMessageControlARB(source, type, severity, count, ids);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glDebugMessageControl(source, type, severity, count, ids);
        } else {
            throw new UnsupportedOperationException("glDebugMessageControl");
        }
    }

    public static void glDebugMessageControl(int source, int type, int severity, int[] count, boolean ids) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glDebugMessageControl(source, type, severity, count, ids);
        } else if (GL_CAPABILITIES.GL_ARB_debug_output) {
            ARBDebugOutput.glDebugMessageControlARB(source, type, severity, count, ids);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glDebugMessageControl(source, type, severity, count, ids);
        } else {
            throw new UnsupportedOperationException("glDebugMessageControl");
        }
    }

    public static void glDebugMessageControl(int source, int type, int severity, int count, boolean ids) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glDebugMessageControl(source, type, severity, count, ids);
        } else if (GL_CAPABILITIES.GL_ARB_debug_output) {
            ARBDebugOutput.glDebugMessageControlARB(source, type, severity, count, ids);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glDebugMessageControl(source, type, severity, count, ids);
        } else {
            throw new UnsupportedOperationException("glDebugMessageControl");
        }
    }

    public static int glGetDebugMessageLog(int count, int[] bufSize, int[] sources, int[] types, int[] ids, int[] severities, ByteBuffer lengths) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetDebugMessageLog(count, bufSize, sources, types, ids, severities, lengths);
        } else if (GL_CAPABILITIES.GL_ARB_debug_output) {
            return ARBDebugOutput.glGetDebugMessageLogARB(count, bufSize, sources, types, ids, severities, lengths);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            return KHRDebug.glGetDebugMessageLog(count, bufSize, sources, types, ids, severities, lengths);
        } else {
            throw new UnsupportedOperationException("glGetDebugMessageLog");
        }
    }

    public static int glGetDebugMessageLog(int count, IntBuffer bufSize, IntBuffer sources, IntBuffer types, IntBuffer ids, IntBuffer severities, ByteBuffer lengths) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetDebugMessageLog(count, bufSize, sources, types, ids, severities, lengths);
        } else if (GL_CAPABILITIES.GL_ARB_debug_output) {
            return ARBDebugOutput.glGetDebugMessageLogARB(count, bufSize, sources, types, ids, severities, lengths);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            return KHRDebug.glGetDebugMessageLog(count, bufSize, sources, types, ids, severities, lengths);
        } else {
            throw new UnsupportedOperationException("glGetDebugMessageLog");
        }
    }

    public static void glDebugMessageCallback(GLDebugMessageCallbackI callback, long userParam) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glDebugMessageCallback(callback, userParam);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glDebugMessageCallback(callback, userParam);
        } else {
            throw new UnsupportedOperationException("glDebugMessageCallback");
        }
    }

    public static void glDebugMessageInsert(int source, int type, int id, int severity, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glDebugMessageInsert(source, type, id, severity, length);
        } else if (GL_CAPABILITIES.GL_ARB_debug_output) {
            ARBDebugOutput.glDebugMessageInsertARB(source, type, id, severity, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glDebugMessageInsert(source, type, id, severity, length);
        } else {
            throw new UnsupportedOperationException("glDebugMessageInsert");
        }
    }

    public static void glDebugMessageInsert(int source, int type, int id, int severity, CharSequence length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glDebugMessageInsert(source, type, id, severity, length);
        } else if (GL_CAPABILITIES.GL_ARB_debug_output) {
            ARBDebugOutput.glDebugMessageInsertARB(source, type, id, severity, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glDebugMessageInsert(source, type, id, severity, length);
        } else {
            throw new UnsupportedOperationException("glDebugMessageInsert");
        }
    }

    public static void glGetObjectPtrLabel(long ptr, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetObjectPtrLabel(ptr, bufSize, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glGetObjectPtrLabel(ptr, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetObjectPtrLabel");
        }
    }

    public static String glGetObjectPtrLabel(long ptr) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetObjectPtrLabel(ptr);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            return KHRDebug.glGetObjectPtrLabel(ptr);
        } else {
            throw new UnsupportedOperationException("glGetObjectPtrLabel");
        }
    }

    public static String glGetObjectPtrLabel(long ptr, int bufSize) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetObjectPtrLabel(ptr, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            return KHRDebug.glGetObjectPtrLabel(ptr, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetObjectPtrLabel");
        }
    }

    public static void glGetObjectPtrLabel(long ptr, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetObjectPtrLabel(ptr, bufSize, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glGetObjectPtrLabel(ptr, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetObjectPtrLabel");
        }
    }

    public static int glGetProgramResourceLocation(int program, int programInterface, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetProgramResourceLocation(program, programInterface, name);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            return ARBProgramInterfaceQuery.glGetProgramResourceLocation(program, programInterface, name);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceLocation");
        }
    }

    public static int glGetProgramResourceLocation(int program, int programInterface, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetProgramResourceLocation(program, programInterface, name);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            return ARBProgramInterfaceQuery.glGetProgramResourceLocation(program, programInterface, name);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceLocation");
        }
    }

    public static void glMultiDrawElementsIndirect(int mode, int type, ByteBuffer indirect, int drawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_multi_draw_indirect) {
            ARBMultiDrawIndirect.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElementsIndirect");
        }
    }

    public static void glMultiDrawElementsIndirect(int mode, int type, long indirect, int drawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_multi_draw_indirect) {
            ARBMultiDrawIndirect.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElementsIndirect");
        }
    }

    public static void glMultiDrawElementsIndirect(int mode, int type, IntBuffer indirect, int drawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_multi_draw_indirect) {
            ARBMultiDrawIndirect.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElementsIndirect");
        }
    }

    public static void glMultiDrawElementsIndirect(int mode, int type, int[] indirect, int drawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_multi_draw_indirect) {
            ARBMultiDrawIndirect.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElementsIndirect");
        }
    }

    public static int glGetProgramResourceLocationIndex(int program, int programInterface, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetProgramResourceLocationIndex(program, programInterface, name);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            return ARBProgramInterfaceQuery.glGetProgramResourceLocationIndex(program, programInterface, name);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceLocationIndex");
        }
    }

    public static int glGetProgramResourceLocationIndex(int program, int programInterface, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetProgramResourceLocationIndex(program, programInterface, name);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            return ARBProgramInterfaceQuery.glGetProgramResourceLocationIndex(program, programInterface, name);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceLocationIndex");
        }
    }

    public static void glShaderStorageBlockBinding(int program, int storageBlockIndex, int storageBlockBinding) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glShaderStorageBlockBinding(program, storageBlockIndex, storageBlockBinding);
        } else if (GL_CAPABILITIES.GL_ARB_shader_storage_buffer_object) {
            ARBShaderStorageBufferObject.glShaderStorageBlockBinding(program, storageBlockIndex, storageBlockBinding);
        } else {
            throw new UnsupportedOperationException("glShaderStorageBlockBinding");
        }
    }

    public static void glGetFramebufferParameteriv(int target, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetFramebufferParameteriv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_no_attachments) {
            ARBFramebufferNoAttachments.glGetFramebufferParameteriv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetFramebufferParameteriv");
        }
    }

    public static void glGetFramebufferParameteriv(int target, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetFramebufferParameteriv(target, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_framebuffer_no_attachments) {
            ARBFramebufferNoAttachments.glGetFramebufferParameteriv(target, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetFramebufferParameteriv");
        }
    }

    public static void glGetObjectLabel(int identifier, int name, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetObjectLabel(identifier, name, bufSize, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glGetObjectLabel(identifier, name, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetObjectLabel");
        }
    }

    public static String glGetObjectLabel(int identifier, int name, int bufSize) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetObjectLabel(identifier, name, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            return KHRDebug.glGetObjectLabel(identifier, name, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetObjectLabel");
        }
    }

    public static void glGetObjectLabel(int identifier, int name, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetObjectLabel(identifier, name, bufSize, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glGetObjectLabel(identifier, name, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetObjectLabel");
        }
    }

    public static String glGetObjectLabel(int identifier, int name) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetObjectLabel(identifier, name);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            return KHRDebug.glGetObjectLabel(identifier, name);
        } else {
            throw new UnsupportedOperationException("glGetObjectLabel");
        }
    }

    public static void glPushDebugGroup(int source, int id, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glPushDebugGroup(source, id, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glPushDebugGroup(source, id, length);
        } else {
            throw new UnsupportedOperationException("glPushDebugGroup");
        }
    }

    public static void glPushDebugGroup(int source, int id, CharSequence length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glPushDebugGroup(source, id, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glPushDebugGroup(source, id, length);
        } else {
            throw new UnsupportedOperationException("glPushDebugGroup");
        }
    }

    public static void glObjectPtrLabel(long ptr, CharSequence length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glObjectPtrLabel(ptr, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glObjectPtrLabel(ptr, length);
        } else {
            throw new UnsupportedOperationException("glObjectPtrLabel");
        }
    }

    public static void glObjectPtrLabel(long ptr, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glObjectPtrLabel(ptr, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glObjectPtrLabel(ptr, length);
        } else {
            throw new UnsupportedOperationException("glObjectPtrLabel");
        }
    }

    public static void glObjectLabel(int identifier, int name, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glObjectLabel(identifier, name, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glObjectLabel(identifier, name, length);
        } else {
            throw new UnsupportedOperationException("glObjectLabel");
        }
    }

    public static void glObjectLabel(int identifier, int name, CharSequence length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glObjectLabel(identifier, name, length);
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glObjectLabel(identifier, name, length);
        } else {
            throw new UnsupportedOperationException("glObjectLabel");
        }
    }

    public static void glPopDebugGroup() {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glPopDebugGroup();
        } else if (GL_CAPABILITIES.GL_KHR_debug) {
            KHRDebug.glPopDebugGroup();
        } else {
            throw new UnsupportedOperationException("glPopDebugGroup");
        }
    }

    public static void glGetInternalformati64v(int target, int internalformat, int pname, long[] count) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetInternalformati64v(target, internalformat, pname, count);
        } else if (GL_CAPABILITIES.GL_ARB_internalformat_query2) {
            ARBInternalformatQuery2.glGetInternalformati64v(target, internalformat, pname, count);
        } else {
            throw new UnsupportedOperationException("glGetInternalformati64v");
        }
    }

    public static void glGetInternalformati64v(int target, int internalformat, int pname, LongBuffer count) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetInternalformati64v(target, internalformat, pname, count);
        } else if (GL_CAPABILITIES.GL_ARB_internalformat_query2) {
            ARBInternalformatQuery2.glGetInternalformati64v(target, internalformat, pname, count);
        } else {
            throw new UnsupportedOperationException("glGetInternalformati64v");
        }
    }

    public static long glGetInternalformati64(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetInternalformati64 has no caps");
    }

    public static void glInvalidateFramebuffer(int target, IntBuffer numAttachments) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glInvalidateFramebuffer(target, numAttachments);
        } else if (GL_CAPABILITIES.GL_ARB_invalidate_subdata) {
            ARBInvalidateSubdata.glInvalidateFramebuffer(target, numAttachments);
        } else {
            throw new UnsupportedOperationException("glInvalidateFramebuffer");
        }
    }

    public static void glInvalidateFramebuffer(int target, int numAttachments) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glInvalidateFramebuffer(target, numAttachments);
        } else if (GL_CAPABILITIES.GL_ARB_invalidate_subdata) {
            ARBInvalidateSubdata.glInvalidateFramebuffer(target, numAttachments);
        } else {
            throw new UnsupportedOperationException("glInvalidateFramebuffer");
        }
    }

    public static void glInvalidateFramebuffer(int target, int[] numAttachments) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glInvalidateFramebuffer(target, numAttachments);
        } else if (GL_CAPABILITIES.GL_ARB_invalidate_subdata) {
            ARBInvalidateSubdata.glInvalidateFramebuffer(target, numAttachments);
        } else {
            throw new UnsupportedOperationException("glInvalidateFramebuffer");
        }
    }

    public static void glInvalidateBufferData(int buffer) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glInvalidateBufferData(buffer);
        } else if (GL_CAPABILITIES.GL_ARB_invalidate_subdata) {
            ARBInvalidateSubdata.glInvalidateBufferData(buffer);
        } else {
            throw new UnsupportedOperationException("glInvalidateBufferData");
        }
    }

    public static void glInvalidateTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glInvalidateTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth);
        } else if (GL_CAPABILITIES.GL_ARB_invalidate_subdata) {
            ARBInvalidateSubdata.glInvalidateTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth);
        } else {
            throw new UnsupportedOperationException("glInvalidateTexSubImage");
        }
    }

    public static void glInvalidateTexImage(int texture, int level) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glInvalidateTexImage(texture, level);
        } else if (GL_CAPABILITIES.GL_ARB_invalidate_subdata) {
            ARBInvalidateSubdata.glInvalidateTexImage(texture, level);
        } else {
            throw new UnsupportedOperationException("glInvalidateTexImage");
        }
    }

    public static void glInvalidateBufferSubData(int buffer, long offset, long length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glInvalidateBufferSubData(buffer, offset, length);
        } else if (GL_CAPABILITIES.GL_ARB_invalidate_subdata) {
            ARBInvalidateSubdata.glInvalidateBufferSubData(buffer, offset, length);
        } else {
            throw new UnsupportedOperationException("glInvalidateBufferSubData");
        }
    }

    public static void glInvalidateSubFramebuffer(int target, int[] numAttachments, int attachments, int x, int y, int width) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glInvalidateSubFramebuffer(target, numAttachments, attachments, x, y, width);
        } else if (GL_CAPABILITIES.GL_ARB_invalidate_subdata) {
            ARBInvalidateSubdata.glInvalidateSubFramebuffer(target, numAttachments, attachments, x, y, width);
        } else {
            throw new UnsupportedOperationException("glInvalidateSubFramebuffer");
        }
    }

    public static void glInvalidateSubFramebuffer(int target, IntBuffer numAttachments, int attachments, int x, int y, int width) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glInvalidateSubFramebuffer(target, numAttachments, attachments, x, y, width);
        } else if (GL_CAPABILITIES.GL_ARB_invalidate_subdata) {
            ARBInvalidateSubdata.glInvalidateSubFramebuffer(target, numAttachments, attachments, x, y, width);
        } else {
            throw new UnsupportedOperationException("glInvalidateSubFramebuffer");
        }
    }

    public static void glInvalidateSubFramebuffer(int target, int numAttachments, int attachments, int x, int y, int width) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glInvalidateSubFramebuffer(target, numAttachments, attachments, x, y, width);
        } else if (GL_CAPABILITIES.GL_ARB_invalidate_subdata) {
            ARBInvalidateSubdata.glInvalidateSubFramebuffer(target, numAttachments, attachments, x, y, width);
        } else {
            throw new UnsupportedOperationException("glInvalidateSubFramebuffer");
        }
    }

    public static void glMultiDrawArraysIndirect(int mode, IntBuffer indirect, int drawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_multi_draw_indirect) {
            ARBMultiDrawIndirect.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawArraysIndirect");
        }
    }

    public static void glMultiDrawArraysIndirect(int mode, int[] indirect, int drawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_multi_draw_indirect) {
            ARBMultiDrawIndirect.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawArraysIndirect");
        }
    }

    public static void glMultiDrawArraysIndirect(int mode, long indirect, int drawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_multi_draw_indirect) {
            ARBMultiDrawIndirect.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawArraysIndirect");
        }
    }

    public static void glMultiDrawArraysIndirect(int mode, ByteBuffer indirect, int drawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_multi_draw_indirect) {
            ARBMultiDrawIndirect.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawArraysIndirect");
        }
    }

    public static int glGetProgramInterfacei(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetProgramInterfacei has no caps");
    }

    public static String glGetProgramResourceName(int program, int programInterface, int index, int bufSize) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetProgramResourceName(program, programInterface, index, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            return ARBProgramInterfaceQuery.glGetProgramResourceName(program, programInterface, index, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceName");
        }
    }

    public static void glGetProgramResourceName(int program, int programInterface, int index, IntBuffer bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetProgramResourceName(program, programInterface, index, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            ARBProgramInterfaceQuery.glGetProgramResourceName(program, programInterface, index, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceName");
        }
    }

    public static String glGetProgramResourceName(int program, int programInterface, int index) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetProgramResourceName(program, programInterface, index);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            return ARBProgramInterfaceQuery.glGetProgramResourceName(program, programInterface, index);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceName");
        }
    }

    public static void glGetProgramResourceName(int program, int programInterface, int index, int[] bufSize, ByteBuffer length) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetProgramResourceName(program, programInterface, index, bufSize, length);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            ARBProgramInterfaceQuery.glGetProgramResourceName(program, programInterface, index, bufSize, length);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceName");
        }
    }

    public static void glGetProgramResourceiv(int program, int programInterface, int index, int[] propCount, int[] props, int[] count) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetProgramResourceiv(program, programInterface, index, propCount, props, count);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            ARBProgramInterfaceQuery.glGetProgramResourceiv(program, programInterface, index, propCount, props, count);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceiv");
        }
    }

    public static void glGetProgramResourceiv(int program, int programInterface, int index, IntBuffer propCount, IntBuffer props, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetProgramResourceiv(program, programInterface, index, propCount, props, count);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            ARBProgramInterfaceQuery.glGetProgramResourceiv(program, programInterface, index, propCount, props, count);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceiv");
        }
    }

    public static void glGetProgramInterfaceiv(int program, int programInterface, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetProgramInterfaceiv(program, programInterface, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            ARBProgramInterfaceQuery.glGetProgramInterfaceiv(program, programInterface, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetProgramInterfaceiv");
        }
    }

    public static void glGetProgramInterfaceiv(int program, int programInterface, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glGetProgramInterfaceiv(program, programInterface, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            ARBProgramInterfaceQuery.glGetProgramInterfaceiv(program, programInterface, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetProgramInterfaceiv");
        }
    }

    public static int glGetProgramResourceIndex(int program, int programInterface, ByteBuffer name) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetProgramResourceIndex(program, programInterface, name);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            return ARBProgramInterfaceQuery.glGetProgramResourceIndex(program, programInterface, name);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceIndex");
        }
    }

    public static int glGetProgramResourceIndex(int program, int programInterface, CharSequence name) {
        if (GL_CAPABILITIES.OpenGL43) {
            return GL43.glGetProgramResourceIndex(program, programInterface, name);
        } else if (GL_CAPABILITIES.GL_ARB_program_interface_query) {
            return ARBProgramInterfaceQuery.glGetProgramResourceIndex(program, programInterface, name);
        } else {
            throw new UnsupportedOperationException("glGetProgramResourceIndex");
        }
    }

    public static void glTexStorage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glTexStorage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
        } else if (GL_CAPABILITIES.GL_ARB_texture_storage_multisample) {
            ARBTextureStorageMultisample.glTexStorage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
        } else {
            throw new UnsupportedOperationException("glTexStorage2DMultisample");
        }
    }

    public static void glTexStorage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glTexStorage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
        } else if (GL_CAPABILITIES.GL_ARB_texture_storage_multisample) {
            ARBTextureStorageMultisample.glTexStorage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
        } else {
            throw new UnsupportedOperationException("glTexStorage3DMultisample");
        }
    }

    public static void glVertexAttribLFormat(int attribindex, int size, int type, int relativeoffset) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glVertexAttribLFormat(attribindex, size, type, relativeoffset);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_binding) {
            ARBVertexAttribBinding.glVertexAttribLFormat(attribindex, size, type, relativeoffset);
        } else {
            throw new UnsupportedOperationException("glVertexAttribLFormat");
        }
    }

    public static void glVertexAttribBinding(int attribindex, int bindingindex) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glVertexAttribBinding(attribindex, bindingindex);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_binding) {
            ARBVertexAttribBinding.glVertexAttribBinding(attribindex, bindingindex);
        } else {
            throw new UnsupportedOperationException("glVertexAttribBinding");
        }
    }

    public static void glVertexBindingDivisor(int bindingindex, int divisor) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glVertexBindingDivisor(bindingindex, divisor);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_binding) {
            ARBVertexAttribBinding.glVertexBindingDivisor(bindingindex, divisor);
        } else {
            throw new UnsupportedOperationException("glVertexBindingDivisor");
        }
    }

    public static void glVertexAttribFormat(int attribindex, int size, int type, boolean normalized, int relativeoffset) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glVertexAttribFormat(attribindex, size, type, normalized, relativeoffset);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_binding) {
            ARBVertexAttribBinding.glVertexAttribFormat(attribindex, size, type, normalized, relativeoffset);
        } else {
            throw new UnsupportedOperationException("glVertexAttribFormat");
        }
    }

    public static void glVertexAttribIFormat(int attribindex, int size, int type, int relativeoffset) {
        if (GL_CAPABILITIES.OpenGL43) {
            GL43.glVertexAttribIFormat(attribindex, size, type, relativeoffset);
        } else if (GL_CAPABILITIES.GL_ARB_vertex_attrib_binding) {
            ARBVertexAttribBinding.glVertexAttribIFormat(attribindex, size, type, relativeoffset);
        } else {
            throw new UnsupportedOperationException("glVertexAttribIFormat");
        }
    }

    // --- GL44 ---
    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, FloatBuffer data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexSubImage");
        }
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, IntBuffer data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexSubImage");
        }
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, double[] data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexSubImage");
        }
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, DoubleBuffer data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexSubImage");
        }
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ShortBuffer data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexSubImage");
        }
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, short[] data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexSubImage");
        }
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, int[] data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexSubImage");
        }
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, float[] data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexSubImage");
        }
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexSubImage");
        }
    }

    public static void glBufferStorage(int target, int[] size, int data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBufferStorage(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_buffer_storage) {
            ARBBufferStorage.glBufferStorage(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferStorage");
        }
    }

    public static void glBufferStorage(int target, short[] size, int data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBufferStorage(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_buffer_storage) {
            ARBBufferStorage.glBufferStorage(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferStorage");
        }
    }

    public static void glBufferStorage(int target, double[] size, int data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBufferStorage(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_buffer_storage) {
            ARBBufferStorage.glBufferStorage(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferStorage");
        }
    }

    public static void glBufferStorage(int target, float[] size, int data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBufferStorage(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_buffer_storage) {
            ARBBufferStorage.glBufferStorage(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferStorage");
        }
    }

    public static void glBufferStorage(int target, DoubleBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBufferStorage(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_buffer_storage) {
            ARBBufferStorage.glBufferStorage(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferStorage");
        }
    }

    public static void glBufferStorage(int target, FloatBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBufferStorage(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_buffer_storage) {
            ARBBufferStorage.glBufferStorage(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferStorage");
        }
    }

    public static void glBufferStorage(int target, ShortBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBufferStorage(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_buffer_storage) {
            ARBBufferStorage.glBufferStorage(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferStorage");
        }
    }

    public static void glBufferStorage(int target, IntBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBufferStorage(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_buffer_storage) {
            ARBBufferStorage.glBufferStorage(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferStorage");
        }
    }

    public static void glBufferStorage(int target, long size, int data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBufferStorage(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_buffer_storage) {
            ARBBufferStorage.glBufferStorage(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferStorage");
        }
    }

    public static void glBufferStorage(int target, ByteBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBufferStorage(target, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_buffer_storage) {
            ARBBufferStorage.glBufferStorage(target, size, data);
        } else {
            throw new UnsupportedOperationException("glBufferStorage");
        }
    }

    public static void glClearTexImage(int texture, int level, int format, int type, ByteBuffer data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexImage(texture, level, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexImage(texture, level, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexImage");
        }
    }

    public static void glClearTexImage(int texture, int level, int format, int type, FloatBuffer data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexImage(texture, level, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexImage(texture, level, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexImage");
        }
    }

    public static void glClearTexImage(int texture, int level, int format, int type, float[] data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexImage(texture, level, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexImage(texture, level, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexImage");
        }
    }

    public static void glClearTexImage(int texture, int level, int format, int type, double[] data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexImage(texture, level, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexImage(texture, level, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexImage");
        }
    }

    public static void glClearTexImage(int texture, int level, int format, int type, DoubleBuffer data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexImage(texture, level, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexImage(texture, level, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexImage");
        }
    }

    public static void glClearTexImage(int texture, int level, int format, int type, IntBuffer data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexImage(texture, level, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexImage(texture, level, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexImage");
        }
    }

    public static void glClearTexImage(int texture, int level, int format, int type, ShortBuffer data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexImage(texture, level, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexImage(texture, level, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexImage");
        }
    }

    public static void glClearTexImage(int texture, int level, int format, int type, short[] data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexImage(texture, level, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexImage(texture, level, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexImage");
        }
    }

    public static void glClearTexImage(int texture, int level, int format, int type, int[] data) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glClearTexImage(texture, level, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_clear_texture) {
            ARBClearTexture.glClearTexImage(texture, level, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearTexImage");
        }
    }

    public static void glBindTextures(int first, int[] count) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindTextures(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindTextures(first, count);
        } else {
            throw new UnsupportedOperationException("glBindTextures");
        }
    }

    public static void glBindTextures(int first, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindTextures(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindTextures(first, count);
        } else {
            throw new UnsupportedOperationException("glBindTextures");
        }
    }

    public static void glBindBuffersRange(int target, int first, IntBuffer count, PointerBuffer buffers, PointerBuffer offsets) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindBuffersRange(target, first, count, buffers, offsets);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindBuffersRange(target, first, count, buffers, offsets);
        } else {
            throw new UnsupportedOperationException("glBindBuffersRange");
        }
    }

    public static void glBindBuffersRange(int target, int first, int[] count, PointerBuffer buffers, PointerBuffer offsets) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindBuffersRange(target, first, count, buffers, offsets);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindBuffersRange(target, first, count, buffers, offsets);
        } else {
            throw new UnsupportedOperationException("glBindBuffersRange");
        }
    }

    public static void glBindSamplers(int first, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindSamplers(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindSamplers(first, count);
        } else {
            throw new UnsupportedOperationException("glBindSamplers");
        }
    }

    public static void glBindSamplers(int first, int[] count) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindSamplers(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindSamplers(first, count);
        } else {
            throw new UnsupportedOperationException("glBindSamplers");
        }
    }

    public static void glBindBuffersBase(int target, int first, int[] count) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindBuffersBase(target, first, count);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindBuffersBase(target, first, count);
        } else {
            throw new UnsupportedOperationException("glBindBuffersBase");
        }
    }

    public static void glBindBuffersBase(int target, int first, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindBuffersBase(target, first, count);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindBuffersBase(target, first, count);
        } else {
            throw new UnsupportedOperationException("glBindBuffersBase");
        }
    }

    public static void glBindImageTextures(int first, IntBuffer count) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindImageTextures(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindImageTextures(first, count);
        } else {
            throw new UnsupportedOperationException("glBindImageTextures");
        }
    }

    public static void glBindImageTextures(int first, int[] count) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindImageTextures(first, count);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindImageTextures(first, count);
        } else {
            throw new UnsupportedOperationException("glBindImageTextures");
        }
    }

    public static void glBindVertexBuffers(int first, int[] count, PointerBuffer buffers, int[] offsets) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindVertexBuffers(first, count, buffers, offsets);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindVertexBuffers(first, count, buffers, offsets);
        } else {
            throw new UnsupportedOperationException("glBindVertexBuffers");
        }
    }

    public static void glBindVertexBuffers(int first, IntBuffer count, PointerBuffer buffers, IntBuffer offsets) {
        if (GL_CAPABILITIES.OpenGL44) {
            GL44.glBindVertexBuffers(first, count, buffers, offsets);
        } else if (GL_CAPABILITIES.GL_ARB_multi_bind) {
            ARBMultiBind.glBindVertexBuffers(first, count, buffers, offsets);
        } else {
            throw new UnsupportedOperationException("glBindVertexBuffers");
        }
    }

    // --- GL45 ---
    public static double glGetnMapd(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetnMapd has no caps");
    }

    public static int glGetnMapi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetnMapi has no caps");
    }

    public static float glGetnMapf(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetnMapf has no caps");
    }

    public static void glClipControl(int origin, int depth) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClipControl(origin, depth);
        } else if (GL_CAPABILITIES.GL_ARB_clip_control) {
            ARBClipControl.glClipControl(origin, depth);
        } else {
            throw new UnsupportedOperationException("glClipControl");
        }
    }

    public static boolean glUnmapNamedBuffer(int buffer) {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glUnmapNamedBuffer(buffer);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glUnmapNamedBuffer(buffer);
        } else {
            throw new UnsupportedOperationException("glUnmapNamedBuffer");
        }
    }

    public static void glBindTextureUnit(int unit, int texture) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glBindTextureUnit(unit, texture);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glBindTextureUnit(unit, texture);
        } else {
            throw new UnsupportedOperationException("glBindTextureUnit");
        }
    }

    public static int glCreateTextures(int target) {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glCreateTextures(target);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glCreateTextures(target);
        } else {
            throw new UnsupportedOperationException("glCreateTextures");
        }
    }

    public static void glCreateTextures(int target, IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateTextures(target, n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateTextures(target, n);
        } else {
            throw new UnsupportedOperationException("glCreateTextures");
        }
    }

    public static void glCreateTextures(int target, int[] n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateTextures(target, n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateTextures(target, n);
        } else {
            throw new UnsupportedOperationException("glCreateTextures");
        }
    }

    public static void glTextureBuffer(int texture, int internalformat, int buffer) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureBuffer(texture, internalformat, buffer);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureBuffer(texture, internalformat, buffer);
        } else {
            throw new UnsupportedOperationException("glTextureBuffer");
        }
    }

    public static void glTextureStorage2D(int texture, int levels, int internalformat, int width, int height) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureStorage2D(texture, levels, internalformat, width, height);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureStorage2D(texture, levels, internalformat, width, height);
        } else {
            throw new UnsupportedOperationException("glTextureStorage2D");
        }
    }

    public static void glTextureStorage1D(int texture, int levels, int internalformat, int width) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureStorage1D(texture, levels, internalformat, width);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureStorage1D(texture, levels, internalformat, width);
        } else {
            throw new UnsupportedOperationException("glTextureStorage1D");
        }
    }

    public static void glGetTextureImage(int texture, int level, int format, int type, int[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureImage(texture, level, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureImage(texture, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureImage");
        }
    }

    public static void glGetTextureImage(int texture, int level, int format, int type, float[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureImage(texture, level, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureImage(texture, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureImage");
        }
    }

    public static void glGetTextureImage(int texture, int level, int format, int type, double[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureImage(texture, level, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureImage(texture, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureImage");
        }
    }

    public static void glGetTextureImage(int texture, int level, int format, int type, IntBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureImage(texture, level, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureImage(texture, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureImage");
        }
    }

    public static void glGetTextureImage(int texture, int level, int format, int type, int bufSize, long pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureImage(texture, level, format, type, bufSize, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureImage(texture, level, format, type, bufSize, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTextureImage");
        }
    }

    public static void glGetTextureImage(int texture, int level, int format, int type, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureImage(texture, level, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureImage(texture, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureImage");
        }
    }

    public static void glGetTextureImage(int texture, int level, int format, int type, ShortBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureImage(texture, level, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureImage(texture, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureImage");
        }
    }

    public static void glGetTextureImage(int texture, int level, int format, int type, short[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureImage(texture, level, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureImage(texture, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureImage");
        }
    }

    public static void glGetTextureImage(int texture, int level, int format, int type, FloatBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureImage(texture, level, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureImage(texture, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureImage");
        }
    }

    public static void glGetTextureImage(int texture, int level, int format, int type, DoubleBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureImage(texture, level, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureImage(texture, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureImage");
        }
    }

    public static int glCreateBuffers() {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glCreateBuffers();
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glCreateBuffers();
        } else {
            throw new UnsupportedOperationException("glCreateBuffers");
        }
    }

    public static void glCreateBuffers(int[] n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateBuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateBuffers(n);
        } else {
            throw new UnsupportedOperationException("glCreateBuffers");
        }
    }

    public static void glCreateBuffers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateBuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateBuffers(n);
        } else {
            throw new UnsupportedOperationException("glCreateBuffers");
        }
    }

    public static ByteBuffer glMapNamedBuffer(int buffer, int access, long arg2, ByteBuffer arg3) {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glMapNamedBuffer(buffer, access, arg2, arg3);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glMapNamedBuffer(buffer, access, arg2, arg3);
        } else {
            throw new UnsupportedOperationException("glMapNamedBuffer");
        }
    }

    public static ByteBuffer glMapNamedBuffer(int buffer, int access, ByteBuffer arg2) {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glMapNamedBuffer(buffer, access, arg2);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glMapNamedBuffer(buffer, access, arg2);
        } else {
            throw new UnsupportedOperationException("glMapNamedBuffer");
        }
    }

    public static ByteBuffer glMapNamedBuffer(int buffer, int access) {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glMapNamedBuffer(buffer, access);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glMapNamedBuffer(buffer, access);
        } else {
            throw new UnsupportedOperationException("glMapNamedBuffer");
        }
    }

    public static void glNamedBufferData(int buffer, short[] size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, int[] size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, long[] size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, ShortBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, IntBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, LongBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, FloatBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, float[] size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, double[] size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, long size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, ByteBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glNamedBufferData(int buffer, DoubleBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferData(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferData(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferData");
        }
    }

    public static void glTextureStorage3D(int texture, int levels, int internalformat, int width, int height, int depth) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureStorage3D(texture, levels, internalformat, width, height, depth);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureStorage3D(texture, levels, internalformat, width, height, depth);
        } else {
            throw new UnsupportedOperationException("glTextureStorage3D");
        }
    }

    public static void glGetVertexArrayiv(int vaobj, int pname, IntBuffer param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetVertexArrayiv(vaobj, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetVertexArrayiv(vaobj, pname, param);
        } else {
            throw new UnsupportedOperationException("glGetVertexArrayiv");
        }
    }

    public static void glGetVertexArrayiv(int vaobj, int pname, int[] param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetVertexArrayiv(vaobj, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetVertexArrayiv(vaobj, pname, param);
        } else {
            throw new UnsupportedOperationException("glGetVertexArrayiv");
        }
    }

    public static int glGetVertexArrayi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetVertexArrayi has no caps");
    }

    public static int glCreateSamplers() {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glCreateSamplers();
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glCreateSamplers();
        } else {
            throw new UnsupportedOperationException("glCreateSamplers");
        }
    }

    public static void glCreateSamplers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateSamplers(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateSamplers(n);
        } else {
            throw new UnsupportedOperationException("glCreateSamplers");
        }
    }

    public static void glCreateSamplers(int[] n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateSamplers(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateSamplers(n);
        } else {
            throw new UnsupportedOperationException("glCreateSamplers");
        }
    }

    public static int glCreateQueries(int target) {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glCreateQueries(target);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glCreateQueries(target);
        } else {
            throw new UnsupportedOperationException("glCreateQueries");
        }
    }

    public static void glCreateQueries(int target, IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateQueries(target, n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateQueries(target, n);
        } else {
            throw new UnsupportedOperationException("glCreateQueries");
        }
    }

    public static void glCreateQueries(int target, int[] n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateQueries(target, n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateQueries(target, n);
        } else {
            throw new UnsupportedOperationException("glCreateQueries");
        }
    }

    public static void glInvalidateNamedFramebufferSubData(int framebuffer, int[] numAttachments, int attachments, int x, int y, int width) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glInvalidateNamedFramebufferSubData(framebuffer, numAttachments, attachments, x, y, width);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glInvalidateNamedFramebufferSubData(framebuffer, numAttachments, attachments, x, y, width);
        } else {
            throw new UnsupportedOperationException("glInvalidateNamedFramebufferSubData");
        }
    }

    public static void glInvalidateNamedFramebufferSubData(int framebuffer, IntBuffer numAttachments, int attachments, int x, int y, int width) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glInvalidateNamedFramebufferSubData(framebuffer, numAttachments, attachments, x, y, width);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glInvalidateNamedFramebufferSubData(framebuffer, numAttachments, attachments, x, y, width);
        } else {
            throw new UnsupportedOperationException("glInvalidateNamedFramebufferSubData");
        }
    }

    public static void glInvalidateNamedFramebufferSubData(int framebuffer, int numAttachments, int attachments, int x, int y, int width) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glInvalidateNamedFramebufferSubData(framebuffer, numAttachments, attachments, x, y, width);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glInvalidateNamedFramebufferSubData(framebuffer, numAttachments, attachments, x, y, width);
        } else {
            throw new UnsupportedOperationException("glInvalidateNamedFramebufferSubData");
        }
    }

    public static void glGetNamedFramebufferAttachmentParameteriv(int framebuffer, int attachment, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedFramebufferAttachmentParameteriv(framebuffer, attachment, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedFramebufferAttachmentParameteriv(framebuffer, attachment, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetNamedFramebufferAttachmentParameteriv");
        }
    }

    public static void glGetNamedFramebufferAttachmentParameteriv(int framebuffer, int attachment, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedFramebufferAttachmentParameteriv(framebuffer, attachment, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedFramebufferAttachmentParameteriv(framebuffer, attachment, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetNamedFramebufferAttachmentParameteriv");
        }
    }

    public static int glGetNamedFramebufferAttachmentParameteri(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetNamedFramebufferAttachmentParameteri has no caps");
    }

    public static void glNamedRenderbufferStorageMultisample(int renderbuffer, int samples, int internalformat, int width, int height) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedRenderbufferStorageMultisample(renderbuffer, samples, internalformat, width, height);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedRenderbufferStorageMultisample(renderbuffer, samples, internalformat, width, height);
        } else {
            throw new UnsupportedOperationException("glNamedRenderbufferStorageMultisample");
        }
    }

    public static void glGetTransformFeedbacki64_v(int xfb, int pname, int index, long[] param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTransformFeedbacki64_v(xfb, pname, index, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTransformFeedbacki64_v(xfb, pname, index, param);
        } else {
            throw new UnsupportedOperationException("glGetTransformFeedbacki64_v");
        }
    }

    public static void glGetTransformFeedbacki64_v(int xfb, int pname, int index, LongBuffer param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTransformFeedbacki64_v(xfb, pname, index, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTransformFeedbacki64_v(xfb, pname, index, param);
        } else {
            throw new UnsupportedOperationException("glGetTransformFeedbacki64_v");
        }
    }

    public static long glGetNamedBufferParameteri64(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetNamedBufferParameteri64 has no caps");
    }

    public static void glNamedFramebufferRenderbuffer(int framebuffer, int attachment, int renderbuffertarget, int renderbuffer) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedFramebufferRenderbuffer(framebuffer, attachment, renderbuffertarget, renderbuffer);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedFramebufferRenderbuffer(framebuffer, attachment, renderbuffertarget, renderbuffer);
        } else {
            throw new UnsupportedOperationException("glNamedFramebufferRenderbuffer");
        }
    }

    public static int glCheckNamedFramebufferStatus(int framebuffer, int target) {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glCheckNamedFramebufferStatus(framebuffer, target);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glCheckNamedFramebufferStatus(framebuffer, target);
        } else {
            throw new UnsupportedOperationException("glCheckNamedFramebufferStatus");
        }
    }

    public static void glTransformFeedbackBufferBase(int xfb, int index, int buffer) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTransformFeedbackBufferBase(xfb, index, buffer);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTransformFeedbackBufferBase(xfb, index, buffer);
        } else {
            throw new UnsupportedOperationException("glTransformFeedbackBufferBase");
        }
    }

    public static void glCompressedTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, long data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, imageSize, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, imageSize, data);
        } else {
            throw new UnsupportedOperationException("glCompressedTextureSubImage2D");
        }
    }

    public static void glCompressedTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, ByteBuffer imageSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, imageSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, imageSize);
        } else {
            throw new UnsupportedOperationException("glCompressedTextureSubImage2D");
        }
    }

    public static void glTextureStorage2DMultisample(int texture, int samples, int internalformat, int width, int height, boolean fixedsamplelocations) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureStorage2DMultisample(texture, samples, internalformat, width, height, fixedsamplelocations);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureStorage2DMultisample(texture, samples, internalformat, width, height, fixedsamplelocations);
        } else {
            throw new UnsupportedOperationException("glTextureStorage2DMultisample");
        }
    }

    public static void glCompressedTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, ByteBuffer imageSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize);
        } else {
            throw new UnsupportedOperationException("glCompressedTextureSubImage3D");
        }
    }

    public static void glCompressedTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, long data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data);
        } else {
            throw new UnsupportedOperationException("glCompressedTextureSubImage3D");
        }
    }

    public static void glTransformFeedbackBufferRange(int xfb, int index, int buffer, long offset, long size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTransformFeedbackBufferRange(xfb, index, buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTransformFeedbackBufferRange(xfb, index, buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glTransformFeedbackBufferRange");
        }
    }

    public static void glGetCompressedTextureImage(int texture, int level, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureImage(texture, level, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetCompressedTextureImage(texture, level, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureImage");
        }
    }

    public static void glGetCompressedTextureImage(int texture, int level, int bufSize, long pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureImage(texture, level, bufSize, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetCompressedTextureImage(texture, level, bufSize, pixels);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureImage");
        }
    }

    public static void glGetTextureLevelParameterfv(int texture, int level, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureLevelParameterfv(texture, level, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureLevelParameterfv(texture, level, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureLevelParameterfv");
        }
    }

    public static void glGetTextureLevelParameterfv(int texture, int level, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureLevelParameterfv(texture, level, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureLevelParameterfv(texture, level, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureLevelParameterfv");
        }
    }

    public static float glGetTextureLevelParameterf(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetTextureLevelParameterf has no caps");
    }

    public static void glGetTextureLevelParameteriv(int texture, int level, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureLevelParameteriv(texture, level, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureLevelParameteriv(texture, level, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureLevelParameteriv");
        }
    }

    public static void glGetTextureLevelParameteriv(int texture, int level, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureLevelParameteriv(texture, level, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureLevelParameteriv(texture, level, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureLevelParameteriv");
        }
    }

    public static void glNamedFramebufferDrawBuffer(int framebuffer, int buf) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedFramebufferDrawBuffer(framebuffer, buf);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedFramebufferDrawBuffer(framebuffer, buf);
        } else {
            throw new UnsupportedOperationException("glNamedFramebufferDrawBuffer");
        }
    }

    public static void glGetNamedRenderbufferParameteriv(int renderbuffer, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedRenderbufferParameteriv(renderbuffer, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedRenderbufferParameteriv(renderbuffer, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetNamedRenderbufferParameteriv");
        }
    }

    public static void glGetNamedRenderbufferParameteriv(int renderbuffer, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedRenderbufferParameteriv(renderbuffer, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedRenderbufferParameteriv(renderbuffer, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetNamedRenderbufferParameteriv");
        }
    }

    public static int glGetNamedRenderbufferParameteri(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetNamedRenderbufferParameteri has no caps");
    }

    public static void glTextureStorage3DMultisample(int texture, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureStorage3DMultisample(texture, samples, internalformat, width, height, depth, fixedsamplelocations);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureStorage3DMultisample(texture, samples, internalformat, width, height, depth, fixedsamplelocations);
        } else {
            throw new UnsupportedOperationException("glTextureStorage3DMultisample");
        }
    }

    public static void glNamedFramebufferTextureLayer(int framebuffer, int attachment, int texture, int level, int layer) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedFramebufferTextureLayer(framebuffer, attachment, texture, level, layer);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedFramebufferTextureLayer(framebuffer, attachment, texture, level, layer);
        } else {
            throw new UnsupportedOperationException("glNamedFramebufferTextureLayer");
        }
    }

    public static void glGetNamedBufferParameteri64v(int buffer, int pname, long[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferParameteri64v(buffer, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferParameteri64v(buffer, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferParameteri64v");
        }
    }

    public static void glGetNamedBufferParameteri64v(int buffer, int pname, LongBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferParameteri64v(buffer, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferParameteri64v(buffer, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferParameteri64v");
        }
    }

    public static void glGetNamedBufferParameteriv(int buffer, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferParameteriv(buffer, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferParameteriv(buffer, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferParameteriv");
        }
    }

    public static void glGetNamedBufferParameteriv(int buffer, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferParameteriv(buffer, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferParameteriv(buffer, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferParameteriv");
        }
    }

    public static void glNamedFramebufferReadBuffer(int framebuffer, int src) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedFramebufferReadBuffer(framebuffer, src);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedFramebufferReadBuffer(framebuffer, src);
        } else {
            throw new UnsupportedOperationException("glNamedFramebufferReadBuffer");
        }
    }

    public static void glGetNamedFramebufferParameteriv(int framebuffer, int pname, int[] param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedFramebufferParameteriv(framebuffer, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedFramebufferParameteriv(framebuffer, pname, param);
        } else {
            throw new UnsupportedOperationException("glGetNamedFramebufferParameteriv");
        }
    }

    public static void glGetNamedFramebufferParameteriv(int framebuffer, int pname, IntBuffer param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedFramebufferParameteriv(framebuffer, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedFramebufferParameteriv(framebuffer, pname, param);
        } else {
            throw new UnsupportedOperationException("glGetNamedFramebufferParameteriv");
        }
    }

    public static int glGetNamedFramebufferParameteri(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetNamedFramebufferParameteri has no caps");
    }

    public static void glFlushMappedNamedBufferRange(int buffer, long offset, long length) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glFlushMappedNamedBufferRange(buffer, offset, length);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glFlushMappedNamedBufferRange(buffer, offset, length);
        } else {
            throw new UnsupportedOperationException("glFlushMappedNamedBufferRange");
        }
    }

    public static void glInvalidateNamedFramebufferData(int framebuffer, IntBuffer numAttachments) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glInvalidateNamedFramebufferData(framebuffer, numAttachments);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glInvalidateNamedFramebufferData(framebuffer, numAttachments);
        } else {
            throw new UnsupportedOperationException("glInvalidateNamedFramebufferData");
        }
    }

    public static void glInvalidateNamedFramebufferData(int framebuffer, int[] numAttachments) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glInvalidateNamedFramebufferData(framebuffer, numAttachments);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glInvalidateNamedFramebufferData(framebuffer, numAttachments);
        } else {
            throw new UnsupportedOperationException("glInvalidateNamedFramebufferData");
        }
    }

    public static void glInvalidateNamedFramebufferData(int framebuffer, int numAttachments) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glInvalidateNamedFramebufferData(framebuffer, numAttachments);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glInvalidateNamedFramebufferData(framebuffer, numAttachments);
        } else {
            throw new UnsupportedOperationException("glInvalidateNamedFramebufferData");
        }
    }

    public static void glCompressedTextureSubImage1D(int texture, int level, int xoffset, int width, int format, ByteBuffer imageSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCompressedTextureSubImage1D(texture, level, xoffset, width, format, imageSize);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCompressedTextureSubImage1D(texture, level, xoffset, width, format, imageSize);
        } else {
            throw new UnsupportedOperationException("glCompressedTextureSubImage1D");
        }
    }

    public static void glCompressedTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int imageSize, long data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCompressedTextureSubImage1D(texture, level, xoffset, width, format, imageSize, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCompressedTextureSubImage1D(texture, level, xoffset, width, format, imageSize, data);
        } else {
            throw new UnsupportedOperationException("glCompressedTextureSubImage1D");
        }
    }

    public static void glNamedFramebufferParameteri(int framebuffer, int pname, int param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedFramebufferParameteri(framebuffer, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedFramebufferParameteri(framebuffer, pname, param);
        } else {
            throw new UnsupportedOperationException("glNamedFramebufferParameteri");
        }
    }

    public static void glNamedFramebufferDrawBuffers(int framebuffer, int[] n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedFramebufferDrawBuffers(framebuffer, n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedFramebufferDrawBuffers(framebuffer, n);
        } else {
            throw new UnsupportedOperationException("glNamedFramebufferDrawBuffers");
        }
    }

    public static void glNamedFramebufferDrawBuffers(int framebuffer, IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedFramebufferDrawBuffers(framebuffer, n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedFramebufferDrawBuffers(framebuffer, n);
        } else {
            throw new UnsupportedOperationException("glNamedFramebufferDrawBuffers");
        }
    }

    public static void glNamedFramebufferDrawBuffers(int framebuffer, int n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedFramebufferDrawBuffers(framebuffer, n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedFramebufferDrawBuffers(framebuffer, n);
        } else {
            throw new UnsupportedOperationException("glNamedFramebufferDrawBuffers");
        }
    }

    public static int glGetTextureLevelParameteri(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetTextureLevelParameteri has no caps");
    }

    public static void glGetVertexArrayIndexed64iv(int vaobj, int index, int pname, long[] param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetVertexArrayIndexed64iv(vaobj, index, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetVertexArrayIndexed64iv(vaobj, index, pname, param);
        } else {
            throw new UnsupportedOperationException("glGetVertexArrayIndexed64iv");
        }
    }

    public static void glGetVertexArrayIndexed64iv(int vaobj, int index, int pname, LongBuffer param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetVertexArrayIndexed64iv(vaobj, index, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetVertexArrayIndexed64iv(vaobj, index, pname, param);
        } else {
            throw new UnsupportedOperationException("glGetVertexArrayIndexed64iv");
        }
    }

    public static void glGetQueryBufferObjectui64v(int id, int buffer, int pname, long offset) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetQueryBufferObjectui64v(id, buffer, pname, offset);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetQueryBufferObjectui64v(id, buffer, pname, offset);
        } else {
            throw new UnsupportedOperationException("glGetQueryBufferObjectui64v");
        }
    }

    public static void glVertexArrayBindingDivisor(int vaobj, int bindingindex, int divisor) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glVertexArrayBindingDivisor(vaobj, bindingindex, divisor);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glVertexArrayBindingDivisor(vaobj, bindingindex, divisor);
        } else {
            throw new UnsupportedOperationException("glVertexArrayBindingDivisor");
        }
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ShortBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureSubImage");
        }
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, short[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureSubImage");
        }
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, IntBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureSubImage");
        }
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, int bufSize, long pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize, pixels);
        } else {
            throw new UnsupportedOperationException("glGetTextureSubImage");
        }
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureSubImage");
        }
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, float[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureSubImage");
        }
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, int[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureSubImage");
        }
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, double[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureSubImage");
        }
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, DoubleBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureSubImage");
        }
    }

    public static void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, FloatBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetTextureSubImage");
        }
    }

    public static ByteBuffer glMapNamedBufferRange(int buffer, long offset, long length, int access, ByteBuffer arg4) {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glMapNamedBufferRange(buffer, offset, length, access, arg4);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glMapNamedBufferRange(buffer, offset, length, access, arg4);
        } else {
            throw new UnsupportedOperationException("glMapNamedBufferRange");
        }
    }

    public static ByteBuffer glMapNamedBufferRange(int buffer, long offset, long length, int access) {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glMapNamedBufferRange(buffer, offset, length, access);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glMapNamedBufferRange(buffer, offset, length, access);
        } else {
            throw new UnsupportedOperationException("glMapNamedBufferRange");
        }
    }

    public static long glGetNamedBufferPointer(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetNamedBufferPointer has no caps");
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, float[] size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, DoubleBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, FloatBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, LongBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, short[] size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, double[] size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, int[] size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, long[] size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, ShortBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, ByteBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, IntBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferSubData");
        }
    }

    public static void glCopyNamedBufferSubData(int readBuffer, int writeBuffer, long readOffset, long writeOffset, long size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCopyNamedBufferSubData(readBuffer, writeBuffer, readOffset, writeOffset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCopyNamedBufferSubData(readBuffer, writeBuffer, readOffset, writeOffset, size);
        } else {
            throw new UnsupportedOperationException("glCopyNamedBufferSubData");
        }
    }

    public static int glGetTransformFeedbacki(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetTransformFeedbacki has no caps");
    }

    public static int glGetTransformFeedbacki(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTransformFeedbacki has no caps");
    }

    public static void glClearNamedBufferSubData(int buffer, int internalformat, long offset, long size, int format, int type, short[] data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferSubData");
        }
    }

    public static void glClearNamedBufferSubData(int buffer, int internalformat, long offset, long size, int format, int type, int[] data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferSubData");
        }
    }

    public static void glClearNamedBufferSubData(int buffer, int internalformat, long offset, long size, int format, int type, float[] data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferSubData");
        }
    }

    public static void glClearNamedBufferSubData(int buffer, int internalformat, long offset, long size, int format, int type, ByteBuffer data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferSubData");
        }
    }

    public static void glClearNamedBufferSubData(int buffer, int internalformat, long offset, long size, int format, int type, IntBuffer data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferSubData");
        }
    }

    public static void glClearNamedBufferSubData(int buffer, int internalformat, long offset, long size, int format, int type, ShortBuffer data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferSubData");
        }
    }

    public static void glClearNamedBufferSubData(int buffer, int internalformat, long offset, long size, int format, int type, FloatBuffer data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferSubData");
        }
    }

    public static void glGetNamedBufferPointerv(int buffer, int pname, PointerBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetNamedBufferPointerv(buffer, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetNamedBufferPointerv(buffer, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetNamedBufferPointerv");
        }
    }

    public static long glGetTransformFeedbacki64(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetTransformFeedbacki64 has no caps");
    }

    public static void glCreateFramebuffers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateFramebuffers(n);
        } else {
            throw new UnsupportedOperationException("glCreateFramebuffers");
        }
    }

    public static void glCreateFramebuffers(int[] n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateFramebuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateFramebuffers(n);
        } else {
            throw new UnsupportedOperationException("glCreateFramebuffers");
        }
    }

    public static int glCreateFramebuffers() {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glCreateFramebuffers();
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glCreateFramebuffers();
        } else {
            throw new UnsupportedOperationException("glCreateFramebuffers");
        }
    }

    public static int glGetNamedBufferParameteri(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetNamedBufferParameteri has no caps");
    }

    public static void glClearNamedBufferData(int buffer, int internalformat, int format, int type, short[] data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferData");
        }
    }

    public static void glClearNamedBufferData(int buffer, int internalformat, int format, int type, int[] data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferData");
        }
    }

    public static void glClearNamedBufferData(int buffer, int internalformat, int format, int type, FloatBuffer data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferData");
        }
    }

    public static void glClearNamedBufferData(int buffer, int internalformat, int format, int type, IntBuffer data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferData");
        }
    }

    public static void glClearNamedBufferData(int buffer, int internalformat, int format, int type, ShortBuffer data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferData");
        }
    }

    public static void glClearNamedBufferData(int buffer, int internalformat, int format, int type, ByteBuffer data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferData");
        }
    }

    public static void glClearNamedBufferData(int buffer, int internalformat, int format, int type, float[] data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedBufferData(buffer, internalformat, format, type, data);
        } else {
            throw new UnsupportedOperationException("glClearNamedBufferData");
        }
    }

    public static void glNamedFramebufferTexture(int framebuffer, int attachment, int texture, int level) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedFramebufferTexture(framebuffer, attachment, texture, level);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedFramebufferTexture(framebuffer, attachment, texture, level);
        } else {
            throw new UnsupportedOperationException("glNamedFramebufferTexture");
        }
    }

    public static void glCreateTransformFeedbacks(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateTransformFeedbacks(n);
        } else {
            throw new UnsupportedOperationException("glCreateTransformFeedbacks");
        }
    }

    public static void glCreateTransformFeedbacks(int[] n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateTransformFeedbacks(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateTransformFeedbacks(n);
        } else {
            throw new UnsupportedOperationException("glCreateTransformFeedbacks");
        }
    }

    public static int glCreateTransformFeedbacks() {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glCreateTransformFeedbacks();
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glCreateTransformFeedbacks();
        } else {
            throw new UnsupportedOperationException("glCreateTransformFeedbacks");
        }
    }

    public static void glGetTransformFeedbackiv(int xfb, int pname, IntBuffer param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTransformFeedbackiv(xfb, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTransformFeedbackiv(xfb, pname, param);
        } else {
            throw new UnsupportedOperationException("glGetTransformFeedbackiv");
        }
    }

    public static void glGetTransformFeedbackiv(int xfb, int pname, int[] param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTransformFeedbackiv(xfb, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTransformFeedbackiv(xfb, pname, param);
        } else {
            throw new UnsupportedOperationException("glGetTransformFeedbackiv");
        }
    }

    public static void glGetTransformFeedbacki_v(int xfb, int pname, int index, IntBuffer param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTransformFeedbacki_v(xfb, pname, index, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTransformFeedbacki_v(xfb, pname, index, param);
        } else {
            throw new UnsupportedOperationException("glGetTransformFeedbacki_v");
        }
    }

    public static void glGetTransformFeedbacki_v(int xfb, int pname, int index, int[] param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTransformFeedbacki_v(xfb, pname, index, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTransformFeedbacki_v(xfb, pname, index, param);
        } else {
            throw new UnsupportedOperationException("glGetTransformFeedbacki_v");
        }
    }

    public static void glNamedBufferStorage(int buffer, long size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferStorage(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferStorage(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferStorage");
        }
    }

    public static void glNamedBufferStorage(int buffer, IntBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferStorage(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferStorage(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferStorage");
        }
    }

    public static void glNamedBufferStorage(int buffer, ByteBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferStorage(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferStorage(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferStorage");
        }
    }

    public static void glNamedBufferStorage(int buffer, ShortBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferStorage(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferStorage(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferStorage");
        }
    }

    public static void glNamedBufferStorage(int buffer, DoubleBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferStorage(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferStorage(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferStorage");
        }
    }

    public static void glNamedBufferStorage(int buffer, FloatBuffer size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferStorage(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferStorage(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferStorage");
        }
    }

    public static void glNamedBufferStorage(int buffer, short[] size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferStorage(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferStorage(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferStorage");
        }
    }

    public static void glNamedBufferStorage(int buffer, int[] size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferStorage(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferStorage(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferStorage");
        }
    }

    public static void glNamedBufferStorage(int buffer, double[] size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferStorage(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferStorage(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferStorage");
        }
    }

    public static void glNamedBufferStorage(int buffer, float[] size, int data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferStorage(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, data);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferStorage(buffer, size, data);
        } else {
            throw new UnsupportedOperationException("glNamedBufferStorage");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, ByteBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glNamedBufferSubDataEXT(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, DoubleBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glNamedBufferSubDataEXT(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, FloatBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glNamedBufferSubDataEXT(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, ShortBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glNamedBufferSubDataEXT(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, IntBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glNamedBufferSubDataEXT(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, LongBuffer size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, int[] size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glNamedBufferSubDataEXT(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, long[] size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, float[] size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glNamedBufferSubDataEXT(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, double[] size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glNamedBufferSubDataEXT(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glNamedBufferSubData(int buffer, long offset, short[] size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedBufferSubData(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glNamedBufferSubDataEXT(buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedBufferSubData(buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glNamedBufferSubData");
        }
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage1D");
        }
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, double[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage1D");
        }
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, DoubleBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage1D");
        }
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage1D");
        }
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage1D");
        }
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage1D");
        }
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage1D");
        }
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage1D");
        }
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage1D");
        }
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage1D");
        }
    }

    public static void glGetTextureParameterfv(int texture, int pname, FloatBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureParameterfv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureParameterfv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureParameterfv");
        }
    }

    public static void glGetTextureParameterfv(int texture, int pname, float[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureParameterfv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureParameterfv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureParameterfv");
        }
    }

    public static void glCopyTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCopyTextureSubImage2D(texture, level, xoffset, yoffset, x, y, width, height);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCopyTextureSubImage2D(texture, level, xoffset, yoffset, x, y, width, height);
        } else {
            throw new UnsupportedOperationException("glCopyTextureSubImage2D");
        }
    }

    public static void glGenerateTextureMipmap(int texture) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGenerateTextureMipmap(texture);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGenerateTextureMipmap(texture);
        } else {
            throw new UnsupportedOperationException("glGenerateTextureMipmap");
        }
    }

    public static int glGetTextureParameteri(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTextureParameteri has no caps");
    }

    public static void glNamedRenderbufferStorage(int renderbuffer, int internalformat, int width, int height) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glNamedRenderbufferStorage(renderbuffer, internalformat, width, height);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glNamedRenderbufferStorage(renderbuffer, internalformat, width, height);
        } else {
            throw new UnsupportedOperationException("glNamedRenderbufferStorage");
        }
    }

    public static void glTextureParameterIi(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glTextureParameterIi has no caps");
    }

    public static void glVertexArrayVertexBuffers(int vaobj, int first, IntBuffer count, PointerBuffer buffers, IntBuffer offsets) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glVertexArrayVertexBuffers(vaobj, first, count, buffers, offsets);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glVertexArrayVertexBuffers(vaobj, first, count, buffers, offsets);
        } else {
            throw new UnsupportedOperationException("glVertexArrayVertexBuffers");
        }
    }

    public static void glVertexArrayVertexBuffers(int vaobj, int first, int[] count, PointerBuffer buffers, int[] offsets) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glVertexArrayVertexBuffers(vaobj, first, count, buffers, offsets);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glVertexArrayVertexBuffers(vaobj, first, count, buffers, offsets);
        } else {
            throw new UnsupportedOperationException("glVertexArrayVertexBuffers");
        }
    }

    public static void glClearNamedFramebufferuiv(int framebuffer, int buffer, int drawbuffer, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedFramebufferuiv(framebuffer, buffer, drawbuffer, value);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedFramebufferuiv(framebuffer, buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearNamedFramebufferuiv");
        }
    }

    public static void glClearNamedFramebufferuiv(int framebuffer, int buffer, int drawbuffer, int[] value) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedFramebufferuiv(framebuffer, buffer, drawbuffer, value);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedFramebufferuiv(framebuffer, buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearNamedFramebufferuiv");
        }
    }

    public static int glCreateRenderbuffers() {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glCreateRenderbuffers();
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glCreateRenderbuffers();
        } else {
            throw new UnsupportedOperationException("glCreateRenderbuffers");
        }
    }

    public static void glCreateRenderbuffers(int[] n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateRenderbuffers(n);
        } else {
            throw new UnsupportedOperationException("glCreateRenderbuffers");
        }
    }

    public static void glCreateRenderbuffers(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateRenderbuffers(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateRenderbuffers(n);
        } else {
            throw new UnsupportedOperationException("glCreateRenderbuffers");
        }
    }

    public static void glTextureParameterIiv(int texture, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureParameterIiv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureParameterIiv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glTextureParameterIiv");
        }
    }

    public static void glTextureParameterIiv(int texture, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureParameterIiv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureParameterIiv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glTextureParameterIiv");
        }
    }

    public static void glGetVertexArrayIndexediv(int vaobj, int index, int pname, int[] param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetVertexArrayIndexediv(vaobj, index, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetVertexArrayIndexediv(vaobj, index, pname, param);
        } else {
            throw new UnsupportedOperationException("glGetVertexArrayIndexediv");
        }
    }

    public static void glGetVertexArrayIndexediv(int vaobj, int index, int pname, IntBuffer param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetVertexArrayIndexediv(vaobj, index, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetVertexArrayIndexediv(vaobj, index, pname, param);
        } else {
            throw new UnsupportedOperationException("glGetVertexArrayIndexediv");
        }
    }

    public static int glGetVertexArrayIndexedi(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetVertexArrayIndexedi has no caps");
    }

    public static long glGetVertexArrayIndexed64i(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glGetVertexArrayIndexed64i has no caps");
    }

    public static void glCopyTextureSubImage1D(int texture, int level, int xoffset, int x, int y, int width) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCopyTextureSubImage1D(texture, level, xoffset, x, y, width);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCopyTextureSubImage1D(texture, level, xoffset, x, y, width);
        } else {
            throw new UnsupportedOperationException("glCopyTextureSubImage1D");
        }
    }

    public static void glClearNamedFramebufferfv(int framebuffer, int buffer, int drawbuffer, float[] value) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedFramebufferfv(framebuffer, buffer, drawbuffer, value);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedFramebufferfv(framebuffer, buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearNamedFramebufferfv");
        }
    }

    public static void glClearNamedFramebufferfv(int framebuffer, int buffer, int drawbuffer, FloatBuffer value) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedFramebufferfv(framebuffer, buffer, drawbuffer, value);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedFramebufferfv(framebuffer, buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearNamedFramebufferfv");
        }
    }

    public static void glTextureParameterIuiv(int texture, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureParameterIuiv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureParameterIuiv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glTextureParameterIuiv");
        }
    }

    public static void glTextureParameterIuiv(int texture, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureParameterIuiv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureParameterIuiv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glTextureParameterIuiv");
        }
    }

    public static void glClearNamedFramebufferfi(int framebuffer, int buffer, int drawbuffer, float depth, int stencil) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedFramebufferfi(framebuffer, buffer, drawbuffer, depth, stencil);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedFramebufferfi(framebuffer, buffer, drawbuffer, depth, stencil);
        } else {
            throw new UnsupportedOperationException("glClearNamedFramebufferfi");
        }
    }

    public static void glGetTextureParameterIiv(int texture, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureParameterIiv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureParameterIiv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureParameterIiv");
        }
    }

    public static void glGetTextureParameterIiv(int texture, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureParameterIiv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureParameterIiv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureParameterIiv");
        }
    }

    public static void glBlitNamedFramebuffer(int readFramebuffer, int drawFramebuffer, int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glBlitNamedFramebuffer(readFramebuffer, drawFramebuffer, srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glBlitNamedFramebuffer(readFramebuffer, drawFramebuffer, srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
        } else {
            throw new UnsupportedOperationException("glBlitNamedFramebuffer");
        }
    }

    public static void glCopyTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCopyTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, x, y, width, height);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCopyTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, x, y, width, height);
        } else {
            throw new UnsupportedOperationException("glCopyTextureSubImage3D");
        }
    }

    public static void glGetTextureParameterIuiv(int texture, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureParameterIuiv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureParameterIuiv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureParameterIuiv");
        }
    }

    public static void glGetTextureParameterIuiv(int texture, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureParameterIuiv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureParameterIuiv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureParameterIuiv");
        }
    }

    public static void glTextureParameterfv(int texture, int pname, float[] param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureParameterfv(texture, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureParameterfv(texture, pname, param);
        } else {
            throw new UnsupportedOperationException("glTextureParameterfv");
        }
    }

    public static void glTextureParameterfv(int texture, int pname, FloatBuffer param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureParameterfv(texture, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureParameterfv(texture, pname, param);
        } else {
            throw new UnsupportedOperationException("glTextureParameterfv");
        }
    }

    public static void glTextureParameteriv(int texture, int pname, IntBuffer param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureParameteriv(texture, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureParameteriv(texture, pname, param);
        } else {
            throw new UnsupportedOperationException("glTextureParameteriv");
        }
    }

    public static void glTextureParameteriv(int texture, int pname, int[] param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureParameteriv(texture, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureParameteriv(texture, pname, param);
        } else {
            throw new UnsupportedOperationException("glTextureParameteriv");
        }
    }

    public static void glTextureParameterf(int texture, int pname, float param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureParameterf(texture, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureParameterf(texture, pname, param);
        } else {
            throw new UnsupportedOperationException("glTextureParameterf");
        }
    }

    public static float glGetTextureParameterf(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTextureParameterf has no caps");
    }

    public static void glGetTextureParameteriv(int texture, int pname, int[] params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureParameteriv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureParameteriv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureParameteriv");
        }
    }

    public static void glGetTextureParameteriv(int texture, int pname, IntBuffer params) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetTextureParameteriv(texture, pname, params);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetTextureParameteriv(texture, pname, params);
        } else {
            throw new UnsupportedOperationException("glGetTextureParameteriv");
        }
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage3D");
        }
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage3D");
        }
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);

        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage3D");
        }
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage3D");
        }
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage3D");
        }
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, double[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage3D");
        }
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, DoubleBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage3D");
        }
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage3D");
        }
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage3D");
        }
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage3D");
        }
    }

    public static void glCreateVertexArrays(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateVertexArrays(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateVertexArrays(n);
        } else {
            throw new UnsupportedOperationException("glCreateVertexArrays");
        }
    }

    public static void glCreateVertexArrays(int[] n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateVertexArrays(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateVertexArrays(n);
        } else {
            throw new UnsupportedOperationException("glCreateVertexArrays");
        }
    }

    public static int glCreateVertexArrays() {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glCreateVertexArrays();
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glCreateVertexArrays();
        } else {
            throw new UnsupportedOperationException("glCreateVertexArrays");
        }
    }

    public static void glTextureBufferRange(int texture, int internalformat, int buffer, long offset, long size) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureBufferRange(texture, internalformat, buffer, offset, size);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureBufferRange(texture, internalformat, buffer, offset, size);
        } else {
            throw new UnsupportedOperationException("glTextureBufferRange");
        }
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, DoubleBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage2D");
        }
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, ShortBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage2D");
        }
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, FloatBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage2D");
        }
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, int[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage2D");
        }
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, double[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage2D");
        }
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, float[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage2D");
        }
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, long pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage2D");
        }
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage2D");
        }
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, IntBuffer pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage2D");
        }
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, short[] pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
        } else {
            throw new UnsupportedOperationException("glTextureSubImage2D");
        }
    }

    public static int glGetTextureParameterIui(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTextureParameterIui has no caps");
    }

    public static void glTextureParameterIui(int arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("glTextureParameterIui has no caps");
    }

    public static void glDisableVertexArrayAttrib(int vaobj, int index) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glDisableVertexArrayAttrib(vaobj, index);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glDisableVertexArrayAttrib(vaobj, index);
        } else {
            throw new UnsupportedOperationException("glDisableVertexArrayAttrib");
        }
    }

    public static void glEnableVertexArrayAttrib(int vaobj, int index) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glEnableVertexArrayAttrib(vaobj, index);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glEnableVertexArrayAttrib(vaobj, index);
        } else {
            throw new UnsupportedOperationException("glEnableVertexArrayAttrib");
        }
    }

    public static void glVertexArrayElementBuffer(int vaobj, int buffer) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glVertexArrayElementBuffer(vaobj, buffer);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glVertexArrayElementBuffer(vaobj, buffer);
        } else {
            throw new UnsupportedOperationException("glVertexArrayElementBuffer");
        }
    }

    public static void glVertexArrayVertexBuffer(int vaobj, int bindingindex, int buffer, long offset, int stride) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glVertexArrayVertexBuffer(vaobj, bindingindex, buffer, offset, stride);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glVertexArrayVertexBuffer(vaobj, bindingindex, buffer, offset, stride);
        } else {
            throw new UnsupportedOperationException("glVertexArrayVertexBuffer");
        }
    }

    public static int glGetTextureParameterIi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetTextureParameterIi has no caps");
    }

    public static void glVertexArrayAttribFormat(int vaobj, int attribindex, int size, int type, boolean normalized, int relativeoffset) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glVertexArrayAttribFormat(vaobj, attribindex, size, type, normalized, relativeoffset);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glVertexArrayAttribFormat(vaobj, attribindex, size, type, normalized, relativeoffset);
        } else {
            throw new UnsupportedOperationException("glVertexArrayAttribFormat");
        }
    }

    public static void glVertexArrayAttribIFormat(int vaobj, int attribindex, int size, int type, int relativeoffset) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glVertexArrayAttribIFormat(vaobj, attribindex, size, type, relativeoffset);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glVertexArrayAttribIFormat(vaobj, attribindex, size, type, relativeoffset);
        } else {
            throw new UnsupportedOperationException("glVertexArrayAttribIFormat");
        }
    }

    public static void glClearNamedFramebufferiv(int framebuffer, int buffer, int drawbuffer, IntBuffer value) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedFramebufferiv(framebuffer, buffer, drawbuffer, value);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedFramebufferiv(framebuffer, buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearNamedFramebufferiv");
        }
    }

    public static void glClearNamedFramebufferiv(int framebuffer, int buffer, int drawbuffer, int[] value) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glClearNamedFramebufferiv(framebuffer, buffer, drawbuffer, value);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glClearNamedFramebufferiv(framebuffer, buffer, drawbuffer, value);
        } else {
            throw new UnsupportedOperationException("glClearNamedFramebufferiv");
        }
    }

    public static void glVertexArrayAttribBinding(int vaobj, int attribindex, int bindingindex) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glVertexArrayAttribBinding(vaobj, attribindex, bindingindex);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glVertexArrayAttribBinding(vaobj, attribindex, bindingindex);
        } else {
            throw new UnsupportedOperationException("glVertexArrayAttribBinding");
        }
    }

    public static void glTextureParameteri(int texture, int pname, int param) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureParameteri(texture, pname, param);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glTextureParameteri(texture, pname, param);
        } else {
            throw new UnsupportedOperationException("glTextureParameteri");
        }
    }

    public static void glVertexArrayAttribLFormat(int vaobj, int attribindex, int size, int type, int relativeoffset) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glVertexArrayAttribLFormat(vaobj, attribindex, size, type, relativeoffset);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glVertexArrayAttribLFormat(vaobj, attribindex, size, type, relativeoffset);
        } else {
            throw new UnsupportedOperationException("glVertexArrayAttribLFormat");
        }
    }

    public static void glMemoryBarrierByRegion(int barriers) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glMemoryBarrierByRegion(barriers);
        } else if (GL_CAPABILITIES.GL_ARB_ES3_1_compatibility) {
            ARBES31Compatibility.glMemoryBarrierByRegion(barriers);
        } else {
            throw new UnsupportedOperationException("glMemoryBarrierByRegion");
        }
    }

    public static void glGetQueryBufferObjectiv(int id, int buffer, int pname, long offset) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetQueryBufferObjectiv(id, buffer, pname, offset);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetQueryBufferObjectiv(id, buffer, pname, offset);
        } else {
            throw new UnsupportedOperationException("glGetQueryBufferObjectiv");
        }
    }

    public static void glGetQueryBufferObjectuiv(int id, int buffer, int pname, long offset) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetQueryBufferObjectuiv(id, buffer, pname, offset);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetQueryBufferObjectuiv(id, buffer, pname, offset);
        } else {
            throw new UnsupportedOperationException("glGetQueryBufferObjectuiv");
        }
    }

    public static void glCreateProgramPipelines(int[] n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateProgramPipelines(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateProgramPipelines(n);
        } else {
            throw new UnsupportedOperationException("glCreateProgramPipelines");
        }
    }

    public static void glCreateProgramPipelines(IntBuffer n) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glCreateProgramPipelines(n);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glCreateProgramPipelines(n);
        } else {
            throw new UnsupportedOperationException("glCreateProgramPipelines");
        }
    }

    public static int glCreateProgramPipelines() {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glCreateProgramPipelines();
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            return ARBDirectStateAccess.glCreateProgramPipelines();
        } else {
            throw new UnsupportedOperationException("glCreateProgramPipelines");
        }
    }

    public static void glGetQueryBufferObjecti64v(int id, int buffer, int pname, long offset) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetQueryBufferObjecti64v(id, buffer, pname, offset);
        } else if (GL_CAPABILITIES.GL_ARB_direct_state_access) {
            ARBDirectStateAccess.glGetQueryBufferObjecti64v(id, buffer, pname, offset);
        } else {
            throw new UnsupportedOperationException("glGetQueryBufferObjecti64v");
        }
    }

    public static void glTextureBarrier() {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glTextureBarrier();
        } else if (GL_CAPABILITIES.GL_ARB_texture_barrier) {
            ARBTextureBarrier.glTextureBarrier();
        } else {
            throw new UnsupportedOperationException("glTextureBarrier");
        }
    }

    public static void glGetnConvolutionFilter(int target, int format, int type, int bufSize, long image) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnConvolutionFilter(target, format, type, bufSize, image);
        } else {
            throw new UnsupportedOperationException("glGetnConvolutionFilter");
        }
    }

    public static void glGetnConvolutionFilter(int target, int format, int type, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnConvolutionFilter(target, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnConvolutionFilter");
        }
    }

    public static void glGetnPolygonStipple(int bufSize, long pattern) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnPolygonStipple(bufSize, pattern);
        } else {
            throw new UnsupportedOperationException("glGetnPolygonStipple");
        }
    }

    public static void glGetnPolygonStipple(ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnPolygonStipple(bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnPolygonStipple");
        }
    }

    public static void glGetnSeparableFilter(int target, int format, int type, int rowBufSize, long row, int columnBufSize, long column, ByteBuffer span) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnSeparableFilter(target, format, type, rowBufSize, row, columnBufSize, column, span);
        } else {
            throw new UnsupportedOperationException("glGetnSeparableFilter");
        }
    }

    public static void glGetnSeparableFilter(int target, int format, int type, ByteBuffer rowBufSize, ByteBuffer row, ByteBuffer columnBufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnSeparableFilter(target, format, type, rowBufSize, row, columnBufSize);
        } else {
            throw new UnsupportedOperationException("glGetnSeparableFilter");
        }
    }

    public static void glGetnCompressedTexImage(int target, int lod, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnCompressedTexImage(target, lod, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnCompressedTexImage");
        }
    }

    public static void glGetnCompressedTexImage(int target, int lod, int bufSize, long pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnCompressedTexImage(target, lod, bufSize, pixels);
        } else {
            throw new UnsupportedOperationException("glGetnCompressedTexImage");
        }
    }

    public static int glGetGraphicsResetStatus() {
        if (GL_CAPABILITIES.OpenGL45) {
            return GL45.glGetGraphicsResetStatus();
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            return KHRRobustness.glGetGraphicsResetStatus();
        } else {
            throw new UnsupportedOperationException("glGetGraphicsResetStatus");
        }
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, ShortBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureSubImage");
        }
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, IntBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureSubImage");
        }
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, FloatBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureSubImage");
        }
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, DoubleBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureSubImage");
        }
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, double[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureSubImage");
        }
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, float[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureSubImage");
        }
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureSubImage");
        }
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, short[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureSubImage");
        }
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int bufSize, long pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize, pixels);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize, pixels);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureSubImage");
        }
    }

    public static void glGetCompressedTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_get_texture_sub_image) {
            ARBGetTextureSubImage.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetCompressedTextureSubImage");
        }
    }

    public static void glGetnUniformuiv(int program, int location, int[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnUniformuiv(program, location, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glGetnUniformuiv(program, location, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnUniformuiv");
        }
    }

    public static void glGetnUniformuiv(int program, int location, IntBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnUniformuiv(program, location, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glGetnUniformuiv(program, location, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnUniformuiv");
        }
    }

    public static void glGetnUniformdv(int program, int location, DoubleBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnUniformdv(program, location, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnUniformdv");
        }
    }

    public static void glGetnUniformdv(int program, int location, double[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnUniformdv(program, location, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnUniformdv");
        }
    }

    public static int glGetnUniformui(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetnUniformui has no caps");
    }

    public static double glGetnUniformd(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetnUniformd has no caps");
    }

    public static float glGetnUniformf(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetnUniformf has no caps");
    }

    public static void glGetnUniformiv(int program, int location, int[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnUniformiv(program, location, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glGetnUniformiv(program, location, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnUniformiv");
        }
    }

    public static void glGetnUniformiv(int program, int location, IntBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnUniformiv(program, location, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glGetnUniformiv(program, location, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnUniformiv");
        }
    }

    public static int glGetnUniformi(int arg0, int arg1) {
        throw new UnsupportedOperationException("glGetnUniformi has no caps");
    }

    public static void glGetnMapiv(int target, int query, int[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnMapiv(target, query, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnMapiv");
        }
    }

    public static void glGetnMapiv(int target, int query, IntBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnMapiv(target, query, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnMapiv");
        }
    }

    public static void glGetnPixelMapfv(int map, float[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnPixelMapfv(map, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnPixelMapfv");
        }
    }

    public static void glGetnPixelMapfv(int map, FloatBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnPixelMapfv(map, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnPixelMapfv");
        }
    }

    public static void glGetnPixelMapusv(int map, short[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnPixelMapusv(map, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnPixelMapusv");
        }
    }

    public static void glGetnPixelMapusv(int map, ShortBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnPixelMapusv(map, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnPixelMapusv");
        }
    }

    public static void glGetnHistogram(int target, boolean reset, int format, int type, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnHistogram(target, reset, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnHistogram");
        }
    }

    public static void glGetnHistogram(int target, boolean reset, int format, int type, int bufSize, long values) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnHistogram(target, reset, format, type, bufSize, values);
        } else {
            throw new UnsupportedOperationException("glGetnHistogram");
        }
    }

    public static void glGetnUniformfv(int program, int location, float[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnUniformfv(program, location, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glGetnUniformfv(program, location, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnUniformfv");
        }
    }

    public static void glGetnUniformfv(int program, int location, FloatBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnUniformfv(program, location, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glGetnUniformfv(program, location, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnUniformfv");
        }
    }

    public static void glGetnMinmax(int target, boolean reset, int format, int type, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnMinmax(target, reset, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnMinmax");
        }
    }

    public static void glGetnMinmax(int target, boolean reset, int format, int type, int bufSize, long values) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnMinmax(target, reset, format, type, bufSize, values);
        } else {
            throw new UnsupportedOperationException("glGetnMinmax");
        }
    }

    public static void glGetnPixelMapuiv(int map, IntBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnPixelMapuiv(map, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnPixelMapuiv");
        }
    }

    public static void glGetnPixelMapuiv(int map, int[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnPixelMapuiv(map, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnPixelMapuiv");
        }
    }

    public static void glGetnMapfv(int target, int query, float[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnMapfv(target, query, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnMapfv");
        }
    }

    public static void glGetnMapfv(int target, int query, FloatBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnMapfv(target, query, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnMapfv");
        }
    }

    public static void glGetnMapdv(int target, int query, double[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnMapdv(target, query, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnMapdv");
        }
    }

    public static void glGetnMapdv(int target, int query, DoubleBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnMapdv(target, query, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnMapdv");
        }
    }

    public static void glReadnPixels(int x, int y, int width, int height, int format, int type, short[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_robustness) {
            ARBRobustness.glReadnPixelsARB(x, y, width, height, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glReadnPixels");
        }
    }

    public static void glReadnPixels(int x, int y, int width, int height, int format, int type, FloatBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_robustness) {
            ARBRobustness.glReadnPixelsARB(x, y, width, height, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glReadnPixels");
        }
    }

    public static void glReadnPixels(int x, int y, int width, int height, int format, int type, float[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_robustness) {
            ARBRobustness.glReadnPixelsARB(x, y, width, height, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glReadnPixels");
        }
    }

    public static void glReadnPixels(int x, int y, int width, int height, int format, int type, int[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_robustness) {
            ARBRobustness.glReadnPixelsARB(x, y, width, height, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glReadnPixels");
        }
    }

    public static void glReadnPixels(int x, int y, int width, int height, int format, int type, IntBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_robustness) {
            ARBRobustness.glReadnPixelsARB(x, y, width, height, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glReadnPixels");
        }
    }

    public static void glReadnPixels(int x, int y, int width, int height, int format, int type, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_robustness) {
            ARBRobustness.glReadnPixelsARB(x, y, width, height, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glReadnPixels");
        }
    }

    public static void glReadnPixels(int x, int y, int width, int height, int format, int type, int bufSize, long data) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glReadnPixels(x, y, width, height, format, type, bufSize, data);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glReadnPixels(x, y, width, height, format, type, bufSize, data);
        } else if (GL_CAPABILITIES.GL_ARB_robustness) {
            ARBRobustness.glReadnPixelsARB(x, y, width, height, format, type, bufSize, data);
        } else {
            throw new UnsupportedOperationException("glReadnPixels");
        }
    }

    public static void glReadnPixels(int x, int y, int width, int height, int format, int type, ShortBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_KHR_robustness) {
            KHRRobustness.glReadnPixels(x, y, width, height, format, type, bufSize);
        } else if (GL_CAPABILITIES.GL_ARB_robustness) {
            ARBRobustness.glReadnPixelsARB(x, y, width, height, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glReadnPixels");
        }
    }

    public static void glGetnColorTable(int target, int format, int type, int[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnColorTable(target, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnColorTable");
        }
    }

    public static void glGetnColorTable(int target, int format, int type, ShortBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnColorTable(target, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnColorTable");
        }
    }

    public static void glGetnColorTable(int target, int format, int type, short[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnColorTable(target, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnColorTable");
        }
    }

    public static void glGetnColorTable(int target, int format, int type, float[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnColorTable(target, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnColorTable");
        }
    }

    public static void glGetnColorTable(int target, int format, int type, FloatBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnColorTable(target, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnColorTable");
        }
    }

    public static void glGetnColorTable(int target, int format, int type, IntBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnColorTable(target, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnColorTable");
        }
    }

    public static void glGetnColorTable(int target, int format, int type, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnColorTable(target, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnColorTable");
        }
    }

    public static void glGetnColorTable(int target, int format, int type, int bufSize, long table) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnColorTable(target, format, type, bufSize, table);
        } else {
            throw new UnsupportedOperationException("glGetnColorTable");
        }
    }

    public static void glGetnTexImage(int target, int level, int format, int type, DoubleBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnTexImage(target, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnTexImage");
        }
    }

    public static void glGetnTexImage(int target, int level, int format, int type, ByteBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnTexImage(target, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnTexImage");
        }
    }

    public static void glGetnTexImage(int target, int level, int format, int type, ShortBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnTexImage(target, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnTexImage");
        }
    }

    public static void glGetnTexImage(int target, int level, int format, int type, FloatBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnTexImage(target, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnTexImage");
        }
    }

    public static void glGetnTexImage(int target, int level, int format, int type, IntBuffer bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnTexImage(target, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnTexImage");
        }
    }

    public static void glGetnTexImage(int target, int level, int format, int type, double[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnTexImage(target, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnTexImage");
        }
    }

    public static void glGetnTexImage(int target, int level, int format, int type, float[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnTexImage(target, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnTexImage");
        }
    }

    public static void glGetnTexImage(int target, int level, int format, int type, int[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnTexImage(target, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnTexImage");
        }
    }

    public static void glGetnTexImage(int target, int level, int format, int type, short[] bufSize) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnTexImage(target, level, format, type, bufSize);
        } else {
            throw new UnsupportedOperationException("glGetnTexImage");
        }
    }

    public static void glGetnTexImage(int target, int level, int format, int type, int bufSize, long pixels) {
        if (GL_CAPABILITIES.OpenGL45) {
            GL45.glGetnTexImage(target, level, format, type, bufSize, pixels);
        } else {
            throw new UnsupportedOperationException("glGetnTexImage");
        }
    }

    // --- GL46 ---
    public static void glMultiDrawElementsIndirectCount(int mode, int type, ByteBuffer indirect, long drawcount, int maxdrawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glMultiDrawElementsIndirectCount(mode, type, indirect, drawcount, maxdrawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_indirect_parameters) {
            ARBIndirectParameters.glMultiDrawElementsIndirectCountARB(mode, type, indirect, drawcount, maxdrawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElementsIndirectCount");
        }
    }

    public static void glMultiDrawElementsIndirectCount(int mode, int type, IntBuffer indirect, long drawcount, int maxdrawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glMultiDrawElementsIndirectCount(mode, type, indirect, drawcount, maxdrawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_indirect_parameters) {
            ARBIndirectParameters.glMultiDrawElementsIndirectCountARB(mode, type, indirect, drawcount, maxdrawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElementsIndirectCount");
        }
    }

    public static void glMultiDrawElementsIndirectCount(int mode, int type, int[] indirect, long drawcount, int maxdrawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glMultiDrawElementsIndirectCount(mode, type, indirect, drawcount, maxdrawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_indirect_parameters) {
            ARBIndirectParameters.glMultiDrawElementsIndirectCountARB(mode, type, indirect, drawcount, maxdrawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElementsIndirectCount");
        }
    }

    public static void glMultiDrawElementsIndirectCount(int mode, int type, long indirect, long drawcount, int maxdrawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glMultiDrawElementsIndirectCount(mode, type, indirect, drawcount, maxdrawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_indirect_parameters) {
            ARBIndirectParameters.glMultiDrawElementsIndirectCountARB(mode, type, indirect, drawcount, maxdrawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawElementsIndirectCount");
        }
    }

    public static void glMultiDrawArraysIndirectCount(int mode, int[] indirect, long drawcount, int maxdrawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glMultiDrawArraysIndirectCount(mode, indirect, drawcount, maxdrawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_indirect_parameters) {
            ARBIndirectParameters.glMultiDrawArraysIndirectCountARB(mode, indirect, drawcount, maxdrawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawArraysIndirectCount");
        }
    }

    public static void glMultiDrawArraysIndirectCount(int mode, ByteBuffer indirect, long drawcount, int maxdrawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glMultiDrawArraysIndirectCount(mode, indirect, drawcount, maxdrawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_indirect_parameters) {
            ARBIndirectParameters.glMultiDrawArraysIndirectCountARB(mode, indirect, drawcount, maxdrawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawArraysIndirectCount");
        }
    }

    public static void glMultiDrawArraysIndirectCount(int mode, IntBuffer indirect, long drawcount, int maxdrawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glMultiDrawArraysIndirectCount(mode, indirect, drawcount, maxdrawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_indirect_parameters) {
            ARBIndirectParameters.glMultiDrawArraysIndirectCountARB(mode, indirect, drawcount, maxdrawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawArraysIndirectCount");
        }
    }

    public static void glMultiDrawArraysIndirectCount(int mode, long indirect, long drawcount, int maxdrawcount, int stride) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glMultiDrawArraysIndirectCount(mode, indirect, drawcount, maxdrawcount, stride);
        } else if (GL_CAPABILITIES.GL_ARB_indirect_parameters) {
            ARBIndirectParameters.glMultiDrawArraysIndirectCountARB(mode, indirect, drawcount, maxdrawcount, stride);
        } else {
            throw new UnsupportedOperationException("glMultiDrawArraysIndirectCount");
        }
    }

    public static void glPolygonOffsetClamp(float factor, float units, float clamp) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glPolygonOffsetClamp(factor, units, clamp);
        } else if (GL_CAPABILITIES.GL_EXT_polygon_offset_clamp) {
            EXTPolygonOffsetClamp.glPolygonOffsetClampEXT(factor, units, clamp);
        } else if (GL_CAPABILITIES.GL_ARB_polygon_offset_clamp) {
            ARBPolygonOffsetClamp.glPolygonOffsetClamp(factor, units, clamp);
        } else {
            throw new UnsupportedOperationException("glPolygonOffsetClamp");
        }
    }

    public static void glSpecializeShader(int shader, CharSequence pEntryPoint, IntBuffer numSpecializationConstants, IntBuffer pConstantIndex) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glSpecializeShader(shader, pEntryPoint, numSpecializationConstants, pConstantIndex);
        } else if (GL_CAPABILITIES.GL_ARB_gl_spirv) {
            ARBGLSPIRV.glSpecializeShaderARB(shader, pEntryPoint, numSpecializationConstants, pConstantIndex);
        } else {
            throw new UnsupportedOperationException("glSpecializeShader");
        }
    }

    public static void glSpecializeShader(int shader, CharSequence pEntryPoint, int[] numSpecializationConstants, int[] pConstantIndex) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glSpecializeShader(shader, pEntryPoint, numSpecializationConstants, pConstantIndex);
        } else if (GL_CAPABILITIES.GL_ARB_gl_spirv) {
            ARBGLSPIRV.glSpecializeShaderARB(shader, pEntryPoint, numSpecializationConstants, pConstantIndex);
        } else {
            throw new UnsupportedOperationException("glSpecializeShader");
        }
    }

    public static void glSpecializeShader(int shader, ByteBuffer pEntryPoint, int[] numSpecializationConstants, int[] pConstantIndex) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glSpecializeShader(shader, pEntryPoint, numSpecializationConstants, pConstantIndex);
        } else if (GL_CAPABILITIES.GL_ARB_gl_spirv) {
            ARBGLSPIRV.glSpecializeShaderARB(shader, pEntryPoint, numSpecializationConstants, pConstantIndex);
        } else {
            throw new UnsupportedOperationException("glSpecializeShader");
        }
    }

    public static void glSpecializeShader(int shader, ByteBuffer pEntryPoint, IntBuffer numSpecializationConstants, IntBuffer pConstantIndex) {
        if (GL_CAPABILITIES.OpenGL46) {
            GL46.glSpecializeShader(shader, pEntryPoint, numSpecializationConstants, pConstantIndex);
        } else if (GL_CAPABILITIES.GL_ARB_gl_spirv) {
            ARBGLSPIRV.glSpecializeShaderARB(shader, pEntryPoint, numSpecializationConstants, pConstantIndex);
        } else {
            throw new UnsupportedOperationException("glSpecializeShader");
        }
    }
}
