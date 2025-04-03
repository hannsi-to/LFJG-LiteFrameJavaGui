package me.hannsi.lfjg.render.openGL.gui.items;

import me.hannsi.lfjg.utils.type.types.KeyBindButtonType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Item
public @interface KeyBindButton {
    String name();

    String description() default "";

    KeyBindButtonType type() default KeyBindButtonType.Normal;
}
