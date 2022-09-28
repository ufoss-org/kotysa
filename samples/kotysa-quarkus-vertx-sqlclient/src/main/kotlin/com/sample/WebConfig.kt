package com.sample

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("/api/users")
@Produces("application/json")
class WebConfig(private val repository: UserRepository) {

    @GET
    fun getAll(): Multi<User> = repository.findAll()

    @GET
    @Path("{id}")
    fun getSingle(id: Int): Uni<User> = repository.findOne(id)
}