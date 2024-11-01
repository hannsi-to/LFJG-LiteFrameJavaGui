package me.hannsi.lfjg.render.openGL.renderer.vertex;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.math.vertex.vector.Vector2f;
import me.hannsi.lfjg.utils.type.types.DrawType;

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
