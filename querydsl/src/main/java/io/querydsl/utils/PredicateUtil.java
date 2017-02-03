package io.querydsl.utils;

import static org.hibernate.jpa.criteria.ValueHandlerFactory.isNumeric;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

import io.querydsl.entity.LocalizedStringEntity;

import java.lang.reflect.Field;

/**
 * Created by Davis on 17/2/3.
 */
public final class PredicateUtil {
  public static BooleanExpression toLocalizedStringPredicate(PathBuilder pathBuilder,
                                                             QueryCriterion criteria) {

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

  public static BooleanExpression toParamsPredicate(PathBuilder pathBuilder,
                                                    QueryCriterion criteria,
                                                    Class clazz) {
    BooleanExpression result = null;

    Field keyField = FieldUtil.getField(clazz, criteria.getKey());

    if (isNumeric(keyField.getType())) {
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
      if (criteria.getOperation().equalsIgnoreCase("=")) {
        result = path.containsIgnoreCase(criteria.getValue().toString());
      }
    }
    return result;
  }
}
