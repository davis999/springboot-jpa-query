package io.querydsl.repository;

import io.querydsl.entity.User;
import io.querydsl.util.SearchCriteria;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Davis on 17/1/18.
 */
@Repository
@Transactional
public class UserDAO{

  @PersistenceContext
  private EntityManager entityManager;

  public List<User> searchUser(List<SearchCriteria> params) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = builder.createQuery(User.class);
    Root r = query.from(User.class);

    Predicate predicate = builder.conjunction();

    for (SearchCriteria param : params) {
      if (param.getOperation().equalsIgnoreCase(">")) {
        predicate = builder.and(predicate,
            builder.greaterThanOrEqualTo(r.get(param.getKey()),
                param.getValue().toString()));
      } else if (param.getOperation().equalsIgnoreCase("<")) {
        predicate = builder.and(predicate,
            builder.lessThanOrEqualTo(r.get(param.getKey()),
                param.getValue().toString()));
      } else if (param.getOperation().equalsIgnoreCase(":")) {
        if (r.get(param.getKey()).getJavaType() == String.class) {
          predicate = builder.and(predicate,
              builder.like(r.get(param.getKey()),
                  "%" + param.getValue() + "%"));
        } else {
          predicate = builder.and(predicate,
              builder.equal(r.get(param.getKey()), param.getValue()));
        }
      }
    }
    query.where(predicate);

    List<User> result = entityManager.createQuery(query).getResultList();
    return result;
  }

  public void save(User entity) {
    entityManager.persist(entity);
  }
}