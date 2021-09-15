package com.example.controller;

import com.example.inmemory.InMemoryLanguages;
import com.example.service.HelloService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import java.util.UUID;

@Controller("/languages")
public class LangController {


    @Inject
    private InMemoryLanguages languages;

    @Put("/{uuid}")
    public HttpResponse<? extends Object> put(@PathVariable UUID uuid, @Body LanguageRequest body){
        languages.put(uuid, body.language);
        return HttpResponse.noContent();
    }

    @Delete("/{uuid}")
    public HttpResponse<? extends Object> delete(@PathVariable UUID uuid){
       if(languages.delete(uuid)){
           return HttpResponse.noContent();
       }
       return HttpResponse.notFound();
    }

    public static class LanguageRequest{
        public String language;
    }
}
