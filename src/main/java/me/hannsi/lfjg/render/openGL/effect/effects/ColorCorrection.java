package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.shader.ShaderUtil;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VAO;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VBO;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.color.ColorUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ColorCorrection extends EffectBase {
    private float brightness;
    private float contrast;
    private float saturation;
    private float hue;
    private float gamma;
    private ShaderUtil shaderUtil;

    public ColorCorrection(float brightness, float contrast, float saturation, float hue, float gamma) {
        super(3, "ColorCorrection", null);

        this.brightness = brightness;
        this.contrast = contrast;
        this.saturation = saturation;
        this.hue = hue;
        this.gamma = gamma;
    }

    @Override
    public void pop(Frame frame, GLPolygon basePolygon) {
        super.pop(frame, basePolygon);
    }

    @Override
    public void push(Frame frame, GLPolygon basePolygon) {
        basePolygon.setFragmentShader(new ResourcesLocation("shader/ColorCorrection.fsh"));
        shaderUtil = new ShaderUtil(basePolygon.getFragmentShader());

        shaderUtil.getGlslSandboxShader().useShader();

        shaderUtil.getGlslSandboxShader().setUniform1f("brightness",brightness);
        shaderUtil.getGlslSandboxShader().setUniform1f("contrast",contrast);
        shaderUtil.getGlslSandboxShader().setUniform1f("saturation",saturation);
        shaderUtil.getGlslSandboxShader().setUniform1f("hue",hue);
        shaderUtil.getGlslSandboxShader().setUniform1f("gamma",gamma);

        shaderUtil.getGlslSandboxShader().finishShader();

        basePolygon.setShaderUtil(shaderUtil);

        super.push(frame,basePolygon);
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getContrast() {
        return contrast;
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public float getGamma() {
        return gamma;
    }

    public void setGamma(float gamma) {
        this.gamma = gamma;
    }
}
