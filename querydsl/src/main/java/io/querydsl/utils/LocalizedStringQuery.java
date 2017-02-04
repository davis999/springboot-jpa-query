package io.querydsl.utils;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by rai on 17/1/27.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface LocalizedStringQuery {
}
