package me.hannsi.lfjg.render.animation.animations;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.utils.math.animation.Easing;
import me.hannsi.lfjg.core.utils.math.animation.EasingUtil;
import me.hannsi.lfjg.render.animation.system.AnimationBase;
import me.hannsi.lfjg.render.renderers.GLObject;

import static me.hannsi.lfjg.core.utils.math.MathHelper.*;

public class Bounce extends AnimationBase {
    protected EasingUtil easingUtil;
    protected float lastX;
    protected float lastY;

    @Getter
    @Setter
    private long millis;
    @Getter
    @Setter
    private float height;
    @Getter
    @Setter
    private float degrees;

    public Bounce(long pauseTime, long millis, float height, float degrees) {
        super("Bounce", 1, pauseTime);

        this.millis = millis;
        this.height = height;
        this.degrees = degrees;
    }

    @Override
    public void loop(long currentTime, GLObject glObject) {
        if (easingUtil == null) {
            easingUtil = new EasingUtil(Easing.easeLinear);
            easingUtil.reset();
        }

        float easeValue = easingUtil.get(millis);
        float distance = abs(sin(toRadians(easeValue * 180))) * height;
        float x = distance * cos(toRadians(degrees));
        float y = distance * sin(toRadians(degrees));

        glObject.getTransform().translate(-lastX, -lastY, 0).translate(x, y, 0);

        if (easingUtil.done(easeValue)) {
            easingUtil.reset();
            easingUtil.setReverse(!easingUtil.isReverse());
        }

        lastX = x;
        lastY = y;

        super.loop(currentTime, glObject);
    }

}
