package me.hannsi.lfjg.core.utils.toolkit;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static me.hannsi.lfjg.core.Core.GLFW.*;


public class KeyboardInfo {
    private static final Map<Integer, String> keyMap = new HashMap<>();
    private static final long DEFAULT_HOLD_THRESHOLD = 10;

    static {
        keyMap.put(GLFW_KEY_SPACE, "Space");
        keyMap.put(GLFW_KEY_APOSTROPHE, "'");
        keyMap.put(GLFW_KEY_COMMA, ",");
        keyMap.put(GLFW_KEY_MINUS, "-");
        keyMap.put(GLFW_KEY_PERIOD, ".");
        keyMap.put(GLFW_KEY_SLASH, "/");
        keyMap.put(GLFW_KEY_0, "0");
        keyMap.put(GLFW_KEY_1, "1");
        keyMap.put(GLFW_KEY_2, "2");
        keyMap.put(GLFW_KEY_3, "3");
        keyMap.put(GLFW_KEY_4, "4");
        keyMap.put(GLFW_KEY_5, "5");
        keyMap.put(GLFW_KEY_6, "6");
        keyMap.put(GLFW_KEY_7, "7");
        keyMap.put(GLFW_KEY_8, "8");
        keyMap.put(GLFW_KEY_9, "9");
        keyMap.put(GLFW_KEY_SEMICOLON, ";");
        keyMap.put(GLFW_KEY_EQUAL, "=");
        keyMap.put(GLFW_KEY_A, "A");
        keyMap.put(GLFW_KEY_B, "B");
        keyMap.put(GLFW_KEY_C, "C");
        keyMap.put(GLFW_KEY_D, "D");
        keyMap.put(GLFW_KEY_E, "E");
        keyMap.put(GLFW_KEY_F, "F");
        keyMap.put(GLFW_KEY_G, "G");
        keyMap.put(GLFW_KEY_H, "H");
        keyMap.put(GLFW_KEY_I, "I");
        keyMap.put(GLFW_KEY_J, "J");
        keyMap.put(GLFW_KEY_K, "K");
        keyMap.put(GLFW_KEY_L, "L");
        keyMap.put(GLFW_KEY_M, "M");
        keyMap.put(GLFW_KEY_N, "N");
        keyMap.put(GLFW_KEY_O, "O");
        keyMap.put(GLFW_KEY_P, "P");
        keyMap.put(GLFW_KEY_Q, "Q");
        keyMap.put(GLFW_KEY_R, "R");
        keyMap.put(GLFW_KEY_S, "S");
        keyMap.put(GLFW_KEY_T, "T");
        keyMap.put(GLFW_KEY_U, "U");
        keyMap.put(GLFW_KEY_V, "V");
        keyMap.put(GLFW_KEY_W, "W");
        keyMap.put(GLFW_KEY_X, "X");
        keyMap.put(GLFW_KEY_Y, "Y");
        keyMap.put(GLFW_KEY_Z, "Z");
        keyMap.put(GLFW_KEY_LEFT_BRACKET, "[");
        keyMap.put(GLFW_KEY_BACKSLASH, "\\");
        keyMap.put(GLFW_KEY_RIGHT_BRACKET, "]");
        keyMap.put(GLFW_KEY_GRAVE_ACCENT, "`");
        keyMap.put(GLFW_KEY_WORLD_1, "World 1");
        keyMap.put(GLFW_KEY_WORLD_2, "World 2");

        keyMap.put(GLFW_KEY_ESCAPE, "Escape");
        keyMap.put(GLFW_KEY_ENTER, "Enter");
        keyMap.put(GLFW_KEY_TAB, "Tab");
        keyMap.put(GLFW_KEY_BACKSPACE, "Backspace");
        keyMap.put(GLFW_KEY_INSERT, "Insert");
        keyMap.put(GLFW_KEY_DELETE, "Delete");
        keyMap.put(GLFW_KEY_RIGHT, "Right Arrow");
        keyMap.put(GLFW_KEY_LEFT, "Left Arrow");
        keyMap.put(GLFW_KEY_DOWN, "Down Arrow");
        keyMap.put(GLFW_KEY_UP, "Up Arrow");
        keyMap.put(GLFW_KEY_PAGE_UP, "Page Up");
        keyMap.put(GLFW_KEY_PAGE_DOWN, "Page Down");
        keyMap.put(GLFW_KEY_HOME, "Home");
        keyMap.put(GLFW_KEY_END, "End");
        keyMap.put(GLFW_KEY_CAPS_LOCK, "Caps Lock");
        keyMap.put(GLFW_KEY_SCROLL_LOCK, "Scroll Lock");
        keyMap.put(GLFW_KEY_NUM_LOCK, "Num Lock");
        keyMap.put(GLFW_KEY_PRINT_SCREEN, "Print Screen");
        keyMap.put(GLFW_KEY_PAUSE, "Pause");
        keyMap.put(GLFW_KEY_F1, "F1");
        keyMap.put(GLFW_KEY_F2, "F2");
        keyMap.put(GLFW_KEY_F3, "F3");
        keyMap.put(GLFW_KEY_F4, "F4");
        keyMap.put(GLFW_KEY_F5, "F5");
        keyMap.put(GLFW_KEY_F6, "F6");
        keyMap.put(GLFW_KEY_F7, "F7");
        keyMap.put(GLFW_KEY_F8, "F8");
        keyMap.put(GLFW_KEY_F9, "F9");
        keyMap.put(GLFW_KEY_F10, "F10");
        keyMap.put(GLFW_KEY_F11, "F11");
        keyMap.put(GLFW_KEY_F12, "F12");
        keyMap.put(GLFW_KEY_F13, "F13");
        keyMap.put(GLFW_KEY_F14, "F14");
        keyMap.put(GLFW_KEY_F15, "F15");
        keyMap.put(GLFW_KEY_F16, "F16");
        keyMap.put(GLFW_KEY_F17, "F17");
        keyMap.put(GLFW_KEY_F18, "F18");
        keyMap.put(GLFW_KEY_F19, "F19");
        keyMap.put(GLFW_KEY_F20, "F20");
        keyMap.put(GLFW_KEY_F21, "F21");
        keyMap.put(GLFW_KEY_F22, "F22");
        keyMap.put(GLFW_KEY_F23, "F23");
        keyMap.put(GLFW_KEY_F24, "F24");
        keyMap.put(GLFW_KEY_F25, "F25");

        keyMap.put(GLFW_KEY_KP_0, "Keypad 0");
        keyMap.put(GLFW_KEY_KP_1, "Keypad 1");
        keyMap.put(GLFW_KEY_KP_2, "Keypad 2");
        keyMap.put(GLFW_KEY_KP_3, "Keypad 3");
        keyMap.put(GLFW_KEY_KP_4, "Keypad 4");
        keyMap.put(GLFW_KEY_KP_5, "Keypad 5");
        keyMap.put(GLFW_KEY_KP_6, "Keypad 6");
        keyMap.put(GLFW_KEY_KP_7, "Keypad 7");
        keyMap.put(GLFW_KEY_KP_8, "Keypad 8");
        keyMap.put(GLFW_KEY_KP_9, "Keypad 9");
        keyMap.put(GLFW_KEY_KP_DECIMAL, "Keypad .");
        keyMap.put(GLFW_KEY_KP_DIVIDE, "Keypad /");
        keyMap.put(GLFW_KEY_KP_MULTIPLY, "Keypad *");
        keyMap.put(GLFW_KEY_KP_SUBTRACT, "Keypad -");
        keyMap.put(GLFW_KEY_KP_ADD, "Keypad +");
        keyMap.put(GLFW_KEY_KP_ENTER, "Keypad Enter");
        keyMap.put(GLFW_KEY_KP_EQUAL, "Keypad =");

        keyMap.put(GLFW_KEY_LEFT_SHIFT, "Left Shift");
        keyMap.put(GLFW_KEY_RIGHT_SHIFT, "Right Shift");
        keyMap.put(GLFW_KEY_LEFT_CONTROL, "Left Control");
        keyMap.put(GLFW_KEY_RIGHT_CONTROL, "Right Control");
        keyMap.put(GLFW_KEY_LEFT_ALT, "Left Alt");
        keyMap.put(GLFW_KEY_RIGHT_ALT, "Right Alt");
        keyMap.put(GLFW_KEY_LEFT_SUPER, "Left Super");
        keyMap.put(GLFW_KEY_RIGHT_SUPER, "Right Super");
        keyMap.put(GLFW_KEY_MENU, "Menu");
    }

