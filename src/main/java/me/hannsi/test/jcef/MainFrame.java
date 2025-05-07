// Copyright (c) 2013 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package me.hannsi.test.jcef;

import me.hannsi.test.jcef.handler.MessageRouterHandler;
import me.hannsi.test.jcef.handler.MessageRouterHandlerEx;
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
import org.cef.handler.CefLoadHandlerAdapter;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends BrowserFrame {
    private static final long serialVersionUID = -2295538706810864538L;
    private final CefClient client_;
    private final boolean osr_enabled_;
    private final boolean transparent_painting_enabled_;
    private String errorMsg_ = "";
    private boolean browserFocus_ = true;

    public MainFrame(boolean osrEnabled, boolean transparentPaintingEnabled, boolean createImmediately, String[] args) {
        this.osr_enabled_ = osrEnabled;
        this.transparent_painting_enabled_ = transparentPaintingEnabled;

        CefApp myApp;
        if (CefApp.getState() != CefApp.CefAppState.INITIALIZED) {
            // 1) CefApp is the entry point for JCEF. You can pass
            //    application arguments to it, if you want to handle any
            //    chromium or CEF related switches/attributes in
            //    the native world.
            CefSettings settings = new CefSettings();
            settings.windowless_rendering_enabled = osrEnabled;
            // try to load URL "about:blank" to see the background color
            settings.background_color = settings.new ColorType(100, 255, 242, 211);
            myApp = CefApp.getInstance(args, settings);

            CefVersion version = myApp.getVersion();
            System.out.println("Using:\n" + version);

            //    We're registering our own AppHandler because we want to
            //    add an own schemes (search:// and client://) and its corresponding
            //    protocol handlers. So if you enter "search:something on the web", your
            //    search request "something on the web" is forwarded to www.google.com
//            CefApp.addAppHandler(new AppHandler(args));
        } else {
            myApp = CefApp.getInstance();
        }

        //    By calling the method createClient() the native part
        //    of JCEF/CEF will be initialized and an  instance of
        //    CefClient will be created. You can create one to many
        //    instances of CefClient.
        client_ = myApp.createClient();

        // 2) You have the ability to pass different handlers to your
        //    instance of CefClient. Each handler is responsible to
        //    deal with different informations (e.g. keyboard input).
        //
        //    For each handler (with more than one method) adapter
        //    classes exists. So you don't need to override methods
        //    you're not interested in.
//        DownloadDialog downloadDialog = new DownloadDialog(this);
//        client_.addContextMenuHandler(new ContextMenuHandler(this));
//        client_.addDownloadHandler(downloadDialog);
//        client_.addDragHandler(new DragHandler());
//        client_.addJSDialogHandler(new JSDialogHandler());
//        client_.addKeyboardHandler(new KeyboardHandler());
//        client_.addRequestHandler(new RequestHandler(this));

        //    Beside the normal handler instances, we're registering a MessageRouter
        //    as well. That gives us the opportunity to reply to JavaScript method
        //    calls (JavaScript binding). We're using the default configuration, so
        //    that the JavaScript binding methods "cefQuery" and "cefQueryCancel"
        //    are used.
        CefMessageRouter msgRouter = CefMessageRouter.create();
        msgRouter.addHandler(new MessageRouterHandler(), true);
        msgRouter.addHandler(new MessageRouterHandlerEx(client_), false);
        client_.addMessageRouter(msgRouter);

        // 2.1) We're overriding CefDisplayHandler as nested anonymous class
        //      to update our address-field, the title of the panel as well
        //      as for updating the status-bar on the bottom of the browser
        client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
            @Override
            public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
//                control_pane_.setAddress(browser, url);
            }

            @Override
            public void onTitleChange(CefBrowser browser, String title) {
//                setTitle(title);
            }

            @Override
            public void onStatusMessage(CefBrowser browser, String value) {
//                status_panel_.setStatusText(value);
            }
        });

        // 2.2) To disable/enable navigation buttons and to display a prgress bar
        //      which indicates the load state of our website, we're overloading
        //      the CefLoadHandler as nested anonymous class. Beside this, the
        //      load handler is responsible to deal with (load) errors as well.
        //      For example if you navigate to a URL which does not exist, the
        //      browser will show up an error message.
        client_.addLoadHandler(new CefLoadHandlerAdapter() {
            @Override
            public void onLoadingStateChange(CefBrowser browser, boolean isLoading, boolean canGoBack, boolean canGoForward) {
                if (!isLoading && !errorMsg_.isEmpty()) {
                    browser.loadURL(DataUri.create("text/html", errorMsg_));
                    errorMsg_ = "";
                }
            }

            @Override
            public void onLoadError(CefBrowser browser, CefFrame frame, ErrorCode errorCode, String errorText, String failedUrl) {
                if (errorCode != ErrorCode.ERR_NONE && errorCode != ErrorCode.ERR_ABORTED) {
                    errorMsg_ = "<html><head>";
                    errorMsg_ += "<title>Error while loading</title>";
                    errorMsg_ += "</head><body>";
                    errorMsg_ += "<h1>" + errorCode + "</h1>";
                    errorMsg_ += "<h3>Failed to load " + failedUrl + "</h3>";
                    errorMsg_ += "<p>" + (errorText == null ? "" : errorText) + "</p>";
                    errorMsg_ += "</body></html>";
                    browser.stopLoad();
                }
            }
        });

        // Create the browser.
        CefBrowser browser = client_.createBrowser("http://www.google.com", osrEnabled, transparentPaintingEnabled, null);
        setBrowser(browser);

        // Set up the UI for this example implementation.
        JPanel contentPanel = createContentPanel();
