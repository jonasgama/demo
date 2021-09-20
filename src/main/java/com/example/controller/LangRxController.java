package com.example.controller;

import com.example.dto.LanguageDTO;
import com.example.inmemory.InMemoryLanguages;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/languages/rx")
public class LangRxController {


    private static final Logger LOG = LoggerFactory.getLogger(LangRxController.class);

    @Inject
    private InMemoryLanguages languages;

    @Operation(summary = "save or include a new item according to the given uuid")
    @ApiResponse(
        content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @ApiResponse(responseCode = "400", description = "invalid language name threshold length")
    @Tag(name="languages")
    @Put("/{uuid}")
    public Single<HttpResponse> put(@PathVariable UUID uuid, @Body LanguageDTO body){
        LOG.debug("put"+Thread.currentThread().getName());
        languages.put(uuid, body.language);
        return Single.fromCallable(()->HttpResponse.noContent());
    }

    @Get
    public Flowable<LanguageDTO> get(){
        LOG.debug("get list"+Thread.currentThread().getName());
        return Flowable.fromIterable(languages.get());
    }

    @Get("/{uuid}")
    public Single<LanguageDTO> get(@PathVariable UUID uuid){
        LOG.debug("get "+Thread.currentThread().getName());
        return Single.fromCallable(() -> languages.get(uuid));
    }

    @Delete("/{uuid}")
    public Single<HttpResponse> delete(@PathVariable UUID uuid){
        LOG.debug("delete"+Thread.currentThread().getName());
        if(languages.delete(uuid)){
           return Single.fromCallable(()->HttpResponse.noContent());
       }
       return Single.fromCallable(()->HttpResponse.notFound());
    }
}
