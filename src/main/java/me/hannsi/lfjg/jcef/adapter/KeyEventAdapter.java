package me.hannsi.lfjg.jcef.adapter;

import org.cef.browser.CefBrowserOsr;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyEventAdapter {
    private final Component dummyComponent;

    public KeyEventAdapter(CefBrowserOsr cefBrowserOsr) {
        this.dummyComponent = cefBrowserOsr.getUIComponent();
    }

    public KeyEvent convertGLFWKey(int glfwKey, int scancode, int action, int mods) {
        char keyChar = '\u0000';

        KeyEvent keyEvent = null;
        if (action == GLFW.GLFW_PRESS) {
            keyEvent = new KeyEvent(
                    dummyComponent,
                    KeyEvent.KEY_PRESSED,
                    0,
                    getAWTModifiers(mods),
                    unicodeToAWTKeyCode(glfwKey),
                    keyChar
            );
        } else if (action == GLFW.GLFW_RELEASE) {
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
        if ((glfwMods & GLFW.GLFW_MOD_SHIFT) != 0) {
            mods |= KeyEvent.SHIFT_DOWN_MASK;
        }
        if ((glfwMods & GLFW.GLFW_MOD_CONTROL) != 0) {
            mods |= KeyEvent.CTRL_DOWN_MASK;
        }
        if ((glfwMods & GLFW.GLFW_MOD_ALT) != 0) {
            mods |= KeyEvent.ALT_DOWN_MASK;
        }
        if ((glfwMods & GLFW.GLFW_MOD_SUPER) != 0) {
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

    private int glfwToAWTKeyCode(int glfwKey) {
        return switch (glfwKey) {
            case GLFW.GLFW_KEY_ENTER -> KeyEvent.VK_ENTER;
            case GLFW.GLFW_KEY_BACKSPACE -> KeyEvent.VK_BACK_SPACE;
            case GLFW.GLFW_KEY_TAB -> KeyEvent.VK_TAB;
            case GLFW.GLFW_KEY_ESCAPE -> KeyEvent.VK_ESCAPE;
            case GLFW.GLFW_KEY_SPACE -> KeyEvent.VK_SPACE;
            case GLFW.GLFW_KEY_LEFT -> KeyEvent.VK_LEFT;
            case GLFW.GLFW_KEY_RIGHT -> KeyEvent.VK_RIGHT;
            case GLFW.GLFW_KEY_UP -> KeyEvent.VK_UP;
            case GLFW.GLFW_KEY_DOWN -> KeyEvent.VK_DOWN;
            case GLFW.GLFW_KEY_DELETE -> KeyEvent.VK_DELETE;
            case GLFW.GLFW_KEY_HOME -> KeyEvent.VK_HOME;
            case GLFW.GLFW_KEY_END -> KeyEvent.VK_END;
            case GLFW.GLFW_KEY_PAGE_UP -> KeyEvent.VK_PAGE_UP;
            case GLFW.GLFW_KEY_PAGE_DOWN -> KeyEvent.VK_PAGE_DOWN;
            case GLFW.GLFW_KEY_INSERT -> KeyEvent.VK_INSERT;

            case GLFW.GLFW_KEY_A -> KeyEvent.VK_A;
            case GLFW.GLFW_KEY_B -> KeyEvent.VK_B;
            case GLFW.GLFW_KEY_C -> KeyEvent.VK_C;
            case GLFW.GLFW_KEY_D -> KeyEvent.VK_D;
            case GLFW.GLFW_KEY_E -> KeyEvent.VK_E;
            case GLFW.GLFW_KEY_F -> KeyEvent.VK_F;
            case GLFW.GLFW_KEY_G -> KeyEvent.VK_G;
            case GLFW.GLFW_KEY_H -> KeyEvent.VK_H;
            case GLFW.GLFW_KEY_I -> KeyEvent.VK_I;
            case GLFW.GLFW_KEY_J -> KeyEvent.VK_J;
            case GLFW.GLFW_KEY_K -> KeyEvent.VK_K;
            case GLFW.GLFW_KEY_L -> KeyEvent.VK_L;
            case GLFW.GLFW_KEY_M -> KeyEvent.VK_M;
            case GLFW.GLFW_KEY_N -> KeyEvent.VK_N;
            case GLFW.GLFW_KEY_O -> KeyEvent.VK_O;
            case GLFW.GLFW_KEY_P -> KeyEvent.VK_P;
            case GLFW.GLFW_KEY_Q -> KeyEvent.VK_Q;
            case GLFW.GLFW_KEY_R -> KeyEvent.VK_R;
            case GLFW.GLFW_KEY_S -> KeyEvent.VK_S;
            case GLFW.GLFW_KEY_T -> KeyEvent.VK_T;
            case GLFW.GLFW_KEY_U -> KeyEvent.VK_U;
            case GLFW.GLFW_KEY_V -> KeyEvent.VK_V;
            case GLFW.GLFW_KEY_W -> KeyEvent.VK_W;
            case GLFW.GLFW_KEY_X -> KeyEvent.VK_X;
            case GLFW.GLFW_KEY_Y -> KeyEvent.VK_Y;
            case GLFW.GLFW_KEY_Z -> KeyEvent.VK_Z;

            case GLFW.GLFW_KEY_0 -> KeyEvent.VK_0;
            case GLFW.GLFW_KEY_1 -> KeyEvent.VK_1;
            case GLFW.GLFW_KEY_2 -> KeyEvent.VK_2;
            case GLFW.GLFW_KEY_3 -> KeyEvent.VK_3;
            case GLFW.GLFW_KEY_4 -> KeyEvent.VK_4;
            case GLFW.GLFW_KEY_5 -> KeyEvent.VK_5;
            case GLFW.GLFW_KEY_6 -> KeyEvent.VK_6;
            case GLFW.GLFW_KEY_7 -> KeyEvent.VK_7;
            case GLFW.GLFW_KEY_8 -> KeyEvent.VK_8;
            case GLFW.GLFW_KEY_9 -> KeyEvent.VK_9;

            case GLFW.GLFW_KEY_F1 -> KeyEvent.VK_F1;
            case GLFW.GLFW_KEY_F2 -> KeyEvent.VK_F2;
            case GLFW.GLFW_KEY_F3 -> KeyEvent.VK_F3;
            case GLFW.GLFW_KEY_F4 -> KeyEvent.VK_F4;
            case GLFW.GLFW_KEY_F5 -> KeyEvent.VK_F5;
            case GLFW.GLFW_KEY_F6 -> KeyEvent.VK_F6;
            case GLFW.GLFW_KEY_F7 -> KeyEvent.VK_F7;
            case GLFW.GLFW_KEY_F8 -> KeyEvent.VK_F8;
            case GLFW.GLFW_KEY_F9 -> KeyEvent.VK_F9;
            case GLFW.GLFW_KEY_F10 -> KeyEvent.VK_F10;
            case GLFW.GLFW_KEY_F11 -> KeyEvent.VK_F11;
            case GLFW.GLFW_KEY_F12 -> KeyEvent.VK_F12;

            case GLFW.GLFW_KEY_LEFT_SHIFT, GLFW.GLFW_KEY_RIGHT_SHIFT -> KeyEvent.VK_SHIFT;
            case GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_RIGHT_CONTROL -> KeyEvent.VK_CONTROL;
            case GLFW.GLFW_KEY_LEFT_ALT, GLFW.GLFW_KEY_RIGHT_ALT -> KeyEvent.VK_ALT;
            case GLFW.GLFW_KEY_LEFT_SUPER, GLFW.GLFW_KEY_RIGHT_SUPER -> KeyEvent.VK_META;

            case GLFW.GLFW_KEY_MINUS -> KeyEvent.VK_MINUS;
            case GLFW.GLFW_KEY_EQUAL -> KeyEvent.VK_EQUALS;
            case GLFW.GLFW_KEY_BACKSLASH -> KeyEvent.VK_BACK_SLASH;
            case GLFW.GLFW_KEY_SEMICOLON -> KeyEvent.VK_SEMICOLON;
            case GLFW.GLFW_KEY_APOSTROPHE -> KeyEvent.VK_QUOTE;
            case GLFW.GLFW_KEY_COMMA -> KeyEvent.VK_COMMA;
            case GLFW.GLFW_KEY_PERIOD -> KeyEvent.VK_PERIOD;
            case GLFW.GLFW_KEY_SLASH -> KeyEvent.VK_SLASH;
            case GLFW.GLFW_KEY_GRAVE_ACCENT -> KeyEvent.VK_BACK_QUOTE;

            default -> KeyEvent.VK_UNDEFINED;
        };
    }
}
