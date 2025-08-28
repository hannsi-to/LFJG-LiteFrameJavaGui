package me.hannsi.lfjg.render.effect.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.Id;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import java.util.*;

public class EffectCache {
    private LinkedHashMap<String,EffectBase> effectBases;
    private FrameBuffer currentFrameBuffer;
    private FrameBuffer latestFrameBuffer;
    private boolean needFrameBuffer;

    EffectCache() {
        this.effectBases = new LinkedHashMap<>();
    }

    public static EffectCache createEffectCache() {
        return new EffectCache();
    }

    public EffectCache attachGLObject(GLObject glObject){
        glObject.setEffectCache(this);

        currentFrameBuffer = new FrameBuffer(glObject);
        currentFrameBuffer.createFrameBuffer();
        currentFrameBuffer.createMatrix(glObject.getTransform().getModelMatrix(),glObject.getViewMatrix());

        latestFrameBuffer = new FrameBuffer(glObject);
        latestFrameBuffer.createFrameBuffer();
        latestFrameBuffer.createMatrix(glObject.getTransform().getModelMatrix(),glObject.getViewMatrix());

        for (Map.Entry<String, EffectBase> effectBaseEntry : effectBases.entrySet()) {
            if(effectBaseEntry.getValue().isNoUseFrameBuffer()){
                continue;
            }
            needFrameBuffer = true;
        }

        return this;
    }

    public EffectCache createCache(EffectBase effectBase) {
        this.effectBases.put(effectBase.getName(),effectBase);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getName(),
                ""
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public EffectCache createCache(String name, EffectBase effectBase, int index) {
        LinkedHashMap<String,EffectBase> newEffectCache = new LinkedHashMap<>();

        int i = 0;
        for (Map.Entry<String,EffectBase> effectBaseIdentifierEntry : effectBases.entrySet()) {
            if (i == index) {
                newEffectCache.put(name,effectBase);
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
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public void push(GLObject glObject) {
        for (Map.Entry<String,EffectBase> effectBasesEntry : effectBases.entrySet()) {
            effectBasesEntry.getValue().push(glObject);
        }
    }

    public void pop(GLObject glObject) {
        for (Map.Entry<String,EffectBase> effectBasesEntry : effectBases.entrySet()) {
            effectBasesEntry.getValue().pop(glObject);
        }
    }

    public void drawFrameBuffer(GLObject glObject){
        for (Map.Entry<String, EffectBase> effectBaseEntry : effectBases.entrySet()) {
            EffectBase effectBase = effectBaseEntry.getValue();
            if(effectBase.isNoUseFrameBuffer()){
                continue;
            }

            currentFrameBuffer.bindFrameBufferNoClear();

            latestFrameBuffer.drawFrameBuffer(false);

            effectBase.drawFrameBuffer(latestFrameBuffer);

            latestFrameBuffer.drawVAORendering();

            currentFrameBuffer.unbindFrameBuffer();

            latestFrameBuffer = currentFrameBuffer;
        }

        latestFrameBuffer.drawFrameBuffer();
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        for (Map.Entry<String,EffectBase> entry : effectBases.entrySet()) {
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
        ).logging(DebugLevel.DEBUG);
    }

    public EffectBase getEffectBase(String name) {
        return effectBases.get(name);
    }

    public boolean isNeedFrameBuffer() {
        return needFrameBuffer;
    }

    public FrameBuffer getCurrentFrameBuffer() {
        return currentFrameBuffer;
    }

    public void setCurrentFrameBuffer(FrameBuffer currentFrameBuffer) {
        this.currentFrameBuffer = currentFrameBuffer;
    }

    public FrameBuffer getLatestFrameBuffer() {
        return latestFrameBuffer;
    }

    public void setLatestFrameBuffer(FrameBuffer latestFrameBuffer) {
        this.latestFrameBuffer = latestFrameBuffer;
    }
}