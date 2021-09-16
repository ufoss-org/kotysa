package com.sample

import org.springframework.web.servlet.function.router

fun routes(userHandler: UserHandler) = router {
    GET("/api/users", userHandler::listApi)
    GET("/api/users/{id}", userHandler::userApi)
}
