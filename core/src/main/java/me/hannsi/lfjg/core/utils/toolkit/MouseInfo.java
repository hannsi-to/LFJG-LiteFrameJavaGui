package me.hannsi.lfjg.core.utils.toolkit;

import org.joml.Vector2f;

import static me.hannsi.lfjg.core.Core.GLFW.*;

public class MouseInfo {
    private Vector2f currentPos;
    private Vector2f displaySize;
    private boolean inWindow;
    private boolean leftButtonPressed;
    private Vector2f previousPos;
    private boolean rightButtonPressed;

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

    public void updateCursorPos(float xpos, float ypos) {
        currentPos.x = xpos;
        currentPos.y = ypos;
    }

    public void updateMouseButton(int button, int action) {
        leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
        rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
    }

    public void updateInWindow(boolean inWindow) {
        this.inWindow = inWindow;
    }

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

    public Vector2f getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Vector2f currentPos) {
        this.currentPos = currentPos;
    }

    public Vector2f getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(Vector2f displaySize) {
        this.displaySize = displaySize;
    }

    public boolean isInWindow() {
        return inWindow;
    }

    public void setInWindow(boolean inWindow) {
        this.inWindow = inWindow;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public void setLeftButtonPressed(boolean leftButtonPressed) {
        this.leftButtonPressed = leftButtonPressed;
    }

    public Vector2f getPreviousPos() {
        return previousPos;
    }

    public void setPreviousPos(Vector2f previousPos) {
        this.previousPos = previousPos;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public void setRightButtonPressed(boolean rightButtonPressed) {
        this.rightButtonPressed = rightButtonPressed;
    }
}