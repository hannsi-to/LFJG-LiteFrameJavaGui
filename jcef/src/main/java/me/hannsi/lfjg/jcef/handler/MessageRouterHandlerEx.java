package me.hannsi.lfjg.jcef.handler;

import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

public class MessageRouterHandlerEx extends CefMessageRouterHandlerAdapter {
    private final CefClient client;
    private final CefMessageRouter.CefMessageRouterConfig config = new CefMessageRouter.CefMessageRouterConfig("myQuery", "myQueryAbort");
    private CefMessageRouter router = null;

    public MessageRouterHandlerEx(CefClient client) {
        this.client = client;
    }

    @Override
    public boolean onQuery(CefBrowser cefBrowser, CefFrame cefFrame, long queryId, String request, boolean persistent, CefQueryCallback cefQueryCallback) {
        if (request.startsWith("hasExtension")) {
            if (router != null) {
                cefQueryCallback.success("");
            } else {
                cefQueryCallback.failure(0, "");
            }
        } else if (request.startsWith("enableExt")) {
            if (router != null) {
                cefQueryCallback.failure(-1, "Already enabled");
            } else {
                router = CefMessageRouter.create(config, new JavaVersionMessageRouter());
                client.addMessageRouter(router);
                cefQueryCallback.success("");
            }
        } else if (request.startsWith("disableExt")) {
            if (router == null) {
                cefQueryCallback.failure(-2, "Already disabled");
            } else {
                client.removeMessageRouter(router);
                router.dispose();
                router = null;
                cefQueryCallback.success("");
            }
        } else {
            return false;
        }
        return true;
    }

    private static class JavaVersionMessageRouter extends CefMessageRouterHandlerAdapter {
        @Override
        public boolean onQuery(CefBrowser cefBrowser, CefFrame cefFrame, long queryId, String request, boolean persistent, CefQueryCallback cefQueryCallback) {
            if (request.startsWith("jcefJava")) {
                cefQueryCallback.success(System.getProperty("java.version"));
                return true;
            }
            return false;
        }
    }
}
