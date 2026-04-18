package me.hannsi.lfjg.frame.event.events.user;

import me.hannsi.lfjg.core.event.Event;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static org.lwjgl.glfw.GLFW.*;

public class MouseButtonEvent extends Event {
    private final long window;
    private final ButtonType buttonType;
    private final ActionType actionType;
    private final int mods;

    public MouseButtonEvent(long window, int button, int action, int mods) {
        this.window = window;
        this.buttonType = ButtonType.getButtonType(button);
        this.actionType = ActionType.getActionType(action);
        this.mods = mods;
    }

    public long getWindow() {
        return window;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getMods() {
        return mods;
    }

    public enum ActionType implements IEnumTypeBase {
        PRESS(GLFW_PRESS, "Press"),
        RELEASE(GLFW_RELEASE, "Release");

        final int id;
        final String name;

        ActionType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static ActionType getActionType(int id) {
            for (ActionType value : ActionType.values()) {
                if (value.getId() == id) {
                    return value;
                }
            }

            return null;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum ButtonType implements IEnumTypeBase {
        BUTTON_LEFT(GLFW_MOUSE_BUTTON_LEFT, "ButtonLeft"),
        BUTTON_RIGHT(GLFW_MOUSE_BUTTON_RIGHT, "ButtonRight"),
        BUTTON_MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE, "ButtonMiddle");

        final int id;
        final String name;

        ButtonType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static ButtonType getButtonType(int id) {
            for (ButtonType value : ButtonType.values()) {
                if (value.getId() == id) {
                    return value;
                }
            }

            return null;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
