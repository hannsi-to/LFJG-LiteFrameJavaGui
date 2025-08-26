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
#include "shader/msdf/FragmentShader.fsh"

void main() {
    switch(fragmentShaderType){
        case 0: objectMain(); break;
        case 1: modelMain(); break;
        case 2: frameBufferMain(); break;
        case 3: msdfMain(); break;
    }
}