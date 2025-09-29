package me.hannsi.example.demo;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.UnicodeBlocks;
import me.hannsi.lfjg.core.utils.type.types.Theme;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.LFJGRenderTextContext;
import me.hannsi.lfjg.render.effect.effects.*;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.*;
import me.hannsi.lfjg.render.renderers.text.GLText;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import me.hannsi.lfjg.render.system.text.AlignType;
import me.hannsi.lfjg.render.system.text.TextFormatType;
import me.hannsi.lfjg.render.system.text.font.Font;
import org.joml.Vector2f;

public class Demo5 implements IScene {
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

    GLText glText1;
    GLText glText2;
    GLText glText3;
    GLText glText4;
    GLText glText5;
    GLText glText6;
    GLText glText7;
    GLText glText8;
    GLText glText9;

    GLRect glRect1a;
    EffectCache effectCache1;
    GLRect glRect2;
    EffectCache effectCache2;
    GLRect glRect3;
    EffectCache effectCache3;
    GLRect glRect4;
    EffectCache effectCache4;
    GLRect glRect5;
    EffectCache effectCache5;
    GLRect glRect6;
    EffectCache effectCache6;
    GLRect glRect7;
    EffectCache effectCache7;
    GLRect glRect8;
    EffectCache effectCache8;
    GLRect glRect9;
    EffectCache effectCache9;
    GLRect glRect10;
    EffectCache effectCache10;
    GLRect glRect11a;
    EffectCache effectCache11;
    GLRect glRect12;
    EffectCache effectCache12;
    GLRect glRect13;
    EffectCache effectCache13;
    GLRect glRect14;
    EffectCache effectCache14;
    GLRect glRect15;
    EffectCache effectCache15;
    GLRect glRect16;
    EffectCache effectCache16;
    GLRect glRect17;
    EffectCache effectCache17;
    GLRect glRect18;
    EffectCache effectCache18;
    GLRect glRect19;
    EffectCache effectCache19;
    GLRect glRect20;
    EffectCache effectCache20;
    GLRect glRect21a;
    EffectCache effectCache21;
    GLRect glRect22;
    EffectCache effectCache22;

    public Demo5(Frame frame) {
        this.frame = frame;
        this.scene = new Scene("Demo5", this);
    }

    @Override
    public void init() {
        glBackGround = GLRect.createGLRect("GLBackGround")
                .x1_y1_color1_2p(0, 0, Color.WHITE)
                .x3_y3_color3_2p(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), Color.WHITE)
                .fill();

