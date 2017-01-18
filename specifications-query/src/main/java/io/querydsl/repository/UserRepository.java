package io.querydsl.repository;

import io.querydsl.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Davis on 17/1/18.
 */
public interface UserRepository
    extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}