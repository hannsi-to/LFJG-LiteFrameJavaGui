package me.hannsi.lfjg.jcef;

import me.hannsi.lfjg.debug.debug.logger.LogGenerator;
import me.hannsi.lfjg.debug.debug.system.DebugLevel;
import me.hannsi.lfjg.debug.debug.system.DebugLog;
import me.hannsi.lfjg.jcef.exceptions.JCefInitializationException;
import me.hannsi.lfjg.jcef.handler.MessageRouterHandler;
import me.hannsi.lfjg.jcef.handler.MessageRouterHandlerEx;
import me.hannsi.lfjg.jcef.util.JCefUtil;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.test.jcef.util.DataUri;
import org.cef.CefApp;
import org.cef.CefApp.CefVersion;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;
import org.cef.handler.CefLifeSpanHandlerAdapter;
import org.cef.handler.CefLoadHandlerAdapter;
import org.cef.network.CefRequest;

import javax.swing.*;
import java.awt.*;

public class JCef {
    public static String DEFAULT_BROWSER_URL = "https://www.google.com";
    private static int browserCount = 0;

    private CefClient client;
    private CefBrowser browser;

    private final String[] args;
    private final boolean offScreenRendering;
    private final boolean transparentPainting;
    private final boolean createImmediately;
    private final Color backgroundColor;
    private String errorMessage;
    private boolean browserFocus;

    private JCef(String[] args, boolean offScreenRendering, boolean transparentPainting, boolean createImmediately, Color backgroundColor) {
        this.args = args;
        this.offScreenRendering = offScreenRendering;
        this.transparentPainting = transparentPainting;
        this.createImmediately = createImmediately;
        this.backgroundColor = backgroundColor;
        this.errorMessage = "";
    }

    public static void main(String... args) {
        JCef jCef = JCef.initJCef(args, false, false, false, Color.WHITE);
    }

    public static JCef initJCef(String[] args, boolean offScreenRendering, boolean transparentPainting, boolean createImmediately, Color backgroundColor) {
        JCef jcef = new JCef(args, offScreenRendering, transparentPainting, createImmediately, backgroundColor);

        if (!CefApp.startup(args)) {
            throw new JCefInitializationException("Startup initialization failed");
        }

        jcef.initCef();

        return jcef;
    }

    public void initCef() {
        CefApp cefApp;
        if (CefApp.getState() != CefApp.CefAppState.INITIALIZED) {
            CefSettings settings = new CefSettings();
            settings.windowless_rendering_enabled = offScreenRendering;
            settings.background_color = JCefUtil.color(settings, backgroundColor);

            cefApp = CefApp.getInstance(args, settings);

            CefVersion version = cefApp.getVersion();
            new LogGenerator(
                    "JavaChromiumEmbedFramework Debug Message",
                    "JavaChromiumEmbedFramework: " + version.getJcefVersion(),
                    "ChromiumEmbedFramework: " + version.getCefVersion(),
                    "Chrome: " + version.getChromeVersion()
            ).logging(DebugLevel.INFO);
        } else {
            cefApp = CefApp.getInstance();
        }

        client = cefApp.createClient();

        CefMessageRouter msgRouter = CefMessageRouter.create();
        msgRouter.addHandler(new MessageRouterHandler(), true);
        msgRouter.addHandler(new MessageRouterHandlerEx(client), false);
        client.addMessageRouter(msgRouter);

        client.addDisplayHandler(new CefDisplayHandlerAdapter() {
            @Override
            public void onAddressChange(CefBrowser cefBrowser, CefFrame cefFrame, String s) {
                super.onAddressChange(cefBrowser, cefFrame, s);
            }

            @Override
            public void onTitleChange(CefBrowser cefBrowser, String s) {
                super.onTitleChange(cefBrowser, s);
            }

            @Override
            public boolean onTooltip(CefBrowser cefBrowser, String s) {
                return super.onTooltip(cefBrowser, s);
            }

            @Override
            public void onStatusMessage(CefBrowser cefBrowser, String s) {
                super.onStatusMessage(cefBrowser, s);
            }

            @Override
            public boolean onConsoleMessage(CefBrowser cefBrowser, CefSettings.LogSeverity logSeverity, String s, String s1, int i) {
                return super.onConsoleMessage(cefBrowser, logSeverity, s, s1, i);
            }

            @Override
            public boolean onCursorChange(CefBrowser cefBrowser, int i) {
                return super.onCursorChange(cefBrowser, i);
            }
        });

        client.addLoadHandler(new CefLoadHandlerAdapter() {
            @Override
            public void onLoadingStateChange(CefBrowser cefBrowser, boolean isLoading, boolean canGoBack, boolean canGoForward) {
                if (!isLoading && !errorMessage.isEmpty()) {
                    cefBrowser.loadURL(DataUri.create("text/html", errorMessage));
                    errorMessage = "";
                }
            }

            @Override
            public void onLoadStart(CefBrowser cefBrowser, CefFrame cefFrame, CefRequest.TransitionType transitionType) {
                super.onLoadStart(cefBrowser, cefFrame, transitionType);
            }

            @Override
            public void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int i) {
                super.onLoadEnd(cefBrowser, cefFrame, i);
            }

            @Override
            public void onLoadError(CefBrowser cefBrowser, CefFrame cefFrame, ErrorCode errorCode, String errorText, String failedUrl) {
                if (errorCode != ErrorCode.ERR_NONE && errorCode != ErrorCode.ERR_ABORTED) {
                    errorMessage = "<html><head>";
                    errorMessage += "<title>Error while loading</title>";
                    errorMessage += "</head><body>";
                    errorMessage += "<h1>" + errorCode + "</h1>";
                    errorMessage += "<h3>Failed to load " + failedUrl + "</h3>";
                    errorMessage += "<p>" + (errorText == null ? "" : errorText) + "</p>";
                    errorMessage += "</body></html>";
                    cefBrowser.stopLoad();
                }
            }
        });

