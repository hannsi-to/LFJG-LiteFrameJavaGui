package me.hannsi.lfjg.render.openGL.gui;

import me.hannsi.lfjg.utils.type.types.InfoType;
import me.hannsi.lfjg.utils.type.types.InfoTypeButton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InfoLineButton {
    String name();

    String description();

    InfoType infoType();

    InfoTypeButton type();
}
