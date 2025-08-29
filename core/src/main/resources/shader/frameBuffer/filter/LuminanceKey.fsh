uniform float luminanceKeyThreshold;
uniform float luminanceKeyBlurAmount;
uniform int luminanceKeyMode;

void luminanceKeyMain() {
    vec4 texColor = texture(frameBufferSampler, outTexture);

    float luminance = dot(texColor.rgb, vec3(0.2126, 0.7152, 0.0722));

    float lower = luminanceKeyThreshold - luminanceKeyBlurAmount;
    float upper = luminanceKeyThreshold + luminanceKeyBlurAmount;
    float smoothed = smoothstep(lower, upper, luminance);

    float alpha = 1.0;

    if (luminanceKeyMode == 0) {
        alpha = smoothed;
    } else if (luminanceKeyMode == 1) {
        alpha = 1.0 - smoothed;
    } else if (luminanceKeyMode == 2) {
        float darkEdge = smoothstep(0.0, lower, luminance);
        float brightEdge = 1.0 - smoothstep(upper, 1.0, luminance);
        alpha = min(darkEdge, brightEdge);
    }

    fragColor = vec4(texColor.rgb, texColor.a * alpha);
}