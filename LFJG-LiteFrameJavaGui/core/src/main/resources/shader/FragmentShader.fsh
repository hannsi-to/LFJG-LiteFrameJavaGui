#version 330

in vec4 outPosition;
in vec4 outColor;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform int fragmentShaderType;

#include "shader/frameBuffer/util/Luminance.glsl"
#include "shader/frameBuffer/util/Blend.glsl"

#include "shader/scene/object/FragmentShader.fsh"
#include "shader/scene/model/FragmentShader.fsh"
#include "shader/frameBuffer/FragmentShader.fsh"

void main() {
    if(fragmentShaderType == 0){
        objectMain();
    }else if(fragmentShaderType == 1){
        modelMain();
    }else if(fragmentShaderType == 2){
        frameBufferMain();
    }
}