    private final Queue<Integer> pressedKeys;
    private final Map<Integer, Long> holdKeys;
    private final Map<Integer, Runnable> keyListeners;
    private long holdThreshold;
    private boolean debugMode;

    public KeyboardInfo() {
        pressedKeys = new ConcurrentLinkedQueue<>();
        holdKeys = new ConcurrentHashMap<>();
        keyListeners = new ConcurrentHashMap<>();
        holdThreshold = DEFAULT_HOLD_THRESHOLD;
        debugMode = false;
    }

    public static String getKeyName(int key) {
        return keyMap.getOrDefault(key, "Unknown Key");
    }

    public void updateKeyState(int key, int action) {
        long currentTime = System.currentTimeMillis();

        if (action == GLFW_PRESS && !pressedKeys.contains(key)) {
            pressedKeys.add(key);
            holdKeys.put(key, currentTime);
            keyListeners.getOrDefault(key, () -> {
            }).run();
        } else if (action == GLFW_RELEASE) {
            pressedKeys.remove(key);
            holdKeys.remove(key);
        }

        if (debugMode) {
            String keyName = getKeyName(key);
            String actionStr = action == GLFW_PRESS ? "pressed" : action == GLFW_RELEASE ? "released" : "hold";
            System.out.printf("Key: %s, Action: %s, Time: %dms\n", keyName, actionStr, currentTime);
        }
    }

