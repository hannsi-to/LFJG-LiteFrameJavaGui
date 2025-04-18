package me.hannsi.lfjg.render.openGL.renderers.shader;

import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;

public class GLShader extends GLPolygon {
    private final long initTime;

    /**
     * Constructs a new GLRect with the specified name.
     *
     * @param name the name of the rectangle renderer
     */
    public GLShader(String name) {
        super(name);

        this.initTime = System.currentTimeMillis();
    }

    public void shader(FileLocation fragmentShaderPath, float x1, float y1, float x2, float y2) {
        put().vertex(new Vector2f(x1, y1)).end();
        put().vertex(new Vector2f(x2, y1)).end();
        put().vertex(new Vector2f(x2, y2)).end();
        put().vertex(new Vector2f(x1, y2)).end();

        setDrawType(DrawType.QUADS);
        rendering(true, fragmentShaderPath);
    }

    public void shaderWH(FileLocation fragmentShaderPath, float x, float y, float width, float height) {
        shader(fragmentShaderPath, x, y, x + width, y + height);
    }

    public void shader(FileLocation fragmentShaderPath, double x1, double y1, double x2, double y2) {
        shader(fragmentShaderPath, (float) x1, (float) y1, (float) x2, (float) y2);
    }

    public void shaderWH(FileLocation fragmentShaderPath, double x, double y, double width, double height) {
        shader(fragmentShaderPath, x, y, x + width, y + height);
    }

    public void setUniform(String name, Object... value) {
        getShaderProgram().bind();
        getShaderProgram().setUniform(name, value);
        getShaderProgram().unbind();
    }

    @Override
    public void draw() {
        getShaderProgram().bind();
        setUniform("resolution", getWidth(), getHeight());
        setUniform("time", (System.currentTimeMillis() - initTime) / 1000f);
        getShaderProgram().unbind();

        super.draw();
    }
}
