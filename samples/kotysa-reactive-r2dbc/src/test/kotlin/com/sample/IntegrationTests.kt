package com.sample

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests {

    @Autowired
    private lateinit var config: WebConfig
    
    private val client: WebTestClient by lazy {
        WebTestClient
            .bindToRouterFunction(config.appRouter())
            .build()
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
}
