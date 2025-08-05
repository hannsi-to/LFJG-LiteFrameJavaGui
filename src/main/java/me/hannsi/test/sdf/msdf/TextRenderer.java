package me.hannsi.test.sdf.msdf;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.StringUtil;
import me.hannsi.lfjg.render.renderers.font.CharState;
import me.hannsi.lfjg.render.renderers.font.TextFormatType;
import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

public class TextRenderer {
    protected List<LineData> lineDatum;
    protected ShaderProgram msdfFontShaderProgram;
    protected ShaderProgram lineShaderProgram;
    protected Mesh lineMesh;
    protected VAORendering vaoRendering;

    private Matrix4f viewMatrix = new Matrix4f();
    private Color defaultFontColor = Color.WHITE;
    private Matrix4f modelMatrix = new Matrix4f();

    private MSDFTextureLoader msdfTextureLoader;
    private TextMeshBuilder textMeshBuilder;
    private Vector2f pos = new Vector2f(100, 100);
    private int size = 32;
    private boolean chatFormat;

    TextRenderer() {
        this.lineDatum = new ArrayList<>();

        this.msdfFontShaderProgram = new ShaderProgram();
        this.msdfFontShaderProgram.createVertexShader(Location.fromResource("shader/msdf/VertexShader.vsh"));
        this.msdfFontShaderProgram.createFragmentShader(Location.fromResource("shader/msdf/FragmentShader.fsh"));
        this.msdfFontShaderProgram.link();

        this.lineShaderProgram = new ShaderProgram();
        this.lineShaderProgram.createVertexShader(Location.fromResource("shader/scene/object/VertexShader.vsh"));
        this.lineShaderProgram.createFragmentShader(Location.fromResource("shader/scene/object/FragmentShader.fsh"));
        this.lineShaderProgram.link();

        float[] positions = new float[]{0, 0, 0, 1, 1, 1, 1, 0};
        float[] colors = defaultFontColor.getFloatArray(4);
        this.lineMesh = Mesh.createMesh()
                .createBufferObject2D(positions, colors, null);

        this.vaoRendering = new VAORendering();
    }

    public static TextRenderer createTextRender() {
        return new TextRenderer();
    }

    public TextRenderer msdfTextureLoader(MSDFTextureLoader msdfTextureLoader) {
        this.msdfTextureLoader = msdfTextureLoader;
        return this;
    }

    public TextRenderer textMeshBuilder(TextMeshBuilder textMeshBuilder) {
        this.textMeshBuilder = textMeshBuilder;
        return this;
    }

    public TextRenderer pos(Vector2f pos) {
        this.pos = pos;
        return this;
    }

    public TextRenderer size(int size) {
        this.size = size;
        return this;
    }

    public TextRenderer defaultFontColor(Color defaultFontColor) {
        this.defaultFontColor = defaultFontColor;
        this.lineMesh.updateVBOData(BufferObjectType.COLORS_BUFFER, defaultFontColor.getFloatArray(4));
        return this;
    }

    public TextRenderer charFormat(boolean chatFormat) {
        this.chatFormat = chatFormat;

        return this;
    }

    public TextRenderer init() {
        return this;
    }

    public float getTextWidth(String text) {
        float maxWidth = 0f;
        float currentLineWidth = 0f;

        for (int i = 0; i < text.length(); i++) {
            int codePoint = text.codePointAt(i);

            if (codePoint == '\n') {
                maxWidth = max(maxWidth, currentLineWidth);
                currentLineWidth = 0f;
                continue;
            }

            MSDFFont.Glyph glyph = textMeshBuilder.getGlyphMap().get(codePoint);
            if (glyph != null) {
                currentLineWidth += glyph.getAdvance() * size;
            }
        }

        maxWidth = max(maxWidth, currentLineWidth);
        return maxWidth;
    }

    public float getTextHeight(String text) {
        float maxGlyphHeight = 0f;
        int lineCount = 1;

        for (int i = 0; i < text.length(); i++) {
            int codePoint = text.codePointAt(i);

            if (codePoint == '\n') {
                lineCount++;
                continue;
            }

            MSDFFont.Glyph glyph = textMeshBuilder.getGlyphMap().get(codePoint);
            if (glyph != null) {
                maxGlyphHeight = max(maxGlyphHeight, textMeshBuilder.getMsdfFont().getMetrics().getLineHeight() * size);
            }
        }

        return lineCount * maxGlyphHeight;
    }

    public TextRenderer draw(String text) {
        msdfFontShaderProgram.bind();

        msdfFontShaderProgram.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, Core.projection2D.getProjMatrix());
        msdfFontShaderProgram.setUniform("viewMatrix", UploadUniformType.PER_FRAME, viewMatrix);
        msdfFontShaderProgram.setUniform("fontAtlas", UploadUniformType.ONCE, 0);
        msdfFontShaderProgram.setUniform("distanceRange", UploadUniformType.ONCE, (float) textMeshBuilder.getMsdfFont().getAtlas().getDistanceRange());

