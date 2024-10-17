package me.hannsi.lfjg.render.renderer.vertex;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.util.type.types.DrawType;
import me.hannsi.lfjg.util.vertex.Color;
import me.hannsi.lfjg.util.vertex.vector.Vector2f;

public class Point extends Polygon {
    public Point(Frame frame) {
        super(frame);
    }

    public void point(float x, float y, float pointSize, Color color) {
        put().vertex(new Vector2f(x, y)).color(color).end();

        rendering().drawType(DrawType.POINTS).pointSize(pointSize).draw();
    }

    public void pointWH(float x, float y, float pointSize, Color color) {
        point(x, y, pointSize, color);
    }
}
