package io.querydsl.utils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

import io.querydsl.entity.LocalizedStringEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rai on 17/1/26.
 */
public class PredicateBuilder {

  public static BooleanExpression toPredicate(PathBuilder pathBuilder,
                                              String queryConditions,
                                              Class clazz) {
    BooleanExpression result = null;

    String[] whereConditions = queryConditions.split(" and | or ");

    List<QueryCriteria> queryCriteria = CriteriaBuilder.build(whereConditions);

    List<BooleanExpression> expressions = queryCriteria.stream().map(
        q -> {
          return toPredicate(pathBuilder, q, clazz);
        }
    ).collect(Collectors.toList());

    result = combineExpressions(queryConditions, expressions);

    return result;
  }

  private static BooleanExpression toPredicate(PathBuilder pathBuilder,
                                               QueryCriteria queryCriteria,
                                               Class clazz) {
    BooleanExpression result = null;
    if (queryCriteria.isMultiQueryCriteria()) {
      String key = queryCriteria.getCurrentKey();
      if (LocalizedStringUtil.isLocalizedStringKey(key, clazz)) {
        result = getLocalizedStringExpression(pathBuilder, queryCriteria, key);
      } else {
        result = getSubPathExpression(pathBuilder, queryCriteria, clazz, key);
      }
    } else {
      result = getParamsExpression(pathBuilder, queryCriteria, clazz);
    }

    return result;
  }

  private static BooleanExpression combineExpressions(String queryConditions,
                                                      List<BooleanExpression> expressions) {
    BooleanExpression result = null;
    switch (ConditionsUtil.getConditionFlag(queryConditions)) {
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

  private static BooleanExpression getSubPathExpression(PathBuilder pathBuilder, QueryCriteria
      queryCriteria, Class clazz, String key) {
    BooleanExpression result;
    PathBuilder subPath = pathBuilder.get(key);
    QueryCriteria subQueryCriteria = queryCriteria.getSubQueryCriteria();
    Class subClass = FieldUtil.getField(clazz, key).getType();
    result = toPredicate(subPath, subQueryCriteria, subClass);
    return result;
  }

  private static BooleanExpression getLocalizedStringExpression(PathBuilder pathBuilder,
                                                                QueryCriteria queryCriteria,
                                                                String key) {
    BooleanExpression result;
    QueryCriterion criteria = QueryCriterion.ofLocalizedStringCriteria(queryCriteria);
    PathBuilder lsPathBuilder = (PathBuilder) pathBuilder.getSet(key, LocalizedStringEntity
        .class).any();
    result = PredicateUtil.toLocalizedStringPredicate(lsPathBuilder, criteria);
    return result;
  }

  private static BooleanExpression getParamsExpression(PathBuilder pathBuilder, QueryCriteria
      queryCriteria, Class clazz) {
    BooleanExpression result;
    QueryCriterion queryCriterion = QueryCriterion.ofQueryCriteria(queryCriteria);
    result = PredicateUtil.toParamsPredicate(pathBuilder, queryCriterion, clazz);
    return result;
  }
}
