package io.querydsl.util;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davis on 17/1/18.
 */
public class UserPredicatesBuilder {
  private List<SearchCriteria> params;

  public UserPredicatesBuilder() {
    params = new ArrayList<SearchCriteria>();
  }

  public UserPredicatesBuilder with(SearchCriteria criteria) {
    params.add(criteria);
    return this;
  }

  public BooleanExpression build() {
    if (params.size() == 0) {
      return null;
    }

    List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
    UserPredicate predicate;
    for (SearchCriteria param : params) {
      predicate = new UserPredicate(param);
      BooleanExpression exp = predicate.getPredicate();
      if (exp != null) {
        predicates.add(exp);
      }
    }

    BooleanExpression result = predicates.get(0);
    for (int i = 1; i < predicates.size(); i++) {
      result = result.and(predicates.get(i));
    }
    return result;
  }
}
