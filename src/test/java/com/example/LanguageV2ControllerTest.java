package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.dto.LanguageDTO;
import com.example.entity.CountryEntity;
import com.example.entity.LanguageEntity;
import com.example.inmemory.InMemoryLanguages;
import com.example.repo.CountryRepository;
import com.example.repo.LanguagesRepository;
import com.example.service.LangService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
class LanguageV2ControllerTest {

  @Inject
  @Client("/languages/v2")
  private Rx3HttpClient client;

  @Inject
  private LanguagesRepository langRepository;

  @Inject
  private LangService langService;

  @Inject
  private CountryRepository countryRepository;


  @BeforeEach
  public void setup(){

    CountryEntity angola = new CountryEntity("angola");
    CountryEntity brazil = new CountryEntity("brazil");
    CountryEntity portugal = new CountryEntity("portugal");

    langService.save(new LanguageEntity(UUID.randomUUID(), "portugues"), List.of(angola, brazil, portugal));
    langService.save(new LanguageEntity(UUID.randomUUID(), "congo"), List.of(angola));
    langService.save(new LanguageEntity(UUID.randomUUID(), "ganguela"), List.of(angola));


  }

  @Test
  public void shouldGetAllInsertedLanguages() {


    Flowable<HttpResponse<List>> exchange = client.exchange(HttpRequest.GET("/"), List.class);
    assertTrue(exchange.blockingLast().body().size()>=3);
    assertEquals(exchange.blockingLast().getStatus(), HttpStatus.OK);

  }
}
