package com.example.controller;

import com.example.dto.LanguageDTO;
import com.example.inmemory.InMemoryLanguages;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/languages")
public class LangController {


    private static final Logger LOG = LoggerFactory.getLogger(LangController.class);

    @Inject
    private InMemoryLanguages languages;

    @Put("/{uuid}")
    public HttpResponse<? extends Object> put(@PathVariable UUID uuid, @Body LanguageDTO body){
        LOG.debug("put"+Thread.currentThread().getName());
        languages.put(uuid, body.language);
        return HttpResponse.noContent();
    }

    @Get
    public HttpResponse<List<LanguageDTO>> get(){
        LOG.debug("get list"+Thread.currentThread().getName());
        return HttpResponse.ok(languages.get());
    }

    @Delete("/{uuid}")
    public HttpResponse<? extends Object> delete(@PathVariable UUID uuid){
        LOG.debug("delete"+Thread.currentThread().getName());
        if(languages.delete(uuid)){
           return HttpResponse.noContent();
       }
       return HttpResponse.notFound();
    }
}