        float offsetX = 20;
        float offsetY = 80;
        glPoint1 = GLPoint.createGLPoint("GLPoint1")
                .x_y_color(offsetX + offset / 2f, offsetY + offset / 2f, Color.BLACK)
                .pointSize(5);
        offsetX += offset + step;
        glPoint2 = GLPoint.createGLPoint("GLPoint2")
                .x_y_color(offsetX + offset / 2f, offsetY + offset / 2f, Color.BLACK)
                .pointSize(10);
        offsetX += offset + step;
        glPoint2 = GLPoint.createGLPoint("GLPoint3")
                .x_y_color(offsetX + offset / 2f, offsetY + offset / 2f, Color.BLACK)
                .pointSize(15);
        offsetX += offset + step;
        glLine1 = GLLine.createGLLine("GLLine1")
                .x1_y1_color1(offsetX, offsetY, Color.BLACK)
                .x2_y2_color2(offsetX + offset, offsetY + offset, Color.BLACK)
                .lineWidth(2);
        offsetX += offset + step;
        glLine2 = GLLine.createGLLine("GLLine2")
                .x1_y1_color1(offsetX, offsetY, Color.BLACK)
                .x2_y2_color2(offsetX + offset, offsetY + offset, Color.BLACK)
                .lineWidth(4);
        offsetX += offset + step;
        glLine3 = GLLine.createGLLine("GLLine3")
                .x1_y1_color1(offsetX, offsetY, Color.BLACK)
                .x2_y2_color2(offsetX + offset, offsetY + offset, Color.BLACK)
                .lineWidth(6);
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
                .fill();
        offsetX += offset * 1.5f + step;
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
                .fill();
        offsetX += offset * 1.5f + step;
        glRoundedRect2 = GLRoundedRect.createRoundedRect("GLRoundedRect2")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, 20f)
                .toggleRadius2_radius2(false, 0f)
                .toggleRadius3_radius3(true, 20f)
                .toggleRadius4_radius4(false, 0f)
                .fill();
        offsetX += offset * 1.5f + step;
        glRoundedRect3 = GLRoundedRect.createRoundedRect("GLRoundedRect3")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(false, 0f)
                .toggleRadius2_radius2(true, 20f)
                .toggleRadius3_radius3(true, 20f)
                .toggleRadius4_radius4(true, 20f)
                .fill();
        offsetX += offset * 1.5f + step;
        glRoundedRect5 = GLRoundedRect.createRoundedRect("GLRoundedRect5")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .toggleRadius1_radius1(true, 20f)
                .toggleRadius2_radius2(true, 20f)
                .toggleRadius3_radius3(false, 0f)
                .toggleRadius4_radius4(true, 20f)
                .fill();
        offsetX += offset * 1.5f + step;
        glCircle1 = new GLCircle("GLCircle1");
        glCircle1.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 360, color);
        offsetX += offset * 1.3f + step;
        glCircle2 = new GLCircle("GLCircle2");
        glCircle2.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset * 1.5f / 2f, offset / 2f, 360, color);
        offsetX = 20;
        offsetY += offset + step;
        color = Color.ORANGE;
        glRect11 = GLRect.createGLRect("GLRect11")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .outLine().lineWidth(6);
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
        glCircle11 = new GLCircle("GLCircle11");
        glCircle11.circleOutLine(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 360, 6, color);
        offsetX += offset * 1.3f + step;
        glCircle12 = new GLCircle("GLCircle12");
        glCircle12.circleOutLine(offsetX + offset / 2f, offsetY + offset / 2f, offset * 1.5f / 2f, offset / 2f, 360, 6, color);
        offsetX = 20;
        offsetY += offset + step;
        color = Color.PINK;
        glRect21 = GLRect.createGLRect("GLRect21")
                .x1_y1_color1_2p(offsetX, offsetY, color)
                .width3_height3_color3_2p(offset * 1.5f, offset, color)
                .outLine().lineWidth(2);
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
        glCircle21 = new GLCircle("GLCircle21");
        glCircle21.circleOutLine(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 360, 2, color);
        offsetX += offset * 1.3f + step;
        glCircle22 = new GLCircle("GLCircle22");
        glCircle22.circleOutLine(offsetX + offset / 2f, offsetY + offset / 2f, offset * 1.5f / 2f, offset / 2f, 360, 2, color);
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
                .fill();
        offsetX += offset + step;
        glTriangle2 = GLTriangle.createGLTriangle("GLTriangle2")
                .x1_y1_color1(offsetX, offsetY, Color.SILVER)
                .x2_y2_color2(offsetX + offset, offsetY, Color.SILVER)
                .x3_y3_color3(offsetX + offset / 2, offsetY + offset, Color.SILVER)
                .outLine().lineWidth(6f);
        offsetX += offset + step;
        glTriangle3 = GLTriangle.createGLTriangle("GLTriangle3")
                .x1_y1_color1(offsetX, offsetY, Color.SILVER)
                .x2_y2_color2(offsetX + offset, offsetY, Color.SILVER)
                .x3_y3_color3(offsetX + offset / 2, offsetY + offset, Color.SILVER)
                .outLine().lineWidth(2f);
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
                .fill();
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
        glCircle31 = new GLCircle("GLCircle31");
        glCircle31.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 360, color1, color2, color3, color4);
        offsetX += offset * 1.3f + step;
        glCircle32 = new GLCircle("GLCircle32");
        glCircle32.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset * 1.5f / 2f, offset / 2f, 360, color1, color2, color3, color4);
        offsetX = 20;
        offsetY += offset + step;
        glCircle13 = new GLCircle("GLCircle13");
        glCircle13.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 5, Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor3());
        offsetX += offset + step;
        glCircle14 = new GLCircle("GLCircle14");
        glCircle14.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 6, Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor3());
        offsetX += offset + step;
        glCircle15 = new GLCircle("GLCircle15");
        glCircle15.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 7, Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor3());
        offsetX += offset + step;
        glCircle16 = new GLCircle("GLCircle16");
        glCircle16.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 8, Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.ELEGANT_BEIGE_GREEN.getSubColor3());
        offsetX += offset + step;
        glCircle17 = new GLCircle("GLCircle17");
        glCircle17.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 9, Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.ELEGANT_BEIGE_GREEN.getSubColor3());
        offsetX += offset + step;
        glCircle18 = new GLCircle("GLCircle18");
        glCircle18.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 10, Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor2());
        offsetX += offset + step;
        glCircle19 = new GLCircle("GLCircle19");
        glCircle19.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 11, Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor2());
        offsetX += offset + step;
        glCircle20 = new GLCircle("GLCircle20");
        glCircle20.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 12, Theme.PLEASANT_OCEAN_BLUE.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor1(), Theme.POPPY_CHOCOLATE_MINT.getSubColor2());
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

        LFJGRenderTextContext.fontCache.createCache(
                "Font1",
                Font.createCustomFont()
                        .name("Font1")
                        .trueTypeFontPath(Location.fromFile("C:\\Windows\\Fonts\\Arial.ttf"))
                        .unicodeBlocks(UnicodeBlocks.getBlocks("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.:,;'\"(!?)+-*/="))
                        .createCache()
        );

        offsetY = frame.getWindowHeight() - 80;
        glText1 = new GLText("GLText1");
        glText1.text(
                "Font1", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" + TextFormatType.NEWLINE + "1234567890.:,;'\"(!?)+-*/=",
                20, offsetY, 30, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText1.getTextHeight("A") * 3;
        glText2 = new GLText("GLText2");
        glText2.text(
                "Font1", "12    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 12, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText2.getTextHeight("A");
        glText3 = new GLText("GLText3");
        glText3.text(
                "Font1", "18    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 18, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText3.getTextHeight("A");
        glText4 = new GLText("GLText4");
        glText4.text(
                "Font1", "24    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 24, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText4.getTextHeight("A");
        glText5 = new GLText("GLText5");
        glText5.text(
                "Font1", "36    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 36, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText5.getTextHeight("A");
        glText6 = new GLText("GLText6");
        glText6.text(
                "Font1", "48    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 48, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText6.getTextHeight("A");
        glText7 = new GLText("GLText7");
        glText7.text(
                "Font1", "60    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 60, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText7.getTextHeight("A");
        glText8 = new GLText("GLText8");
        glText8.text(
                "Font1", "72    The quick brown fox jumps over the lazy dog. 1234567890",
                20, offsetY, 72, Color.BLACK, true, AlignType.LEFT_TOP
        );
        offsetY -= glText1.getTextHeight("A") * 3;
        glText9 = new GLText("GLText9");
        glText9.text(
                "Font1",
                TextFormatType.BLACK + "BLACK  " +
                        TextFormatType.DARK_BLUE + "DARK_BLUE  " +
                        TextFormatType.DARK_GREEN + "DARK_GREEN  " +
                        TextFormatType.DARK_AQUA + "DARK_AQUA  " +
                        TextFormatType.DARK_RED + "DARK_RED  " +
                        TextFormatType.DARK_PURPLE + "DARK_PURPLE  " +
                        TextFormatType.GOLD + "GOLD  " +
                        TextFormatType.GRAY + "GRAY  " + TextFormatType.NEWLINE +
                        TextFormatType.DARK_GRAY + "DARK_GRAY  " +
                        TextFormatType.BLUE + "BLUE  " +
                        TextFormatType.GREEN + "GREEN  " +
                        TextFormatType.AQUA + "AQUA  " +
                        TextFormatType.RED + "RED  " +
                        TextFormatType.LIGHT_PURPLE + "LIGHT_PURPLE  " +
                        TextFormatType.YELLOW + "YELLOW  " +
                        TextFormatType.WHITE + "WHITE  " +
                        TextFormatType.DEFAULT_COLOR + "DEFAULT_COLOR" + TextFormatType.NEWLINE +
                        "OBFUSCATED: " + TextFormatType.OBFUSCATED + "OBFUSCATED" + TextFormatType.RESET + TextFormatType.NEWLINE +
                        "BOLD: " + TextFormatType.BOLD + "BOLD" + TextFormatType.RESET +
                        "  STRIKETHROUGH: " + TextFormatType.STRIKETHROUGH + "STRIKETHROUGH" + TextFormatType.RESET +
                        "  UNDERLINE: " + TextFormatType.UNDERLINE + "UnderLine" + TextFormatType.RESET +
                        "  ITALIC: " + TextFormatType.ITALIC + "Italic" + TextFormatType.RESET +
                        "  GHOST: " + TextFormatType.GHOST + "Ghost" + TextFormatType.RESET + TextFormatType.NEWLINE +
                        "HIDE_BOX: " + TextFormatType.HIDE_BOX + "HideBox" + TextFormatType.RESET +
                        "  SHADOW: " + TextFormatType.SHADOW + "Shadow" + TextFormatType.RESET +
                        "  OUTLINE: " + TextFormatType.OUTLINE + "OutLine" + TextFormatType.RESET +
                        "  DOUBLE_UNDERLINE: " + TextFormatType.DOUBLE_UNDERLINE + "DoubleUnderLine" + TextFormatType.RESET + TextFormatType.NEWLINE +
                        "DOUBLE_STRIKETHROUGH: " + TextFormatType.DOUBLE_STRIKETHROUGH + "DoubleSTRIKETHROUGH" + TextFormatType.RESET +
                        "  OVERLINE: " + TextFormatType.OVERLINE + "OverLine" + TextFormatType.RESET,
                20, offsetY, 30, Color.BLACK, true, AlignType.LEFT_TOP
        );

        LFJGRenderContext.textureCache.createCache("Demo3", Location.fromResource("texture/test/Demo3.jpg"));

        float stepX = 1920 / 8f + 20;
        float stepY = 1440 / 8f - 20;
        offsetX = 20;
        offsetY = frame.getWindowHeight() - stepY;
        glRect1a = GLRect.createGLRect("GLRect1")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect1a.uv(0, 1, 1, 0);
        effectCache1 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .attachGLObject(glRect1a);
        offsetX += stepX;
        glRect2 = GLRect.createGLRect("GLRect2")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect2.uv(0, 1, 1, 0);
        effectCache2 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(BoxBlur.createBoxBlur("BoxBlusr1"))
                .attachGLObject(glRect2);
        offsetX += stepX;
        glRect3 = GLRect.createGLRect("GLRect3")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect3.uv(0, 1, 1, 0);
        effectCache3 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ChromaKey.createChromaKey("ChromaKey1").chromaKeyColor(Color.of(205, 107, 23, 255)))
                .attachGLObject(glRect3);
        offsetX += stepX;
        glRect4 = GLRect.createGLRect("GLRect4")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect4.uv(0, 1, 1, 0);
        effectCache4 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ChromaticAberration.createChromaticAberration("ChromaticAberration1"))
                .attachGLObject(glRect4);
        offsetX += stepX;
        glRect5 = GLRect.createGLRect("GLRect5")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect5.uv(0, 1, 1, 0);
        effectCache5 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Bloom.createBloom("Bloom"))
                .attachGLObject(glRect5);
        offsetX += stepX;
        glRect6 = GLRect.createGLRect("GLRect6")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect6.uv(0, 1, 1, 0);
        effectCache6 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ColorChanger.createColorChanger("ColorChanger1").targetColor(Color.of(17, 40, 133, 255)).newColor(Color.of(255, 0, 0, 255)))
                .attachGLObject(glRect6);
        offsetX += stepX;
        glRect7 = GLRect.createGLRect("GLRect7")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect7.uv(0, 1, 1, 0);
        effectCache7 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ColorCorrection.createColorCorrection("ColorCorrection1"))
                .attachGLObject(glRect7);
        offsetX = 20;
        offsetY -= stepY + 40;
        glRect8 = GLRect.createGLRect("GLRect8")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect8.uv(0, 1, 1, 0);
        effectCache8 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(DiagonalClipping.createDiagonalClipping("DiagonalClipping").centerX(offsetX + (1920 / 8f) / 2f).centerY(offsetY + (1440 / 8f) / 2f))
                .attachGLObject(glRect8);
        offsetX += stepX;
        glRect9 = GLRect.createGLRect("GLRect9")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect9.uv(0, 1, 1, 0);
        effectCache9 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(DirectionalBlur.createDirectionBlur("DirectionalBlur1"))
                .attachGLObject(glRect9);
        offsetX += stepX;
        glRect10 = GLRect.createGLRect("GLRect10")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect10.uv(0, 1, 1, 0);
        effectCache10 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(EdgeExtraction.createEdgeExtraction("EdgeExtraction1"))
                .attachGLObject(glRect10);
        offsetX += stepX;
        glRect11 = GLRect.createGLRect("GLRect11")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect11.uv(0, 1, 1, 0);
        effectCache11 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(FXAA.createFXAA("FXAA1").useAlpha(true))
                .attachGLObject(glRect11);
        offsetX += stepX;
        glRect12 = GLRect.createGLRect("GLRect12")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect12.uv(0, 1, 1, 0);
        effectCache12 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Flash.createFlash("Flash1"))
                .attachGLObject(glRect12);
        offsetX += stepX;
        glRect13 = GLRect.createGLRect("GLRect13")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect13.uv(0, 1, 1, 0);
        effectCache13 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(GaussianBlurHorizontal.createGaussianBlurHorizontal("GaussianBlurHorizontal1"))
                .attachGLObject(glRect13);
        offsetX += stepX;
        glRect14 = GLRect.createGLRect("GLRect14")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect14.uv(0, 1, 1, 0);
        effectCache14 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(GaussianBlurVertical.createGaussianBlurVertical("GaussianBlurVertical1"))
                .attachGLObject(glRect14);
        offsetX = 20;
        offsetY -= stepY + 40;
        glRect15 = GLRect.createGLRect("GLRect15")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect15.uv(0, 1, 1, 0);
        effectCache15 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Glow.createGlow("Glow1"))
                .attachGLObject(glRect15);
        offsetX += stepX;
        glRect16 = GLRect.createGLRect("GLRect16")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect16.uv(0, 1, 1, 0);
        effectCache16 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Gradation.createGradation("Gradation1"))
                .attachGLObject(glRect16);
        offsetX += stepX;
        glRect17 = GLRect.createGLRect("GLRect17")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect17.uv(0, 1, 1, 0);
        effectCache17 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Inversion.createInversion("Inversion1"))
                .createCache(Translate.createTranslate("Translate1").x(600).y(-121))
                .attachGLObject(glRect17);
        offsetX += stepX;
        glRect18 = GLRect.createGLRect("GLRect18")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect18.uv(0, 1, 1, 0);
        effectCache18 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(LensBlur.createLensBlur("LensBlur1"))
                .attachGLObject(glRect18);
        offsetX += stepX;
        glRect19 = GLRect.createGLRect("GLRect19")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect19.uv(0, 1, 1, 0);
        effectCache19 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(LuminanceKey.createLuminanceKey("LuminanceKey1"))
                .attachGLObject(glRect19);
        offsetX += stepX;
        glRect20 = GLRect.createGLRect("GLRect20")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect20.uv(0, 1, 1, 0);
        effectCache20 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Monochrome.createMonochrome("Monochrome"))
                .attachGLObject(glRect20);
        offsetX += stepX;
        glRect21 = GLRect.createGLRect("GLRect21")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect21.uv(0, 1, 1, 0);
        effectCache21 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Pixelate.createPixelate("Pixelate1"))
                .attachGLObject(glRect21);
        offsetX = 20;
        offsetY -= stepY + 40;
        glRect22 = GLRect.createGLRect("GLRect22")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill();
        glRect22.uv(0, 1, 1, 0);
        effectCache22 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(RadialBlur.createRadialBlur("RadialBlur1").centerX(offsetX + (1920 / 8f) / 2f).centerY(offsetY + (1440 / 8f) / 2f))
                .attachGLObject(glRect22);
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

        glText1.draw();
        glText2.draw();
        glText3.draw();
        glText4.draw();
        glText5.draw();
        glText6.draw();
        glText7.draw();
        glText8.draw();
        glText9.draw();

        glRect1a.draw();
        glRect2.draw();
        glRect3.draw();
        glRect4.draw();
        glRect5.draw();
        glRect6.draw();
        glRect7.draw();
        glRect8.draw();
        glRect9.draw();
        glRect10.draw();
        glRect11a.draw();
        glRect12.draw();
        glRect13.draw();
        glRect14.draw();
        glRect15.draw();
        glRect16.draw();
        glRect17.draw();
        glRect18.draw();
        glRect19.draw();
        glRect20.draw();
        glRect21a.draw();
        glRect22.draw();
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
