package com.sample

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*

@Suppress("UNUSED_PARAMETER")
class UserHandler(private val repository: UserRepository) {

    suspend fun listApi(request: ServerRequest) = ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyAndAwait(repository.findAll())

    suspend fun userApi(request: ServerRequest) = ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(repository.findOne(request.pathVariable("id").toInt()))

}