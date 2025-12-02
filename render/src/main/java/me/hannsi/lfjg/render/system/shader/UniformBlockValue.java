package me.hannsi.lfjg.render.system.shader;

import java.util.Arrays;
import java.util.List;

public class UniformBlockValue {
    public final List<Object[]> values;
    public final UploadUniformType uploadUniformType;
    private int hashCache;

    public UniformBlockValue(UploadUniformType uploadUniformType, Object[]... values) {
        this.values = Arrays.asList(values);
        this.uploadUniformType = uploadUniformType;
    }

    public boolean equalsValues(Object[]... newValues) {
        int newHash = Arrays.deepHashCode(newValues);

        if (hashCache == newHash) {
            return true;
        }

        if (!realDeepEquals(newValues)) {
            return false;
        }

        hashCache = newHash;
        return true;
    }

    private boolean realDeepEquals(Object[][] newValues) {
        if (values.size() != newValues.length) {
            return false;
        }

        for (int i = 0; i < newValues.length; i++) {
            if (!Arrays.equals(values.get(i), newValues[i])) {
                return false;
            }
        }

        return true;
    }
}