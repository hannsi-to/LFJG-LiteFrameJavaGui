package me.hannsi.lfjg.render.renderers.text;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.StringUtil;
import me.hannsi.lfjg.render.debug.exceptions.UnknownAlignType;
import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import me.hannsi.lfjg.render.system.text.AlignType;
import me.hannsi.lfjg.render.system.text.CharState;
import me.hannsi.lfjg.render.system.text.TextFormatType;
import me.hannsi.lfjg.render.system.text.TextMeshBuilder;
import me.hannsi.lfjg.render.system.text.msdf.MSDFFont;
import me.hannsi.lfjg.render.system.text.msdf.MSDFTextureLoader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.render.system.text.Align.*;
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
    private boolean textFormat;
    private int align = AlignType.CENTER_BOTTOM.getId();

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

    public TextRenderer textFormat(boolean textFormat) {
        this.textFormat = textFormat;
        return this;
    }


    public TextRenderer align(int align) {
        this.align = align;
        return this;
    }

    public TextRenderer align(AlignType alignType) {
        this.align = alignType.getId();
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

            maxGlyphHeight = MathHelper.max(maxGlyphHeight, textMeshBuilder.getMsdfFont().getMetrics().getLineHeight() * size);
        }

        return lineCount * maxGlyphHeight;
    }

    public float[] setAlignPos(String text) {
        float[] position = new float[2];

        if ((align & ALIGN_LEFT) != 0) {
            position[0] = pos.x();
        } else if ((align & ALIGN_CENTER) != 0) {
            position[0] = pos.x() - getTextWidth(text) / 2f;
        } else if ((align & ALIGN_RIGHT) != 0) {
            position[0] = pos.x() - getTextWidth(text);
        } else {
            throw new UnknownAlignType("This alignment constant does not exist. const: " + align);
        }

        if ((align & ALIGN_TOP) != 0) {
            position[1] = pos.y() - getTextHeight(text);
        } else if ((align & ALIGN_MIDDLE) != 0) {
            position[1] = pos.y() - getTextHeight(text) / 2f;
        } else if ((align & ALIGN_BASELINE) != 0) {
            position[1] = pos.y();
        } else if ((align & ALIGN_BOTTOM) != 0) {
            MSDFFont.Metrics metrics = textMeshBuilder.getMsdfFont().getMetrics();
            float offset = metrics.getDescender();
            position[1] = pos.y() - (offset * size);
        } else {
            throw new UnknownAlignType("This alignment constant does not exist. const: " + align);
        }

        return position;
    }

    public TextRenderer draw(String text) {
        GLStateCache.enable(GL_BLEND);
        GLStateCache.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GLStateCache.enable(GL_TEXTURE_2D);
        GLStateCache.activeTexture(GL_TEXTURE0);
        GLStateCache.bindTexture(GL_TEXTURE_2D, msdfTextureLoader.textureId);

        msdfFontShaderProgram.bind();

        msdfFontShaderProgram.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, Core.projection2D.getProjMatrix());
        msdfFontShaderProgram.setUniform("viewMatrix", UploadUniformType.PER_FRAME, viewMatrix);
        msdfFontShaderProgram.setUniform("fontAtlas", UploadUniformType.ONCE, 0);
        msdfFontShaderProgram.setUniform("distanceRange", UploadUniformType.ONCE, (float) textMeshBuilder.getMsdfFont().getAtlas().getDistanceRange());

        boolean code = false;
        TextFormatType textFormatType;
        CharState charState = new CharState(defaultFontColor);

        float[] alignPos = setAlignPos(text);
        float cursorX = alignPos[0];
        float cursorY = alignPos[1];

        float spaseX = 0f;
        float spaseY = 0f;
        StringBuilder value = null;
        LineData underLineData = null;
        LineData doubleUnderLineData = null;
        LineData strikethroughLineData = null;
        LineData doubleStrikethroughData = null;
        LineData overLineData = null;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == TextFormatType.PREFIX_CODE && textFormat) {
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
                cursorX = alignPos[0];
                cursorY = alignPos[1];
            }

            if (charState.spaseX || charState.spaseY) {
                if (ch == '{') {
                    charState.spaseCheck = true;
                    value = new StringBuilder();
                    continue;
                } else if (ch == '}') {
                    charState.spaseCheck = false;
                    assert value != null;
                    charState.value = value.toString();

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
                    if (value != null) {
                        value.append(StringUtil.getStringFromChar(ch));
                    }
                    continue;
                }
            }

            int charCode = ch;

            TextMeshBuilder.TextMesh textMesh = textMeshBuilder.getTextMeshMap().get(charCode);
            if (textMesh == null && charCode != 32) {
                DebugLog.error(getClass(), "Code point " + charCode + "(" + ch + ") is not generated by " + TextMeshBuilder.class.getSimpleName());
                continue;
            }

            MSDFFont.Glyph glyph = textMeshBuilder.getGlyphMap().get(charCode);
            if (glyph == null) {
                continue;
            }
            MSDFFont.Metrics metrics = textMeshBuilder.getMsdfFont().getMetrics();
            float bearingY = 0f;
            if (glyph.getPlaneBounds().getBottom() < metrics.getDescender()) {
                bearingY = -glyph.getPlaneBounds().getBottom() * size;
            }

            float advance = glyph.getAdvance();
            if (glyph.getPlaneBounds() == null || glyph.getAtlasBounds() == null) {
                cursorX += advance * size;
                continue;
            }

            assert textMesh != null;
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

                if (charState.doubleStrikethrough) {
                    doubleStrikethroughData = new LineData(LineData.LineType.DOUBLE_STRIKETHROUGH, cursorX, cursorX, cursorY, charState.color);
                }

                if (charState.overLine) {
                    overLineData = new LineData(LineData.LineType.OVERLINE, cursorX, cursorX, cursorY, charState.color);
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
                    msdfFontShaderProgram.setUniform("outlineWidth", UploadUniformType.ONCE, 0f);
                } else {
                    msdfFontShaderProgram.setUniform("outline", UploadUniformType.ON_CHANGE, false);
                }

                float glyphYOffset = -glyph.getPlaneBounds().getBottom() * size;
                modelMatrix.translate(cursorX + (charState.shadow ? size * 0.02f : 0), cursorY - bearingY + (charState.shadow ? size * 0.02f : 0) + glyphYOffset, 0).scale(size, size, 1);
                msdfFontShaderProgram.setUniform("fontColor", UploadUniformType.ON_CHANGE, charState.color);
                msdfFontShaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, modelMatrix);
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
            if (doubleStrikethroughData != null) {
                doubleStrikethroughData.endX = cursorX;
                doubleStrikethroughData.baseY += textMeshBuilder.getMsdfFont().getMetrics().getUnderlineThickness() * 1.5f * size;
                lineDatum.add(doubleStrikethroughData.newInstance());
                doubleStrikethroughData.baseY -= textMeshBuilder.getMsdfFont().getMetrics().getUnderlineThickness() * 1.5f * size * 2;
                lineDatum.add(doubleStrikethroughData.newInstance());
                doubleStrikethroughData = null;
            }
            if (overLineData != null) {
                overLineData.endX = cursorX;
                lineDatum.add(overLineData);
                overLineData = null;
            }

            modelMatrix.identity();
        }

        for (int i = 0, lineDatumSize = lineDatum.size(); i < lineDatumSize; i++) {
            LineData data = lineDatum.get(i);
            lineShaderProgram.bind();

            float x;
            float y;
            float thickness;
            float width;

            MSDFFont.Metrics metrics = textMeshBuilder.getMsdfFont().getMetrics();
            float offsetY;
            switch (data.lineType) {
                case UNDERLINE:
                case DOUBLE_UNDERLINE:
                    offsetY = metrics.getUnderlineY() * size;
                    thickness = metrics.getUnderlineThickness() * size;

                    x = data.startX;
                    y = data.baseY + offsetY;

                    width = data.endX - data.startX;
                    break;
                case STRIKETHROUGH:
                case DOUBLE_STRIKETHROUGH:
                    offsetY = metrics.getAscender() * 0.3f * size;
                    thickness = metrics.getUnderlineThickness() * 1.5f * size;

                    x = data.startX;
                    y = data.baseY + offsetY;

                    width = data.endX - data.startX;
                    break;
                case OVERLINE:
                    offsetY = metrics.getAscender() * size;
                    thickness = metrics.getUnderlineThickness() * size;

                    x = data.startX;
                    y = data.baseY + offsetY;

                    width = data.endX - data.startX;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + data.lineType);
            }

            lineShaderProgram.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, Core.projection2D.getProjMatrix());
            lineShaderProgram.setUniform("viewMatrix", UploadUniformType.PER_FRAME, viewMatrix);
            lineShaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, modelMatrix.translate(x, y, 0).scale(width, thickness, 1));
            lineShaderProgram.setUniform("color", UploadUniformType.PER_FRAME, data.color);
            lineShaderProgram.setUniform("replaceColor", UploadUniformType.ONCE, true);

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

    public boolean isTextFormat() {
        return textFormat;
    }

    public void setTextFormat(boolean textFormat) {
        this.textFormat = textFormat;
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
            DOUBLE_UNDERLINE,
            DOUBLE_STRIKETHROUGH,
            OVERLINE
        }
    }
}
