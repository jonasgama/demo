package com.example.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/hello")
public class HelloController {

    @Get
    public String hello(){
        return "Hello World";
    }
}
