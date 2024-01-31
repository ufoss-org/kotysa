package com.sample

import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeExactly
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*

class IntegrationTests : StringSpec({

    "Request HTTP API endpoint for listing all users" {
        kotysaApiTest {
            val response = client.get("/api/users")
            response shouldHaveStatus HttpStatusCode.OK
            response.body<List<User>>() shouldHaveSize 2
        }
    }

    "Request HTTP API endpoint for getting one specified user" {
        kotysaApiTest {
            val response = client.get("/api/users/123")
            response shouldHaveStatus HttpStatusCode.OK
            response.body<User>().id shouldBeExactly 123
        }
    }
})

internal fun kotysaApiTest(test: suspend ClientProvider.() -> Unit) = testApplication {
    application {
        configureApp()
    }
    // configure a custom HTTP client for tests with JSON support
    val httpClient = createClient {
        install(ContentNegotiation) {
            json()
        }
    }
    test.invoke(ClientProvider(httpClient))
}

@JvmInline
internal value class ClientProvider(internal val client: HttpClient)
