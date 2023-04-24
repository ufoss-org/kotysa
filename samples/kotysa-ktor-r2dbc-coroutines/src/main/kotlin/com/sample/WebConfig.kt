package com.sample

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
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
