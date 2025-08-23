package me.hannsi.lfjg.frame;

import me.hannsi.lfjg.core.Service;
import me.hannsi.lfjg.core.ServiceData;

public class FrameCore implements Service {

    @Override
    public ServiceData execute() {
        return new ServiceData(true, "Frame", "v1.0.0");
    }
}
