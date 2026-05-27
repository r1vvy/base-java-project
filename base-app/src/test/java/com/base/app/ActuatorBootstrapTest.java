package com.base.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActuatorBootstrapTest {

    @LocalServerPort
    private int port;

    @Autowired
    ApplicationContext ctx;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        this.client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void shouldHaveAllEndpoints() {
        testActuatorEndpoint("/health");
        testActuatorEndpoint("/info");
        testActuatorEndpoint("/metrics");
    }

    private void testActuatorEndpoint(String uri) {
        client.get().uri(uri)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> logResponse(uri, response));
    }

    private void logResponse(String uri, EntityExchangeResult<byte[]> response) {
        Optional.ofNullable(response.getResponseBody())
                .map(String::new)
                .ifPresent(body -> log.info("{} response: {}", uri, body));
    }
}