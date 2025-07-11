package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.rendering.SplitFrameBuffer;

@Getter
public class SplitObject extends EffectBase {
    private final EffectCache effectCache;
    @Setter
    private int rows;
    @Setter
    private int cols;
    @Setter
    private int offsetX;
    @Setter
    private int offsetY;
    @Setter
    private SplitFrameBuffer splitFrameBuffer;

    public SplitObject(int rows, int cols, int offsetX, int offsetY) {
        this(rows, cols, offsetX, offsetY, null);
    }

    public SplitObject(int rows, int cols, int offsetX, int offsetY, EffectCache effectCache) {
        super(26, "SplitObject");

        this.cols = cols;
        this.rows = rows;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.effectCache = effectCache;
        if (this.effectCache != null) {
            this.effectCache.createCache("DrawFrameBuffer1", DrawFrameBuffer.createDrawFrameBuffer(), 0);
        }
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

                if (effectCache == null) {
                    smallFrameBuffer.getModelMatrix().translate(ox, oy, 0);
                    smallFrameBuffer.drawFrameBuffer();
                    smallFrameBuffer.getModelMatrix().translate(-ox, -oy, 0);
                } else {
                    effectCache.updateFrameBufferSize(smallFrameBuffer);

                    DrawFrameBuffer drawFrameBuffer = (DrawFrameBuffer) effectCache.getEffectBase("DrawFrameBuffer1");
                    drawFrameBuffer.setFrameBuffer(smallFrameBuffer);
                    drawFrameBuffer.setTranslateX(ox);
                    drawFrameBuffer.setTranslateY(oy);
                    effectCache.frameBuffer(baseGLObject);
                }

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
