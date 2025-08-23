#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float intensity;
uniform vec3 color;
uniform bool preserveBrightness;

void main() {
    vec4 texColor = texture(textureSampler, outTexture);

    vec3 monoColor;
    if (preserveBrightness) {
        float brightness = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));
        monoColor = mix(texColor.rgb, color * brightness, intensity);
    } else {
        monoColor = mix(texColor.rgb, color, intensity);
    }

    fragColor = vec4(monoColor, texColor.a);
}