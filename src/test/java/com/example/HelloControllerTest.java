package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.reactivex.rxjava3.annotations.NonNull;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class HelloControllerTest {

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
    public void shouldHandleNotFoundLang() {
        HttpClientResponseException httpException = assertThrows(HttpClientResponseException.class,
            () -> client.exchange("/hello/qwerty", String.class).blockingLast());

        assertEquals(httpException.getStatus(), HttpStatus.NOT_FOUND);
    }


    @Test
    public void shouldReceiveStubGreeting() {
        MutableHttpRequest<Object> get = HttpRequest.GET("/hello/stub");
        HttpResponse<ObjectNode> exchange = client.exchange(get, ObjectNode.class).blockingLast();
        assertTrue(String.valueOf(exchange.getBody().get()).contains("text_message"));
        assertTrue(String.valueOf(exchange.getBody().get()).contains("random_number"));
        assertTrue(String.valueOf(exchange.getBody().get()).contains("time_utc"));
    }

}
