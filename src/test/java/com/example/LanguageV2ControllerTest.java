package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.dto.LanguageDTO;
import com.example.entity.LanguageEntity;
import com.example.inmemory.InMemoryLanguages;
import com.example.repo.LanguagesRepository;
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
  private LanguagesRepository repository;


  @BeforeEach
  public void setup(){
   Flowable.just(
        new LanguageEntity(UUID.randomUUID(), "Latin"),
        new LanguageEntity(UUID.randomUUID(), "Greek"),
        new LanguageEntity(UUID.randomUUID(), "Hebraic"),
        new LanguageEntity(UUID.randomUUID(), "Chinese")
    ).map(repository::save)
        .blockingLast();


  }

  @Test
  public void shouldGetAllInsertedLanguages() {


    Flowable<HttpResponse<List>> exchange = client.exchange(HttpRequest.GET("/"), List.class);
    assertTrue(exchange.blockingLast().body().size()>=4);
    assertEquals(exchange.blockingLast().getStatus(), HttpStatus.OK);

  }
}
