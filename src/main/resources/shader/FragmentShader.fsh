#version 330

in vec4 outPosition;
in vec4 outColor;
in vec2 outTexture;

out vec4 fragColor;

uniform vec2 resolution;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

uniform sampler2D textureSampler;

vec4 color;

#include "shader/ColorCorrection.glsl"
#include "shader/Clipping.glsl"

void main()
{
    clippingRect2D();

    color = texture(textureSampler, outTexture) + outColor;

    color = vec4(applyColorCorrection(color.rgb), color.a);

    fragColor = color;
}