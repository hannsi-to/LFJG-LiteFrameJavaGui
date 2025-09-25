package me.hannsi.lfjg.jcef;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.jcef.exceptions.JCefInitializationException;
import me.hannsi.lfjg.jcef.handler.RenderHandler;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;

import javax.swing.*;
import java.awt.*;

public class JCef {
    public static String DEFAULT_BROWSER_URL = "https://www.google.com";
    private final String[] args;
    private final boolean offScreenRendering;
    private final boolean transparentPainting;
    private final boolean createImmediately;
    private final Color backgroundColor;
    private final RenderHandler renderHandler;
    private final int cefWindowWidth;
    private final int cefWindowHeight;
    private final String errorMessage;
    private final boolean browserFocus;
    private CefClient client;
    private CefBrowser browser;

    private JCef(String[] args, int cefWindowWidth, int cefWindowHeight, boolean offScreenRendering, boolean transparentPainting, boolean createImmediately, Color backgroundColor, RenderHandler renderHandler) {
        this.args = args;
        this.offScreenRendering = offScreenRendering;
        this.transparentPainting = transparentPainting;
        this.createImmediately = createImmediately;
        this.backgroundColor = backgroundColor;
        this.renderHandler = renderHandler;
        this.errorMessage = "";
        this.browserFocus = false;
        this.cefWindowWidth = cefWindowWidth;
        this.cefWindowHeight = cefWindowHeight;
    }

    public static JCef initJCef(String[] args, int browserWidth, int browserHeight, boolean offScreenRendering, boolean transparentPainting, boolean createImmediately, Color backgroundColor, RenderHandler renderHandler) {
        JCef jcef = new JCef(args, browserWidth, browserHeight, offScreenRendering, transparentPainting, createImmediately, backgroundColor, renderHandler);

        if (!CefApp.startup(args)) {
            throw new JCefInitializationException("Startup initialization failed");
        }

        jcef.initCef();

        return jcef;
    }

    public void initCef() {
        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = offScreenRendering;

        CefApp cefApp = CefApp.getInstance(settings);
        client = cefApp.createLFJGCefClient(renderHandler);
        client.addMessageRouter(CefMessageRouter.create());

        browser = client.createBrowser(DEFAULT_BROWSER_URL, offScreenRendering, transparentPainting);

        if (createImmediately) {
            browser.createImmediately();
        }

        if (!offScreenRendering) {
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(browser.getUIComponent(), BorderLayout.CENTER);
            JFrame jFrame = new JFrame();
            jFrame.setContentPane(contentPanel);
            jFrame.setSize(cefWindowWidth, cefWindowHeight);
            jFrame.setVisible(true);
        }
    }
}
