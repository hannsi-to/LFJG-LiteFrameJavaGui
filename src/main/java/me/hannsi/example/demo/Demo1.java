package me.hannsi.example.demo;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.type.types.Theme;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.renderers.polygon.*;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import org.joml.Vector2f;

public class Demo1 implements IScene {
    Frame frame;
    Scene scene;

    float offset = 80;
    float step = 20;

    GLRect glBackGround;
    GLPoint glPoint1;
    GLPoint glPoint2;
    GLPoint glPoint3;
    GLLine glLine1;
    GLLine glLine2;
    GLLine glLine3;
    GLPolygon glPolygon1;
    GLPolygon glPolygon2;
    GLPolygon glPolygon3;
    GLBezierLine glBezierLine1;
    GLBezierLine glBezierLine2;
    GLBezierLine glBezierLine3;
    GLRect glRect1;
    GLRoundedRect glRoundedRect1;
    GLRoundedRect glRoundedRect2;
    GLRoundedRect glRoundedRect3;
    GLRoundedRect glRoundedRect4;
    GLRoundedRect glRoundedRect5;
    GLCircle glCircle1;
    GLCircle glCircle2;
    GLRect glRect11;
    GLRoundedRect glRoundedRect11;
    GLRoundedRect glRoundedRect12;
    GLRoundedRect glRoundedRect13;
    GLRoundedRect glRoundedRect14;
    GLRoundedRect glRoundedRect15;
    GLCircle glCircle11;
    GLCircle glCircle12;
    GLRect glRect21;
    GLRoundedRect glRoundedRect21;
    GLRoundedRect glRoundedRect22;
    GLRoundedRect glRoundedRect23;
    GLRoundedRect glRoundedRect24;
    GLRoundedRect glRoundedRect25;
    GLCircle glCircle21;
    GLCircle glCircle22;
    GLPolygon glPolygon4;
    GLPolygon glPolygon5;
    GLPolygon glPolygon6;
    GLPolygon glPolygon7;
    GLPolygon glPolygon8;
    GLPolygon glPolygon9;
    GLTriangle glTriangle1;
    GLTriangle glTriangle2;
    GLTriangle glTriangle3;
    GLRect glRect31;
    GLRoundedRect glRoundedRect31;
    GLRoundedRect glRoundedRect32;
    GLRoundedRect glRoundedRect33;
    GLRoundedRect glRoundedRect34;
    GLRoundedRect glRoundedRect35;
    GLCircle glCircle31;
    GLCircle glCircle32;
    GLCircle glCircle13;
    GLCircle glCircle14;
    GLCircle glCircle15;
    GLCircle glCircle16;
    GLCircle glCircle17;
    GLCircle glCircle18;
    GLCircle glCircle19;
    GLCircle glCircle20;
    GLBezierLine glBezierLine4;
    GLBezierLine glBezierLine5;
    GLBezierLine glBezierLine6;

    public Demo1(Frame frame) {
        this.frame = frame;
        this.scene = new Scene("Demo1", this);
    }

    @Override
    public void init() {
        glBackGround = GLRect.createGLRect("GLBackGround")
                .x1_y1_color1_2p(0, 0, Color.WHITE)
                .x3_y3_color3_2p(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), Color.WHITE)
                .fill()
                .update();

