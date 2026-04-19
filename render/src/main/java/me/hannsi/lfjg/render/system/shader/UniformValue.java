package me.hannsi.lfjg.render.system.shader;

import java.util.Objects;

public class UniformValue {
    public Object[] values;
    public UploadUniformType uploadUniformType;

    public UniformValue(UploadUniformType uploadUniformType, Object... values) {
        this.values = values;
        this.uploadUniformType = uploadUniformType;
    }

    public boolean equalsValues(Object... newValues) {
        if (values == null || newValues == null || values.length != newValues.length) {
            return false;
        }

        for (int i = 0; i < values.length; i++) {
            if (!Objects.equals(values[i], newValues[i])) {
                return false;
            }
        }
        return true;
    }
}
