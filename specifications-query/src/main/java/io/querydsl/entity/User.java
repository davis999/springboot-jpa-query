package io.querydsl.entity;

import lombok.Data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by Davis on 17/1/18.
 */
@Data
@Entity
@Table(name = "d_user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private String name;

  @Column
  private Integer age;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Address> addresses;
}
