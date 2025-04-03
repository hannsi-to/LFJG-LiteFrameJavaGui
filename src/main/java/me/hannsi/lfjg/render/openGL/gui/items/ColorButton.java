package me.hannsi.lfjg.render.openGL.gui.items;

import me.hannsi.lfjg.utils.type.types.ColorButtonType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Item
public @interface ColorButton {
    String name();

    String description() default "";

    ColorButtonType type() default ColorButtonType.Normal;
}
