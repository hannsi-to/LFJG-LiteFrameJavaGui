#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform bool aplha;
uniform vec4 targetColor;
uniform vec4 newColor;

void main() {
    vec4 baseColor = texture(textureSampler, outTexture);

    if (aplha){
        if (baseColor.rgba == targetColor.rgba){
            baseColor.rgba = newColor.rgba;
        }
    } else {
        if (baseColor.rgb == targetColor.rgb){
            baseColor.rgb = newColor.rgb;
        }
    }

    fragColor = baseColor;
}