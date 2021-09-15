package com.example.config;

import io.micronaut.context.annotation.ConfigurationInject;
import io.micronaut.context.annotation.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.validation.constraints.NotBlank;

@ConfigurationProperties("hello.config.greeting")
public class ConfigProps {

    private String pt;
    private String jp;


    @ConfigurationInject
    public ConfigProps(@NotBlank String pt, @NotBlank String jp) {
        this.pt = pt;
        this.jp = jp;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }
}
