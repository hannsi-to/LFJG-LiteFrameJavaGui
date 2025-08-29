uniform float pixelateMosaicSize = 0;

vec4 pixelate(float size, vec2 uv){
    float dx = size / resolution.x;
    float dy = size / resolution.y;

    float x = dx * (floor(uv.x / dx) + 0.5);
    float y = dy * (floor(uv.y / dy) + 0.5);

    return texture(frameBufferSampler, vec2(x, y));
}

void pixelateMain(){
    vec2 uv = outTexture;

    vec4 resultColor = pixelate(pixelateMosaicSize, uv);
    if (pixelateMosaicSize == 0){
        resultColor = texture(frameBufferSampler, uv);
    }

    fragColor = resultColor;
}