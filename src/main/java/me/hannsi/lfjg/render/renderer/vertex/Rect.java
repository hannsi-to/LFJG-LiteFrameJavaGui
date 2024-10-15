package me.hannsi.lfjg.render.renderer.vertex;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.util.type.types.DrawType;
import me.hannsi.lfjg.util.vertex.Color;
import me.hannsi.lfjg.util.vertex.vector.Vector2f;

public class Rect extends Polygon {
    public Rect(Frame frame) {
        super(frame);
    }

    public void rect(float x1, float y1, float x2, float y2, Color color) {
        put().vertex(new Vector2f(x1, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y2)).color(color).end();
        put().vertex(new Vector2f(x1, y2)).color(color).end();

        draw(DrawType.QUADS);
    }

    public void rectWH(float x, float y, float width, float height, Color color) {
        rect(x, y, x + width, y + height, color);
    }

    public void rect(float x1, float y1, float x2, float y2, Color color1, Color color2) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color1).end();
        put().vertex(new Vector2f(x1, y2)).color(color1).end();

        draw(DrawType.QUADS);
    }

    public void rectWH(float x, float y, float width, float height, Color color1, Color color2) {
        rect(x, y, x + width, y + height, color1, color2);
    }

    public void rect(float x1, float y1, float x2, float y2, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color3).end();
        put().vertex(new Vector2f(x1, y2)).color(color1).end();

        draw(DrawType.QUADS);
    }

    public void rectWH(float x, float y, float width, float height, Color color1, Color color2, Color color3) {
        rect(x, y, x + width, y + height, color1, color2, color3);
    }

    public void rect(float x1, float y1, float x2, float y2, Color color1, Color color2, Color color3, Color color4) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color3).end();
        put().vertex(new Vector2f(x1, y2)).color(color4).end();

        draw(DrawType.QUADS);
    }

    public void rectWH(float x, float y, float width, float height, Color color1, Color color2, Color color3, Color color4) {
        rect(x, y, x + width, y + height, color1, color2, color3, color4);
    }
}
