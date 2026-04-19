package me.hannsi.lfjg.testRender.system.material;

import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.ShaderPropertyID;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import me.hannsi.lfjg.testRender.system.RenderQueue;
import org.joml.Matrix4f;

import java.util.*;

public class Material implements Cloneable {
    public static final int BASE_COLOR = ShaderPropertyID.of("_BaseColor");
    public static final int METALLIC = ShaderPropertyID.of("_Metallic");
    public static final int SMOOTHNESS = ShaderPropertyID.of("_Smoothness");
    public static final int MAIN_TEX = ShaderPropertyID.of("_MainTex");
    public static final int EMISSION = ShaderPropertyID.of("_Emission");

    private final String shaderKey;
    private final Map<Integer, MaterialProperty> properties = new LinkedHashMap<>();
    private final Set<String> enabledKeywords = new HashSet<>();
    private int renderQueue = RenderQueue.GEOMETRY;
    private boolean dirty = true;

    public Material(String shaderKey) {
        this.shaderKey = shaderKey;
    }

    public Material setColor(UploadUniformType uniformType, int id, float r, float g, float b, float a) {
        properties.put(id, new MaterialProperty.ColorProp(uniformType, r, g, b, a));
        dirty = true;
        return this;
    }

    public Material setFloat(UploadUniformType uniformType, int id, float value) {
        properties.put(id, new MaterialProperty.FloatProp(uniformType, value));
        dirty = true;
        return this;
    }

    public Material setInt(UploadUniformType uniformType, int id, int value) {
        properties.put(id, new MaterialProperty.IntProp(uniformType, value));
        dirty = true;
        return this;
    }

    public Material setVector(UploadUniformType uniformType, int id, float x, float y, float z, float w) {
        properties.put(id, new MaterialProperty.Vec4Prop(uniformType, x, y, z, w));
        dirty = true;
        return this;
    }

    public Material setTexture(UploadUniformType uniformType, int id, int textureId) {
        properties.put(id, new MaterialProperty.TextureProp(uniformType, textureId));
        dirty = true;
        return this;
    }

    public Material setMatrix(UploadUniformType uniformType, int id, Matrix4f matrix) {
        properties.put(id, new MaterialProperty.MatrixProp(uniformType, new Matrix4f(matrix)));
        dirty = true;
        return this;
    }

    public Optional<MaterialProperty> getProperty(int id) {
        return Optional.ofNullable(properties.get(id));
    }

    public float getFloat(int id) {
        return getProperty(id)
                .map(p -> ((MaterialProperty.FloatProp) p).value())
                .orElse(0f);
    }

    public Material enableKeyword(String keyword) {
        enabledKeywords.add(keyword);
        dirty = true;
        return this;
    }

    public Material disableKeyword(String keyword) {
        enabledKeywords.remove(keyword);
        dirty = true;
        return this;
    }

    public boolean isKeywordEnabled(String keyword) {
        return enabledKeywords.contains(keyword);
    }

    public int getRenderQueue() {
        return renderQueue;
    }

    public Material setRenderQueue(int queue) {
        this.renderQueue = queue;
        return this;
    }

    public String getShaderKey() {
        return shaderKey;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void clearDirty() {
        dirty = false;
    }

    @Override
    public Material clone() {
        Material copy = new Material(this.shaderKey);
        copy.properties.putAll(this.properties);
        copy.enabledKeywords.addAll(this.enabledKeywords);
        copy.renderQueue = this.renderQueue;
        return copy;
    }

//    public void bind(ShaderProgramPool pool) {
//        ShaderProgram sp = pool.get(shaderKey);
//        if (sp == null) {
//            throw new IllegalStateException("Shader not found: " + shaderKey);
//        }
//
//        sp.bind();
//        uploadProperties(sp);
//        clearDirty();
//    }

    private void uploadProperties(ShaderProgram sp) {
        properties.forEach((id, prop) -> {
            String name = ShaderPropertyID.nameOf(id);
            switch (prop) {
                case MaterialProperty.FloatProp p ->
                        sp.setUniform(name, p.uniformType(), p.value());
                case MaterialProperty.IntProp p ->
                        sp.setUniform(name, p.uniformType(), p.value());
                case MaterialProperty.ColorProp p ->
                        sp.setUniform(name, p.uniformType(), p.r(), p.g(), p.b(), p.a());
                case MaterialProperty.Vec4Prop p ->
                        sp.setUniform(name, p.uniformType(), p.x(), p.y(), p.z(), p.w());
                case MaterialProperty.TextureProp p ->
                        sp.setUniform(name, p.uniformType(), p.textureId());
                case MaterialProperty.MatrixProp p ->
                        sp.setUniform(name, p.uniformType(), p.value());
            }
        });
    }
}
