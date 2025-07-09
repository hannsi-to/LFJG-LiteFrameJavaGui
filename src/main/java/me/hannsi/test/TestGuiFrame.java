package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.event.events.user.*;
import me.hannsi.lfjg.frame.event.system.EventHandler;
import me.hannsi.lfjg.frame.frame.IFrame;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.frame.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.jcef.adapter.KeyEventAdapter;
import me.hannsi.lfjg.jcef.adapter.MouseEventAdapter;
import me.hannsi.lfjg.jcef.adapter.MouseWheelEventAdapter;
import me.hannsi.lfjg.jcef.handler.RenderHandler;
import me.hannsi.lfjg.render.system.font.Font;
import me.hannsi.lfjg.render.system.font.FontCache;
import me.hannsi.lfjg.render.system.scene.SceneSystem;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.toolkit.Camera;
import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;
import me.hannsi.lfjg.utils.type.types.AntiAliasingType;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import me.hannsi.lfjg.utils.type.types.VSyncType;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefBrowserOsr;
import org.joml.Vector2f;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import static me.hannsi.lfjg.frame.frame.LFJGContext.*;
import static org.lwjgl.glfw.GLFW.*;

public class TestGuiFrame implements LFJGFrame {
    private static final String URL = "https://google.com";
    private static final boolean OFFSCREEN = true;
    private static final boolean TRANSPARENT = true;
    public static int textureId = -1;
    public Camera camera;
    SceneSystem sceneSystem;
    CefApp cefApp;
    CefClient client;
    CefBrowser browser;
    RenderHandler myRenderHandler;
    MouseEventAdapter mouseEventAdapter;
    MouseWheelEventAdapter mouseWheelEventAdapter;
    KeyEventAdapter keyEventAdapter;

    private float x;
    private float y;
    private float width;
    private float height;

    public static void main(String[] args) {
        LFJGContext.args = args;
        new TestGuiFrame().setFrame();
    }

    public void setFrame() {
        frame = new Frame(this, "TestGuiFrame");
    }

    @Override
    public void init() {
        IFrame.eventManager.register(this);

        frame.updateLFJGLContext();
        mouseInfo = new MouseInfo();
        keyboardInfo = new KeyboardInfo();
        camera = new Camera();

        fontCache = FontCache.initFontCache()
                .createCache(new Font("font", Location.fromResource("font/default.ttf")))
                .loadFonts();

        sceneSystem = SceneSystem.initSceneSystem()
                .addScene(new TestScene1().getScene())
                .addScene(new TestScene2(frame).getScene())
                .addScene(new TestScene3(frame).getScene())
                .addScene(new TestScene4(frame).getScene())
                .addScene(new TestSound1(frame).getScene())
                .addScene(new TestVideo1(frame).getScene())
                .addScene(new TestPhysic1(frame).getScene())
                .addScene(new Test3D1(frame, this).getScene())
                .setCurrentScene("TestScene4")
                .initScenes();

//        if (!CefApp.startup(args)) {
//            System.out.println("Startup initialization failed!");
//            return;
//        }

//        x = windowSize.x() / 4f;
//        y = windowSize.y() / 4f;
//        width = windowSize.x() / 2f;
//        height = windowSize.y() / 2f;

        x = 0;
        y = 0;
        width = 1920;
        height = 1080;

//        CefSettings settings = new CefSettings();
//        settings.windowless_rendering_enabled = OFFSCREEN;
//        cefApp = CefApp.getInstance(settings);
//        myRenderHandler = new RenderHandler((int) x, (int) y, (int) width, (int) height);
//        client = cefApp.createLFJGCefClient(myRenderHandler);
//        client.addMessageRouter(CefMessageRouter.create());
//        browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT);
//        browser.createImmediately();
//
//        mouseEventAdapter = new MouseEventAdapter((CefBrowserOsr) browser);
//        mouseWheelEventAdapter = new MouseWheelEventAdapter((CefBrowserOsr) browser);
//        keyEventAdapter = new KeyEventAdapter((CefBrowserOsr) browser);
    }

