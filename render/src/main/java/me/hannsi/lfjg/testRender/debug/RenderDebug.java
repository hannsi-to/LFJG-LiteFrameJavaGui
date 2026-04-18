package me.hannsi.lfjg.testRender.debug;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.testRender.debug.exceptions.render.RenderDebugException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;

import java.nio.*;

import static me.hannsi.lfjg.testRender.LFJGRenderContext.CONTEXT_PROFILE_MASK;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.RENDER_DEBUG_THROW_ERROR;
import static org.lwjgl.opengl.GL32.GL_CONTEXT_COMPATIBILITY_PROFILE_BIT;
import static org.lwjgl.opengl.GL32.GL_CONTEXT_CORE_PROFILE_BIT;

public class RenderDebug {
    private final boolean profileCheck;

    public RenderDebug() {
        if ((CONTEXT_PROFILE_MASK & GL_CONTEXT_COMPATIBILITY_PROFILE_BIT) != 0) {
            profileCheck = true;
            DebugLog.debug(RenderDebug.class, "Compatibility profile. profileCheck: " + true);
        } else if ((CONTEXT_PROFILE_MASK & GL_CONTEXT_CORE_PROFILE_BIT) != 0) {
            profileCheck = false;
            DebugLog.debug(RenderDebug.class, "Core profile. profileCheck: " + false);
        } else {
            profileCheck = false;
        }
    }

    public void glBegin(int i) {
        if (canUseImmediateMode()) {
            GL11.glBegin(i);
        }
    }

    public void glEnd() {
        if (canUseImmediateMode()) {
            GL11.glEnd();
        }
    }

    public void glVertex2f(float v, float v1) {
        if (canUseImmediateMode()) {
            GL11.glVertex2f(v, v1);
        }
    }

