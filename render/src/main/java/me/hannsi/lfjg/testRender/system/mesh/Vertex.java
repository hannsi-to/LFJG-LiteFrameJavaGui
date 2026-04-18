package me.hannsi.lfjg.testRender.system.mesh;

import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.Cleanup;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.testRender.uitl.memory.MemoryFrameArena;
import me.hannsi.lfjg.testRender.uitl.memory.ObjectMemory;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.VERTEX_DEFAULT_COLOR;

public class Vertex implements ObjectMemory, Cleanup {
    public static final int BYTES = 3 * Float.BYTES + 4 * Float.BYTES + 2 * Float.BYTES + 3 * Float.BYTES;
    public boolean nullVertex;
    public float x;
    public float y;
    public float z;
    public float red;
    public float green;
    public float blue;
    public float alpha;
    public float u;
    public float v;
    public float normalsX;
    public float normalsY;
    public float normalsZ;

    public Vertex(boolean nullVertex, float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, float normalsX, float normalsY, float normalsZ) {
        this.nullVertex = nullVertex;
        this.x = x;
        this.y = y;
        this.z = z;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.u = u;
        this.v = v;
        this.normalsX = normalsX;
        this.normalsY = normalsY;
        this.normalsZ = normalsZ;
    }

    public Vertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, float normalsX, float normalsY, float normalsZ) {
        this(false, x, y, z, red, green, blue, alpha, u, v, normalsX, normalsY, normalsZ);
    }

    public Vertex(float x, float y, float z, Color color, float u, float v, float normalsX, float normalsY, float normalsZ) {
        this(x, y, z, color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF(), u, v, normalsX, normalsY, normalsZ);
    }

    public Vertex(float x, float y, float z, Color color, float u, float v) {
        this(x, y, z, color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF(), u, v, 0, 0, 0);
    }

    public Vertex(float x, float y, float z, Color color) {
        this(x, y, z, color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF(), 0, 0, 0, 0, 0);
    }

    public Vertex(float x, float y, float z, float red, float green, float blue, float alpha) {
        this(x, y, z, red, green, blue, alpha, 0, 0, 0, 0, 0);
    }

    public Vertex(float x, float y, float z) {
        this(x, y, z, VERTEX_DEFAULT_COLOR, 0, 0, 0, 0, 0);
    }

    public Vertex(float x, float y, Color color) {
        this(x, y, 0, color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF());
    }

    public Vertex(float x, float y, float red, float green, float blue, float alpha) {
        this(x, y, 0, red, green, blue, alpha);
    }

    public Vertex(float x, float y) {
        this(x, y, 0);
    }

    public Vertex() {
        this(true, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    @Override
    public void putMemory(MemoryFrameArena arena) {
        f(arena, x)
                .f(arena, y)
                .f(arena, z)
                .f(arena, red)
                .f(arena, green)
                .f(arena, blue)
                .f(arena, alpha)
                .f(arena, u)
                .f(arena, v)
                .f(arena, normalsX)
                .f(arena, normalsY)
                .f(arena, normalsZ);
    }

    @Override
    public int getBytes() {
        return BYTES;
    }

    private Vertex f(MemoryFrameArena arena, float value) {
        long address = arena.alloc(Float.BYTES, Float.BYTES);
        UNSAFE.putFloat(address, value);
        return this;
    }

    public Vertex add(Vertex other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        this.red += other.red;
        this.green += other.green;
        this.blue += other.blue;
        this.alpha += other.alpha;
        this.u += other.u;
        this.v += other.v;
        this.normalsX += other.normalsX;
        this.normalsY += other.normalsY;
        this.normalsZ += other.normalsZ;

        return this;
    }

    public Vertex subtract(Vertex other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        this.red -= other.red;
        this.green -= other.green;
        this.blue -= other.blue;
        this.alpha -= other.alpha;
        this.u -= other.u;
        this.v -= other.v;
        this.normalsX -= other.normalsX;
        this.normalsY -= other.normalsY;
        this.normalsZ -= other.normalsZ;

        return this;
    }

    public Vertex replaceXYZ(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    public Vertex moveXYZ(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

    public Vertex replaceColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;

        return this;
    }

    public Vertex replaceColor(Color color) {
        this.red = color.getRedF();
        this.green = color.getGreenF();
        this.blue = color.getBlueF();
        this.alpha = color.getAlphaF();

        return this;
    }

    public Vertex replaceUV(float u, float v) {
        this.u = u;
        this.v = v;

        return this;
    }

    public Vertex replaceNormal(float normalsX, float normalsY, float normalsZ) {
        this.normalsX = normalsX;
        this.normalsY = normalsY;
        this.normalsZ = normalsZ;

        return this;
    }

    public boolean isNullVertex() {
        return nullVertex;
    }

    public Vertex setNullVertex(boolean nullVertex) {
        this.nullVertex = nullVertex;

        return this;
    }

    public float[] getPositions() {
        return new float[]{x, y, z};
    }

    public Color getColor() {
        return Color.of(red, green, blue, alpha);
    }

    public float[] getColors() {
        return new float[]{red, green, blue, alpha};
    }

    public float[] getTextures() {
        return new float[]{u, v};
    }

    public float[] getNormals() {
        return new float[]{normalsX, normalsY, normalsZ};
    }

    public float[] toArray() {
        return new float[]{x, y, z, red, green, blue, alpha, u, v, normalsX, normalsY, normalsZ};
    }

    public FloatBuffer toBuffer() {
        return BufferUtils.createFloatBuffer(12).put(toArray()).flip();
    }

    public Vector2f toVector2f() {
        return new Vector2f(x, y);
    }

    public Vertex copy() {
        return new Vertex(x, y, z, red, green, blue, alpha, u, v, normalsX, normalsY, normalsZ);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Vertex other = (Vertex) obj;

        return Boolean.compare(other.nullVertex, nullVertex) == 0 &&
                Float.compare(other.x, x) == 0 &&
                Float.compare(other.y, y) == 0 &&
                Float.compare(other.z, z) == 0 &&
                Float.compare(other.red, red) == 0 &&
                Float.compare(other.green, green) == 0 &&
                Float.compare(other.blue, blue) == 0 &&
                Float.compare(other.alpha, alpha) == 0 &&
                Float.compare(other.u, u) == 0 &&
                Float.compare(other.v, v) == 0 &&
                Float.compare(other.normalsX, normalsX) == 0 &&
                Float.compare(other.normalsY, normalsY) == 0 &&
                Float.compare(other.normalsZ, normalsZ) == 0;
    }

    @Override
    public int hashCode() {
        int result = Boolean.hashCode(nullVertex);
        result = 31 * result + Float.hashCode(x);
        result = 31 * result + Float.hashCode(y);
        result = 31 * result + Float.hashCode(z);
        result = 31 * result + Float.hashCode(red);
        result = 31 * result + Float.hashCode(green);
        result = 31 * result + Float.hashCode(blue);
        result = 31 * result + Float.hashCode(alpha);
        result = 31 * result + Float.hashCode(u);
        result = 31 * result + Float.hashCode(v);
        result = 31 * result + Float.hashCode(normalsX);
        result = 31 * result + Float.hashCode(normalsY);
        result = 31 * result + Float.hashCode(normalsZ);

        return result;
    }

    @Override
    public String toString() {
        return "Vertex{nullVertex=(" + nullVertex + ") pos=(" + x + ", " + y + ", " + z + ") color=(" + red + ", " + green + ", " + blue + ", " + alpha + ") uv=(" + u + ", " + v + ") normal=(" + normalsX + ", " + normalsY + ", " + normalsZ + ")}";
    }

    @Override
    public boolean cleanup(CleanupEvent event) {
        nullVertex = false;
        x = 0;
        y = 0;
        z = 0;
        red = 0;
        green = 0;
        blue = 0;
        alpha = 0;
        u = 0;
        v = 0;
        normalsX = 0;
        normalsY = 0;
        normalsZ = 0;

        return event.debug(Vertex.class, new CleanupEvent.CleanupData(this.getClass())
                .addData("nullVertex", !nullVertex, nullVertex)
                .addData("x", x == 0, x)
                .addData("y", y == 0, y)
                .addData("z", z == 0, z)
                .addData("red", red == 0, red)
                .addData("green", green == 0, green)
                .addData("blue", blue == 0, blue)
                .addData("alpha", alpha == 0, alpha)
                .addData("u", u == 0, u)
                .addData("v", v == 0, v)
                .addData("normalsX", normalsX == 0, normalsX)
                .addData("normalsY", normalsY == 0, normalsY)
                .addData("normalsZ", normalsZ == 0, normalsZ)
        );
    }

    public Vertex setX(float x) {
        this.x = x;

        return this;
    }

    public Vertex setY(float y) {
        this.y = y;

        return this;
    }

    public Vertex setZ(float z) {
        this.z = z;

        return this;
    }

    public Vertex setRed(float red) {
        this.red = red;

        return this;
    }

    public Vertex setGreen(float green) {
        this.green = green;

        return this;
    }

    public Vertex setBlue(float blue) {
        this.blue = blue;

        return this;
    }

    public Vertex setAlpha(float alpha) {
        this.alpha = alpha;

        return this;
    }

    public Vertex setU(float u) {
        this.u = u;

        return this;
    }

    public Vertex setV(float v) {
        this.v = v;

        return this;
    }

    public Vertex setNormalsX(float normalsX) {
        this.normalsX = normalsX;

        return this;
    }

    public Vertex setNormalsY(float normalsY) {
        this.normalsY = normalsY;

        return this;
    }

    public Vertex setNormalsZ(float normalsZ) {
        this.normalsZ = normalsZ;

        return this;
    }
}
