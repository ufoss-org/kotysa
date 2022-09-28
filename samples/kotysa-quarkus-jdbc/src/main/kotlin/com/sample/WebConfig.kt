package com.sample

import javax.ws.rs.GET
import javax.ws.rs.Path
import org.jboss.resteasy.annotations.jaxrs.PathParam
import javax.ws.rs.Produces

@Path("/api/users")
@Produces("application/json")
class WebConfig(private val repository: UserRepository) {

    @GET
    fun getAll() = repository.findAll()

    @GET
    @Path("{id}")
    fun getSingle(@PathParam(value = "id") id: Int) = repository.findOne(id)
}