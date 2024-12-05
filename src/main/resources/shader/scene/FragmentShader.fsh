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

vec4 blendColor;

#include "shader/scene/effect/ColorCorrection.glsl"
#include "shader/scene/effect/Clipping.glsl"

void main()
{
    clippingRect2D();

    blendColor = texture(textureSampler, outTexture) + outColor;

    blendColor = vec4(applyColorCorrection(blendColor.rgb), blendColor.a);

    fragColor = blendColor;
}