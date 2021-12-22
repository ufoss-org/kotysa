package com.sample

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

@Component
@Suppress("UNUSED_PARAMETER")
class UserHandler(private val repository: UserRepository) {

    fun listApi(request: ServerRequest) = ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(repository.findAll())

    fun userApi(request: ServerRequest) = ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(repository.findOne(request.pathVariable("id").toInt()))

}
