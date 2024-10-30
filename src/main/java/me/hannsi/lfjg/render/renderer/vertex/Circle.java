package me.hannsi.lfjg.render.renderer.vertex;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.util.type.types.DrawType;
import me.hannsi.lfjg.util.vertex.Color;
import me.hannsi.lfjg.util.vertex.vector.Vector2f;

public class Circle extends Polygon {
    public Circle(Frame frame) {
        super(frame);
    }

    public void circle(float xCenter, float yCenter, float xRadius, float yRadius, int segmentCount, Color color) {
        Vector2f[] vertices = createCircleVertices(xCenter, yCenter, xRadius, yRadius, segmentCount);

        for (Vector2f vector2f : vertices) {
            put().vertex(vector2f).color(color).end();
        }

        rendering().drawType(DrawType.POLYGON).draw();
    }

    private Vector2f[] createCircleVertices(float xCenter, float yCenter, float xRadius, float yRadius, int segmentCount) {
        Vector2f[] vertices = new Vector2f[segmentCount + 1];

        for (int i = 0; i <= segmentCount; i++) {
            double x = Math.sin(((i * Math.PI) / 180)) * xRadius;
            double y = Math.cos(((i * Math.PI) / 180)) * yRadius;

            vertices[i] = new Vector2f((float) (xCenter + x), (float) (yCenter + y));
        }

        return vertices;
    }
}
