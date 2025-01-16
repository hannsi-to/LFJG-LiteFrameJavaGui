package me.hannsi.lfjg.render.openGL.renderers.font;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.render.openGL.system.font.CFont;
import me.hannsi.lfjg.render.openGL.system.font.CharInfo;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL15;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15C.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_BUFFER;

public class Batch {
    public static final int BATCH_SIZE = 100;
    public static final int VERTEX_SIZE = 8;
    private final int[] indices = {0, 1, 3, 1, 2, 3};
    public float[] vertices;
    public int size = 0;
    public Matrix4f projectionMatrix;

    public int vao;
    public int vbo;
    public ShaderProgram shaderProgram;
    public CFont font;

    public Batch(Projection projection) {
        this(projection.getProjMatrix());
    }

    public Batch(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        this.vertices = new float[BATCH_SIZE * VERTEX_SIZE];
    }

    private void generateEbo() {
        int elementSize = BATCH_SIZE * 3;
        int[] elementBuffer = new int[elementSize];

        for (int i = 0; i < elementSize; i++) {
            elementBuffer[i] = indices[(i % 6)] + ((i / 6) * 4);
        }

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
    }

    private void generateShader() {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(new ResourcesLocation("shader/scene/font/VertexShader.vsh"));
        shaderProgram.createFragmentShader(new ResourcesLocation("shader/scene/font/FragmentShader.fsh"));
        shaderProgram.link();
    }

    public void initBatch() {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (long) Float.BYTES * VERTEX_SIZE * BATCH_SIZE, GL_DYNAMIC_DRAW);

        generateEbo();

        int stride = VERTEX_SIZE * Float.BYTES;
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 4, GL_FLOAT, false, stride, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);

        generateShader();
    }

    public void flushBatch() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (long) Float.BYTES * VERTEX_SIZE * BATCH_SIZE, GL_DYNAMIC_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        shaderProgram.bind();

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_BUFFER, font.textureId);

        shaderProgram.setUniform1i("uFontTexture", 0);
        shaderProgram.setUniformMatrix4fv("uProjection", projectionMatrix);

        glBindVertexArray(vao);

        glDrawElements(GL_TRIANGLES, size * 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);

        glBindTexture(GL_TEXTURE_BUFFER, 0);

        shaderProgram.unbind();

        size = 0;
    }

    public void addCharacter(float x, float y, float scale, CharInfo charInfo, Color color) {
        if (size >= BATCH_SIZE - 4) {
            flushBatch();
        }

        float r = color.getRedF();
        float g = color.getGreenF();
        float b = color.getBlueF();
        float a = color.getAlphaF();

        float x1 = x + scale * charInfo.getWidth();
        float y1 = y + scale * charInfo.getHeight();

        float ux0 = charInfo.textureCoordinates[0].x;
        float uy0 = charInfo.textureCoordinates[0].y;
        float ux1 = charInfo.textureCoordinates[1].x;
        float uy1 = charInfo.textureCoordinates[1].y;

        int index = size * VERTEX_SIZE;
        vertices[index] = x1;
        vertices[index + 1] = y;
        vertices[index + 2] = r;
        vertices[index + 3] = g;
        vertices[index + 4] = b;
        vertices[index + 5] = a;
        vertices[index + 6] = ux1;
        vertices[index + 7] = uy0;

        index += VERTEX_SIZE;
        vertices[index] = x1;
        vertices[index + 1] = y1;
        vertices[index + 2] = r;
        vertices[index + 3] = g;
        vertices[index + 4] = b;
        vertices[index + 5] = a;
        vertices[index + 6] = ux1;
        vertices[index + 7] = uy1;

        index += VERTEX_SIZE;
        vertices[index] = x;
        vertices[index + 1] = y1;
        vertices[index + 2] = r;
        vertices[index + 3] = g;
        vertices[index + 4] = b;
        vertices[index + 5] = a;
        vertices[index + 6] = ux0;
        vertices[index + 7] = uy1;

        index += VERTEX_SIZE;
        vertices[index] = x;
        vertices[index + 1] = y;
        vertices[index + 2] = r;
        vertices[index + 3] = g;
        vertices[index + 4] = b;
        vertices[index + 5] = a;
        vertices[index + 6] = ux0;
        vertices[index + 7] = uy0;

        size += 4;
    }

    public void addText(String text, float x, float y, float scale, Color color) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            CharInfo charInfo = font.getCharacter(c);
            if (charInfo.getWidth() == 0) {
                DebugLog.warning(getClass(), "Unknown character " + c);
                continue;
            }

            float xPos = x;
            addCharacter(xPos, y, scale, charInfo, color);
            x += (int) (charInfo.getWidth() * scale);
        }
    }

    public float getFontWidth(String text) {
        return font.getFontWidth(text);
    }

    public float getFontHeight() {
        return font.getFontHeight();
    }
}
