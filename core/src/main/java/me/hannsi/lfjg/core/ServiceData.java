package me.hannsi.lfjg.core;

import java.util.Objects;

public final class ServiceData {
    private final boolean canUsed;
    private final String serviceName;
    private final String serviceVersion;

    public ServiceData(boolean canUsed, String serviceName, String serviceVersion) {
        this.canUsed = canUsed;
        this.serviceName = serviceName;
        this.serviceVersion = serviceVersion;
    }

    @Override
    public String toString() {
        return "ServiceName: " + serviceName + " | Version: " + serviceVersion + " | State: " + canUsed;
    }

    public boolean canUsed() {
        return canUsed;
    }

    public String serviceName() {
        return serviceName;
    }

    public String serviceVersion() {
        return serviceVersion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        ServiceData that = (ServiceData) obj;
        return this.canUsed == that.canUsed &&
                Objects.equals(this.serviceName, that.serviceName) &&
                Objects.equals(this.serviceVersion, that.serviceVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canUsed, serviceName, serviceVersion);
    }

}
