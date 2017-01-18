package io.querydsl.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Davis on 17/1/18.
 */
@Data
@Entity
@Table(name = "d_address")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private String city;

  @Column
  private String streetName;

  @Column
  private String postalCode;
}
