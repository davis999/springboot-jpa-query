package io.querydsl.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Davis on 17/1/18.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueryCriterias {
  private List<String> key;
  private String operation;
  private String value;

  public QueryCriterias getSubQueryCriterias() {
    QueryCriterias result = new QueryCriterias();
    if (key.size() > 1) {
      result.setKey(key.subList(1, key.size()));
    }
    result.setOperation(operation);
    result.setValue(value);
    return result;
  }

  public boolean isMultiQueryCriterias() {
    return key.size() > 1;
  }

  public String getCurrentKey() {
    return key.get(0);
  }
}