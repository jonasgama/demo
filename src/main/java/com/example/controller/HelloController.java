package com.example.controller;

import com.example.service.HelloService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller("${hello.controller.path}")
public class HelloController {


    @Inject
    private HelloService service;

    @Get
    public String hello(){
        return service.greeting();
    }

    @Get("/pt")
    public String pt(){
        return service.greetingFromPt();
    }

    @Get("/jp")
    public String jp(){
        return service.greetingFromJp();
    }
}
