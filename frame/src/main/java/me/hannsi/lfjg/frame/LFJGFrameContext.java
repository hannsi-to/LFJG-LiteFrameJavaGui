package me.hannsi.lfjg.frame;

import me.hannsi.lfjg.frame.system.Attachment;
import me.hannsi.lfjg.frame.system.RenderPass;

import java.util.List;

import static me.hannsi.lfjg.core.Core.GLStateCache.getLastFrameBuffer;
import static me.hannsi.lfjg.core.Core.OPEN_GL_PARAMETER_NAME_MAP;

public class LFJGFrameContext {
    public static Frame frame = null;
    public static RenderPass renderPass;

    public static void init() {
        renderPass = new RenderPass(
                getLastFrameBuffer(),
                List.of(
                        new Attachment(
                                OPEN_GL_PARAMETER_NAME_MAP.get("GL_COLOR_ATTACHMENT0"),
                                Attachment.LoadOp.CLEAR,
                                Attachment.StoreOp.STORE,
                                new float[]{0f, 0f, 0f, 1f}
                        ),
                        new Attachment(
                                OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEPTH_ATTACHMENT"),
                                Attachment.LoadOp.CLEAR,
                                Attachment.StoreOp.DONT_CARE,
                                new float[]{1f}
                        )
                )
        );
    }
}
