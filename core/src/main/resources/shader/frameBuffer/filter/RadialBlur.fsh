uniform float radialBlurRange;
uniform float radialBlurCenterX;
uniform float radialBlurCenterY;

void radialBlurMain() {
    vec2 uv = outTexture;
    vec2 center = vec2(radialBlurCenterX / resolution.x, radialBlurCenterY / resolution.y);

    vec4 color = vec4(0.0);
    float total = 0.0;

    for (float t = 0.0; t < 1.0; t += 0.01) {
        vec2 offset = uv + (uv - center) * t * radialBlurRange;
        color += texture(frameBufferSampler, offset);
        total += 1.0;
    }

    fragColor = color / total;
}