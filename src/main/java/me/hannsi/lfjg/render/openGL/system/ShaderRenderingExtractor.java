package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.utils.type.types.ShaderRenderingType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderRenderingExtractor {
    public static final Map<String, Integer> map = new HashMap<>();

    static {
        for (ShaderRenderingType shaderRenderingType : ShaderRenderingType.values()) {
            map.put(shaderRenderingType.getName(), shaderRenderingType.getId());
        }
    }

    public static List<Integer> getAlignmentsAsInteger(int align) {
        List<String> alignmentsString = getAlignmentsAsList(align);

        List<Integer> alignmentsInteger = new ArrayList<>();
        for (String alignment : alignmentsString) {
            alignmentsInteger.add(getAlignFromString(alignment));
        }

        return alignmentsInteger;
    }

    public static List<String> getAlignmentsAsList(int align) {
        List<String> alignments = new ArrayList<>();

        if ((align & ShaderRenderingType.SHADER_COLOR.getId()) != 0) {
            alignments.add("SHADER_COLOR");
        }
        if ((align & ShaderRenderingType.SHADER_TEXTURE.getId()) != 0) {
            alignments.add("SHADER_TEXTURE");
        }

        return alignments;
    }

    public static int getAlignFromString(String align) {
        return map.get(align);
    }
}
