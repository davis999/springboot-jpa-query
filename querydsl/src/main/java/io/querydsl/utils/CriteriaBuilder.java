package io.querydsl.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by rai on 17/1/27.
 */
public class CriteriaBuilder {
  private static QueryCriteria build(String queryCondition) {
    List<String> conditions = ConditionsUtil.splitCondition(queryCondition);

    QueryCriteria result = new QueryCriteria();

    int length = conditions.size();

    result.setValue(conditions.get(length - 1));
    result.setOperation(conditions.get(length - 2));
    result.setKey(conditions.subList(0, length - 2));

    return result;
  }

  public static List<QueryCriteria> build(String[] queryConditions) {
    return Stream.of(queryConditions).map(
        q -> {
          return build(q);
        }
    ).collect(Collectors.toList());
  }
}
