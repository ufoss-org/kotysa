package com.sample

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests {

    private lateinit var client: WebTestClient

    @BeforeAll
    fun before(@LocalServerPort port: Int) {
        client = WebTestClient.bindToServer()
            .baseUrl("http://localhost:$port")
            .build()
    }

    @Test
    fun `Request HTTP API endpoint for listing all users`() {
        client.get().uri("/api/users").exchange()
            .expectStatus().is2xxSuccessful
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBodyList<User>()
            .hasSize(2)
    }

    @Test
    fun `Request HTTP API endpoint for getting one specified user`() {
        client.get().uri("/api/users/123").exchange()
            .expectStatus().is2xxSuccessful
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody<User>()
            .consumeWith { 
                assertThat(it.responseBody!!.id).isEqualTo(123)
            }
    }
}