    public void glVertex2fv(float[] coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex2fv(coords);
        }
    }

    public void glVertex2fv(FloatBuffer coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex2fv(coords);
        }
    }

    public void glVertex2i(int i, int i1) {
        if (canUseImmediateMode()) {
            GL11.glVertex2i(i, i1);
        }
    }

    public void glVertex2iv(int[] coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex2iv(coords);
        }
    }

    public void glVertex2iv(IntBuffer coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex2iv(coords);
        }
    }

    public void glVertex2d(double d, double d1) {
        if (canUseImmediateMode()) {
            GL11.glVertex2d(d, d1);
        }
    }

    public void glVertex2dv(double[] coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex2dv(coords);
        }
    }

    public void glVertex2dv(DoubleBuffer coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex2dv(coords);
        }
    }

    public void glVertex2s(short i, short i1) {
        if (canUseImmediateMode()) {
            GL11.glVertex2s(i, i1);
        }
    }

    public void glVertex2sv(short[] coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex2sv(coords);
        }
    }

    public void glVertex2sv(ShortBuffer coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex2sv(coords);
        }
    }

    public void glVertex3f(float v, float v1, float v2) {
        if (canUseImmediateMode()) {
            GL11.glVertex3f(v, v1, v2);
        }
    }

    public void glVertex3fv(float[] coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex3fv(coords);
        }
    }

    public void glVertex3fv(FloatBuffer coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex3fv(coords);
        }
    }

    public void glVertex3i(int i, int i1, int i2) {
        if (canUseImmediateMode()) {
            GL11.glVertex3i(i, i1, i2);
        }
    }

    public void glVertex3iv(int[] coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex3iv(coords);
        }
    }

    public void glVertex3iv(IntBuffer coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex3iv(coords);
        }
    }

    public void glVertex3d(double d, double d1, double d2) {
        if (canUseImmediateMode()) {
            GL11.glVertex3d(d, d1, d2);
        }
    }

    public void glVertex3dv(double[] coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex3dv(coords);
        }
    }

    public void glVertex3dv(DoubleBuffer coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex3dv(coords);
        }
    }

    public void glVertex3s(short i, short i1, short i2) {
        if (canUseImmediateMode()) {
            GL11.glVertex3s(i, i1, i2);
        }
    }

    public void glVertex3sv(short[] coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex3sv(coords);
        }
    }

    public void glVertex3sv(ShortBuffer coords) {
        if (canUseImmediateMode()) {
            GL11.glVertex3sv(coords);
        }
    }

    public void glColor3b(byte var0, byte var1, byte var2) {
        if (canUseImmediateMode()) {
            GL11.glColor3b(var0, var1, var2);
        }
    }

    public void glColor3s(short var0, short var1, short var2) {
        if (canUseImmediateMode()) {
            GL11.glColor3s(var0, var1, var2);
        }
    }

    public void glColor3i(int var0, int var1, int var2) {
        if (canUseImmediateMode()) {
            GL11.glColor3i(var0, var1, var2);
        }
    }

    public void glColor3f(float var0, float var1, float var2) {
        if (canUseImmediateMode()) {
            GL11.glColor3f(var0, var1, var2);
        }
    }

    public void glColor3d(double var0, double var1, double var2) {
        if (canUseImmediateMode()) {
            GL11.glColor3d(var0, var1, var2);
        }
    }

    public void glColor3ub(byte var0, byte var1, byte var2) {
        if (canUseImmediateMode()) {
            GL11.glColor3ub(var0, var1, var2);
        }
    }

    public void glColor3us(short var0, short var1, short var2) {
        if (canUseImmediateMode()) {
            GL11.glColor3us(var0, var1, var2);
        }
    }

    public void glColor3ui(int var0, int var1, int var2) {
        if (canUseImmediateMode()) {
            GL11.glColor3ui(var0, var1, var2);
        }
    }

    public void glColor3bv(ByteBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor3bv(v);
        }
    }

    public void glColor3sv(ShortBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor3sv(v);
        }
    }

    public void glColor3iv(IntBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor3iv(v);
        }
    }

    public void glColor3fv(FloatBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor3fv(v);
        }
    }

    public void glColor3dv(DoubleBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor3dv(v);
        }
    }

    public void glColor3ubv(ByteBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor3ubv(v);
        }
    }

    public void glColor3usv(ShortBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor3usv(v);
        }
    }

    public void glColor3uiv(IntBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor3uiv(v);
        }
    }

    public void glColor4b(byte var0, byte var1, byte var2, byte var3) {
        if (canUseImmediateMode()) {
            GL11.glColor4b(var0, var1, var2, var3);
        }
    }

    public void glColor4s(short var0, short var1, short var2, short var3) {
        if (canUseImmediateMode()) {
            GL11.glColor4s(var0, var1, var2, var3);
        }
    }

    public void glColor4i(int var0, int var1, int var2, int var3) {
        if (canUseImmediateMode()) {
            GL11.glColor4i(var0, var1, var2, var3);
        }
    }

    public void glColor4f(float var0, float var1, float var2, float var3) {
        if (canUseImmediateMode()) {
            GL11.glColor4f(var0, var1, var2, var3);
        }
    }

    public void glColor4d(double var0, double var1, double var2, double var3) {
        if (canUseImmediateMode()) {
            GL11.glColor4d(var0, var1, var2, var3);
        }
    }

    public void glColor4ub(byte var0, byte var1, byte var2, byte var3) {
        if (canUseImmediateMode()) {
            GL11.glColor4ub(var0, var1, var2, var3);
        }
    }

    public void glColor4us(short var0, short var1, short var2, short var3) {
        if (canUseImmediateMode()) {
            GL11.glColor4us(var0, var1, var2, var3);
        }
    }

    public void glColor4ui(int var0, int var1, int var2, int var3) {
        if (canUseImmediateMode()) {
            GL11.glColor4ui(var0, var1, var2, var3);
        }
    }

    public void glColor4bv(ByteBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor4bv(v);
        }
    }

    public void glColor4sv(ShortBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor4sv(v);
        }
    }

    public void glColor4iv(IntBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor4iv(v);
        }
    }

    public void glColor4fv(FloatBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor4fv(v);
        }
    }

    public void glColor4dv(DoubleBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor4dv(v);
        }
    }

    public void glColor4ubv(ByteBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor4ubv(v);
        }
    }

    public void glColor4usv(ShortBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor4usv(v);
        }
    }

    public void glColor4uiv(IntBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glColor4uiv(v);
        }
    }

    public void glNormal3f(float x, float y, float z) {
        if (canUseImmediateMode()) {
            GL11.glNormal3f(x, y, z);
        }
    }

    public void glNormal3d(double x, double y, double z) {
        if (canUseImmediateMode()) {
            GL11.glNormal3d(x, y, z);
        }
    }

    public void glNormal3i(int x, int y, int z) {
        if (canUseImmediateMode()) {
            GL11.glNormal3i(x, y, z);
        }
    }

    public void glNormal3s(short x, short y, short z) {
        if (canUseImmediateMode()) {
            GL11.glNormal3s(x, y, z);
        }
    }

    public void glNormal3b(byte x, byte y, byte z) {
        if (canUseImmediateMode()) {
            GL11.glNormal3b(x, y, z);
        }
    }

    public void glNormal3fv(FloatBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glNormal3fv(v);
        }
    }

    public void glTexCoord2f(float s, float t) {
        if (canUseImmediateMode()) {
            GL11.glTexCoord2f(s, t);
        }
    }

    public void glTexCoord2d(double s, double t) {
        if (canUseImmediateMode()) {
            GL11.glTexCoord2d(s, t);
        }
    }

    public void glTexCoord2i(int s, int t) {
        if (canUseImmediateMode()) {
            GL11.glTexCoord2i(s, t);
        }
    }

    public void glTexCoord2s(short s, short t) {
        if (canUseImmediateMode()) {
            GL11.glTexCoord2s(s, t);
        }
    }

    public void glTexCoord2f(FloatBuffer v) {
        if (canUseImmediateMode()) {
            GL11.glTexCoord2fv(v);
        }
    }

    public void glEdgeFlag(boolean flag) {
        if (canUseImmediateMode()) {
            GL11.glEdgeFlag(flag);
        }
    }

    public void glEdgeFlagv(ByteBuffer buffer) {
        if (canUseImmediateMode()) {
            GL11.glEdgeFlagv(buffer);
        }
    }

    public void glIndexi(int index) {
        if (canUseImmediateMode()) {
            GL11.glIndexi(index);
        }
    }

    public void glIndexf(float index) {
        if (canUseImmediateMode()) {
            GL11.glIndexf(index);
        }
    }

    public void glIndexd(double index) {
        if (canUseImmediateMode()) {
            GL11.glIndexd(index);
        }
    }

    public void glRasterPos2f(float x, float y) {
        if (canUseImmediateMode()) {
            GL11.glRasterPos2f(x, y);
        }
    }

    public void glRasterPos3f(float x, float y, float z) {
        if (canUseImmediateMode()) {
            GL11.glRasterPos3f(x, y, z);
        }
    }

    public void glRasterPos4f(float x, float y, float z, float w) {
        if (canUseImmediateMode()) {
            GL11.glRasterPos4f(x, y, z, w);
        }
    }

    public void glRectf(float x1, float y1, float x2, float y2) {
        if (canUseImmediateMode()) {
            GL11.glRectf(x1, y1, x2, y2);
        }
    }

    public void glRectd(double x1, double y1, double x2, double y2) {
        if (canUseImmediateMode()) {
            GL11.glRectd(x1, y1, x2, y2);
        }
    }

    public void glSecondaryColor3b(byte r, byte g, byte b) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3b(r, g, b);
        }
    }

    public void glSecondaryColor3s(short r, short g, short b) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3s(r, g, b);
        }
    }

    public void glSecondaryColor3i(int r, int g, int b) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3i(r, g, b);
        }
    }

    public void glSecondaryColor3f(float r, float g, float b) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3f(r, g, b);
        }
    }

    public void glSecondaryColor3d(double r, double g, double b) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3d(r, g, b);
        }
    }

    public void glSecondaryColor3ub(byte r, byte g, byte b) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3ub(r, g, b);
        }
    }

    public void glSecondaryColor3us(short r, short g, short b) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3us(r, g, b);
        }
    }

    public void glSecondaryColor3ui(int r, int g, int b) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3ui(r, g, b);
        }
    }

    public void glSecondaryColor3fv(FloatBuffer v) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3fv(v);
        }
    }

    public void glSecondaryColor3dv(DoubleBuffer v) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3dv(v);
        }
    }

    public void glSecondaryColor3iv(IntBuffer v) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3iv(v);
        }
    }

    public void glSecondaryColor3sv(ShortBuffer v) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3sv(v);
        }
    }

    public void glSecondaryColor3bv(ByteBuffer v) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3bv(v);
        }
    }

    public void glSecondaryColor3ubv(ByteBuffer v) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3ubv(v);
        }
    }

    public void glSecondaryColor3usv(ShortBuffer v) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3usv(v);
        }
    }

    public void glSecondaryColor3uiv(IntBuffer v) {
        if (canUseImmediateMode()) {
            GL14.glSecondaryColor3uiv(v);
        }
    }

    public void glFogCoordf(float coord) {
        if (canUseImmediateMode()) {
            GL14.glFogCoordf(coord);
        }
    }

    public void glFogCoordd(double coord) {
        if (canUseImmediateMode()) {
            GL14.glFogCoordd(coord);
        }
    }

    public void glFogCoordfv(FloatBuffer v) {
        if (canUseImmediateMode()) {
            GL14.glFogCoordfv(v);
        }
    }

    public void glFogCoorddv(DoubleBuffer v) {
        if (canUseImmediateMode()) {
            GL14.glFogCoorddv(v);
        }
    }

    public void glMultiTexCoord1f(int target, float s) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord1f(target, s);
        }
    }

    public void glMultiTexCoord1d(int target, double s) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord1d(target, s);
        }
    }

    public void glMultiTexCoord1i(int target, int s) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord1i(target, s);
        }
    }

    public void glMultiTexCoord1s(int target, short s) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord1s(target, s);
        }
    }

    public void glMultiTexCoord2f(int target, float s, float t) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord2f(target, s, t);
        }
    }

    public void glMultiTexCoord2d(int target, double s, double t) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord2d(target, s, t);
        }
    }

    public void glMultiTexCoord2i(int target, int s, int t) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord2i(target, s, t);
        }
    }

    public void glMultiTexCoord2s(int target, short s, short t) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord2s(target, s, t);
        }
    }

    public void glMultiTexCoord3f(int target, float s, float t, float r) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord3f(target, s, t, r);
        }
    }

    public void glMultiTexCoord3d(int target, double s, double t, double r) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord3d(target, s, t, r);
        }
    }

    public void glMultiTexCoord3i(int target, int s, int t, int r) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord3i(target, s, t, r);
        }
    }

    public void glMultiTexCoord3s(int target, short s, short t, short r) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord3s(target, s, t, r);
        }
    }

    public void glMultiTexCoord4f(int target, float s, float t, float r, float q) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord4f(target, s, t, r, q);
        }
    }

    public void glMultiTexCoord4d(int target, double s, double t, double r, double q) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord4d(target, s, t, r, q);
        }
    }

    public void glMultiTexCoord4i(int target, int s, int t, int r, int q) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord4i(target, s, t, r, q);
        }
    }

    public void glMultiTexCoord4s(int target, short s, short t, short r, short q) {
        if (canUseImmediateMode()) {
            GL13.glMultiTexCoord4s(target, s, t, r, q);
        }
    }

    public void glMatrixMode(int i) {
        if (canUseImmediateMode()) {
            GL11.glMatrixMode(i);
        }
    }

    public void glLoadIdentity() {
        if (canUseImmediateMode()) {
            GL11.glLoadIdentity();
        }
    }

    private boolean canUseImmediateMode() {
        if (profileCheck) {
            return true;
        } else {
            if (RENDER_DEBUG_THROW_ERROR) {
                throw new RenderDebugException("This frame does not support OpenGL Immediate Mode, and this function cannot be used.");
            } else {
                return false;
            }
        }
    }

    public boolean isProfileCheck() {
        return profileCheck;
    }
}
