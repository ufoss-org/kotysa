package com.sample

import io.quarkus.test.junit.QuarkusTest
import kotlin.reflect.jvm.javaType
import kotlin.reflect.typeOf

import io.restassured.module.kotlin.extensions.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import javax.ws.rs.core.MediaType

@QuarkusTest
class IntegrationTests {

    @Test
    fun `Request HTTP API endpoint for listing all users`() {
        val users =
            When {
                get("/api/users")
            } Then {
                statusCode(200)
                contentType(MediaType.APPLICATION_JSON)
            } Extract  { `as`<List<User>>(typeOf<List<User>>().javaType) }
        assertThat(users, hasSize(2))
    }

    @Test
    fun `Request HTTP API endpoint for getting one specified user`() {
        val user =
            When {
                get("/api/users/123")
            } Then {
                statusCode(200)
                contentType(MediaType.APPLICATION_JSON)
            } Extract { `as`(User::class.java) }
        assertThat(user.id, equalTo(123))
    }
}
