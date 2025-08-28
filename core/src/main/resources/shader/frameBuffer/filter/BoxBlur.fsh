uniform int kernelX;
uniform int kernelY;

void boxBlurMain() {
    vec2 texSize = textureSize(frameBufferSampler, 0);
    vec2 lr = vec2(1.0 / texSize.x, 0);
    vec2 tb = vec2(0, 1.0 / texSize.y);
    vec4 color = texture(frameBufferSampler, outTexture);
    int count = 1;

    for (int i = -kernelX; i <= kernelX; i++) {
        for (int j = -kernelY; j <= kernelY; j++) {
            if (i == 0 && j == 0) {
                continue;
            }
            vec2 offset = vec2(i, j);
            vec2 texCoord = outTexture + lr * offset.x + tb * offset.y;

            if (texCoord.x >= 0.0 && texCoord.x <= 1.0 && texCoord.y >= 0.0 && texCoord.y <= 1.0) {
                color += texture(frameBufferSampler, texCoord);
                count++;
            }
        }
    }

    color /= float(count);
    fragColor = vec4(color.rgb, color.a);
}