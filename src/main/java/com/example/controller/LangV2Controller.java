package com.example.controller;

import com.example.dto.LanguageDTO;
import com.example.entity.LanguageEntity;
import com.example.repo.LanguagesRepository;
import com.example.service.LangService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Inject;
import java.util.List;
import java.util.concurrent.Flow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/languages/v2")
public class LangV2Controller {


    private static final Logger LOG = LoggerFactory.getLogger(LangV2Controller.class);

    @Inject
    private LangService service;


    @Get
    public Single<List<LanguageDTO>> get(){
        LOG.debug("get list from jpa "+Thread.currentThread().getName());
        return Single.just(service.get());
    }
}
