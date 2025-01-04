#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform float brightness = 0.0;
uniform float contrast = 0.0;
uniform float saturation = 0.0;
uniform float hue = 0.0;

#include "shader/frameBuffer/util/MathUtil.glsl"

vec3 applyBrightness(vec3 color,float value){
    vec3 rgb = color.rgb + value;
    return rgb;
}

vec3 applyContrast(vec3 color, float value) {
    vec3 rgb = 0.5 + (1.0 + value) * (color.rgb - 0.5);
    return rgb;
}

vec3 desaturated(vec3 color) {
    vec3 luma = vec3(0.2126, 0.7152, 0.0722);
    vec3 rgb = vec3(dot(color, luma));
    return rgb;
}

vec3 applySaturation(vec3 color, float value) {
    vec3 desaturated = desaturated(color);
    return mix(desaturated, color, 1.0 + value);
}

vec3 applyHue(vec3 color, float value){
    vec3 hsb = rgb2hsb(color);
    hsb[0] = hsb[0] + value;
    return hsb2rgb(hsb);
}

vec3 applyColorCorrection(vec3 color) {
    color = applyBrightness(color, brightness);
    color = applyContrast(color, contrast);
    color = applySaturation(color, saturation);
    color = applyHue(color, hue);

    return color;
}

void main(){
    vec4 textureColor = texture(textureSampler, outTexture);
    fragColor = vec4(applyColorCorrection(textureColor.rgb),textureColor.a);
}