package me.hannsi.test;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.frame.setting.settings.RefreshRateSetting;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.system.mesh.TestMesh;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class TestNewMeshSystem implements LFJGFrame {
    private TestMesh testMesh;
    private Matrix4f modelMatrix;
    private Matrix4f viewMatrix;
    private BlendType blendType;

    public static void main(String[] args) {
        new TestNewMeshSystem().setFrame();
    }

    @Override
    public void init() {
        LFJGContext.frame.updateLFJGLContext();

        int numObjects = 1000000;
        float minX = 0;
        float maxX = 1920;
        float minY = 0;
        float maxY = 1080;
        float minSize = 10.0f;
        float maxSize = 20.0f;

        Random random = new Random();

        testMesh = TestMesh.createMesh(
                (numObjects * 3 * 12),
                (numObjects * 3),
                numObjects
        );


        for (int i = 0; i < numObjects; i++) {
            float centerX = minX + random.nextFloat() * (maxX - minX);
            float centerY = minY + random.nextFloat() * (maxY - minY);
            float size = minSize + random.nextFloat() * (maxSize - minSize);

            float r = random.nextFloat();
            float g = random.nextFloat();
            float b = random.nextFloat();

            Vertex v1 = new Vertex(
                    centerX - size / 2, centerY - size / 2, 0f,
                    r, g, b, 1f,
                    0f, 0f,
                    0f, 0f, 1f
            );
            Vertex v2 = new Vertex(
                    centerX + size / 2, centerY - size / 2, 0f,
                    r, g, b, 1f,
                    1f, 0f,
                    0f, 0f, 1f
            );
            Vertex v3 = new Vertex(
                    centerX, centerY + size / 2, 0f,
                    r, g, b, 1f,
                    0.5f, 1f,
                    0f, 0f, 1f
            );

            testMesh.addObject(ProjectionType.PERSPECTIVE_PROJECTION, DrawType.TRIANGLES, v1, v2, v3);
        }

        testMesh.initBufferObject();

        modelMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        blendType = BlendType.NORMAL;
    }

    @Override
    public void drawFrame() {
        LFJGRenderContext.shaderProgram.bind();

        GLStateCache.blendFunc(blendType.getSfactor(), blendType.getDfactor());
        GLStateCache.setBlendEquation(blendType.getEquation());
        GLStateCache.enable(GL11.GL_BLEND);
        GLStateCache.disable(GL11.GL_DEPTH_TEST);

        LFJGRenderContext.shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.OBJECT.getId());
        LFJGRenderContext.shaderProgram.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, Core.projection2D.getProjMatrix());
        LFJGRenderContext.shaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, modelMatrix);
        LFJGRenderContext.shaderProgram.setUniform("viewMatrix", UploadUniformType.PER_FRAME, viewMatrix);
        LFJGRenderContext.shaderProgram.setUniform("resolution", UploadUniformType.ON_CHANGE, Core.frameBufferSize);

        testMesh.debugDraw(DrawType.TRIANGLES.getId());

        System.out.println(LFJGContext.frame.getFps());
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {
        LFJGContext.frame.setFrameSettingValue(RefreshRateSetting.class, -1);
    }

    public void setFrame() {
        LFJGContext.frame = new Frame(this, "TestNewMeshSystem");
    }
}
