package me.hannsi.lfjg.core.utils.toolkit;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.Core;
import org.joml.Vector2f;

/**
 * Represents the mouse information in the OpenGL rendering system.
 * Handles mouse position, displacement, button states, and window focus.
 */
@Getter
@Setter
public class MouseInfo {
    /**
     * -- SETTER --
     * Sets the current cursor position.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the current cursor position.
     *
     * @param currentPos the new cursor position
     * @return the current cursor position
     */
    private Vector2f currentPos;
    /**
     * -- SETTER --
     * Sets the displacement vector.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the displacement vector.
     *
     * @param displaySize the new displacement vector
     * @return the displacement vector
     */
    private Vector2f displaySize;
    /**
     * -- SETTER --
     * Sets whether the mouse is in the window.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the mouse is in the window.
     *
     * @param inWindow true if the mouse is in the window, false otherwise
     * @return true if the mouse is in the window, false otherwise
     */
    private boolean inWindow;
    /**
     * -- SETTER --
     * Sets the state of the left mouse button.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the left mouse button is pressed.
     *
     * @param leftButtonPressed true if the left mouse button is pressed, false otherwise
     * @return true if the left mouse button is pressed, false otherwise
     */
    private boolean leftButtonPressed;
    /**
     * -- SETTER --
     * Sets the previous cursor position.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the previous cursor position.
     *
     * @param previousPos the new previous cursor position
     * @return the previous cursor position
     */
    private Vector2f previousPos;
    /**
     * -- SETTER --
     * Sets the state of the right mouse button.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the right mouse button is pressed.
     *
     * @param rightButtonPressed true if the right mouse button is pressed, false otherwise
     * @return true if the right mouse button is pressed, false otherwise
     */
    private boolean rightButtonPressed;

    /**
     * Constructs a new MouseInfo instance with default values.
     */
    public MouseInfo() {
        previousPos = new Vector2f(-1, -1);
        currentPos = new Vector2f();
        displaySize = new Vector2f();
        leftButtonPressed = false;
        rightButtonPressed = false;
        inWindow = false;
    }

    public void cleanup() {
        currentPos = null;
        displaySize = null;
        previousPos = null;
    }

    /**
     * Updates the current cursor position.
     *
     * @param xpos the X position of the cursor
     * @param ypos the Y position of the cursor
     */
    public void updateCursorPos(float xpos, float ypos) {
        currentPos.x = xpos;
        currentPos.y = ypos;
    }

    /**
     * Updates the state of the mouse buttons.
     *
     * @param button the mouse button (e.g., GLFW_MOUSE_BUTTON_1)
     * @param action the action (e.g., GLFW_PRESS)
     */
    public void updateMouseButton(int button, int action) {
        leftButtonPressed = button == Core.GLFW.GLFW_MOUSE_BUTTON_1 && action == Core.GLFW.GLFW_PRESS;
        rightButtonPressed = button == Core.GLFW.GLFW_MOUSE_BUTTON_2 && action == Core.GLFW.GLFW_PRESS;
    }

    /**
     * Updates whether the mouse is in the window.
     *
     * @param inWindow true if the mouse is in the window, false otherwise
     */
    public void updateInWindow(boolean inWindow) {
        this.inWindow = inWindow;
    }

    /**
     * Processes the mouse input to calculate the displacement vector.
     */
    public void input() {
        displaySize.x = 0;
        displaySize.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displaySize.y = (float) deltax;
            }
            if (rotateY) {
                displaySize.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    public boolean isInside(float x, float y, float width, float height) {
        return x <= currentPos.x && currentPos.x <= x + width && y <= currentPos.y && currentPos.y <= y + height;
    }

}