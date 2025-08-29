package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector4f;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

public class EdgeExtraction extends EffectBase {
    private float edgeStrength = 0.5f;
    private float threshold = 0.1f;
    private boolean enableLuminanceEdge = false;
    private boolean enableAlphaEdge = true;
    private Color edgeColor = Color.BLUE;

    EdgeExtraction(String name) {
        super(name, false);
    }

    public static EdgeExtraction createEdgeExtraction(String name) {
        return new EdgeExtraction(name);
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
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.EDGE_EXTRACTION.getId());
        shaderProgram.setUniform("edgeExtractionEdgeStrength", UploadUniformType.ON_CHANGE, edgeStrength);
        shaderProgram.setUniform("edgeExtractionThreshold", UploadUniformType.ON_CHANGE, threshold);
        shaderProgram.setUniform("edgeExtractionEnableLuminanceEdge", UploadUniformType.ON_CHANGE, enableLuminanceEdge);
        shaderProgram.setUniform("edgeExtractionEnableAlphaEdge", UploadUniformType.ON_CHANGE, enableAlphaEdge);
        shaderProgram.setUniform("edgeExtractionEdgeColor", UploadUniformType.ON_CHANGE, new Vector4f(edgeColor.getRedF(), edgeColor.getGreenF(), edgeColor.getBlueF(), edgeColor.getAlphaF()));

        super.drawFrameBuffer(latestFrameBuffer);
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
