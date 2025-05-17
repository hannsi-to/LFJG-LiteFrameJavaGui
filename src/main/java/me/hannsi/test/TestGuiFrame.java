package me.hannsi.test;

import me.hannsi.lfjg.frame.event.events.user.*;
import me.hannsi.lfjg.frame.event.system.EventHandler;
import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.frame.IFrame;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.frame.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.jcef.adapter.KeyEventAdapter;
import me.hannsi.lfjg.jcef.adapter.MouseEventAdapter;
import me.hannsi.lfjg.jcef.adapter.MouseWheelEventAdapter;
import me.hannsi.lfjg.render.openGL.system.scene.SceneSystem;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.toolkit.Camera;
import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;
import me.hannsi.lfjg.utils.type.types.AntiAliasingType;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import me.hannsi.lfjg.utils.type.types.VSyncType;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefBrowserOsr;
import org.cef.browser.CefMessageRouter;
import org.cef.browser.CefPaintEvent;
import org.cef.callback.CefNative;
import org.cef.handler.CefRenderHandlerAdapter;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static me.hannsi.lfjg.frame.frame.LFJGContext.args;
import static me.hannsi.lfjg.frame.frame.LFJGContext.frame;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_BGRA;

public class TestGuiFrame implements LFJGFrame {
    private static final String URL = "https://google.com";
    private static final boolean OFFSCREEN = true;
    private static final boolean TRANSPARENT = true;
    public static int textureId = -1;
    SceneSystem sceneSystem;
    MouseInfo mouseInfo;
    KeyboardInfo keyboardInfo;
    Camera camera;

    CefApp cefApp;
    CefClient client;
    CefBrowser browser;
    MyRenderHandler myRenderHandler;
    MouseEventAdapter mouseEventAdapter;
    MouseWheelEventAdapter mouseWheelEventAdapter;
    KeyEventAdapter keyEventAdapter;

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

        sceneSystem = new SceneSystem();
        sceneSystem.addScene(new TestScene1().getScene());
        sceneSystem.addScene(new TestScene2(frame).getScene());
        sceneSystem.addScene(new TestScene3(frame).getScene());
        sceneSystem.setCurrentScene("TestScene3");
        sceneSystem.initScenes();

        mouseInfo = new MouseInfo();
        keyboardInfo = new KeyboardInfo();
        camera = new Camera();


