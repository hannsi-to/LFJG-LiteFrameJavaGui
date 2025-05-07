package me.hannsi.lfjg.jcef.handler;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

public class MessageRouterHandler extends CefMessageRouterHandlerAdapter {
    @Override
    public boolean onQuery(CefBrowser cefBrowser, CefFrame cefFrame, long queryId, String request, boolean persistent, CefQueryCallback cefQueryCallback) {
        if (request.indexOf("BindingTest:") == 0) {
            String message = request.substring(12);
            cefQueryCallback.success(new StringBuilder(message).reverse().toString());
            return true;
        }

        return false;
    }
}
