  package com.example.dto;

  import com.example.entity.CountryEntity;
  import io.swagger.v3.oas.annotations.media.Schema;
  import java.util.ArrayList;
  import java.util.List;

  @Schema(name="Language",description = "represents the language dto")
  public class LanguageDTO {


    @Schema(description = "language name", minLength = 2, maxLength = 20)
    public String language;

    public List<String> countries;

    public LanguageDTO(){}

    public LanguageDTO(String language){

      this.countries = new ArrayList<>();
      this.language = language;
    }

    public void addCountries(List<CountryEntity> entities){
      entities.forEach(countryEntity -> countries.add(countryEntity.getName()));
    }

    public List<String> getCountries() {
      return countries;
    }

    public void setCountries(List<String> countries) {
      this.countries = countries;
    }

  }