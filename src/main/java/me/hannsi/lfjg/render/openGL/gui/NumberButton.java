package me.hannsi.lfjg.render.openGL.gui;

import me.hannsi.lfjg.utils.type.types.NumberButtonType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NumberButton {
    String name();

    String description();

    float min();

    float max();

    float step();

    NumberButtonType type();
}
