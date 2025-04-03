package me.hannsi.lfjg.render.openGL.gui.items;

import me.hannsi.lfjg.utils.type.types.TextButtonType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Item
public @interface TextButton {
    String name();

    String description() default "";

    String placeholder() default "";

    boolean secure() default false;

    boolean multiline() default false;

    TextButtonType type() default TextButtonType.Normal;
}
