uniform bool clippingRect2DBool = false;
uniform bool clippingRect2DInvert = false;
uniform vec4 clippingRect2DSize;

void clippingRect2D() {
    if (!clippingRect2DBool) {
        return;
    }
    vec2 clipMin = (clippingRect2DSize.xy / resolution) * 2.0 - 1.0;
    vec2 clipMax = (clippingRect2DSize.zw / resolution) * 2.0 - 1.0;

    if (clippingRect2DInvert){
        if (outPosition.x >= clipMin.x && outPosition.x <= clipMax.x &&
        outPosition.y >= clipMin.y && outPosition.y <= clipMax.y) {
            discard;
        }
    } else {
        if (outPosition.x < clipMin.x || outPosition.x > clipMax.x ||
        outPosition.y < clipMin.y || outPosition.y > clipMax.y) {
            discard;
        }
    }
}
