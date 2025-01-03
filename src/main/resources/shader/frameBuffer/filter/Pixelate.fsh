#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform float mosaicSize = 0;
uniform vec2 resolution;

vec4 pixelate(float size, vec2 uv){
    float dx = size / resolution.x;
    float dy = size / resolution.y;

    float x = dx * (floor(uv.x / dx) + 0.5);
    float y = dy * (floor(uv.y / dy) + 0.5);

    return texture(textureSampler, vec2(x, y));
}

void main(){
    vec2 uv = outTexture;

    vec4 resultColor = pixelate(mosaicSize, uv);
    if (mosaicSize == 0){
        resultColor = texture(textureSampler, uv);
    }

    fragColor = resultColor;
}