        float offsetX = 20;
        float offsetY = 80;
        glPoint1 = GLPoint.createGLPoint("GLPoint1")
                .x_y_color(offsetX + offset / 2f, offsetY + offset / 2f, Color.BLACK)
                .pointSize(5)
                .update();
        offsetX += offset + step;
        glPoint2 = GLPoint.createGLPoint("GLPoint2")
                .x_y_color(offsetX + offset / 2f, offsetY + offset / 2f, Color.BLACK)
                .pointSize(10)
                .update();
        offsetX += offset + step;
        glPoint3 = GLPoint.createGLPoint("GLPoint3")
                .x_y_color(offsetX + offset / 2f, offsetY + offset / 2f, Color.BLACK)
                .pointSize(15)
                .update();
        offsetX += offset + step;
        glLine1 = GLLine.createGLLine("GLLine1")
                .x1_y1_color1(offsetX, offsetY, Color.BLACK)
                .x2_y2_color2(offsetX + offset, offsetY + offset, Color.BLACK)
                .lineWidth(2)
                .update();
        offsetX += offset + step;
        glLine2 = GLLine.createGLLine("GLLine2")
                .x1_y1_color1(offsetX, offsetY, Color.BLACK)
                .x2_y2_color2(offsetX + offset, offsetY + offset, Color.BLACK)
                .lineWidth(4)
                .update();
        offsetX += offset + step;
        glLine3 = GLLine.createGLLine("GLLine3")
                .x1_y1_color1(offsetX, offsetY, Color.BLACK)
                .x2_y2_color2(offsetX + offset, offsetY + offset, Color.BLACK)
                .lineWidth(6)
                .update();
        offsetX += offset + step;
        glPolygon1 = new GLPolygon("GLPolygon1");
        glPolygon1.put().vertex(new Vector2f(offsetX, offsetY)).color(Color.BLACK);
        glPolygon1.put().vertex(new Vector2f(offsetX + offset / 3, offsetY + offset)).color(Color.BLACK);
        glPolygon1.put().vertex(new Vector2f(offsetX + (offset / 3) * 2, offsetY)).color(Color.BLACK);
        glPolygon1.put().vertex(new Vector2f(offsetX + offset, offsetY + offset)).color(Color.BLACK);
        glPolygon1.setDrawType(DrawType.LINE_STRIP).setLineWidth(2);
        glPolygon1.rendering();
        offsetX += offset + step;
        glPolygon2 = new GLPolygon("GLPolygon2");
        glPolygon2.put().vertex(new Vector2f(offsetX, offsetY)).color(Color.BLACK);
        glPolygon2.put().vertex(new Vector2f(offsetX + offset / 3, offsetY + offset)).color(Color.BLACK);
        glPolygon2.put().vertex(new Vector2f(offsetX + (offset / 3) * 2, offsetY)).color(Color.BLACK);
        glPolygon2.put().vertex(new Vector2f(offsetX + offset, offsetY + offset)).color(Color.BLACK);
        glPolygon2.setDrawType(DrawType.LINE_STRIP).setLineWidth(4);
        glPolygon2.rendering();
        offsetX += offset + step;
        glPolygon3 = new GLPolygon("GLPolygon3");
        glPolygon3.put().vertex(new Vector2f(offsetX, offsetY)).color(Color.BLACK);
        glPolygon3.put().vertex(new Vector2f(offsetX + offset / 3, offsetY + offset)).color(Color.BLACK);
        glPolygon3.put().vertex(new Vector2f(offsetX + (offset / 3) * 2, offsetY)).color(Color.BLACK);
        glPolygon3.put().vertex(new Vector2f(offsetX + offset, offsetY + offset)).color(Color.BLACK);
        glPolygon3.setDrawType(DrawType.LINE_STRIP).setLineWidth(6);
        glPolygon3.rendering();
        offsetX += offset + step;
        glBezierLine1 = new GLBezierLine("GLBezierLine1");
        glBezierLine1.bezierLine(
                new GLBezierLine.BezierPoint[]{
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX, offsetY), Color.BLACK),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + offset / 3, offsetY + offset), Color.BLACK),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + (offset / 3) * 2, offsetY), Color.BLACK),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + offset, offsetY + offset), Color.BLACK)
                }, 2, (int) offset
        );
        offsetX += offset + step;
        glBezierLine2 = new GLBezierLine("GLBezierLine2");
        glBezierLine2.bezierLine(
                new GLBezierLine.BezierPoint[]{
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX, offsetY), Color.BLACK),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + offset / 3, offsetY + offset), Color.BLACK),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + (offset / 3) * 2, offsetY), Color.BLACK),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + offset, offsetY + offset), Color.BLACK)
                }, 4, (int) offset
        );
        offsetX += offset + step;
        glBezierLine3 = new GLBezierLine("GLBezierLine3");
        glBezierLine3.bezierLine(
                new GLBezierLine.BezierPoint[]{
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX, offsetY), Color.BLACK),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + offset / 3, offsetY + offset), Color.BLACK),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + (offset / 3) * 2, offsetY), Color.BLACK),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + offset, offsetY + offset), Color.BLACK)
                }, 6, (int) offset
        );
        offsetX = 20;
        offsetY += offset + step;
        Color color = Color.GREEN;
        glRect1 = GLRect.createGLRect("GLRect1")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect1 = GLRoundedRect.createRoundedRect("GLRoundedRect1")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1_2p(true, 15f)
                .toggleRadius3_radius3_2p(true, 15f)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect2 = GLRoundedRect.createRoundedRect("GLRoundedRect2")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, 20f)
                .toggleRadius2_radius2(false, 0f)
                .toggleRadius3_radius3(true, 20f)
                .toggleRadius4_radius4(false, 0f)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect3 = GLRoundedRect.createRoundedRect("GLRoundedRect3")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(false, 0f)
                .toggleRadius2_radius2(true, 20f)
                .toggleRadius3_radius3(true, 20f)
                .toggleRadius4_radius4(true, 20f)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect5 = GLRoundedRect.createRoundedRect("GLRoundedRect5")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, 20f)
                .toggleRadius2_radius2(true, 20f)
                .toggleRadius3_radius3(false, 0f)
                .toggleRadius4_radius4(true, 20f)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect4 = GLRoundedRect.createRoundedRect("GLRoundedRect4")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, offset / 2f)
                .toggleRadius2_radius2(true, offset / 2f)
                .toggleRadius3_radius3(true, offset / 2f)
                .toggleRadius4_radius4(true, offset / 2f)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glCircle1 = GLCircle.createGLCirce("GLCircle1")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(360)
                .colors(color)
                .fill()
                .update();
        offsetX += offset * 1.3f + step;
        glCircle2 = GLCircle.createGLCirce("GLCircle2")
                .cx_xRadius(offsetX + offset / 2f, offset * 1.5f / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(360)
                .colors(color)
                .fill()
                .update();
        offsetX = 20;
        offsetY += offset + step;
        color = Color.ORANGE;
        glRect11 = GLRect.createGLRect("GLRect11")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .outLine().lineWidth(6)
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect11 = GLRoundedRect.createRoundedRect("GLRoundedRect11")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1_2p(true, 15f)
                .toggleRadius3_radius3_2p(true, 15f)
                .outLine().lineWidth(6)
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect12 = GLRoundedRect.createRoundedRect("GLRoundedRect12")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, 20f)
                .toggleRadius2_radius2(false, 0f)
                .toggleRadius3_radius3(true, 20f)
                .toggleRadius4_radius4(false, 0f)
                .outLine().lineWidth(6)
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect13 = GLRoundedRect.createRoundedRect("GLRoundedRect13")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(false, 0f)
                .toggleRadius2_radius2(true, 20f)
                .toggleRadius3_radius3(true, 20f)
                .toggleRadius4_radius4(true, 20f)
                .outLine().lineWidth(6)
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect15 = GLRoundedRect.createRoundedRect("GLRoundedRect15")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, 20f)
                .toggleRadius2_radius2(true, 20f)
                .toggleRadius3_radius3(false, 0f)
                .toggleRadius4_radius4(true, 20f)
                .outLine().lineWidth(6)
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect14 = GLRoundedRect.createRoundedRect("GLRoundedRect14")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, offset / 2f)
                .toggleRadius2_radius2(true, offset / 2f)
                .toggleRadius3_radius3(true, offset / 2f)
                .toggleRadius4_radius4(true, offset / 2f)
                .outLine().lineWidth(6)
                .update();
        offsetX += offset * 1.5f + step;
        glCircle11 = GLCircle.createGLCirce("GLCircle11")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(360)
                .colors(color)
                .outLine().lineWidth(6)
                .update();
        offsetX += offset * 1.3f + step;
        glCircle12 = GLCircle.createGLCirce("GLCircle12")
                .cx_xRadius(offsetX + offset / 2f, offset * 1.5f / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(360)
                .colors(color)
                .outLine().lineWidth(6)
                .update();
        offsetX = 20;
        offsetY += offset + step;
        color = Color.PINK;
        glRect21 = GLRect.createGLRect("GLRect21")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .outLine().lineWidth(2)
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect21 = GLRoundedRect.createRoundedRect("GLRoundedRect21")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1_2p(true, 15f)
                .toggleRadius3_radius3_2p(true, 15f)
                .outLine().lineWidth(2)
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect22 = GLRoundedRect.createRoundedRect("GLRoundedRect22")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, 20f)
                .toggleRadius2_radius2(false, 0f)
                .toggleRadius3_radius3(true, 20f)
                .toggleRadius4_radius4(false, 0f)
                .outLine().lineWidth(2)
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect23 = GLRoundedRect.createRoundedRect("GLRoundedRect23")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(false, 0f)
                .toggleRadius2_radius2(true, 20f)
                .toggleRadius3_radius3(true, 20f)
                .toggleRadius4_radius4(true, 20f)
                .outLine().lineWidth(2)
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect25 = GLRoundedRect.createRoundedRect("GLRoundedRect25")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, 20f)
                .toggleRadius2_radius2(true, 20f)
                .toggleRadius3_radius3(false, 0f)
                .toggleRadius4_radius4(true, 20f)
                .outLine().lineWidth(2)
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect24 = GLRoundedRect.createRoundedRect("GLRoundedRect24")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, offset / 2f)
                .toggleRadius2_radius2(true, offset / 2f)
                .toggleRadius3_radius3(true, offset / 2f)
                .toggleRadius4_radius4(true, offset / 2f)
                .outLine().lineWidth(2)
                .update();
        offsetX += offset * 1.5f + step;
        glCircle21 = GLCircle.createGLCirce("GLCircle21")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(360)
                .colors(color)
                .outLine().lineWidth(2)
                .update();
        offsetX += offset * 1.3f + step;
        glCircle22 = GLCircle.createGLCirce("GLCircle22")
                .cx_xRadius(offsetX + offset / 2f, offset * 1.5f / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(360)
                .colors(color)
                .outLine().lineWidth(2)
                .update();
        offsetX = 20;
        offsetY += offset + step;
        glPolygon4 = new GLPolygon("GLPolygon4");
        float centerX = offsetX + offset / 2;
        float centerY = offsetY + offset / 2;
        float radius = offset / 2;
        for (int i = 0; i < 6; i++) {
            float angle = (float) (Math.PI / 3 * i);
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);
            glPolygon4.put().vertex(new Vector2f(x, y)).color(Color.SILVER);
        }
        glPolygon4.setDrawType(DrawType.POLYGON);
        glPolygon4.rendering();
        offsetX += offset + step;
        glPolygon5 = new GLPolygon("GLPolygon5");
        centerX = offsetX + offset / 2;
        centerY = offsetY + offset / 2;
        radius = offset / 2;
        for (int i = 0; i < 6; i++) {
            float angle = (float) (Math.PI / 3 * i);
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);
            glPolygon5.put().vertex(new Vector2f(x, y)).color(Color.SILVER);
        }
        glPolygon5.setDrawType(DrawType.LINE_LOOP).setLineWidth(6);
        glPolygon5.rendering();
        offsetX += offset + step;
        glPolygon6 = new GLPolygon("GLPolygon5");
        centerX = offsetX + offset / 2;
        centerY = offsetY + offset / 2;
        radius = offset / 2;
        for (int i = 0; i < 6; i++) {
            float angle = (float) (Math.PI / 3 * i);
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);
            glPolygon6.put().vertex(new Vector2f(x, y)).color(Color.SILVER);
        }
        glPolygon6.setDrawType(DrawType.LINE_LOOP).setLineWidth(2);
        glPolygon6.rendering();
        offsetX += offset + step;
        glPolygon7 = new GLPolygon("GLPolygon7");
        centerX = offsetX + offset / 2;
        centerY = offsetY + offset / 2;
        radius = offset / 2;
        for (int i = 0; i < 6; i++) {
            float angle = MathHelper.toRadians(72 * i - 90);
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);
            glPolygon7.put().vertex(new Vector2f(x, y)).color(Color.SILVER);
        }
        glPolygon7.setDrawType(DrawType.POLYGON);
        glPolygon7.rendering();
        offsetX += offset + step;
        glPolygon8 = new GLPolygon("GLPolygon8");
        centerX = offsetX + offset / 2;
        centerY = offsetY + offset / 2;
        radius = offset / 2;
        for (int i = 0; i < 6; i++) {
            float angle = MathHelper.toRadians(72 * i - 90);
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);
            glPolygon8.put().vertex(new Vector2f(x, y)).color(Color.SILVER);
        }
        glPolygon8.setDrawType(DrawType.LINE_LOOP).setLineWidth(6);
        glPolygon8.rendering();
        offsetX += offset + step;
        glPolygon9 = new GLPolygon("GLPolygon9");
        centerX = offsetX + offset / 2;
        centerY = offsetY + offset / 2;
        radius = offset / 2;
        for (int i = 0; i < 6; i++) {
            float angle = MathHelper.toRadians(72 * i - 90);
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);
            glPolygon9.put().vertex(new Vector2f(x, y)).color(Color.SILVER);
        }
        glPolygon9.setDrawType(DrawType.LINE_LOOP).setLineWidth(2);
        glPolygon9.rendering();
        offsetX += offset + step;
        glTriangle1 = GLTriangle.createGLTriangle("GLTriangle1")
                .x1_y1_color1(offsetX, offsetY, Color.SILVER)
                .x2_y2_color2(offsetX + offset, offsetY, Color.SILVER)
                .x3_y3_color3(offsetX + offset / 2, offsetY + offset, Color.SILVER)
                .fill()
                .update();
        offsetX += offset + step;
        glTriangle2 = GLTriangle.createGLTriangle("GLTriangle2")
                .x1_y1_color1(offsetX, offsetY, Color.SILVER)
                .x2_y2_color2(offsetX + offset, offsetY, Color.SILVER)
                .x3_y3_color3(offsetX + offset / 2, offsetY + offset, Color.SILVER)
                .outLine().lineWidth(6f)
                .update();
        offsetX += offset + step;
        glTriangle3 = GLTriangle.createGLTriangle("GLTriangle3")
                .x1_y1_color1(offsetX, offsetY, Color.SILVER)
                .x2_y2_color2(offsetX + offset, offsetY, Color.SILVER)
                .x3_y3_color3(offsetX + offset / 2, offsetY + offset, Color.SILVER)
                .outLine().lineWidth(2f)
                .update();
        offsetX = 20;
        offsetY += offset + step;
        Color color1 = Theme.PLEASANT_OCEAN_BLUE.getMainColor();
        Color color2 = Theme.PLEASANT_OCEAN_BLUE.getSubColor1();
        Color color3 = Theme.PLEASANT_OCEAN_BLUE.getSubColor2();
        Color color4 = Theme.PLEASANT_OCEAN_BLUE.getSubColor3();
        glRect31 = GLRect.createGLRect("GLRect31")
                .x1_y1_color1(offsetX, offsetY, color1)
                .width2_height2_color2(offset * 1.5f, 0, color2)
                .width3_height3_color3(offset * 1.5f, offset, color3)
                .width4_height4_color4(0, offset, color4)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect31 = GLRoundedRect.createRoundedRect("GLRoundedRect31")
                .x1_y1_color1(offsetX, offsetY, color1)
                .width2_height2_color2(offset * 1.5f, 0, color2)
                .width3_height3_color3(offset * 1.5f, offset, color3)
                .width4_height4_color4(0, offset, color4)
                .toggleRadius1_radius1(true, 15f)
                .toggleRadius2_radius2(true, 15f)
                .toggleRadius3_radius3(true, 15f)
                .toggleRadius4_radius4(true, 15f)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect32 = GLRoundedRect.createRoundedRect("GLRoundedRect32")
                .x1_y1_color1(offsetX, offsetY, color1)
                .width2_height2_color2(offset * 1.5f, 0, color2)
                .width3_height3_color3(offset * 1.5f, offset, color3)
                .width4_height4_color4(0, offset, color4)
                .toggleRadius1_radius1(true, 15f)
                .toggleRadius2_radius2(false, 0f)
                .toggleRadius3_radius3(true, 15f)
                .toggleRadius4_radius4(false, 0f)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect33 = GLRoundedRect.createRoundedRect("GLRoundedRect33")
                .x1_y1_color1(offsetX, offsetY, color1)
                .width2_height2_color2(offset * 1.5f, 0, color2)
                .width3_height3_color3(offset * 1.5f, offset, color3)
                .width4_height4_color4(0, offset, color4)
                .toggleRadius1_radius1(false, 0f)
                .toggleRadius2_radius2(true, 20f)
                .toggleRadius3_radius3(true, 20f)
                .toggleRadius4_radius4(true, 20f)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect35 = GLRoundedRect.createRoundedRect("GLRoundedRect35")
                .x1_y1_color1(offsetX, offsetY, color1)
                .width2_height2_color2(offset * 1.5f, 0, color2)
                .width3_height3_color3(offset * 1.5f, offset, color3)
                .width4_height4_color4(0, offset, color4)
                .toggleRadius1_radius1(true, 20f)
                .toggleRadius2_radius2(true, 20f)
                .toggleRadius3_radius3(false, 0f)
                .toggleRadius4_radius4(true, 20f)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glRoundedRect34 = GLRoundedRect.createRoundedRect("GLRoundedRect34")
                .x1_y1_color1(offsetX, offsetY, color1)
                .width2_height2_color2(offset * 1.5f, 0, color2)
                .width3_height3_color3(offset * 1.5f, offset, color3)
                .width4_height4_color4(0, offset, color4)
                .toggleRadius1_radius1(true, offset / 2f)
                .toggleRadius2_radius2(true, offset / 2f)
                .toggleRadius3_radius3(true, offset / 2f)
                .toggleRadius4_radius4(true, offset / 2f)
                .fill()
                .update();
        offsetX += offset * 1.5f + step;
        glCircle31 = GLCircle.createGLCirce("GLCircle31")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(360)
                .colors(color1, color2, color3, color4)
                .fill()
                .update();
        offsetX += offset * 1.3f + step;
        glCircle32 = GLCircle.createGLCirce("GLCircle32")
                .cx_xRadius(offsetX + offset / 2f, offset * 1.5f / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(360)
                .colors(color1, color2, color3, color4)
                .fill()
                .update();
        offsetX = 20;
        offsetY += offset + step;
        glCircle13 = GLCircle.createGLCirce("GLCircle13")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(5)
                .colors(Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor3())
                .fill()
                .update();
        offsetX += offset + step;
        glCircle14 = GLCircle.createGLCirce("GLCircle14")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(6)
                .colors(Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor3())
                .fill()
                .update();
        offsetX += offset + step;
        glCircle15 = GLCircle.createGLCirce("GLCircle15")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(7)
                .colors(Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor3())
                .fill()
                .update();
        offsetX += offset + step;
        glCircle16 = GLCircle.createGLCirce("GLCircle16")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(8)
                .colors(Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.ELEGANT_BEIGE_GREEN.getSubColor3())
                .fill()
                .update();
        offsetX += offset + step;
        glCircle17 = GLCircle.createGLCirce("GLCircle17")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(9)
                .colors(Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.ELEGANT_BEIGE_GREEN.getSubColor3())
                .fill()
                .update();
        offsetX += offset + step;
        glCircle18 = GLCircle.createGLCirce("GLCircle18")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(10)
                .colors(Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor2())
                .fill()
                .update();
        offsetX += offset + step;
        glCircle19 = GLCircle.createGLCirce("GLCircle19")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(11)
                .colors(Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor2())
                .fill()
                .update();
        offsetX += offset + step;
        glCircle20 = GLCircle.createGLCirce("GLCircle20")
                .cx_xRadius(offsetX + offset / 2f, offset / 2f)
                .cy_yRadius(offsetY + offset / 2f, offset / 2f)
                .segment(12)
                .colors(Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor2())
                .fill()
                .update();
        offsetX = 20;
        offsetY += offset + step;
        glBezierLine4 = new GLBezierLine("GlBezierLine4");
        glBezierLine4.bezierLine(
                new GLBezierLine.BezierPoint[]{
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX, offsetY), Theme.MUTED_ICE_CREAM_COLOR.getMainColor()),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + (offset * 10) / 5, offsetY + offset), Theme.MUTED_ICE_CREAM_COLOR.getSubColor1()),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + ((offset * 10) / 5) * 3, offsetY + offset), Theme.MUTED_ICE_CREAM_COLOR.getSubColor3()),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + ((offset * 10) / 5) * 5, offsetY + offset), Theme.MUTED_ICE_CREAM_COLOR.getSubColor3())
                }, 4, (int) offset
        );
        offsetX = 20;
        offsetY += offset / 2f + step;
        glBezierLine5 = new GLBezierLine("GlBezierLine5");
        glBezierLine5.bezierLine(
                new GLBezierLine.BezierPoint[]{
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX, offsetY), Theme.MUTED_ICE_CREAM_COLOR.getMainColor()),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + (offset * 10) / 5, offsetY + offset), Theme.MUTED_ICE_CREAM_COLOR.getSubColor1()),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + ((offset * 10) / 5) * 3, offsetY + offset), Theme.MUTED_ICE_CREAM_COLOR.getSubColor3()),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + ((offset * 10) / 5) * 5, offsetY + offset), Theme.MUTED_ICE_CREAM_COLOR.getSubColor3())
                }, 4, (int) offset
        );
        offsetX = 20;
        offsetY += offset / 2f + step;
        glBezierLine6 = new GLBezierLine("GlBezierLine6");
        glBezierLine6.bezierLine(
                new GLBezierLine.BezierPoint[]{
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX, offsetY), Theme.MUTED_ICE_CREAM_COLOR.getMainColor()),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + (offset * 10) / 5, offsetY + offset), Theme.MUTED_ICE_CREAM_COLOR.getSubColor1()),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + ((offset * 10) / 5) * 3, offsetY + offset), Theme.MUTED_ICE_CREAM_COLOR.getSubColor3()),
                        new GLBezierLine.BezierPoint(new Vector2f(offsetX + ((offset * 10) / 5) * 5, offsetY + offset), Theme.MUTED_ICE_CREAM_COLOR.getSubColor3())
                }, 4, (int) offset
        );
    }

    @Override
    public void drawFrame() {
        glBackGround.draw();
        glPoint1.draw();
        glPoint2.draw();
        glPoint3.draw();
        glLine1.draw();
        glLine2.draw();
        glLine3.draw();
        glPolygon1.draw();
        glPolygon2.draw();
        glPolygon3.draw();
        glBezierLine1.draw();
        glBezierLine2.draw();
        glBezierLine3.draw();
        glRect1.draw();
        glRoundedRect1.draw();
        glRoundedRect2.draw();
        glRoundedRect3.draw();
        glRoundedRect4.draw();
        glRoundedRect5.draw();
        glCircle1.draw();
        glCircle2.draw();
        glRect11.draw();
        glRoundedRect11.draw();
        glRoundedRect12.draw();
        glRoundedRect13.draw();
        glRoundedRect14.draw();
        glRoundedRect15.draw();
        glCircle11.draw();
        glCircle12.draw();
        glRect21.draw();
        glRoundedRect21.draw();
        glRoundedRect22.draw();
        glRoundedRect23.draw();
        glRoundedRect24.draw();
        glRoundedRect25.draw();
        glCircle21.draw();
        glCircle22.draw();
        glPolygon4.draw();
        glPolygon5.draw();
        glPolygon6.draw();
        glPolygon7.draw();
        glPolygon8.draw();
        glPolygon9.draw();
        glTriangle1.draw();
        glTriangle2.draw();
        glTriangle3.draw();
        glRect31.draw();
        glRoundedRect31.draw();
        glRoundedRect32.draw();
        glRoundedRect33.draw();
        glRoundedRect34.draw();
        glRoundedRect35.draw();
        glCircle31.draw();
        glCircle32.draw();
        glCircle13.draw();
        glCircle14.draw();
        glCircle15.draw();
        glCircle16.draw();
        glCircle17.draw();
        glCircle18.draw();
        glCircle19.draw();
        glCircle20.draw();
        glBezierLine4.draw();
        glBezierLine5.draw();
        glBezierLine6.draw();
    }

    @Override
    public void stopFrame() {
        glBackGround.cleanup();
        glPoint1.cleanup();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