    public void addKeyListener(int key, Runnable action) {
        keyListeners.put(key, action);
    }

    public void removeKeyListener(int key) {
        keyListeners.remove(key);
    }

    public boolean isKeyPressed(int key) {
        return pressedKeys.contains(key);
    }

    public boolean areKeysPressed(int... keys) {
        for (int key : keys) {
            if (!pressedKeys.contains(key)) {
                return false;
            }
        }
        return true;
    }

    public boolean isKeyHeld(int key) {
        if (holdKeys.containsKey(key)) {
            long pressedTime = holdKeys.get(key);
            return (System.currentTimeMillis() - pressedTime) >= holdThreshold;
        }
        return false;
    }

    public Map<Integer, Long> getHeldKeys() {
        Map<Integer, Long> heldKeys = new HashMap<>();
        long currentTime = System.currentTimeMillis();

        for (Map.Entry<Integer, Long> entry : holdKeys.entrySet()) {
            if ((currentTime - entry.getValue()) >= holdThreshold) {
                heldKeys.put(entry.getKey(), currentTime - entry.getValue());
            }
        }
        return heldKeys;
    }

    public void cleanup() {
        pressedKeys.clear();
        holdKeys.clear();
    }

    public Queue<Integer> getPressedKeys() {
        return pressedKeys;
    }

    public Map<Integer, Long> getHoldKeys() {
        return holdKeys;
    }

    public Map<Integer, Runnable> getKeyListeners() {
        return keyListeners;
    }

    public long getHoldThreshold() {
        return holdThreshold;
    }

    public void setHoldThreshold(long holdThreshold) {
        this.holdThreshold = holdThreshold;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
