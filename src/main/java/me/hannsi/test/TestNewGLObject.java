package me.hannsi.test;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.render.Vertex;
import me.hannsi.lfjg.testRender.RenderSystemSetting;
import me.hannsi.lfjg.testRender.renderers.JointType;
import me.hannsi.lfjg.testRender.renderers.polygon.GLRoundedRect;
import me.hannsi.lfjg.testRender.system.mesh.Corner;
import org.lwjgl.opengl.GL11;

import java.util.Random;

import static me.hannsi.lfjg.core.CoreSystemSetting.SHARE_OBJECT;
import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;
import static me.hannsi.lfjg.testRender.LFJGRenderContext.glStateCache;
import static me.hannsi.lfjg.testRender.LFJGRenderContext.update;
import static org.lwjgl.opengl.GL11.*;

public class TestNewGLObject implements LFJGFrame {
    public static void main(String[] args) {
        new TestNewGLObject().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        RenderSystemSetting.VAO_RENDERING_FRONT_AND_BACK = false;

        Random random = new Random();
        int screenWidth = frame.getWindowWidth();
        int screenHeight = frame.getWindowHeight();
        int margin = 200;

        Vertex v1 = new Vertex(random.nextInt(screenWidth - margin * 2) + margin, random.nextInt(screenHeight - margin * 2) + margin, Color.RED.setAlpha(50));
        Vertex v2 = new Vertex(random.nextInt(screenWidth - margin * 2) + margin, random.nextInt(screenHeight - margin * 2) + margin, Color.GREEN.setAlpha(50));
        Vertex v3 = new Vertex(random.nextInt(screenWidth - margin * 2) + margin, random.nextInt(screenHeight - margin * 2) + margin, Color.BLUE.setAlpha(50));
        Vertex v4 = new Vertex(random.nextInt(screenWidth - margin * 2) + margin, random.nextInt(screenHeight - margin * 2) + margin, Color.WHITE.setAlpha(50));

        System.out.println("GLLineLoop v1: (" + v1.x + ", " + v1.y + ")");
        System.out.println("GLLineLoop v2: (" + v2.x + ", " + v2.y + ")");
        System.out.println("GLLineLoop v3: (" + v3.x + ", " + v3.y + ")");
        System.out.println("GLLineLoop v4: (" + v4.x + ", " + v4.y + ")");

//        GLBezierLine.createGLBezierLine("GLBezierLine")
//                .addControlPoint(new Vertex(100, 100))
//                .addControlPoint(new Vertex(200, 2000, Color.YELLOW))
//                .addControlPoint(new Vertex(1000, 0, Color.WHITE))
//                .addControlPoint(new Vertex(1000, 1000, Color.BLUE))
//                .end()
//                .segment()
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(10f);

//        GLRoundedRect.createRoundedRect("GLRoundedRect")
//                .from(new Vertex(100, 100), Corner.createCircle(20f))
//                .to(new Vertex(200, 200), Corner.createBevel(20f))
//                .from(new Vertex(1000, 1000), Corner.createCircle(20f))
//                .end(new Vertex(1100, 1100), Corner.createBevel(20f))
//                .fill();

        GLRoundedRect.createRoundedRect("GLRoundedRect")
                .from(new Vertex(100, 100), Corner.createCircle(20f))
                .to(new Vertex(200, 200), Corner.createBevel(20f))
                .from(new Vertex(200, 200), Corner.createBevel(20f))
                .end(new Vertex(1100, 1100), Corner.createBevel(20f))
                .stroke()
                .strokeJointType(JointType.ROUND)
                .strokeWidth(10f);

//        GLLineLoop.createGLLineLoop("GLLineLoop")
//                .vertex1(v1)
//                .vertex2(v2)
//                .vertex2(v3)
//                .vertex2_end(v4)
//                .strokeJointType(JointType.MITER)
//                .strokeWidth(100f);

//        GLLineStrip.createGLLineStrip("GLLineStrip")
//                .vertex1(v1)
//                .vertex2(v2)
//                .vertex2(v3)
//                .vertex2_end(v4)
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(20f);

//        GLLines.createGLLines("GLLines")
//                .vertex1(new Vertex(500 + 400, 500, Color.RED.setAlpha(50)))
//                .vertex2(new Vertex(1000 + 400, 500, Color.GREEN.setAlpha(50)))
//                .vertex1(new Vertex(700 + 400, 700, Color.BLUE.setAlpha(50)))
//                .vertex2_end(new Vertex(200 + 400, 400, Color.WHITE.setAlpha(50)))
//                .strokeJointType(JointType.ROUND_START_END)
//                .strokeWidth(100f);

//        GLLineStrip.createGLLineStrip("GLLineStrip")
//                .vertex1(new Vertex(500 + 400, 500, Color.RED.setAlpha(50)))
//                .vertex2(new Vertex(1000 + 400, 500, Color.GREEN.setAlpha(50)))
//                .vertex2(new Vertex(700 + 400, 700, Color.BLUE.setAlpha(50)))
//                .vertex2_end(new Vertex(200 + 400, 400, Color.WHITE.setAlpha(50)))
//                .strokeJointType(JointType.ROUND_START_END)
//                .strokeWidth(100f);

//        GLLineLoop.createGLLineLoop("GLLineLoop")
//                .addVertex(new Vertex(500 + 400, 500, Color.RED.setAlpha(50)))
//                .addVertex(new Vertex(1000 + 400, 500, Color.GREEN.setAlpha(50)))
//                .addVertex(new Vertex(700 + 400, 700, Color.BLUE.setAlpha(50)))
//                .addVertex(new Vertex(200 + 400, 400, Color.WHITE.setAlpha(50)))
//                .end()
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(100f);

//        GLPoint.createGLPoint("GLPoint")
//                .vertex_end(new Vertex(500, 500, 0, Color.RED))
//                .pointType(PointType.ROUND)
//                .pointSize(100f)
//                .fill();

//        GLPoint.createGLPoint("GLPoint")
//                .vertex_end(new Vertex(500, 500, 0, Color.RED))
//                .pointType(PointType.ROUND)
//                .pointSize(500f)
//                .stroke()
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(100f);

//        GLCircle.createGLCircle("GLCircle")
//                .vertexCenter(new Vertex(500, 500, Color.of(255, 255, 255, 255)))
//                .circleData(400, 400)
////                .circleData_innerRadius(400, 400, 200, 200)
//                .color(Color.BLUE)
//                .color_end_easingColor(Color.GREEN)
//                .easingColor(Easing.easeOutCubic)
//                .fill();

//        GLCircle.createGLCircle("GLCircle")
//                .vertexCenter(new Vertex(500, 500, Color.of(255, 255, 255, 255)))
//                .circleData(400, 400)
////                .circleData_innerRadius(400, 400, 200, 200)
//                .color(Color.BLUE)
//                .color_end_easingColor(Color.GREEN)
//                .easingColor(Easing.easeOutCubic)
//                .stroke()
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(10f);

//        GLTriangleFan.createGLTriangleFan("GLTriangleFan")
//                .vertex1(new Vertex(500, 500))
//                .vertex2(new Vertex(1000, 500))
//                .vertex3(new Vertex(750, 750))
//                .vertex3_end(new Vertex(1000, 1200))
//                .fill();

//        GLTriangleFan.createGLTriangleFan("GLTriangleFan")
//                .vertex1(new Vertex(500, 500))
//                .vertex2(new Vertex(1000, 500))
//                .vertex3(new Vertex(750, 750))
//                .vertex3_end(new Vertex(1000, 1200))
//                .stroke()
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(10f);

//        GLTriangleStrip.createGLTriangleStrip("GLTriangleStrip")
//                .vertex1(new Vertex(500, 500))
//                .vertex2(new Vertex(1000, 500))
//                .vertex3(new Vertex(750, 750))
//                .vertex3_end(new Vertex(1000, 1200))
//                .fill();

//        GLTriangleStrip.createGLTriangleStrip("GLTriangleStrip")
//                .vertex1(new Vertex(500, 500))
//                .vertex2(new Vertex(1000, 500))
//                .vertex3(new Vertex(750, 750))
//                .vertex3_end(new Vertex(1000, 1200))
//                .stroke()
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(10f);

//        GLTriangle.createGLTriangle("GLTriangle")
//                .vertex1(new Vertex(500, 500))
//                .vertex2(new Vertex(1000, 500))
//                .vertex3_end(new Vertex(750, 750))
//                .fill();

//        GLTriangle.createGLTriangle("GLTriangle")
//                .vertex1(new Vertex(500, 500))
//                .vertex2(new Vertex(1000, 500))
//                .vertex3_end(new Vertex(750, 750))
//                .stroke()
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(10f);

//        GLRect.createGLRect("GLRect1")
//                .vertex1_2p(new Vertex(500, 500, 0))
//                .vertex2_2p_end(new Vertex(1000, 1000, 0))
//                .stroke()
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(10f);

//        GLRect.createGLRect("GLRect1")
//                .vertex1_2p(new Vertex(500, 500, 0))
//                .vertex2_2p_end(new Vertex(1000, 1000, 0))
//                .fill();

//        GLPolygon.createGLPolygon("GLPolygon2")
//                .vertex(new Vertex(500, 500, 0, 1f, 1f, 0f, 1f))
//                .vertex(new Vertex(500, 1000, 0, 1f, 1f, 0f, 1f))
//                .vertex(new Vertex(1000, 1000, 0, 1f, 1f, 0f, 1f))
//                .vertex_end(new Vertex(1000, 500, 0, 1f, 1f, 0f, 1f))
//                .fill();

//        GLRoundedRect.createRoundedRect("GLRoundedRect1")
//                .vertex1_2p(new Vertex(500, 500, 0, 1f, 0f, 0f, 0.5f), Corner.createCircle(40f))
//                .vertex2_2p_end(new Vertex(1000, 1000, 0, 1f, 0f, 0f, 0.5f), Corner.createCircle(20f))
//                .fill();

//        GLRoundedRect.createRoundedRect("GLRoundedRect1")
//                .vertex1(new Vertex(600, 500, 0, 1f, 0f, 0f, 0.5f), Corner.createConcave(40f, 80f))
//                .vertex2(new Vertex(1000, 500, 0, 1f, 0f, 0f, 0.5f), Corner.createInset(50f, 20f))
//                .vertex3(new Vertex(1000, 1000, 0, 1f, 0f, 0f, 0.5f), Corner.createChamfer(20f, 50f))
//                .vertex4_end(new Vertex(500, 1000, 0, 1f, 0f, 0f, 0.5f), Corner.createNone())
//                .stroke()
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(10f);

//        GLPolygon.createGLPolygon("GLPolygon1")
//                .vertex(new Vertex(500, 500, 0))
//                .vertex(new Vertex(500, 1500, 0))
//                .vertex(new Vertex(1500, 1500, 0))
//                .vertex_end(new Vertex(1500, 500, 0))
//                .fill();

//        GLPolygon.createGLPolygon("GLPolygon1")
//                .vertex(new Vertex(0, 0, 0))
//                .vertex(new Vertex(100, 0, 0))
//                .vertex(new Vertex(230, 500, 0))
//                .vertex(new Vertex(500, 500, 0))
//                .vertex(new Vertex(230, 1000, 0))
//                .vertex(new Vertex(100, 1400, 0))
//                .vertex_end(new Vertex(230, 700, 0))
//                .stroke()
//                .strokeJointType(JointType.ROUND)
//                .strokeWidth(10f);

//        RandomPolygonGenerator.generate(RandomPolygonGenerator.Shape.CONCAVE, 10, 20, 100, 1920, 100, 1080, new Random(), GLPolygon.createGLPolygon("GLPolygon1"))
//                .fill();
    }

    @Override
    public void drawFrame() {
        update();

        int width = frame.getWindowWidth();
        int height = frame.getWindowHeight();

        glStateCache.useProgram(0);

        GL11.glViewport(0, 0, width, height);

        GL11.glMatrixMode(GL_PROJECTION);
        GL11.glLoadIdentity();

        if (width > 0 && height > 0) {
            GL11.glOrtho(
                    0, width,
                    0, height,
                    -1, 1
            );
        }

        GL11.glMatrixMode(GL_MODELVIEW);
        GL11.glLoadIdentity();

        GL11.glColor3f(1, 1, 1);
        GL11.glPointSize(10f);
        GL11.glBegin(GL_LINE_LOOP);

        int count = 0;
        for (Object o : SHARE_OBJECT) {
            if (!(o instanceof Vertex)) {
                return;
            }

            if (count < SHARE_OBJECT.size()) {
                GL11.glColor4f(((Vertex) o).red, ((Vertex) o).green, ((Vertex) o).blue, ((Vertex) o).alpha);
                GL11.glVertex3f(((Vertex) o).x, ((Vertex) o).y, ((Vertex) o).z);
            }

            count++;
        }

        GL11.glEnd();
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
