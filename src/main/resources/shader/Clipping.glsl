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
        if (fragPosition.x >= clipMin.x && fragPosition.x <= clipMax.x &&
        fragPosition.y >= clipMin.y && fragPosition.y <= clipMax.y) {
            discard;
        }
    } else {
        if (fragPosition.x < clipMin.x || fragPosition.x > clipMax.x ||
        fragPosition.y < clipMin.y || fragPosition.y > clipMax.y) {
            discard;
        }
    }
}
