package com.example.controller;

import com.example.dto.LanguageDTO;
import com.example.entity.LanguageEntity;
import com.example.repo.LanguagesRepository;
import com.example.service.LangService;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/languages/v2")
public class LangV2Controller {


    private static final Logger LOG = LoggerFactory.getLogger(LangV2Controller.class);

    @Inject
    private LangService service;


    @Get("/pagination")
    public Single<Slice<LanguageDTO>> get(@QueryValue Optional<Integer> page, @QueryValue Optional<Integer> size){
        LOG.debug("get pageable list from jpa "+Thread.currentThread().getName());
        Integer pageQuery = page.isEmpty() ? 0 : page.get() - 1; //page 0 would be the first one
        Integer sizeQuery = size.isEmpty() ? 2 : size.get();
        return Single.just(service.get(Pageable.from(pageQuery, sizeQuery)));
    }

    @Get
    public Single<List<LanguageDTO>> get(@QueryValue Optional<String> name){
        LOG.debug("get list like name from jpa "+Thread.currentThread().getName());
        if(name.isEmpty()) return Single.just(service.get());
        return Single.just(service.get(name.get()+"%"));
    }

    @Get("/desc")
    public Single<List<LanguageDTO>> get(){
        LOG.debug("get desc list from jpa "+Thread.currentThread().getName());
        return Single.just(service.getByDesc());
    }
}
