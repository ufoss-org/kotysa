package com.sample

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces

@Path("/api/users")
@Produces("application/json")
class WebConfig(private val repository: UserRepository) {

    @GET
    fun getAll() = repository.findAll()

    @GET
    @Path("{id}")
    fun getSingle(id: Int) = repository.findOne(id)
}