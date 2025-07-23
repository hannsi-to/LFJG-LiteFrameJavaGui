#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float range;
uniform float intensity;
uniform float sigma;
uniform int radialSteps;
uniform int angularSamples;

void main() {
    vec2 texSize = vec2(textureSize(textureSampler, 0));
    vec2 texCoord = outTexture;

    vec4 color = vec4(0.0);
    float totalWeight = 0.0;

    for (int i = 0; i < angularSamples; i++) {
        float angle = float(i) * 6.2831853 / float(angularSamples);
        vec2 dir = vec2(cos(angle), sin(angle));

        for (int j = 0; j <= radialSteps; j++) {
            float r = float(j) * range / float(radialSteps);
            vec2 offset = dir * r / texSize;

            float weight = exp(-(r * r) / (2.0 * sigma * sigma));
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