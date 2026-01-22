package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.Cleanup;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class Vertex implements Cleanup {
    public static final long BYTES = 3 * Float.BYTES + 4 * Float.BYTES + 2 * Float.BYTES + 3 * Float.BYTES;
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

    public Vertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, float normalsX, float normalsY, float normalsZ) {
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

    public Vertex replaceXYZ(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    public float[] getPositions() {
        return new float[]{x, y, z};
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

    public Vertex copy() {
        return new Vertex(x, y, z, red, green, blue, alpha, u, v, normalsX, normalsY, normalsZ);
    }

    @Override
    public boolean cleanup(CleanupEvent event) {
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

        return event.debug(Vertex.class, new CleanupEvent.CleanupData("Vertex")
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
}
