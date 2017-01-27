package io.querydsl.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import io.querydsl.entity.Address;
import io.querydsl.entity.LocalizedStringEntity;
import io.querydsl.entity.User;
import io.querydsl.repository.UserRepository;
import io.querydsl.utils.PredicateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Davis on 17/1/18.
 */
@Service
public class UserService {
  @Autowired
  private transient UserRepository userRepository;

  public User createUser() {
    User entity = new User();

    Random random = new Random();
    LocalizedStringEntity enName = new LocalizedStringEntity("en", "davis" + random.nextInt());
    Set<LocalizedStringEntity> name = Sets.newHashSet(enName);

    entity.setName(name);
    entity.setAge(random.nextInt(128));
    Address address = new Address();
    address.setCity("GZ");
    address.setStreetName("SYL");
    address.setPostalCode("510000");
    List<Address> addresses = Lists.newArrayList(address);
    entity.setAddresses(addresses);
    entity.setDefaultAddress(address);

    userRepository.save(entity);
    return entity;
  }

  public List<User> queryUserByCriterias(String queryConditions) {

    PathBuilder<User> entityPath = new PathBuilder<User>(User.class, "user");

    long start = System.currentTimeMillis();
    BooleanExpression expression = PredicateUtil.toPredicate(entityPath, queryConditions, User.class);
    long end = System.currentTimeMillis();

    System.out.println("build expression time : " + (end - start));

    Iterable<User> result = userRepository.findAll(expression);
    return Lists.newArrayList(result);
  }
}
