package com.example.service;

import com.example.config.ConfigProps;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class HelloService {

  private static final Logger LOG = LoggerFactory.getLogger(HelloService.class);

  @Property(name = "hello.service.greeting", defaultValue = "Hello from service")
  private String greeting;

  @Inject private ConfigProps config;

  @EventListener
  public void onStartup(StartupEvent event) {
    LOG.debug("Service's been initialized");
  }

  public String greeting(String language) {
    switch (language) {
      case "pt":
        return config.getPt();
      case "jp":
        return config.getJp();
      case "eng":
        return "Hello";
    }
    return "";
  }

  public String greetingFromPt() {
    return config.getPt();
  }

  public String greetingFromJp() {
    return config.getJp();
  }
}