        GLStateCache.enable(GL_BLEND);
        GLStateCache.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GLStateCache.enable(GL_TEXTURE_2D);
        GLStateCache.activeTexture(GL_TEXTURE0);
        GLStateCache.bindTexture(GL_TEXTURE_2D, msdfTextureLoader.textureId);

        boolean code = false;
        TextFormatType textFormatType;
        CharState charState = new CharState(defaultFontColor);

        float cursorX = pos.x();
        float cursorY = pos.y();
        float spaseX = 0f;
        float spaseY = 0f;
        LineData underLineData = null;
        LineData doubleUnderLineData = null;
        LineData strikethroughLineData = null;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.toCharArray()[i];
            if (ch == TextFormatType.PREFIX_CODE) {
                code = true;
                continue;
            }

            if (code) {
                textFormatType = TextFormatType.getTextFormatType(ch);
                code = false;
                if (textFormatType != null) {
                    charState.setState(textFormatType);

                    if (textFormatType == TextFormatType.NEWLINE) {
                        cursorX = pos.x();
                        cursorY -= getTextHeight(String.valueOf(ch));
                        cursorY += spaseY;
                    }
                } else {
                    new LogGenerator("TextFormat Message", "Source: GLFont", "Type: No Code", "ID: " + hashCode(), "Severity: Waring", "Message: Not font text format code: " + TextFormatType.PREFIX_CODE + ch).logging(DebugLevel.WARNING);
                }

                continue;
            }

            if (charState.obfuscated) {
                ch = StringUtil.getRandomCharacter(StringUtil.getStringFromChars(textMeshBuilder.getChars()));
            }

            if (charState.spaseX || charState.spaseY) {
                if (ch == '{') {
                    charState.spaseCheck = true;
                    continue;
                } else if (ch == '}') {
                    charState.spaseCheck = false;

                    if (charState.spaseX) {
                        spaseX = Float.parseFloat(charState.value);
                        charState.value = "";
                        charState.spaseX = false;
                    }
                    if (charState.spaseY) {
                        spaseY = Float.parseFloat(charState.value);
                        charState.value = "";
                        charState.spaseY = false;
                    }
                    continue;
                } else if (charState.spaseCheck) {
                    charState.value += StringUtil.getStringFromChar(ch);
                    continue;
                }
            }

            int charCode = ch;
            MSDFFont.Glyph glyph = textMeshBuilder.getGlyphMap().get(charCode);
            if (glyph == null) {
                continue;
            }

            float advance = glyph.getAdvance();
            if (glyph.getPlaneBounds() == null || glyph.getAtlasBounds() == null) {
                cursorX += advance * size;
                continue;
            }

            TextMeshBuilder.TextMesh textMesh = textMeshBuilder.getTextMeshMap().get(charCode);
            if (textMesh == null) {
                DebugLog.error(getClass(), "Code point " + charCode + " is not generated by " + TextMeshBuilder.class.getSimpleName());
                continue;
            }

