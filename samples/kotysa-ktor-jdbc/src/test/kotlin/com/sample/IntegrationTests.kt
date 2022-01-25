package com.sample

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.http.*
import io.ktor.server.testing.*
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test
import kotlin.test.assertEquals


class IntegrationTests {
    private val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    @Test
    fun `Request HTTP API endpoint for listing all users`() = kotysaApiTest {
        handleRequest(HttpMethod.Get, "/api/users").apply {
            assertEquals(HttpStatusCode.OK, response.status())
            assertThat(response.parseBodyList<User>())
                .hasSize(2)
        }
    }

    @Test
    fun `Request HTTP API endpoint for getting one specified user`() = kotysaApiTest {
        handleRequest(HttpMethod.Get, "/api/users/123").apply {
            assertEquals(HttpStatusCode.OK, response.status())
            response.parseBody<User>()
        }
    }

    private fun kotysaApiTest(test: TestApplicationEngine.() -> Unit) {
        withTestApplication({ configureApp("test") }, test)
    }

    private inline fun <reified T> TestApplicationResponse.parseBody(): T =
        mapper.readValue(content, T::class.java)

    private inline fun <reified T> TestApplicationResponse.parseBodyList(): List<T> {
        val type = mapper.typeFactory.constructCollectionType(List::class.java, T::class.java)
        return mapper.readValue(content, type);
    }
}
