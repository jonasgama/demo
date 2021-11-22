package com.example.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "languages")
public class LanguageEntity implements Serializable {

  @Id
  private UUID id;

  @NotEmpty
  private String name;

  @ManyToMany(targetEntity=CountryEntity.class)
  private List<CountryEntity> countries;

  public LanguageEntity() {

  }

  public LanguageEntity(UUID id, String name){
    this.id = id;
    this.name = name;
    countries = new ArrayList<>();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCountry(List<CountryEntity> countries) {
    this.countries = countries;
  }

  public List<CountryEntity> getCountries() {
    return countries;
  }
}
