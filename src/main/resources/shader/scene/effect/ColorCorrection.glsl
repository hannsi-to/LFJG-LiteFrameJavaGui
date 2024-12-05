uniform float brightness = 0.0;
uniform float contrast = 0.0;
uniform float saturation = 0.0;
uniform float hue = 0.0;

vec3 rgb2hsb(vec3 c) {
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));
    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 hsb2rgb(vec3 c){
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

vec3 applyBrightness(vec3 color,float value){
    vec3 rgb = color.rgb + value;
    return rgb;
}

vec3 applyContrast(vec3 color, float value) {
    vec3 rgb = 0.5 + (1.0 + value) * (color.rgb - 0.5);
    return rgb;
}

vec3 desaturated(vec3 color) {
    vec3 luma = vec3(0.2126, 0.7152, 0.0722);
    vec3 rgb = vec3(dot(color, luma));
    return rgb;
}

vec3 applySaturation(vec3 color, float value) {
    vec3 desaturated = desaturated(color);
    return mix(desaturated, color, 1.0 + value);
}

vec3 applyHue(vec3 color, float value){
    vec3 hsb = rgb2hsb(color);
    hsb[0] = hsb[0] + value;
    return hsb2rgb(hsb);
}

vec3 applyColorCorrection(vec3 color) {
    color = applyBrightness(color, brightness);
    color = applyContrast(color, contrast);
    color = applySaturation(color, saturation);
    color = applyHue(color, hue);

    return color;
}