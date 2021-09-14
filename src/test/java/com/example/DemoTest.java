package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class DemoTest {

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
    public void shouldReceiveHelloWorld(){
        String retrieve = client.retrieve("/hello/eng").blockingLast();
        assertEquals(retrieve,"Hello");
    }

    @Test
    public void shouldReceivePortugueseGreeting() {
        String retrieve = client.retrieve("/hello/pt").blockingLast();
        assertEquals(retrieve, "Olá");
    }

    @Test
    public void shouldReceiveJapaneseGreeting() {
        String retrieve = client.retrieve("/hello/jp").blockingLast();
        assertEquals(retrieve, "Ohayou");
    }

    @Test
    public void shoudHandleNotFoundLang() {
        HttpClientResponseException httpException = assertThrows(HttpClientResponseException.class,
            () -> client.exchange("/hello/qwerty", String.class).blockingLast());

        assertEquals(httpException.getStatus(), HttpStatus.NOT_FOUND);
    }

}
