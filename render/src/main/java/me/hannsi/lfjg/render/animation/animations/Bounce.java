package me.hannsi.lfjg.render.animation.animations;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.math.animation.Easing;
import me.hannsi.lfjg.core.utils.math.animation.EasingUtil;
import me.hannsi.lfjg.render.animation.system.AnimationBase;
import me.hannsi.lfjg.render.renderers.GLObject;

public class Bounce extends AnimationBase {
    protected EasingUtil easingUtil;
    protected float lastX;
    protected float lastY;

    private long millis = 500;
    private float height = 100;
    private float degrees = 45;

    Bounce(String name) {
        super(name);
    }

    public static Bounce createBounce(String name) {
        return new Bounce(name);
    }

    public Bounce millis(long millis) {
        this.millis = millis;
        return this;
    }

    public Bounce height(float height) {
        this.height = height;
        return this;
    }

    public Bounce degrees(float degrees) {
        this.degrees = degrees;
        return this;
    }

    @Override
    public void loop(long currentTime, GLObject glObject) {
        if (easingUtil == null) {
            easingUtil = new EasingUtil(Easing.easeLinear);
            easingUtil.reset();
        }

        float easeValue = easingUtil.get(millis);
        float distance = MathHelper.abs(MathHelper.sin(MathHelper.toRadians(easeValue * 180))) * height;
        float x = distance * MathHelper.cos(MathHelper.toRadians(degrees));
        float y = distance * MathHelper.sin(MathHelper.toRadians(degrees));

        glObject.getTransform().translate(-lastX, -lastY, 0).translate(x, y, 0);

        if (easingUtil.done(easeValue)) {
            easingUtil.reset();
            easingUtil.reverse = !easingUtil.reverse;
        }

        lastX = x;
        lastY = y;

        super.loop(currentTime, glObject);
    }

    public long getMillis() {
        return millis;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getDegrees() {
        return degrees;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
    }
}
