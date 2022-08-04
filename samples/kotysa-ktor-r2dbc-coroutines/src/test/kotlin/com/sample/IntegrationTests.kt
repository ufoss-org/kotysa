package com.sample

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test


class IntegrationTests {
    private val json = DefaultJson

    @Test
    fun `Request HTTP API endpoint for listing all users`() = kotysaApiTest {
        val response = client.get("/api/users")
        assertThat(response.status).isEqualTo(HttpStatusCode.OK)
        assertThat(response.parseBodyList<User>())
            .hasSize(2)
    }

    @Test
    fun `Request HTTP API endpoint for getting one specified user`() = kotysaApiTest {
        val response = client.get("/api/users/123")
        assertThat(response.status).isEqualTo(HttpStatusCode.OK)
        assertThat(response.parseBody<User>().id).isEqualTo(123)
    }

    private fun kotysaApiTest(test: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        application { configureApp() }
        test.invoke(this)
    }

    private suspend inline fun <reified T> HttpResponse.parseBody(): T =
        json.decodeFromString(bodyAsText())

    private suspend inline fun <reified T> HttpResponse.parseBodyList(): List<T> =
        json.decodeFromString(bodyAsText())
}
