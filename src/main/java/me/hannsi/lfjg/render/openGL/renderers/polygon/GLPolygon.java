package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.system.VAORendering;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VAO;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VBO;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.BlendType;
import me.hannsi.lfjg.utils.type.types.DrawType;
import me.hannsi.lfjg.utils.type.types.ShaderRenderingType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GLPolygon {
    private Frame frame;
    private DrawType drawType;
    private float lineWidth = -1f;
    private float pointSize = -1f;
    private VBO vboVertex;
    private VBO vboColor;
    private VBO vboTexture;
    private VAO vao;
    private VAORendering vaoRendering;
    private Matrix4f modelMatrix;
    private ResourcesLocation vertexShader;
    private ResourcesLocation fragmentShader;
    private List<EffectBase> effectBaseList;
    private int shaderTarget = -1;
    private BlendType blendType;

    public GLPolygon(Frame frame) {
        this.frame = frame;
        this.vboVertex = new VBO(0, 1, 2);
        this.effectBaseList = new ArrayList<>();
        this.vaoRendering = new VAORendering(frame);
        this.modelMatrix = new Matrix4f();
        this.blendType = BlendType.MULTIPLY;
    }

    public GLPolygon put() {
        return this;
    }

    public GLPolygon uv(Vector2f vector2f) {
        if (this.vboTexture == null) {
            this.vboTexture = new VBO(2, 1, 2);
        }

        this.vboTexture.put(vector2f);

        return this;
    }

    public GLPolygon vertex(Vector2f vector2f) {
        this.vboVertex.put(vector2f);

        return this;
    }

    public GLPolygon color(Color color) {
        if (color != null) {
            if (this.vboColor == null) {
                this.vboColor = new VBO(1, 1, 4);
            }

            this.vboColor.put(color);
        }

        return this;
    }

    public void end() {
    }

    public GLPolygon rendering() {
        for (EffectBase effectBase : effectBaseList) {
            effectBase.rendering(frame, this);
        }

        vboVertex.flip();
        if (vboColor != null) {
            vboColor.flip();
            shaderTarget = ShaderRenderingType.SHADER_COLOR.getId();
        }
        if (vboTexture != null) {
            vboTexture.flip();

            if (shaderTarget != -1) {
                shaderTarget = shaderTarget | ShaderRenderingType.SHADER_TEXTURE.getId();
            } else {
                shaderTarget = ShaderRenderingType.SHADER_TEXTURE.getId();
            }
        }

        vao = new VAO();

        vao.setVertexVBO(vboVertex);
        if (vboColor != null) {
            vao.setColorVBO(vboColor);
        }
        if (vboTexture != null) {
            vao.setTextureVBO(vboTexture);
        }

        vao.bindVertexArray();
        vao.configureAttributes();
        vao.unBindVertexArray();

        return this;
    }

    public GLPolygon drawType(DrawType drawType) {
        this.drawType = drawType;

        return this;
    }

    public GLPolygon lineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    public GLPolygon pointSize(float pointSize) {
        this.pointSize = pointSize;
        return this;
    }

    public void addEffect(EffectBase effectBase) {
        this.effectBaseList.add(effectBase);
    }

    public void cleanUp() {
        vaoRendering.cleanUp();
    }

    public void init() {
        vertexShader = new ResourcesLocation("shader/vertexShader.vsh");
        fragmentShader = new ResourcesLocation("shader/FragmentShader.fsh");

        vaoRendering.init(vertexShader, fragmentShader);
    }

    public void draw() {
        GL11.glPushMatrix();

        vaoRendering.setVao(vao);

        if (lineWidth != -1f) {
            GL11.glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            GL11.glPointSize(pointSize);
        }

        for (EffectBase effectBase : effectBaseList) {
            effectBase.push(frame, this);
        }

        vaoRendering.draw(this, modelMatrix);

        for (EffectBase effectBase : effectBaseList) {
            effectBase.pop(frame, this);
        }

        effectBaseList = new ArrayList<>();
        modelMatrix = new Matrix4f();

        GL11.glPopMatrix();
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public VBO getVboVertex() {
        return vboVertex;
    }

    public void setVboVertex(VBO vboVertex) {
        this.vboVertex = vboVertex;
    }

    public VBO getVboColor() {
        return vboColor;
    }

    public void setVboColor(VBO vboColor) {
        this.vboColor = vboColor;
    }

    public DrawType getDrawType() {
        return drawType;
    }

    public void setDrawType(DrawType drawType) {
        this.drawType = drawType;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public float getPointSize() {
        return pointSize;
    }

    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
    }

    public List<EffectBase> getEffectBaseList() {
        return effectBaseList;
    }

    public void setEffectBaseList(List<EffectBase> effectBaseList) {
        this.effectBaseList = effectBaseList;
    }

    public VAORendering getVaoRendering() {
        return vaoRendering;
    }

    public void setVaoRendering(VAORendering vaoRendering) {
        this.vaoRendering = vaoRendering;
    }

    public ResourcesLocation getFragmentShader() {
        return fragmentShader;
    }

    public void setFragmentShader(ResourcesLocation fragmentShader) {
        this.fragmentShader = fragmentShader;
    }

    public VAO getVao() {
        return vao;
    }

    public void setVao(VAO vao) {
        this.vao = vao;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    public ResourcesLocation getVertexShader() {
        return vertexShader;
    }

    public void setVertexShader(ResourcesLocation vertexShader) {
        this.vertexShader = vertexShader;
    }

    public VBO getVboTexture() {
        return vboTexture;
    }

    public void setVboTexture(VBO vboTexture) {
        this.vboTexture = vboTexture;
    }

    public int getShaderTarget() {
        return shaderTarget;
    }

    public void setShaderTarget(int shaderTarget) {
        this.shaderTarget = shaderTarget;
    }

    public BlendType getBlendType() {
        return blendType;
    }

    public void setBlendType(BlendType blendType) {
        this.blendType = blendType;
    }
}
