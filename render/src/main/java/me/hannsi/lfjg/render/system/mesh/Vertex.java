package me.hannsi.lfjg.render.system.mesh;

public class Vertex {
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

    public Vertex copy() {
        return new Vertex(x, y, z, red, green, blue, alpha, u, v, normalsX, normalsY, normalsZ);
    }
}
