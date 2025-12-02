package me.hannsi.test;

import me.hannsi.lfjg.core.utils.reflection.reference.LongRef;
import me.hannsi.lfjg.core.utils.time.Timer;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.hannsi.lfjg.core.Core.frameBufferSize;
import static me.hannsi.lfjg.core.Core.projection2D;
import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

public class TestNewMeshSystem implements LFJGFrame {
    Timer timer = new Timer();
    List<LongRef> objectIds = new ArrayList<>();
    private Matrix4f modelMatrix;
    private Matrix4f viewMatrix;
    private BlendType blendType;

    public static void main(String[] args) {
        new TestNewMeshSystem().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        int numObjects = 100;
        int numVerticesPerStrip = 100;
        float minX = 0;
        float maxX = 1920;
        float minY = 0;
        float maxY = 1080;
        float minSize = 100.0f;
        float maxSize = 500.0f;

        Random random = new Random();

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

            LongRef id = new LongRef();
            MESH.addObject(
                    id,
                    ProjectionType.ORTHOGRAPHIC_PROJECTION,
                    DrawType.POINTS,
                    30,
                    JointType.MITER,
                    10f,
                    PointType.ROUND,
                    vertices.toArray(new Vertex[0])
            );

            objectIds.add(id);
        }

//        for (int i = 0; i < numObjects; i++) {
//            float x = minX + random.nextFloat() * (maxX - minX);
//            float y = minY + random.nextFloat() * (maxY - minY);
//
//            List<Vertex> vertices = new ArrayList<>();
//
//            for (int j = 0; j < numVerticesPerStrip; j++) {
//                float r = random.nextFloat();
//                float g = random.nextFloat();
//                float b = random.nextFloat();
//
//                Vertex v = new Vertex(x, y, 0, r, g, b, 0.5f, 0, 0, 0, 0, 1);
//                vertices.add(v);
//
//                float angle = (float) (random.nextFloat() * Math.PI * 2.0);
//                float step = minSize + random.nextFloat() * (maxSize - minSize);
//                x += (float) (Math.cos(angle) * step);
//                y += (float) (Math.sin(angle) * step);
//            }
//
//            testMesh.addObject(
//                    ProjectionType.ORTHOGRAPHIC_PROJECTION,
//                    DrawType.LINE_LOOP,
//                    30,
//                    JointType.MITER,
//                    vertices.toArray(new Vertex[0])
//            );
//        }

//        Vertex vertex1 = new Vertex(100, 100, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
//        Vertex vertex2 = new Vertex(300, 500, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
//        Vertex vertex3 = new Vertex(600, 100, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
//        Vertex vertex4 = new Vertex(1500, 500, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
//
//        testMesh.addObject(
//                ProjectionType.ORTHOGRAPHIC_PROJECTION,
//                DrawType.LINE_LOOP,
//                50f,
//                JointType.BEVEL,
//                vertex1, vertex2, vertex3, vertex4
//        );

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
//            testMesh.addObject(
//                    ProjectionType.ORTHOGRAPHIC_PROJECTION,
//                    DrawType.TRIANGLES,
//                    1f,
//                    JointType.MITER,
//                    v1, v2, v3
//            );
//        }

        MESH.initBufferObject();

        modelMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        blendType = BlendType.NORMAL;
    }

    @Override
    public void drawFrame() {
        SHADER_PROGRAM.bind();

        GL_STATE_CACHE.blendFunc(blendType.getSfactor(), blendType.getDfactor());
        GL_STATE_CACHE.setBlendEquation(blendType.getEquation());
        GL_STATE_CACHE.enable(GL_BLEND);
        GL_STATE_CACHE.disable(GL_DEPTH_TEST);

        SHADER_PROGRAM.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.OBJECT.getId());
        SHADER_PROGRAM.setUniform("resolution", UploadUniformType.ON_CHANGE, frameBufferSize);
        SHADER_PROGRAM.updateMatrixUniformBlock(projection2D.getProjMatrix(), viewMatrix, modelMatrix);

        MESH.debugDraw(DrawType.TRIANGLES.getId(), false);

        if (timer.passed(2000)) {
            System.out.println("FPS: " + frame.getFps());
//            mesh.debugLogging(
//                    true,
//                    true,
//                    true,
//                    true,
//                    true,
//                    true
//            );
            timer.reset();
//
//            System.out.println(objectIds.toString());
//
//            long id = objectIds.get((int) (MathHelper.random() * objectIds.size())).getValue();
//            mesh.deleteObject(objectIds, id);
        }
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.V_SYNC_OFF);
        frame.setFrameSettingValue(CheckSeveritiesSetting.class, new SeverityType[]{SeverityType.LOW, SeverityType.MEDIUM, SeverityType.HIGH});
    }

    public void setFrame() {
        frame = new Frame(this, "TestNewMeshSystem");
    }
}
