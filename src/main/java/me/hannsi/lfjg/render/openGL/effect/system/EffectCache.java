package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.FrameBuffer;

import java.util.HashMap;
import java.util.Map;

public class EffectCache {
    private Map<EffectBase, Long> effectBases;

    public EffectCache() {
        this.effectBases = new HashMap<>();
    }

    public void createCache(EffectBase effectBase, GLObject glObject) {
        this.effectBases.put(effectBase, glObject.getObjectId());
    }

    public void push(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            if (glObject.getObjectId() != effectBase.getValue()) {
                continue;
            }

            effectBase.getKey().push(glObject);
        }
    }

    public void pop(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            if (glObject.getObjectId() != effectBase.getValue()) {
                continue;
            }

            effectBase.getKey().pop(glObject);
        }
    }

    public void frameBuffer(GLObject glObject) {
        int lateIndex = 0;

        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            if (glObject.getObjectId() != effectBase.getValue()) {
                continue;
            }

            FrameBuffer frameBuffer = effectBase.getKey().getFrameBuffer();
            FrameBuffer nextFrameBuffer = getNextFrameBuffer(effectBase);

            if (nextFrameBuffer != null) {
                nextFrameBuffer.bindFrameBuffer();
            }

            effectBase.getKey().frameBuffer(this, lateIndex, glObject);

            frameBuffer.drawFrameBuffer();

            if (nextFrameBuffer != null) {
                nextFrameBuffer.unbindFrameBuffer();
            }

            lateIndex++;
        }
    }

    private FrameBuffer getNextFrameBuffer(Map.Entry<EffectBase, Long> effectBase) {
        boolean setFrameBufferValue = false;
        FrameBuffer nextFrameBuffer = null;

        for (Map.Entry<EffectBase, Long> effectBase2 : effectBases.entrySet()) {
            if (setFrameBufferValue) {
                nextFrameBuffer = effectBase2.getKey().getFrameBuffer();
            }

            if (effectBase2 != effectBase) {
                continue;
            }

            setFrameBufferValue = true;
        }

        return nextFrameBuffer;
    }

    public void cleanup(long objectId) {
    }

    public Map<EffectBase, Long> getEffectBases() {
        return effectBases;
    }

    public void setEffectBases(Map<EffectBase, Long> effectBases) {
        this.effectBases = effectBases;
    }
}
