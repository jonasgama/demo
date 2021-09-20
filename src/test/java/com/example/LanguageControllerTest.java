package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.dto.LanguageDTO;
import com.example.inmemory.InMemoryLanguages;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.reactivex.rxjava3.annotations.NonNull;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
class LanguageControllerTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/")
    private Rx3HttpClient client;

    @Inject
    private InMemoryLanguages inMemoryLanguages;

    private HttpResponse<BearerAccessRefreshToken> bearer;

    @Test
    void testItWorks() {
        assertTrue(application.isRunning());
    }



    @BeforeEach
    public void setup(){
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("sherlock", "pleaseChangeThisSecretForANewOne");
        MutableHttpRequest<UsernamePasswordCredentials> authRequest = HttpRequest.POST("/login",
            credentials);
        bearer = client.exchange(
            authRequest, BearerAccessRefreshToken.class).blockingLast();
    }

    @Test
    public void shouldSaveNewLanguage(){
        LanguageDTO body = new LanguageDTO("German");
        MutableHttpRequest<LanguageDTO> put = HttpRequest.PUT("/languages/" + UUID.randomUUID(),
            body).bearerAuth(bearer.body().getAccessToken());

        HttpResponse response = client.exchange(put, HttpResponse.class).blockingLast();
        assertEquals(response.status(), HttpStatus.NO_CONTENT);
    }


    @Test
    public void shouldRemove(){
        LanguageDTO body = new LanguageDTO("Spanish");
        UUID uuid = UUID.randomUUID();

        MutableHttpRequest<LanguageDTO> put = HttpRequest.PUT("/languages/" + uuid, body)
            .bearerAuth(bearer.body().getAccessToken());

        HttpResponse insertion = client.exchange(put, HttpResponse.class).blockingLast();
        assertEquals(insertion.status(), HttpStatus.NO_CONTENT);

        MutableHttpRequest<Object> delete = HttpRequest.DELETE("/languages/" + uuid)
            .bearerAuth(bearer.body().getAccessToken());
        HttpResponse removal = client.exchange(delete, HttpResponse.class).blockingLast();
        assertEquals(removal.status(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldHandleNonexistentID(){
        UUID uuid = UUID.randomUUID();
        MutableHttpRequest<Object> delete = HttpRequest.DELETE("/languages/" + uuid)
            .bearerAuth(bearer.body().getAccessToken());

        HttpClientResponseException httpException = assertThrows(HttpClientResponseException.class,
            () ->client.exchange(delete, HttpResponse.class).blockingLast());
        assertEquals(httpException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldGetAllInsertedLanguages(){
        inMemoryLanguages.put(UUID.randomUUID(), "lang1");
        inMemoryLanguages.put(UUID.randomUUID(), "lang2");
        inMemoryLanguages.put(UUID.randomUUID(), "lang3");

        MutableHttpRequest<Object> get = HttpRequest.GET("/languages")
            .bearerAuth(bearer.body().getAccessToken());
        HttpResponse<List> exchange = client.exchange(get, List.class).blockingLast();

        assertEquals(exchange.getStatus(), HttpStatus.OK);
        assertTrue(exchange.getBody().get().size()>=3);

    }
}
