package com.sample

import org.springframework.web.reactive.function.server.router

fun routes(userHandler: UserHandler) = router {
    GET("/api/user", userHandler::listApi)
    GET("/api/user/{id}", userHandler::userApi)
}
