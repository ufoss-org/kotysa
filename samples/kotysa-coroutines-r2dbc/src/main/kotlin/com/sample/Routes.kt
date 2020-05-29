package com.sample

import org.springframework.web.reactive.function.server.coRouter

fun routes(userHandler: UserHandler) = coRouter {
    GET("/api/user", userHandler::listApi)
    GET("/api/user/{id}", userHandler::userApi)
}
