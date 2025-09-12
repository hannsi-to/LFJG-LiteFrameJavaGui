package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector4f;

public class ClippingRect extends EffectBase {
    private float x1 = 0f;
    private float y1 = 0f;
    private float x2 = 0f;
    private float y2 = 0f;
    private boolean invert = false;

    ClippingRect(String name) {
        super(name, false);
    }

    public static ClippingRect createClippingRect(String name) {
        return new ClippingRect(name);
    }

    public ClippingRect x1(float x1) {
        this.x1 = x1;
        return this;
    }

    public ClippingRect x1(double x1) {
        this.x1 = (float) x1;
        return this;
    }

    public ClippingRect x1(int x1) {
        this.x1 = x1;
        return this;
    }

    public ClippingRect y1(float y1) {
        this.y1 = y1;
        return this;
    }

    public ClippingRect y1(double y1) {
        this.y1 = (float) y1;
        return this;
    }

    public ClippingRect y1(int y1) {
        this.y1 = y1;
        return this;
    }

    public ClippingRect x2(float x2) {
        this.x2 = x2;
        return this;
    }

    public ClippingRect x2(double x2) {
        this.x2 = (float) x2;
        return this;
    }

    public ClippingRect x2(int x2) {
        this.x2 = x2;
        return this;
    }

    public ClippingRect y2(float y2) {
        this.y2 = y2;
        return this;
    }

    public ClippingRect y2(double y2) {
        this.y2 = (float) y2;
        return this;
    }

    public ClippingRect y2(int y2) {
        this.y2 = y2;
        return this;
    }

    public ClippingRect invert(boolean invert) {
        this.invert = invert;
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        LFJGRenderContext.shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.CLIPPING_RECT.getId());
        LFJGRenderContext.shaderProgram.setUniform("clippingRectInvert", UploadUniformType.ON_CHANGE, invert);
        LFJGRenderContext.shaderProgram.setUniform("clippingRectSize", UploadUniformType.ON_CHANGE, new Vector4f(x1, y1, x2, y2));

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public boolean isInvert() {
        return invert;
    }

    public void setInvert(boolean invert) {
        this.invert = invert;
    }
}
