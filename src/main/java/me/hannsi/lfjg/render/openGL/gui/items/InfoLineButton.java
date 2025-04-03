package me.hannsi.lfjg.render.openGL.gui.items;

import me.hannsi.lfjg.utils.type.types.InfoType;
import me.hannsi.lfjg.utils.type.types.InfoTypeButton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Item
public @interface InfoLineButton {
    String name();

    String description() default "";

    InfoType infoType() default InfoType.Info;

    InfoTypeButton type() default InfoTypeButton.Normal;
}
