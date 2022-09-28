package com.sample

import io.quarkus.test.junit.QuarkusTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
class UserRepositoryTests {

    @Inject
    lateinit var repository: UserRepository

    @Test
    fun count() {
        val count = repository.count().await().indefinitely()
        assertThat(count, equalTo(2))
    }

    @Test
    fun selectWithJoin() {
        val userDtos = repository.selectWithJoin().await().indefinitely()
        assertThat(userDtos, hasSize(2))
    }
}
