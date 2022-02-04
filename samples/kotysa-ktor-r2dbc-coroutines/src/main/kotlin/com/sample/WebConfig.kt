package com.sample

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.withContext
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

// inspiration : https://github.com/Kotlin/kotlinx.serialization/blob/master/formats/json/jvmTest/src/kotlinx/serialization/features/JsonStreamFlowTest.kt
@OptIn(ExperimentalSerializationApi::class)
private suspend inline fun <reified T> Flow<T>.writeToStream(os: OutputStream, json: Json) =
    withContext(Dispatchers.IO) {
        os.write("[".encodeToByteArray())
        collectIndexed { index, value ->
            withContext(Dispatchers.IO) {
                if (index > 0) {
                    os.write(",".encodeToByteArray())
                }
                json.encodeToStream(value, os)
            }
        }
        os.write("]".encodeToByteArray())
    }
