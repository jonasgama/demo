package com.example.controller;

import com.example.service.HelloService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;

@Secured(SecurityRule.IS_ANONYMOUS)
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

    @Get("/stub")
    public Greeting greeting(){
        return new Greeting();
    }

    public class Greeting{
        public String textMessage = "STUBBED MESSAGE";
        public BigDecimal randomNumber = BigDecimal.valueOf(29312093);
        public Instant timeUtc  = Instant.now();
    }
}
