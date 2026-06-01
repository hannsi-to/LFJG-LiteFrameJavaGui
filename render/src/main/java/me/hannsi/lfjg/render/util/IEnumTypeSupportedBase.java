package me.hannsi.lfjg.render.util;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static me.hannsi.lfjg.core.Core.*;

public interface IEnumTypeSupportedBase extends IEnumTypeBase {
    boolean isSupported();

    default void throwUnsupportedGPUDriver() {
        throw new UnsupportedGPUDriver(getName() + "is not supported by the GPU you are currently using. Vendor: " + VENDOR + " | GPU: " + RENDERER + " | OpenGL: " + VERSION + " | ShadingLanguageVersion: " + SHADING_LANGUAGE_VERSION);
    }
}
