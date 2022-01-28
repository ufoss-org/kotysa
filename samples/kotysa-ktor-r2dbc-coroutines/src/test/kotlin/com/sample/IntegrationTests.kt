package com.sample

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test
import kotlin.test.assertEquals


class IntegrationTests {
    private val json = DefaultJson

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
        withTestApplication({ configureApp() }, test)
    }

    private inline fun <reified T> TestApplicationResponse.parseBody(): T =
        json.decodeFromString(content!!)

    private inline fun <reified T> TestApplicationResponse.parseBodyList(): List<T> =
        json.decodeFromString(content!!)

    /*
    // inspiration : https://github.com/Kotlin/kotlinx.serialization/blob/master/formats/json/jvmTest/src/kotlinx/serialization/features/JsonStreamFlowTest.kt
    private inline fun <reified T> TestApplicationResponse.parseBodyList(): List<T> = runBlocking {
        json.readFromStream<T>(call.receiveStream()).toList()
    }

    private suspend inline fun <reified T> Json.readFromStream(iss: InputStream): Flow<T> = flow {
        val serial = serializer<T>()
        val iter = iterateOverStream(iss, serial)
        while (iter.hasNext()) {
            emit(iter.next())
        }
    }.flowOn(Dispatchers.IO)

    @OptIn(ExperimentalSerializationApi::class)
    private fun <T> Json.iterateOverStream(stream: InputStream, deserializer: DeserializationStrategy<T>): Iterator<T> =
        this.decodeToSequence(stream, deserializer).iterator()
     */
}
