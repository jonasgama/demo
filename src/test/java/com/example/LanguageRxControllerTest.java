package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.dto.LanguageDTO;
import com.example.inmemory.InMemoryLanguages;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class LanguageRxControllerTest {

  @Inject EmbeddedApplication<?> application;

  @Inject
  @Client("/languages/rx")
  private Rx3HttpClient client;

  @Inject private InMemoryLanguages inMemoryLanguages;

  @Test
  void testItWorks() {
    Assertions.assertTrue(application.isRunning());
  }

  @Test
  public void shouldSaveNewLanguage() {
    LanguageDTO body = new LanguageDTO("German");
    HttpResponse response =
        client
            .exchange(HttpRequest.PUT("/" + UUID.randomUUID(), body), HttpResponse.class)
            .blockingLast();
    assertEquals(response.status(), HttpStatus.NO_CONTENT);
  }

  @Test
  public void shouldRemove() {
    LanguageDTO body = new LanguageDTO("Spanish");
    UUID uuid = UUID.randomUUID();

    HttpResponse insertion =
        client.exchange(HttpRequest.PUT("/" + uuid, body), HttpResponse.class).blockingLast();
    assertEquals(insertion.status(), HttpStatus.NO_CONTENT);

    HttpResponse removal =
        client.exchange(HttpRequest.DELETE("/" + uuid, null), HttpResponse.class).blockingLast();
    assertEquals(removal.status(), HttpStatus.NO_CONTENT);
  }

  @Test
  public void shouldHandleNonexistentID() {
    UUID uuid = UUID.randomUUID();
    HttpClientResponseException httpException = assertThrows(HttpClientResponseException.class,
        () ->client.exchange(HttpRequest.DELETE("/"+uuid, null), HttpResponse.class).blockingLast());
    assertEquals(httpException.getStatus(), HttpStatus.NOT_FOUND);
  }

  @Test
  public void shouldGetAllInsertedLanguages() {
    UUID uuid = UUID.randomUUID();
    inMemoryLanguages.put(uuid, "Latin");
    inMemoryLanguages.put(UUID.randomUUID(), "Greek");
    inMemoryLanguages.put(UUID.randomUUID(), "Hebraic");
    inMemoryLanguages.put(UUID.randomUUID(), "Chinese");
    Flowable<HttpResponse<List>> exchange = client.exchange(HttpRequest.GET("/"), List.class);
    assertTrue(exchange.blockingLast().body().size()>=4);
    assertEquals(exchange.blockingLast().getStatus(), HttpStatus.OK);

  }

  @Test
  public void shouldGetLanguage() {
    UUID uuid = UUID.randomUUID();
    inMemoryLanguages.put(uuid, "French");

    Single<HttpResponse<LanguageDTO>> exchange =
        client.exchange(HttpRequest.GET("/" + uuid), LanguageDTO.class).singleOrError();

    assertEquals(exchange.blockingGet().body().language, "French");
    assertEquals(exchange.blockingGet().getStatus(), HttpStatus.OK);
  }
}
