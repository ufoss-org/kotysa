package com.sample

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class WebConfig(private val userHandler: UserHandler) {

    @Bean
    fun appRouter() = router {
        GET("/api/user", userHandler::listApi)
        GET("/api/user/{id}", userHandler::userApi)
    }
}
