package com.example.entity;

import org.hibernate.annotations.Columns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Table(name = "countries")
@Entity
public class CountryEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="countries_id_seq")
  @SequenceGenerator(name = "countries_id_seq", allocationSize = 1)
  private Long id;

  private String name;

  @ManyToMany(targetEntity = LanguageEntity.class, mappedBy="spokenCountries")
  @Column(table = "languages_countries", name="used_languages")
  private List<LanguageEntity> usedLanguages;


  public CountryEntity(String name){
    this.name = name;
    this.usedLanguages = new ArrayList<>();
  }

  public CountryEntity() {
    this.usedLanguages = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<LanguageEntity> getUsedLanguages() {
    return usedLanguages;
  }

  public void setUsedLanguages(List<LanguageEntity> usedLanguages) {
    this.usedLanguages = usedLanguages;
  }

  public void addUsedLanguages(List<LanguageEntity> languageEntities) {
    languageEntities.forEach(usedLanguages::add);
  }
}