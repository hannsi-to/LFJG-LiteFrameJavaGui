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

    float luminance = dot(texColor.rgb, vec3(0.2126, 0.7152, 0.0722));

    float lower = threshold - blurAmount;
    float upper = threshold + blurAmount;
    float smoothed = smoothstep(lower, upper, luminance);

    float alpha = 1.0;

    if (mode == 0) {
        alpha = smoothed;
    } else if (mode == 1) {
        alpha = 1.0 - smoothed;
    } else if (mode == 2) {
        float darkEdge = smoothstep(0.0, lower, luminance);
        float brightEdge = 1.0 - smoothstep(upper, 1.0, luminance);
        alpha = min(darkEdge, brightEdge);
    }

    fragColor = vec4(texColor.rgb, texColor.a * alpha);
}