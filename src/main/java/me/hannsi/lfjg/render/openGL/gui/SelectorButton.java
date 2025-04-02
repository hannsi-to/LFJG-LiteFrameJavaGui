package me.hannsi.lfjg.render.openGL.gui;

import me.hannsi.lfjg.utils.type.types.SelectorButtonType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SelectorButton {
    String name();

    String description();

    String[] optionList();

    SelectorButtonType type();
}
