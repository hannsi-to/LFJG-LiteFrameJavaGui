package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.rendering.SplitFrameBuffer;

@Setter
@Getter
public class SplitObject extends EffectBase {
    private int rows = 5;
    private int cols = 5;
    private int offsetX = 5;
    private int offsetY = 5;

    private SplitFrameBuffer splitFrameBuffer;

    SplitObject() {
        super(26, "SplitObject");
    }

    public static SplitObject createSplitObject() {
        return new SplitObject();
    }

    public SplitObject rows(int rows) {
        this.rows = rows;
        return this;
    }

    public SplitObject cols(int cols) {
        this.cols = cols;
        return this;
    }

    public SplitObject offsetX(int offsetX) {
        this.offsetX = offsetX;
        return this;
    }

    public SplitObject offsetY(int offsetY) {
        this.offsetY = offsetY;
        return this;
    }

    /**
     * Pushes the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();
        super.frameBufferPush(baseGLObject);
    }

    /**
     * Pops the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();
        super.frameBufferPop(baseGLObject);
    }

    /**
     * Draws the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBuffer(GLObject baseGLObject) {
        if (splitFrameBuffer == null) {
            splitFrameBuffer = new SplitFrameBuffer(getFrameBuffer(), cols, rows, offsetX, offsetY);
            splitFrameBuffer.createSmallFrameBuffers();
        }

        splitFrameBuffer.setMainFrameBuffer(getFrameBuffer());
        splitFrameBuffer.blitToSmallFrameBuffers();

        FrameBuffer[][] smallFrameBuffers = splitFrameBuffer.getSmallFrameBuffers();
        int ox = 0;
        int oy = 0;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                FrameBuffer smallFrameBuffer = smallFrameBuffers[y][x];

                smallFrameBuffer.getModelMatrix().translate(ox, oy, 0);
                smallFrameBuffer.drawFrameBuffer();
                smallFrameBuffer.getModelMatrix().translate(-ox, -oy, 0);

                ox += offsetX;
            }

            ox = 0;
            oy += offsetY;
        }

        super.frameBuffer(baseGLObject);
    }

    /**
     * Sets the uniform variables for the shader program.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void setUniform(GLObject baseGLObject) {
        super.setUniform(baseGLObject);
    }

}
