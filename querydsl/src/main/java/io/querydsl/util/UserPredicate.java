package io.querydsl.util;

import static org.hibernate.jpa.criteria.ValueHandlerFactory.isNumeric;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

import io.querydsl.entity.User;

/**
 * Created by Davis on 17/1/18.
 */
public class UserPredicate {
  private SearchCriteria criteria;

  public UserPredicate(SearchCriteria criteria) {
    this.criteria = criteria;
  }

  public BooleanExpression getPredicate() {
    PathBuilder<User> entityPath = new PathBuilder<User>(User.class, "user");

    if (isNumeric(criteria.getValue().toString())) {
      NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
      int value = Integer.parseInt(criteria.getValue().toString());
      if (criteria.getOperation().equalsIgnoreCase(":")) {
        return path.eq(value);
      }
      else if (criteria.getOperation().equalsIgnoreCase(">")) {
        return path.goe(value);
      }
      else if (criteria.getOperation().equalsIgnoreCase("<")) {
        return path.loe(value);
      }
    }
    else {
      StringPath path = entityPath.getString(criteria.getKey());
      if (criteria.getOperation().equalsIgnoreCase(":")) {
        return path.containsIgnoreCase(criteria.getValue().toString());
      }
    }
    return null;
  }
}
