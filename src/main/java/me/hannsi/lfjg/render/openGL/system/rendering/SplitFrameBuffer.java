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
    private int cols;
    private int rows;

    /**
     * Constructs a new SplitFrameBuffer with the specified main frame buffer, columns, and rows.
     *
     * @param mainFrameBuffer the main frame buffer
     * @param cols            the number of columns
     * @param rows            the number of rows
     */
    public SplitFrameBuffer(FrameBuffer mainFrameBuffer, int cols, int rows) {
        this.mainFrameBuffer = mainFrameBuffer;
        this.cols = cols;
        this.rows = rows;
        this.smallResolution = new Vector2f(LFJGContext.resolution.x() / cols, LFJGContext.resolution.y() / rows);
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
                FrameBuffer frameBuffer = new FrameBuffer();
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
     * Sets the number of columns.
     *
     * @param cols the new number of columns
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Gets the number of rows.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows.
     *
     * @param rows the new number of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }
}