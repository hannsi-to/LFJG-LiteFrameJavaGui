package me.hannsi.lfjg.core;

import java.util.Objects;

public record ServiceData(boolean canUsed, String serviceName, String serviceVersion, Object... otherData) {
    @Override
    public String toString() {
        return "ServiceName: " + serviceName + " | Version: " + serviceVersion + " | State: " + canUsed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        ServiceData that = (ServiceData) obj;
        return this.canUsed == that.canUsed && Objects.equals(this.serviceName, that.serviceName) && Objects.equals(this.serviceVersion, that.serviceVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canUsed, serviceName, serviceVersion);
    }
}