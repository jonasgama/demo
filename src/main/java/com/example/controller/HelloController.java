package com.example.controller;

import com.example.service.HelloService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;

@Controller("${hello.controller.path}")
public class HelloController {


    @Inject
    private HelloService service;

    @Get("/{language}")
    public HttpResponse<? extends Object> hello(@PathVariable String language){
        String greeting = service.greeting(language);
        if (greeting.isBlank() || greeting.isEmpty()){
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(greeting);
    }
}
