package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class DrawObject extends EffectBase {
    public DrawObject(Vector2f resolution) {
        super(resolution,Integer.MAX_VALUE,"DrawObject", (Class<GLObject>) null);
    }

    @Override
    public void frameBuffer(EffectCache effectCache, int oldIndex, GLObject glObject) {
        super.frameBuffer(effectCache, oldIndex, glObject);
    }
}