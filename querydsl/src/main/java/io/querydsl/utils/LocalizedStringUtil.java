package io.querydsl.utils;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Davis on 17/2/3.
 */
public final class LocalizedStringUtil {
  public static boolean isLocalizedStringKey(String key, Class clazz) {
    boolean result = false;
    Field keyField = FieldUtil.getField(clazz, key);

    List annotationList = Lists.newArrayList(keyField.getDeclaredAnnotations());

    Predicate<Object> predicate = o -> o instanceof LocalizedStringQuery;

    result = annotationList.stream().anyMatch(predicate);

    return result;
  }
}
