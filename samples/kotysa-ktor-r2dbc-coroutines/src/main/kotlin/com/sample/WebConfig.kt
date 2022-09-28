package com.sample

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.io.OutputStream

fun Application.webConfig() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        val userRepository by closestDI().instance<UserRepository>()
        val json by closestDI().instance<Json>()

        get("/api/users") {
            call.respondOutputStream(ContentType.Application.Json) {
                userRepository.findAll().writeToStream(this, json)
            }
        }

        get("/api/users/{id}") {
            call.respond(userRepository.findOne(call.parameters["id"]!!.toInt()))
        }
    }
}

// inspiration : https://github.com/Kotlin/kotlinx.serialization/blob/master/formats/json-tests/jvmTest/src/kotlinx/serialization/features/JsonLazySequenceTest.kt
@OptIn(ExperimentalSerializationApi::class)
@Suppress("BlockingMethodInNonBlockingContext") // ktor already makes sure this executes in Dispatchers.IO context
private suspend inline fun <reified T> Flow<T>.writeToStream(os: OutputStream, json: Json) {
    os.write("[".encodeToByteArray())
    collectIndexed { index, value ->
        if (index > 0) {
            os.write(",".encodeToByteArray())
        }
        json.encodeToStream(value, os)
    }
    os.write("]".encodeToByteArray())
}
