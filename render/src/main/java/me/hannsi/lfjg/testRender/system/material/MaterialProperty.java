package me.hannsi.lfjg.testRender.system.material;

import me.hannsi.lfjg.testRender.system.shader.UploadUniformType;
import org.joml.Matrix4f;

public sealed interface MaterialProperty permits MaterialProperty.FloatProp, MaterialProperty.ColorProp, MaterialProperty.Vec4Prop, MaterialProperty.TextureProp, MaterialProperty.IntProp, MaterialProperty.MatrixProp {
    record FloatProp(UploadUniformType uniformType, float value) implements MaterialProperty {}

    record ColorProp(UploadUniformType uniformType, float r, float g, float b, float a) implements MaterialProperty {}

    record Vec4Prop(UploadUniformType uniformType, float x, float y, float z, float w) implements MaterialProperty {}

    record TextureProp(UploadUniformType uniformType, int textureId) implements MaterialProperty {}

    record IntProp(UploadUniformType uniformType, int value) implements MaterialProperty {}

    record MatrixProp(UploadUniformType uniformType, Matrix4f value) implements MaterialProperty {}
}
