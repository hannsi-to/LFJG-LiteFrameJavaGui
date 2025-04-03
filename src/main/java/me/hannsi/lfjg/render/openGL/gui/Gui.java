package me.hannsi.lfjg.render.openGL.gui;

import me.hannsi.lfjg.render.openGL.gui.items.Item;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gui {
    private final List<Field> itemFields;

    public Gui() {
        itemFields = new ArrayList<>();
    }

    public void registerItem(Class<?> clazz) {
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            boolean isItemField = false;
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof Item) {
                    isItemField = true;
                    break;
                }
            }

            if (isItemField) {
                itemFields.add(field);
            }
        });
    }
}
