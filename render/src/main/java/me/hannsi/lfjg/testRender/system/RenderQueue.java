package me.hannsi.lfjg.testRender.system;

import static me.hannsi.lfjg.testRender.RenderSystemSetting.*;

public class RenderQueue {
    public static final int BACKGROUND = RENDER_QUEUE_BACKGROUND;
    public static final int GEOMETRY = RENDER_QUEUE_GEOMETRY;
    public static final int ALPHA_TEST = RENDER_QUEUE_ALPHA_TEST;
    public static final int TRANSPARENT = RENDER_QUEUE_TRANSPARENT;
    public static final int OVERLAY = RENDER_QUEUE_OVERLAY;

    private RenderQueue() {
    }
}