        CefBrowser cefBrowser = client.createBrowser(DEFAULT_BROWSER_URL, offScreenRendering, transparentPainting, null);
        setBrowser(cefBrowser);

        client.addFocusHandler(new CefFocusHandlerAdapter() {
            @Override
            public void onGotFocus(CefBrowser browser) {
                if (browserFocus) {
                    return;
                }
                browserFocus = true;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                browser.setFocus(true);
            }

            @Override
            public void onTakeFocus(CefBrowser browser, boolean next) {
                browserFocus = false;
            }
        });

        if (createImmediately) {
            browser.createImmediately();
        }

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(browser.getUIComponent(), BorderLayout.CENTER);
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(contentPanel);
        jFrame.setSize(800, 600);
        jFrame.setVisible(true);
    }

    public void setBrowser(CefBrowser cefBrowser) {
        if (browser == null) {
            browser = cefBrowser;
        }

        browser.getClient().removeLifeSpanHandler();
        browser.getClient().addLifeSpanHandler(new CefLifeSpanHandlerAdapter() {
            @Override
            public boolean onBeforePopup(CefBrowser cefBrowser, CefFrame cefFrame, String s, String s1) {
                return super.onBeforePopup(cefBrowser, cefFrame, s, s1);
            }

            @Override
            public void onAfterCreated(CefBrowser cefBrowser) {
                DebugLog.debug(getClass(), "Browser.onAfterCreated id: " + cefBrowser.getIdentifier());
                browserCount++;
            }

            @Override
            public void onAfterParentChanged(CefBrowser cefBrowser) {
                super.onAfterParentChanged(cefBrowser);
            }

            @Override
            public boolean doClose(CefBrowser cefBrowser) {
                boolean result = browser.doClose();
                DebugLog.debug(getClass(), "Browser.onClose id: " + cefBrowser.getIdentifier() + " result: " + result);
                return result;
            }

            @Override
            public void onBeforeClose(CefBrowser cefBrowser) {
                DebugLog.debug(getClass(), "Browser.onBeforeClose id: " + cefBrowser.getIdentifier());
                if (--browserCount == 0) {
                    CefApp.getInstance().dispose();
                    DebugLog.debug(getClass(), "Browser.onBeforeClose.dispose id: " + cefBrowser.getIdentifier());
                }
            }
        });
    }
}
