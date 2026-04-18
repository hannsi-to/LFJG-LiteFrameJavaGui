package me.hannsi.lfjg.testRender.system.shader;

import java.util.HashMap;
import java.util.Map;

public class ShaderPropertyID {
    private static final Map<String, Integer> cache = new HashMap<>();
    private static int counter = 0;

    private ShaderPropertyID() {

    }

    public static int of(String name) {
        return cache.computeIfAbsent(name, UniformBlockValue -> counter++);
    }

    public static String nameOf(int id) {
        return cache.entrySet().stream()
                .filter(e -> e.getValue() == id)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("unknown");
    }
}
