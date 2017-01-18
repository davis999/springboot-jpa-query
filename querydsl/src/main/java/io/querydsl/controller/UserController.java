package io.querydsl.controller;

import io.querydsl.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Davis on 17/1/18.
 */
@RestController
public class UserController {
  @Autowired
  private transient UserService userService;


}
