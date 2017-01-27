package io.querydsl.utils;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import io.querydsl.entity.LocalizedStringEntity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hibernate.jpa.criteria.ValueHandlerFactory.isNumeric;

/**
 * Created by rai on 17/1/26.
 */
public class PredicateUtil {

  public static BooleanExpression toPredicate(PathBuilder pathBuilder,
                                              String queryConditions,
                                              Class clazz) {
    BooleanExpression result = null;

    String[] whereConditions = queryConditions.split(" and | or ");

    List<QueryCriterias> queryCriterias = CriteriasBuilder.build(whereConditions);

    List<BooleanExpression> expressions = queryCriterias.stream().map(
        q -> {
          return toPredicate(pathBuilder, q, clazz);
        }
    ).collect(Collectors.toList());

    switch (getConditionFlag(queryConditions)) {
      case 0:
        result = expressions.get(0);
        break;
      case 1:
        result = expressions.get(0);
        for (int i = 1; i < expressions.size(); i++) {
          result = result.and(expressions.get(i));
        }
        break;
      case 2:
        result = expressions.get(0);
        for (int i = 1; i < expressions.size(); i++) {
          result = result.or(expressions.get(i));
        }
        break;
      default:
        break;
    }

    return result;
  }

  private static int getConditionFlag(String queryConditions) {
    int result = 0;
    if (queryConditions.contains(" and ")) {
      result = 1;
    } else if (queryConditions.contains(" or ")) {
      result = 2;
    }
    return result;
  }

  private static BooleanExpression toPredicate(PathBuilder pathBuilder,
                                               QueryCriterias queryCriterias,
                                               Class clazz) {
    BooleanExpression result = null;
    if (queryCriterias.isMultiQueryCriterias()) {
      String key = queryCriterias.getCurrentKey();
      if (isLocalizedStringKey(key, clazz)) {
        QueryCriteria queryCriteria = QueryCriteria.ofLocalizedStringCriteria(queryCriterias);
        PathBuilder lsPathBuilder = (PathBuilder) pathBuilder.getSet(key, LocalizedStringEntity.class).any();
        result = toLocalizedStringPredicate(lsPathBuilder, queryCriteria);
      } else {
        PathBuilder subPath = pathBuilder.get(key);
        QueryCriterias subQueryCriterias = queryCriterias.getSubQueryCriterias();
        result = toPredicate(subPath, subQueryCriterias, clazz);
      }
    } else {
      QueryCriteria queryCriteria = QueryCriteria.ofQueryCriterias(queryCriterias);
      result = toParamsPredicate(pathBuilder, queryCriteria);
    }

    return result;
  }

  private static BooleanExpression toLocalizedStringPredicate(PathBuilder pathBuilder,
                                                              QueryCriteria criteria) {

    StringPath lPath = pathBuilder.getString("language");
    StringPath kPath = pathBuilder.getString("text");

    BooleanExpression lExpression = lPath.eq(criteria.getKey());
    BooleanExpression kExpression = null;

    switch (criteria.getOperation()) {
      case "=":
        kExpression = kPath.eq(criteria.getValue().toString());
        break;
      case "like":
        kExpression = kPath.like(criteria.getKey().toString());
        break;
      default:
        break;
    }
    return lExpression.and(kExpression);
  }

  private static BooleanExpression toParamsPredicate(PathBuilder pathBuilder,
                                                     QueryCriteria criteria) {
    BooleanExpression result = null;
    if (isNumeric(criteria.getValue().toString())) {
      NumberPath<Integer> path = pathBuilder.getNumber(criteria.getKey(), Integer.class);
      int value = Integer.parseInt(criteria.getValue().toString());
      if (criteria.getOperation().equalsIgnoreCase("=")) {
        result = path.eq(value);
      } else if (criteria.getOperation().equalsIgnoreCase(">")) {
        result = path.goe(value);
      } else if (criteria.getOperation().equalsIgnoreCase("<")) {
        result = path.loe(value);
      }
    } else {
      StringPath path = pathBuilder.getString(criteria.getKey());
      if (criteria.getOperation().equalsIgnoreCase(":")) {
        result = path.containsIgnoreCase(criteria.getValue().toString());
      }
    }
    return result;
  }

  private static boolean isLocalizedStringKey(String key, Class clazz) {
    boolean result = false;
    Field keyField = null;
    try {
      keyField = clazz.getDeclaredField(key);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }

    List annotationList = Lists.newArrayList(keyField.getDeclaredAnnotations());

    Predicate<Object> predicate = o -> o instanceof LocalizedStringQuery;

    result = annotationList.stream().anyMatch(predicate);

    return result;
  }
}
