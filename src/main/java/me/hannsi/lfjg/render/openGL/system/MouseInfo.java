package me.hannsi.lfjg.render.openGL.system;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInfo {
    private Vector2f currentPos;
    private Vector2f displVec;
    private boolean inWindow;
    private boolean leftButtonPressed;
    private Vector2f previousPos;
    private boolean rightButtonPressed;

    public MouseInfo() {
        previousPos = new Vector2f(-1, -1);
        currentPos = new Vector2f();
        displVec = new Vector2f();
        leftButtonPressed = false;
        rightButtonPressed = false;
        inWindow = false;
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

    public Vector2f getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Vector2f currentPos) {
        this.currentPos = currentPos;
    }

    public Vector2f getDisplVec() {
        return displVec;
    }

    public void setDisplVec(Vector2f displVec) {
        this.displVec = displVec;
    }

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

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public void setLeftButtonPressed(boolean leftButtonPressed) {
        this.leftButtonPressed = leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public void setRightButtonPressed(boolean rightButtonPressed) {
        this.rightButtonPressed = rightButtonPressed;
    }

    public boolean isInWindow() {
        return inWindow;
    }

    public void setInWindow(boolean inWindow) {
        this.inWindow = inWindow;
    }

    public Vector2f getPreviousPos() {
        return previousPos;
    }

    public void setPreviousPos(Vector2f previousPos) {
        this.previousPos = previousPos;
    }
}