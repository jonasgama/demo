package com.example;

import io.micronaut.http.client.HttpClient;
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
        String retrieve = client.retrieve("/hello").blockingLast();
        assertEquals(retrieve,"Hello World");
    }

}