            if (!charState.skip) {
                if (charState.underLine) {
                    underLineData = new LineData(LineData.LineType.UNDERLINE, cursorX, cursorX, cursorY, charState.color);
                }

                if (charState.doubleUnderLine) {
                    doubleUnderLineData = new LineData(LineData.LineType.DOUBLE_UNDERLINE, cursorX, cursorX, cursorY, charState.color);
                }

                if (charState.strikethrough) {
                    strikethroughLineData = new LineData(LineData.LineType.STRIKETHROUGH, cursorX, cursorX, cursorY, charState.color);
                }

                if (charState.italic) {
                    msdfFontShaderProgram.setUniform("italicSkew", UploadUniformType.ON_CHANGE, 0.4f);
                } else {
                    msdfFontShaderProgram.setUniform("italicSkew", UploadUniformType.ON_CHANGE, 0f);
                }

                if (charState.bold) {
                    msdfFontShaderProgram.setUniform("boldness", UploadUniformType.ON_CHANGE, 0.24f);
                } else {
                    msdfFontShaderProgram.setUniform("boldness", UploadUniformType.ON_CHANGE, 0f);
                }

                if (charState.ghost) {
                    msdfFontShaderProgram.setUniform("boldness", UploadUniformType.ON_CHANGE, -0.5f);
                } else {
                    msdfFontShaderProgram.setUniform("boldness", UploadUniformType.ON_CHANGE, 0f);
                }

                if (charState.box) {
                    msdfFontShaderProgram.setUniform("box", UploadUniformType.PER_FRAME, true);
                    msdfFontShaderProgram.setUniform("uvSize", UploadUniformType.PER_FRAME, new Vector4f(textMesh.uvs[0], textMesh.uvs[1], textMesh.uvs[2], textMesh.uvs[5]));
                } else {
                    msdfFontShaderProgram.setUniform("box", UploadUniformType.ON_CHANGE, false);
                }

                if (charState.outLine) {
                    msdfFontShaderProgram.setUniform("outline", UploadUniformType.ON_CHANGE, true);
                    msdfFontShaderProgram.setUniform("outlineWidth", UploadUniformType.ON_CHANGE, 0f);
                } else {
                    msdfFontShaderProgram.setUniform("outline", UploadUniformType.ON_CHANGE, false);
                }

                msdfFontShaderProgram.setUniform("fontColor", UploadUniformType.ON_CHANGE, charState.color);
                msdfFontShaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, modelMatrix.translate(cursorX + (charState.shadow ? size * 0.02f : 0), cursorY - (charState.shadow ? size * 0.02f : 0), 0).scale(size, size, 1));
                vaoRendering.draw(textMesh.mesh, GL_TRIANGLES);
            }

            cursorX += advance * size + spaseX;

            if (underLineData != null) {
                underLineData.endX = cursorX;
                lineDatum.add(underLineData.newInstance());
                underLineData = null;
            }
            if (doubleUnderLineData != null) {
                doubleUnderLineData.endX = cursorX;
                lineDatum.add(doubleUnderLineData.newInstance());
                doubleUnderLineData.baseY = doubleUnderLineData.baseY - textMeshBuilder.getMsdfFont().getMetrics().getUnderlineThickness() * size * 2;
                lineDatum.add(doubleUnderLineData.newInstance());
                doubleUnderLineData = null;
            }
            if (strikethroughLineData != null) {
                strikethroughLineData.endX = cursorX;
                lineDatum.add(strikethroughLineData.newInstance());
                strikethroughLineData = null;
            }
            modelMatrix.identity();
        }

        if (!lineDatum.isEmpty()) {
            GLStateCache.disable(GL_TEXTURE_2D);
        }
        for (LineData data : lineDatum) {
            lineShaderProgram.bind();

            float thickness;

            float x;
            float y;

            float width;

            MSDFFont.Metrics metrics = textMeshBuilder.getMsdfFont().getMetrics();
            switch (data.lineType) {
                case UNDERLINE, DOUBLE_UNDERLINE -> {
                    float offsetY = metrics.getUnderlineY() * size;
                    thickness = metrics.getUnderlineThickness() * size;

                    x = data.startX;
                    y = data.baseY + offsetY;

                    width = data.endX - data.startX;
                }
                case STRIKETHROUGH -> {
                    float offsetY = metrics.getAscender() * 0.3f * size;
                    thickness = metrics.getUnderlineThickness() * 1.5f * size;

                    x = data.startX;
                    y = data.baseY + offsetY;

                    width = data.endX - data.startX;
                }
                default -> throw new IllegalStateException("Unexpected value: " + data.lineType);
            }

            lineShaderProgram.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, Core.projection2D.getProjMatrix());
            lineShaderProgram.setUniform("viewMatrix", UploadUniformType.PER_FRAME, viewMatrix);
            lineShaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, modelMatrix.translate(x, y, 0).scale(width, thickness, 1));
            lineShaderProgram.setUniform("color", UploadUniformType.PER_FRAME, data.color);

            vaoRendering.draw(lineMesh);

            modelMatrix.identity();
        }
        lineDatum.clear();

        return this;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    public MSDFTextureLoader getMsdfTextureLoader() {
        return msdfTextureLoader;
    }

    public void setMsdfTextureLoader(MSDFTextureLoader msdfTextureLoader) {
        this.msdfTextureLoader = msdfTextureLoader;
    }

    public TextMeshBuilder getTextMeshBuilder() {
        return textMeshBuilder;
    }

    public void setTextMeshBuilder(TextMeshBuilder textMeshBuilder) {
        this.textMeshBuilder = textMeshBuilder;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Color getDefaultFontColor() {
        return defaultFontColor;
    }

    public void setDefaultFontColor(Color defaultFontColor) {
        this.defaultFontColor = defaultFontColor;
    }

    public boolean isChatFormat() {
        return chatFormat;
    }

    public void setChatFormat(boolean chatFormat) {
        this.chatFormat = chatFormat;
    }

    public static class LineData {
        public LineType lineType;
        public float startX;
        public float endX;
        public float baseY;
        public Color color;

        public LineData(LineType lineType, float startX, float endX, float baseY, Color color) {
            this.lineType = lineType;
            this.startX = startX;
            this.endX = endX;
            this.baseY = baseY;
            this.color = color;
        }

        public LineData newInstance() {
            return new LineData(lineType, startX, endX, baseY, color);
        }

        public enum LineType {
            UNDERLINE,
            STRIKETHROUGH,
            DOUBLE_UNDERLINE
        }
    }
}
