package com.sample

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.webConfig() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        val userRepository by closestDI().instance<UserRepository>()

        get("/api/users") {
            call.respond(userRepository.findAll())
        }

        get("/api/users/{id}") {
            call.respond(userRepository.findOne(call.parameters["id"]!!.toInt()))
        }
    }
}
