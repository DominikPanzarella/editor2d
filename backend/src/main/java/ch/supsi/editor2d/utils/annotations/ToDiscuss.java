package ch.supsi.editor2d.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to mark parts of the code that need further discussion.
 * Store additional information:
 *      - author
 *      - date
 *      - description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface ToDiscuss {
    String author() default "Unknown";
    String date() default "";
    String description() default "";

}
