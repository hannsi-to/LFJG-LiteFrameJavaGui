package me.hannsi.test;

import me.hannsi.lfjg.frame.event.events.user.*;
import me.hannsi.lfjg.frame.event.system.EventHandler;
import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.frame.IFrame;
import me.hannsi.lfjg.frame.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.openGL.system.scene.SceneSystem;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.toolkit.Camera;
import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;
import me.hannsi.lfjg.utils.type.types.AntiAliasingType;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import me.hannsi.lfjg.utils.type.types.VSyncType;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import static me.hannsi.lfjg.frame.frame.LFJGContext.frame;

public class TestGuiFrame implements LFJGFrame {
    SceneSystem sceneSystem;
    MouseInfo mouseInfo;
    KeyboardInfo keyboardInfo;
    Camera camera;

    public static void main(String[] args) {
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
    }

    @Override
    public void drawFrame() {
        sceneSystem.drawFrameScenes();

        float move = 0.01f;
        if (isKeyPressed(GLFW.GLFW_KEY_W)) {
            camera.moveForward(move);
        } else if (isKeyPressed(GLFW.GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if (isKeyPressed(GLFW.GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            camera.moveUp(move);
        } else if (isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
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
        return GLFW.glfwGetKey(frame.getWindowID(), keyCode) == GLFW.GLFW_PRESS;
    }

    @Override
    public void stopFrame() {
        sceneSystem.stopFrameScenes();
        sceneSystem.cleanup();
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
    }

    @EventHandler
    public void keyCallbackEvent(KeyEvent event) {
        keyboardInfo.updateKeyState(event.getKey(), event.getAction());
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
    }

    @EventHandler
    public void mouseButtonPressEvent(MouseButtonPressEvent event) {

    }

    @EventHandler
    public void mouseButtonReleasedEvent(MouseButtonReleasedEvent event) {

    }
}
