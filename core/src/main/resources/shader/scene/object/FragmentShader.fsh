#version 330

in vec4 outPosition;
in vec4 outColor;
in vec2 outTexture;

out vec4 fragColor;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

uniform sampler2D textureSampler;
uniform vec4 color;
uniform int blendMode;
uniform bool replaceColor;

#include "shader/frameBuffer/util/Blend.glsl"

void main()
{
    vec4 texColor = texture(textureSampler, outTexture);
    vec4 newColor;
    if (replaceColor){
        newColor = color;
    } else {
        newColor = outColor;
    }

    fragColor = blend(texColor, newColor, blendMode);
}