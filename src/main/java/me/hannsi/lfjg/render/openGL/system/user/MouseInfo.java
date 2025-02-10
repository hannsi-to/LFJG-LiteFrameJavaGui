package me.hannsi.lfjg.render.openGL.system.user;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Represents the mouse information in the OpenGL rendering system.
 * Handles mouse position, displacement, button states, and window focus.
 */
public class MouseInfo {
    private Vector2f currentPos;
    private Vector2f displVec;
    private boolean inWindow;
    private boolean leftButtonPressed;
    private Vector2f previousPos;
    private boolean rightButtonPressed;

    /**
     * Constructs a new MouseInfo instance with default values.
     */
    public MouseInfo() {
        previousPos = new Vector2f(-1, -1);
        currentPos = new Vector2f();
        displVec = new Vector2f();
        leftButtonPressed = false;
        rightButtonPressed = false;
        inWindow = false;
    }

    public void cleanup() {
        currentPos = null;
        displVec = null;
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
        leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
        rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
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
     * Gets the current cursor position.
     *
     * @return the current cursor position
     */
    public Vector2f getCurrentPos() {
        return currentPos;
    }

    /**
     * Sets the current cursor position.
     *
     * @param currentPos the new cursor position
     */
    public void setCurrentPos(Vector2f currentPos) {
        this.currentPos = currentPos;
    }

    /**
     * Gets the displacement vector.
     *
     * @return the displacement vector
     */
    public Vector2f getDisplVec() {
        return displVec;
    }

    /**
     * Sets the displacement vector.
     *
     * @param displVec the new displacement vector
     */
    public void setDisplVec(Vector2f displVec) {
        this.displVec = displVec;
    }

    /**
     * Processes the mouse input to calculate the displacement vector.
     */
    public void input() {
        displVec.x = 0;
        displVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    /**
     * Checks if the left mouse button is pressed.
     *
     * @return true if the left mouse button is pressed, false otherwise
     */
    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    /**
     * Sets the state of the left mouse button.
     *
     * @param leftButtonPressed true if the left mouse button is pressed, false otherwise
     */
    public void setLeftButtonPressed(boolean leftButtonPressed) {
        this.leftButtonPressed = leftButtonPressed;
    }

    /**
     * Checks if the right mouse button is pressed.
     *
     * @return true if the right mouse button is pressed, false otherwise
     */
    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    /**
     * Sets the state of the right mouse button.
     *
     * @param rightButtonPressed true if the right mouse button is pressed, false otherwise
     */
    public void setRightButtonPressed(boolean rightButtonPressed) {
        this.rightButtonPressed = rightButtonPressed;
    }

    /**
     * Checks if the mouse is in the window.
     *
     * @return true if the mouse is in the window, false otherwise
     */
    public boolean isInWindow() {
        return inWindow;
    }

    /**
     * Sets whether the mouse is in the window.
     *
     * @param inWindow true if the mouse is in the window, false otherwise
     */
    public void setInWindow(boolean inWindow) {
        this.inWindow = inWindow;
    }

    /**
     * Gets the previous cursor position.
     *
     * @return the previous cursor position
     */
    public Vector2f getPreviousPos() {
        return previousPos;
    }

    /**
     * Sets the previous cursor position.
     *
     * @param previousPos the new previous cursor position
     */
    public void setPreviousPos(Vector2f previousPos) {
        this.previousPos = previousPos;
    }
}