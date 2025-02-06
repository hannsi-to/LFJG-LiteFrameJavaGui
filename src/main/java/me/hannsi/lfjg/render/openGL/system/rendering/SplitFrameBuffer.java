package me.hannsi.lfjg.render.openGL.system.rendering;

import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;

public class SplitFrameBuffer {
    private FrameBuffer mainFrameBuffer;
    private FrameBuffer[][] smallFrameBuffers;
    private Vector2f smallResolution;
    private int cols;
    private int rows;

    public SplitFrameBuffer(FrameBuffer mainFrameBuffer, int cols, int rows) {
        this.mainFrameBuffer = mainFrameBuffer;
        this.cols = cols;
        this.rows = rows;
        this.smallResolution = new Vector2f(mainFrameBuffer.getResolution().x() / cols, mainFrameBuffer.getResolution().y() / rows);
    }

    public void createSmallFrameBuffers() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                FrameBuffer frameBuffer = new FrameBuffer(smallResolution);
                frameBuffer.createFrameBuffer();
                frameBuffer.createShaderProgram();

                smallFrameBuffers[y][x] = frameBuffer;
            }
        }
    }

    public void blitToSmallFrameBuffers() {
        mainFrameBuffer.bindReadFrameBuffer();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                smallFrameBuffers[y][x].bindDrawFrameBuffer();

                int srcX0 = (int) (x * smallResolution.x());
                int srcY0 = (int) (y * smallResolution.y());
                int srcX1 = (int) (srcX0 + smallResolution.x());
                int srcY1 = (int) (srcY0 + smallResolution.y());

                glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, 0, 0, (int) smallResolution.x(), (int) smallResolution.y(), GL_COLOR_BUFFER_BIT, GL_NEAREST);

                smallFrameBuffers[y][x].unbindDrawFrameBuffer();
            }
        }

        mainFrameBuffer.unbindRenderBuffer();
    }

    public FrameBuffer getSmallFrameBuffer(int x, int y) {
        return smallFrameBuffers[y][x];
    }

    public void cleanup() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                smallFrameBuffers[y][x].cleanup();
            }
        }
    }

    public FrameBuffer getMainFrameBuffer() {
        return mainFrameBuffer;
    }

    public void setMainFrameBuffer(FrameBuffer mainFrameBuffer) {
        this.mainFrameBuffer = mainFrameBuffer;
    }

    public FrameBuffer[][] getSmallFrameBuffers() {
        return smallFrameBuffers;
    }

    public void setSmallFrameBuffers(FrameBuffer[][] smallFrameBuffers) {
        this.smallFrameBuffers = smallFrameBuffers;
    }

    public Vector2f getSmallResolution() {
        return smallResolution;
    }

    public void setSmallResolution(Vector2f smallResolution) {
        this.smallResolution = smallResolution;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