    @Override
    public void drawFrame() {
//        x = 0;
//        y = 0;
//        width = 1920;
//        height = 1080;
//
//        if (myRenderHandler.isFrameUpdated()) {
//            IntBuffer widthBuf = BufferUtils.createIntBuffer(1);
//            IntBuffer heightBuf = BufferUtils.createIntBuffer(1);
//            myRenderHandler.consumeFrame(widthBuf, heightBuf);
//            ByteBuffer frame;
//            synchronized (this) {
//                frame = myRenderHandler.getLatestFrame();
//            }
//
//            if (frame != null) {
//                width = widthBuf.get(0);
//                height = heightBuf.get(0);
//
//                if (textureId == -1) {
//                    textureId = glGenTextures();
//                    glBindTexture(GL_TEXTURE_2D, textureId);
//                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//                    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, (int) width, (int) height, 0, GL_BGRA, GL_UNSIGNED_BYTE, frame);
//                } else {
//                    glBindTexture(GL_TEXTURE_2D, textureId);
//                    glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, (int) width, (int) height, GL_BGRA, GL_UNSIGNED_BYTE, frame);
//                }
//            }
//        }
//
//        if (textureId != -1) {
//            glEnable(GL_TEXTURE_2D);
//            glBindTexture(GL_TEXTURE_2D, textureId);
//            glBegin(GL_QUADS);
//            glTexCoord2f(0, 1);
//            glVertex2f(x, y);
//            glTexCoord2f(1, 1);
//            glVertex2f(x + width, y);
//            glTexCoord2f(1, 0);
//            glVertex2f(x + width, y + height);
//            glTexCoord2f(0, 0);
//            glVertex2f(x, y + height);
//            glEnd();
//            glBindTexture(GL_TEXTURE_2D, 0);
//            glDisable(GL_TEXTURE_2D);
//        }

        sceneSystem.drawFrameScenes();

        float move = 0.01f;
        if (isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        } else if (isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if (isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (isKeyPressed(GLFW_KEY_SPACE)) {
            camera.moveUp(move);
        } else if (isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveDown(move);
        }

        float MOUSE_SENSITIVITY = 0.1f;

        if (mouseInfo.isRightButtonPressed()) {
            Vector2f displVec = mouseInfo.getDisplaySize();
            camera.addRotation((float) Math.toRadians(-displVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }

        mouseInfo.input();
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(frame.getWindowID(), keyCode) == GLFW_PRESS;
    }

    @Override
    public void stopFrame() {
        fontCache.cleanup();

        sceneSystem.stopFrameScenes();
        sceneSystem.cleanup();

        if (browser != null) {
            browser.close(true);
        }
        if (client != null) {
            client.dispose();
        }
    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.BORDERLESS);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.V_SYNC_OFF);
        frame.setFrameSettingValue(FloatingSetting.class, false);
        frame.setFrameSettingValue(IconSetting.class, Location.fromResource("salad_x32.png"));
        frame.setFrameSettingValue(AntiAliasingSetting.class, AntiAliasingType.OFF);
        frame.setFrameSettingValue(WidthSetting.class, 1920);
        frame.setFrameSettingValue(HeightSetting.class, 1080);
    }

    @EventHandler
    public void keyPressEvent(KeyPressEvent event) {

    }

    @EventHandler
    public void mouseButtonCallbackEvent(MouseButtonEvent event) {
        mouseInfo.updateMouseButton(event.getButton(), event.getAction());

        MouseEvent mouseEvent = mouseEventAdapter.convertGLFWMouseEvent(event.getButton(), event.getAction(), event.getMods(), mouseInfo.getCurrentPos().x() - x, mouseInfo.getCurrentPos().y() - y);
        ((CefBrowserOsr) browser).sendMouseEvent(mouseEvent);
    }

    @EventHandler
    public void keyCallbackEvent(KeyEvent event) {
        keyboardInfo.updateKeyState(event.getKey(), event.getAction());

        java.awt.event.KeyEvent keyEvent = keyEventAdapter.convertGLFWKey(event.getKey(), event.getScancode(), event.getAction(), event.getMods());
        if (keyEvent != null) {
            ((CefBrowserOsr) browser).sendKeyEvent(keyEvent);
        }
    }

    @EventHandler
    public void CharEvent(CharEvent event) {
        java.awt.event.KeyEvent keyEvent = keyEventAdapter.convertGLFWChar(event.getCodepoint());
        if (keyEvent != null) {
            ((CefBrowserOsr) browser).sendKeyEvent(keyEvent);
        }
    }

    @EventHandler
    public void cursorEnterEvent(CursorEnterEvent event) {
        mouseInfo.updateInWindow(event.isEntered());
    }

    @EventHandler
    public void keyReleasedEvent(KeyReleasedEvent event) {

    }

    @EventHandler
    public void cursorPosEvent(CursorPosEvent event) {
        mouseInfo.updateCursorPos((float) event.getXPos(), (float) event.getYPos());

        MouseEvent moveEvent = mouseEventAdapter.createMouseMovedEvent(event.getXPos() - x, event.getYPos() - y);
        ((CefBrowserOsr) browser).sendMouseEvent(moveEvent);
    }

    @EventHandler
    public void scrollEvent(ScrollEvent event) {
        MouseWheelEvent mouseWheelEvent = mouseWheelEventAdapter.convertGLFWScroll(event.getXoffset(), event.getYoffset(), mouseInfo.getCurrentPos().x() - x, mouseInfo.getCurrentPos().y() - y);
        ((CefBrowserOsr) browser).sendMouseWheelEvent(mouseWheelEvent);
    }

    @EventHandler
    public void mouseButtonPressEvent(MouseButtonPressEvent event) {

    }

    @EventHandler
    public void mouseButtonReleasedEvent(MouseButtonReleasedEvent event) {

    }
}
