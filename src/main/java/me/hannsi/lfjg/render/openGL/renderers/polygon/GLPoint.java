package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;

public class GLPoint extends GLPolygon {
    public GLPoint(Frame frame) {
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
