package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector4f;

public class EdgeExtraction extends EffectBase {
    private float edgeStrength = 0.5f;
    private float threshold = 0.1f;
    private boolean enableLuminanceEdge = false;
    private boolean enableAlphaEdge = true;
    private Color edgeColor = Color.BLUE;

    EdgeExtraction() {
        super(Location.fromResource("shader/frameBuffer/filter/EdgeExtraction.fsh"), true, 14, "EdgeExtraction");
    }

    public static EdgeExtraction createEdgeExtraction() {
        return new EdgeExtraction();
    }

    public EdgeExtraction edgeStrength(float edgeStrength) {
        this.edgeStrength = edgeStrength;
        return this;
    }

    public EdgeExtraction edgeStrength(double edgeStrength) {
        this.edgeStrength = (float) edgeStrength;
        return this;
    }

    public EdgeExtraction threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public EdgeExtraction threshold(double threshold) {
        this.threshold = (float) threshold;
        return this;
    }

    public EdgeExtraction enableLuminanceEdge(boolean enableLuminanceEdge) {
        this.enableLuminanceEdge = enableLuminanceEdge;
        return this;
    }

    public EdgeExtraction enableAlphaEdge(boolean enableAlphaEdge) {
        this.enableAlphaEdge = enableAlphaEdge;
        return this;
    }

    public EdgeExtraction edgeColor(Color edgeColor) {
        this.edgeColor = edgeColor;
        return this;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("edgeStrength", UploadUniformType.ON_CHANGE, edgeStrength);
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", UploadUniformType.ON_CHANGE, threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform("enableLuminanceEdge", UploadUniformType.ON_CHANGE, enableLuminanceEdge);
        getFrameBuffer().getShaderProgramFBO().setUniform("enableAlphaEdge", UploadUniformType.ON_CHANGE, enableAlphaEdge);
        getFrameBuffer().getShaderProgramFBO().setUniform("edgeColor", UploadUniformType.ON_CHANGE, new Vector4f(edgeColor.getRedF(), edgeColor.getGreenF(), edgeColor.getBlueF(), edgeColor.getAlphaF()));

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