package me.hannsi.lfjg.frame.setting.system;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify the reflection level of a frame setting.
 * This annotation is used to mark classes with a specific reflection level.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ReflectionsLevel {
    /**
     * Returns the reflection level.
     *
     * @return the reflection level
     */
    int level();
}