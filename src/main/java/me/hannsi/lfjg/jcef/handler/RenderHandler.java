package me.hannsi.lfjg.jcef.handler;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefPaintEvent;
import org.cef.callback.CefNative;
import org.cef.handler.CefRenderHandlerAdapter;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RenderHandler extends CefRenderHandlerAdapter implements CefNative {
    private final Map<String, Long> nativeRefs = new HashMap<>();

    private volatile ByteBuffer latestFrame;
    private int texWidth, texHeight;
    private volatile boolean frameUpdated = false;
    private int x;
    private int y;
    private int width;
    private int height;

    private int frameCount = 0;
    private long lastTime = System.nanoTime();
    private volatile int currentFPS = 0;

    public RenderHandler(int x, int y, int width, int height) {
        super();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle getViewRect(CefBrowser browser) {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void onPaint(CefBrowser browser, boolean popup, Rectangle[] dirtyRects, ByteBuffer buffer, int width, int height) {
        synchronized (this) {
            if (buffer == null || buffer.limit() == 0) {
                return;
            }

            int size = width * height * 4;

            if (latestFrame == null || latestFrame.capacity() != size) {
                latestFrame = ByteBuffer.allocateDirect(size);
            }

            buffer.rewind();
            latestFrame.clear();
            latestFrame.put(buffer);
            latestFrame.flip();

            texWidth = width;
            texHeight = height;
            frameUpdated = true;
        }

        frameCount++;
        long now = System.nanoTime();
        if (now - lastTime >= 1_000_000_000L) {
            currentFPS = frameCount;
            frameCount = 0;
            lastTime = now;
        }
    }

    @Override
    public void addOnPaintListener(Consumer<CefPaintEvent> listener) {

    }

    @Override
    public void setOnPaintListener(Consumer<CefPaintEvent> listener) {

    }

    @Override
    public void removeOnPaintListener(Consumer<CefPaintEvent> listener) {

    }

    @Override
    public void setNativeRef(String identifer, long nativeRef) {
        nativeRefs.put(identifer, nativeRef);
    }

    @Override
    public long getNativeRef(String identifer) {
        return 0;
    }

    public boolean isFrameUpdated() {
        return frameUpdated;
    }

    public void consumeFrame(IntBuffer outTexWidth, IntBuffer outTexHeight) {
        outTexWidth.put(0, texWidth);
        outTexHeight.put(0, texHeight);
        frameUpdated = false;
    }

    public ByteBuffer getLatestFrame() {
        return latestFrame;
    }

    public int getFPS() {
        return currentFPS;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
