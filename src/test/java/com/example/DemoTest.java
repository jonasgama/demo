package com.example;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
