package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class EdgeExtraction extends EffectBase {
    private float edgeStrength;
    private float threshold;
    private boolean enableLuminanceEdge;
    private boolean enableAlphaEdge;
    private Color edgeColor;

    public EdgeExtraction(Vector2f resolution, float edgeStrength, float threshold, boolean enableLuminanceEdge, boolean enableAlphaEdge, Color edgeColor) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/EdgeExtraction.fsh"), true, 14, "EdgeExtraction");

        this.edgeStrength = edgeStrength;
        this.threshold = threshold;
        this.enableLuminanceEdge = enableLuminanceEdge;
        this.enableAlphaEdge = enableAlphaEdge;
        this.edgeColor = edgeColor;
    }

    public EdgeExtraction(Vector2f resolution, double edgeStrength, double threshold, boolean enableLuminanceEdge, boolean enableAlphaEdge, Color edgeColor) {
        this(resolution, (float) edgeStrength, (float) threshold, enableLuminanceEdge, enableAlphaEdge, edgeColor);
    }

    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();

        super.frameBufferPush(baseGLObject);
    }

    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();

        super.frameBufferPop(baseGLObject);
    }

    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();

        super.frameBuffer(baseGLObject);
    }

    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().getShaderProgramFBO().setUniform("edgeStrength", edgeStrength);
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform("enableLuminanceEdge", enableLuminanceEdge);
        getFrameBuffer().getShaderProgramFBO().setUniform("enableAlphaEdge", enableAlphaEdge);
        getFrameBuffer().getShaderProgramFBO().setUniform("edgeColor", new Vector4f(edgeColor.getRedF(), edgeColor.getGreenF(), edgeColor.getBlueF(), edgeColor.getAlphaF()));

        super.setUniform(baseGLObject);
    }

    public float getEdgeStrength() {
        return edgeStrength;
    }

    public void setEdgeStrength(float edgeStrength) {
        this.edgeStrength = edgeStrength;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public boolean isEnableLuminanceEdge() {
        return enableLuminanceEdge;
    }

    public void setEnableLuminanceEdge(boolean enableLuminanceEdge) {
        this.enableLuminanceEdge = enableLuminanceEdge;
    }

    public boolean isEnableAlphaEdge() {
        return enableAlphaEdge;
    }

    public void setEnableAlphaEdge(boolean enableAlphaEdge) {
        this.enableAlphaEdge = enableAlphaEdge;
    }

    public Color getEdgeColor() {
        return edgeColor;
    }

    public void setEdgeColor(Color edgeColor) {
        this.edgeColor = edgeColor;
    }
}
