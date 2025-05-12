package me.hannsi.lfjg.jcef.handler;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefPaintEvent;
import org.cef.callback.CefDragData;
import org.cef.handler.CefRenderHandler;
import org.cef.handler.CefRenderHandlerAdapter;
import org.cef.handler.CefScreenInfo;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class RenderHandler implements CefRenderHandler {

    @Override
    public Rectangle getViewRect(CefBrowser cefBrowser) {
        return null;
    }

    @Override
    public boolean getScreenInfo(CefBrowser cefBrowser, CefScreenInfo cefScreenInfo) {
        return false;
    }

    @Override
    public Point getScreenPoint(CefBrowser cefBrowser, Point point) {
        return null;
    }

    @Override
    public void onPopupShow(CefBrowser cefBrowser, boolean b) {

    }

    @Override
    public void onPopupSize(CefBrowser cefBrowser, Rectangle rectangle) {

    }

    @Override
    public void onPaint(CefBrowser cefBrowser, boolean b, Rectangle[] rectangles, ByteBuffer byteBuffer, int i, int i1) {

    }

    @Override
    public void addOnPaintListener(Consumer<CefPaintEvent> consumer) {

    }

    @Override
    public void setOnPaintListener(Consumer<CefPaintEvent> consumer) {

    }

    @Override
    public void removeOnPaintListener(Consumer<CefPaintEvent> consumer) {

    }

    @Override
    public boolean onCursorChange(CefBrowser cefBrowser, int i) {
        return false;
    }

    @Override
    public boolean startDragging(CefBrowser cefBrowser, CefDragData cefDragData, int i, int i1, int i2) {
        return false;
    }

    @Override
    public void updateDragCursor(CefBrowser cefBrowser, int i) {

    }
}
