package me.hannsi.lfjg.render.animation.animations;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.math.animation.Easing;
import me.hannsi.lfjg.core.utils.math.animation.EasingUtil;
import me.hannsi.lfjg.render.animation.system.AnimationBase;
import me.hannsi.lfjg.render.renderers.GLObject;

public class Trembling extends AnimationBase {
    protected EasingUtil easingUtil;
    protected float latestRadians;
    protected float degreesValue;

    private long millis = 1000;
    private float degrees = 90;
    private boolean random = true;
    private float cx = 500;
    private float cy = 500;
    private float cz = 0;

    Trembling(String name) {
        super(name);
    }

    public static Trembling createTrembling(String name) {
        return new Trembling(name);
    }

    public Trembling millis(long millis) {
        this.millis = millis;
        return this;
    }

    public Trembling degrees(float degrees) {
        this.degrees = degrees;
        return this;
    }

    public Trembling random(boolean random) {
        this.random = random;
        return this;
    }

    public Trembling cx(float cx) {
        this.cx = cx;
        return this;
    }

    public Trembling cy(float cy) {
        this.cy = cy;
        return this;
    }

    public Trembling cz(float cz) {
        this.cz = cz;
        return this;
    }

    @Override
    public void loop(long currentTime, GLObject glObject) {
        if (easingUtil == null) {
            easingUtil = new EasingUtil(Easing.easeLinear);
            easingUtil.reset();
            setDegreesValue();
        }

        float easeValue = easingUtil.get(millis);
        float radian = (easingUtil.reverse ? -1 : 1) * (MathHelper.toRadians(MathHelper.sin(MathHelper.toRadians(easeValue * 180)) * degreesValue));

//        glObject.getTransform().translate(cx, cy, cz).rotateXYZ(0, 0, -latestRadians).rotateXYZ(0, 0, radian).translate(-cx, -cy, -cz);

        if (easingUtil.done(easeValue)) {
            easingUtil.reset();
            easingUtil.reverse = !easingUtil.reverse;
            setDegreesValue();
        }

        latestRadians = radian;

        super.loop(currentTime, glObject);
    }

    protected void setDegreesValue() {
        degreesValue = random ? (float) (Math.random() * degrees) : degrees;
    }

    public long getMillis() {
        return millis;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }

    public float getDegrees() {
        return degrees;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
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