//        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Clear focus from the address field when the browser gains focus.
        client_.addFocusHandler(new CefFocusHandlerAdapter() {
            @Override
            public void onGotFocus(CefBrowser browser) {
                if (browserFocus_) {
                    return;
                }
                browserFocus_ = true;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                browser.setFocus(true);

                System.out.println("BrowserFocus: " + browserFocus_);
            }

            @Override
            public void onTakeFocus(CefBrowser browser, boolean next) {
                browserFocus_ = false;

                System.out.println("Take focus");
            }
        });

        if (createImmediately) {
            browser.createImmediately();
        }

        // Add the browser to the UI.
        contentPanel.add(getBrowser().getUIComponent(), BorderLayout.CENTER);

//        MenuBar menuBar = getMenuBar(browser, downloadDialog);
//        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        // Perform startup initialization on platforms that require it.
        if (!CefApp.startup(args)) {
            System.out.println("Startup initialization failed!");
            return;
        }

        // OSR mode is enabled by default on Linux.
        // and disabled by default on Windows and Mac OS X.
        boolean osrEnabledArg = false;
        boolean transparentPaintingEnabledArg = false;
        boolean createImmediately = false;
        for (String arg : args) {
            arg = arg.toLowerCase();
            switch (arg) {
                case "--off-screen-rendering-enabled" -> osrEnabledArg = true;
                case "--transparent-painting-enabled" -> transparentPaintingEnabledArg = true;
                case "--create-immediately" -> createImmediately = true;
            }
        }

        System.out.println("Offscreen rendering " + (osrEnabledArg ? "enabled" : "disabled"));

        // MainFrame keeps all the knowledge to display the embedded browser
        // frame.
        final MainFrame frame = new MainFrame(osrEnabledArg, transparentPaintingEnabledArg, createImmediately, args);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
//        control_pane_ = new ControlPanel(getBrowser());
//        status_panel_ = new StatusPanel();
//        contentPanel.add(control_pane_, BorderLayout.NORTH);
//        contentPanel.add(status_panel_, BorderLayout.SOUTH);
        return contentPanel;
    }

    public boolean isOsrEnabled() {
        return osr_enabled_;
    }

    public boolean isTransparentPaintingEnabled() {
        return transparent_painting_enabled_;
    }
}
