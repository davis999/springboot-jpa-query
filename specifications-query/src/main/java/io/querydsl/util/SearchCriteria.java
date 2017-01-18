package io.querydsl.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Davis on 17/1/18.
 */
@Data
@AllArgsConstructor
public class SearchCriteria {
  private String key;
  private String operation;
  private Object value;
}