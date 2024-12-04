#version 330

in  vec4 outColor;
out vec4 fragColor;

vec4 color;

#include "shader/ColorCorrection.glsl"

void main()
{
    color = outColor;

    color = vec4(applyColorCorrection(color.rgb), color.a);

    fragColor = color;
}