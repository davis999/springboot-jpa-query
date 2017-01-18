package io.querydsl.controller;

import io.querydsl.entity.User;
import io.querydsl.service.UserService;
import io.querydsl.util.SearchCriteria;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Davis on 17/1/18.
 */
@RestController
public class UserController {
  @Autowired
  private transient UserService userService;

  @ApiOperation("create user")
  @PostMapping("/users")
  public User createUser() {
    return userService.createUser();
  }

  @GetMapping("/users")
  public List<User> findAll(@RequestParam(value = "search", required = false)
                            @ApiParam(value = "query conditions", required = true)
                                String query) {
    List<SearchCriteria> params = new ArrayList<SearchCriteria>();
    if (query != null) {
      Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
      Matcher matcher = pattern.matcher(query + ",");

      while (matcher.find()) {
        params.add(new SearchCriteria(matcher.group(1),
            matcher.group(2), matcher.group(3)));
      }
    }
    return userService.searchUsers(params);
  }
}
