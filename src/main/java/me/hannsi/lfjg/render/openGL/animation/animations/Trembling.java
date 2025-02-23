package me.hannsi.lfjg.render.openGL.animation.animations;

import me.hannsi.lfjg.render.openGL.animation.system.AnimationBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.math.animation.Easing;
import me.hannsi.lfjg.utils.math.animation.EasingUtil;

import static me.hannsi.lfjg.utils.math.MathHelper.sin;
import static me.hannsi.lfjg.utils.math.MathHelper.toRadians;

public class Trembling extends AnimationBase {
    protected EasingUtil easingUtil;
    protected float latestRadians;
    protected float degreesValue;
    private long millis;
    private float degrees;
    private boolean random;
    private float cx;
    private float cy;
    private float cz;

    public Trembling(long pauseTime, long millis, float degrees, boolean random, float cx, float cy, float cz) {
        super("Trembling", 0, pauseTime);

        this.millis = millis;
        this.degrees = degrees;
        this.random = random;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
    }

    public Trembling(long pauseTime, long millis, float degrees, float cx, float cy) {
        this(pauseTime, millis, degrees, false, cx, cy, 0);
    }

    public Trembling(long pauseTime, long millis, float degrees, boolean random, float cx, float cy) {
        this(pauseTime, millis, degrees, random, cx, cy, 0);
    }


    @Override
    public void loop(long currentTime, GLObject glObject) {
        if (easingUtil == null) {
            easingUtil = new EasingUtil(Easing.easeLinear);
            easingUtil.reset();
            setDegreesValue();
        }

        float easeValue = easingUtil.get(millis);
        float radian = (easingUtil.isReverse() ? -1 : 1) * (toRadians(sin(toRadians(easeValue * 180)) * degreesValue));

        glObject.getModelMatrix().translate(cx, cy, cz).rotateXYZ(0, 0, -latestRadians).rotateXYZ(0, 0, radian).translate(-cx, -cy, -cz);

        if (easingUtil.done(easeValue)) {
            easingUtil.reset();
            easingUtil.setReverse(!easingUtil.isReverse());
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
