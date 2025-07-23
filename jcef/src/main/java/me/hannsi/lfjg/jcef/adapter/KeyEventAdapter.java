package me.hannsi.lfjg.jcef.adapter;

import me.hannsi.lfjg.core.Core;
import org.cef.browser.CefBrowserOsr;

import java.awt.*;
import java.awt.event.KeyEvent;

@SuppressWarnings("ALL")
public class KeyEventAdapter {
    private final Component dummyComponent;

    public KeyEventAdapter(CefBrowserOsr cefBrowserOsr) {
        this.dummyComponent = cefBrowserOsr.getUIComponent();
    }

    public KeyEvent convertGLFWKey(int glfwKey, int scancode, int action, int mods) {
        char keyChar = '\u0000';

        KeyEvent keyEvent = null;
        if (action == Core.GLFW.GLFW_PRESS) {
            keyEvent = new KeyEvent(
                    dummyComponent,
                    KeyEvent.KEY_PRESSED,
                    0,
                    getAWTModifiers(mods),
                    unicodeToAWTKeyCode(glfwKey),
                    keyChar
            );
        } else if (action == Core.GLFW.GLFW_RELEASE) {
            keyEvent = new KeyEvent(
                    dummyComponent,
                    KeyEvent.KEY_RELEASED,
                    0,
                    getAWTModifiers(mods),
                    unicodeToAWTKeyCode(glfwKey),
                    keyChar
            );
        }

        return keyEvent;
    }

    private int getAWTModifiers(int glfwMods) {
        int mods = 0;
        if ((glfwMods & Core.GLFW.GLFW_MOD_SHIFT) != 0) {
            mods |= KeyEvent.SHIFT_DOWN_MASK;
        }
        if ((glfwMods & Core.GLFW.GLFW_MOD_CONTROL) != 0) {
            mods |= KeyEvent.CTRL_DOWN_MASK;
        }
        if ((glfwMods & Core.GLFW.GLFW_MOD_ALT) != 0) {
            mods |= KeyEvent.ALT_DOWN_MASK;
        }
        if ((glfwMods & Core.GLFW.GLFW_MOD_SUPER) != 0) {
            mods |= KeyEvent.META_DOWN_MASK;
        }
        return mods;
    }

    public KeyEvent convertGLFWChar(int codepoint) {
        char keyChar = (char) codepoint;

        return new KeyEvent(
                dummyComponent,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                0,
                keyChar
        );
    }

    private int unicodeToAWTKeyCode(int unicode) {
        return switch (unicode) {
            case '\n' -> KeyEvent.VK_ENTER;
            case '\b' -> KeyEvent.VK_BACK_SPACE;
            case '\t' -> KeyEvent.VK_TAB;
            case 0x1B -> KeyEvent.VK_ESCAPE;
            case ' ' -> KeyEvent.VK_SPACE;

            case 'a', 'A' -> KeyEvent.VK_A;
            case 'b', 'B' -> KeyEvent.VK_B;
            case 'c', 'C' -> KeyEvent.VK_C;
            case 'd', 'D' -> KeyEvent.VK_D;
            case 'e', 'E' -> KeyEvent.VK_E;
            case 'f', 'F' -> KeyEvent.VK_F;
            case 'g', 'G' -> KeyEvent.VK_G;
            case 'h', 'H' -> KeyEvent.VK_H;
            case 'i', 'I' -> KeyEvent.VK_I;
            case 'j', 'J' -> KeyEvent.VK_J;
            case 'k', 'K' -> KeyEvent.VK_K;
            case 'l', 'L' -> KeyEvent.VK_L;
            case 'm', 'M' -> KeyEvent.VK_M;
            case 'n', 'N' -> KeyEvent.VK_N;
            case 'o', 'O' -> KeyEvent.VK_O;
            case 'p', 'P' -> KeyEvent.VK_P;
            case 'q', 'Q' -> KeyEvent.VK_Q;
            case 'r', 'R' -> KeyEvent.VK_R;
            case 's', 'S' -> KeyEvent.VK_S;
            case 't', 'T' -> KeyEvent.VK_T;
            case 'u', 'U' -> KeyEvent.VK_U;
            case 'v', 'V' -> KeyEvent.VK_V;
            case 'w', 'W' -> KeyEvent.VK_W;
            case 'x', 'X' -> KeyEvent.VK_X;
            case 'y', 'Y' -> KeyEvent.VK_Y;
            case 'z', 'Z' -> KeyEvent.VK_Z;

            case '0' -> KeyEvent.VK_0;
            case '1' -> KeyEvent.VK_1;
            case '2' -> KeyEvent.VK_2;
            case '3' -> KeyEvent.VK_3;
            case '4' -> KeyEvent.VK_4;
            case '5' -> KeyEvent.VK_5;
            case '6' -> KeyEvent.VK_6;
            case '7' -> KeyEvent.VK_7;
            case '8' -> KeyEvent.VK_8;
            case '9' -> KeyEvent.VK_9;

            case '-' -> KeyEvent.VK_MINUS;
            case '=' -> KeyEvent.VK_EQUALS;
            case '\\' -> KeyEvent.VK_BACK_SLASH;
            case ';' -> KeyEvent.VK_SEMICOLON;
            case '\'' -> KeyEvent.VK_QUOTE;
            case ',' -> KeyEvent.VK_COMMA;
            case '.' -> KeyEvent.VK_PERIOD;
            case '/' -> KeyEvent.VK_SLASH;
            case '`' -> KeyEvent.VK_BACK_QUOTE;

            default -> KeyEvent.VK_UNDEFINED;
        };
    }

