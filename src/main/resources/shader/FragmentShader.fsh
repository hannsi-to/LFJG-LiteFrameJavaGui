#version 330

in vec4 fragPosition;
in vec4 outColor;

out vec4 fragColor;

uniform vec2 resolution;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

vec4 color;

#include "shader/ColorCorrection.glsl"
#include "shader/Clipping.glsl"

void main()
{
    clippingRect2D();

    color = outColor;

    color = vec4(applyColorCorrection(color.rgb), color.a);

    if (fragPosition.x < 100){
        fragColor = vec4(0, 0, 0, 0);
    }

    fragColor = color;
}