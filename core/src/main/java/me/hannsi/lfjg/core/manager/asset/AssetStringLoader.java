package me.hannsi.lfjg.core.manager.asset;

import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

public class AssetStringLoader implements AssetLoader<String> {
    @Override
    public String load(Location location) throws AssetLoadException {
        try {
            return IOUtil.readInputStreamToString(location.openStream());
        } catch (Exception e) {
            throw new AssetLoadException(location, e);
        }
    }

    @Override
    public Class<String> getAssetType() {
        return String.class;
    }
}
