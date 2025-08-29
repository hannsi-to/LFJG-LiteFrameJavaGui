uniform float edgeExtractionEdgeStrength;
uniform float edgeExtractionThreshold;
uniform bool edgeExtractionEnableLuminanceEdge;
uniform bool edgeExtractionEnableAlphaEdge;
uniform vec4 edgeExtractionEdgeColor;

void edgeExtractionMain() {
    vec2 texCoord = outTexture;

    float offset = 1.0 / 512.0;

    vec4 texCenter = texture(frameBufferSampler, texCoord);
    vec3 center = texCenter.rgb;
    vec3 left = texture(frameBufferSampler, texCoord + vec2(-offset, 0.0)).rgb;
    vec3 right = texture(frameBufferSampler, texCoord + vec2(offset, 0.0)).rgb;
    vec3 up = texture(frameBufferSampler, texCoord + vec2(0.0, offset)).rgb;
    vec3 down = texture(frameBufferSampler, texCoord + vec2(0.0, -offset)).rgb;

    float luminanceCenter = dot(center, vec3(0.299, 0.587, 0.114));
    float luminanceLeft = dot(left, vec3(0.299, 0.587, 0.114));
    float luminanceRight = dot(right, vec3(0.299, 0.587, 0.114));
    float luminanceUp = dot(up, vec3(0.299, 0.587, 0.114));
    float luminanceDown = dot(down, vec3(0.299, 0.587, 0.114));

    float edgeX = luminanceRight - luminanceLeft;
    float edgeY = luminanceUp - luminanceDown;
    float edgeMagnitude = sqrt(edgeX * edgeX + edgeY * edgeY);
    edgeMagnitude *= edgeExtractionEdgeStrength;

    float edge = step(edgeExtractionThreshold, edgeMagnitude);

    fragColor = texCenter;

    if (edgeExtractionEnableLuminanceEdge) {
        fragColor = edge * edgeExtractionEdgeColor;
    }

    if (edgeExtractionEnableAlphaEdge) {
        fragColor.a *= edge;
    }
}