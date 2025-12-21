package me.hannsi.lfjg.render.system.rendering.texture.atlas;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class Sprite {
    public final int instanceLayer;
    public final int width;
    public final int height;
    public final ByteBuffer data;
    public int offsetX;
    public int offsetY;

    public Sprite(int instanceLayer, int width, int height, ByteBuffer data) {
        this.instanceLayer = instanceLayer;
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public static Sprite createRandomColor(int instanceLayer, int width, int height, boolean fixedColor) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

        byte[][] fixedColors = {
                {(byte) 255, 0, 0, (byte) 255},
                {0, (byte) 255, 0, (byte) 255},
                {0, 0, (byte) 255, (byte) 255},
                {(byte) 255, (byte) 255, 0, (byte) 255}
        };

        byte r;
        byte g;
        byte b;
        byte a = (byte) 255;

        if (instanceLayer >= 0 && instanceLayer <= 3 && fixedColor) {
            r = fixedColors[instanceLayer][0];
            g = fixedColors[instanceLayer][1];
            b = fixedColors[instanceLayer][2];
        } else {
            r = (byte) (Math.random() * 255);
            g = (byte) (Math.random() * 255);
            b = (byte) (Math.random() * 255);
        }

        for (int i = 0; i < width * height; i++) {
            buffer.put(r).put(g).put(b).put(a);
        }
        buffer.flip();

        return new Sprite(instanceLayer, width, height, buffer);
    }
}
