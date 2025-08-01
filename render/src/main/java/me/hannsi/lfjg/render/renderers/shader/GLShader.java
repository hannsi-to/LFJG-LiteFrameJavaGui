package me.hannsi.lfjg.render.renderers.shader;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
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

    public void shader(Location fragmentShaderPath, float x1, float y1, float x2, float y2) {
        put().vertex(new Vector2f(x1, y1)).end();
        put().vertex(new Vector2f(x2, y1)).end();
        put().vertex(new Vector2f(x2, y2)).end();
        put().vertex(new Vector2f(x1, y2)).end();

        setDrawType(DrawType.QUADS);
        rendering(true, fragmentShaderPath);
    }

    public void shaderWH(Location fragmentShaderPath, float x, float y, float width, float height) {
        shader(fragmentShaderPath, x, y, x + width, y + height);
    }

    public void shader(Location fragmentShaderPath, double x1, double y1, double x2, double y2) {
        shader(fragmentShaderPath, (float) x1, (float) y1, (float) x2, (float) y2);
    }

    public void shaderWH(Location fragmentShaderPath, double x, double y, double width, double height) {
        shader(fragmentShaderPath, x, y, x + width, y + height);
    }

    @Override
    public void draw() {
        getShaderProgram().bind();
        getShaderProgram().setUniform("offset", UploadUniformType.ON_CHANGE, new Vector2f(getTransform().getX(), getTransform().getY()));
        getShaderProgram().setUniform("resolution", UploadUniformType.ON_CHANGE, new Vector2f(getTransform().getWidth(), getTransform().getHeight()));
        getShaderProgram().setUniform("time", UploadUniformType.ON_CHANGE, (System.currentTimeMillis() - initTime) / 1000f);
        getShaderProgram().unbind();

        super.draw();
    }
}
