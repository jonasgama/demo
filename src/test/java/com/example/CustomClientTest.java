package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.client.CustomClient;
import com.example.dto.LanguageDTO;
import com.example.inmemory.InMemoryLanguages;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
class CustomClientTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/")
    private CustomClient client;

    @Inject
    private InMemoryLanguages inMemoryLanguages;

    private String authorization;

    @Test
    void testItWorks() {
        assertTrue(application.isRunning());
    }



    @BeforeEach
    public void setup(){
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("sherlock", "pleaseChangeThisSecretForANewOne");
        BearerAccessRefreshToken bearer = client.login(credentials);
        authorization = client.bearer(bearer.getAccessToken());
    }

    @Test
    public void shouldGetAllInsertedLanguages(){
        inMemoryLanguages.put(UUID.randomUUID(), "lang1");
        inMemoryLanguages.put(UUID.randomUUID(), "lang2");
        inMemoryLanguages.put(UUID.randomUUID(), "lang3");

        List<LanguageDTO> languages = client.getAllLanguages(authorization)
            .blockingLast();

        assertTrue(languages.size()>=3);

    }

    @Test
    public void shouldHandleNonexistentID(){
        UUID uuid = UUID.randomUUID();

        HttpClientResponseException httpException = assertThrows(HttpClientResponseException.class,
            () -> client.deleteLanguage(authorization, uuid).blockingGet());
        assertEquals(httpException.getStatus(), HttpStatus.NOT_FOUND);
    }
}
