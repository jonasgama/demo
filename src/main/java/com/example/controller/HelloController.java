package com.example.controller;

import com.example.service.HelloService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;

@Controller("${hello.controller.path}")
public class HelloController {


    @Inject
    private HelloService service;

    @Get("/{language}")
    public String hello(@PathVariable String language){
        return service.greeting(language);
    }

}
