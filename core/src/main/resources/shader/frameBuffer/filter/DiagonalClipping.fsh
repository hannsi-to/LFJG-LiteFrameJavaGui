uniform vec2 diagonalClippingClipCenter;
uniform float diagonalClippingClipAngle;
uniform float diagonalClippingBlurWidth;
uniform bool diagonalClippingInvertClip;

void diagonalClippingMain() {
    vec4 texColor = texture(frameBufferSampler, outTexture);

    vec2 screenPos = gl_FragCoord.xy;

    vec2 toFrag = screenPos - diagonalClippingClipCenter;
    float distanceToLine = toFrag.x * cos(diagonalClippingClipAngle) + toFrag.y * sin(diagonalClippingClipAngle);

    float edge = smoothstep(-diagonalClippingBlurWidth, 0.0, distanceToLine);

    if (diagonalClippingInvertClip) {
        edge = 1.0 - edge;
    }

    if (edge <= 0.0) {
        discard;
    }

    fragColor = texColor * edge;
}