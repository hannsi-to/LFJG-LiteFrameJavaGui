package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector2i;

public class RadialBlur extends EffectBase {
    private Vector2i resolution = new Vector2i();
    private float range = 1f;
    private float centerX = 500f;
    private float centerY = 500f;

    RadialBlur() {
        super(Location.fromResource("shader/frameBuffer/filter/RadialBlur.fsh"), true, 18, "RadialBlur");
    }

    public static RadialBlur createRadialBlur() {
        return new RadialBlur();
    }

    public RadialBlur resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
    }

    public RadialBlur range(float range) {
        this.range = range;
        return this;
    }

    public RadialBlur range(double range) {
        this.range = (float) range;
        return this;
    }

    public RadialBlur centerX(float centerX) {
        this.centerX = centerX;
        return this;
    }

    public RadialBlur centerX(double centerX) {
        this.centerX = (float) centerX;
        return this;
    }

    public RadialBlur centerY(float centerY) {
        this.centerY = centerY;
        return this;
    }

    public RadialBlur centerY(double centerY) {
        this.centerY = (float) centerY;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("range", UploadUniformType.ON_CHANGE, range);
        getFrameBuffer().getShaderProgramFBO().setUniform("centerX", UploadUniformType.ON_CHANGE, centerX / resolution.x);
        getFrameBuffer().getShaderProgramFBO().setUniform("centerY", UploadUniformType.ON_CHANGE, centerY / resolution.y);

        super.setUniform(baseGLObject);
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public void setResolution(Vector2i resolution) {
        this.resolution = resolution;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }
}