#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float threshold;
uniform float blurAmount;
uniform int mode;

void main() {
    vec4 texColor = texture(textureSampler, outTexture);

    float luminance = 0.2126 * texColor.r + 0.7152 * texColor.g + 0.0722 * texColor.b;

    float alpha = 1.0;
    if (mode == 0) {
        alpha = smoothstep(threshold - blurAmount, threshold + blurAmount, luminance);
    } else if (mode == 1) {
        alpha = 1.0 - smoothstep(threshold - blurAmount, threshold + blurAmount, luminance);
    } else if (mode == 2) {
        float darkAlpha = smoothstep(threshold - blurAmount, threshold + blurAmount, luminance);
        float brightAlpha = 1.0 - smoothstep(threshold - blurAmount, threshold + blurAmount, luminance);
        alpha = min(darkAlpha, brightAlpha);
    }

    fragColor = vec4(texColor.rgb, texColor.a * alpha);
}