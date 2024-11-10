package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.utils.graphics.DisplayUtil;

public class Size extends EffectBase {
    private float x;
    private float y;
    private float z;
    private float cx;
    private float cy;
    private float cz;

    public Size(float x, float y, float z) {
        this(x, y, z, 0, 0, 0);
    }

    public Size(float x, float y, float z, float cx, float cy, float cz) {
        super(0, "Size", null);
        this.x = x;
        this.y = y;
        this.z = z;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
    }

    public Size(float x, float y) {
        this(x, y, 1.0f);
    }

    public Size(float x, float y, float cx, float cy) {
        this(x, y, 1.0f, cx, cy, 0);
    }

    @Override
    public void pop(Frame frame, GLPolygon basePolygon) {
        super.pop(frame, basePolygon);
    }

    @Override
    public void push(Frame frame, GLPolygon basePolygon) {
        float scaleX = x;
        float scaleY = y;
        float scaleZ = z;
        float acx = (2.0f * cx) / DisplayUtil.getDisplayWidthF() - 1f;
        float acy = (2.0f * cy) / DisplayUtil.getDisplayHeightF() - 1f;
        float acz = cz;

        basePolygon.getModelMatrix().translate(acx, acy, acz).scale(scaleX, scaleY, scaleZ).translate(-acx, -acy, -acz);

        super.push(frame, basePolygon);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getCz() {
        return cz;
    }

    public void setCz(float cz) {
        this.cz = cz;
    }
}
