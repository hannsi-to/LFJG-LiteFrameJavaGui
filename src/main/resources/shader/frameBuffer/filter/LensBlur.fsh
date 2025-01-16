#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float range;
uniform float intensity;


void main() {
    vec2 texSize = vec2(textureSize(textureSampler, 0));
    vec2 texCoord = outTexture;

    vec4 color = vec4(0.0);
    float totalWeight = 0.0;
    float sigma = range * 0.5;

    int numSamples = 360;

    for (int i = 0; i < numSamples; i++) {
        float angle = float(i) * 6.283185307179586 / float(numSamples);

        for (float r = 0.0; r <= range; r++) {
            vec2 offset = vec2(cos(angle), sin(angle)) * r / texSize;

            float weight = exp(-(offset.x * offset.x + offset.y * offset.y) / (2.0 * sigma * sigma));

            vec4 sampleT = texture(textureSampler, texCoord + offset);
            color.rgb += sampleT.rgb * weight;
            totalWeight += weight;
        }
    }

    color.rgb /= totalWeight;
    color.rgb *= intensity;

    color.a = texture(textureSampler, texCoord).a;

    fragColor = color;
}