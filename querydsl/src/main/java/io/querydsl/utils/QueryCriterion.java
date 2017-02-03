package io.querydsl.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Davis on 17/1/18.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueryCriterion {
  private String key;
  private String operation;
  private Object value;

  public static QueryCriterion ofQueryCriteria(QueryCriteria queryCriteria) {
    QueryCriterion result = new QueryCriterion();

    result.key = queryCriteria.getCurrentKey();
    result.operation = queryCriteria.getOperation();
    result.value = queryCriteria.getValue();

    return result;
  }

  public static QueryCriterion ofLocalizedStringCriteria(QueryCriteria queryCriteria) {
    QueryCriterion result = new QueryCriterion();
    result.key = queryCriteria.getKey().get(1);
    result.operation = queryCriteria.getOperation();
    result.value = queryCriteria.getValue();
    return result;
  }
}