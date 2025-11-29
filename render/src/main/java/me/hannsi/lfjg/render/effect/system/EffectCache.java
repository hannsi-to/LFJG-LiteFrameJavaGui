package me.hannsi.lfjg.render.effect.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.rendering.GLObjectCache;

import java.util.LinkedHashMap;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;


public class EffectCache {
    private LinkedHashMap<String, EffectBase> effectBases;
    private boolean needFrameBuffer;
    private FrameBuffer baseFrameBuffer;

    EffectCache() {
        this.effectBases = new LinkedHashMap<>();
    }

    public static EffectCache createEffectCache() {
        return new EffectCache();
    }


    public EffectCache attachGLObjectCache(GLObjectCache glObjectCache) {
        for (Map.Entry<Long, GLObject> glObjectEntry : glObjectCache.getGlObjects().entrySet()) {
            GLObject glObject = glObjectEntry.getValue();
            glObject.setEffectCache(this);

            for (Map.Entry<String, EffectBase> effectBaseEntry : effectBases.entrySet()) {
                effectBaseEntry.getValue().create(glObject);

                if (effectBaseEntry.getValue().isNoUseFrameBuffer()) {
                    continue;
                }

                needFrameBuffer = true;
            }
        }

        return this;
    }

    public EffectCache attachGLObject(GLObject glObject) {
        glObject.setEffectCache(this);

        for (Map.Entry<String, EffectBase> effectBaseEntry : effectBases.entrySet()) {
            effectBaseEntry.getValue().create(glObject);

            if (effectBaseEntry.getValue().isNoUseFrameBuffer()) {
                continue;
            }

            needFrameBuffer = true;
        }

        return this;
    }

    public EffectCache createCache(EffectBase effectBase) {
        this.effectBases.put(effectBase.getName(), effectBase);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getName(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);

        return this;
    }

    public EffectCache createCache(String name, EffectBase effectBase, int index) {
        LinkedHashMap<String, EffectBase> newEffectCache = new LinkedHashMap<>();

        int i = 0;
        for (Map.Entry<String, EffectBase> effectBaseIdentifierEntry : effectBases.entrySet()) {
            if (i == index) {
                newEffectCache.put(name, effectBase);
            }

            newEffectCache.put(effectBaseIdentifierEntry.getKey(), effectBaseIdentifierEntry.getValue());

            i++;
        }

        this.effectBases = newEffectCache;

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getName(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);

        return this;
    }

    public void push(GLObject glObject) {
        for (Map.Entry<String, EffectBase> effectBasesEntry : effectBases.entrySet()) {
            effectBasesEntry.getValue().push(glObject);
        }
    }

    public void pop(GLObject glObject) {
        for (Map.Entry<String, EffectBase> effectBasesEntry : effectBases.entrySet()) {
            effectBasesEntry.getValue().pop(glObject);
        }
    }

    public void drawFrameBuffer(GLObject glObject) {
        int index = 0;
        EffectBase lastEffectBase = null;
        for (Map.Entry<String, EffectBase> effectBaseEntry : effectBases.entrySet()) {
            EffectBase effectBase = effectBaseEntry.getValue();
            if (effectBase.isNoUseFrameBuffer()) {
                continue;
            }

            FrameBuffer nowFrameBuffer = effectBase.getFrameBuffer();
            if (index == 0) {
                nowFrameBuffer.bindFrameBuffer();
                baseFrameBuffer.drawFrameBuffer(false);
                effectBase.drawFrameBuffer(nowFrameBuffer);
                baseFrameBuffer.drawVAORendering();
            } else {
                FrameBuffer lastFrameBuffer = lastEffectBase.getFrameBuffer();

                nowFrameBuffer.bindFrameBufferNoClear();
                lastFrameBuffer.drawFrameBuffer(false);
                effectBase.drawFrameBuffer(nowFrameBuffer);
                lastFrameBuffer.drawVAORendering();
            }

            lastEffectBase = effectBase;
            index++;
        }

        glStateCache.bindFrameBuffer(0);
        if (lastEffectBase != null) {
            push(glObject);
            lastEffectBase.getFrameBuffer().drawFrameBuffer();
            pop(glObject);
        }
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        for (Map.Entry<String, EffectBase> entry : effectBases.entrySet()) {
            EffectBase effectBase = entry.getValue();
            String name = entry.getKey();
            effectBase.cleanup();
            ids.append(name).append(", ");
        }
        effectBases.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.length() == 0 ? "" : ids.substring(0, ids.length() - 2),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public EffectBase getEffectBase(String name) {
        return effectBases.get(name);
    }

    public boolean isNeedFrameBuffer() {
        return needFrameBuffer;
    }

    public LinkedHashMap<String, EffectBase> getEffectBases() {
        return effectBases;
    }

    public void setEffectBases(LinkedHashMap<String, EffectBase> effectBases) {
        this.effectBases = effectBases;
    }

    public FrameBuffer getBaseFrameBuffer() {
        return baseFrameBuffer;
    }

    public void setBaseFrameBuffer(FrameBuffer baseFrameBuffer) {
        this.baseFrameBuffer = baseFrameBuffer;
    }
}