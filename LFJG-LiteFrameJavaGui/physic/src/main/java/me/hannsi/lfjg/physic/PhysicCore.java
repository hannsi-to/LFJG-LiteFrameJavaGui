package me.hannsi.lfjg.physic;

import me.hannsi.lfjg.core.Service;
import me.hannsi.lfjg.core.ServiceData;

public class PhysicCore implements Service {
    @Override
    public ServiceData execute() {
        return new ServiceData(true, "Physic", "v1.0.0");
    }
}
