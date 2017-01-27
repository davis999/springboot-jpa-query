package io.querydsl.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Davis on 16/11/16.
 */
@Entity
@Table(name = "common_localized_String")
@Data
@EqualsAndHashCode(callSuper = false)
public class LocalizedStringEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Integer id;

  /**
   * language.
   */
  @Column
  protected String language;

  /**
   * text value.
   */
  @Column(columnDefinition = "text")
  protected String text;

  /**
   * Instantiates a new Localized string entity.
   */
  public LocalizedStringEntity() {
    super();
  }

  /**
   * Instantiates a new Localized string entity.
   *
   * @param language the language
   * @param text     the text
   */
  public LocalizedStringEntity(String language, String text) {
    this.language = language;
    this.text = text;
  }

}
