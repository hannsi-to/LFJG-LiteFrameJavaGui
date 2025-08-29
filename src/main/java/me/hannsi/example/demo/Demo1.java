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
        glBackGround = new GLRect("GLBackGround");
        glBackGround.rect(0, 0, frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), Color.WHITE);

        float offsetX = 20;
        float offsetY = 80;
        glPoint1 = new GLPoint("GLPoint1");
        glPoint1.point(offsetX + offset / 2f, offsetY + offset / 2f, 5, Color.BLACK);
        offsetX += offset + step;
        glPoint2 = new GLPoint("GLPoint2");
        glPoint2.point(offsetX + offset / 2f, offsetY + offset / 2f, 10, Color.BLACK);
        offsetX += offset + step;
        glPoint3 = new GLPoint("GLPoint3");
        glPoint3.point(offsetX + offset / 2f, offsetY + offset / 2f, 15, Color.BLACK);
        offsetX += offset + step;
        glLine1 = new GLLine("GLLine1");
        glLine1.line(offsetX, offsetY, offsetX + offset, offsetY + offset, 2, Color.BLACK);
        offsetX += offset + step;
        glLine2 = new GLLine("GLLine2");
        glLine2.line(offsetX, offsetY, offsetX + offset, offsetY + offset, 4, Color.BLACK);
        offsetX += offset + step;
        glLine3 = new GLLine("GLLine3");
        glLine3.line(offsetX, offsetY, offsetX + offset, offsetY + offset, 6, Color.BLACK);
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
        glRect1 = new GLRect("GLRect1");
        glRect1.rectWH(offsetX, offsetY, offset * 1.5f, offset, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect1 = new GLRoundedRect("GLRoundedRect1");
        glRoundedRect1.roundedRectWH(offsetX, offsetY, offset * 1.5f, offset, 15, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect2 = new GLRoundedRect("GLRoundedRect2");
        glRoundedRect2.roundedRectWH(offsetX, offsetY, offset * 1.5f, offset, 20, 0, 20, 0, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect3 = new GLRoundedRect("GLRoundedRect3");
        glRoundedRect3.roundedRectWH(offsetX, offsetY, offset * 1.5f, offset, 0, 20, 20, 20, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect5 = new GLRoundedRect("GLRoundedRect5");
        glRoundedRect5.roundedRectWH(offsetX, offsetY, offset * 1.5f, offset, 20, 20, 0, 20, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect4 = new GLRoundedRect("GLRoundedRect4");
        glRoundedRect4.roundedRectWH(offsetX, offsetY, offset * 1.5f, offset, offset / 2f, offset / 2f, offset / 2f, offset / 2f, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glCircle1 = new GLCircle("GLCircle1");
        glCircle1.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 360, color);
        offsetX += offset * 1.3f + step;
        glCircle2 = new GLCircle("GLCircle2");
        glCircle2.circle(offsetX + offset / 2f, offsetY + offset / 2f, offset * 1.5f / 2f, offset / 2f, 360, color);
        offsetX = 20;
        offsetY += offset + step;
        color = Color.ORANGE;
        glRect11 = new GLRect("GLRect11");
        glRect11.rectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 6, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect11 = new GLRoundedRect("GLRoundedRect11");
        glRoundedRect11.roundedRectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 6, 15, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect12 = new GLRoundedRect("GLRoundedRect12");
        glRoundedRect12.roundedRectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 6, 20, 0, 20, 0, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect13 = new GLRoundedRect("GLRoundedRect13");
        glRoundedRect13.roundedRectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 6, 0, 20, 20, 20, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect15 = new GLRoundedRect("GLRoundedRect15");
        glRoundedRect15.roundedRectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 6, 20, 20, 0, 20, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect14 = new GLRoundedRect("GLRoundedRect14");
        glRoundedRect14.roundedRectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 6, offset / 2f, offset / 2f, offset / 2f, offset / 2f, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glCircle11 = new GLCircle("GLCircle11");
        glCircle11.circleOutLine(offsetX + offset / 2f, offsetY + offset / 2f, offset / 2f, 360, 6, color);
        offsetX += offset * 1.3f + step;
        glCircle12 = new GLCircle("GLCircle12");
        glCircle12.circleOutLine(offsetX + offset / 2f, offsetY + offset / 2f, offset * 1.5f / 2f, offset / 2f, 360, 6, color);
        offsetX = 20;
        offsetY += offset + step;
        color = Color.PINK;
        glRect21 = new GLRect("GLRect21");
        glRect21.rectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 2, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect21 = new GLRoundedRect("GLRoundedRect21");
        glRoundedRect21.roundedRectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 2, 15, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect22 = new GLRoundedRect("GLRoundedRect22");
        glRoundedRect22.roundedRectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 2, 20, 0, 20, 0, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect23 = new GLRoundedRect("GLRoundedRect23");
        glRoundedRect23.roundedRectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 2, 0, 20, 20, 20, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect25 = new GLRoundedRect("GLRoundedRect25");
        glRoundedRect25.roundedRectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 2, 20, 20, 0, 20, color, color, color, color);
        offsetX += offset * 1.5f + step;
        glRoundedRect24 = new GLRoundedRect("GLRoundedRect24");
        glRoundedRect24.roundedRectWHOutLine(offsetX, offsetY, offset * 1.5f, offset, 2, offset / 2f, offset / 2f, offset / 2f, offset / 2f, color, color, color, color);
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
        glTriangle1 = new GLTriangle("GLTriangle1");
        glTriangle1.triangle(offsetX, offsetY, offsetX + offset, offsetY, offsetX + offset / 2, offsetY + offset, Color.SILVER);
        offsetX += offset + step;
        glTriangle2 = new GLTriangle("GLTriangle2");
        glTriangle2.triangleOutLine(offsetX, offsetY, offsetX + offset, offsetY, offsetX + offset / 2, offsetY + offset, 6f, Color.SILVER);
        offsetX += offset + step;
        glTriangle3 = new GLTriangle("GLTriangle3");
        glTriangle3.triangleOutLine(offsetX, offsetY, offsetX + offset, offsetY, offsetX + offset / 2, offsetY + offset, 2f, Color.SILVER);
        offsetX = 20;
        offsetY += offset + step;
        Color color1 = Theme.PLEASANT_OCEAN_BLUE.getMainColor();
        Color color2 = Theme.PLEASANT_OCEAN_BLUE.getSubColor1();
        Color color3 = Theme.PLEASANT_OCEAN_BLUE.getSubColor2();
        Color color4 = Theme.PLEASANT_OCEAN_BLUE.getSubColor3();
        glRect31 = new GLRect("GLRect31");
        glRect31.rectWH(offsetX, offsetY, offset * 1.5f, offset, color1, color2, color3, color4);
        offsetX += offset * 1.5f + step;
        glRoundedRect31 = new GLRoundedRect("GLRoundedRect31");
        glRoundedRect31.roundedRectWH(offsetX, offsetY, offset * 1.5f, offset, 15, color1, color2, color3, color4);
        offsetX += offset * 1.5f + step;
        glRoundedRect32 = new GLRoundedRect("GLRoundedRect32");
        glRoundedRect32.roundedRectWH(offsetX, offsetY, offset * 1.5f, offset, 20, 0, 20, 0, color1, color2, color3, color4);
        offsetX += offset * 1.5f + step;
        glRoundedRect33 = new GLRoundedRect("GLRoundedRect33");
        glRoundedRect33.roundedRectWH(offsetX, offsetY, offset * 1.5f, offset, 0, 20, 20, 20, color1, color2, color3, color4);
        offsetX += offset * 1.5f + step;
        glRoundedRect35 = new GLRoundedRect("GLRoundedRect35");
        glRoundedRect35.roundedRectWH(offsetX, offsetY, offset * 1.5f, offset, 20, 20, 0, 20, color1, color2, color3, color4);
        offsetX += offset * 1.5f + step;
        glRoundedRect34 = new GLRoundedRect("GLRoundedRect34");
        glRoundedRect34.roundedRectWH(offsetX, offsetY, offset * 1.5f, offset, offset / 2f, offset / 2f, offset / 2f, offset / 2f, color1, color2, color3, color4);
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
