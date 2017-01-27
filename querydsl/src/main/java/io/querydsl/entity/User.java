package io.querydsl.entity;

import io.querydsl.utils.LocalizedStringQuery;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

  @LocalizedStringQuery
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<LocalizedStringEntity> name;

  @Column
  private Integer age;

  @OneToOne
  private Address defaultAddress;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Address> addresses;
}
