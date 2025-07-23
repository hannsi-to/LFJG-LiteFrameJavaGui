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

vec3 rgb2hsv(vec3 color) {
    float cMax = max(max(color.r, color.g), color.b);
    float cMin = min(min(color.r, color.g), color.b);
    float delta = cMax - cMin;

    float hue = 0.0;
    if (delta > 0.0) {
        if (cMax == color.r) {
            hue = mod((color.g - color.b) / delta, 6.0);
        } else if (cMax == color.g) {
            hue = (color.b - color.r) / delta + 2.0;
        } else {
            hue = (color.r - color.g) / delta + 4.0;
        }
        hue /= 6.0;
        if (hue < 0.0) {
            hue += 1.0;
        }
    }

    float saturation = (cMax > 0.0) ? delta / cMax : 0.0;
    return vec3(hue, saturation, cMax);
}

vec3 hsv2rgb(vec3 c) {
    float h = c.x * 6.0;
    float s = c.y;
    float v = c.z;

    float i = floor(h);
    float f = h - i;
    float p = v * (1.0 - s);
    float q = v * (1.0 - f * s);
    float t = v * (1.0 - (1.0 - f) * s);

    vec3 rgb;
    if (i == 0.0) {
        rgb = vec3(v, t, p);
    } else if (i == 1.0) {
        rgb = vec3(q, v, p);
    } else if (i == 2.0) {
        rgb = vec3(p, v, t);
    } else if (i == 3.0) {
        rgb = vec3(p, q, v);
    } else if (i == 4.0) {
        rgb = vec3(t, p, v);
    } else {
        rgb = vec3(v, p, q);
    }

    return rgb;
}

float random(float x) {
    return fract(sin(x) * 43758.5453);
}