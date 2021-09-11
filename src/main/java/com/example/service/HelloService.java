package com.example.service;

import com.example.config.ConfigProps;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class HelloService {

    @Property(name="hello.service.greeting", defaultValue = "Hello from service")
    private String greeting;

    @Inject
    private ConfigProps config;

    public String greeting(){
        return greeting;
    }

    public String greetingFromPt(){
        return config.getPt();
    }
    public String greetingFromJp(){
        return config.getJp();
    }

}
