package com.sample

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.sample.UserRepository
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.routes() {
    install(ContentNegotiation) {
        jackson {
            registerModule(JavaTimeModule())  // support java.time.* types
        }
    }

    routing {
        val userRepository by closestDI().instance<UserRepository>()

        get("/api/users") {
            call.respond(userRepository.findAll())
        }

        get("/api/users/{id}") {
            call.respond(userRepository.findOne(call.parameters["login"]!!.toInt()))
        }
    }
}
