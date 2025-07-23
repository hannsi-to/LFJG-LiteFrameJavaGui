#version 330

in vec4 outPosition;
in vec4 outColor;
in vec2 outTexture;

out vec4 fragColor;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

uniform sampler2D textureSampler;
uniform int blendMode;

#include "shader/frameBuffer/util/Blend.glsl"

void main()
{
    vec4 texColor = texture(textureSampler, outTexture);
    fragColor = blend(texColor, outColor, blendMode);
}