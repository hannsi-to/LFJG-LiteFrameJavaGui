package me.hannsi.lfjg.render.openGL.renderers.font;

import me.hannsi.lfjg.render.openGL.system.font.CFont;
import me.hannsi.lfjg.render.openGL.system.font.CharInfo;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.math.StringUtil;
import me.hannsi.lfjg.utils.math.TextFormat;
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

/**
 * Class representing a batch renderer for fonts in OpenGL.
 */
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

    /**
     * Constructs a new Batch with the specified projection.
     *
     * @param projection the projection to use
     */
    public Batch(Projection projection) {
        this(projection.getProjMatrix());
    }

    /**
     * Constructs a new Batch with the specified projection matrix.
     *
     * @param projectionMatrix the projection matrix to use
     */
    public Batch(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        this.vertices = new float[BATCH_SIZE * VERTEX_SIZE];
    }

    /**
     * Generates the element buffer object (EBO) for the batch.
     */
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

    /**
     * Generates the shader program for the batch.
     */
    private void generateShader() {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(new ResourcesLocation("shader/scene/font/VertexShader.vsh"));
        shaderProgram.createFragmentShader(new ResourcesLocation("shader/scene/font/FragmentShader.fsh"));
        shaderProgram.link();
    }

    /**
     * Initializes the batch by setting up the VAO, VBO, and shaders.
     */
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

    /**
     * Flushes the batch, rendering all characters and resetting the batch.
     */
    public void flushBatch() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (long) Float.BYTES * VERTEX_SIZE * BATCH_SIZE, GL_DYNAMIC_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        shaderProgram.bind();

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_BUFFER, font.textureId);

        shaderProgram.setUniform1i("uFontTexture", 0);
        shaderProgram.setUniform("uProjection", projectionMatrix);

        glBindVertexArray(vao);

        glDrawElements(GL_TRIANGLES, size * 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);

        glBindTexture(GL_TEXTURE_BUFFER, 0);

        shaderProgram.unbind();

        size = 0;
    }

    /**
     * Adds a character to the batch.
     *
     * @param x the x position of the character
     * @param y the y position of the character
     * @param scale the scale of the character
     * @param italic the italic amount of the character
     * @param charInfo the character information
     * @param color the color of the character
     */
    public void addCharacter(float x, float y, float scale, float italic, CharInfo charInfo, Color color) {
        if (size >= BATCH_SIZE - 4) {
            flushBatch();
        }

        float r = color.getRedF();
        float g = color.getGreenF();
        float b = color.getBlueF();
        float a = color.getAlphaF();

        float uItalic = (italic * scale) / (2 * scale);
        x += (italic * scale) / (4 * scale);
        float x1 = x + scale * charInfo.getWidth();
        float y1 = y + scale * charInfo.getHeight();

        float ux0 = charInfo.textureCoordinates[0].x;
        float uy0 = charInfo.textureCoordinates[0].y;
        float ux1 = charInfo.textureCoordinates[1].x;
        float uy1 = charInfo.textureCoordinates[1].y;

        int index = size * VERTEX_SIZE;
        vertices[index] = x1 - uItalic;
        vertices[index + 1] = y;
        vertices[index + 2] = r;
        vertices[index + 3] = g;
        vertices[index + 4] = b;
        vertices[index + 5] = a;
        vertices[index + 6] = ux1;
        vertices[index + 7] = uy0;

        index += VERTEX_SIZE;
        vertices[index] = x1 + uItalic;
        vertices[index + 1] = y1;
        vertices[index + 2] = r;
        vertices[index + 3] = g;
        vertices[index + 4] = b;
        vertices[index + 5] = a;
        vertices[index + 6] = ux1;
        vertices[index + 7] = uy1;

        index += VERTEX_SIZE;
        vertices[index] = x + uItalic;
        vertices[index + 1] = y1;
        vertices[index + 2] = r;
        vertices[index + 3] = g;
        vertices[index + 4] = b;
        vertices[index + 5] = a;
        vertices[index + 6] = ux0;
        vertices[index + 7] = uy1;

        index += VERTEX_SIZE;
        vertices[index] = x - uItalic;
        vertices[index + 1] = y;
        vertices[index + 2] = r;
        vertices[index + 3] = g;
        vertices[index + 4] = b;
        vertices[index + 5] = a;
        vertices[index + 6] = ux0;
        vertices[index + 7] = uy0;

        size += 4;
    }

    /**
     * Adds text to the batch.
     *
     * @param text the text to add
     * @param x the x position of the text
     * @param y the y position of the text
     * @param scale the scale of the text
     * @param color the color of the text
     */
    public void addText(String text, float x, float y, float scale, Color color) {
        boolean format = false;
        String formatCode = "";

        Color color1 = color;
        boolean newLine = false;
        float xPos = x;
        float yPos = y;
        float italic = 0.0f;
        boolean underLine = false;
        float bold = 1f;
        boolean obfuscated = false;
        boolean spaceCheckX = false;
        boolean spaceCheckedX = false;
        String spaceXs = "";
        float spaceXf = 0.0f;
        boolean spaceCheckY = false;
        boolean spaceCheckedY = false;
        String spaceYs = "";
        float spaceYf = 0.0f;
        boolean reset = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (format) {
                formatCode = TextFormat.PREFIX_CODE + String.valueOf(c);
                format = false;
            }

            if (TextFormat.isPrefix(c)) {
                format = true;
                reset = true;
                continue;
            }

            if (spaceCheckX) {
                if (c == '{') {
                    continue;
                }

                if (c == '}') {
                    spaceXf = Float.parseFloat(spaceXs);
                    spaceCheckX = false;
                    spaceCheckedX = true;

                    continue;
                }

                spaceXs += String.valueOf(c);
                continue;
            }

            if (spaceCheckY) {
                if (c == '{') {
                    continue;
                }

                if (c == '}') {
                    spaceYf = Float.parseFloat(spaceYs);
                    spaceCheckY = false;
                    spaceCheckedY = true;

                    continue;
                }

                spaceYs += String.valueOf(c);
                continue;
            }

            CharInfo charInfo = font.getCharacter(c);
            if (charInfo.getWidth() == 0) {
                continue;
            }

            shaderProgram.bind();
            shaderProgram.setUniform("uItalicAmount", 0.0f);

            if (formatCode.equals(TextFormat.NEWLINE) && !newLine) {
                yPos -= charInfo.getHeight() * scale + spaceYf;
                newLine = true;
            }
            if (formatCode.equals(TextFormat.ITALIC)) {
                italic = 20f;
            }
//            if (formatCode.equals(TextFormat.UNDERLINE)) {
//                underLine = true;
//            }
            if (formatCode.equals(TextFormat.BOLD)) {
                bold = 1.2f;
            }
            if (formatCode.equals(TextFormat.OBFUSCATED)) {
                obfuscated = true;
            }
            if (formatCode.equals(TextFormat.SPASE_X) && !spaceCheckedX) {
                spaceCheckX = true;
            }
            if (formatCode.equals(TextFormat.SPASE_Y) && !spaceCheckedY) {
                spaceCheckY = true;
            }
            if (formatCode.equals(TextFormat.RESET) && reset) {
                color1 = color;
                newLine = false;
                italic = 0.0f;
                underLine = false;
                bold = 1f;
                obfuscated = false;
                spaceCheckedX = false;
                spaceXs = "";
                spaceXf = 0.0f;
                spaceCheckedY = false;
                spaceYs = "";
                spaceYf = 0.0f;
                reset = false;
            }
            if (formatCode.equals(TextFormat.RESET_POINT_X)) {
                xPos = x;
            }
            if (formatCode.equals(TextFormat.RESET_POINT_Y)) {
                yPos = y;
            }

            if (!formatCode.isEmpty()) {
                color1 = TextFormat.getColor(formatCode, color1);
                formatCode = "";
                continue;
            }

            shaderProgram.unbind();

            if (obfuscated) {
                c = StringUtil.getRandomCharacter("0123456789abcdefghijklmnopqrstyvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
                charInfo = font.getCharacter(c);
                if (charInfo.getWidth() == 0) {
                    continue;
                }
            }

            Color newColor = color1;

            addCharacter(xPos, yPos, scale * bold, italic, charInfo, newColor);

            xPos += (charInfo.getWidth() * scale * bold) + spaceXf;
        }
    }

    /**
     * Gets the width of the specified text.
     *
     * @param text the text to measure
     * @return the width of the text
     */
    public float getFontWidth(String text) {
        return font.getFontWidth(text);
    }

    /**
     * Gets the height of the specified text.
     *
     * @return the height of the text
     */
    public float getFontHeight() {
        return font.getFontHeight();
    }
}