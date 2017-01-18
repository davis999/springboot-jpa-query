package io.querydsl.service;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;

import io.querydsl.entity.Address;
import io.querydsl.entity.User;
import io.querydsl.repository.UserRepository;
import io.querydsl.util.SearchCriteria;
import io.querydsl.util.UserPredicatesBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/1/18.
 */
@Service
public class UserService {
  @Autowired
  private transient UserRepository userRepository;

  public User createUser() {
    User entity = new User();

    entity.setName("davis");
    entity.setAge(12);
    Address address = new Address();
    address.setCity("GZ");
    address.setStreetName("SYL");
    address.setPostalCode("510000");
    List<Address> addresses = Lists.newArrayList(address);
    entity.setAddresses(addresses);

    userRepository.save(entity);
    return entity;
  }

  public List<User> searchUsers(List<SearchCriteria> params) {
    BooleanExpression exp = buildPredicate(params);
    return Lists.newArrayList(userRepository.findAll(exp));
  }

  private BooleanExpression buildPredicate(List<SearchCriteria> params) {
    UserPredicatesBuilder builder = new UserPredicatesBuilder();
    params.parallelStream().forEach(
        p -> {
          builder.with(p);
        }
    );
    BooleanExpression exp = builder.build();
    return exp;
  }
}
