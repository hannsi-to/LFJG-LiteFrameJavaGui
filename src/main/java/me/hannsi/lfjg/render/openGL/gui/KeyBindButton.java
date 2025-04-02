package me.hannsi.lfjg.render.openGL.gui;

import me.hannsi.lfjg.utils.type.types.KeyBindButtonType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KeyBindButton {
    String name();

    String description();

    KeyBindButtonType type();
}
