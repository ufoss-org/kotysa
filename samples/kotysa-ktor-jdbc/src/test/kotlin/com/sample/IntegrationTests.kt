package com.sample

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.http.*
import io.ktor.server.testing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import kotlin.test.Test
import kotlin.test.assertEquals


class IntegrationTests {
    private val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())
    
    @Test
    fun `Request HTTP API endpoint for listing all users`() {
        withTestApplication({
            configureApp("test")
            
            // init DB
            val roleRepository by closestDI().instance<RoleRepository>()
            roleRepository.init()
            val userRepository by closestDI().instance<UserRepository>()
            userRepository.init()
        }) {
            handleRequest(HttpMethod.Get, "/api/users").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val list = response.parseBodyList<User>()
            }
        }
    }

    private inline fun <reified T> TestApplicationResponse.parseBody(): T =
        mapper.readValue(content, T::class.java)

    private inline fun <reified T> TestApplicationResponse.parseBodyList(): List<T> {
        val type = mapper.getTypeFactory().constructCollectionType(List::class.java, T::class.java)
        return mapper.readValue(content, type);
    }
}
