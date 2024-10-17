package me.hannsi.lfjg.render.renderer.vertex;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.util.type.types.DrawType;
import me.hannsi.lfjg.util.vertex.Color;
import me.hannsi.lfjg.util.vertex.vector.Vector2f;

public class Line extends Polygon {

    public Line(Frame frame) {
        super(frame);
    }

    public void line(float x1, float y1, float x2, float y2, float lineWidth, Color color) {
        put().vertex(new Vector2f(x1, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y2)).color(color).end();

        rendering().drawType(DrawType.LINES).lineWidth(lineWidth).draw();
    }

    public void lineWH(float x1, float y1, float width, float height, float lineWidth, Color color) {
        line(x1, y1, x1 + width, y1 + height, lineWidth, color);
    }

    public void line(float x1, float y1, float x2, float y2, float lineWidth, Color color1, Color color2) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y2)).color(color2).end();

        rendering().drawType(DrawType.LINES).lineWidth(lineWidth).draw();
    }

    public void lineWH(float x1, float y1, float width, float height, float lineWidth, Color color1, Color color2) {
        line(x1, y1, x1 + width, y1 + height, lineWidth, color1, color2);
    }
}