    public int glfwToAWTKeyCode(int glfwKey) {
        if (glfwKey == Core.GLFW.GLFW_KEY_ENTER) {
            return KeyEvent.VK_ENTER;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_BACKSPACE) {
            return KeyEvent.VK_BACK_SPACE;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_TAB) {
            return KeyEvent.VK_TAB;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_ESCAPE) {
            return KeyEvent.VK_ESCAPE;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_SPACE) {
            return KeyEvent.VK_SPACE;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_LEFT) {
            return KeyEvent.VK_LEFT;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_RIGHT) {
            return KeyEvent.VK_RIGHT;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_UP) {
            return KeyEvent.VK_UP;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_DOWN) {
            return KeyEvent.VK_DOWN;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_DELETE) {
            return KeyEvent.VK_DELETE;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_HOME) {
            return KeyEvent.VK_HOME;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_END) {
            return KeyEvent.VK_END;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_PAGE_UP) {
            return KeyEvent.VK_PAGE_UP;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_PAGE_DOWN) {
            return KeyEvent.VK_PAGE_DOWN;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_INSERT) {
            return KeyEvent.VK_INSERT;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_A) {
            return KeyEvent.VK_A;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_B) {
            return KeyEvent.VK_B;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_C) {
            return KeyEvent.VK_C;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_D) {
            return KeyEvent.VK_D;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_E) {
            return KeyEvent.VK_E;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F) {
            return KeyEvent.VK_F;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_G) {
            return KeyEvent.VK_G;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_H) {
            return KeyEvent.VK_H;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_I) {
            return KeyEvent.VK_I;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_J) {
            return KeyEvent.VK_J;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_K) {
            return KeyEvent.VK_K;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_L) {
            return KeyEvent.VK_L;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_M) {
            return KeyEvent.VK_M;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_N) {
            return KeyEvent.VK_N;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_O) {
            return KeyEvent.VK_O;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_P) {
            return KeyEvent.VK_P;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_Q) {
            return KeyEvent.VK_Q;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_R) {
            return KeyEvent.VK_R;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_S) {
            return KeyEvent.VK_S;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_T) {
            return KeyEvent.VK_T;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_U) {
            return KeyEvent.VK_U;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_V) {
            return KeyEvent.VK_V;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_W) {
            return KeyEvent.VK_W;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_X) {
            return KeyEvent.VK_X;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_Y) {
            return KeyEvent.VK_Y;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_Z) {
            return KeyEvent.VK_Z;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_0) {
            return KeyEvent.VK_0;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_1) {
            return KeyEvent.VK_1;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_2) {
            return KeyEvent.VK_2;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_3) {
            return KeyEvent.VK_3;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_4) {
            return KeyEvent.VK_4;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_5) {
            return KeyEvent.VK_5;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_6) {
            return KeyEvent.VK_6;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_7) {
            return KeyEvent.VK_7;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_8) {
            return KeyEvent.VK_8;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_9) {
            return KeyEvent.VK_9;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F1) {
            return KeyEvent.VK_F1;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F2) {
            return KeyEvent.VK_F2;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F3) {
            return KeyEvent.VK_F3;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F4) {
            return KeyEvent.VK_F4;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F5) {
            return KeyEvent.VK_F5;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F6) {
            return KeyEvent.VK_F6;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F7) {
            return KeyEvent.VK_F7;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F8) {
            return KeyEvent.VK_F8;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F9) {
            return KeyEvent.VK_F9;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F10) {
            return KeyEvent.VK_F10;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F11) {
            return KeyEvent.VK_F11;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_F12) {
            return KeyEvent.VK_F12;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_LEFT_SHIFT || glfwKey == Core.GLFW.GLFW_KEY_RIGHT_SHIFT) {
            return KeyEvent.VK_SHIFT;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_LEFT_CONTROL || glfwKey == Core.GLFW.GLFW_KEY_RIGHT_CONTROL) {
            return KeyEvent.VK_CONTROL;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_LEFT_ALT || glfwKey == Core.GLFW.GLFW_KEY_RIGHT_ALT) {
            return KeyEvent.VK_ALT;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_LEFT_SUPER || glfwKey == Core.GLFW.GLFW_KEY_RIGHT_SUPER) {
            return KeyEvent.VK_META;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_MINUS) {
            return KeyEvent.VK_MINUS;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_EQUAL) {
            return KeyEvent.VK_EQUALS;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_BACKSLASH) {
            return KeyEvent.VK_BACK_SLASH;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_SEMICOLON) {
            return KeyEvent.VK_SEMICOLON;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_APOSTROPHE) {
            return KeyEvent.VK_QUOTE;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_COMMA) {
            return KeyEvent.VK_COMMA;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_PERIOD) {
            return KeyEvent.VK_PERIOD;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_SLASH) {
            return KeyEvent.VK_SLASH;
        } else if (glfwKey == Core.GLFW.GLFW_KEY_GRAVE_ACCENT) {
            return KeyEvent.VK_BACK_QUOTE;
        } else {
            return KeyEvent.VK_UNDEFINED;
        }
    }
}
