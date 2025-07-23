package me.hannsi.lfjg.jcef;

import org.cef.CefClient;
import org.cef.handler.CefRenderHandler;
import org.cef.handler.CefRenderHandlerAdapter;

public class LFJGCefClient extends CefClient {
    private final CefRenderHandlerAdapter renderHandler;

    public LFJGCefClient(CefRenderHandlerAdapter renderHandler) {
        super();
        this.renderHandler = renderHandler;
    }

    @Override
    public CefRenderHandler getRenderHandler() {
        return renderHandler;
    }
}
