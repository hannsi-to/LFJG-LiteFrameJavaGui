package me.hannsi.lfjg.render.renderer.vertex;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.util.type.types.DrawType;
import me.hannsi.lfjg.util.vertex.Color;
import me.hannsi.lfjg.util.vertex.vector.Vector2f;

public class Circle extends Polygon {
    public Circle(Frame frame) {
        super(frame);
    }

    public void circle(float xCenter, float yCenter, float xRadius, float yRadius, int segmentCount, Color... colors) {
        int colorCount = colors.length;

        if (colorCount < 1) {
            return;
        }

        for (int i = 0; i <= segmentCount; i++) {
            float theta = (float) (2.0f * Math.PI * i / segmentCount);
            float x = xCenter + xRadius * (float) Math.cos(theta);
            float y = yCenter + yRadius * (float) Math.sin(theta);

            Color color = null;
            if (colorCount > 1) {
                float colorIndex = (float) i / segmentCount * (colorCount - 1);
                int startColorIndex = (int) colorIndex;
                int endColorIndex = Math.min(startColorIndex + 1, colorCount - 1);
                float factor = colorIndex - startColorIndex;

                Color startColor = colors[startColorIndex];
                Color endColor = colors[endColorIndex];

                float r = startColor.getRed() / 255.0f + factor * (endColor.getRed() / 255.0f - startColor.getRed() / 255.0f);
                float g = startColor.getGreen() / 255.0f + factor * (endColor.getGreen() / 255.0f - startColor.getGreen() / 255.0f);
                float b = startColor.getBlue() / 255.0f + factor * (endColor.getBlue() / 255.0f - startColor.getBlue() / 255.0f);
                float a = startColor.getAlpha() / 255.0f + factor * (endColor.getAlpha() / 255.0f - startColor.getAlpha() / 255.0f); // アルファの補間

                color = new Color(r, g, b, a);
            } else {
                Color color2 = colors[0];
                color = new Color(color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, color2.getAlpha() / 255.0f);
            }

            put().vertex(new Vector2f(x, y)).color(color).end();
        }

        rendering().drawType(DrawType.POLYGON).draw();
    }

    public void circleOutLine(float xCenter, float yCenter, float xRadius, float yRadius, int segmentCount, float lineWidth, Color... colors) {
        for (int i = 0; i <= segmentCount; i++) {
            float theta = (float) (2.0f * Math.PI * i / segmentCount);
            float x = xCenter + xRadius * (float) Math.cos(theta);
            float y = yCenter + yRadius * (float) Math.sin(theta);

            float colorFactor = (float) i / segmentCount;
            float r = (1 - colorFactor);
            float g = 0.0f;
            float b = colorFactor;

            put().vertex(new Vector2f(x, y)).color(new Color(r, g, b, 1f)).end();
        }

        rendering().drawType(DrawType.LINE_LOOP).lineWidth(lineWidth).draw();
    }
}
