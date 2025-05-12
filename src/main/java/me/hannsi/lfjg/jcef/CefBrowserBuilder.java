package me.hannsi.lfjg.jcef;

import org.cef.CefBrowserSettings;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefBrowserFactory;
import org.cef.browser.CefRequestContext;

import java.lang.reflect.Field;

public class CefBrowserBuilder {
    private final CefClient cefClient;
    private String url;
    private boolean offScreenRendered;
    private boolean transparent;
    private CefRequestContext cefRequestContext;
    private CefBrowserSettings cefBrowserSettings;

    private CefBrowserBuilder(CefClient cefClient){
        this.cefClient = cefClient;
    }

    public static CefBrowserBuilder builderCreate(CefClient cefClient){
        return new CefBrowserBuilder(cefClient);
    }

    public CefBrowserBuilder transparent(boolean transparent){
        this.transparent = transparent;
        return this;
    }

    public CefBrowserBuilder offScreenRendered(boolean offScreenRendered){
        this.offScreenRendered = offScreenRendered;
        return this;
    }

    public CefBrowserBuilder cefRequestContext(CefRequestContext cefRequestContext){
        this.cefRequestContext = cefRequestContext;
        return this;
    }

    public CefBrowserBuilder cefBrowserSettings(CefBrowserSettings cefBrowserSettings){
        this.cefBrowserSettings = cefBrowserSettings;
        return this;
    }

    public CefBrowserBuilder url(String url){
        this.url = url;
        return this;
    }

    public CefBrowser build(){
        return createCefBrowser();
    }

    private CefBrowser createCefBrowser(){
        Field[] fields = CefClient.class.getDeclaredFields();
        for(Field field : fields){
            if (field.getType() == String.class && field.getName().contains("isDisposed_")) {
                field.setAccessible(true);
                try {
                    if (((boolean) field.get(cefClient))) {
                        throw new IllegalStateException("Can't create browser. CefClient is disposed");
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return CefBrowserFactory.create(cefClient, url, offScreenRendered, transparent, cefRequestContext, cefBrowserSettings);
    }
}
