package me.hannsi.test;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.time.Timer;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.frame.setting.settings.RefreshRateSetting;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.system.mesh.TestMesh;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestNewMeshSystem implements LFJGFrame {
    Timer timer = new Timer();
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

        int numObjects = 10;
        int numVerticesPerStrip = 10;
        float minX = 0;
        float maxX = 1920;
        float minY = 0;
        float maxY = 1080;
        float minSize = 100.0f;
        float maxSize = 500.0f;

        Random random = new Random();

        testMesh = TestMesh.createMesh(
                (numObjects * numVerticesPerStrip * 5 * 12),
                (numObjects * numVerticesPerStrip * 100),
                numObjects
        );

        for (int i = 0; i < numObjects; i++) {
            float x = minX + random.nextFloat() * (maxX - minX);
            float y = minY + random.nextFloat() * (maxY - minY);

            List<Vertex> vertices = new ArrayList<>();

            for (int j = 0; j < numVerticesPerStrip; j++) {
                float r = random.nextFloat();
                float g = random.nextFloat();
                float b = random.nextFloat();

                Vertex v = new Vertex(x, y, 0, r, g, b, 0.5f, 0, 0, 0, 0, 1);
                vertices.add(v);

                float angle = (float) (random.nextFloat() * Math.PI * 2.0);
                float step = minSize + random.nextFloat() * (maxSize - minSize);
                x += (float) (Math.cos(angle) * step);
                y += (float) (Math.sin(angle) * step);
            }

            testMesh.addObject(
                    ProjectionType.ORTHOGRAPHIC_PROJECTION,
                    DrawType.LINE_STRIP,
                    10f,
                    JointType.MITER,
                    vertices.toArray(new Vertex[0])
            );
        }

        Vertex vertex1 = new Vertex(100, 100, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
        Vertex vertex2 = new Vertex(300, 500, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
//        Vertex vertex3 = new Vertex(600, 100, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
//        Vertex vertex4 = new Vertex(1500, 500, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);

        testMesh.addObject(
                ProjectionType.ORTHOGRAPHIC_PROJECTION,
                DrawType.LINE_STRIP,
                50f,
                JointType.BEVEL,
                vertex1, vertex2
        );

//        for (int i = 0; i < numObjects; i++) {
//            float x1 = minX + random.nextFloat() * (maxX - minX);
//            float y1 = minY + random.nextFloat() * (maxY - minY);
//
//            float angle = (float) (random.nextFloat() * Math.PI * 2.0);
//            float length = minSize + random.nextFloat() * (maxSize - minSize);
//
//            float x2 = x1 + (float) Math.cos(angle) * length;
//            float y2 = y1 + (float) Math.sin(angle) * length;
//
//            float r = random.nextFloat();
//            float g = random.nextFloat();
//            float b = random.nextFloat();
//
//            Vertex v1 = new Vertex(x1, y1, 0, r, g, b, 1, 0, 0, 0, 0, 1);
//            Vertex v2 = new Vertex(x2, y2, 0, r, g, b, 1, 0, 0, 0, 0, 1);
//
//            testMesh.addObject(
//                    ProjectionType.ORTHOGRAPHIC_PROJECTION,
//                    DrawType.LINES,
//                    1f,
//                    JointType.MITER,
//                    v1, v2
//            );
//        }

//        for (int i = 0; i < numObjects; i++) {
//            float centerX = minX + random.nextFloat() * (maxX - minX);
//            float centerY = minY + random.nextFloat() * (maxY - minY);
//            float size = minSize + random.nextFloat() * (maxSize - minSize);
//
//            float r = random.nextFloat();
//            float g = random.nextFloat();
//            float b = random.nextFloat();
//
//            Vertex v1 = new Vertex(
//                    centerX - size / 2, centerY - size / 2, 0f,
//                    r, g, b, 1f,
//                    0f, 0f,
//                    0f, 0f, 1f
//            );
//            Vertex v2 = new Vertex(
//                    centerX + size / 2, centerY - size / 2, 0f,
//                    r, g, b, 1f,
//                    1f, 0f,
//                    0f, 0f, 1f
//            );
//            Vertex v3 = new Vertex(
//                    centerX, centerY + size / 2, 0f,
//                    r, g, b, 1f,
//                    0.5f, 1f,
//                    0f, 0f, 1f
//            );
//
//            testMesh.addObject(ProjectionType.ORTHOGRAPHIC_PROJECTION, DrawType.TRIANGLES, v1, v2, v3);
//        }

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

        if (timer.passed(2000)) {
            System.out.println(LFJGContext.frame.getFps());
            timer.reset();
        }
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
