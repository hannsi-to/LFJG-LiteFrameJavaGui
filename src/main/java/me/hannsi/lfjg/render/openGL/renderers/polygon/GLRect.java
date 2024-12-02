package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;

public class GLRect extends GLPolygon {
    public GLRect(String name) {
        super(name);
    }

    public void rect(float x1, float y1, float x2, float y2, Color color) {
        put().vertex(new Vector2f(x1, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y2)).color(color).end();
        put().vertex(new Vector2f(x1, y2)).color(color).end();

        setDrawType(DrawType.QUADS);
        rendering();
    }

    public void rectWH(float x, float y, float width, float height, Color color) {
        rect(x, y, x + width, y + height, color);
    }

    public void rect(float x1, float y1, float x2, float y2, Color color1, Color color2) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color1).end();
        put().vertex(new Vector2f(x1, y2)).color(color1).end();

        setDrawType(DrawType.QUADS);
        rendering();
    }

    public void rectWH(float x, float y, float width, float height, Color color1, Color color2) {
        rect(x, y, x + width, y + height, color1, color2);
    }

    public void rect(float x1, float y1, float x2, float y2, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color3).end();
        put().vertex(new Vector2f(x1, y2)).color(color1).end();

        setDrawType(DrawType.QUADS);
        rendering();
    }

    public void rectWH(float x, float y, float width, float height, Color color1, Color color2, Color color3) {
        rect(x, y, x + width, y + height, color1, color2, color3);
    }

    public void rect(float x1, float y1, float x2, float y2, Color color1, Color color2, Color color3, Color color4) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color3).end();
        put().vertex(new Vector2f(x1, y2)).color(color4).end();

        setDrawType(DrawType.QUADS);
        rendering();
    }

    public void rectWH(float x, float y, float width, float height, Color color1, Color color2, Color color3, Color color4) {
        rect(x, y, x + width, y + height, color1, color2, color3, color4);
    }

    public void rectOutLine(float x1, float y1, float x2, float y2, float lineWidth, Color color) {
        put().vertex(new Vector2f(x1, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y2)).color(color).end();
        put().vertex(new Vector2f(x1, y2)).color(color).end();

        setDrawType(DrawType.LINE_LOOP).setLineWidth(lineWidth);
        rendering();
    }

    public void rectWHOutLine(float x, float y, float width, float height, float lineWidth, Color color) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color);
    }

    public void rectOutLine(float x1, float y1, float x2, float y2, float lineWidth, Color color1, Color color2) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color1).end();
        put().vertex(new Vector2f(x1, y2)).color(color1).end();

        setDrawType(DrawType.LINE_LOOP).setLineWidth(lineWidth);
        rendering();
    }

    public void rectWHOutLine(float x, float y, float width, float height, float lineWidth, Color color1, Color color2) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color1, color2);
    }

    public void rectOutLine(float x1, float y1, float x2, float y2, float lineWidth, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color3).end();
        put().vertex(new Vector2f(x1, y2)).color(color1).end();

        setDrawType(DrawType.LINE_LOOP).setLineWidth(lineWidth);
        rendering();
    }

    public void rectWHOutLine(float x, float y, float width, float height, float lineWidth, Color color1, Color color2, Color color3) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color1, color2, color3);
    }

    public void rectOutLine(float x1, float y1, float x2, float y2, float lineWidth, Color color1, Color color2, Color color3, Color color4) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color3).end();
        put().vertex(new Vector2f(x1, y2)).color(color4).end();

        setDrawType(DrawType.LINE_LOOP).setLineWidth(lineWidth);
        rendering();
    }

    public void rectWHOutLine(float x, float y, float width, float height, float lineWidth, Color color1, Color color2, Color color3, Color color4) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color1, color2, color3, color4);
    }
}
