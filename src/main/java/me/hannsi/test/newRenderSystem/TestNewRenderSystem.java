package me.hannsi.test.newRenderSystem;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.system.buffer.BufferConfig;
import me.hannsi.lfjg.render.system.buffer.BufferSystem;
import me.hannsi.lfjg.render.system.memory.LinearAllocator;
import me.hannsi.lfjg.render.system.memory.PerFrameAllocatorSystem;

import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class TestNewRenderSystem implements LFJGFrame {
    private BufferSystem bufferSystem;

    public static void main(String[] args) {
        new TestNewRenderSystem().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        BufferConfig bufferConfig = new BufferConfig(
                BufferConfig.AllocationMode.BUFFER_STORAGE,
                BufferConfig.WriteConfig.builder()
                        .add(
                                BufferConfig.WriteMode.PERSISTENT_COHERENT,
                                new PerFrameAllocatorSystem(new LinearAllocator(1024, 4), 3)
                        )
                        .build(),
                BufferConfig.BufferProperty.BIND_BUFFER,
                false
        );

        bufferSystem = new BufferSystem(bufferConfig);
        bufferSystem.allocate(glGenBuffers(), GL_VERTEX_ARRAY);
    }

    @Override
    public void drawFrame() {
        bufferSystem.startFrame();


        bufferSystem.endFrame();
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {

    }

    private void setFrame() {
        frame = new Frame(this, getClass().getSimpleName());
    }
}
