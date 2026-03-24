package me.hannsi.lfjg.render.system.video;

import me.hannsi.lfjg.core.manager.asset.AssetLoadException;
import me.hannsi.lfjg.core.manager.asset.AssetLoader;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

public class AssetVideoLoader implements AssetLoader<VideoDecoder> {
    @Override
    public VideoDecoder load(Location location) throws AssetLoadException {
        try {
            return new VideoDecoder().open(location);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<VideoDecoder> getAssetType() {
        return VideoDecoder.class;
    }
}
