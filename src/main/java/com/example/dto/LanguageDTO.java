package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="Language",description = "represents the language dto")
public class LanguageDTO {


  @Schema(description = "language name", minLength = 2, maxLength = 20)
  public String language;

  public LanguageDTO(){}

  public LanguageDTO(String language){
    this.language = language;
  }
}