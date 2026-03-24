package me.hannsi.lfjg.render;

import me.hannsi.lfjg.render.system.video.AssetVideoLoader;

import static me.hannsi.lfjg.core.Core.ASSET_MANAGER;

public class LFJGRenderVideoContext {
    public static void init() {
        ASSET_MANAGER.registerLoader(new AssetVideoLoader());
    }
}
