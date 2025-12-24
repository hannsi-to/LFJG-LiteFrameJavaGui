package me.hannsi.lfjg.render.system.rendering.texture.atlas;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class Sprite {
    public final int width;
    public final int height;
    public final ByteBuffer data;
    public boolean commited;
    public int offsetX;
    public int offsetY;
    public int offsetZ;

    public Sprite(int width, int height, ByteBuffer data) {
        this.width = width;
        this.height = height;
        this.data = data;
        this.commited = false;
    }

    public static Sprite createRandomColor(int width, int height) {
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

        r = (byte) (Math.random() * 255);
        g = (byte) (Math.random() * 255);
        b = (byte) (Math.random() * 255);

        for (int i = 0; i < width * height; i++) {
            buffer.put(r).put(g).put(b).put(a);
        }
        buffer.flip();

        return new Sprite(width, height, buffer);
    }

    public Sprite setCommited(boolean commited) {
        this.commited = commited;

        return this;
    }
}
