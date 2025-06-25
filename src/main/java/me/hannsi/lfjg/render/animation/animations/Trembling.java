package me.hannsi.lfjg.render.animation.animations;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.animation.system.AnimationBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.math.animation.Easing;
import me.hannsi.lfjg.utils.math.animation.EasingUtil;

import static me.hannsi.lfjg.utils.math.MathHelper.sin;
import static me.hannsi.lfjg.utils.math.MathHelper.toRadians;

public class Trembling extends AnimationBase {
    protected EasingUtil easingUtil;
    protected float latestRadians;
    protected float degreesValue;
    @Getter
    @Setter
    private long millis;
    @Getter
    @Setter
    private float degrees;
    @Getter
    @Setter
    private boolean random;
    @Getter
    @Setter
    private float cx;
    @Getter
    @Setter
    private float cy;
    @Getter
    @Setter
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

        glObject.getTransform().translate(cx, cy, cz).rotateXYZ(0, 0, -latestRadians).rotateXYZ(0, 0, radian).translate(-cx, -cy, -cz);

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

}
