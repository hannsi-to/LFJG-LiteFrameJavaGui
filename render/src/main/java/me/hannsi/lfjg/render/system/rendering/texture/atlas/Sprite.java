package me.hannsi.lfjg.render.system.rendering.texture.atlas;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.core.utils.math.MathHelper.random;

public class Sprite {
    public final int width;
    public final int height;
    public final SpriteMemoryPolicy memoryPolicy;
    public ByteBuffer data;
    public boolean commited;
    public int offsetX;
    public int offsetY;
    public int offsetZ;

    public Sprite(int width, int height, ByteBuffer data, SpriteMemoryPolicy memoryPolicy) {
        this.width = width;
        this.height = height;
        this.data = data;
        this.memoryPolicy = memoryPolicy;
        this.commited = false;
    }

    public static Sprite createRandomColor(int width, int height, boolean doAlpha, SpriteMemoryPolicy memoryPolicy) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

        byte r;
        byte g;
        byte b;
        byte a = (byte) 255;

        r = (byte) (random() * 255);
        g = (byte) (random() * 255);
        b = (byte) (random() * 255);
        if (doAlpha) {
            a = (byte) (random() * 255);
        }

        for (int i = 0; i < width * height; i++) {
            buffer.put(r).put(g).put(b).put(a);
        }
        buffer.flip();

        return new Sprite(width, height, buffer, memoryPolicy);
    }

    public Sprite setCommited(boolean commited) {
        this.commited = commited;

        return this;
    }
}
