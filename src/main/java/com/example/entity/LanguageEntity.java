package com.example.entity;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "languages")
public class LanguageEntity {

  @Id
  private UUID id;

  @NotEmpty
  private String name;

  public LanguageEntity() {

  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LanguageEntity(UUID id, String name){
    this.id = id;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "LanguageEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