        if (!CefApp.startup(args)) {
            System.out.println("Startup initialization failed!");
            return;
        }

        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = OFFSCREEN;
        cefApp = CefApp.getInstance(settings);
        myRenderHandler = new MyRenderHandler();
        client = cefApp.createLFJGCefClient(myRenderHandler);
        client.addMessageRouter(CefMessageRouter.create());
        browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT);
        browser.createImmediately();

        mouseEventAdapter = new MouseEventAdapter((CefBrowserOsr) browser);
        mouseWheelEventAdapter = new MouseWheelEventAdapter((CefBrowserOsr) browser);
        keyEventAdapter = new KeyEventAdapter((CefBrowserOsr) browser);
    }

    @Override
    public void drawFrame() {
        if (myRenderHandler.isFrameUpdated()) {
            IntBuffer widthBuf = BufferUtils.createIntBuffer(1);
            IntBuffer heightBuf = BufferUtils.createIntBuffer(1);
            myRenderHandler.consumeFrame(widthBuf, heightBuf);
            ByteBuffer frame;
            synchronized (this) {
                frame = myRenderHandler.getLatestFrame();
            }

            if (frame != null) {
                int width = widthBuf.get(0);
                int height = heightBuf.get(0);

                if (textureId == -1) {
                    textureId = glGenTextures();
                    glBindTexture(GL_TEXTURE_2D, textureId);
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_BGRA, GL_UNSIGNED_BYTE, frame);
                } else {
                    glBindTexture(GL_TEXTURE_2D, textureId);
                    glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_BGRA, GL_UNSIGNED_BYTE, frame);
                }
            }
        }

        if (textureId != -1) {
            glEnable(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, textureId);
            glBegin(GL_QUADS);
            glTexCoord2f(0, 1);
            glVertex2f(0, 0);
            glTexCoord2f(1, 1);
            glVertex2f(1920, 0);
            glTexCoord2f(1, 0);
            glVertex2f(1920, 1080);
            glTexCoord2f(0, 0);
            glVertex2f(0, 1080);
            glEnd();
            glBindTexture(GL_TEXTURE_2D, 0);
            glDisable(GL_TEXTURE_2D);
        }

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
        frame.setFrameSettingValue(RefreshRateSetting.class, 360);
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.BORDERLESS);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.V_SYNC_OFF);
        frame.setFrameSettingValue(FloatingSetting.class, false);
        frame.setFrameSettingValue(IconSetting.class, new ResourcesLocation("salad_x32.png"));
        frame.setFrameSettingValue(AntiAliasingSetting.class, AntiAliasingType.OFF);
        frame.setFrameSettingValue(WidthSetting.class, 1920);
        frame.setFrameSettingValue(HeightSetting.class, 1080);
    }

    @EventHandler
    public void keyPressEvent(KeyPressEvent event) {

    }

    @EventHandler
    public void mouseButtonCallbackEvent(MouseButtonCallbackEvent event) {
        mouseInfo.updateMouseButton(event.getButton(), event.getAction());

        MouseEvent mouseEvent = mouseEventAdapter.convertGLFWMouseEvent(event.getButton(), event.getAction(), event.getMods(), mouseInfo.getCurrentPos().x(), mouseInfo.getCurrentPos().y());
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

        MouseEvent moveEvent = mouseEventAdapter.createMouseMovedEvent(event.getXPos(), event.getYPos());
        ((CefBrowserOsr) browser).sendMouseEvent(moveEvent);
    }

    @EventHandler
    public void scrollEvent(ScrollEvent event) {
        MouseWheelEvent mouseWheelEvent = mouseWheelEventAdapter.convertGLFWScroll(event.getXoffset(), event.getYoffset(), mouseInfo.getCurrentPos().x(), mouseInfo.getCurrentPos().y());
        ((CefBrowserOsr) browser).sendMouseWheelEvent(mouseWheelEvent);
    }

    @EventHandler
    public void mouseButtonPressEvent(MouseButtonPressEvent event) {

    }

    @EventHandler
    public void mouseButtonReleasedEvent(MouseButtonReleasedEvent event) {

    }

    static class MyRenderHandler extends CefRenderHandlerAdapter implements CefNative {
        private final Map<String, Long> nativeRefs = new HashMap<>();

        private volatile ByteBuffer latestFrame;
        private int texWidth, texHeight;
        private volatile boolean frameUpdated = false;

        private int frameCount = 0;
        private long lastTime = System.nanoTime();
        private volatile int currentFPS = 0;

        public MyRenderHandler() {
            super();
        }

        @Override
        public Rectangle getViewRect(CefBrowser browser) {
            return new Rectangle(0, 0, 1920, 1080); // 固定サイズでも良い
        }

        @Override
        public void onPaint(CefBrowser browser, boolean popup, Rectangle[] dirtyRects, ByteBuffer buffer, int width, int height) {
            synchronized (this) {
                if (buffer == null || buffer.limit() == 0) {
                    return;
                }

                int size = width * height * 4;

                if (latestFrame == null || latestFrame.capacity() != size) {
                    latestFrame = ByteBuffer.allocateDirect(size);
                }

                buffer.rewind();
                latestFrame.clear();
                latestFrame.put(buffer);
                latestFrame.flip();

                texWidth = width;
                texHeight = height;
                frameUpdated = true;
            }

            frameCount++;
            long now = System.nanoTime();
            if (now - lastTime >= 1_000_000_000L) { // 1秒経過
                currentFPS = frameCount;
                frameCount = 0;
                lastTime = now;
            }
        }

        @Override
        public void addOnPaintListener(Consumer<CefPaintEvent> listener) {

        }

        @Override
        public void setOnPaintListener(Consumer<CefPaintEvent> listener) {

        }

        @Override
        public void removeOnPaintListener(Consumer<CefPaintEvent> listener) {

        }

        @Override
        public void setNativeRef(String identifer, long nativeRef) {
            nativeRefs.put(identifer, nativeRef);
        }

        @Override
        public long getNativeRef(String identifer) {
            return 0;
        }

        public boolean isFrameUpdated() {
            return frameUpdated;
        }

        public void consumeFrame(IntBuffer outTexWidth, IntBuffer outTexHeight) {
            outTexWidth.put(0, texWidth);
            outTexHeight.put(0, texHeight);
            frameUpdated = false;
        }

        public ByteBuffer getLatestFrame() {
            return latestFrame;
        }

        public int getFPS() {
            return currentFPS;
        }
    }
}
