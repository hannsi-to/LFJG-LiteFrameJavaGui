package me.hannsi.lfjg.frame;

import me.hannsi.lfjg.core.Service;
import me.hannsi.lfjg.core.ServiceData;

public class Frame3Core implements Service {

    @Override
    public ServiceData execute() {
        return new ServiceData(true, "Frame3", "v1.0.0");
    }
}
