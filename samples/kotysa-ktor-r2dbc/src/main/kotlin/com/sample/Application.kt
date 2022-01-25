package com.sample

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.application.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureApp()
    }.start(wait = true)
}

fun Application.configureApp() {
    configuration()
    web()
}
