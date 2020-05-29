package com.sample

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList

class IntegrationTests {

    private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181").build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "dev")
    }

    @Test
    fun `Request HTTP API endpoint for listing all users`() {
        client.get().uri("/api/user").exchange()
                .expectStatus().is2xxSuccessful
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBodyList<User>()
                .hasSize(2)
    }

    @Test
    fun `Request HTTP API endpoint for getting one specified user`() {
        client.get().uri("/api/user/123").exchange()
                .expectStatus().is2xxSuccessful
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody<User>()
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }
}
