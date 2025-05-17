package me.hannsi.lfjg.jcef.handler;

import me.hannsi.lfjg.utils.graphics.image.ImageCapture;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.type.types.ColorFormatType;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefPaintEvent;
import org.cef.callback.CefNative;
import org.cef.handler.CefRenderHandlerAdapter;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RenderHandler extends CefRenderHandlerAdapter implements CefNative {
    private final Map<String, Long> nativeRefs;
    private ByteBuffer frame;
    private int viewWidth;
    private int viewHeight;

    public RenderHandler(int width, int height) {
        super();

        this.nativeRefs = new HashMap<>();
        this.frame = null;
        this.viewWidth = width;
        this.viewHeight = height;
    }

    @Override
    public Rectangle getViewRect(CefBrowser browser) {
        return new Rectangle(0, 0, viewWidth, viewHeight);
    }

    @Override
    public void onPaint(CefBrowser browser, boolean popup, Rectangle[] dirtyRects, ByteBuffer buffer, int width, int height) {
        this.frame = buffer.flip();
        this.viewWidth = width;
        this.viewHeight = height;

        ImageCapture imageCapture = new ImageCapture(new FileLocation("C:\\Users\\hanns\\idea-project\\LFJG-LiteFrameJavaGui\\log\\png"));
        imageCapture.setColorFormatType(ColorFormatType.RGBA);
        imageCapture.setFlip(false);
        imageCapture.saveImage(buffer, "onPaint");
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

    public ByteBuffer getFrame() {
        return frame;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }
}
