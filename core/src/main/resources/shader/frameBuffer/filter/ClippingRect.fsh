uniform bool clippingRectInvert = false;
uniform vec4 clippingRectSize;

void clippingRectMain() {
    vec2 screenPos = gl_FragCoord.xy;

    bool insideClippingRect =
    screenPos.x >= clippingRectSize.x &&
    screenPos.y >= clippingRectSize.y &&
    screenPos.x <= (clippingRectSize.x + clippingRectSize.z) &&
    screenPos.y <= (clippingRectSize.y + clippingRectSize.w);

    if (clippingRectInvert ? insideClippingRect : !insideClippingRect) {
        discard;
    }

    fragColor = texture(frameBufferSampler, outTexture);
}
