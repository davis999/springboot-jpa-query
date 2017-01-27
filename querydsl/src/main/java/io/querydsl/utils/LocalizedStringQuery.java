package io.querydsl.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by rai on 17/1/27.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface LocalizedStringQuery {
}
