package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;

public class GLLine extends GLPolygon {

    public GLLine(String name) {
        super(name);
    }

    public void line(float x1, float y1, float x2, float y2, float lineWidth, Color color) {
        put().vertex(new Vector2f(x1, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y2)).color(color).end();

        setDrawType(DrawType.LINES).setLineWidth(lineWidth);
        rendering();
    }

    public void lineWH(float x1, float y1, float width, float height, float lineWidth, Color color) {
        line(x1, y1, x1 + width, y1 + height, lineWidth, color);
    }

    public void line(float x1, float y1, float x2, float y2, float lineWidth, Color color1, Color color2) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y2)).color(color2).end();

        setDrawType(DrawType.LINES).setLineWidth(lineWidth);
        rendering();
    }

    public void lineWH(float x1, float y1, float width, float height, float lineWidth, Color color1, Color color2) {
        line(x1, y1, x1 + width, y1 + height, lineWidth, color1, color2);
    }
}
