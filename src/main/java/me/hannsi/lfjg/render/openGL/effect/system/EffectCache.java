package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

public class EffectCache {
    private List<EffectBase> effectBases;

    public EffectCache() {
        this.effectBases = new ArrayList<>();
    }

    public void createCache(EffectBase effectBase){
        this.effectBases.add(effectBase);
    }

    public void push(GLObject glObject){
        for (EffectBase effectBase : effectBases) {
            effectBase.push(glObject);
        }
    }

    public void pop(GLObject glObject){
        for (EffectBase effectBase : effectBases) {
            effectBase.pop(glObject);
        }
    }

    public void frameBufferPush(GLObject glObject){
        for (EffectBase effectBase : effectBases) {
            effectBase.frameBufferPush(glObject);
        }
    }

    public void frameBufferPop(GLObject glObject){
        for (EffectBase effectBase : effectBases) {
            effectBase.frameBufferPop(glObject);
        }
    }

    public List<EffectBase> getEffectBases() {
        return effectBases;
    }

    public void setEffectBases(List<EffectBase> effectBases) {
        this.effectBases = effectBases;
    }
}
