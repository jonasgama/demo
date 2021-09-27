package com.example.controller;


import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import java.security.Principal;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/security")
public class AuthController {

  @Get("/attributes")
  public Object attributes (Principal principal) {
    Authentication details = (Authentication) principal;
    return details.getAttributes();
  }

  @Get("/name")
  public Object name (Principal principal) {
    Authentication details = (Authentication) principal;
    return details.getName();
  }

  @Get("/roles")
  public Object roles (Principal principal) {
    Authentication details = (Authentication) principal;
  return  details.getRoles();
  }


  @Get
  public Object details (Principal principal) {
    Authentication details = (Authentication) principal;
    return  details;
  }

}
