#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

vec4 colorFilter;

uniform sampler2D textureSampler;
uniform vec2 direction;
uniform float radius;
uniform float weights[256];
uniform vec2 texelSize;

#define offset (texelSize * direction)

void main() {
    vec3 blr = texture(textureSampler, outTexture).rgb * weights[0];

    for (int i = 1; i <= int(radius); i++) {
        float f = float(i);
        blr += texture(textureSampler, outTexture + f * offset).rgb * weights[i];
        blr += texture(textureSampler, outTexture - f * offset).rgb * weights[i];
    }

    fragColor = texture(textureSampler, outTexture);

    //int kernelX = 10;
    //int kernelY = 10;

    //vec2 lr = vec2(1, 0)/resolution;
    //vec2 tb = vec2(0, 1)/resolution;
    //vec4 color = inputColor;

    //for (float i = - kernelX; i < kernelX; i++){
    //    for (float j = - kernelY; j < kernelY; j++) {
    //        color += texture(textureSampler, outTexture + lr * i + tb * j);
    //    }
    //}

    //vec4(color.rgb/color.w, 1);
}