package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;

public class GLPoint extends GLPolygon {
    public GLPoint(String name) {
        super(name);
    }

    public void point(float x, float y, float pointSize, Color color) {
        put().vertex(new Vector2f(x, y)).color(color).end();

        setDrawType(DrawType.POINTS).setPointSize(pointSize);
        rendering();
    }

    public void pointWH(float x, float y, float pointSize, Color color) {
        point(x, y, pointSize, color);
    }
}
