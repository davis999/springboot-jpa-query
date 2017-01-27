package io.querydsl.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by rai on 17/1/27.
 */
public class CriteriasBuilder {
  private static QueryCriterias build(String queryCondition) {
    List<String> criterias = ConditionsUtil.splitCondition(queryCondition);

    QueryCriterias result = new QueryCriterias();

    int length = criterias.size();

    result.setValue(criterias.get(length - 1));
    result.setOperation(criterias.get(length - 2));
    result.setKey(criterias.subList(0, length - 2));

    return result;
  }

  public static List<QueryCriterias> build(String[] queryConditions) {
    return Stream.of(queryConditions).map(
        q -> {
          return build(q);
        }
    ).collect(Collectors.toList());
  }
}
