package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;

public class SplitFrameBuffer {
    private final int cols;
    private final int rows;

    private FrameBuffer mainFrameBuffer;
    private FrameBuffer[][] smallFrameBuffers;
    private Vector2f smallResolution;
    private int offsetX;
    private int offsetY;
    private int indexX;
    private int indexY;

    public SplitFrameBuffer(FrameBuffer mainFrameBuffer, int cols, int rows, int offsetX, int offsetY) {
        this.mainFrameBuffer = mainFrameBuffer;
        this.cols = cols;
        this.rows = rows;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.indexX = 0;
        this.indexY = 0;
        this.smallFrameBuffers = new FrameBuffer[rows][cols];
        this.smallResolution = new Vector2f(mainFrameBuffer.getWidth() / cols, mainFrameBuffer.getHeight() / rows);
    }

    public SplitFrameBuffer(FrameBuffer mainFrameBuffer, int cols, int rows) {
        this(mainFrameBuffer, cols, rows, 0, 0);
    }

    public void cleanup() {
        mainFrameBuffer.cleanup();

        StringBuilder ids = new StringBuilder();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                ids.append(smallFrameBuffers[y][x].getFrameBufferId()).append(", ");
                smallFrameBuffers[y][x].cleanup();
            }
        }

        smallFrameBuffers = null;
        smallResolution = null;

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.substring(0, ids.length() - 2),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public void createSmallFrameBuffers() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int srcX = (int) (x * smallResolution.x());
                int srcY = (int) (y * smallResolution.y());
                int srcWidth = (int) (smallResolution.x());
                int srcHeight = (int) (smallResolution.y());

                FrameBuffer frameBuffer = new FrameBuffer(srcX, srcY, srcWidth, srcHeight);
                frameBuffer.createFrameBuffer();
                frameBuffer.createMatrix(new Matrix4f(), mainFrameBuffer.getViewMatrix());

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

                int distX0 = 0;
                int distY0 = 0;
                int distX1 = (int) smallResolution.x();
                int distY1 = (int) smallResolution.y();

                glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, distX0, distY0, distX1, distY1, GL_COLOR_BUFFER_BIT, GL_NEAREST);
            }
        }
    }

    public FrameBuffer getNextFrameBuffer() {
        if (indexY >= smallFrameBuffers.length) {
            return null;
        }

        FrameBuffer frameBuffer = smallFrameBuffers[indexY][indexX];

        indexX++;
        if (indexX >= smallFrameBuffers[indexY].length) {
            indexX = 0;
            indexY++;
        }

        return frameBuffer;
    }

    public void reset() {
        indexX = 0;
        indexY = 0;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
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

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getIndexX() {
        return indexX;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }
}

