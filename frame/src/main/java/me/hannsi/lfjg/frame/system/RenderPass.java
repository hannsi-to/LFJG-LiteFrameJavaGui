package me.hannsi.lfjg.frame.system;

import java.util.List;

public class RenderPass {
    public final int fbo;
    public final List<Attachment> attachments;

    public RenderPass(int fbo, List<Attachment> attachments) {
        this.fbo = fbo;
        this.attachments = attachments;
    }
}
