package io.querydsl.service;

import com.google.common.collect.Lists;

import io.querydsl.entity.Address;
import io.querydsl.entity.User;
import io.querydsl.repository.UserDAO;
import io.querydsl.util.SearchCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/1/18.
 */
@Service
public class UserService {
  @Autowired
  private transient UserDAO userDAO;

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

    userDAO.save(entity);
    return entity;
  }

  public List<User> searchUsers(List<SearchCriteria> params) {
    return userDAO.searchUser(params);
  }
}
