package me.hannsi.lfjg.render.openGL.system.rendering;

import me.hannsi.lfjg.frame.LFJGContext;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;

/**
 * Represents a split frame buffer in the OpenGL rendering system.
 */
public class SplitFrameBuffer {
    private FrameBuffer mainFrameBuffer;
    private FrameBuffer[][] smallFrameBuffers;
    private Vector2f smallResolution;
    private final int cols;
    private final int rows;
    private int offsetX;
    private int offsetY;
    private int indexX;
    private int indexY;

    /**
     * Constructs a new SplitFrameBuffer with the specified main frame buffer, columns, and rows.
     *
     * @param mainFrameBuffer the main frame buffer
     * @param cols            the number of columns
     * @param rows            the number of rows
     */
    public SplitFrameBuffer(FrameBuffer mainFrameBuffer, int cols, int rows, int offsetX, int offsetY) {
        this.mainFrameBuffer = mainFrameBuffer;
        this.cols = cols;
        this.rows = rows;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.indexX = 0;
        this.indexY = 0;
        this.smallFrameBuffers = new FrameBuffer[rows][cols];
        this.smallResolution = new Vector2f(LFJGContext.resolution.x() / cols, LFJGContext.resolution.y() / rows);
    }

    public SplitFrameBuffer(FrameBuffer mainFrameBuffer, int cols, int rows) {
        this(mainFrameBuffer, cols, rows, 0, 0);
    }

    /**
     * Cleans up all small frame buffers.
     */
    public void cleanup() {
        mainFrameBuffer.cleanup();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                smallFrameBuffers[y][x].cleanup();
            }
        }

        smallFrameBuffers = null;
        smallResolution = null;
    }

    /**
     * Creates the small frame buffers based on the specified columns and rows.
     */
    public void createSmallFrameBuffers() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                FrameBuffer frameBuffer = new FrameBuffer(0, 0, LFJGContext.resolution.x(), LFJGContext.resolution.y());
                frameBuffer.createFrameBuffer();
                frameBuffer.createShaderProgram();

                smallFrameBuffers[y][x] = frameBuffer;
            }
        }
    }

    /**
     * Blits the main frame buffer to the small frame buffers.
     */
    public void blitToSmallFrameBuffers() {
        mainFrameBuffer.bindReadFrameBuffer();

        int ox = 0;
        int oy = 0;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                smallFrameBuffers[y][x].bindDrawFrameBuffer();

                int srcX0 = (int) (x * smallResolution.x());
                int srcY0 = (int) (y * smallResolution.y());
                int srcX1 = (int) (srcX0 + smallResolution.x());
                int srcY1 = (int) (srcY0 + smallResolution.y());

                glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, srcX0 + ox, srcY0 + oy, srcX1 + ox, srcY1 + oy, GL_COLOR_BUFFER_BIT, GL_NEAREST);

                smallFrameBuffers[y][x].unbindDrawFrameBuffer();
                ox += offsetX;
            }

            ox = 0;
            oy += offsetY;
        }

        mainFrameBuffer.unbindRenderBuffer();
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

    /**
     * Gets the small frame buffer at the specified column and row.
     *
     * @param x the column index
     * @param y the row index
     * @return the small frame buffer at the specified column and row
     */
    public FrameBuffer getSmallFrameBuffer(int x, int y) {
        return smallFrameBuffers[y][x];
    }

    /**
     * Gets the main frame buffer.
     *
     * @return the main frame buffer
     */
    public FrameBuffer getMainFrameBuffer() {
        return mainFrameBuffer;
    }

    /**
     * Sets the main frame buffer.
     *
     * @param mainFrameBuffer the new main frame buffer
     */
    public void setMainFrameBuffer(FrameBuffer mainFrameBuffer) {
        this.mainFrameBuffer = mainFrameBuffer;
    }

    /**
     * Gets the small frame buffers.
     *
     * @return the small frame buffers
     */
    public FrameBuffer[][] getSmallFrameBuffers() {
        return smallFrameBuffers;
    }

    /**
     * Sets the small frame buffers.
     *
     * @param smallFrameBuffers the new small frame buffers
     */
    public void setSmallFrameBuffers(FrameBuffer[][] smallFrameBuffers) {
        this.smallFrameBuffers = smallFrameBuffers;
    }

    /**
     * Gets the resolution of the small frame buffers.
     *
     * @return the resolution of the small frame buffers
     */
    public Vector2f getSmallResolution() {
        return smallResolution;
    }

    /**
     * Sets the resolution of the small frame buffers.
     *
     * @param smallResolution the new resolution of the small frame buffers
     */
    public void setSmallResolution(Vector2f smallResolution) {
        this.smallResolution = smallResolution;
    }

    /**
     * Gets the number of columns.
     *
     * @return the number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gets the number of rows.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
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
}