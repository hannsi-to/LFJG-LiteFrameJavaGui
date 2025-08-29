package me.hannsi.lfjg.render.effect.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;

public class EffectBase {
    private final String name;
    private final boolean noUseFrameBuffer;
    private FrameBuffer frameBuffer;

    public EffectBase(String name,boolean noUseFrameBuffer) {
        this.name = name;
        this.noUseFrameBuffer = noUseFrameBuffer;


    }

    public void create(GLObject glObject){
        if(!noUseFrameBuffer){
            frameBuffer = new FrameBuffer();
            frameBuffer.createFrameBuffer();
            frameBuffer.createMatrix(glObject.getTransform().getModelMatrix(),glObject.getViewMatrix());
        }
    }

    public void cleanup() {
        if(frameBuffer != null){
            frameBuffer.cleanup();
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                name,
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public void pop(GLObject baseGLObject) {

    }

    public void push(GLObject baseGLObject) {

    }

    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {

    }

    public String getName() {
        return name;
    }

    public boolean isNoUseFrameBuffer() {
        return noUseFrameBuffer;
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }
}