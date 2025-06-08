package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;

public class GLTriangle extends GLPolygon {
    /**
     * Constructs a new GLPolygon with the specified name.
     *
     * @param name the name of the polygon renderer
     */
    public GLTriangle(String name) {
        super(name);
    }

    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y2)).color(color2).end();
        put().vertex(new Vector2f(x3, y3)).color(color3).end();

        setDrawType(DrawType.TRIANGLES);
        rendering();
    }

    public void triangleWH(float x, float y, float width1, float height1, float width2, float height2, Color color1, Color color2, Color color3) {
        triangle(x, y, x + width1, y + height1, x + width2, y + height2, color1, color2, color3);
    }

    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3, Color color1, Color color2) {
        triangle(x1, y1, x2, y2, x3, y3, color1, color2, color2);
    }

    public void triangleWH(float x, float y, float width1, float height1, float width2, float height2, Color color1, Color color2) {
        triangleWH(x, y, width1, height1, width2, height2, color1, color2, color2);
    }

    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3, Color color) {
        triangle(x1, y1, x2, y2, x3, y3, color, color, color);
    }

    public void triangleWH(float x, float y, float width1, float height1, float width2, float height2, Color color) {
        triangleWH(x, y, width1, height1, width2, height2, color, color, color);
    }

    public void triangleOutLine(float x1, float y1, float x2, float y2, float x3, float y3, float lineWidth, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y2)).color(color2).end();
        put().vertex(new Vector2f(x3, y3)).color(color3).end();

        setDrawType(DrawType.LINE_LOOP).setLineWidth(lineWidth);
        rendering();
    }

    public void triangleWHOutLine(float x, float y, float width1, float height1, float width2, float height2, float lineWidth, Color color1, Color color2, Color color3) {
        triangleOutLine(x, y, x + width1, y + height1, x + width2, y + height2, lineWidth, color1, color2, color3);
    }

    public void triangleOutLine(float x1, float y1, float x2, float y2, float x3, float y3, float lineWidth, Color color1, Color color2) {
        triangleOutLine(x1, y1, x2, y2, x3, y3, lineWidth, color1, color2, color2);
    }

    public void triangleWHOutLine(float x, float y, float width1, float height1, float width2, float height2, float lineWidth, Color color1, Color color2) {
        triangleWHOutLine(x, y, width1, height1, width2, height2, lineWidth, color1, color2, color2);
    }

    public void triangleOutLine(float x1, float y1, float x2, float y2, float x3, float y3, float lineWidth, Color color) {
        triangleOutLine(x1, y1, x2, y2, x3, y3, lineWidth, color, color, color);
    }

    public void triangleWHOutLine(float x, float y, float width1, float height1, float width2, float height2, float lineWidth, Color color) {
        triangleWHOutLine(x, y, width1, height1, width2, height2, lineWidth, color, color, color);
    }

    public void triangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f((float) x1, (float) y1)).color(color1).end();
        put().vertex(new Vector2f((float) x2, (float) y2)).color(color2).end();
        put().vertex(new Vector2f((float) x3, (float) y3)).color(color3).end();

        setDrawType(DrawType.TRIANGLES);
        rendering();
    }

    public void triangleWH(double x, double y, double width1, double height1, double width2, double height2, Color color1, Color color2, Color color3) {
        triangle(x, y, x + width1, y + height1, x + width2, y + height2, color1, color2, color3);
    }

    public void triangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color1, Color color2) {
        triangle(x1, y1, x2, y2, x3, y3, color1, color2, color2);
    }

    public void triangleWH(double x, double y, double width1, double height1, double width2, double height2, Color color1, Color color2) {
        triangleWH(x, y, width1, height1, width2, height2, color1, color2, color2);
    }

    public void triangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {
        triangle(x1, y1, x2, y2, x3, y3, color, color, color);
    }

    public void triangleWH(double x, double y, double width1, double height1, double width2, double height2, Color color) {
        triangleWH(x, y, width1, height1, width2, height2, color, color, color);
    }

    public void triangleOutLine(double x1, double y1, double x2, double y2, double x3, double y3, double lineWidth, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f((float) x1, (float) y1)).color(color1).end();
        put().vertex(new Vector2f((float) x2, (float) y2)).color(color2).end();
        put().vertex(new Vector2f((float) x3, (float) y3)).color(color3).end();

        setDrawType(DrawType.LINE_LOOP).setLineWidth((float) lineWidth);
        rendering();
    }

    public void triangleWHOutLine(double x, double y, double width1, double height1, double width2, double height2, double lineWidth, Color color1, Color color2, Color color3) {
        triangleOutLine(x, y, x + width1, y + height1, x + width2, y + height2, lineWidth, color1, color2, color3);
    }

    public void triangleOutLine(double x1, double y1, double x2, double y2, double x3, double y3, double lineWidth, Color color1, Color color2) {
        triangleOutLine(x1, y1, x2, y2, x3, y3, lineWidth, color1, color2, color2);
    }

    public void triangleWHOutLine(double x, double y, double width1, double height1, double width2, double height2, double lineWidth, Color color1, Color color2) {
        triangleWHOutLine(x, y, width1, height1, width2, height2, lineWidth, color1, color2, color2);
    }

    public void triangleOutLine(double x1, double y1, double x2, double y2, double x3, double y3, double lineWidth, Color color) {
        triangleOutLine(x1, y1, x2, y2, x3, y3, lineWidth, color, color, color);
    }

    public void triangleWHOutLine(double x, double y, double width1, double height1, double width2, double height2, double lineWidth, Color color) {
        triangleWHOutLine(x, y, width1, height1, width2, height2, lineWidth, color, color, color);
    }
}