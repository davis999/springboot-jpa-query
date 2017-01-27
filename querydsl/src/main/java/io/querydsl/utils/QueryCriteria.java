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
public class QueryCriteria {
  private String key;
  private String operation;
  private Object value;

  public static QueryCriteria ofQueryCriterias(QueryCriterias queryCriterias) {
    QueryCriteria result = new QueryCriteria();

    result.key = queryCriterias.getCurrentKey();
    result.operation = queryCriterias.getOperation();
    result.value = queryCriterias.getValue();

    return result;
  }

  public static QueryCriteria ofLocalizedStringCriteria(QueryCriterias queryCriterias) {
    QueryCriteria result = new QueryCriteria();
    result.key = queryCriterias.getKey().get(1);
    result.operation = queryCriterias.getOperation();
    result.value = queryCriterias.getValue();
    return result;
  }
}