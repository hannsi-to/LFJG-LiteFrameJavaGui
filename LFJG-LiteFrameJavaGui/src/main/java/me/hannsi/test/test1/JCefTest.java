package me.hannsi.test.test1;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.cef.handler.CefKeyboardHandlerAdapter;

import javax.swing.*;
import java.awt.*;

public class JCefTest {
    private static final String URL = "https://google.com";
    private static final boolean OFFSCREEN = false;
    private static final boolean TRANSPARENT = true;

    public static void main(String[] args) {
        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = OFFSCREEN;
        CefApp cefApp = CefApp.getInstance(settings);
        CefClient client = cefApp.createClient();
        client.addMessageRouter(CefMessageRouter.create());
        CefBrowser browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT);
        browser.createImmediately();

        client.addKeyboardHandler(new CefKeyboardHandlerAdapter() {
            @Override
            public boolean onKeyEvent(CefBrowser browser, CefKeyEvent event) {
                System.out.println("Key event: " + event.character);
                return false;
            }
        });

        Component component = browser.getUIComponent();
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(component, BorderLayout.CENTER);
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(contentPanel);
        jFrame.setSize(1920, 1080);
        jFrame.setVisible(true);
    }
}
