package io.querydsl.service;

import com.google.common.collect.Lists;

import io.querydsl.entity.Address;
import io.querydsl.entity.User;
import io.querydsl.repository.UserRepository;
import io.querydsl.specification.UserSpecification;
import io.querydsl.util.SearchCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
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
    Specifications specifications = getSpec(params);
    return userRepository.findAll(specifications);
  }

  private Specifications getSpec(List<SearchCriteria> params) {
    Specifications specifications = Specifications.where(new UserSpecification(params.get(0)));

    if (params.size() > 1) {
      for (int i = 1; i < params.size(); i ++) {
        specifications = specifications.and(new UserSpecification(params.get(i)));
      }
    }
    return specifications;
  }
}
