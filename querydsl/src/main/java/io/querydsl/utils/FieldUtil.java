package io.querydsl.utils;

import java.lang.reflect.Field;

/**
 * Created by Davis on 17/2/3.
 */
public final class FieldUtil {
  public static Field getField(Class clazz, String key) {
    Field result = null;
    try {
      result = clazz.getDeclaredField(key);
    } catch (NoSuchFieldException e) {
      //TODO log and throw exception
    }
    return result;
  }
}
