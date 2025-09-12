uniform sampler2D fontAtlas;
uniform vec4 msdfFontColor;
uniform float msdfDistanceRange;
uniform float msdfBoldness;
uniform float msdfGhost;
uniform bool msdfBox;
uniform vec4 msdfUVSize;
uniform bool msdfOutline;
uniform float msdfOutlineWidth;

float msdfMedian(float r, float g, float b) {
    return max(min(r, g), min(max(r, g), b));
}

void msdfMain() {
    vec3 msdf = texture(fontAtlas, outTexture).rgb;
    float sd = msdfMedian(msdf.r, msdf.g, msdf.b);
    sd += msdfGhost;
    float threshold = 0.5 + msdfBoldness;

    float screenPxDistance = fwidth(sd);

    float outerEdge = 0.5 + msdfOutlineWidth * 0.5;

    float fillAlpha = smoothstep(threshold - screenPxDistance, threshold + screenPxDistance, sd);
    float outlineAlpha = smoothstep(outerEdge - screenPxDistance, outerEdge + screenPxDistance, sd);
    float outlineOnlyAlpha = clamp(outlineAlpha * (1.0 - fillAlpha), 0.0, 1.0);

    vec4 resultColor;
    vec4 uOutlineColor = msdfFontColor;

    if (msdfOutline) {
        resultColor = vec4(uOutlineColor.rgb, outlineOnlyAlpha * uOutlineColor.a);
    } else {
        resultColor = vec4(msdfFontColor.rgb, fillAlpha * msdfFontColor.a);
    }

    bool isBorder =
    outTexture.x < msdfUVSize.x || outTexture.x > msdfUVSize.z ||
    outTexture.y < msdfUVSize.y || outTexture.y > msdfUVSize.w;

    if (isBorder && msdfBox) {
        fragColor = msdfFontColor;
    } else {
        fragColor = resultColor;
    }
}