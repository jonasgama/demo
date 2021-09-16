package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.dto.LanguageDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class LanguageControllerTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/")
    private Rx3HttpClient client;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    public void shouldSaveNewLanguage(){
        LanguageDTO body = new LanguageDTO("German");
        HttpResponse response = client.exchange(HttpRequest.PUT("/languages/"+UUID.randomUUID(), body), HttpResponse.class).blockingLast();
        assertEquals(response.status(), HttpStatus.NO_CONTENT);
    }


    @Test
    public void shouldRemove(){
        LanguageDTO body = new LanguageDTO("Spanish");
        UUID uuid = UUID.randomUUID();

        HttpResponse insertion = client.exchange(HttpRequest.PUT("/languages/"+uuid, body), HttpResponse.class).blockingLast();
        assertEquals(insertion.status(), HttpStatus.NO_CONTENT);

        HttpResponse removal = client.exchange(HttpRequest.DELETE("/languages/"+uuid, null), HttpResponse.class).blockingLast();
        assertEquals(removal.status(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldHandleNonexistentID(){
        UUID uuid = UUID.randomUUID();
        HttpClientResponseException httpException = assertThrows(HttpClientResponseException.class,
            () ->client.exchange(HttpRequest.DELETE("/languages/"+uuid, null), HttpResponse.class).blockingLast());
        assertEquals(httpException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldGetAllInsertedLanguages(){
        client.exchange(HttpRequest.GET("/languages"), List.class);

    }
}
