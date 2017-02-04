package io.querydsl.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

import io.querydsl.entity.Address;
import io.querydsl.entity.LocalizedStringEntity;
import io.querydsl.entity.User;
import io.querydsl.repository.UserRepository;
import io.querydsl.utils.PredicateBuilder;
import io.querydsl.utils.SortBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    LocalizedStringEntity zhName = new LocalizedStringEntity("zh", "戴维斯");
    Set<LocalizedStringEntity> name = Sets.newHashSet(enName, zhName);

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

  public List<User> queryUserByCriteria(String queryConditions) {

    PathBuilder<User> entityPath = new PathBuilder<User>(User.class, "user");

    long start = System.currentTimeMillis();
    BooleanExpression expression = PredicateBuilder.toPredicate(entityPath, queryConditions, User
        .class);
    long end = System.currentTimeMillis();

    System.out.println("build expression time : " + (end - start));

    Sort sort = SortBuilder.buildSortCriteria("");

    PageRequest pageable = new PageRequest(0, 200, sort);

    Iterable<User> result = userRepository.findAll(expression, pageable);
    return Lists.newArrayList(result);
  }
}
