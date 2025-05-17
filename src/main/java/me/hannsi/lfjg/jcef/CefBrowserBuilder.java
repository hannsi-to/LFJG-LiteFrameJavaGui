package me.hannsi.lfjg.jcef;

import org.cef.CefBrowserSettings;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefRequestContext;

public class CefBrowserBuilder {
    private final CefClient cefClient;
    private String url;
    private boolean offScreenRendered;
    private boolean transparent;
    private CefRequestContext cefRequestContext;
    private CefBrowserSettings cefBrowserSettings;

    private CefBrowserBuilder(CefClient cefClient) {
        this.cefClient = cefClient;
    }

    public static CefBrowserBuilder builderCreate(CefClient cefClient) {
        return new CefBrowserBuilder(cefClient);
    }

    public CefBrowserBuilder transparent(boolean transparent) {
        this.transparent = transparent;
        return this;
    }

    public CefBrowserBuilder offScreenRendered(boolean offScreenRendered) {
        this.offScreenRendered = offScreenRendered;
        return this;
    }

    public CefBrowserBuilder cefRequestContext(CefRequestContext cefRequestContext) {
        this.cefRequestContext = cefRequestContext;
        return this;
    }

    public CefBrowserBuilder cefBrowserSettings(CefBrowserSettings cefBrowserSettings) {
        this.cefBrowserSettings = cefBrowserSettings;
        return this;
    }

    public CefBrowserBuilder url(String url) {
        this.url = url;
        return this;
    }

    public CefBrowser build() {
        return createCefBrowser();
    }

    private CefBrowser createCefBrowser() {
        return cefClient.createBrowser(url, offScreenRendered, transparent, cefRequestContext, cefBrowserSettings);
    }
}
