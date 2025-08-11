package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.Service;
import me.hannsi.lfjg.core.ServiceData;

public class AudioCore implements Service {
    @Override
    public ServiceData execute() {
        return new ServiceData(true, "Audio", "v1.0.0");
    }
}
