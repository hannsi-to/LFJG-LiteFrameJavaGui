package me.hannsi.lfjg.render.openGL.renderers.shader;

import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.nio.FloatBuffer;

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

    public void setUniform(String name, Object... value) {
        getShaderProgram().bind();

        if (value.length == 1) {
            if (value[0] instanceof Boolean) {
                getShaderProgram().setUniform(name, (boolean) value[0]);
            } else if (value[0] instanceof Matrix4f) {
                getShaderProgram().setUniform(name, (Matrix4f) value[0]);
            } else if (value[0] instanceof FloatBuffer) {
                getShaderProgram().setUniform(name, (FloatBuffer) value[0]);
            } else if (value[0] instanceof Float) {
                getShaderProgram().setUniform(name, (float) value[0]);
            } else if (value[0] instanceof Vector2f) {
                getShaderProgram().setUniform(name, (Vector2f) value[0]);
            } else if (value[0] instanceof Vector3f) {
                getShaderProgram().setUniform(name, (Vector3f) value[0]);
            } else if (value[0] instanceof Integer) {
                getShaderProgram().setUniform(name, (int) value[0]);
            } else if (value[0] instanceof Vector4f) {
                getShaderProgram().setUniform(name, (Vector4f) value[0]);
            } else if (value[0] instanceof Color) {
                getShaderProgram().setUniform(name, (Color) value[0]);
            } else {
                throw new RuntimeException(value[0].getClass().getName() + " type is not supported.");
            }
        } else if (value.length == 2) {
            if (value[0] instanceof Float && value[1] instanceof Float) {
                getShaderProgram().setUniform(name, (float) value[0], (float) value[1]);
            } else {
                throw new RuntimeException(value[0].getClass().getName() + ", " + value[1].getClass().getName() + " types are not supported.");
            }
        } else if (value.length == 3) {
            if (value[0] instanceof Float && value[1] instanceof Float && value[2] instanceof Float) {
                getShaderProgram().setUniform(name, (float) value[0], (float) value[1], (float) value[2]);
            } else {
                throw new RuntimeException(value[0].getClass().getName() + ", " + value[1].getClass().getName() + ", " + value[2].getClass().getName() + " types are not supported.");
            }
        } else if (value.length == 4) {
            if (value[0] instanceof Float && value[1] instanceof Float && value[2] instanceof Float && value[3] instanceof Float) {
                getShaderProgram().setUniform(name, (float) value[0], (float) value[1], (float) value[2], (float) value[3]);
            } else {
                throw new RuntimeException(value[0].getClass().getName() + ", " + value[1].getClass().getName() + ", " + value[2].getClass().getName() + ", " + value[3].getClass().getName() + " types are not supported.");
            }
        } else {
            throw new RuntimeException("Number of arguments is not supported.");
        }

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
